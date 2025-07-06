package pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.transform;

import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.UpdateLocationCommand;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources.UpdateLocationResource;

public class UpdateLocationCommandFromResourceAssembler {
    public static UpdateLocationCommand toCommandFromResource(UpdateLocationResource resource) {
        return new UpdateLocationCommand(
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