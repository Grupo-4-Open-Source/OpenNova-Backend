package pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.resources;

import java.math.BigDecimal;

public record InsuranceResource(
        Long id,
        String planName,
        String description,
        BigDecimal dailyCost,
        BigDecimal deductible,
        String coverageDetails
) {
}