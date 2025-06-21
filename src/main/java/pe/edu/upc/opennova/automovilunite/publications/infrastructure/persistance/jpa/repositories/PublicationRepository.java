package pe.edu.upc.opennova.automovilunite.publications.infrastructure.persistance.jpa.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Publication;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

}
