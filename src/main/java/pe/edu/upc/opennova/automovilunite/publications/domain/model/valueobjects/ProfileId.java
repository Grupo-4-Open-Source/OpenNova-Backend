package pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Embeddable
public record ProfileId(
        @NotBlank @Size(min = 1, max = 36) String profileUuid
) {
    public ProfileId {
        if (profileUuid == null || profileUuid.isBlank()) {
            throw new IllegalArgumentException("Profile UUID cannot be null or blank");
        }
    }

    public ProfileId() {
        this("");
    }
}