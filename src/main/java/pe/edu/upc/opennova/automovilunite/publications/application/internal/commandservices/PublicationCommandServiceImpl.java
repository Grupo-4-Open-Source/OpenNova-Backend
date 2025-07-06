package pe.edu.upc.opennova.automovilunite.publications.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.opennova.automovilunite.publications.application.internal.outboundservices.acl.ExternalVehicleService;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Location;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Publication;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.CreatePublicationCommand;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.UpdatePublicationCommand;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.UpdatePublicationStatusCommand;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects.EPublicationStatus;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects.LocationId;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects.ProfileId;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects.RentalRate;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects.VehicleId;
import pe.edu.upc.opennova.automovilunite.publications.domain.services.PublicationCommandService;
import pe.edu.upc.opennova.automovilunite.publications.infrastructure.persistence.jpa.repositories.LocationRepository;
import pe.edu.upc.opennova.automovilunite.publications.infrastructure.persistence.jpa.repositories.PublicationRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class PublicationCommandServiceImpl implements PublicationCommandService {

    private final PublicationRepository publicationRepository;
    private final LocationRepository locationRepository;
    private final ExternalVehicleService externalVehicleService;

    public PublicationCommandServiceImpl(PublicationRepository publicationRepository,
                                         LocationRepository locationRepository,
                                         ExternalVehicleService externalVehicleService) {
        this.publicationRepository = publicationRepository;
        this.locationRepository = locationRepository;
        this.externalVehicleService = externalVehicleService;
    }

    @Override
    @Transactional
    public Optional<Publication> handle(CreatePublicationCommand command) {
        String newExternalId = UUID.randomUUID().toString();
        if (publicationRepository.existsByExternalId(newExternalId)) {
            throw new IllegalArgumentException("Generated publication externalId " + newExternalId + " already exists. Please retry.");
        }

        if (!externalVehicleService.existsVehicleById(command.vehicleId())) {
            throw new IllegalArgumentException("Vehicle with ID " + command.vehicleId() + " does not exist.");
        }

        Optional<Location> pickupLocation = locationRepository.findByExternalId(command.pickupLocationId());
        if (pickupLocation.isEmpty()) {
            throw new IllegalArgumentException("Pickup location with ID " + command.pickupLocationId() + " does not exist.");
        }

        try {
            var rentalRate = new RentalRate(command.dailyPrice(), command.weeklyPrice());
            var ownerId = new ProfileId(command.ownerId());
            var vehicleId = new VehicleId(command.vehicleId());
            var pickupLocationId = new LocationId(command.pickupLocationId());

            var publication = new Publication(
                    newExternalId,
                    command.title(),
                    command.description(),
                    rentalRate,
                    vehicleId,
                    ownerId,
                    pickupLocationId,
                    command.carRules(),
                    false,
                    command.availableFrom(),
                    command.availableUntil()
            );
            return Optional.of(publicationRepository.save(publication));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Failed to create publication due to invalid data: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Optional<Publication> handle(String externalId, UpdatePublicationCommand command) {
        Optional<Publication> publicationOptional = publicationRepository.findByExternalId(externalId);
        if (publicationOptional.isEmpty()) {
            throw new IllegalArgumentException("Publication with externalId " + externalId + " not found");
        }

        Publication publication = publicationOptional.get();

        if (!publication.getPickupLocationId().locationUuid().equals(command.pickupLocationId())) {
            Optional<Location> newPickupLocation = locationRepository.findByExternalId(command.pickupLocationId());
            if (newPickupLocation.isEmpty()) {
                throw new IllegalArgumentException("New pickup location with ID " + command.pickupLocationId() + " does not exist.");
            }
        }

        try {
            publication.update(
                    command.title(),
                    command.description(),
                    new RentalRate(command.dailyPrice(), command.weeklyPrice()),
                    new LocationId(command.pickupLocationId()),
                    command.carRules(),
                    command.isFeatured(),
                    command.availableFrom(),
                    command.availableUntil()
            );
            return Optional.of(publicationRepository.save(publication));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Failed to update publication due to invalid data: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Optional<Publication> handle(String externalId, UpdatePublicationStatusCommand command) {
        Optional<Publication> publicationOptional = publicationRepository.findByExternalId(externalId);
        if (publicationOptional.isEmpty()) {
            throw new IllegalArgumentException("Publication with externalId " + externalId + " not found");
        }

        Publication publication = publicationOptional.get();

        if (command.statusName() == null || command.statusName().isBlank()) {
            throw new IllegalArgumentException("Status name cannot be null or blank.");
        }
        try {
            EPublicationStatus newStatus = EPublicationStatus.valueOf(command.statusName().toUpperCase());
            publication.changeStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status name: " + command.statusName() + ". Error: " + e.getMessage(), e);
        }
        return Optional.of(publicationRepository.save(publication));
    }

    @Override
    @Transactional
    public void handle(String externalId) {
        Optional<Publication> publicationOptional = publicationRepository.findByExternalId(externalId);
        if (publicationOptional.isEmpty()) {
            throw new IllegalArgumentException("Publication with externalId " + externalId + " not found");
        }
        publicationRepository.delete(publicationOptional.get());
    }
}