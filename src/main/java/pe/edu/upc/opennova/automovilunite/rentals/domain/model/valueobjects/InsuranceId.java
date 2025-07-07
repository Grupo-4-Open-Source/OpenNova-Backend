package pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Embeddable
@Getter
@EqualsAndHashCode
public class InsuranceId {

    private Long insuranceInternalId;

    protected InsuranceId() {}

    public InsuranceId(Long insuranceInternalId) {
        this.insuranceInternalId = Objects.requireNonNull(insuranceInternalId, "Insurance internal ID cannot be null");
        if (insuranceInternalId <= 0) throw new IllegalArgumentException("Insurance internal ID must be positive");
    }
}