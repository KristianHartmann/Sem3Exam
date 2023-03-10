package rest;

import com.google.gson.*;
import dtos.UserDto;
import facades.UserFacade;
import security.entities.Role;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("user")
public class UserEndpoint {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();
    UserFacade facade = UserFacade.getUserFacade(em.getEntityManagerFactory());

    @Context
    SecurityContext securityContext;

    @GET
    @RolesAllowed({"admin", "user"})
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
        String role;
        if(json.get("role").getAsString() == null || !json.get("role").getAsString().isEmpty()){
            role = "user";
        } else {
            role = json.get("role").getAsString();
        }
        if (facade.checkUserExists(username)){
            return "user already exists";
        }
        try {
            facade.createUser(username, password, new Role(role));
            return "created user";
        } catch (Exception e) {
            return "something went wrong";
        }
    }
    @POST
    @Path("user")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    public Response getUser(String prompt) {
        JsonObject json = JsonParser.parseString(prompt).getAsJsonObject();
        String username = json.get("username").getAsString();
        if (facade.checkUserExists(username)){
            try {
                UserDto user = facade.getUser(username);
                return Response.ok(GSON.toJson(user)).build();
            } catch (Exception e) {
                return Response.noContent().build();
            }
        }
        return Response.noContent().build();
    }

    @POST
    @Path("remove")
    @RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public boolean removeUser(String prompt) {
        JsonObject json = JsonParser.parseString(prompt).getAsJsonObject();
        String userName = json.get("username").getAsString();
        try {
            if(facade.remove(userName)){
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("userTest")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("adminTest")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }


}
