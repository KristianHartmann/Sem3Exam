package UnitTest;

import entities.*;
import facades.TenantFacade;
import facades.UserFacade;
import org.junit.jupiter.api.Test;
import security.entities.Role;
import security.entities.User;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TenantTest {



    private static EntityManagerFactory emf;


        @Test
        public void testTenantCreate() throws AuthenticationException {
            EMF_Creator.startREST_TestWithDB();
            emf = EMF_Creator.createEntityManagerFactoryForTest();
            TenantFacade tenantFacade =  TenantFacade.getTenantFacade(emf);

            User user = new User("Kristian", "test");
            Role userRole = new Role("user");
            user.addRole(userRole);
            // Create a new user
            Tenant tenant = tenantFacade.createTenant(user,"KristianTenant", 11111111, "Developer");

            // Assert that the user's name and password are correct
            assertEquals("KristianTenant", tenant.getName());
            assertEquals("Kristian", user.getUserName());
            // Assert that the user has the correct role
            assertEquals(1, user.getRoleList().size());
            assertEquals("user", user.getRolesAsStrings().get(0));
        }


}
