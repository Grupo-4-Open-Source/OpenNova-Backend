package pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Embeddable
@Getter
@EqualsAndHashCode
public class RenterId {

    private String renterUuid;

    protected RenterId() {}

    public RenterId(String renterUuid) {
        this.renterUuid = Objects.requireNonNull(renterUuid, "Renter UUID cannot be null");
        if (renterUuid.isBlank()) throw new IllegalArgumentException("Renter UUID cannot be blank");
    }
}