package pe.edu.upc.opennova.automovilunite.publications.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Publication;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.CreatePublicationCommand;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.UpdatePublicationCommand;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.UpdatePublicationStatusCommand;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects.EPublicationStatus;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects.LocationId;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects.ProfileId;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects.VehicleId;
import pe.edu.upc.opennova.automovilunite.publications.domain.services.PublicationCommandService;
import pe.edu.upc.opennova.automovilunite.publications.infrastructure.persistence.jpa.repositories.PublicationRepository;

import java.util.Optional;

@Service
public class PublicationCommandServiceImpl implements PublicationCommandService {

    private final PublicationRepository publicationRepository;

    public PublicationCommandServiceImpl(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    @Override
    public Optional<Publication> handle(CreatePublicationCommand command) {
        if (command.externalId() == null || command.externalId().isBlank()) {
            throw new IllegalArgumentException("Publication externalId cannot be null or blank");
        }
        if (publicationRepository.existsByExternalId(command.externalId())) {
            throw new IllegalArgumentException("Publication with externalId " + command.externalId() + " already exists");
        }
        if (command.title() == null || command.title().isBlank()) {
            throw new IllegalArgumentException("Publication title cannot be null or blank.");
        }
        if (command.description() == null || command.description().isBlank()) {
            throw new IllegalArgumentException("Publication description cannot be null or blank.");
        }
        if (command.dailyPrice() == null || command.dailyPrice().doubleValue() < 0.01) {
            throw new IllegalArgumentException("Publication daily price must be at least 0.01.");
        }
        if (command.vehicleUuid() == null || command.vehicleUuid().isBlank()) {
            throw new IllegalArgumentException("Vehicle ID cannot be null or blank.");
        }
        if (command.ownerUuid() == null || command.ownerUuid().isBlank()) {
            throw new IllegalArgumentException("Owner ID cannot be null or blank.");
        }
        if (command.pickupLocationUuid() == null || command.pickupLocationUuid().isBlank()) {
            throw new IllegalArgumentException("Pickup location ID cannot be null or blank.");
        }
        if (command.availableFrom() == null || command.availableUntil() == null) {
            throw new IllegalArgumentException("Available from and until dates cannot be null.");
        }
        if (command.availableFrom().after(command.availableUntil())) {
            throw new IllegalArgumentException("Available from date cannot be after available until date.");
        }


        Publication publication = new Publication(
                command.externalId(),
                command.title(),
                command.description(),
                command.toRentalRate(),
                new VehicleId(command.vehicleUuid()),
                new ProfileId(command.ownerUuid()),
                new LocationId(command.pickupLocationUuid()),
                command.carRules(),
                command.isFeatured(),
                command.availableFrom(),
                command.availableUntil()
        );
        return Optional.of(publicationRepository.save(publication));
    }

    @Override
    public Optional<Publication> handle(String externalId, UpdatePublicationCommand command) {
        return publicationRepository.findByExternalId(externalId).map(publication -> {
            if (command.title() == null || command.title().isBlank()) {
                throw new IllegalArgumentException("Publication title cannot be null or blank.");
            }
            if (command.description() == null || command.description().isBlank()) {
                throw new IllegalArgumentException("Publication description cannot be null or blank.");
            }
            if (command.dailyPrice() == null || command.dailyPrice().doubleValue() < 0.01) {
                throw new IllegalArgumentException("Publication daily price must be at least 0.01.");
            }
            if (command.pickupLocationUuid() == null || command.pickupLocationUuid().isBlank()) {
                throw new IllegalArgumentException("Pickup location ID cannot be null or blank.");
            }
            if (command.availableFrom() == null || command.availableUntil() == null) {
                throw new IllegalArgumentException("Available from and until dates cannot be null.");
            }
            if (command.availableFrom().after(command.availableUntil())) {
                throw new IllegalArgumentException("Available from date cannot be after available until date.");
            }

            publication.update(
                    command.title(),
                    command.description(),
                    command.toRentalRate(),
                    new LocationId(command.pickupLocationUuid()),
                    command.carRules(),
                    command.isFeatured(),
                    command.availableFrom(),
                    command.availableUntil()
            );
            return Optional.of(publicationRepository.save(publication));
        }).orElseThrow(() -> new IllegalArgumentException("Publication with externalId " + externalId + " not found"));
    }

    @Override
    public Optional<Publication> handle(String externalId, UpdatePublicationStatusCommand command) {
        return publicationRepository.findByExternalId(externalId).map(publication -> {
            if (command.statusName() == null || command.statusName().isBlank()) {
                throw new IllegalArgumentException("Status name cannot be null or blank.");
            }
            try {
                EPublicationStatus newStatus = EPublicationStatus.valueOf(command.statusName().toUpperCase());
                switch (newStatus) {
                    case ACTIVE: publication.activate(); break;
                    case INACTIVE: publication.deactivate(); break;
                    case EXPIRED: publication.expire(); break;
                    case CANCELLED: publication.cancel(); break;
                    case PENDING:
                        throw new IllegalArgumentException("Cannot set status to PENDING via update command.");
                }
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status name: " + command.statusName());
            }
            return Optional.of(publicationRepository.save(publication));
        }).orElseThrow(() -> new IllegalArgumentException("Publication with externalId " + externalId + " not found"));
    }

    @Override
    @Transactional
    public void handle(String externalId) {
        if (!publicationRepository.existsByExternalId(externalId)) {
            throw new IllegalArgumentException("Publication with externalId " + externalId + " not found");
        }
        publicationRepository.deleteByExternalId(externalId);
    }
}