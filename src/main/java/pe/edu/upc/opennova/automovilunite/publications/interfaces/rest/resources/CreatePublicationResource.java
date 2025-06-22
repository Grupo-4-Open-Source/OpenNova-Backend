package pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources;

public record CreatePublicationResource(
        String model,
        String brand,
        String year,
        String description,
        String image,
        Integer price
) {
}
