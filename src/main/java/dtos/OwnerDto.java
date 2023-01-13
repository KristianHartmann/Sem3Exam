package dtos;

import entities.Owner;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link entities.Owner} entity
 */
public class OwnerDto implements Serializable {
    private final Integer id;
    @Size(max = 45)
    private final String phone;
    @Size(max = 45)
    private final String address;

    public OwnerDto(Owner owner) {
        this.id = owner.getId();
        this.phone = owner.getPhone();
        this.address = owner.getAddress();
    }

    public Integer getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerDto entity = (OwnerDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.phone, entity.phone) &&
                Objects.equals(this.address, entity.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phone, address);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "phone = " + phone + ", " +
                "address = " + address + ")";
    }
}