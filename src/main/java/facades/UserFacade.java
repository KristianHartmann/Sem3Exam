package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import security.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

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


    public boolean remove(int id) {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, id);
        try {
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
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

    //    public User create(String name, String password, String faceitnickname) {
//        FaceitUser faceitUser = new FaceitUser(faceitnickname, "idlalala13567");
//        CommunityPlayer communityPlayer = new CommunityPlayer(faceitUser);
//        Community community = new Community("abekattene");
//        community.addCommunityPlayer(communityPlayer);
//        User user = new User(name, password, faceitUser);
//        user.addCommunity(community);
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        em.persist(user);
//        em.getTransaction().commit();
//        return user;
//    }

    public static String getFaceitId(String faceitnickname) throws IOException, IndexOutOfBoundsException {
        URL url = new URL("https://open.faceit.com/data/v4/search/players?nickname=" + faceitnickname + "&game=csgo&offset=0&limit=1");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET");

        httpConn.setRequestProperty("accept", "application/json");
        httpConn.setRequestProperty("Authorization", "Bearer ad0be573-6f92-405a-a9c5-e5e59a062061");

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        return json.get("items").getAsJsonArray().get(0).getAsJsonObject().get("player_id").getAsString();
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

    public static void main(String[] args) throws IOException {
        String test = UserFacade.getFaceitId("DirtyHarty");
        System.out.println(test);
    }
}
