package pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.transform;

import pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands.UpdateInsuranceCommand;
import pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.resources.CreateInsuranceResource;

public class UpdateInsuranceCommandFromResourceAssembler {
    public static UpdateInsuranceCommand toCommandFromResource(Long insuranceId, CreateInsuranceResource resource) {
        return new UpdateInsuranceCommand(
                insuranceId,
                resource.planName(),
                resource.description(),
                resource.dailyCost(),
                resource.deductible(),
                resource.coverageDetails()
        );
    }
}