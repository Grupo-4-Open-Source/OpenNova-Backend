package pe.edu.upc.opennova.automovilunite.publications.domain.services;

import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Location;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetAllLocationsQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetLocationByExternalIdQuery;

import java.util.List;
import java.util.Optional;

public interface LocationQueryService {
    List<Location> handle(GetAllLocationsQuery query);
    Optional<Location> handle(GetLocationByExternalIdQuery query);
}