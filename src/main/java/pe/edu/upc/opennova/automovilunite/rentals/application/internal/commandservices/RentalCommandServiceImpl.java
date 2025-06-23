package pe.edu.upc.opennova.automovilunite.rentals.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.aggregates.Rental;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands.CreateRentalCommand;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands.DeleteRentalCommand;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands.UpdateRentalCommand;
import pe.edu.upc.opennova.automovilunite.rentals.domain.services.RentalCommandService;
import pe.edu.upc.opennova.automovilunite.rentals.infrastructure.persistence.jpa.repositories.RentalRepository;

import java.util.Optional;

@Service
public class RentalCommandServiceImpl implements RentalCommandService {

    private final RentalRepository rentalRepository;

    public RentalCommandServiceImpl(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Override
    public Long handle(CreateRentalCommand command) {
        var rental = new Rental(
                command.publicationId(),
                command.renterId(),
                command.bookingDate(),
                command.startDate(),
                command.endDate(),
                command.totalCost(),
                command.baseCost(),
                command.insuranceCost(),
                command.platformCommission(),
                command.pickupMileage(),
                command.insuranceId(),
                command.pickupLocationId()
        );
        try {
            rentalRepository.save(rental);
            return rental.getId();
        } catch (Exception e) {
            System.err.println("Error creating rental: " + e.getMessage());
            return 0L;
        }
    }

    @Override
    public Optional<Rental> handle(UpdateRentalCommand command) {
        return rentalRepository.findById(command.rentalId()).map(rental -> {
            rental.update(
                    command.startDate(),
                    command.endDate(),
                    command.totalCost(),
                    command.baseCost(),
                    command.insuranceCost(),
                    command.platformCommission(),
                    command.dropoffMileage(),
                    command.status()
            );
            return Optional.of(rentalRepository.save(rental));
        }).orElse(Optional.empty());
    }

    @Override
    public void handle(DeleteRentalCommand command) {
        rentalRepository.deleteById(command.rentalId());
    }
}