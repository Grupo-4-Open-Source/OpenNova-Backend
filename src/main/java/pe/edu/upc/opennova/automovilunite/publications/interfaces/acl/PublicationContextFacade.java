package pe.edu.upc.opennova.automovilunite.publications.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Publication;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetPublicationByExternalIdQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.services.PublicationQueryService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class PublicationContextFacade {

    private final PublicationQueryService publicationQueryService;

    public PublicationContextFacade(PublicationQueryService publicationQueryService) {
        this.publicationQueryService = publicationQueryService;
    }

    public boolean existsPublicationByExternalId(String publicationExternalId) {
        return publicationQueryService.handle(new GetPublicationByExternalIdQuery(publicationExternalId)).isPresent();
    }

    public Optional<BigDecimal> getPublicationDailyPrice(String publicationExternalId) {
        return publicationQueryService.handle(new GetPublicationByExternalIdQuery(publicationExternalId))
                .map(publication -> publication.getRentalRate().getDailyPrice());
    }

    public Optional<Double> getVehicleCurrentMileage(String publicationExternalId) {
        return publicationQueryService.handle(new GetPublicationByExternalIdQuery(publicationExternalId))
                .map(publication -> {
                    return 50000.0;
                });
    }

    public Optional<String> getPublicationPickupLocationId(String publicationExternalId) {
        return publicationQueryService.handle(new GetPublicationByExternalIdQuery(publicationExternalId))
                .map(publication -> publication.getPickupLocationId().locationUuid());
    }

    public boolean isPublicationAvailable(String publicationExternalId, Date startDate, Date endDate) {
        Optional<Publication> publicationOptional = publicationQueryService.handle(new GetPublicationByExternalIdQuery(publicationExternalId));
        if (publicationOptional.isEmpty()) {
            return false;
        }
        Publication publication = publicationOptional.get();

        return !startDate.before(publication.getAvailableFrom()) && !endDate.after(publication.getAvailableUntil());
    }
}