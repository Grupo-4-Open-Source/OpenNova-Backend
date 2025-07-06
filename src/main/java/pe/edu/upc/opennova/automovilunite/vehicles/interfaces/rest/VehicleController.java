package pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.commands.DeleteVehicleCommand;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.queries.GetAllVehiclesQuery;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.queries.GetVehicleByIdQuery;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.queries.GetVehiclesByOwnerIdQuery;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.services.VehicleCommandService;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.services.VehicleQueryService;
import pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.resources.CreateVehicleResource;
import pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.resources.VehicleResource;
import pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.transform.CreateVehicleCommandFromResourceAssembler;
import pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.transform.VehicleResourceFromEntityAssembler;
import pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.transform.UpdateVehicleCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH })
@RestController
@RequestMapping(value = "/api/v1/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Vehicles", description = "Vehicles Management Endpoints")
public class VehicleController {
    private final VehicleCommandService vehicleCommandService;
    private final VehicleQueryService vehicleQueryService;

    public VehicleController(VehicleCommandService vehicleCommandService, VehicleQueryService vehicleQueryService) {
        this.vehicleCommandService = vehicleCommandService;
        this.vehicleQueryService = vehicleQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new Vehicle", description = "Creates a new Vehicle with the provided data")
    public ResponseEntity<VehicleResource> createVehicle(@RequestBody CreateVehicleResource resource) {
        var createVehicleCommand = CreateVehicleCommandFromResourceAssembler.toCommandFromResource(resource);
        return vehicleCommandService.handle(createVehicleCommand)
                .map(vehicle -> new ResponseEntity<>(VehicleResourceFromEntityAssembler.toResourceFromEntity(vehicle), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping
    @Operation(summary = "Get all Vehicles", description = "Retrieves a list of all existing Vehicles")
    public ResponseEntity<List<VehicleResource>> getAllVehicles() {
        var getAllVehiclesQuery = new GetAllVehiclesQuery();
        var vehicles = this.vehicleQueryService.handle(getAllVehiclesQuery);

        var vehicleResources = vehicles.stream()
                .map(VehicleResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(vehicleResources);
    }

    @GetMapping("/{vehicleId}")
    @Operation(summary = "Get a Vehicle by its ID", description = "Retrieves a Vehicle by its unique ID")
    public ResponseEntity<VehicleResource> getVehicleById(@PathVariable Long vehicleId) {
        var getVehicleByIdQuery = new GetVehicleByIdQuery(vehicleId);
        return this.vehicleQueryService.handle(getVehicleByIdQuery)
                .map(vehicle -> new ResponseEntity<>(VehicleResourceFromEntityAssembler.toResourceFromEntity(vehicle), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/owner/{ownerId}")
    @Operation(summary = "Get Vehicles by Owner ID", description = "Retrieves a list of Vehicles by a specific Owner ID")
    public ResponseEntity<List<VehicleResource>> getVehiclesByOwnerId(@PathVariable String ownerId) {
        var getVehiclesByOwnerIdQuery = new GetVehiclesByOwnerIdQuery(ownerId);
        var vehicles = this.vehicleQueryService.handle(getVehiclesByOwnerIdQuery);

        if (vehicles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var vehicleResources = vehicles.stream()
                .map(VehicleResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(vehicleResources);
    }


    @PutMapping("/{vehicleId}")
    @Operation(summary = "Update a Vehicle", description = "Updates an existing Vehicle identified by its ID")
    public ResponseEntity<VehicleResource> updateVehicle(@PathVariable Long vehicleId, @RequestBody VehicleResource resource) {
        var updateVehicleCommand = UpdateVehicleCommandFromResourceAssembler.toCommandFromResource(vehicleId, resource);
        return this.vehicleCommandService.handle(updateVehicleCommand)
                .map(vehicle -> new ResponseEntity<>(VehicleResourceFromEntityAssembler.toResourceFromEntity(vehicle), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{vehicleId}")
    @Operation(summary = "Delete a Vehicle", description = "Deletes a Vehicle identified by its ID")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long vehicleId) {
        var deleteVehicleCommand = new DeleteVehicleCommand(vehicleId);
        try {
            this.vehicleCommandService.handle(deleteVehicleCommand);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting vehicle: " + e.getMessage());
        }
    }
}