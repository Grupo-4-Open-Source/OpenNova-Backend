package pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.transform;

import lombok.extern.slf4j.Slf4j;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.commands.UpdateVehicleCommand;
import pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.resources.VehicleResource;

@Slf4j
public class UpdateVehicleCommandFromResourceAssembler {
    public static UpdateVehicleCommand toCommandFromResource(Long publicationId, VehicleResource resource) {
        return new UpdateVehicleCommand(
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
