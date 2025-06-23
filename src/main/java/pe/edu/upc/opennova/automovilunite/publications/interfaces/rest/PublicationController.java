package pe.edu.upc.opennova.automovilunite.publications.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetAllPublicationsQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetFeaturedPublicationsQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetPublicationByExternalIdQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetPublicationsByOwnerIdQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.services.PublicationCommandService;
import pe.edu.upc.opennova.automovilunite.publications.domain.services.PublicationQueryService;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources.CreatePublicationResource;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources.PublicationResource;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources.UpdatePublicationResource;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources.UpdatePublicationStatusResource;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.transform.CreatePublicationCommandFromResourceAssembler;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.transform.PublicationResourceFromEntityAssembler;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.transform.UpdatePublicationCommandFromResourceAssembler;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.transform.UpdatePublicationStatusCommandFromResourceAssembler;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/publications", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Publications", description = "Publication Management Endpoints")
public class PublicationController {

    private final PublicationCommandService publicationCommandService;
    private final PublicationQueryService publicationQueryService;

    public PublicationController(PublicationCommandService publicationCommandService, PublicationQueryService publicationQueryService) {
        this.publicationCommandService = publicationCommandService;
        this.publicationQueryService = publicationQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new Publication", description = "Creates a new Publication with the provided data")
    public ResponseEntity<PublicationResource> createPublication(@RequestBody CreatePublicationResource resource) {
        var createPublicationCommand = CreatePublicationCommandFromResourceAssembler.toCommandFromResource(resource);
        return publicationCommandService.handle(createPublicationCommand)
                .map(publication -> new ResponseEntity<>(PublicationResourceFromEntityAssembler.toResourceFromEntity(publication), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{publicationId}")
    @Operation(summary = "Get a Publication by its External ID", description = "Retrieves a Publication by its unique external ID")
    public ResponseEntity<PublicationResource> getPublicationByExternalId(@PathVariable String publicationId) {
        GetPublicationByExternalIdQuery query = new GetPublicationByExternalIdQuery(publicationId);
        return publicationQueryService.handle(query)
                .map(publication -> new ResponseEntity<>(PublicationResourceFromEntityAssembler.toResourceFromEntity(publication), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all Publications", description = "Retrieves a list of all existing Publications")
    public ResponseEntity<List<PublicationResource>> getAllPublications() {
        List<pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Publication> publications = publicationQueryService.handle(new GetAllPublicationsQuery());
        List<PublicationResource> publicationResources = publications.stream()
                .map(PublicationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(publicationResources);
    }

    @GetMapping("/owner/{ownerId}")
    @Operation(summary = "Get Publications by Owner ID", description = "Retrieves a list of Publications by a specific Owner ID")
    public ResponseEntity<List<PublicationResource>> getPublicationsByOwnerId(@PathVariable String ownerId) {
        List<pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Publication> publications = publicationQueryService.handle(new GetPublicationsByOwnerIdQuery(ownerId));
        List<PublicationResource> publicationResources = publications.stream()
                .map(PublicationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(publicationResources);
    }

    @GetMapping("/featured")
    @Operation(summary = "Get Featured Publications", description = "Retrieves a list of Publications marked as featured")
    public ResponseEntity<List<PublicationResource>> getFeaturedPublications() {
        List<pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Publication> publications = publicationQueryService.handle(new GetFeaturedPublicationsQuery());
        List<PublicationResource> publicationResources = publications.stream()
                .map(PublicationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(publicationResources);
    }

    @PutMapping("/{publicationId}")
    @Operation(summary = "Update a Publication", description = "Updates an existing Publication identified by its external ID")
    public ResponseEntity<PublicationResource> updatePublication(@PathVariable String publicationId, @RequestBody UpdatePublicationResource resource) {
        var updatePublicationCommand = UpdatePublicationCommandFromResourceAssembler.toCommandFromResource(resource);
        return publicationCommandService.handle(publicationId, updatePublicationCommand)
                .map(publication -> new ResponseEntity<>(PublicationResourceFromEntityAssembler.toResourceFromEntity(publication), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{publicationId}/status")
    @Operation(summary = "Update Publication Status", description = "Updates the status of an existing Publication")
    public ResponseEntity<PublicationResource> updatePublicationStatus(@PathVariable String publicationId, @RequestBody UpdatePublicationStatusResource resource) {
        var updatePublicationStatusCommand = UpdatePublicationStatusCommandFromResourceAssembler.toCommandFromResource(resource);
        return publicationCommandService.handle(publicationId, updatePublicationStatusCommand)
                .map(publication -> new ResponseEntity<>(PublicationResourceFromEntityAssembler.toResourceFromEntity(publication), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{publicationId}")
    @Operation(summary = "Delete a Publication", description = "Deletes a Publication identified by its external ID")
    public ResponseEntity<?> deletePublication(@PathVariable String publicationId) {
        publicationCommandService.handle(publicationId);
        return ResponseEntity.noContent().build();
    }
}