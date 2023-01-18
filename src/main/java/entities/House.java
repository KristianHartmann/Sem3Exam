package entities;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class House {
    public House() {
    }


    @JoinColumn(name = "FK_idcityinfo", referencedColumnName = "idcityinfo")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Cityinfo cityinfo = new Cityinfo();

    public House(String address, Integer numberOfRooms) {
        this.address = address;
        this.numberOfRooms = numberOfRooms;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHouse", nullable = false)

    private Integer id;

    @Size(max = 45)
    @Column(name = "address", length = 45)
    private String address;

    @Column(name = "numberOfRooms")
    private Integer numberOfRooms;

    public Integer getId() {
        return id;
    }
    public Cityinfo getCityinfo() {
        return cityinfo;
    }

    public void addCityinfo(Cityinfo cityinfo){
        this.cityinfo = cityinfo;
    }
    public void setCityinfo(Cityinfo cityinfo) {
        this.cityinfo = cityinfo;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

}