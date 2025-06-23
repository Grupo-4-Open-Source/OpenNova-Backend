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
        var createPublicationCommand = CreateVehicleCommandFromResourceAssembler.toCommandFromResource(resource);
        var publicationId = this.vehicleCommandService.handle(createPublicationCommand);

        if (publicationId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getPublicationByIdQuery =new GetVehiclesByIdQuery(publicationId);
        var optionalPublication = this.vehicleQueryService.handle(getPublicationByIdQuery);

        var publicationResource = VehicleResourceFromEntityAssembler.toResourceFromEntity(optionalPublication.get());
        return new ResponseEntity<>(publicationResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VehicleResource>> getAllVehicles() {
        var getAllPublicationsQuery = new GetAllVehiclesQuery();
        var publications = this.vehicleQueryService.handle(getAllPublicationsQuery);

        var publicationResources = publications.stream()
                .map(VehicleResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(publicationResources);
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<VehicleResource> getVehicleById(@PathVariable Long vehicleId) {
        var getPublicationByIdQuery = new GetVehiclesByIdQuery(vehicleId);
        var optionalPublication = this.vehicleQueryService.handle(getPublicationByIdQuery);

        if (optionalPublication.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var publicationResource = VehicleResourceFromEntityAssembler.toResourceFromEntity(optionalPublication.get());
        return ResponseEntity.ok(publicationResource);
    }

    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long vehicleId) {
        var deletePublicationCommand = new DeleteVehicleCommand(vehicleId);
        this.vehicleCommandService.handle(deletePublicationCommand);
        return ResponseEntity.badRequest().build();
        }

    @PutMapping("/{vehicleId}")
    public ResponseEntity<VehicleResource> updateSaving(@PathVariable Long vehicleId, @RequestBody VehicleResource resource) {
        var updatePublicationCommand = UpdateVehicleCommandFromResourceAssembler.toCommandFromResource(vehicleId, resource);
        var optionalPublication = this.vehicleCommandService.handle(updatePublicationCommand);

        if (optionalPublication.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var publicationResource = VehicleResourceFromEntityAssembler.toResourceFromEntity(optionalPublication.get());
        return ResponseEntity.ok(publicationResource);
    }
}
