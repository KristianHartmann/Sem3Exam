package entities;

import javax.persistence.*;

@Entity
@Table(name = "owner_has_boat")
public class OwnerHasBoat {
    @EmbeddedId
    private OwnerHasBoatId id;

    @MapsId("ownerIdowner")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Owner_idOwner", nullable = false)
    private Owner ownerIdowner;

    @MapsId("boatIdboat")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Boat_idBoat", nullable = false)
    private Boat boatIdboat;

    public OwnerHasBoatId getId() {
        return id;
    }

    public void setId(OwnerHasBoatId id) {
        this.id = id;
    }

    public Owner getOwnerIdowner() {
        return ownerIdowner;
    }

    public void setOwnerIdowner(Owner ownerIdowner) {
        this.ownerIdowner = ownerIdowner;
    }

    public Boat getBoatIdboat() {
        return boatIdboat;
    }

    public void setBoatIdboat(Boat boatIdboat) {
        this.boatIdboat = boatIdboat;
    }

}