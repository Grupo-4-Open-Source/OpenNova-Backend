package pe.edu.upc.opennova.automovilunite.rentals.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.aggregates.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
}