package entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Cityinfo {
    public Cityinfo() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcityinfo", nullable = false)
    private Integer id;

    @Column(name = "zip")
    private Integer zip;

    @Size(max = 45)
    @Column(name = "cityname", length = 45)
    private String cityname;

    @OneToMany(mappedBy = "cityinfoIdcityinfo")
    private Set<Owner> owners = new LinkedHashSet<>();

    @OneToMany(mappedBy = "cityinfoIdcityinfo")
    private Set<Harbour> harbours = new LinkedHashSet<>();

    public Cityinfo(Integer zip, String cityname) {
        this.zip = zip;
        this.cityname = cityname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public Set<Owner> getOwners() {
        return owners;
    }

    public void setOwners(Set<Owner> owners) {
        this.owners = owners;
    }

    public Set<Harbour> getHarbours() {
        return harbours;
    }

    public void setHarbours(Set<Harbour> harbours) {
        this.harbours = harbours;
    }

}