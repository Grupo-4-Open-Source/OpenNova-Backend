package pe.edu.upc.opennova.automovilunite.publications.application.internal.commandservices;


import org.springframework.stereotype.Service;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Publication;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.CreatePublicationCommand;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.DeletePublicationCommand;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.UpdatePublicationCommand;
import pe.edu.upc.opennova.automovilunite.publications.domain.services.PublicationCommandService;
import pe.edu.upc.opennova.automovilunite.publications.infrastructure.persistance.jpa.repositories.PublicationRepository;
import java.util.Optional;

@Service
public class PublicationCommandServiceImpl implements PublicationCommandService {

    private final PublicationRepository publicationRepository;

    public PublicationCommandServiceImpl(PublicationRepository publicationRepository) {this.publicationRepository = publicationRepository;}

    @Override
    public Long handle(CreatePublicationCommand command) {
        Publication publication = new Publication(
                command.model(),
                command.brand(),
                command.year(),
                command.description(),
                command.image(),
                command.price()
        );
        Publication savedPublication = publicationRepository.save(publication);
        return savedPublication.getId();
    }

    @Override
    public Optional<Publication> handle(UpdatePublicationCommand command) {
        var publicationId = command.publicationId();
        Publication publicationToUpdate = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new IllegalArgumentException("Publication with id " + publicationId + " does not exist"));

        publicationToUpdate.updatePublication(
                command.model(),
                command.brand(),
                command.year(),
                command.description(),
                command.image(),
                command.price()
        );

        return Optional.of(publicationRepository.save(publicationToUpdate));
    }

    @Override
    public void handle(DeletePublicationCommand command) {
        var publicationId = command.publicationId();
        if (!publicationRepository.existsById(publicationId)) {
            throw new IllegalArgumentException("Publication with id " + publicationId + " does not exist");
        }
        try {
            this.publicationRepository.deleteById(publicationId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting saving: " + e.getMessage());
        }
    }
}
