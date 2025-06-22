package pe.edu.upc.opennova.automovilunite.publications.domain.model.aggregates;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.opennova.automovilunite.publications.domain.model.commands.CreatePublicationCommand;
import pe.edu.upc.opennova.automovilunite.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Getter
@Entity
@Table(name = "publications")

public class Publication extends AuditableAbstractAggregateRoot<Publication> {


    @NotBlank
    @Column(name = "model", nullable = false)
    private String model;

    @NotBlank
    @Column(name = "brand", nullable = false)
    private String brand;

    @NotBlank
    @Column(name = "year", nullable = false)
    private String year;

    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;

    @NotBlank
    @Column(name = "image", nullable = false)
    private String image;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    public Publication() {

    }


    public Publication(String model, String brand, String year, String description, String image, Integer price) {
        this.model = model;
        this.brand = brand;
        this.year = year;
        this.description = description;
        this.image = image;
        this.price = price;
    }

    public Publication(CreatePublicationCommand command) {
        this.model = command.model();
        this.brand = command.brand();
        this.year = command.year();
        this.description = command.description();
        this.image = command.image();
        this.price = command.price();
    }

    public Publication updatePublication(String model, String brand, String year, String description, String image, Integer price) {
        this.model = model;
        this.brand = brand;
        this.year = year;
        this.description = description;
        this.image = image;
        this.price = price;
        return this;
    }

}
