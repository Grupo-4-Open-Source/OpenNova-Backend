package pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.transform;

import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Location;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources.LocationResource;

public class LocationResourceFromEntityAssembler {
    public static LocationResource toResourceFromEntity(Location entity) {
        return new LocationResource(
                entity.getId(),
                entity.getExternalId(),
                entity.getAddressLine1(),
                entity.getCity(),
                entity.getStateProvince(),
                entity.getZipCode(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getInstructions(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}