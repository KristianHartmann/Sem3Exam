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
import java.util.ArrayList;
import java.util.List;

@Path("user")
public class UserEndpoint {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();
    UserFacade facade = UserFacade.getUserFacade(em.getEntityManagerFactory());
    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllUsers(){
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();
        List<UserDto> userDtos = facade.getAllUsers();
        try {
            return Response.ok(GSON.toJson(userDtos)).build();
        } finally {
            em.close();
        }
    }

    @POST
    @Path("create")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String createUser(String prompt) {

        JsonObject json = JsonParser.parseString(prompt).getAsJsonObject();
        String username = json.get("username").getAsString();
        String password = json.get("password").getAsString();
        String role = json.get("role").getAsString();
        if (facade.checkUserExists(username)){
            return "user already exists";
        }
        try {
            facade.create(username, password, new Role(role));
            return "true";
        } catch (Exception e) {
            return "false";
        }
    }

    @POST
    @Path("remove")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public boolean removeUser(String prompt) {
        JsonObject json = JsonParser.parseString(prompt).getAsJsonObject();
        String userName = json.get("username").getAsString();
        try {
            facade.remove(userName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
