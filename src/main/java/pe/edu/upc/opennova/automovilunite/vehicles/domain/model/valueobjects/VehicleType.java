package pe.edu.upc.opennova.automovilunite.vehicles.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Embeddable
@Getter
public class VehicleType {
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false)
    private EVehicleType type;

    public VehicleType() {
        this.type = null;
    }

    public VehicleType(EVehicleType type) {
        this.type = type;
    }

    public String getStringName() {
        return this.type.name();
    }
}