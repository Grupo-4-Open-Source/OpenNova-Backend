package pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.transform;

import lombok.extern.slf4j.Slf4j;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.UpdatePublicationCommand;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources.PublicationResource;

@Slf4j
public class UpdatePublicationCommandFromResourceAssembler {
    public static UpdatePublicationCommand toCommandFromResource(Long publicationId, PublicationResource resource) {
        return new UpdatePublicationCommand(
                publicationId,
                resource.model(),
                resource.brand(),
                resource.year(),
                resource.description(),
                resource.image(),
                resource.price()
        );
    }
}
