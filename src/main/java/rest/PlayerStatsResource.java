//package rest;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import java.io.IOException;
//import javax.persistence.EntityManagerFactory;
//import javax.ws.rs.*;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.UriInfo;
//import javax.ws.rs.core.MediaType;
//
//import controllers.PlayerStatsController;
//import dto.PlayerStatsDto;
//import entities.PlayerStats;
//import utils.EMF_Creator;
//import javax.ws.rs.Path;
//
//@Path("faceit")
//public class PlayerStatsResource {
//
//    @Context
//    private UriInfo context;
//    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
//
//    private static final PlayerStatsController CONTROLLER = new PlayerStatsController();
//
//
////    @GET
////    @Path("{name}")
////    @Produces(MediaType.APPLICATION_JSON)
////    @Consumes(MediaType.APPLICATION_JSON)
////    public Response getPlayer(@PathParam("name") String name) throws IOException {
////        PlayerStatsDto playerStatsDto = CONTROLLER.fetchIdImgEloLvl(name);
////        PlayerStats player = CONTROLLER.mapPlayerStatsDtoToPlayerStats(playerStatsDto);
////        return Response.ok().entity(gson.toJson(player)).build();
////    }
//
//}
