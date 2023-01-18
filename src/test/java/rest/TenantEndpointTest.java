package rest;


import entities.Tenant;
import facades.Populator;
import facades.TenantFacade;
import security.entities.User;
import security.entities.Role;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
public class TenantEndpointTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/Sem3Exam_war_exploded/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();
    TenantFacade tenantFacade = TenantFacade.getTenantFacade(em.getEntityManagerFactory());


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
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        Populator populator = new Populator();
        try {
            populator.clearDatabase();
           populator.populateTestDatabase();;
        } finally {
            em.close();
        }

    }
    private static String securityToken;
    @Test
    public void serverIsRunning() {
        given().when().get("/info").then().extract().path("token");
    }

    @Test
    public void testCreateTenant() {
        String tenantJson = "{\n" +
                "    \"username\":\"testuser\",\n" +
                "    \"password\":\"test\",\n" +
                "    \"name\":\"testname\",\n" +
                "    \"phone\" : 11111111,\n" +
                "    \"job\" : \"testjob\",\n" +
                "    \"role\":\"admin\"\n" +
                "}";
        given()
                .contentType("application/json")
                .body(tenantJson)
                .when()
                .post("/tenant/create")
                .then()
                .statusCode(200);

        // Check that the tenant was created in the database
        Tenant tenant = tenantFacade.getTenantByUsername("testuser");
        assertNotNull(tenant);
        assertEquals("testname", tenant.getName());
        assertEquals(11111111, tenant.getPhone().intValue());
        assertEquals("testjob", tenant.getJob());
    }


}
