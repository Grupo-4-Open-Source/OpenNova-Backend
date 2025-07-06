package pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.opennova.automovilunite.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Getter
@Entity
@Table(name = "locations")
public class Location extends AuditableAbstractAggregateRoot<Location> {

    @Column(name = "external_id", nullable = false, unique = true, updatable = false)
    private String externalId;

    @Column(name = "address_line_1", nullable = false)
    private String addressLine1;

    @Column(nullable = false)
    private String city;

    @Column(name = "state_province", nullable = false)
    private String stateProvince;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = true)
    private String instructions;

    public Location() {
    }

    public Location(String externalId, String addressLine1, String city, String stateProvince, String zipCode, Double latitude, Double longitude, String instructions) {
        this.externalId = externalId;
        this.addressLine1 = addressLine1;
        this.city = city;
        this.stateProvince = stateProvince;
        this.zipCode = zipCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.instructions = instructions;
    }

    public Location update(String addressLine1, String city, String stateProvince, String zipCode, Double latitude, Double longitude, String instructions) {
        this.addressLine1 = addressLine1;
        this.city = city;
        this.stateProvince = stateProvince;
        this.zipCode = zipCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.instructions = instructions;
        return this;
    }
}