package utils;

import entity.Role;
import entity.User;
import facades.UserFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class makeTestUsers {

  //Only for initial testing REMOVE BEFORE PRODUCTION
  //Run this file to setup the users required to use the initial version of the seed
  public static void main(String[] args) {
    EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
      
      UserFacade facade = new UserFacade(Persistence.createEntityManagerFactory("PU"));
    try {
      System.out.println("Creating TEST Users");
        em.getTransaction().begin();
        Role userRole = new Role("User");
//        Role adminRole = new Role("Admin");
//        User user = new User("user", "test");
//        user.addRole(userRole);
//        User admin = new User("admin", "test");
//        admin.addRole(adminRole);
//        User both = new User("user_admin", "test");
//        both.addRole(userRole);
//        both.addRole(adminRole);
//        em.persist(userRole);
//        em.persist(adminRole);
//        em.persist(user);
//        em.persist(admin);
//        em.persist(both);
//        em.getTransaction().commit();
//        System.out.println("Created TEST Users");
//        Role role = facade.getUserRole("User");
        User shopOwner = new User("owner","test");
        shopOwner.addRole(userRole);
        em.persist(shopOwner);
        em.getTransaction().commit();
        System.out.println("Created Shop User");
      
    } catch (Exception ex) {
      Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println("GET's IN HERE: rollback");
      em.getTransaction().rollback();
    } finally {
      em.close();
    }
  }
}
