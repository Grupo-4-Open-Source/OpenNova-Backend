package pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.transform;

import pe.edu.upc.opennova.automovilunite.rentals.domain.model.aggregates.Insurance;
import pe.edu.upc.opennova.automovilunite.rentals.interfaces.rest.resources.InsuranceResource;

public class InsuranceResourceFromEntityAssembler {
    public static InsuranceResource toResourceFromEntity(Insurance entity) {
        return new InsuranceResource(
                entity.getId(),
                entity.getPlanName(),
                entity.getDescription(),
                entity.getDailyCost(),
                entity.getDeductible(),
                entity.getCoverageDetails()
        );
    }
}