package pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.transform;

import pe.edu.upc.opennova.automovilunite.rentals.domain.model.aggregates.Rental;
import pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.resources.RentalResource;

public class RentalResourceFromEntityAssembler {
    public static RentalResource toResourceFromEntity(Rental entity) {
        return new RentalResource(
                entity.getId(),
                entity.getPublicationId().getPublicationUuid(),
                entity.getRenterId().getRenterUuid(),
                entity.getBookingDate(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getTotalCost(),
                entity.getBaseCost(),
                entity.getInsuranceCost(),
                entity.getPlatformCommission(),
                entity.getPickupMileage(),
                entity.getDropoffMileage(),
                entity.getStatus().getStringName(),
                entity.getInsuranceId().getInsuranceInternalId(), 
                entity.getPickupLocationId().getLocationUuid()
        );
    }
}