package pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.Date;

public record UpdatePublicationResource(
        String title,
        String description,
        BigDecimal dailyPrice,
        BigDecimal weeklyPrice,
        String pickupLocationId,
        String carRules,
        boolean isFeatured,
        Date availableFrom,
        Date availableUntil
) {
}