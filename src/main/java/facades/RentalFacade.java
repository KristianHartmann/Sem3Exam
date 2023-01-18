package facades;

import dtos.*;
import entities.Rental;
import security.entities.User;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
            RentalDto rentalDto = new RentalDto(rental.getDeposit(), rental.getEndDate(), rental.getPriceAnnual(), rental.getStartDate(), contactPersonDto, houseDto);
            em.getTransaction().commit();
            return rentalDto;
        } catch (Exception e) {
            return null;
        }
    }


}
