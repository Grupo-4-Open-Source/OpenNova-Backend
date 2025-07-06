package pe.edu.upc.opennova.automovilunite.publications.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Publication;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects.ProfileId;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects.VehicleId;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
    Optional<Publication> findByExternalId(String externalId);
    List<Publication> findByOwnerId(ProfileId ownerId);
    List<Publication> findByIsFeaturedTrue();
    List<Publication> findByVehicleId(VehicleId vehicleId);
    boolean existsByExternalId(String externalId);
    void deleteByExternalId(String externalId);
}