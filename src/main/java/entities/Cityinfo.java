package entities;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cityinfo")
public class Cityinfo {

    public Cityinfo() {
    }


    public Cityinfo(String cityname, Integer zip) {
        this.cityname = cityname;
        this.zip = zip;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcityinfo", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "cityname", length = 45)
    private String cityname;

    @Column(name = "zip")
    private Integer zip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

}