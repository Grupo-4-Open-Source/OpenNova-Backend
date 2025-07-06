package pe.edu.upc.opennova.automovilunite.vehicles.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.aggregates.Vehicle;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.queries.GetVehicleByIdQuery;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.services.VehicleCommandService;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.services.VehicleQueryService;

import java.util.Optional;

@Service
public class VehicleContextFacade {

    private final VehicleCommandService vehicleCommandService;
    private final VehicleQueryService vehicleQueryService;

    public VehicleContextFacade(VehicleCommandService vehicleCommandService, VehicleQueryService vehicleQueryService) {
        this.vehicleCommandService = vehicleCommandService;
        this.vehicleQueryService = vehicleQueryService;
    }

    public boolean existsVehicleById(Long vehicleId) {
        return vehicleQueryService.handle(new GetVehicleByIdQuery(vehicleId)).isPresent();
    }

    public Optional<Vehicle> fetchVehicleById(Long vehicleId) {
        return vehicleQueryService.handle(new GetVehicleByIdQuery(vehicleId));
    }
}