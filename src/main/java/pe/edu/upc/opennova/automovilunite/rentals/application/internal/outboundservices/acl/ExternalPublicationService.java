package pe.edu.upc.opennova.automovilunite.rentals.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.acl.PublicationContextFacade;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.aggregates.Rental;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects.ERentalStatus;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects.PublicationId;
import pe.edu.upc.opennova.automovilunite.rentals.infrastructure.persistence.jpa.repositories.RentalRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Anti-Corruption Layer for the Publications Bounded Context.
 * This class translates requests from the Rentals Bounded Context
 * into calls understandable by the Publications Bounded Context,
 * using the PublicationContextFacade.
 * It acts as a Port for outgoing requests from Rentals.
 */
@Service
public class ExternalPublicationService {

    private final PublicationContextFacade publicationContextFacade;
    private final RentalRepository rentalRepository;

    public ExternalPublicationService(PublicationContextFacade publicationContextFacade, RentalRepository rentalRepository) {
        this.publicationContextFacade = publicationContextFacade;
        this.rentalRepository = rentalRepository;
    }

    public boolean existsPublicationByExternalId(String publicationExternalId) {
        return publicationContextFacade.existsPublicationByExternalId(publicationExternalId);
    }

    public Optional<BigDecimal> getPublicationDailyPrice(String publicationExternalId) {
        return publicationContextFacade.getPublicationDailyPrice(publicationExternalId);
    }

    public Optional<Double> getVehicleCurrentMileage(String publicationExternalId) {
        return publicationContextFacade.getVehicleCurrentMileage(publicationExternalId);
    }

    public Optional<String> getPublicationPickupLocationId(String publicationExternalId) {
        return publicationContextFacade.getPublicationPickupLocationId(publicationExternalId);
    }

    public boolean isPublicationAvailable(String publicationExternalId, Date startDate, Date endDate) {
        if (!publicationContextFacade.isPublicationAvailable(publicationExternalId, startDate, endDate)) {
            return false;
        }

            List<Rental> overlappingRentals = rentalRepository.findOverlappingRentalsForPublication(
                new PublicationId(publicationExternalId),
                startDate,
                endDate,
                List.of(ERentalStatus.CONFIRMED, ERentalStatus.ONGOING)
        );

        return overlappingRentals.isEmpty();
    }
}