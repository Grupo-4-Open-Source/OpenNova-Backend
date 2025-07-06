package pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public record VehicleId(Long vehicleId) {
    public VehicleId {
        Objects.requireNonNull(vehicleId, "Vehicle ID cannot be null");
        if (vehicleId <= 0) {
            throw new IllegalArgumentException("Vehicle ID must be positive");
        }
    }

    public VehicleId() {
        this(0L);
    }
}