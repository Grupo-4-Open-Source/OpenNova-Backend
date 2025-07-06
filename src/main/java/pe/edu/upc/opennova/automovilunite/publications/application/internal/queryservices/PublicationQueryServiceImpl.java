package pe.edu.upc.opennova.automovilunite.publications.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.opennova.automovilunite.publications.application.internal.outboundservices.acl.ExternalVehicleService;
import pe.edu.upc.opennova.automovilunite.publications.application.internal.outboundservices.acl.dtos.ProfileDto;
import pe.edu.upc.opennova.automovilunite.publications.application.internal.outboundservices.acl.dtos.VehicleDto;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Location;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Publication;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetAllPublicationsQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetFeaturedPublicationsQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetPublicationByExternalIdQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetPublicationsByOwnerIdQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects.ProfileId;
import pe.edu.upc.opennova.automovilunite.publications.domain.services.PublicationQueryService;
import pe.edu.upc.opennova.automovilunite.publications.infrastructure.persistence.jpa.repositories.LocationRepository;
import pe.edu.upc.opennova.automovilunite.publications.infrastructure.persistence.jpa.repositories.PublicationRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublicationQueryServiceImpl implements PublicationQueryService {

    private final PublicationRepository publicationRepository;
    private final LocationRepository locationRepository;
    private final ExternalVehicleService externalVehicleService;

    public PublicationQueryServiceImpl(PublicationRepository publicationRepository,
                                       LocationRepository locationRepository,
                                       ExternalVehicleService externalVehicleService) {
        this.publicationRepository = publicationRepository;
        this.locationRepository = locationRepository;
        this.externalVehicleService = externalVehicleService;
    }

    @Override
    public List<Publication> handle(GetAllPublicationsQuery query) {
        return publicationRepository.findAll();
    }

    @Override
    public Optional<Publication> handle(GetPublicationByExternalIdQuery query) {
        if (query.externalId() == null || query.externalId().isBlank()) {
            throw new IllegalArgumentException("External ID cannot be null or blank.");
        }
        return publicationRepository.findByExternalId(query.externalId());
    }

    @Override
    public List<Publication> handle(GetPublicationsByOwnerIdQuery query) {
        if (query.ownerUuid() == null || query.ownerUuid().isBlank()) {
            throw new IllegalArgumentException("Owner UUID cannot be null or blank.");
        }
        return publicationRepository.findByOwnerId(new ProfileId(query.ownerUuid()));
    }

    @Override
    public List<Publication> handle(GetFeaturedPublicationsQuery query) {
        return publicationRepository.findByIsFeaturedTrue();
    }

    public Optional<PublicationDataDto> getPublicationData(String publicationExternalId) {
        return handle(new GetPublicationByExternalIdQuery(publicationExternalId))
                .map(this::enrichPublicationWithMinimalExternalData);
    }

    public List<PublicationDataDto> getAllPublicationsData() {
        return handle(new GetAllPublicationsQuery()).stream()
                .map(this::enrichPublicationWithMinimalExternalData)
                .collect(Collectors.toList());
    }

    public List<PublicationDataDto> getPublicationsByOwnerData(String ownerUuid) {
        return handle(new GetPublicationsByOwnerIdQuery(ownerUuid)).stream()
                .map(this::enrichPublicationWithMinimalExternalData)
                .collect(Collectors.toList());
    }

    public List<PublicationDataDto> getFeaturedPublicationsData() {
        return handle(new GetFeaturedPublicationsQuery()).stream()
                .map(this::enrichPublicationWithMinimalExternalData)
                .collect(Collectors.toList());
    }

    private PublicationDataDto enrichPublicationWithMinimalExternalData(Publication publication) {
        Optional<VehicleDto> vehicleSummaryDto = externalVehicleService.fetchVehicleSummaryById(publication.getVehicleId().vehicleId());
        ProfileDto ownerSummaryDto = new ProfileDto(publication.getOwnerId().profileUuid(), "Unknown", "Owner");

        Optional<Location> pickupLocation = locationRepository.findByExternalId(publication.getPickupLocationId().locationUuid());

        return new PublicationDataDto(
                publication,
                vehicleSummaryDto.orElse(null),
                ownerSummaryDto,
                pickupLocation.orElse(null)
        );
    }
}