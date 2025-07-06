package pe.edu.upc.opennova.automovilunite.publications.application.internal.eventhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates.Publication;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects.EPublicationStatus; // Import EPublicationStatus
import pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects.PublicationStatus;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.valueobjects.VehicleId;
import pe.edu.upc.opennova.automovilunite.publications.infrastructure.persistence.jpa.repositories.PublicationRepository;
import pe.edu.upc.opennova.automovilunite.vehicles.domain.model.events.VehicleDeletedEvent;

import java.util.List;

@Service
public class VehicleDeletedEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleDeletedEventHandler.class);
    private final PublicationRepository publicationRepository;

    public VehicleDeletedEventHandler(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    @EventListener
    @Transactional
    public void onVehicleDeletedEvent(VehicleDeletedEvent event) {
        LOGGER.info("Received VehicleDeletedEvent for vehicleId: {}", event.vehicleId());

        List<Publication> publicationsToUpdate = publicationRepository.findByVehicleId(new VehicleId(event.vehicleId()));

        if (publicationsToUpdate.isEmpty()) {
            LOGGER.info("No publications found for deleted vehicleId: {}", event.vehicleId());
            return;
        }

        for (Publication publication : publicationsToUpdate) {
            publication.changeStatus(EPublicationStatus.INACTIVE);
            LOGGER.info("Inactivated publication {} due to vehicle deletion.", publication.getExternalId());
        }
        publicationRepository.saveAll(publicationsToUpdate);

        LOGGER.info("Successfully processed VehicleDeletedEvent for vehicleId: {}. {} publications updated.",
                event.vehicleId(), publicationsToUpdate.size());
    }
}