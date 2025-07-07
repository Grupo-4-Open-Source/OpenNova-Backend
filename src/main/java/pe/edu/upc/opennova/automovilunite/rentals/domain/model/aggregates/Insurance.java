package pe.edu.upc.opennova.automovilunite.rentals.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.opennova.automovilunite.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Entity
@Table(name = "insurances")
public class Insurance extends AuditableAbstractAggregateRoot<Insurance> {

    @Column(nullable = false, length = 100)
    private String planName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal dailyCost;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal deductible;

    @Column(columnDefinition = "TEXT")
    private String coverageDetails;

    protected Insurance() {}

    public Insurance(String planName, String description, BigDecimal dailyCost, BigDecimal deductible, String coverageDetails) {
        this.planName = Objects.requireNonNull(planName, "Plan name cannot be null");
        if (planName.isBlank()) throw new IllegalArgumentException("Plan name cannot be blank");

        this.description = Objects.requireNonNull(description, "Description cannot be null");
        this.dailyCost = Objects.requireNonNull(dailyCost, "Daily cost cannot be null");
        if (dailyCost.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Daily cost cannot be negative");

        this.deductible = Objects.requireNonNull(deductible, "Deductible cannot be null");
        if (deductible.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Deductible cannot be negative");

        this.coverageDetails = Objects.requireNonNull(coverageDetails, "Coverage details cannot be null");
    }

    public void update(String planName, String description, BigDecimal dailyCost, BigDecimal deductible, String coverageDetails) {
        this.planName = Objects.requireNonNull(planName, "Plan name cannot be null");
        if (planName.isBlank()) throw new IllegalArgumentException("Plan name cannot be blank");

        this.description = Objects.requireNonNull(description, "Description cannot be null");
        this.dailyCost = Objects.requireNonNull(dailyCost, "Daily cost cannot be null");
        if (dailyCost.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Daily cost cannot be negative");

        this.deductible = Objects.requireNonNull(deductible, "Deductible cannot be null");
        if (deductible.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Deductible cannot be negative");

        this.coverageDetails = Objects.requireNonNull(coverageDetails, "Coverage details cannot be null");
    }
}