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
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserEndpointTest {
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
            populator.populateTestDatabase();

        } finally {
            em.close();
        }

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

    @Test
    public void testCreateUser() {
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
    }

    @Test
    public void restTestGetAllUsers() {
        login("Kristian", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/user/all").then()
                .statusCode(200);
    }
    @Test
    public void restTestRemoveUser() {
        login("Kristian", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .body("{\"username\":\"HESS\"}")
                .when()
                .post("/user/remove")
                .then()
                .statusCode(200);
    }
    @Test
    public void restTestCreateUser() {
        login("Kristian", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .body("{\n" +
                        "    \"username\":\"NewUser\",\n" +
                        "    \"password\":\"test\",\n" +
                        "    \"role\":\"user\"\n" +
                        "}")
                .when()
                .post("/user/create")
                .then()
                .statusCode(200);
    }


}
