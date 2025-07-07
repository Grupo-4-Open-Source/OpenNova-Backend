package pe.edu.upc.opennova.automovilunite.rentals.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.aggregates.Rental;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects.ERentalStatus;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects.PublicationId;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {


    @Query("SELECT r FROM Rental r WHERE " +
            "r.publicationId = :publicationId AND " +
            "r.status.status IN :statuses AND " +
            "((:startDate <= r.endDate AND :endDate >= r.startDate) OR " +
            "(:startDate BETWEEN r.startDate AND r.endDate) OR " +
            "(:endDate BETWEEN r.startDate AND r.endDate))")
    List<Rental> findOverlappingRentalsForPublication(
            @Param("publicationId") PublicationId publicationId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("statuses") List<ERentalStatus> statuses
    );

    Optional<Rental> findByPublicationId(PublicationId publicationId);

    boolean existsByPublicationId(PublicationId publicationId);
}