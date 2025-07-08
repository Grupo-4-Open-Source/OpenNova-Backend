package pe.edu.upc.opennova.automovilunite.vehicles.infrastructure.persistance.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.aggregates.Vehicle;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByOwnerIdValue(String ownerId);

    boolean existsByLicensePlate(String licensePlate);
}