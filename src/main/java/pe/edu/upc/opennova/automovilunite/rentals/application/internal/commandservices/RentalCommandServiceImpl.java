package pe.edu.upc.opennova.automovilunite.rentals.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.opennova.automovilunite.rentals.application.internal.outboundservices.acl.ExternalPublicationService;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.aggregates.Insurance;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.aggregates.Rental;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands.CreateRentalCommand;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands.DeleteRentalCommand;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.commands.UpdateRentalCommand;
import pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects.ERentalStatus;
import pe.edu.upc.opennova.automovilunite.rentals.domain.services.RentalCommandService;
import pe.edu.upc.opennova.automovilunite.rentals.infrastructure.persistence.jpa.repositories.InsuranceRepository;
import pe.edu.upc.opennova.automovilunite.rentals.infrastructure.persistence.jpa.repositories.RentalRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class RentalCommandServiceImpl implements RentalCommandService {

    private final RentalRepository rentalRepository;
    private final InsuranceRepository insuranceRepository;
    private final ExternalPublicationService externalPublicationService;

    private static final BigDecimal PLATFORM_COMMISSION_RATE = BigDecimal.valueOf(0.10);

    public RentalCommandServiceImpl(RentalRepository rentalRepository,
                                    InsuranceRepository insuranceRepository,
                                    ExternalPublicationService externalPublicationService) {
        this.rentalRepository = rentalRepository;
        this.insuranceRepository = insuranceRepository;
        this.externalPublicationService = externalPublicationService;
    }

    @Override
    @Transactional
    public Long handle(CreateRentalCommand command) {
        // Corrected call: use getPublicationUuid()
        if (!externalPublicationService.existsPublicationByExternalId(command.publicationId().getPublicationUuid())) {
            throw new IllegalArgumentException("Publication with ID " + command.publicationId().getPublicationUuid() + " does not exist.");
        }

        Optional<Insurance> insuranceOptional = insuranceRepository.findById(command.insuranceId().getInsuranceInternalId());
        if (insuranceOptional.isEmpty()) {
            throw new IllegalArgumentException("Insurance plan with ID " + command.insuranceId().getInsuranceInternalId() + " does not exist in rentals bounded context.");
        }
        BigDecimal insuranceDailyCost = insuranceOptional.get().getDailyCost();

        if (command.startDate().after(command.endDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
        if (command.bookingDate().after(command.startDate())) {
            throw new IllegalArgumentException("Booking date cannot be after start date.");
        }

        // Corrected call: use getPublicationUuid()
        if (!externalPublicationService.isPublicationAvailable(command.publicationId().getPublicationUuid(), command.startDate(), command.endDate())) {
            throw new IllegalArgumentException("Publication is not available for the requested dates.");
        }

        // Corrected calls: use getPublicationUuid()
        BigDecimal publicationDailyPrice = externalPublicationService.getPublicationDailyPrice(command.publicationId().getPublicationUuid())
                .orElseThrow(() -> new IllegalArgumentException("Could not retrieve daily price for publication " + command.publicationId().getPublicationUuid()));
        Double vehicleCurrentMileage = externalPublicationService.getVehicleCurrentMileage(command.publicationId().getPublicationUuid())
                .orElseThrow(() -> new IllegalArgumentException("Could not retrieve vehicle current mileage for publication " + command.publicationId().getPublicationUuid()));
        String publicationPickupLocationId = externalPublicationService.getPublicationPickupLocationId(command.publicationId().getPublicationUuid())
                .orElseThrow(() -> new IllegalArgumentException("Could not retrieve pickup location for publication " + command.publicationId().getPublicationUuid()));


        long diffDays = ChronoUnit.DAYS.between(command.startDate().toInstant(), command.endDate().toInstant()) + 1;
        if (diffDays <= 0) {
            throw new IllegalArgumentException("Rental duration must be at least one day.");
        }

        BigDecimal baseCost = publicationDailyPrice.multiply(BigDecimal.valueOf(diffDays)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal insuranceCost = insuranceDailyCost.multiply(BigDecimal.valueOf(diffDays)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalBeforeCommission = baseCost.add(insuranceCost);
        BigDecimal platformCommission = totalBeforeCommission.multiply(PLATFORM_COMMISSION_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalCost = totalBeforeCommission.add(platformCommission).setScale(2, RoundingMode.HALF_UP);

        if (command.baseCost().compareTo(baseCost) != 0 ||
                command.insuranceCost().compareTo(insuranceCost) != 0 ||
                command.platformCommission().compareTo(platformCommission) != 0 ||
                command.totalCost().compareTo(totalCost) != 0) {
            System.out.println("Warning: Frontend provided costs do not match backend calculated costs. Using backend values.");
        }

        var rental = new Rental(
                command.publicationId(),
                command.renterId(),
                command.bookingDate(),
                command.startDate(),
                command.endDate(),
                totalCost,
                baseCost,
                insuranceCost,
                platformCommission,
                vehicleCurrentMileage,
                command.insuranceId(),
                new pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects.LocationId(publicationPickupLocationId)
        );
        try {
            rentalRepository.save(rental);
            return rental.getId();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create rental: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Optional<Rental> handle(UpdateRentalCommand command) {
        return rentalRepository.findById(command.rentalId()).map(rental -> {
            if (command.startDate().after(command.endDate())) {
                throw new IllegalArgumentException("Updated start date cannot be after updated end date.");
            }

            ERentalStatus currentStatus = rental.getStatus().getStatus();
            ERentalStatus newStatus = command.status();

            if (newStatus == ERentalStatus.CANCELLED_BY_RENTER || newStatus == ERentalStatus.CANCELLED_BY_OWNER) {
                if (currentStatus != ERentalStatus.PENDING_OWNER_APPROVAL &&
                        currentStatus != ERentalStatus.CONFIRMED &&
                        currentStatus != ERentalStatus.ONGOING) {
                    throw new IllegalArgumentException("Cannot cancel a rental that is already " + currentStatus.getStringName());
                }
            } else if (newStatus == ERentalStatus.CONFIRMED) {
                if (currentStatus != ERentalStatus.PENDING_OWNER_APPROVAL) {
                    throw new IllegalArgumentException("Cannot confirm a rental that is not in PENDING_OWNER_APPROVAL status.");
                }
                if (!externalPublicationService.isPublicationAvailable(rental.getPublicationId().getPublicationUuid(), rental.getStartDate(), rental.getEndDate())) {
                    throw new IllegalArgumentException("Publication is no longer available for these dates, cannot confirm rental.");
                }
            } else if (newStatus == ERentalStatus.ONGOING) {
                if (currentStatus != ERentalStatus.CONFIRMED) {
                    throw new IllegalArgumentException("Cannot set rental to ONGOING if it's not CONFIRMED.");
                }
            } else if (newStatus == ERentalStatus.COMPLETED) {
                if (currentStatus != ERentalStatus.ONGOING && currentStatus != ERentalStatus.CONFIRMED) {
                    throw new IllegalArgumentException("Cannot set rental to COMPLETED if it's not ONGOING or CONFIRMED.");
                }
                if (command.dropoffMileage() == null || command.dropoffMileage() <= rental.getPickupMileage()) {
                    throw new IllegalArgumentException("Dropoff mileage must be provided and greater than pickup mileage to complete rental.");
                }
            }

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
    @Transactional
    public void handle(DeleteRentalCommand command) {
        if (!rentalRepository.existsById(command.rentalId())) {
            throw new IllegalArgumentException("Rental with ID " + command.rentalId() + " not found, cannot delete.");
        }
        rentalRepository.deleteById(command.rentalId());
    }
}