package pe.edu.upc.opennova.automovilunite.publications.domain.services;

import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Publication;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.CreatePublicationCommand;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.UpdatePublicationCommand;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.UpdatePublicationStatusCommand;

import java.util.Optional;

public interface PublicationCommandService {
    Optional<Publication> handle(CreatePublicationCommand command);
    Optional<Publication> handle(String externalId, UpdatePublicationCommand command);
    Optional<Publication> handle(String externalId, UpdatePublicationStatusCommand command);
    void handle(String externalId); // For delete
}