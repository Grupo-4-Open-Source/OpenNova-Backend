package pe.edu.upc.opennova.automovilunite.vehicles.domain.model.valueobjects;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
public class GalleryImageUrls {
    @ElementCollection
    @CollectionTable(name = "vehicle_gallery_images", joinColumns = @JoinColumn(name = "vehicle_id"))
    @Column(name = "image_url")
    private List<String> urls;

    public GalleryImageUrls() {
        this.urls = new ArrayList<>();
    }

    public GalleryImageUrls(List<String> urls) {
        this.urls = urls != null ? new ArrayList<>(urls) : new ArrayList<>();
    }
}