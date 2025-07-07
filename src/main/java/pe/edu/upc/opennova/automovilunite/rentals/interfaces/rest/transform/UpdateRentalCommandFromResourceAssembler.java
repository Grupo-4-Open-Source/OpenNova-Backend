package pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.transform;

import pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands.UpdateRentalCommand;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects.ERentalStatus;
import pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.resources.RentalResource;

public class UpdateRentalCommandFromResourceAssembler {
    public static UpdateRentalCommand toCommandFromResource(Long rentalId, RentalResource resource) {
        ERentalStatus statusEnum = ERentalStatus.fromString(resource.status())
                .orElseThrow(() -> new IllegalArgumentException("Invalid rental status provided: " + resource.status()));

        return new UpdateRentalCommand(
                rentalId,
                resource.startDate(),
                resource.endDate(),
                resource.totalCost(),
                resource.baseCost(),
                resource.insuranceCost(),
                resource.platformCommission(),
                resource.dropoffMileage(),
                statusEnum 
        );
    }
}