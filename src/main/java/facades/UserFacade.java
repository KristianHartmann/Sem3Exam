package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.UserDto;
import security.entities.Role;
import security.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.ext.Provider;

import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author lam@cphbusiness.dk
 */
@Provider
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
                userDtos.add(new UserDto(user, user.getRolesAsStrings().get(0)));
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
        UserDto userDto = new UserDto(user, user.getRolesAsStrings().get(0));
        return userDto;
    }

        public User create(String name, String password, Role role) {
        User user = new User(name, password);
        user.addRole(role);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
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

       FACADE.create("Test Name", "Test PW", new Role("admin"));
       User userExists = FACADE.getVerifiedUser("Test Name", "Test PW");
       boolean test = FACADE.checkUserExists("Test Name");
        System.out.printf("test" + test);
        System.out.printf("User" + userExists.getUserName());
        System.out.printf("User" + userExists.getUserPass());
    }
}
