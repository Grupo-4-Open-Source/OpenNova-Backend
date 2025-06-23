package pe.edu.upc.opennova.automovilunite.vehicles.application.internal.commandservices;


import org.springframework.stereotype.Service;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.aggregates.Vehicle;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.commands.CreateVehicleCommand;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.commands.DeleteVehicleCommand;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.commands.UpdateVehicleCommand;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.services.VehicleCommandService;
import pe.edu.upc.opennova.automovilunite.vehicles.infrastructure.persistance.jpa.repositories.VehicleRepository;
import java.util.Optional;

@Service
public class VehicleCommandServiceImpl implements VehicleCommandService {

    private final VehicleRepository vehicleRepository;

    public VehicleCommandServiceImpl(VehicleRepository vehicleRepository) {this.vehicleRepository = vehicleRepository;}

    @Override
    public Long handle(CreateVehicleCommand command) {
        Vehicle vehicle = new Vehicle(
                command.model(),
                command.brand(),
                command.year(),
                command.description(),
                command.image(),
                command.price()
        );
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return savedVehicle.getId();
    }

    @Override
    public Optional<Vehicle> handle(UpdateVehicleCommand command) {
        var publicationId = command.publicationId();
        Vehicle vehicleToUpdate = vehicleRepository.findById(publicationId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle with id " + publicationId + " does not exist"));

        vehicleToUpdate.updateVehicle(
                command.model(),
                command.brand(),
                command.year(),
                command.description(),
                command.image(),
                command.price()
        );

        return Optional.of(vehicleRepository.save(vehicleToUpdate));
    }

    @Override
    public void handle(DeleteVehicleCommand command) {
        var publicationId = command.publicationId();
        if (!vehicleRepository.existsById(publicationId)) {
            throw new IllegalArgumentException("Vehicle with id " + publicationId + " does not exist");
        }
        try {
            this.vehicleRepository.deleteById(publicationId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting saving: " + e.getMessage());
        }
    }
}
