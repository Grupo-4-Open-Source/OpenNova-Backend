package pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Embeddable
@Getter
@EqualsAndHashCode
public class RentalStatus {

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ERentalStatus status;

    public RentalStatus() {
        this.status = ERentalStatus.PENDING_OWNER_APPROVAL;
    }

    public RentalStatus(ERentalStatus status) {
        this.status = Objects.requireNonNull(status, "Rental status cannot be null");
    }

    public String getStringName() {
        return this.status.getStringName();
    }

    public boolean isPendingOwnerApproval() {
        return this.status == ERentalStatus.PENDING_OWNER_APPROVAL;
    }

    public boolean isConfirmed() {
        return this.status == ERentalStatus.CONFIRMED;
    }

    public boolean isCancelledByRenter() {
        return this.status == ERentalStatus.CANCELLED_BY_RENTER;
    }

    public boolean isCancelledByOwner() {
        return this.status == ERentalStatus.CANCELLED_BY_OWNER;
    }

    public boolean isCompleted() {
        return this.status == ERentalStatus.COMPLETED;
    }

    public boolean isOnGoing() {
        return this.status == ERentalStatus.ONGOING;
    }
}