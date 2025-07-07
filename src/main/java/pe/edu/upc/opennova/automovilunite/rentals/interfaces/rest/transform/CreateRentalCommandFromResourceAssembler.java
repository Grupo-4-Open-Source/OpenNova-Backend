package pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.transform;

import pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands.CreateRentalCommand;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects.InsuranceId;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects.LocationId;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects.PublicationId;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects.RenterId;
import pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.resources.CreateRentalResource;

public class CreateRentalCommandFromResourceAssembler {
    public static CreateRentalCommand toCommandFromResource(CreateRentalResource resource) {
        return new CreateRentalCommand(
                new PublicationId(resource.publicationId()),
                new RenterId(resource.renterId()),
                resource.bookingDate(),
                resource.startDate(),
                resource.endDate(),
                resource.totalCost(),
                resource.baseCost(),
                resource.insuranceCost(),
                resource.platformCommission(),
                resource.pickupMileage(),
                new InsuranceId(resource.insuranceId()),
                new LocationId(resource.pickupLocationId())
        );
    }
}