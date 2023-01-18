package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.*;
import entities.Rental;
import entities.Tenant;
import entities.TenantHasRental;
import rest.AdminEndpoint;
import security.entities.User;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class RentalFacade {
    private static EntityManagerFactory emf;
    private static RentalFacade instance;
    EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();



    public static RentalFacade getRentalFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RentalFacade();
        }
        return instance;
    }
    public RentalDto getRental(Integer rentalId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Rental> query = em.createQuery("SELECT r FROM Rental r JOIN r.contactPerson cp JOIN r.house h JOIN h.cityinfo c WHERE r.id = :rentalId", Rental.class);
            query.setParameter("rentalId", rentalId);
            Rental rental = query.getSingleResult();
            ContactPersonDto contactPersonDto= new ContactPersonDto(rental.getContactPerson().getName(), rental.getContactPerson().getPhone());
            HouseDto houseDto= new HouseDto(rental.getHouse().getAddress(), rental.getHouse().getNumberOfRooms(), new CityInfoDto(rental.getHouse().getCityinfo().getCityname(), rental.getHouse().getCityinfo().getZip()));
            RentalDto rentalDto = new RentalDto(rentalId,rental.getDeposit(), rental.getEndDate(), rental.getPriceAnnual(), rental.getStartDate(), contactPersonDto, houseDto);
            em.getTransaction().commit();
            return rentalDto;
        } catch (Exception e) {
            return null;
        }
    }

    public List<RentalDto> getAllRentals() {
        EntityManager em = emf.createEntityManager();
        List<RentalDto> rentalDtos = new ArrayList<>();
        try {
            em.getTransaction().begin();
            List<Rental> rentalList = em.createQuery("SELECT r FROM Rental r", Rental.class).getResultList();
            for (Rental rental : rentalList) {
               RentalDto rentalDto = getRentalDto(rental);
                rentalDtos.add(rentalDto);
            }
            em.getTransaction().commit();
            return rentalDtos;
        } catch (Exception e) {
            return null;
        }
    }

    public Rental rentalHouseExists(Integer houseId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Rental> query = em.createQuery("SELECT r FROM Rental r where r.house.id = :houseId", Rental.class)
                            .setParameter("houseId", houseId);
            Rental rental = query.getSingleResult();
            em.getTransaction().commit();
            return rental;
        } catch (Exception e) {
            return null;
        }
    }


    public List<RentalDto> getRentalsByTenant(Integer tenantId) {
        EntityManager em = emf.createEntityManager();
        List<RentalDto> rentalDtos = new ArrayList<>();

        try {
            em.getTransaction().begin();
            Tenant tenant = em.find(Tenant.class, tenantId);
            for (Rental rental : tenant.getRentalList()) {
                rentalDtos.add(getRental(rental.getId()));
            }
            em.getTransaction().commit();
            return rentalDtos;
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();
        RentalFacade facade = RentalFacade.getRentalFacade(em.getEntityManagerFactory());
//        RentalDto dto = facade.getRental(2);
        List<RentalDto> dto = facade.getRentalsByTenant(1);
        System.out.println("Yay");
    }

    public RentalDto getRentalDto(Rental rental) {
        ContactPersonDto contactPersonDto= new ContactPersonDto(rental.getContactPerson().getName(), rental.getContactPerson().getPhone());
        HouseDto houseDto= new HouseDto(rental.getHouse().getAddress(), rental.getHouse().getNumberOfRooms(), new CityInfoDto(rental.getHouse().getCityinfo().getCityname(), rental.getHouse().getCityinfo().getZip()));
        return new RentalDto(rental.getId(), rental.getDeposit(), rental.getEndDate(), rental.getPriceAnnual(), rental.getStartDate(), contactPersonDto, houseDto);
    }
}
