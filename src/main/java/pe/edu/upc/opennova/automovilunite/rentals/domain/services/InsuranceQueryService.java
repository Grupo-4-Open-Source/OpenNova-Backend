package pe.edu.upc.opennova.automovilunite.rentals.domain.services;

import pe.edu.upc.opennova.automovilunite.rentals.domain.model.aggregates.Insurance;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.queries.GetAllInsurancesQuery;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.queries.GetInsuranceByIdQuery;

import java.util.List;
import java.util.Optional;

public interface InsuranceQueryService {
    Optional<Insurance> handle(GetInsuranceByIdQuery query);
    List<Insurance> handle(GetAllInsurancesQuery query);
}