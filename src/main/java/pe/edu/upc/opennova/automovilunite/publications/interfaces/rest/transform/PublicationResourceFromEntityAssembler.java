package pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.transform;

import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Publication;
import pe.edu.upc.opennova.automovilunite.publications.interfaces.rest.resources.PublicationResource;

public class PublicationResourceFromEntityAssembler {
    public static PublicationResource toResourceFromEntity(Publication entity) {
        return new PublicationResource(
                entity.getId(),
                entity.getModel(),
                entity.getBrand(),
                entity.getYear(),
                entity.getDescription(),
                entity.getImage(),
                entity.getPrice()
                );
    }
}
