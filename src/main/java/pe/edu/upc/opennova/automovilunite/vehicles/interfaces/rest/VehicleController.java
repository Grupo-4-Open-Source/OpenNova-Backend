package pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.commands.DeleteVehicleCommand;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.queries.GetAllVehiclesQuery;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.queries.GetVehiclesByIdQuery;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.services.VehicleCommandService;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.services.VehicleQueryService;
import pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.resources.CreateVehicleResource;
import pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.resources.VehicleResource;
import pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.transform.CreateVehicleCommandFromResourceAssembler;
import pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.transform.VehicleResourceFromEntityAssembler;
import pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.transform.UpdateVehicleCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Vehicles", description = "Vehicles Management Endpoints")

public class VehicleController {
    private final VehicleCommandService vehicleCommandService;
    private final VehicleQueryService vehicleQueryService;

    public VehicleController(VehicleCommandService vehicleCommandService, VehicleQueryService vehicleQueryService) {
        this.vehicleCommandService = vehicleCommandService;
        this.vehicleQueryService = vehicleQueryService;
    }

    @PostMapping
    public ResponseEntity<VehicleResource> createVehicle(@RequestBody CreateVehicleResource resource) {
        var createVehicleCommand = CreateVehicleCommandFromResourceAssembler.toCommandFromResource(resource);
        var vehicleId = this.vehicleCommandService.handle(createVehicleCommand);

        if (vehicleId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getVehiclesByIdQuery =new GetVehiclesByIdQuery(vehicleId);
        var optionalVehicle = this.vehicleQueryService.handle(getVehiclesByIdQuery);

        var vehicleResource = VehicleResourceFromEntityAssembler.toResourceFromEntity(optionalVehicle.get());
        return new ResponseEntity<>(vehicleResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VehicleResource>> getAllVehicles() {
        var getAllVehiclesQuery = new GetAllVehiclesQuery();
        var vehicles = this.vehicleQueryService.handle(getAllVehiclesQuery);

        var vehicleResources = vehicles.stream()
                .map(VehicleResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(vehicleResources);
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<VehicleResource> getVehicleById(@PathVariable Long vehicleId) {
        var getVehiclesByIdQuery = new GetVehiclesByIdQuery(vehicleId);
        var optionalVehicle = this.vehicleQueryService.handle(getVehiclesByIdQuery);

        if (optionalVehicle.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var vehicleResource = VehicleResourceFromEntityAssembler.toResourceFromEntity(optionalVehicle.get());
        return ResponseEntity.ok(vehicleResource);
    }

    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long vehicleId) {
        var deleteVehicleCommand = new DeleteVehicleCommand(vehicleId);
        this.vehicleCommandService.handle(deleteVehicleCommand);
        return ResponseEntity.badRequest().build();
        }

    @PutMapping("/{vehicleId}")
    public ResponseEntity<VehicleResource> updateSaving(@PathVariable Long vehicleId, @RequestBody VehicleResource resource) {
        var updateVehicleCommand = UpdateVehicleCommandFromResourceAssembler.toCommandFromResource(vehicleId, resource);
        var optionalVehicle = this.vehicleCommandService.handle(updateVehicleCommand);

        if (optionalVehicle.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var vehicleResource = VehicleResourceFromEntityAssembler.toResourceFromEntity(optionalVehicle.get());
        return ResponseEntity.ok(vehicleResource);
    }
}
