package pe.edu.upc.opennova.automovilunite.publications.domain.services;

import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Publication;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetPublicationsByIdQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetAllPublicationsQuery;

import java.util.List;
import java.util.Optional;

public interface PublicationQueryService {
    List<Publication> handle(GetAllPublicationsQuery query);
    Optional<Publication> handle(GetPublicationsByIdQuery query);

}
