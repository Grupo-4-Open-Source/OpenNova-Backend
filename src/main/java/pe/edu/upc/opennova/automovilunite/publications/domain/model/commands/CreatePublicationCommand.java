package pe.edu.upc.opennova.automovilunite.publications.domain.model.commands;

public record CreatePublicationCommand(
        String model,
        String brand,
        String year,
        String description,
        String image,
        Integer price
) {
}
