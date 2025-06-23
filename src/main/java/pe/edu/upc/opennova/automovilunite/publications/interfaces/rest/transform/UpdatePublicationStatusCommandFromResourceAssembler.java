package pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.transform;

import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.UpdatePublicationStatusCommand;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources.UpdatePublicationStatusResource;

public class UpdatePublicationStatusCommandFromResourceAssembler {
    public static UpdatePublicationStatusCommand toCommandFromResource(UpdatePublicationStatusResource resource) {
        return new UpdatePublicationStatusCommand(resource.status());
    }
}