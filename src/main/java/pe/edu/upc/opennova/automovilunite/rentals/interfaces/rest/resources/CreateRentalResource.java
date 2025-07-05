package pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.Date;

public record CreateRentalResource(
        Long publicationId,
        Long renterId,
        Date bookingDate,
        Date startDate,
        Date endDate,
        BigDecimal totalCost,
        BigDecimal baseCost,
        BigDecimal insuranceCost,
        BigDecimal platformCommission,
        Double pickupMileage,
        Long insuranceId,
        Long pickupLocationId
) {
}