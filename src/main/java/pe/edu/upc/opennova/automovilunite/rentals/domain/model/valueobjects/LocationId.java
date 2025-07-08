package pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Embeddable
@Getter
@EqualsAndHashCode
public class LocationId {
    private String locationUuid;

    protected LocationId() {}

    public LocationId(String locationUuid) {
        this.locationUuid = Objects.requireNonNull(locationUuid, "Location UUID cannot be null");
        if (locationUuid.isBlank()) throw new IllegalArgumentException("Location UUID cannot be blank");
    }
}