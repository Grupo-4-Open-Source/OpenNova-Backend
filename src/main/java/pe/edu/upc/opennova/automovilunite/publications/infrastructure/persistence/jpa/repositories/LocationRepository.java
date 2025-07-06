package pe.edu.upc.opennova.automovilunite.publications.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Location;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByExternalId(String externalId);
    boolean existsByExternalId(String externalId);
    void deleteByExternalId(String externalId);
}