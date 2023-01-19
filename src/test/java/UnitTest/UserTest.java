package UnitTest;

import dtos.UserDto;
import facades.Populator;
import facades.UserFacade;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import security.entities.Role;
import security.entities.User;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {



    private static EntityManagerFactory emf;
    Populator populator = new Populator();
    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
    }
    @BeforeEach
    public void setUp() {
        populator.clearDatabase();
        populator.populateTestDatabase();
    }

//        @Test
//        public void testGetAllUser() {
//            EMF_Creator.startREST_TestWithDB();
//            UserFacade userFacade =  UserFacade.getUserFacade(emf);
//            List<UserDto> userDtoList = userFacade.getAllUsers();
//            assertEquals("Hess", userDtoList.get(0).getUsername());
//            assertEquals("Kristian", userDtoList.get(1).getUsername());
//
//            assertEquals("user", userDtoList.get(0).getRole());
//            assertEquals("admin", userDtoList.get(1).getRole());
//
//        }
        @Test
        public void testGetUser() throws AuthenticationException {
            EMF_Creator.startREST_TestWithDB();
            UserFacade userFacade =  UserFacade.getUserFacade(emf);

            UserDto userDto = userFacade.getUser("Hess");

            assertEquals("Hess", userDto.getUsername());
            assertEquals("user", userDto.getRole());

        }
        @Test
        public void testRemoveUser() throws AuthenticationException {
            EMF_Creator.startREST_TestWithDB();
            UserFacade userFacade =  UserFacade.getUserFacade(emf);

            // Create a new role
            boolean test = userFacade.remove("Hess");

            // Assert that the user's name and password are correct
            assertEquals(true, test);
//

        }




}
