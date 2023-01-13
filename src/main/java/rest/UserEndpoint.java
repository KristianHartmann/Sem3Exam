package rest;

import com.google.gson.*;
import dtos.UserDto;
import facades.UserFacade;
import security.entities.Role;
import security.entities.User;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("user")
public class UserEndpoint {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

//    @GET
//    @Path("all")
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response getAllUsers(){
//        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();
//        List<UserDto> userDtos = new ArrayList<>();
//        try {
//            List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
//            for (User user : users) {
//                userDtos.add(new UserDto(user));
//            }
//            return Response.ok(GSON.toJson(userDtos)).build();
//        } finally {
//            em.close();
//        }
//    }

//    @POST
//    @Path("create")
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_JSON})
//    public String createUser(String prompt) {
//        User user;
//        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();
//        JsonObject json = JsonParser.parseString(prompt).getAsJsonObject();
//        String username = json.get("username").getAsString();
//        String password = json.get("password").getAsString();
//        if (UserFacade.checkUserExists(username)){
//            return "user already exists";
//        }
//        if(json.has("faceitnickname")){
//            try {
//                String faceitId = UserFacade.getFaceitId(json.get("faceitnickname").getAsString());
////                FaceitUser faceitUser = new FaceitUser(json.get("faceitnickname").getAsString(), faceitId);
////                user = new User(username, password, faceitUser);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            } catch (IndexOutOfBoundsException e){
//                return "no faceit user with that nickname";
//            }
//        }else{
//            user = new User(username, password);
//        }
////        user.addRole(em.find(Role.class, 2));
//        try {
//            em.getTransaction().begin();
//            em.persist(user);
//            em.getTransaction().commit();
//            em.close();
//            return "true";
//        } catch (Exception e) {
//            return "false";
//        }
//    }

    @POST
    @Path("remove")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public boolean removeUser(String prompt) {
        JsonObject json = JsonParser.parseString(prompt).getAsJsonObject();
        int id = json.get("id").getAsInt();
        try {
            EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();
            User user = em.find(User.class, id);
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

//    @POST
//    @Path("setnickname")
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_JSON})
//    public String addFaceitNickname(String prompt) {
//        JsonObject json = JsonParser.parseString(prompt).getAsJsonObject();
//        String nickname = json.get("faceitnickname").getAsString();
//        int id = json.get("id").getAsInt();
//        try {
//            EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();
//            User user = em.find(User.class, id);
//            FaceitUser faceitUser = new FaceitUser(nickname, UserFacade.getFaceitId(nickname));
//            em.getTransaction().begin();
//            user.addFaceituser(faceitUser);
//            em.getTransaction().commit();
//            em.close();
//            return "true";
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (IndexOutOfBoundsException e){
//            return "no faceit user with that nickname";
//        }
//    }

}
