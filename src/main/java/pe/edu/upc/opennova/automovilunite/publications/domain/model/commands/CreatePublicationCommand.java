package pe.edu.upc.opennova.automovilunite.publications.domain.model.commands;

import pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects.RentalRate;

import java.math.BigDecimal;
import java.util.Date;

public record CreatePublicationCommand(
        String externalId,
        String title,
        String description,
        BigDecimal dailyPrice,
        BigDecimal weeklyPrice,
        Long vehicleId,
        String ownerId,
        String pickupLocationId,
        String carRules,
        boolean isFeatured,
        Date availableFrom,
        Date availableUntil
) {
    public RentalRate toRentalRate() {
        return new RentalRate(dailyPrice, weeklyPrice);
    }
}