package pe.edu.upc.opennova.automovilunite.vehicles.domain.services;

import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.aggregates.Vehicle;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.queries.GetVehiclesByIdQuery;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.queries.GetAllVehiclesQuery;

import java.util.List;
import java.util.Optional;

public interface VehicleQueryService {
    List<Vehicle> handle(GetAllVehiclesQuery query);
    Optional<Vehicle> handle(GetVehiclesByIdQuery query);

}
