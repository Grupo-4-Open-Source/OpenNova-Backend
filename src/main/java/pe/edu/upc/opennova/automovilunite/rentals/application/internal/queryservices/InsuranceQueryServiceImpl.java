package pe.edu.upc.opennova.automovilunite.rentals.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.aggregates.Insurance;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.queries.GetAllInsurancesQuery;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.queries.GetInsuranceByIdQuery;
import pe.edu.upc.opennova.automovilunite.rentals.domain.services.InsuranceQueryService;
import pe.edu.upc.opennova.automovilunite.rentals.infrastructure.persistence.jpa.repositories.InsuranceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InsuranceQueryServiceImpl implements InsuranceQueryService {

    private final InsuranceRepository insuranceRepository;

    public InsuranceQueryServiceImpl(InsuranceRepository insuranceRepository) {
        this.insuranceRepository = insuranceRepository;
    }

    @Override
    public Optional<Insurance> handle(GetInsuranceByIdQuery query) {
        return insuranceRepository.findById(query.insuranceId());
    }

    @Override
    public List<Insurance> handle(GetAllInsurancesQuery query) {
        return insuranceRepository.findAll();
    }
}