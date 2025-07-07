package pe.edu.upc.opennova.automovilunite.rentals.domain.services;

import pe.edu.upc.opennova.automovilunite.rentals.domain.model.aggregates.Insurance;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands.CreateInsuranceCommand;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands.UpdateInsuranceCommand;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands.DeleteInsuranceCommand;

import java.util.Optional;

public interface InsuranceCommandService {
    Long handle(CreateInsuranceCommand command);
    Optional<Insurance> handle(UpdateInsuranceCommand command);
    void handle(DeleteInsuranceCommand command);
}