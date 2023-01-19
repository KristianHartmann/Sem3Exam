package UnitTest;

import dtos.RentalDto;
import dtos.UserDto;
import facades.Populator;
import facades.RentalFacade;
import facades.UserFacade;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RentalTest {
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

//    @Test
//    public void testGetUser() throws AuthenticationException {
//
//        LocalDate date1YearFromNow = LocalDate.now().plusYears(1);
//        LocalDate dateNow = LocalDate.now();
//
//        EMF_Creator.startREST_TestWithDB();
//        RentalFacade rentalFacade =  RentalFacade.getRentalFacade(emf);
//
//        RentalDto rentalDto = rentalFacade.getRental(1);
//
//        assertEquals(dateNow.toString(), rentalDto.getStartDate());
//        assertEquals(date1YearFromNow.toString(), rentalDto.getEndDate());
//        assertEquals(1000, rentalDto.getPriceAnnual());
//        assertEquals(10000, rentalDto.getDeposit());
//
//    }
//    @Test
//    public void testGetTenantRentals() throws AuthenticationException {
//
//        LocalDate date1YearFromNow = LocalDate.now().plusYears(1);
//        LocalDate dateNow = LocalDate.now();
//
//        EMF_Creator.startREST_TestWithDB();
//        RentalFacade rentalFacade =  RentalFacade.getRentalFacade(emf);
//
//        List<RentalDto> rentalDto = rentalFacade.getRentalsByTenant("Hess");
//
//        assertEquals(dateNow.toString(), rentalDto.get(0).getStartDate());
//        assertEquals(date1YearFromNow.toString(), rentalDto.get(0).getEndDate());
//        assertEquals(1000, rentalDto.get(0).getPriceAnnual());
//        assertEquals(10000, rentalDto.get(0).getDeposit());
//
//    }
    @Test
    public void testGetAllRentals() throws AuthenticationException {

        LocalDate date1YearFromNow = LocalDate.now().plusYears(1);
        LocalDate dateNow = LocalDate.now();

        EMF_Creator.startREST_TestWithDB();
        RentalFacade rentalFacade =  RentalFacade.getRentalFacade(emf);

        List<RentalDto> rentalDto = rentalFacade.getAllRentals();

        assertEquals(dateNow.toString(), rentalDto.get(0).getStartDate());
        assertEquals(date1YearFromNow.toString(), rentalDto.get(0).getEndDate());
        assertEquals(1000, rentalDto.get(0).getPriceAnnual());
        assertEquals(10000, rentalDto.get(0).getDeposit());

    }


}
