package pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.transform;

import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.commands.UpdateVehicleCommand;
import pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.resources.VehicleResource;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.valueobjects.EVehicleType;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.valueobjects.EFuelType;

public class UpdateVehicleCommandFromResourceAssembler {
    public static UpdateVehicleCommand toCommandFromResource(Long vehicleId, VehicleResource resource) {
        return new UpdateVehicleCommand(
                vehicleId,
                resource.make(),
                resource.model(),
                resource.year(),
                resource.color(),
                resource.currentMileage(),
                EVehicleType.valueOf(resource.vehicleType()),
                EFuelType.valueOf(resource.fuelType()),
                resource.passengerCapacity(),
                resource.description(),
                resource.mainImageUrl(),
                resource.galleryImageUrls()
        );
    }
}