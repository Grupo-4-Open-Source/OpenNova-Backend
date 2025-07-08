package pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands;

import java.math.BigDecimal;

public record UpdateInsuranceCommand(
        Long insuranceId,
        String planName,
        String description,
        BigDecimal dailyCost,
        BigDecimal deductible,
        String coverageDetails
) {
}