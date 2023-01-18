package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.*;
import entities.*;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.*;

public class AdminFacade {
    private static EntityManagerFactory emf;
    private static AdminFacade instance;

    EntityManager em = emf.createEntityManager();
    TenantFacade tenantFacade = TenantFacade.getTenantFacade(em.getEntityManagerFactory());
    HouseFacade houseFacade = HouseFacade.getHouseFacade(em.getEntityManagerFactory());
    RentalFacade rentalFacade = RentalFacade.getRentalFacade(em.getEntityManagerFactory());
    ContactPersonFacade contactPersonFacade = ContactPersonFacade.getContactPersonFacade(em.getEntityManagerFactory());
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private AdminFacade() {
    }

    public static AdminFacade getAdminFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AdminFacade();
        }
        return instance;
    }
    public TenantsInHouseDto getTenantsInHouse(Integer houseID) {

        try {
            em.getTransaction().begin();
            House house = em.find(House.class, houseID);
            Rental rental = rentalFacade.rentalHouseExists(houseID);
            Set<Tenant> tenantList = rental.getTenants();
            CityInfoDto cityInfoDto = new CityInfoDto(house.getCityinfo().getCityname(), house.getCityinfo().getZip());
            HouseDto houseDto = new HouseDto(house.getAddress(), house.getNumberOfRooms(), cityInfoDto);
            List<TenantDto> tenantDtoList = new ArrayList<>();
            for (Tenant tenant : tenantList) {
                tenantDtoList.add(new TenantDto(tenant.getId(), tenant.getName(), tenant.getPhone(), tenant.getJob(), tenant.getUser().getUserName()));
            }
            TenantsInHouseDto tenantsInHouseDto = new TenantsInHouseDto(houseDto, tenantDtoList);
            em.getTransaction().commit();
            return tenantsInHouseDto;
        } catch (Exception e) {
            return null;
        }
    }
    public Rental createRental(String startDate, String endDate, Integer priceAnnual, Integer deposit, Integer contactPersonID, Integer houseId, List<String> tenantNames) throws AuthenticationException {

        House house = houseFacade.getHouse(houseId);
        if(rentalFacade.rentalHouseExists(houseId) != null){
            return null;
        }
        ContactPerson contactPerson = contactPersonFacade.getContactPerson(contactPersonID);
        List<Tenant> tenantList = new ArrayList<>();
        Rental rental = new Rental(startDate, endDate, priceAnnual, deposit);
        rental.addContactPerson(contactPerson);
        rental.addHouse(house);
        for (String tenant : tenantNames) {
            tenantList.add(tenantFacade.getTenantByUsername(tenant));
        }

        em.getTransaction().begin();
        em.merge(rental);
        for (Tenant tenant : tenantList) {
            tenant.addRental(rental);
            em.merge(tenant);
        }
        em.getTransaction().commit();
        return rental;
    }


    public boolean changeTenantsForRental(List<String> tenantNames, Integer rentalID) {
        Set<Tenant> tenantList = new LinkedHashSet<>();

        try {
            em.getTransaction().begin();
            Rental rental = em.find(Rental.class, rentalID);
            if (rental != null) {
                // remove existing relationships between the rental and the tenants
                Set<Tenant> currentTenants = rental.getTenants();
                Set<Tenant> oldTenants = new HashSet<>(currentTenants);
                for (Tenant tenant : currentTenants) {
                    TenantHasRentalId id = new TenantHasRentalId();
                    id.setTenantIdtenant(tenant.getId());
                    id.setRentalIdrental(rental.getId());
                    TenantHasRental tenantHasRental = em.find(TenantHasRental.class, id);
                    em.remove(tenantHasRental);
                }
                rental.removeTenants();
                // add new relationships between the rental and the tenants
                for (String tenant : tenantNames) {
                    Tenant t = tenantFacade.getTenantByUsername(tenant);
                    if (t != null) {
                        tenantList.add(t);
                        TenantHasRentalId id = new TenantHasRentalId();
                        id.setTenantIdtenant(t.getId());
                        id.setRentalIdrental(rental.getId());
                        TenantHasRental tenantHasRental = new TenantHasRental();
                        tenantHasRental.setId(id);
                        em.persist(tenantHasRental);
                        rental.addTenant(t);
                    }

                }
                em.merge(rental);
                em.getTransaction().commit();
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    public boolean removeRental(Integer rentalId) {
        try{
            Rental rental = em.find(Rental.class, rentalId);
            em.getTransaction().begin();
            em.remove(rental);
            em.getTransaction().commit();
            return true;
        } catch(Exception e){
            return false;
        }

    }

    public static void main(String[] args) throws AuthenticationException {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();
        AdminFacade facade = AdminFacade.getAdminFacade(em.getEntityManagerFactory());
        LocalDate date1YearFromNow = LocalDate.now().plusYears(1);
        LocalDate dateNow = LocalDate.now();
        List<String> tenantNames = new ArrayList<>();
        tenantNames.add("Hess");
        tenantNames.add("KristianTenant");
        facade.createRental(dateNow.toString(), date1YearFromNow.toString(), 1000, 10000, 2, 2, tenantNames);
//        facade.changeTenantsForRental(tenantNames, 9);
        //        facade.removeRental(2);
        System.out.println("Yay");
    }


}
