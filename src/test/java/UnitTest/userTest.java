package UnitTest;

import facades.UserFacade;

import org.junit.jupiter.api.Test;
import security.entities.Role;
import security.entities.User;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class userTest {



        EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactoryForTest();
        UserFacade FACADE =  UserFacade.getUserFacade(EMF);


        @Test
        public void testCreate() {
            // Create a new role
            Role role = new Role("admin");

            // Create a new user
            User user = FACADE.create("John", "password", role);

            // Assert that the user's name and password are correct
            assertEquals("John", user.getUserName());

            // Assert that the user has the correct role
            assertEquals(1, user.getRoleList().size());
            assertEquals("admin", user.getRolesAsStrings().get(0));
        }


}
