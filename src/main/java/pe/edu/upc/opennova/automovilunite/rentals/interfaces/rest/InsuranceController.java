package pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands.DeleteInsuranceCommand;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.queries.GetAllInsurancesQuery;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.queries.GetInsuranceByIdQuery;
import pe.edu.upc.opennova.automovilunite.rentals.domain.services.InsuranceCommandService;
import pe.edu.upc.opennova.automovilunite.rentals.domain.services.InsuranceQueryService;
import pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.resources.CreateInsuranceResource;
import pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.resources.InsuranceResource;
import pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.transform.CreateInsuranceCommandFromResourceAssembler;
import pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.transform.InsuranceResourceFromEntityAssembler;
import pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.transform.UpdateInsuranceCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/insurances", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Insurances", description = "Insurance Plan Management Endpoints (within Rentals Bounded Context)")
public class InsuranceController {
    private final InsuranceCommandService insuranceCommandService;
    private final InsuranceQueryService insuranceQueryService;

    public InsuranceController(InsuranceCommandService insuranceCommandService, InsuranceQueryService insuranceQueryService) {
        this.insuranceCommandService = insuranceCommandService;
        this.insuranceQueryService = insuranceQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new Insurance Plan", description = "Creates a new Insurance Plan with the provided data")
    public ResponseEntity<InsuranceResource> createInsurance(@RequestBody @Valid CreateInsuranceResource resource) {
        var createInsuranceCommand = CreateInsuranceCommandFromResourceAssembler.toCommandFromResource(resource);
        Long insuranceId = this.insuranceCommandService.handle(createInsuranceCommand);

        var getInsuranceByIdQuery = new GetInsuranceByIdQuery(insuranceId);
        var optionalInsurance = this.insuranceQueryService.handle(getInsuranceByIdQuery);

        if (optionalInsurance.isEmpty()) {
            return ResponseEntity.internalServerError().build();
        }

        var insuranceResource = InsuranceResourceFromEntityAssembler.toResourceFromEntity(optionalInsurance.get());
        return new ResponseEntity<>(insuranceResource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all Insurance Plans", description = "Retrieves a list of all existing Insurance Plans")
    public ResponseEntity<List<InsuranceResource>> getAllInsurances() {
        var getAllInsurancesQuery = new GetAllInsurancesQuery();
        var insurances = this.insuranceQueryService.handle(getAllInsurancesQuery);

        var insuranceResources = insurances.stream()
                .map(InsuranceResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(insuranceResources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an Insurance Plan by ID", description = "Retrieves an Insurance Plan by its unique internal ID")
    public ResponseEntity<InsuranceResource> getInsuranceById(@PathVariable Long id) {
        var getInsuranceByIdQuery = new GetInsuranceByIdQuery(id);
        var optionalInsurance = this.insuranceQueryService.handle(getInsuranceByIdQuery);

        if (optionalInsurance.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var insuranceResource = InsuranceResourceFromEntityAssembler.toResourceFromEntity(optionalInsurance.get());
        return ResponseEntity.ok(insuranceResource);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an Insurance Plan", description = "Updates an existing Insurance Plan identified by its ID")
    public ResponseEntity<InsuranceResource> updateInsurance(@PathVariable Long id, @RequestBody @Valid CreateInsuranceResource resource) {
        var updateInsuranceCommand = UpdateInsuranceCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var optionalInsurance = this.insuranceCommandService.handle(updateInsuranceCommand);

        if (optionalInsurance.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var insuranceResource = InsuranceResourceFromEntityAssembler.toResourceFromEntity(optionalInsurance.get());
        return ResponseEntity.ok(insuranceResource);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an Insurance Plan", description = "Deletes an Insurance Plan identified by its ID")
    public ResponseEntity<?> deleteInsurance(@PathVariable Long id) {
        this.insuranceCommandService.handle(new DeleteInsuranceCommand(id));
        return ResponseEntity.noContent().build();
    }
}