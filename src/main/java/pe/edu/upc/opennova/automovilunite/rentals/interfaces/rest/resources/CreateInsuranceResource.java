package pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.resources;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateInsuranceResource(
        @NotBlank(message = "Plan name cannot be blank")
        String planName,
        @NotBlank(message = "Description cannot be blank")
        String description,
        @NotNull(message = "Daily cost cannot be null")
        @DecimalMin(value = "0.00", message = "Daily cost cannot be negative")
        BigDecimal dailyCost,
        @NotNull(message = "Deductible cannot be null")
        @DecimalMin(value = "0.00", message = "Deductible cannot be negative")
        BigDecimal deductible,
        @NotBlank(message = "Coverage details cannot be blank")
        String coverageDetails
) {
}