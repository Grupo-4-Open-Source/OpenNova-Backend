package pe.edu.upc.opennova.automovilunite.publications.domain.services;

import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Publication;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetAllPublicationsQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetFeaturedPublicationsQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetPublicationByExternalIdQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetPublicationsByOwnerIdQuery;

import java.util.List;
import java.util.Optional;

public interface PublicationQueryService {
    List<Publication> handle(GetAllPublicationsQuery query);
    Optional<Publication> handle(GetPublicationByExternalIdQuery query);
    List<Publication> handle(GetPublicationsByOwnerIdQuery query);
    List<Publication> handle(GetFeaturedPublicationsQuery query);
}