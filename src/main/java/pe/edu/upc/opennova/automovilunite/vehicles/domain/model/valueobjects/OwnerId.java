// src/main/java/pe/edu/upc/opennova/automovilunite/vehicles/domain/model/valueobjects/OwnerId.java
package pe.edu.upc.opennova.automovilunite.vehicles.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Embeddable
@Getter
@EqualsAndHashCode
public class OwnerId {
    @NotBlank
    @Column(name = "owner_id", nullable = false)
    private String value;

    public OwnerId() {
        this.value = null;
    }

    public OwnerId(String value) {
        this.value = value;
    }
}