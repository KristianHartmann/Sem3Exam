package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.RentalDto;
import dtos.UserDto;
import facades.RentalFacade;
import facades.TenantFacade;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("rental")
public class RentalEndpint {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();
    RentalFacade rentalFacade = RentalFacade.getRentalFacade(em.getEntityManagerFactory());

    @POST
    @Path("rental")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getRental(String prompt) {
        JsonObject json = JsonParser.parseString(prompt).getAsJsonObject();
        Integer rentalId = json.get("rentalId").getAsInt();
            try {
                RentalDto rentalDto = rentalFacade.getRental(rentalId);
                return Response.ok(GSON.toJson(rentalDto)).build();
            } catch (Exception e) {
                return Response.noContent().build();
        }
    }



    @POST
    @Path("tentantRental")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getAllRentals(String prompt) {
        JsonObject json = JsonParser.parseString(prompt).getAsJsonObject();
        String username = json.get("username").getAsString();
            try {
                List<RentalDto> rentalDto = rentalFacade.getRentalsByTenant(username);
                return Response.ok(GSON.toJson(rentalDto)).build();
            } catch (Exception e) {
                return Response.noContent().build();
        }
    }

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllRentals(){
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();
        List<RentalDto> rentalDtos = rentalFacade.getAllRentals();
        try {
            return Response.ok(GSON.toJson(rentalDtos)).build();
        } finally {
            em.close();
        }
    }


}
