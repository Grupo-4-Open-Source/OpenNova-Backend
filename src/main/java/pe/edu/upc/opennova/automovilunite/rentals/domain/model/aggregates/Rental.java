package pe.edu.upc.opennova.automovilunite.rentals.domain.model.aggregates;

import jakarta.persistence.*;
import pe.edu.upc.opennova.automovilunite.shared.domain.model.aggregates.AuditableAbstractAggregateRoot; // Usamos TU clase base existente
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects.ERentalStatus;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "rentals")
public class Rental extends AuditableAbstractAggregateRoot<Rental> {

    @Column(nullable = false)
    private Long publicationId;

    @Column(nullable = false)
    private Long renterId;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date bookingDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private BigDecimal totalCost;

    @Column(nullable = false)
    private BigDecimal baseCost;

    @Column(nullable = false)
    private BigDecimal insuranceCost;

    @Column(nullable = false)
    private BigDecimal platformCommission;

    @Column(nullable = false)
    private Double pickupMileage;

    @Column(nullable = true)
    private Double dropoffMileage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ERentalStatus status;

    @Column(nullable = false)
    private Long insuranceId;

    @Column(nullable = false)
    private Long pickupLocationId;

    protected Rental() {}

    public Rental(Long publicationId, Long renterId, Date bookingDate, Date startDate, Date endDate,
                  BigDecimal totalCost, BigDecimal baseCost, BigDecimal insuranceCost, BigDecimal platformCommission,
                  Double pickupMileage, Long insuranceId, Long pickupLocationId) {
        this.publicationId = publicationId;
        this.renterId = renterId;
        this.bookingDate = bookingDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
        this.baseCost = baseCost;
        this.insuranceCost = insuranceCost;
        this.platformCommission = platformCommission;
        this.pickupMileage = pickupMileage;
        this.dropoffMileage = null;
        this.status = ERentalStatus.PENDING_OWNER_APPROVAL;
        this.insuranceId = insuranceId;
        this.pickupLocationId = pickupLocationId;
    }

    public void update(Date startDate, Date endDate, BigDecimal totalCost, BigDecimal baseCost,
                       BigDecimal insuranceCost, BigDecimal platformCommission, Double dropoffMileage, String status) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
        this.baseCost = baseCost;
        this.insuranceCost = insuranceCost;
        this.platformCommission = platformCommission;
        this.dropoffMileage = dropoffMileage;
        ERentalStatus newStatus = ERentalStatus.fromString(status)
                .orElseThrow(() -> new IllegalArgumentException("Invalid status: " + status));
        this.status = newStatus;
    }

    public Long getPublicationId() { return publicationId; }
    public Long getRenterId() { return renterId; }
    public Date getBookingDate() { return bookingDate; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public BigDecimal getTotalCost() { return totalCost; }
    public BigDecimal getBaseCost() { return baseCost; }
    public BigDecimal getInsuranceCost() { return insuranceCost; }
    public BigDecimal getPlatformCommission() { return platformCommission; }
    public Double getPickupMileage() { return pickupMileage; }
    public Double getDropoffMileage() { return dropoffMileage; }
    public ERentalStatus getStatus() { return status; }
    public Long getInsuranceId() { return insuranceId; }
    public Long getPickupLocationId() { return pickupLocationId; }
}