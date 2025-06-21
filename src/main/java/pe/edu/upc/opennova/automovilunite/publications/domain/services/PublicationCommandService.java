package pe.edu.upc.opennova.automovilunite.publications.domain.services;

import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Publication;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.CreatePublicationCommand;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.DeletePublicationCommand;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.UpdatePublicationCommand;

import java.util.Optional;

public interface PublicationCommandService {
    Long handle(CreatePublicationCommand command);
    Optional<Publication> handle(UpdatePublicationCommand command);
    void handle(DeletePublicationCommand command);
}
