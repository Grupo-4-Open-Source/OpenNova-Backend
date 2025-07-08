package pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.resources;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public record CreateRentalResource(
        @NotBlank(message = "Publication ID cannot be blank")
        String publicationId,
        @NotBlank(message = "Renter ID cannot be blank")
        String renterId,
        @NotNull(message = "Booking date cannot be null")
        @FutureOrPresent(message = "Booking date must be today or in the future")
        Date bookingDate,
        @NotNull(message = "Start date cannot be null")
        @FutureOrPresent(message = "Start date must be today or in the future")
        Date startDate,
        @NotNull(message = "End date cannot be null")
        Date endDate,
        @NotNull(message = "Total cost cannot be null")
        @DecimalMin(value = "0.01", message = "Total cost must be positive")
        BigDecimal totalCost,
        @NotNull(message = "Base cost cannot be null")
        @DecimalMin(value = "0.00", message = "Base cost cannot be negative")
        BigDecimal baseCost,
        @NotNull(message = "Insurance cost cannot be null")
        @DecimalMin(value = "0.00", message = "Insurance cost cannot be negative")
        BigDecimal insuranceCost,
        @NotNull(message = "Platform commission cannot be null")
        @DecimalMin(value = "0.00", message = "Platform commission cannot be negative")
        BigDecimal platformCommission,
        @NotNull(message = "Pickup mileage cannot be null")
        @DecimalMin(value = "0.0", inclusive = true, message = "Pickup mileage cannot be negative")
        Double pickupMileage,
        @NotNull(message = "Insurance ID cannot be null")
        Long insuranceId,
        @NotBlank(message = "Pickup location ID cannot be blank")
        String pickupLocationId
) {
}