package pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.transform;

import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.aggregates.Vehicle;
import pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.resources.VehicleResource;

public class VehicleResourceFromEntityAssembler {
    public static VehicleResource toResourceFromEntity(Vehicle entity) {
        return new VehicleResource(
                entity.getId(),
                entity.getModel(),
                entity.getBrand(),
                entity.getYear(),
                entity.getDescription(),
                entity.getImage(),
                entity.getPrice()
                );
    }
}
