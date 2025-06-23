package pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.transform;

import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Publication;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources.PublicationResource;

public class PublicationResourceFromEntityAssembler {
    public static PublicationResource toResourceFromEntity(Publication entity) {
        return new PublicationResource(
                entity.getId(),
                entity.getExternalId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getRentalRate().getDailyPrice(),
                entity.getRentalRate().getWeeklyPrice(),
                entity.getVehicleId().vehicleUuid(),
                entity.getOwnerId().profileUuid(),
                entity.getPickupLocationId().locationUuid(),
                entity.getCarRules(),
                entity.getStatus().getStringName(),
                entity.isFeatured(),
                entity.getAvailableFrom(),
                entity.getAvailableUntil(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}