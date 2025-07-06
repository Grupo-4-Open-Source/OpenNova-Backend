package pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources;

import java.util.Date;

public record LocationResource(
        Long id,
        String externalId,
        String addressLine1,
        String city,
        String stateProvince,
        String zipCode,
        Double latitude,
        Double longitude,
        String instructions,
        Date createdAt,
        Date updatedAt
) {
}