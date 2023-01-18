package entities;

import security.entities.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Tenant {

    @JoinTable(name = "tenant_has_rental", joinColumns = {
            @JoinColumn(name = "Tenant_idTenant", referencedColumnName = "idTenant")}, inverseJoinColumns = {
            @JoinColumn(name = "Rental_idRental", referencedColumnName = "idRental")})
    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Rental> rentalList = new LinkedHashSet<>();

    @JoinColumn(name = "FK_user_name", referencedColumnName = "user_name")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user = new User();

    public Tenant() {
    }

    public Tenant(String name, Integer phone, String job) {
        this.name = name;
        this.phone = phone;
        this.job = job;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTenant", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "phone")
    private Integer phone;

    @Size(max = 45)
    @Column(name = "job", length = 45)
    private String job;
    public User getUser() {
        return user;
    }

    public void addUser(User user){
        this.user = user;
    }
    public void setUser(User user) {
        this.user = user;
    }



    public List<String> getRentalsAsStrings() {
        if (rentalList.isEmpty()) {
            return null;
        }
        List<String> rentalsAsStrings = new ArrayList<>();
        rentalList.forEach((rental) -> {
            rentalsAsStrings.add(rental.getId().toString());
        });
        return rentalsAsStrings;
    }

    public Set<Rental> getRentalList() {
        return rentalList;
    }

    public void setRentalList(Set<Rental> rentalList) {
        this.rentalList = rentalList;
    }

    public void addRental(Rental rental) {
        rental.getTenants().add(this);
        this.rentalList.add(rental);
    }
  public void removeRental(Rental rental) {
        rental.getTenants().remove(this);
        this.rentalList.remove(rental);
    }



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

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }


}