package pe.edu.upc.opennova.automovilunite.publications.domain.model.commands;

public record UpdateLocationCommand(
        String addressLine1,
        String city,
        String stateProvince,
        String zipCode,
        Double latitude,
        Double longitude,
        String instructions
) {
}