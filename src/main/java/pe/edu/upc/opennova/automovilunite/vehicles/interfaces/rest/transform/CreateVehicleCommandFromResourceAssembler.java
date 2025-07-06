package pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.transform;

import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.commands.CreateVehicleCommand;
import pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.resources.CreateVehicleResource;

public class CreateVehicleCommandFromResourceAssembler {
    public static CreateVehicleCommand toCommandFromResource(CreateVehicleResource resource) {
        return new CreateVehicleCommand(
                resource.ownerId(),
                resource.make(),
                resource.model(),
                resource.year(),
                resource.color(),
                resource.licensePlate(),
                resource.currentMileage(),
                resource.vehicleType(),
                resource.fuelType(),
                resource.passengerCapacity(),
                resource.description(),
                resource.mainImageUrl(),
                resource.galleryImageUrls()
        );
    }
}