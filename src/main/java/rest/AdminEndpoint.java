package rest;

import com.google.gson.*;
import dtos.*;
import entities.House;
import entities.Rental;
import facades.AdminFacade;
import facades.HouseFacade;
import facades.RentalFacade;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Path("admin")
public class AdminEndpoint {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();
    AdminFacade adminFacade = AdminFacade.getAdminFacade(em.getEntityManagerFactory());
    HouseFacade houseFacade = HouseFacade.getHouseFacade(em.getEntityManagerFactory());
    RentalFacade rentalFacade = RentalFacade.getRentalFacade(em.getEntityManagerFactory());

    @Context
    SecurityContext securityContext;

    @POST
    @RolesAllowed("admin")
    @Path("tenantsInHouse")
    @Produces({MediaType.APPLICATION_JSON})
    public Response adminGetTenantsInHouse(String prompt) {
        JsonObject json = JsonParser.parseString(prompt).getAsJsonObject();
        Integer houseId = json.get("houseId").getAsInt();
        TenantsInHouseDto tenantsInHouseDto =  adminFacade.getTenantsInHouse(houseId);

        if(tenantsInHouseDto == null){
            return Response.noContent().build();
        }
        try {
            return Response.ok(GSON.toJson(tenantsInHouseDto)).build();
        } finally {
            em.close();
        }
    }

    @POST
    @RolesAllowed("admin")
    @Path("createRental")
    @Produces({MediaType.APPLICATION_JSON})
    public Response adminCreateRental(String prompt) throws AuthenticationException {
        List<String> tenantNames = new ArrayList<>();
        JsonObject json = JsonParser.parseString(prompt).getAsJsonObject();
        String startDate = json.get("startDate").getAsString();
        String endDate = json.get("endDate").getAsString();
        Integer priceAnnual = json.get("priceAnnual").getAsInt();
        Integer deposit = json.get("deposit").getAsInt();
        Integer contactPersonId = json.get("contactPersonId").getAsInt();
        Integer houseId = json.get("houseId").getAsInt();
        JsonArray tenantNamesJson = json.get("tenantNames").getAsJsonArray();

        if (tenantNamesJson != null) {
            for (int i=0;i<tenantNamesJson.size();i++){
                tenantNames.add(String.valueOf(tenantNamesJson.get(i)));
            }
        }

        for (int i = 0; i < tenantNames.size(); i++) {
            String placeHolder = tenantNames.get(i).replace("\"", "");
            tenantNames.set(i, placeHolder);
        }
        Rental rental = adminFacade.createRental(startDate, endDate, priceAnnual, deposit, contactPersonId, houseId, tenantNames);

        if(rental == null){
            return Response.serverError().build();
        }
        RentalDto rentalDto = rentalFacade.getRentalDto(rental);
        try {
            return Response.ok(GSON.toJson(rentalDto)).build();
        } finally {
            em.close();
        }
    }

    @POST
    @RolesAllowed("admin")
    @Path("createHouse")
    @Produces({MediaType.APPLICATION_JSON})
    public Response adminCreateHouse(String prompt) throws AuthenticationException {
        JsonObject json = JsonParser.parseString(prompt).getAsJsonObject();
        Integer cityinfoId = json.get("cityinfoId").getAsInt();
        String address = json.get("address").getAsString();
        Integer numberOfRooms = json.get("numberOfRooms").getAsInt();

        House house = houseFacade.createHouse(cityinfoId, address, numberOfRooms);

        if(house == null){
            return Response.serverError().build();
        }
        HouseDto houseDto= new HouseDto(house.getAddress(), house.getNumberOfRooms(), new CityInfoDto(house.getCityinfo().getCityname(), house.getCityinfo().getZip()));
        try {
            return Response.ok(GSON.toJson(houseDto)).build();
        } finally {
            em.close();
        }
    }

    @PATCH
    @Path("updateRental")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    public Response updateRental(String prompt) {
        JsonObject json = JsonParser.parseString(prompt).getAsJsonObject();
        Integer rentalId = json.get("rentalId").getAsInt();
        String startDate = json.get("startDate").getAsString();
        String endDate = json.get("endDate").getAsString();
        Integer priceAnnual = json.get("priceAnnual").getAsInt();
        Integer deposit = json.get("deposit").getAsInt();
        Integer contactPersonId = json.get("contactPersonId").getAsInt();
        Integer houseId = json.get("houseId").getAsInt();

            try {
                Rental rental = adminFacade.updateRentalInformation(rentalId, startDate, endDate, priceAnnual, deposit, contactPersonId, houseId);
                RentalDto rentalDto = rentalFacade.getRentalDto(rental);
                return Response.ok(GSON.toJson(rentalDto)).build();
            } catch (Exception e) {
                return Response.noContent().build();
            }
    }
    @PATCH
    @Path("updateTenants")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    public Response updateTenantsforRental(String prompt) {

        JsonObject json = JsonParser.parseString(prompt).getAsJsonObject();
        List<String> tenantNames = new ArrayList<>();
        Integer rentalId = json.get("rentalId").getAsInt();
        JsonArray tenantNamesJson = json.get("tenantNames").getAsJsonArray();

        if (tenantNamesJson != null) {
            for (int i=0;i<tenantNamesJson.size();i++){
                tenantNames.add(String.valueOf(tenantNamesJson.get(i)));
            }
        }

        for (int i = 0; i < tenantNames.size(); i++) {
            String placeHolder = tenantNames.get(i).replace("\"", "");
            tenantNames.set(i, placeHolder);
        }

        try {
            Rental rental = adminFacade.changeTenantsForRental(tenantNames, rentalId);
            RentalDto rentalDto = rentalFacade.getRentalDto(rental);
            return Response.ok(GSON.toJson(rentalDto)).build();
        } catch (Exception e) {
            return Response.noContent().build();
        }
    }



    @DELETE
    @Path("removeRental")
    @RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public boolean removeRental(String prompt) {
        JsonObject json = JsonParser.parseString(prompt).getAsJsonObject();
        Integer rentalId = json.get("rentalId").getAsInt();
        boolean result = adminFacade.removeRental(rentalId);
        try {
            if(result){
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }


}
