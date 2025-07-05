package pe.edu.upc.opennova.automovilunite.vehicles.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Embeddable
@Getter
public class FuelType {
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false)
    private EFuelType type;

    public FuelType() {
        this.type = null;
    }

    public FuelType(EFuelType type) {
        this.type = type;
    }

    public String getStringName() {
        return this.type.name();
    }
}