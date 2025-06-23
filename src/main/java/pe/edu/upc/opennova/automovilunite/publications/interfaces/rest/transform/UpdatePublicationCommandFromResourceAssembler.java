package pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.transform;

import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.UpdatePublicationCommand;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources.UpdatePublicationResource;

public class UpdatePublicationCommandFromResourceAssembler {
    public static UpdatePublicationCommand toCommandFromResource(UpdatePublicationResource resource) {
        return new UpdatePublicationCommand(
                resource.title(),
                resource.description(),
                resource.dailyPrice(),
                resource.weeklyPrice(),
                resource.pickupLocationId(),
                resource.carRules(),
                resource.isFeatured(),
                resource.availableFrom(),
                resource.availableUntil()
        );
    }
}