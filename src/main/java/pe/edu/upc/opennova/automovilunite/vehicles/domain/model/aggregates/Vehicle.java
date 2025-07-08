package pe.edu.upc.opennova.automovilunite.vehicles.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import pe.edu.upc.opennova.automovilunite.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.commands.CreateVehicleCommand;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.commands.UpdateVehicleCommand;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.valueobjects.FuelType;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.valueobjects.GalleryImageUrls;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.valueobjects.OwnerId;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.valueobjects.VehicleType;

import java.util.List;

@Getter
@Entity
@Table(name = "vehicles")
public class Vehicle extends AuditableAbstractAggregateRoot<Vehicle> {

    @Embedded
    @NotNull
    private OwnerId ownerId;

    @NotBlank
    @Column(name = "make", nullable = false)
    private String make;

    @NotBlank
    @Column(name = "model", nullable = false)
    private String model;

    @NotNull
    @Column(name = "year", nullable = false)
    @Min(1900)
    private Integer year;

    @NotBlank
    @Column(name = "color", nullable = false)
    private String color;

    @NotBlank
    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    @NotNull
    @Column(name = "current_mileage", nullable = false)
    @Min(0)
    private Integer currentMileage;

    @Embedded
    @NotNull
    private VehicleType vehicleType;

    @Embedded
    @NotNull
    private FuelType fuelType;

    @NotNull
    @Column(name = "passenger_capacity", nullable = false)
    @Min(1)
    private Integer passengerCapacity;

    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;

    @NotBlank
    @Column(name = "main_image_url", nullable = false)
    @Pattern(regexp = "^(https?|ftp):\\/\\/[^\\s\\/$.?#].[^\\s]*$", message = "Invalid URL format for main image")
    private String mainImageUrl;

    @Embedded
    private GalleryImageUrls galleryImageUrls;

    public Vehicle() {
        this.ownerId = new OwnerId();
        this.make = null;
        this.model = null;
        this.year = null;
        this.color = null;
        this.licensePlate = null;
        this.currentMileage = null;
        this.vehicleType = new VehicleType();
        this.fuelType = new FuelType();
        this.passengerCapacity = null;
        this.description = null;
        this.mainImageUrl = null;
        this.galleryImageUrls = new GalleryImageUrls();
    }

    public Vehicle(OwnerId ownerId, String make, String model, Integer year, String color, String licensePlate,
                   Integer currentMileage, VehicleType vehicleType, FuelType fuelType, Integer passengerCapacity,
                   String description, String mainImageUrl, GalleryImageUrls galleryImageUrls) {
        this.ownerId = ownerId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.licensePlate = licensePlate;
        this.currentMileage = currentMileage;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
        this.passengerCapacity = passengerCapacity;
        this.description = description;
        this.mainImageUrl = mainImageUrl;
        this.galleryImageUrls = galleryImageUrls;
    }

    public Vehicle(CreateVehicleCommand command) {
        this.ownerId = new OwnerId(command.ownerId());
        this.make = command.make();
        this.model = command.model();
        this.year = command.year();
        this.color = command.color();
        this.licensePlate = command.licensePlate();
        this.currentMileage = command.currentMileage();
        this.vehicleType = new VehicleType(command.vehicleType());
        this.fuelType = new FuelType(command.fuelType());
        this.passengerCapacity = command.passengerCapacity();
        this.description = command.description();
        this.mainImageUrl = command.mainImageUrl();
        this.galleryImageUrls = new GalleryImageUrls(command.galleryImageUrls());
    }

    public Vehicle updateVehicle(UpdateVehicleCommand command) {
        this.make = command.make();
        this.model = command.model();
        this.year = command.year();
        this.color = command.color();
        this.currentMileage = command.currentMileage();
        this.vehicleType = new VehicleType(command.vehicleType());
        this.fuelType = new FuelType(command.fuelType());
        this.passengerCapacity = command.passengerCapacity();
        this.description = command.description();
        this.mainImageUrl = command.mainImageUrl();
        this.galleryImageUrls = new GalleryImageUrls(command.galleryImageUrls());
        return this;
    }
}