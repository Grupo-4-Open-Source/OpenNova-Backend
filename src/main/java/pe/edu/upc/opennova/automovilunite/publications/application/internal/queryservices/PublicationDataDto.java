package pe.edu.upc.opennova.automovilunite.publications.application.internal.queryservices;

import pe.edu.upc.opennova.automovilunite.publications.application.internal.outboundservices.acl.dtos.ProfileDto;
import pe.edu.upc.opennova.automovilunite.publications.application.internal.outboundservices.acl.dtos.VehicleDto;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Location;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Publication;

public record PublicationDataDto(
        Publication publication,
        VehicleDto vehicleSummaryDto,
        ProfileDto ownerSummaryDto,
        Location pickupLocation
) {
}