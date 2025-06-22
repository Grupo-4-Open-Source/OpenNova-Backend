package pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects.*;
import pe.edu.upc.opennova.automovilunite.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.Date;

@Getter
@Entity
@Table(name = "publications")
public class Publication extends AuditableAbstractAggregateRoot<Publication> {

    @Column(name = "external_id", nullable = false, unique = true, updatable = false)
    private String externalId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Embedded
    private RentalRate rentalRate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "vehicleUuid", column = @Column(name = "vehicle_id", nullable = false))
    })
    private VehicleId vehicleId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "profileUuid", column = @Column(name = "owner_id", nullable = false))
    })
    private ProfileId ownerId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "locationUuid", column = @Column(name = "pickup_location_id", nullable = false))
    })
    private LocationId pickupLocationId;

    @Column(name = "car_rules", nullable = false)
    private String carRules;

    @Embedded
    private PublicationStatus status;

    @Column(name = "is_featured", nullable = false)
    private boolean isFeatured;

    @Temporal(TemporalType.DATE)
    @Column(name = "available_from", nullable = false)
    private Date availableFrom;

    @Temporal(TemporalType.DATE)
    @Column(name = "available_until", nullable = false)
    private Date availableUntil;

    // Constructors
    public Publication() {
        this.status = new PublicationStatus();
        this.isFeatured = false;
        this.carRules = "";
    }

    public Publication(String externalId, String title, String description, RentalRate rentalRate,
                       VehicleId vehicleId, ProfileId ownerId, LocationId pickupLocationId,
                       String carRules, boolean isFeatured, Date availableFrom, Date availableUntil) {
        this();
        this.externalId = externalId;
        this.title = title;
        this.description = description;
        this.rentalRate = rentalRate;
        this.vehicleId = vehicleId;
        this.ownerId = ownerId;
        this.pickupLocationId = pickupLocationId;
        this.carRules = carRules;
        this.isFeatured = isFeatured;
        this.availableFrom = availableFrom;
        this.availableUntil = availableUntil;
    }


    public Publication update(String title, String description, RentalRate rentalRate,
                              LocationId pickupLocationId, String carRules, boolean isFeatured,
                              Date availableFrom, Date availableUntil) {
        this.title = title;
        this.description = description;
        this.rentalRate = rentalRate;
        this.pickupLocationId = pickupLocationId;
        this.carRules = carRules;
        this.isFeatured = isFeatured;
        this.availableFrom = availableFrom;
        this.availableUntil = availableUntil;
        return this;
    }

    public void activate() {
        this.status = new PublicationStatus(EPublicationStatus.ACTIVE);
    }

    public void deactivate() {
        this.status = new PublicationStatus(EPublicationStatus.INACTIVE);
    }

    public void expire() {
        this.status = new PublicationStatus(EPublicationStatus.EXPIRED);
    }

    public void cancel() {
        this.status = new PublicationStatus(EPublicationStatus.CANCELLED);
    }

    public boolean isFeatured() {
        return this.isFeatured;
    }
}