package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Harbour {
    public Harbour() {
    }

    public Harbour(String name, String address, Integer capacity) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHarbour", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "name", length = 45)
    private String name;

    @Size(max = 45)
    @Column(name = "address", length = 45)
    private String address;

    @Column(name = "capacity")
    private Integer capacity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cityinfo_idcityinfo", nullable = false)
    private Cityinfo cityinfoIdcityinfo;

    @OneToMany(mappedBy = "harbourIdharbour")
    private Set<Boat> boats = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
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