package pe.edu.upc.opennova.automovilunite.vehicles.domain.model.commands;

import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.valueobjects.EFuelType;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.valueobjects.EVehicleType;

import java.util.List;

public record UpdateVehicleCommand(
        Long vehicleId,
        String make,
        String model,
        Integer year,
        String color,
        Integer currentMileage,
        EVehicleType vehicleType,
        EFuelType fuelType,
        Integer passengerCapacity,
        String description,
        String mainImageUrl,
        List<String> galleryImageUrls
) {
}