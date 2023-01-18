package facades;

import dtos.RentalDto;
import entities.Rental;
import entities.Tenant;
import security.entities.Role;
import security.entities.User;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.List;

public class TenantFacade {

    private static EntityManagerFactory emf;
    private static TenantFacade instance;
    EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();
    UserFacade userFacade = UserFacade.getUserFacade(em.getEntityManagerFactory());



    public static TenantFacade getTenantFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TenantFacade();
        }
        return instance;
    }

    public Tenant createTenant(User user, String name, Integer phone, String job) throws AuthenticationException {
        Tenant tenant = new Tenant(name, phone, job);
        tenant.addUser(user);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(tenant);
        em.getTransaction().commit();
        return tenant;
    }
    public Tenant getTenantByUsername(String username) {
        EntityManager em = emf.createEntityManager();
        try {
            Tenant tenant = (Tenant) em.createQuery("SELECT t FROM Tenant t JOIN t.user u WHERE u.userName = :username")
                    .setParameter("username", username)
                    .getSingleResult();
            return tenant;
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }

    public boolean addRentalToTenant(Tenant tenant, Rental rental){
        try{
            tenant.addRental(rental);
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(tenant);
            em.getTransaction().commit();
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
