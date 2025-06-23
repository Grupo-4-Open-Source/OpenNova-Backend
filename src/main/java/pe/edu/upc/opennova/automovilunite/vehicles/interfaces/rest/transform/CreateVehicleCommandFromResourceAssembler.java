package pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.transform;

import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.commands.CreateVehicleCommand;
import pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.resources.CreateVehicleResource;

public class CreateVehicleCommandFromResourceAssembler {
    public static CreateVehicleCommand toCommandFromResource (CreateVehicleResource resource) {
        return new CreateVehicleCommand(
                resource.model(),
                resource.brand(),
                resource.year(),
                resource.description(),
                resource.image(),
                resource.price()
        );
    }
}
