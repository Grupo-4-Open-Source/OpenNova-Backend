package pe.edu.upc.opennova.automovilunite.publications.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetAllLocationsQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetLocationByExternalIdQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.services.LocationCommandService;
import pe.edu.upc.opennova.automovilunite.publications.domain.services.LocationQueryService;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources.CreateLocationResource;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources.LocationResource;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources.UpdateLocationResource;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.transform.CreateLocationCommandFromResourceAssembler;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.transform.LocationResourceFromEntityAssembler;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.transform.UpdateLocationCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/publications/locations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Locations", description = "Location Management Endpoints within Publications BC")
public class LocationController {

    private final LocationCommandService locationCommandService;
    private final LocationQueryService locationQueryService;

    public LocationController(LocationCommandService locationCommandService, LocationQueryService locationQueryService) {
        this.locationCommandService = locationCommandService;
        this.locationQueryService = locationQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new Location", description = "Creates a new Location with the provided data")
    public ResponseEntity<LocationResource> createLocation(@RequestBody CreateLocationResource resource) {
        var createLocationCommand = CreateLocationCommandFromResourceAssembler.toCommandFromResource(resource);
        return locationCommandService.handle(createLocationCommand)
                .map(location -> new ResponseEntity<>(LocationResourceFromEntityAssembler.toResourceFromEntity(location), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{locationId}")
    @Operation(summary = "Get a Location by its External ID", description = "Retrieves a Location by its unique external ID")
    public ResponseEntity<LocationResource> getLocationByExternalId(@PathVariable String locationId) {
        GetLocationByExternalIdQuery query = new GetLocationByExternalIdQuery(locationId);
        return locationQueryService.handle(query)
                .map(location -> new ResponseEntity<>(LocationResourceFromEntityAssembler.toResourceFromEntity(location), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all Locations", description = "Retrieves a list of all existing Locations")
    public ResponseEntity<List<LocationResource>> getAllLocations() {
        List<pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Location> locations = locationQueryService.handle(new GetAllLocationsQuery());
        List<LocationResource> locationResources = locations.stream()
                .map(LocationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(locationResources);
    }

    @PutMapping("/{locationId}")
    @Operation(summary = "Update a Location", description = "Updates an existing Location identified by its external ID")
    public ResponseEntity<LocationResource> updateLocation(@PathVariable String locationId, @RequestBody UpdateLocationResource resource) {
        var updateLocationCommand = UpdateLocationCommandFromResourceAssembler.toCommandFromResource(resource);
        return locationCommandService.handle(locationId, updateLocationCommand)
                .map(location -> new ResponseEntity<>(LocationResourceFromEntityAssembler.toResourceFromEntity(location), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{locationId}")
    @Operation(summary = "Delete a Location", description = "Deletes a Location identified by its external ID")
    public ResponseEntity<?> deleteLocation(@PathVariable String locationId) {
        locationCommandService.handle(locationId);
        return ResponseEntity.noContent().build();
    }
}