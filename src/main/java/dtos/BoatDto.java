package dtos;

import entities.Boat;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link entities.Boat} entity
 */
public class BoatDto implements Serializable {
    private final Integer id;
    @Size(max = 45)
    private final String brand;
    @Size(max = 45)
    private final String model;
    @Size(max = 45)
    private final String name;
    @Size(max = 45)
    private final String image;

    public BoatDto(Boat boat) {
        this.id = boat.getId();
        this.brand = boat.getBrand();
        this.model = boat.getModel();
        this.name = boat.getName();
        this.image = boat.getImage();
    }

    public Integer getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoatDto entity = (BoatDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.brand, entity.brand) &&
                Objects.equals(this.model, entity.model) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.image, entity.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, name, image);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "brand = " + brand + ", " +
                "model = " + model + ", " +
                "name = " + name + ", " +
                "image = " + image + ")";
    }
}