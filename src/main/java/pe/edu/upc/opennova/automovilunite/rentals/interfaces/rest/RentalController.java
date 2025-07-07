package pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands.DeleteRentalCommand;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.queries.GetAllRentalsQuery;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.queries.GetRentalByIdQuery;
import pe.edu.upc.opennova.automovilunite.rentals.domain.services.RentalCommandService;
import pe.edu.upc.opennova.automovilunite.rentals.domain.services.RentalQueryService;
import pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.resources.CreateRentalResource;
import pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.resources.RentalResource;
import pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.transform.CreateRentalCommandFromResourceAssembler;
import pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.transform.RentalResourceFromEntityAssembler;
import pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.transform.UpdateRentalCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/rentals", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Rentals", description = "Rental Management Endpoints")
public class RentalController {
    private final RentalCommandService rentalCommandService;
    private final RentalQueryService rentalQueryService;

    public RentalController(RentalCommandService rentalCommandService, RentalQueryService rentalQueryService) {
        this.rentalCommandService = rentalCommandService;
        this.rentalQueryService = rentalQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new Rental", description = "Creates a new Rental with the provided data")
    public ResponseEntity<RentalResource> createRental(@RequestBody @Valid CreateRentalResource resource) {
        var createRentalCommand = CreateRentalCommandFromResourceAssembler.toCommandFromResource(resource);
        Long rentalId = this.rentalCommandService.handle(createRentalCommand);

        if (rentalId == null || rentalId.equals(0L)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        var getRentalByIdQuery = new GetRentalByIdQuery(rentalId);
        var optionalRental = this.rentalQueryService.handle(getRentalByIdQuery);

        if (optionalRental.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var rentalResource = RentalResourceFromEntityAssembler.toResourceFromEntity(optionalRental.get());
        return new ResponseEntity<>(rentalResource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all Rentals", description = "Retrieves a list of all existing Rentals")
    public ResponseEntity<List<RentalResource>> getAllRentals() {
        var getAllRentalsQuery = new GetAllRentalsQuery();
        var rentals = this.rentalQueryService.handle(getAllRentalsQuery);

        var rentalResources = rentals.stream()
                .map(RentalResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(rentalResources);
    }

    @GetMapping("/{rentalId}")
    @Operation(summary = "Get a Rental by ID", description = "Retrieves a Rental by its unique internal ID")
    public ResponseEntity<RentalResource> getRentalById(@PathVariable Long rentalId) {
        var getRentalByIdQuery = new GetRentalByIdQuery(rentalId);
        var optionalRental = this.rentalQueryService.handle(getRentalByIdQuery);

        if (optionalRental.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var rentalResource = RentalResourceFromEntityAssembler.toResourceFromEntity(optionalRental.get());
        return ResponseEntity.ok(rentalResource);
    }

    @PutMapping("/{rentalId}")
    @Operation(summary = "Update a Rental", description = "Updates an existing Rental identified by its ID")
    public ResponseEntity<RentalResource> updateRental(@PathVariable Long rentalId, @RequestBody @Valid RentalResource resource) {
        var updateRentalCommand = UpdateRentalCommandFromResourceAssembler.toCommandFromResource(rentalId, resource);
        var optionalRental = this.rentalCommandService.handle(updateRentalCommand);

        if (optionalRental.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var rentalResource = RentalResourceFromEntityAssembler.toResourceFromEntity(optionalRental.get());
        return ResponseEntity.ok(rentalResource);
    }

    @DeleteMapping("/{rentalId}")
    @Operation(summary = "Delete a Rental", description = "Deletes a Rental identified by its ID")
    public ResponseEntity<?> deleteRental(@PathVariable Long rentalId) {
        this.rentalCommandService.handle(new DeleteRentalCommand(rentalId));
        return ResponseEntity.noContent().build();
    }
}