package pe.edu.upc.opennova.automovilunite.vehicles.domain.model.commands;

import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.valueobjects.EFuelType;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.valueobjects.EVehicleType;

import java.util.List;

public record CreateVehicleCommand(
        String ownerId,
        String make,
        String model,
        Integer year,
        String color,
        String licensePlate,
        Integer currentMileage,
        EVehicleType vehicleType,
        EFuelType fuelType,
        Integer passengerCapacity,
        String description,
        String mainImageUrl,
        List<String> galleryImageUrls
) {
}