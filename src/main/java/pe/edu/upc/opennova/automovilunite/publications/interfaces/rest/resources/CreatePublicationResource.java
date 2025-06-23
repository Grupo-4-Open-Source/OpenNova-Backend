package pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.Date;

public record CreatePublicationResource(
        String id,
        String title,
        String description,
        BigDecimal dailyPrice,
        BigDecimal weeklyPrice,
        String vehicleId,
        String ownerId,
        String pickupLocationId,
        String carRules,
        boolean isFeatured,
        Date availableFrom,
        Date availableUntil
) {
}