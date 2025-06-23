package pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.transform;

import pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands.CreateRentalCommand;
import pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.resources.CreateRentalResource;

public class CreateRentalCommandFromResourceAssembler {
    public static CreateRentalCommand toCommandFromResource(CreateRentalResource resource) {
        return new CreateRentalCommand(
                resource.publicationId(),
                resource.renterId(),
                resource.bookingDate(),
                resource.startDate(),
                resource.endDate(),
                resource.totalCost(),
                resource.baseCost(),
                resource.insuranceCost(),
                resource.platformCommission(),
                resource.pickupMileage(),
                resource.insuranceId(),
                resource.pickupLocationId()
        );
    }
}