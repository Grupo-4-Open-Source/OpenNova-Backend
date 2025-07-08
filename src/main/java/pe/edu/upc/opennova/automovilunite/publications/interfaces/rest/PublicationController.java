package pe.edu.upc.opennova.automovilunite.publications.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.opennova.automovilunite.publications.application.internal.queryservices.PublicationQueryServiceImpl;
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
    private final PublicationQueryServiceImpl publicationQueryService;

    public PublicationController(PublicationCommandService publicationCommandService, PublicationQueryService publicationQueryService) {
        this.publicationCommandService = publicationCommandService;
        this.publicationQueryService = (PublicationQueryServiceImpl) publicationQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new Publication", description = "Creates a new Publication with the provided data")
    public ResponseEntity<PublicationResource> createPublication(@RequestBody CreatePublicationResource resource) {
        var createPublicationCommand = CreatePublicationCommandFromResourceAssembler.toCommandFromResource(resource);
        return publicationCommandService.handle(createPublicationCommand)
                .map(publication -> {
                    return publicationQueryService.getPublicationData(publication.getExternalId())
                            .map(PublicationResourceFromEntityAssembler::toResourceFromEntity)
                            .map(res -> new ResponseEntity<>(res, HttpStatus.CREATED))
                            .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                })
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{publicationId}")
    @Operation(summary = "Get a Publication by its External ID", description = "Retrieves a Publication by its unique external ID")
    public ResponseEntity<PublicationResource> getPublicationByExternalId(@PathVariable String publicationId) {
        return publicationQueryService.getPublicationData(publicationId)
                .map(PublicationResourceFromEntityAssembler::toResourceFromEntity)
                .map(publication -> new ResponseEntity<>(publication, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all Publications", description = "Retrieves a list of all existing Publications")
    public ResponseEntity<List<PublicationResource>> getAllPublications() {
        List<PublicationResource> publicationResources = publicationQueryService.getAllPublicationsData().stream()
                .map(PublicationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(publicationResources);
    }

    @GetMapping("/owner/{ownerId}")
    @Operation(summary = "Get Publications by Owner ID", description = "Retrieves a list of Publications by a specific Owner ID")
    public ResponseEntity<List<PublicationResource>> getPublicationsByOwnerId(@PathVariable String ownerId) {
        List<PublicationResource> publicationResources = publicationQueryService.getPublicationsByOwnerData(ownerId).stream()
                .map(PublicationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(publicationResources);
    }

    @GetMapping("/featured")
    @Operation(summary = "Get Featured Publications", description = "Retrieves a list of Publications marked as featured")
    public ResponseEntity<List<PublicationResource>> getFeaturedPublications() {
        List<PublicationResource> publicationResources = publicationQueryService.getFeaturedPublicationsData().stream()
                .map(PublicationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(publicationResources);
    }

    @PutMapping("/{publicationId}")
    @Operation(summary = "Update a Publication", description = "Updates an existing Publication identified by its external ID")
    public ResponseEntity<PublicationResource> updatePublication(@PathVariable String publicationId, @RequestBody UpdatePublicationResource resource) {
        var updatePublicationCommand = UpdatePublicationCommandFromResourceAssembler.toCommandFromResource(resource);
        return publicationCommandService.handle(publicationId, updatePublicationCommand)
                .map(publication -> {
                    return publicationQueryService.getPublicationData(publication.getExternalId())
                            .map(PublicationResourceFromEntityAssembler::toResourceFromEntity)
                            .map(res -> new ResponseEntity<>(res, HttpStatus.OK))
                            .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{publicationId}/status")
    @Operation(summary = "Update Publication Status", description = "Updates the status of an existing Publication")
    public ResponseEntity<PublicationResource> updatePublicationStatus(@PathVariable String publicationId, @RequestBody UpdatePublicationStatusResource resource) {
        var updatePublicationStatusCommand = UpdatePublicationStatusCommandFromResourceAssembler.toCommandFromResource(resource);
        return publicationCommandService.handle(publicationId, updatePublicationStatusCommand)
                .map(publication -> {
                    return publicationQueryService.getPublicationData(publication.getExternalId())
                            .map(PublicationResourceFromEntityAssembler::toResourceFromEntity)
                            .map(res -> new ResponseEntity<>(res, HttpStatus.OK))
                            .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{publicationId}")
    @Operation(summary = "Delete a Publication", description = "Deletes a Publication identified by its external ID")
    public ResponseEntity<?> deletePublication(@PathVariable String publicationId) {
        publicationCommandService.handle(publicationId);
        return ResponseEntity.noContent().build();
    }
}