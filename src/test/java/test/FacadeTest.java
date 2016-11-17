package test;

import entity.Shop;
import entity.User;
import enums.Category;
import facades.UserFacade;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import security.PasswordStorage;

/**
 *
 * @author lam
 */
public class FacadeTest {
  
    private static EntityManagerFactory emf;
    private static UserFacade facade;
    private static EntityManager em;
    
  public FacadeTest() {
      emf = Persistence.createEntityManagerFactory("PU_TEST");
      facade = new UserFacade(emf);
  }
  
  @BeforeClass
  public static void setUpClass() {
      
      
        
  }
  
  @Before
  public void setUp() {
      em = emf.createEntityManager();
      em.getTransaction().begin();
  }
  
  @After 
    public void tearDown() {
        if (em != null) {
          em.close();
        }
    }
  
  @Test  
  public void dummyTest(){
    assertTrue(true);  //Really "nothing" to test in the initial version of the seed
  }
  
  
  // really stupid test to see if the method to createShop a shop works
  @Test
    public void createShopTest() {
        
        Shop s = new Shop("ToysRUS", "email@em.dk", "12345678", "Toy store", "www.trs.dk", Category.PAPE, "street", "houseNumber", new Date());
        Shop found = facade.createShop(s);
        assertEquals(found.getName(),"ToysRUS");
    }
    
    @Test
    public void getAllShopsTest(){}
  
}
