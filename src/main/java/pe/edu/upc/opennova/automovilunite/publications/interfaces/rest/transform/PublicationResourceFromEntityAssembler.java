package pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.transform;

import pe.edu.upc.opennova.automovilunite.publications.application.internal.queryservices.PublicationDataDto;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources.PublicationResource;

public class PublicationResourceFromEntityAssembler {
    public static PublicationResource toResourceFromEntity(PublicationDataDto entity) {
        String vehicleMake = entity.vehicleSummaryDto() != null ? entity.vehicleSummaryDto().make() : null;
        String vehicleModel = entity.vehicleSummaryDto() != null ? entity.vehicleSummaryDto().model() : null;

        String ownerFullName = String.format("Owner: %s (Details not available)", entity.publication().getOwnerId().profileUuid());
        if (entity.ownerSummaryDto() != null) {
            ownerFullName = String.format("%s %s", entity.ownerSummaryDto().firstName(), entity.ownerSummaryDto().lastName());
        }

        String pickupLocationAddressSummary = null;
        if (entity.pickupLocation() != null) {
            pickupLocationAddressSummary = String.format("%s, %s, %s",
                    entity.pickupLocation().getAddressLine1(),
                    entity.pickupLocation().getCity(),
                    entity.pickupLocation().getStateProvince());
        }

        return new PublicationResource(
                entity.publication().getId(),
                entity.publication().getExternalId(),
                entity.publication().getTitle(),
                entity.publication().getDescription(),
                entity.publication().getRentalRate().getDailyPrice(),
                entity.publication().getRentalRate().getWeeklyPrice(),
                entity.publication().getVehicleId().vehicleId(),
                entity.publication().getOwnerId().profileUuid(),
                entity.publication().getPickupLocationId().locationUuid(),
                entity.publication().getCarRules(),
                entity.publication().getStatus().getStringName(),
                entity.publication().isFeatured(),
                entity.publication().getAvailableFrom(),
                entity.publication().getAvailableUntil(),
                entity.publication().getCreatedAt(),
                entity.publication().getUpdatedAt(),
                vehicleMake,
                vehicleModel,
                ownerFullName,
                pickupLocationAddressSummary
        );
    }
}