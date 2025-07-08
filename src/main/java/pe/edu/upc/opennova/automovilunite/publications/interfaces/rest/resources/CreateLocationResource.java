package pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources;

public record CreateLocationResource(
        String id,
        String addressLine1,
        String city,
        String stateProvince,
        String zipCode,
        Double latitude,
        Double longitude,
        String instructions
) {
}