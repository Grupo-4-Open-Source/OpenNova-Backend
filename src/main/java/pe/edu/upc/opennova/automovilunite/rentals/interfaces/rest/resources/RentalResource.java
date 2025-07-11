package pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.Date;

public record RentalResource(
        Long id,
        String publicationId,
        String renterId,
        Date bookingDate,
        Date startDate,
        Date endDate,
        BigDecimal totalCost,
        BigDecimal baseCost,
        BigDecimal insuranceCost,
        BigDecimal platformCommission,
        Double pickupMileage,
        Double dropoffMileage,
        String status,
        Long insuranceId,
        String pickupLocationId
) {
}