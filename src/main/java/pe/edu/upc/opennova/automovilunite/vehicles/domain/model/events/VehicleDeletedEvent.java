package pe.edu.upc.opennova.automovilunite.vehicles.domain.model.events;

import java.time.Instant;
import java.util.Objects;

public record VehicleDeletedEvent(
        Long vehicleId,
        Instant occurredOn
) {
    public VehicleDeletedEvent {
        Objects.requireNonNull(vehicleId, "Vehicle ID cannot be null");
        if (vehicleId <= 0) {
            throw new IllegalArgumentException("Vehicle ID must be positive");
        }
        Objects.requireNonNull(occurredOn, "Occurred on cannot be null");
    }

    public VehicleDeletedEvent(Long vehicleId) {
        this(vehicleId, Instant.now());
    }
}