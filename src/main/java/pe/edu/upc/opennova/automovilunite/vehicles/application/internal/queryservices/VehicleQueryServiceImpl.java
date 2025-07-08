package pe.edu.upc.opennova.automovilunite.vehicles.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.aggregates.Vehicle;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.queries.GetAllVehiclesQuery;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.queries.GetVehicleByIdQuery;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.queries.GetVehiclesByOwnerIdQuery;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.services.VehicleQueryService;
import pe.edu.upc.opennova.automovilunite.vehicles.infrastructure.persistance.jpa.repositories.VehicleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleQueryServiceImpl implements VehicleQueryService {

    private final VehicleRepository vehicleRepository;

    public VehicleQueryServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public List<Vehicle> handle(GetAllVehiclesQuery query) {
        return this.vehicleRepository.findAll();
    }

    @Override
    public Optional<Vehicle> handle(GetVehicleByIdQuery query) {
        return this.vehicleRepository.findById(query.vehicleId());
    }

    @Override
    public List<Vehicle> handle(GetVehiclesByOwnerIdQuery query) {
        return this.vehicleRepository.findByOwnerIdValue(query.ownerId());
    }
}