package pe.edu.upc.opennova.automovilunite.vehicles.domain.model.commands;

public record UpdateVehicleCommand(
        Long publicationId,
        String model,
        String brand,
        String year,
        String description,
        String image,
        Integer price
) {
}
