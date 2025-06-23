package pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.Date;

public record PublicationResource(
        Long id,
        String externalId,
        String title,
        String description,
        BigDecimal dailyPrice,
        BigDecimal weeklyPrice,
        String vehicleId,
        String ownerId,
        String pickupLocationId,
        String carRules,
        String status,
        boolean isFeatured,
        Date availableFrom,
        Date availableUntil,
        Date createdAt,
        Date updatedAt
) {
}