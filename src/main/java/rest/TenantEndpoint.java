package rest;

import com.google.gson.*;
import facades.TenantFacade;
import facades.UserFacade;
import security.entities.Role;
import security.entities.User;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
@Path("tenant")
public class TenantEndpoint {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();
    UserFacade userFacade = UserFacade.getUserFacade(em.getEntityManagerFactory());
    TenantFacade tenantFacade = TenantFacade.getTenantFacade(em.getEntityManagerFactory());
    @Context
    SecurityContext securityContext;


    @POST
    @Path("create")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String createTenant(String prompt) {

        JsonObject json = JsonParser.parseString(prompt).getAsJsonObject();
        String username = json.get("username").getAsString();
        String password = json.get("password").getAsString();
        String name = json.get("name").getAsString();
        Integer phone = json.get("phone").getAsInt();
        String job = json.get("job").getAsString();
        JsonElement roleElement = json.get("role");
        String role;
        if(roleElement.isJsonNull()){
            role = "user";
        } else {
            role = roleElement.getAsString();
        }

        if (userFacade.checkUserExists(username)){
            return "user already exists";
        }
        try {
            User user = new User(username, password);
            tenantFacade.createTenant(user, name, phone, job);
            return "created tenant";
        } catch (Exception e) {
            return "something went wrong";
        }
    }
}
