package pe.edu.upc.opennova.automovilunite.publications.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Location;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetAllLocationsQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetLocationByExternalIdQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.services.LocationQueryService;
import pe.edu.upc.opennova.automovilunite.publications.infrastructure.persistence.jpa.repositories.LocationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LocationQueryServiceImpl implements LocationQueryService {

    private final LocationRepository locationRepository;

    public LocationQueryServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> handle(GetAllLocationsQuery query) {
        return locationRepository.findAll();
    }

    @Override
    public Optional<Location> handle(GetLocationByExternalIdQuery query) {
        if (query.externalId() == null || query.externalId().isBlank()) {
            throw new IllegalArgumentException("External ID cannot be null or blank.");
        }
        return locationRepository.findByExternalId(query.externalId());
    }
}