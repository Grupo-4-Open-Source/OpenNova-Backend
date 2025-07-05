package pe.edu.upc.opennova.automovilunite.rentals.domain.model.valueobjects;

import java.util.Arrays;
import java.util.Optional;

public enum ERentalStatus {
    PENDING_OWNER_APPROVAL("PENDING_OWNER_APPROVAL"),
    CONFIRMED("CONFIRMED"),
    CANCELLED_BY_RENTER("CANCELLED_BY_RENTER"),
    CANCELLED_BY_OWNER("CANCELLED_BY_OWNER"),
    COMPLETED("COMPLETED"),
    ONGOING("ONGOING");

    private final String stringName;

    ERentalStatus(String stringName) {
        this.stringName = stringName;
    }

    public String getStringName() {
        return stringName;
    }

    public static Optional<ERentalStatus> fromString(String status) {
        return Arrays.stream(ERentalStatus.values())
                .filter(s -> s.stringName.equalsIgnoreCase(status))
                .findFirst();
    }
}