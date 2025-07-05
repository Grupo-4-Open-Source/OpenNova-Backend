package pe.edu.upc.opennova.automovilunite.rentals.domain.services;

import pe.edu.upc.opennova.automovilunite.rentals.domain.model.aggregates.Rental;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands.CreateRentalCommand;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands.DeleteRentalCommand;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands.UpdateRentalCommand;

import java.util.Optional;

public interface RentalCommandService {
    Long handle(CreateRentalCommand command);
    Optional<Rental> handle(UpdateRentalCommand command);
    void handle(DeleteRentalCommand command);
}