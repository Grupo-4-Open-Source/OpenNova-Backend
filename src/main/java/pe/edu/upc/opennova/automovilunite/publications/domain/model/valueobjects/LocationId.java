package pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Embeddable
public record LocationId(
        @NotBlank @Size(min = 1, max = 36) String locationUuid
) {
    public LocationId {
        if (locationUuid == null || locationUuid.isBlank()) {
            throw new IllegalArgumentException("Location UUID cannot be null or blank");
        }
    }

    public LocationId() {
        this("");
    }
}