package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.UserDto;
import entities.Tenant;
import security.entities.Role;
import security.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */

public class UserFacade {

    private static EntityManagerFactory emf;

    private static UserFacade instance;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private UserFacade() {
    }

    /**
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }


    public boolean remove(String user_name) {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, user_name);
        if(user == null){
            return false;
        }
        TenantFacade tenantFacade = TenantFacade.getTenantFacade(em.getEntityManagerFactory());
        Tenant tenant = tenantFacade.getTenantByUsername(user_name);

        if(tenant != null){
            em.remove(em.merge(tenant));
        }

        try {
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
  public List<UserDto> getAllUsers() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
            List<UserDto> userDtos = new ArrayList<>();
            for (User user : users) {
                userDtos.add(new UserDto(user.getUserName(), user.getRolesAsStrings().get(0)));
            }
            em.getTransaction().commit();
            return userDtos;
        } catch (Exception e) {
            return null;
        }
    }

    public User getVerifiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }
    public UserDto getUser(String username) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null) {
                throw new AuthenticationException("User doesn't exist");
            }
        } finally {
            em.close();
        }
        UserDto userDto = new UserDto(user.getUserName(), user.getRolesAsStrings().get(0));
        return userDto;
    }

        public User createUser(String name, String password, Role role) {
        User user = new User(name, password);
        user.addRole(role);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        return user;
    }


    public static boolean checkUserExists(String username) {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();
        TypedQuery<User> query = em.createQuery("select u from User u", User.class);
        for (User u : query.getResultList()) {
            if (u.getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException, AuthenticationException {
        EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactoryForTest();
       UserFacade FACADE =  UserFacade.getUserFacade(EMF);

       FACADE.createUser("Test Name", "Test PW", new Role("admin"));
       User userExists = FACADE.getVerifiedUser("Test Name", "Test PW");
       boolean test = FACADE.checkUserExists("Test Name");
        System.out.printf("test" + test);
        System.out.printf("User" + userExists.getUserName());
        System.out.printf("User" + userExists.getUserPass());
    }
}
