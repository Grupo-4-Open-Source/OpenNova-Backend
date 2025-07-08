package pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
@EqualsAndHashCode
public class PublicationId {

    private String publicationUuid;

    protected PublicationId() {
        this.publicationUuid = UUID.randomUUID().toString();
    }

    public PublicationId(String publicationUuid) {
        this.publicationUuid = Objects.requireNonNull(publicationUuid, "Publication UUID cannot be null");
        if (publicationUuid.isBlank()) throw new IllegalArgumentException("Publication UUID cannot be blank");
    }
}