package pe.edu.upc.opennova.automovilunite.publications.domain.model.commands;

public record UpdatePublicationCommand(
        Long publicationId,
        String model,
        String brand,
        String year,
        String description,
        String image,
        Integer price
) {
}
