package pe.edu.upc.opennova.automovilunite.rentals.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.aggregates.Insurance;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands.CreateInsuranceCommand;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands.DeleteInsuranceCommand;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands.UpdateInsuranceCommand;
import pe.edu.upc.opennova.automovilunite.rentals.domain.services.InsuranceCommandService;
import pe.edu.upc.opennova.automovilunite.rentals.infrastructure.persistence.jpa.repositories.InsuranceRepository;

import java.util.Optional;

@Service
public class InsuranceCommandServiceImpl implements InsuranceCommandService {

    private final InsuranceRepository insuranceRepository;

    public InsuranceCommandServiceImpl(InsuranceRepository insuranceRepository) {
        this.insuranceRepository = insuranceRepository;
    }

    @Override
    @Transactional
    public Long handle(CreateInsuranceCommand command) {
        var insurance = new Insurance(
                command.planName(),
                command.description(),
                command.dailyCost(),
                command.deductible(),
                command.coverageDetails()
        );
        insuranceRepository.save(insurance);
        return insurance.getId();
    }

    @Override
    @Transactional
    public Optional<Insurance> handle(UpdateInsuranceCommand command) {
        return insuranceRepository.findById(command.insuranceId()).map(insurance -> {
            insurance.update(
                    command.planName(),
                    command.description(),
                    command.dailyCost(),
                    command.deductible(),
                    command.coverageDetails()
            );
            return Optional.of(insuranceRepository.save(insurance));
        }).orElse(Optional.empty());
    }

    @Override
    @Transactional
    public void handle(DeleteInsuranceCommand command) {
        if (!insuranceRepository.existsById(command.insuranceId())) {
            throw new IllegalArgumentException("Insurance with ID " + command.insuranceId() + " not found, cannot delete.");
        }
        insuranceRepository.deleteById(command.insuranceId());
    }
}