package dtos;

import entities.Harbour;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link entities.Harbour} entity
 */
public class HarbourDto implements Serializable {
    private final Integer id;
    @Size(max = 45)
    private final String name;
    @Size(max = 45)
    private final String address;
    private final Integer capacity;

    public HarbourDto(Harbour harbour) {
        this.id = harbour.getId();
        this.name = harbour.getName();
        this.address = harbour.getAddress();
        this.capacity = harbour.getCapacity();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Integer getCapacity() {
        return capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HarbourDto entity = (HarbourDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.address, entity.address) &&
                Objects.equals(this.capacity, entity.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, capacity);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "address = " + address + ", " +
                "capacity = " + capacity + ")";
    }
}