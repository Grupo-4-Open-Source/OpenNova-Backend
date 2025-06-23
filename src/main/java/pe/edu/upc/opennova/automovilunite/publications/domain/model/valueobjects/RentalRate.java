package pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Embeddable
@Getter
public class RentalRate {
    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    private BigDecimal dailyPrice;

    @DecimalMin(value = "0.00", inclusive = true)
    private BigDecimal weeklyPrice;

    public RentalRate() {
        this.dailyPrice = BigDecimal.ZERO;
        this.weeklyPrice = BigDecimal.ZERO;
    }

    public RentalRate(BigDecimal dailyPrice, BigDecimal weeklyPrice) {
        this.dailyPrice = dailyPrice;
        this.weeklyPrice = weeklyPrice != null ? weeklyPrice : BigDecimal.ZERO;
    }
}