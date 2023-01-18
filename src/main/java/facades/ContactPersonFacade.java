package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.ContactPerson;
import entities.House;
import security.errorhandling.AuthenticationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ContactPersonFacade {

    private static EntityManagerFactory emf;
    private static ContactPersonFacade instance;


    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private ContactPersonFacade() {
    }

    public static ContactPersonFacade getContactPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ContactPersonFacade();
        }
        return instance;
    }
    public ContactPerson getContactPerson(Integer contactPersonID) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        ContactPerson contactPerson;
        try {
            contactPerson = em.find(ContactPerson.class, contactPersonID);
            if (contactPerson == null) {
                throw new AuthenticationException("Contact Person doesn't exist");
            }
        } finally {
            em.close();
        }
        return contactPerson;
    }


}
