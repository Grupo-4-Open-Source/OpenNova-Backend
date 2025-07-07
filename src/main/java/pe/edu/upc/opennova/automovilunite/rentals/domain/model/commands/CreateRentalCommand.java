package pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands;

import pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects.InsuranceId;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects.LocationId;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects.PublicationId;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects.RenterId;

import java.math.BigDecimal;
import java.util.Date;

public record CreateRentalCommand(
        PublicationId publicationId,
        RenterId renterId,
        Date bookingDate,
        Date startDate,
        Date endDate,
        BigDecimal totalCost,
        BigDecimal baseCost,
        BigDecimal insuranceCost,
        BigDecimal platformCommission,
        Double pickupMileage,
        InsuranceId insuranceId,
        LocationId pickupLocationId
) {
}