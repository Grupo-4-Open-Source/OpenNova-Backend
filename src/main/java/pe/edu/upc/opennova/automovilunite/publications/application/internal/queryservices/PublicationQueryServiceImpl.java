package pe.edu.upc.opennova.automovilunite.publications.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Publication;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetAllPublicationsQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetFeaturedPublicationsQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetPublicationByExternalIdQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.queries.GetPublicationsByOwnerIdQuery;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects.ProfileId;
import pe.edu.upc.opennova.automovilunite.publications.domain.services.PublicationQueryService;
import pe.edu.upc.opennova.automovilunite.publications.infrastructure.persistence.jpa.repositories.PublicationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PublicationQueryServiceImpl implements PublicationQueryService {

    private final PublicationRepository publicationRepository;

    public PublicationQueryServiceImpl(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    @Override
    public List<Publication> handle(GetAllPublicationsQuery query) {
        return publicationRepository.findAll();
    }

    @Override
    public Optional<Publication> handle(GetPublicationByExternalIdQuery query) {
        if (query.externalId() == null || query.externalId().isBlank()) {
            throw new IllegalArgumentException("External ID cannot be null or blank.");
        }
        return publicationRepository.findByExternalId(query.externalId());
    }

    @Override
    public List<Publication> handle(GetPublicationsByOwnerIdQuery query) {
        if (query.ownerUuid() == null || query.ownerUuid().isBlank()) {
            throw new IllegalArgumentException("Owner UUID cannot be null or blank.");
        }
        return publicationRepository.findByOwnerId(new ProfileId(query.ownerUuid()));
    }

    @Override
    public List<Publication> handle(GetFeaturedPublicationsQuery query) {
        return publicationRepository.findByIsFeaturedTrue();
    }
}