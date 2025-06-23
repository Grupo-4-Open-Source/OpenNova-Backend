package pe.edu.upc.opennova.automovilunite.rentals.domain.services;

import pe.edu.upc.opennova.automovilunite.rentals.domain.model.aggregates.Rental;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.queries.GetAllRentalsQuery;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.queries.GetRentalByIdQuery;

import java.util.List;
import java.util.Optional;

public interface RentalQueryService {
    Optional<Rental> handle(GetRentalByIdQuery query);
    List<Rental> handle(GetAllRentalsQuery query);
}