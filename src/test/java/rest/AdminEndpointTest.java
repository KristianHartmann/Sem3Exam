package rest;

import facades.Populator;
import facades.TenantFacade;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class AdminEndpointTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/Sem3Exam_war_exploded/api";
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();
    TenantFacade tenantFacade = TenantFacade.getTenantFacade(em.getEntityManagerFactory());
    Populator populator = new Populator();


    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();

        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class

    @BeforeEach
    public void setUp() throws InterruptedException {
//        TimeUnit.SECONDS.sleep(2);
        populator.clearDatabase();
        populator.populateTestDatabase();
    }
    private static String securityToken;
    private static void login(String username, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", username, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    @Test
    public void serverIsRunning() {
        given().when().get("/info").then().extract().path("token");
    }

//    @Test
//    public void getRentalById() {
//        login("Kristian", "test");
//        given()
//                .contentType("application/json")
//                .accept(ContentType.JSON)
//                .body("{\"houseId\" : 1}")
//                .when()
//                .header("x-access-token", securityToken)
//                .post("/admin/tenantsInHouse")
//                .then()
//                .statusCode(204);
//    }

   //gives 400 for some reason?????
//    @Test
//    public void testRemoveRental() {;
//        String rentalJson = "{\n" +
//                "    \"rentalId\":\"1\",\n" +
//                "}";
//        login("Kristian", "test");
//        given()
//                .contentType("application/json")
//                .accept(ContentType.JSON)
//                .body(rentalJson)
//                .when()
//                .header("x-access-token", securityToken)
//                .delete("/admin/removeRental")
//                .then()
//                .statusCode(200);
//    }

//    @Test
//    public void testCreateRental() {
//        em.getTransaction().begin();
//        em.createQuery("delete from Rental ").executeUpdate();
//        em.createNativeQuery("ALTER TABLE Rental AUTO_INCREMENT = 1").executeUpdate();
//        em.close();
//        login("Kristian", "test");
//        given()
//                .contentType("application/json")
//                .body("{\n" +
//                        "    \"startDate\":\"2023-01-18\",\n" +
//                        "    \"endDate\":\"2024-01-18\",\n" +
//                        "    \"priceAnnual\": 2000,\n" +
//                        "    \"deposit\" : 20000,\n" +
//                        "    \"contactPersonId\" : 1,\n" +
//                        "    \"houseId\": 1,\n" +
//                        "    \"tenantNames\" : [ \"Hess\"]\n" +
//                        "}")
//                .when()
//                .header("x-access-token", securityToken)
//                .post("/admin/createRental")
//                .then()
//                .statusCode(200);
//    }

}
