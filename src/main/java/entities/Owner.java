package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Owner {
    public Owner() {
    }

    public Owner(String phone, String address) {
        this.phone = phone;
        this.address = address;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idOwner", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "phone", length = 45)
    private String phone;

    @Size(max = 45)
    @Column(name = "address", length = 45)
    private String address;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cityinfo_idcityinfo", nullable = false)
    private Cityinfo cityinfoIdcityinfo;

    @ManyToMany
    @JoinTable(name = "Owner_has_Boat",
            joinColumns = @JoinColumn(name = "Owner_idOwner"),
            inverseJoinColumns = @JoinColumn(name = "Boat_idBoat"))
    private Set<Boat> boats = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Cityinfo getCityinfoIdcityinfo() {
        return cityinfoIdcityinfo;
    }

    public void setCityinfoIdcityinfo(Cityinfo cityinfoIdcityinfo) {
        this.cityinfoIdcityinfo = cityinfoIdcityinfo;
    }

    public Set<Boat> getBoats() {
        return boats;
    }

    public void setBoats(Set<Boat> boats) {
        this.boats = boats;
    }

}