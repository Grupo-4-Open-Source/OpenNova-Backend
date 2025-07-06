package pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.transform;

import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.aggregates.Vehicle;
import pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.resources.VehicleResource;

public class VehicleResourceFromEntityAssembler {
    public static VehicleResource toResourceFromEntity(Vehicle entity) {
        return new VehicleResource(
                entity.getId(),
                entity.getOwnerId().getValue(),
                entity.getMake(),
                entity.getModel(),
                entity.getYear(),
                entity.getColor(),
                entity.getLicensePlate(),
                entity.getCurrentMileage(),
                entity.getVehicleType().getStringName(),
                entity.getFuelType().getStringName(),
                entity.getPassengerCapacity(),
                entity.getDescription(),
                entity.getMainImageUrl(),
                entity.getGalleryImageUrls().getUrls()
        );
    }
}