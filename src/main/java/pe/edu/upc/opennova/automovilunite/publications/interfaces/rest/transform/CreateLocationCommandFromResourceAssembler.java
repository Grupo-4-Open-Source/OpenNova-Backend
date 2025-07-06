package pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.transform;

import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.CreateLocationCommand;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources.CreateLocationResource;

public class CreateLocationCommandFromResourceAssembler {
    public static CreateLocationCommand toCommandFromResource(CreateLocationResource resource) {
        return new CreateLocationCommand(
                resource.id(),
                resource.addressLine1(),
                resource.city(),
                resource.stateProvince(),
                resource.zipCode(),
                resource.latitude(),
                resource.longitude(),
                resource.instructions()
        );
    }
}