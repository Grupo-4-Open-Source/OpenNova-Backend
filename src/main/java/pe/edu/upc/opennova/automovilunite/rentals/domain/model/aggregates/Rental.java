package pe.edu.upc.opennova.automovilunite.rentals.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.opennova.automovilunite.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Getter
@Entity
@Table(name = "rentals")
public class Rental extends AuditableAbstractAggregateRoot<Rental> {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "publicationUuid", column = @Column(name = "publication_id", nullable = false))
    })
    private PublicationId publicationId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "renterUuid", column = @Column(name = "renter_id", nullable = false))
    })
    private RenterId renterId;

    @Temporal(TemporalType.DATE)
    @Column(name = "booking_date", nullable = false)
    private Date bookingDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "total_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCost;

    @Column(name = "base_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal baseCost;

    @Column(name = "insurance_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal insuranceCost;

    @Column(name = "platform_commission", nullable = false, precision = 10, scale = 2)
    private BigDecimal platformCommission;

    @Column(name = "pickup_mileage", nullable = false)
    private Double pickupMileage;

    @Column(name = "dropoff_mileage", nullable = true)
    private Double dropoffMileage;

    @Embedded
    private RentalStatus status;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "insuranceInternalId", column = @Column(name = "insurance_id", nullable = false))
    })
    private InsuranceId insuranceId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "locationUuid", column = @Column(name = "pickup_location_id", nullable = false))
    })
    private LocationId pickupLocationId;

    protected Rental() {
        this.status = new RentalStatus();
        this.dropoffMileage = null;
    }

    public Rental(PublicationId publicationId, RenterId renterId, Date bookingDate, Date startDate, Date endDate,
                  BigDecimal totalCost, BigDecimal baseCost, BigDecimal insuranceCost, BigDecimal platformCommission,
                  Double pickupMileage, InsuranceId insuranceId, LocationId pickupLocationId) {
        this();

        this.publicationId = Objects.requireNonNull(publicationId, "Publication ID cannot be null");
        this.renterId = Objects.requireNonNull(renterId, "Renter ID cannot be null");
        this.bookingDate = Objects.requireNonNull(bookingDate, "Booking date cannot be null");
        this.startDate = Objects.requireNonNull(startDate, "Start date cannot be null");
        this.endDate = Objects.requireNonNull(endDate, "End date cannot be null");

        if (this.startDate.after(this.endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
        if (this.bookingDate.after(this.startDate)) {
            throw new IllegalArgumentException("Booking date cannot be after start date.");
        }

        this.totalCost = Objects.requireNonNull(totalCost, "Total cost cannot be null");
        if (this.totalCost.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Total cost must be positive.");
        }

        this.baseCost = Objects.requireNonNull(baseCost, "Base cost cannot be null");
        this.insuranceCost = Objects.requireNonNull(insuranceCost, "Insurance cost cannot be null");
        this.platformCommission = Objects.requireNonNull(platformCommission, "Platform commission cannot be null");

        this.pickupMileage = Objects.requireNonNull(pickupMileage, "Pickup mileage cannot be null");
        if (this.pickupMileage < 0) {
            throw new IllegalArgumentException("Pickup mileage cannot be negative.");
        }

        this.insuranceId = Objects.requireNonNull(insuranceId, "Insurance ID cannot be null");
        this.pickupLocationId = Objects.requireNonNull(pickupLocationId, "Pickup location ID cannot be null");
    }

    public void update(Date startDate, Date endDate, BigDecimal totalCost, BigDecimal baseCost,
                       BigDecimal insuranceCost, BigDecimal platformCommission, Double dropoffMileage, ERentalStatus newStatusEnum) {
        this.startDate = Objects.requireNonNull(startDate, "Start date cannot be null");
        this.endDate = Objects.requireNonNull(endDate, "End date cannot be null");

        if (this.startDate.after(this.endDate)) {
            throw new IllegalArgumentException("Updated start date cannot be after updated end date.");
        }

        this.totalCost = Objects.requireNonNull(totalCost, "Total cost cannot be null");
        if (this.totalCost.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Updated total cost must be positive.");
        }

        this.baseCost = Objects.requireNonNull(baseCost, "Base cost cannot be null");
        this.insuranceCost = Objects.requireNonNull(insuranceCost, "Insurance cost cannot be null");
        this.platformCommission = Objects.requireNonNull(platformCommission, "Platform commission cannot be null");

        this.dropoffMileage = dropoffMileage;

        this.status = new RentalStatus(Objects.requireNonNull(newStatusEnum, "New status cannot be null"));
    }

    public void changeStatus(ERentalStatus newStatus) {
        Objects.requireNonNull(newStatus, "New status cannot be null");
        this.status = new RentalStatus(newStatus);
    }
}