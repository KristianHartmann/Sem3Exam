package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.UserDto;
import entities.Cityinfo;
import entities.House;
import entities.Rental;
import entities.Tenant;
import security.entities.User;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HouseFacade {

    private static EntityManagerFactory emf;
    private static HouseFacade instance;

    CityInfoFacade cityInfoFacade = CityInfoFacade.getCityinfoFacade(emf);


    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private HouseFacade() {
    }

    public static HouseFacade getHouseFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HouseFacade();
        }
        return instance;
    }

    public House getHouse(Integer houseId) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        House house;
        try {
            house = em.find(House.class, houseId);
            if (house == null) {
                throw new AuthenticationException("House doesn't exist");
            }
        } finally {
            em.close();
        }
        return house;
    }

    public House createHouse(Integer cityinfoId, String address, Integer numberOfRooms) throws AuthenticationException {
        Cityinfo cityinfo = cityInfoFacade.getCityInfo(cityinfoId);
        House house = new House(address, numberOfRooms);
        house.addCityinfo(cityinfo);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(house);
        em.getTransaction().commit();
        return house;
    }

    public static void main(String[] args) throws AuthenticationException {
            EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();
            HouseFacade facade = HouseFacade.getHouseFacade(em.getEntityManagerFactory());
            facade.createHouse(2, "Address2", 1);
            System.out.println("Yay");
        }
    }

