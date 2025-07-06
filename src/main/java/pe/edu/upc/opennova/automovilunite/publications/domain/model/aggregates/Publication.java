package pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects.*;
import pe.edu.upc.opennova.automovilunite.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.Date;
import java.util.Objects;

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
            @AttributeOverride(name = "vehicleId", column = @Column(name = "vehicle_id", nullable = false))
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

    public Publication() {
        this.status = new PublicationStatus();
        this.isFeatured = false;
        this.carRules = "";
    }

    public Publication(String externalId, String title, String description, RentalRate rentalRate,
                       VehicleId vehicleId, ProfileId ownerId, LocationId pickupLocationId,
                       String carRules, boolean isFeatured, Date availableFrom, Date availableUntil) {
        this();

        this.externalId = Objects.requireNonNull(externalId, "Publication external ID cannot be null");
        if (externalId.isBlank()) throw new IllegalArgumentException("Publication external ID cannot be blank");

        this.title = Objects.requireNonNull(title, "Publication title cannot be null");
        if (title.isBlank()) throw new IllegalArgumentException("Publication title cannot be blank");

        this.description = Objects.requireNonNull(description, "Publication description cannot be null");
        if (description.isBlank()) throw new IllegalArgumentException("Publication description cannot be blank");

        this.rentalRate = Objects.requireNonNull(rentalRate, "Rental rate cannot be null");
        this.vehicleId = Objects.requireNonNull(vehicleId, "Vehicle ID cannot be null");
        this.ownerId = Objects.requireNonNull(ownerId, "Owner ID cannot be null");
        this.pickupLocationId = Objects.requireNonNull(pickupLocationId, "Pickup location ID cannot be null");

        this.carRules = Objects.requireNonNull(carRules, "Car rules cannot be null");

        this.availableFrom = Objects.requireNonNull(availableFrom, "Available from date cannot be null");
        this.availableUntil = Objects.requireNonNull(availableUntil, "Available until date cannot be null");

        if (this.availableFrom.after(this.availableUntil)) {
            throw new IllegalArgumentException("Available from date cannot be after available until date.");
        }

        this.isFeatured = isFeatured;
    }

    public Publication update(String title, String description, RentalRate rentalRate,
                              LocationId pickupLocationId, String carRules, boolean isFeatured,
                              Date availableFrom, Date availableUntil) {
        this.title = Objects.requireNonNull(title, "Publication title cannot be null");
        if (title.isBlank()) throw new IllegalArgumentException("Publication title cannot be blank");

        this.description = Objects.requireNonNull(description, "Publication description cannot be null");
        if (description.isBlank()) throw new IllegalArgumentException("Publication description cannot be blank");

        this.rentalRate = Objects.requireNonNull(rentalRate, "Rental rate cannot be null");
        this.pickupLocationId = Objects.requireNonNull(pickupLocationId, "Pickup location ID cannot be null");

        this.carRules = Objects.requireNonNull(carRules, "Car rules cannot be null");

        this.availableFrom = Objects.requireNonNull(availableFrom, "Available from date cannot be null");
        this.availableUntil = Objects.requireNonNull(availableUntil, "Available until date cannot be null");

        if (this.availableFrom.after(this.availableUntil)) {
            throw new IllegalArgumentException("Available from date cannot be after available until date.");
        }

        this.isFeatured = isFeatured;

        return this;
    }

    public void changeStatus(EPublicationStatus newStatus) {
        Objects.requireNonNull(newStatus, "New status cannot be null");
        this.status = new PublicationStatus(newStatus);
    }

    public boolean isFeatured() {
        return this.isFeatured;
    }
}