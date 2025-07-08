package pe.edu.upc.opennova.automovilunite.publications.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Location;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.CreateLocationCommand;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.UpdateLocationCommand;
import pe.edu.upc.opennova.automovilunite.publications.domain.services.LocationCommandService;
import pe.edu.upc.opennova.automovilunite.publications.infrastructure.persistence.jpa.repositories.LocationRepository;

import java.util.Optional;

@Service
public class LocationCommandServiceImpl implements LocationCommandService {

    private final LocationRepository locationRepository;

    public LocationCommandServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Optional<Location> handle(CreateLocationCommand command) {
        if (command.externalId() == null || command.externalId().isBlank()) {
            throw new IllegalArgumentException("Location externalId cannot be null or blank");
        }
        if (locationRepository.existsByExternalId(command.externalId())) {
            throw new IllegalArgumentException("Location with externalId " + command.externalId() + " already exists");
        }
        if (command.addressLine1() == null || command.addressLine1().isBlank()) {
            throw new IllegalArgumentException("Address line 1 cannot be null or blank.");
        }
        if (command.city() == null || command.city().isBlank()) {
            throw new IllegalArgumentException("City cannot be null or blank.");
        }

        Location location = new Location(
                command.externalId(),
                command.addressLine1(),
                command.city(),
                command.stateProvince(),
                command.zipCode(),
                command.latitude(),
                command.longitude(),
                command.instructions()
        );
        return Optional.of(locationRepository.save(location));
    }

    @Override
    public Optional<Location> handle(String externalId, UpdateLocationCommand command) {
        return locationRepository.findByExternalId(externalId).map(location -> {
            if (command.addressLine1() == null || command.addressLine1().isBlank()) {
                throw new IllegalArgumentException("Address line 1 cannot be null or blank.");
            }
            if (command.city() == null || command.city().isBlank()) {
                throw new IllegalArgumentException("City cannot be null or blank.");
            }

            location.update(
                    command.addressLine1(),
                    command.city(),
                    command.stateProvince(),
                    command.zipCode(),
                    command.latitude(),
                    command.longitude(),
                    command.instructions()
            );
            return Optional.of(locationRepository.save(location));
        }).orElseThrow(() -> new IllegalArgumentException("Location with externalId " + externalId + " not found"));
    }

    @Override
    @Transactional
    public void handle(String externalId) {
        if (!locationRepository.existsByExternalId(externalId)) {
            throw new IllegalArgumentException("Location with externalId " + externalId + " not found");
        }
        locationRepository.deleteByExternalId(externalId);
    }
}