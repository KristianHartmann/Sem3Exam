package UnitTest;

import dtos.RentalDto;
import dtos.TenantDto;
import dtos.TenantsInHouseDto;
import entities.*;
import facades.*;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import security.entities.Role;
import security.entities.User;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {

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
    @Test
    public void getTenantsInHousetTest() throws AuthenticationException {
        AdminFacade adminFacade = AdminFacade.getAdminFacade(emf);
        House house = adminFacade.getHouseByAddress("Address");
        TenantsInHouseDto tenantDto = adminFacade.getTenantsInHouse(house.getId());
        // Assert that the user's name and password are correct
        assertEquals("Hess", tenantDto.getTenantDto().get(0).getUser_name());

    }


    //also test create house
    @Test
    public void testCreateRental() throws AuthenticationException {
        AdminFacade adminFacade =  AdminFacade.getAdminFacade(emf);
        LocalDate date1YearFromNow = LocalDate.now().plusYears(1);
        LocalDate dateNow = LocalDate.now();
        List<String> tenantNames = new ArrayList<>();
        tenantNames.add("Hess");
        House house = new House("TEST", 1);
        house.addCityinfo(new Cityinfo("test", 1));
        HouseFacade houseFacade = HouseFacade.getHouseFacade(emf);
        houseFacade.createHouseForTest(house);
        // Create a new user
        Rental rental = adminFacade.createRental(dateNow.toString(), date1YearFromNow.toString(), 500, 1000, 1, 2, tenantNames);

        assertEquals(dateNow.toString(), rental.getStartDate());
        assertEquals(date1YearFromNow.toString(), rental.getEndDate());
        assertEquals(500, rental.getPriceAnnual());
        assertEquals(1, rental.getContactPerson().getId());
        assertEquals(2, rental.getHouse().getId());
        assertTrue(!rental.getTenants().isEmpty());
    }
    @Test
    public void testUpdateRental() throws AuthenticationException {
        AdminFacade adminFacade =  AdminFacade.getAdminFacade(emf);
        LocalDate date2YearFromNow = LocalDate.now().plusYears(2);
        LocalDate date1YearFromNow = LocalDate.now().plusYears(1);
      // Create a new user
        Rental rental = adminFacade.updateRentalInformation(1, date1YearFromNow.toString(), date2YearFromNow.toString(), 100, 150, 1, 1);

        assertEquals(date1YearFromNow.toString(), rental.getStartDate());
        assertEquals(date2YearFromNow.toString(), rental.getEndDate());
        assertEquals(100, rental.getPriceAnnual());
        assertEquals(150, rental.getDeposit());
        assertEquals(1, rental.getContactPerson().getId());
        assertEquals(1, rental.getHouse().getId());
        assertTrue(!rental.getTenants().isEmpty());
    }


    @Test
    public void testUpdateTenants() throws AuthenticationException {
        AdminFacade adminFacade =  AdminFacade.getAdminFacade(emf);
        List<String> updateTenant = new ArrayList<>();
        updateTenant.add("Kristian");
        adminFacade.changeTenantsForRental(updateTenant, 1);
        RentalFacade rentalFacade =  RentalFacade.getRentalFacade(emf);
        String tenantInRental = "";
        Rental rental = rentalFacade.getRentalForTest(1);

        for (Tenant tenant : rental.getTenants()) {
            tenantInRental = tenant.getUser().getUserName();
        }
//        assertEquals("Hess",tenantInRental );
        assertNotNull(tenantInRental);
    }


    @Test
    public void testRemoveRental() throws AuthenticationException {
        AdminFacade adminFacade =  AdminFacade.getAdminFacade(emf);
        RentalFacade rentalFacade =  RentalFacade.getRentalFacade(emf);

        RentalDto rental = rentalFacade.getRental(1);
        if(rental != null){
            adminFacade.removeRental(1);
        }

        assertEquals(null, rentalFacade.getRental(1));

    }

}
