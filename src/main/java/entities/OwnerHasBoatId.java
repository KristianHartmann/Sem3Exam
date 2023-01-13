package entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OwnerHasBoatId implements Serializable {
    private static final long serialVersionUID = -7381691314467914342L;
    @NotNull
    @Column(name = "Owner_idOwner", nullable = false)
    private Integer ownerIdowner;

    @NotNull
    @Column(name = "Boat_idBoat", nullable = false)
    private Integer boatIdboat;

    public Integer getOwnerIdowner() {
        return ownerIdowner;
    }

    public void setOwnerIdowner(Integer ownerIdowner) {
        this.ownerIdowner = ownerIdowner;
    }

    public Integer getBoatIdboat() {
        return boatIdboat;
    }

    public void setBoatIdboat(Integer boatIdboat) {
        this.boatIdboat = boatIdboat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerHasBoatId entity = (OwnerHasBoatId) o;
        return Objects.equals(this.boatIdboat, entity.boatIdboat) &&
                Objects.equals(this.ownerIdowner, entity.ownerIdowner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boatIdboat, ownerIdowner);
    }

}