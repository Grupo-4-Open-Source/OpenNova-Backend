package pe.edu.upc.opennova.automovilunite.vehicles.domain.model.commands;

public record CreateVehicleCommand(
        String model,
        String brand,
        String year,
        String description,
        String image,
        Integer price
) {
}
