package pe.edu.upc.opennova.automovilunite.publications.application.internal.queryservices;


import org.springframework.stereotype.Service;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Publication;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetAllPublicationsQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetPublicationsByIdQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.services.PublicationQueryService;
import pe.edu.upc.opennova.automovilunite.publications.infrastructure.persistance.jpa.repositories.PublicationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PublicationQueryServiceImpl implements PublicationQueryService {

    private final PublicationRepository publicationRepository;

    public PublicationQueryServiceImpl(PublicationRepository publicationRepository) {this.publicationRepository = publicationRepository;}

    @Override
    public List<Publication> handle(GetAllPublicationsQuery query) { return this.publicationRepository.findAll(); }

    @Override
    public Optional<Publication> handle(GetPublicationsByIdQuery query) {
        return this.publicationRepository.findById(query.publicationId());
    }
}
