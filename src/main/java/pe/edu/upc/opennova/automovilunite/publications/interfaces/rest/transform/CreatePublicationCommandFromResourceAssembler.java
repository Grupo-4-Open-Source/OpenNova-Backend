package pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.transform;

import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.CreatePublicationCommand;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources.CreatePublicationResource;

public class CreatePublicationCommandFromResourceAssembler {
    public static CreatePublicationCommand toCommandFromResource (CreatePublicationResource resource) {
        return new CreatePublicationCommand(
                resource.model(),
                resource.brand(),
                resource.year(),
                resource.description(),
                resource.image(),
                resource.price()
        );
    }
}
