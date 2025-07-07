package pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.transform;

import pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands.CreateInsuranceCommand;
import pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.resources.CreateInsuranceResource;

public class CreateInsuranceCommandFromResourceAssembler {
    public static CreateInsuranceCommand toCommandFromResource(CreateInsuranceResource resource) {
        return new CreateInsuranceCommand(
                resource.planName(),
                resource.description(),
                resource.dailyCost(),
                resource.deductible(),
                resource.coverageDetails()
        );
    }
}