/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;


import entities.*;
import security.entities.Role;
import security.entities.User;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;

/**
 *
 * @author tha
 */
public class Populator {

    public void populateTestDatabase(){

        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactoryForTest();
        EntityManager em = emf.createEntityManager();

        try{


        LocalDate date1YearFromNow = LocalDate.now().plusYears(1);
        LocalDate dateNow = LocalDate.now();
        ContactPerson contactPerson = new ContactPerson("ContactPersonName", 11111111);
        Cityinfo cityinfo = new Cityinfo("Nordhavn", 2100);
        House house = new House("Address",  2);
        Rental rental = new Rental(dateNow.toString(), date1YearFromNow.toString(), 1000, 10000);
        User user = new User("Hess", "test");
        User admin = new User("Kristian", "test");
        Tenant tenant = new Tenant("Tenant", 11111111, "Developer");
        Tenant tenant2 = new Tenant("2", 2222222, "Software");
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");


        em.getTransaction().begin();
        user.addRole(userRole);
        admin.addRole(adminRole);
        em.merge(userRole);
        em.merge(adminRole);
        em.merge(user);
        em.merge(admin);
        em.merge(cityinfo);
        em.merge(contactPerson);
        house.addCityinfo(cityinfo);
        em.merge(house);
        rental.addContactPerson(contactPerson);
        rental.addHouse(house);
        tenant.addUser(user);
        tenant2.addUser(admin);
        tenant.addRental(rental);
        em.merge(tenant);
        em.merge(tenant2);
        rental.addTenant(tenant);
        em.merge(rental);
        em.flush();
        em.clear();
        em.getTransaction().commit();
        em.close();
        System.out.println("Created Objects");
        }catch (Exception e){
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void clearDatabase(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactoryForTest();
        EntityManager em = emf.createEntityManager();
        try {
            //Delete to get a "fresh" database
            em.getTransaction().begin();
            em.createNativeQuery("SET FOREIGN_KEY_CHECKS=0;").executeUpdate();
            em.createNativeQuery("delete from user_has_roles").executeUpdate();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();
            em.createNativeQuery("delete from Tenant_Has_Rental ").executeUpdate();
            em.createQuery("delete from Tenant ").executeUpdate();
            em.createQuery("delete from Rental ").executeUpdate();
            em.createQuery("delete from ContactPerson").executeUpdate();
            em.createQuery("delete from Cityinfo").executeUpdate();
            em.createQuery("delete from House").executeUpdate();
            em.createNativeQuery("ALTER TABLE Role AUTO_INCREMENT = 1").executeUpdate();
            em.createNativeQuery("ALTER TABLE Tenant AUTO_INCREMENT = 1").executeUpdate();
            em.createNativeQuery("ALTER TABLE Rental AUTO_INCREMENT = 1").executeUpdate();
            em.createNativeQuery("ALTER TABLE ContactPerson AUTO_INCREMENT = 1").executeUpdate();
            em.createNativeQuery("ALTER TABLE Cityinfo AUTO_INCREMENT = 1").executeUpdate();
            em.createNativeQuery("ALTER TABLE House AUTO_INCREMENT = 1").executeUpdate();
            em.createNativeQuery("SET FOREIGN_KEY_CHECKS=1;").executeUpdate();
            //System.out.println("Saved test data to database");
            em.getTransaction().commit();
        } catch(Exception e){
            em.getTransaction().rollback();
        } finally{
            em.close();
        }
    }

    public static void main(String[] args) {
//        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        LocalDate date1YearFromNow = LocalDate.now().plusYears(1);
        LocalDate dateNow = LocalDate.now();


        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactoryForTest();
        EntityManager em = emf.createEntityManager();
        ContactPerson contactPerson = new ContactPerson("ContactPersonName", 11111111);
        Cityinfo cityinfo = new Cityinfo("Nordhavn", 2100);
        House house = new House("Address",  2);
        Rental rental = new Rental(dateNow.toString(), date1YearFromNow.toString(), 1000, 10000);
        User user = new User("Hess", "test");
        User admin = new User("Kristian", "test");
        Tenant tenant = new Tenant("Tenant", 11111111, "Developer");
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");


        em.getTransaction().begin();
        user.addRole(userRole);
        admin.addRole(adminRole);
        em.merge(userRole);
        em.merge(adminRole);
        em.merge(user);
        em.merge(admin);
        em.merge(cityinfo);
        em.merge(contactPerson);
        house.addCityinfo(cityinfo);
        em.merge(house);
        rental.addContactPerson(contactPerson);
        rental.addHouse(house);
        em.merge(rental);
        tenant.addUser(user);
//        tenant.addRental(rental);
        em.merge(tenant);
        em.getTransaction().commit();
        System.out.println("Created Objects");
    }

}
