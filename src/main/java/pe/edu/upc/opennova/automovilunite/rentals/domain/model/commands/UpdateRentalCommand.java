package pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands;

import pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects.ERentalStatus;

import java.math.BigDecimal;
import java.util.Date;

public record UpdateRentalCommand(
        Long rentalId,
        Date startDate,
        Date endDate,
        BigDecimal totalCost,
        BigDecimal baseCost,
        BigDecimal insuranceCost,
        BigDecimal platformCommission,
        Double dropoffMileage,
        ERentalStatus status
) {
}