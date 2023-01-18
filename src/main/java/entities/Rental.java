package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Rental {
    public Rental() {
    }

    @JoinColumn(name = "FK_idHouse", referencedColumnName = "idHouse")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private House house = new House();

    @JoinColumn(name = "FK_idContactPerson", referencedColumnName = "idContactPerson")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private ContactPerson contactPerson = new ContactPerson();

    @ManyToMany(mappedBy = "rentalList")
    private Set<Tenant> tenants = new LinkedHashSet<>();


    public Rental(String startDate, String endDate, Integer priceAnnual, Integer deposit) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceAnnual = priceAnnual;
        this.deposit = deposit;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRental", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "startDate", length = 45)
    private String startDate;

    @Size(max = 45)
    @Column(name = "endDate", length = 45)
    private String endDate;

    @Column(name = "priceAnnual")
    private Integer priceAnnual;

    @Column(name = "deposit")
    private Integer deposit;



    public ContactPerson getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(ContactPerson contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void addHouse(House house){
        this.house = house;
    }
    public void addContactPerson(ContactPerson contactPerson){
        this.contactPerson = contactPerson;
    }
    public House getHouse() {
        return house;
    }

    public Set<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(Set<Tenant> tenants) {
        this.tenants = tenants;
    }

    public void setHouse(House house) {
        this.house = house;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getPriceAnnual() {
        return priceAnnual;
    }

    public void setPriceAnnual(Integer priceAnnual) {
        this.priceAnnual = priceAnnual;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }



}