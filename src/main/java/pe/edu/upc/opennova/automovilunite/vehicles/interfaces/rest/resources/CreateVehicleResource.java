package pe.edu.upc.opennova.automovilunite.vehicles.interfaces.rest.resources;

public record CreateVehicleResource(
        String model,
        String brand,
        String year,
        String description,
        String image,
        Integer price
) {
}
