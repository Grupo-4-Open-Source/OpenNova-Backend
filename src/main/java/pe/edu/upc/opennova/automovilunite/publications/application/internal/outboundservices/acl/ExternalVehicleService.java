package pe.edu.upc.opennova.automovilunite.publications.application.internal.outboundservices.acl;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pe.edu.upc.opennova.automovilunite.publications.application.internal.outboundservices.acl.dtos.VehicleDto;
import pe.edu.upc.opennova.automovilunite.vehicles.interfaces.acl.VehicleContextFacade;

import java.util.Optional;

@Service
public class ExternalVehicleService {

    private final VehicleContextFacade vehicleContextFacade;

    public ExternalVehicleService(@Lazy VehicleContextFacade vehicleContextFacade) {
        this.vehicleContextFacade = vehicleContextFacade;
    }

    public Optional<VehicleDto> fetchVehicleSummaryById(Long vehicleId) {
        return vehicleContextFacade.fetchVehicleById(vehicleId)
                .map(vehicle -> new VehicleDto(vehicle.getId().toString(), vehicle.getMake(), vehicle.getModel()));
    }

    public boolean existsVehicleById(Long vehicleId) {
        return vehicleContextFacade.existsVehicleById(vehicleId);
    }
}