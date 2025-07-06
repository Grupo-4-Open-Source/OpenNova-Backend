package pe.edu.upc.opennova.automovilunite.publications.domain.services;

import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Location;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.CreateLocationCommand;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.UpdateLocationCommand;

import java.util.Optional;

public interface LocationCommandService {
    Optional<Location> handle(CreateLocationCommand command);
    Optional<Location> handle(String externalId, UpdateLocationCommand command);
    void handle(String externalId);
}