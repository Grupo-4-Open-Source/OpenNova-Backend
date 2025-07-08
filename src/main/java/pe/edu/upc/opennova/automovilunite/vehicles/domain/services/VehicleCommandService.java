package pe.edu.upc.opennova.automovilunite.vehicles.domain.services;

import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.aggregates.Vehicle;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.commands.CreateVehicleCommand;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.commands.DeleteVehicleCommand;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.commands.UpdateVehicleCommand;

import java.util.Optional;

public interface VehicleCommandService {
    Optional<Vehicle> handle(CreateVehicleCommand command);
    Optional<Vehicle> handle(UpdateVehicleCommand command);
    void handle(DeleteVehicleCommand command);
}
