package pe.edu.upc.opennova.automovilunite.publications.interfaces.rest;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Publication;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.DeletePublicationCommand;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.UpdatePublicationCommand;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetAllPublicationsQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetPublicationsByIdQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.services.PublicationCommandService;
import pe.edu.upc.opennova.automovilunite.publications.domain.services.PublicationQueryService;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources.CreatePublicationResource;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources.PublicationResource;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.transform.CreatePublicationCommandFromResourceAssembler;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.transform.PublicationResourceFromEntityAssembler;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.transform.UpdatePublicationCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/publication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Publications", description = "Publications Management Endpoints")

public class PublicationController {
    private final PublicationCommandService publicationCommandService;
    private final PublicationQueryService publicationQueryService;

    public PublicationController(PublicationCommandService publicationCommandService, PublicationQueryService publicationQueryService) {
        this.publicationCommandService = publicationCommandService;
        this.publicationQueryService = publicationQueryService;
    }

    @PostMapping
    public ResponseEntity<PublicationResource> createPublication(@RequestBody CreatePublicationResource resource) {
        var createPublicationCommand = CreatePublicationCommandFromResourceAssembler.toCommandFromResource(resource);
        var publicationId = this.publicationCommandService.handle(createPublicationCommand);

        if (publicationId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getPublicationByIdQuery =new GetPublicationsByIdQuery(publicationId);
        var optionalPublication = this.publicationQueryService.handle(getPublicationByIdQuery);

        var publicationResource = PublicationResourceFromEntityAssembler.toResourceFromEntity(optionalPublication.get());
        return new ResponseEntity<>(publicationResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PublicationResource>> getAllPublications() {
        var getAllPublicationsQuery = new GetAllPublicationsQuery();
        var publications = this.publicationQueryService.handle(getAllPublicationsQuery);

        var publicationResources = publications.stream()
                .map(PublicationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(publicationResources);
    }

    @GetMapping("/{publicationId}")
    public ResponseEntity<PublicationResource> getPublicationById(@PathVariable Long publicationId) {
        var getPublicationByIdQuery = new GetPublicationsByIdQuery(publicationId);
        var optionalPublication = this.publicationQueryService.handle(getPublicationByIdQuery);

        if (optionalPublication.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var publicationResource = PublicationResourceFromEntityAssembler.toResourceFromEntity(optionalPublication.get());
        return ResponseEntity.ok(publicationResource);
    }

    @DeleteMapping("/{publicationId}")
    public ResponseEntity<?> deletePublication(@PathVariable Long publicationId) {
        var deletePublicationCommand = new DeletePublicationCommand(publicationId);
        this.publicationCommandService.handle(deletePublicationCommand);
        return ResponseEntity.badRequest().build();
        }

    @PutMapping("/{publicationId}")
    public ResponseEntity<PublicationResource> updateSaving(@PathVariable Long publicationId, @RequestBody PublicationResource resource) {
        var updatePublicationCommand = UpdatePublicationCommandFromResourceAssembler.toCommandFromResource(publicationId, resource);
        var optionalPublication = this.publicationCommandService.handle(updatePublicationCommand);

        if (optionalPublication.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var publicationResource = PublicationResourceFromEntityAssembler.toResourceFromEntity(optionalPublication.get());
        return ResponseEntity.ok(publicationResource);
    }
}
