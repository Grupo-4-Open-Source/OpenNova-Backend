package pe.edu.upc.opennova.automovilunite.vehicles.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.aggregates.Vehicle;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.commands.CreateVehicleCommand;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.commands.DeleteVehicleCommand;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.commands.UpdateVehicleCommand;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.valueobjects.FuelType;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.valueobjects.GalleryImageUrls;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.valueobjects.OwnerId;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.valueobjects.VehicleType;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.services.VehicleCommandService;
import pe.edu.upc.opennova.automovilunite.vehicles.infrastructure.persistance.jpa.repositories.VehicleRepository;

import java.util.Optional;

@Service
public class VehicleCommandServiceImpl implements VehicleCommandService {

    private final VehicleRepository vehicleRepository;

    public VehicleCommandServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Optional<Vehicle> handle(CreateVehicleCommand command) {
        if (vehicleRepository.existsByLicensePlate(command.licensePlate())) {
            throw new IllegalArgumentException("Vehicle with license plate " + command.licensePlate() + " already exists.");
        }

        Vehicle vehicle = new Vehicle(
                new OwnerId(command.ownerId()),
                command.make(),
                command.model(),
                command.year(),
                command.color(),
                command.licensePlate(),
                command.currentMileage(),
                new VehicleType(command.vehicleType()),
                new FuelType(command.fuelType()),
                command.passengerCapacity(),
                command.description(),
                command.mainImageUrl(),
                new GalleryImageUrls(command.galleryImageUrls())
        );
        return Optional.of(vehicleRepository.save(vehicle));
    }

    @Override
    public Optional<Vehicle> handle(UpdateVehicleCommand command) {
        var vehicleId = command.vehicleId();
        return vehicleRepository.findById(vehicleId).map(vehicleToUpdate -> {
            vehicleToUpdate.updateVehicle(command);
            return Optional.of(vehicleRepository.save(vehicleToUpdate));
        }).orElseThrow(() -> new IllegalArgumentException("Vehicle with id " + vehicleId + " does not exist"));
    }

    @Override
    public void handle(DeleteVehicleCommand command) {
        var vehicleId = command.vehicleId();
        if (!vehicleRepository.existsById(vehicleId)) {
            throw new IllegalArgumentException("Vehicle with id " + vehicleId + " does not exist");
        }
        try {
            this.vehicleRepository.deleteById(vehicleId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting vehicle: " + e.getMessage());
        }
    }
}