package pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Embeddable
@Getter
public class PublicationStatus {
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EPublicationStatus status;

    public PublicationStatus() {
        this.status = EPublicationStatus.PENDING;
    }

    public PublicationStatus(EPublicationStatus status) {
        this.status = status;
    }

    public String getStringName() {
        return this.status.name();
    }

    public boolean is(EPublicationStatus status) {
        return this.status.equals(status);
    }
}