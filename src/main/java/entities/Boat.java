package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Boat {
    public Boat() {
    }

    public Boat(String brand, String model, String name, String image) {
        this.brand = brand;
        this.model = model;
        this.name = name;
        this.image = image;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBoat", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "brand", length = 45)
    private String brand;

    @Size(max = 45)
    @Column(name = "model", length = 45)
    private String model;

    @Size(max = 45)
    @Column(name = "name", length = 45)
    private String name;

    @Size(max = 45)
    @Column(name = "image", length = 45)
    private String image;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Harbour_idHarbour", nullable = false)
    private Harbour harbourIdharbour;

    @ManyToMany
    @JoinTable(name = "Owner_has_Boat",
            joinColumns = @JoinColumn(name = "Boat_idBoat"),
            inverseJoinColumns = @JoinColumn(name = "Owner_idOwner"))
    private Set<Owner> owners = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Harbour getHarbourIdharbour() {
        return harbourIdharbour;
    }

    public void setHarbourIdharbour(Harbour harbourIdharbour) {
        this.harbourIdharbour = harbourIdharbour;
    }

    public Set<Owner> getOwners() {
        return owners;
    }

    public void setOwners(Set<Owner> owners) {
        this.owners = owners;
    }

}