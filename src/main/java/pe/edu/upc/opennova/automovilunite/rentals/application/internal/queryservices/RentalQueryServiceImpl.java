package pe.edu.upc.opennova.automovilunite.rentals.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.aggregates.Rental;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.queries.GetAllRentalsQuery;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.queries.GetRentalByIdQuery;
import pe.edu.upc.opennova.automovilunite.rentals.domain.services.RentalQueryService;
import pe.edu.upc.opennova.automovilunite.rentals.infrastructure.persistence.jpa.repositories.RentalRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RentalQueryServiceImpl implements RentalQueryService {

    private final RentalRepository rentalRepository;

    public RentalQueryServiceImpl(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Override
    public Optional<Rental> handle(GetRentalByIdQuery query) {
        return rentalRepository.findById(query.rentalId());
    }

    @Override
    public List<Rental> handle(GetAllRentalsQuery query) {
        return rentalRepository.findAll();
    }
}