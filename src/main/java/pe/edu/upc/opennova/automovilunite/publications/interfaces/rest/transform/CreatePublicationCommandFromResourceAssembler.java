package pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.transform;

import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.CreatePublicationCommand;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources.CreatePublicationResource;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects.RentalRate;


public class CreatePublicationCommandFromResourceAssembler {
    public static CreatePublicationCommand toCommandFromResource(CreatePublicationResource resource) {
        return new CreatePublicationCommand(
                resource.id(),
                resource.title(),
                resource.description(),
                resource.dailyPrice(),
                resource.weeklyPrice(),
                resource.vehicleId(),
                resource.ownerId(),
                resource.pickupLocationId(),
                resource.carRules(),
                resource.isFeatured(),
                resource.availableFrom(),
                resource.availableUntil()
        );
    }
}