package pe.edu.upc.opennova.automovilunite.publications.domain.model.commands;

import pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects.RentalRate;

import java.math.BigDecimal;
import java.util.Date;

public record UpdatePublicationCommand(
        String title,
        String description,
        BigDecimal dailyPrice,
        BigDecimal weeklyPrice,
        String pickupLocationUuid,
        String carRules,
        boolean isFeatured,
        Date availableFrom,
        Date availableUntil
) {
    public RentalRate toRentalRate() {
        return new RentalRate(dailyPrice, weeklyPrice);
    }
}