package pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Embeddable
public record VehicleId(
        @NotBlank @Size(min = 1, max = 36) String vehicleUuid
) {
    public VehicleId {
        if (vehicleUuid == null || vehicleUuid.isBlank()) {
            throw new IllegalArgumentException("Vehicle UUID cannot be null or blank");
        }
    }

    public VehicleId() {
        this("");
    }
}