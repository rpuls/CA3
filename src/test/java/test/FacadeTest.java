package test;

import entity.Role;
import entity.Shop;
import entity.User;
import enums.Category;
import facades.ShopJpaController;
import facades.UserFacade;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
    private static ShopJpaController shopctrl;
    
  public FacadeTest() {
      emf = Persistence.createEntityManagerFactory("PU_TEST");
      facade = new UserFacade(emf);
      shopctrl = new ShopJpaController(emf);
      
  }
  
  @BeforeClass
  public static void setUpClass() {
  }
  
  @Before
  public void setUp() {
  }
  
  @After 
    public void tearDown() {
    }
  
    
    @Test
    public void setAUserToAShop() throws PasswordStorage.CannotPerformOperationException{
        Shop s = new Shop("ToysRUS", "email@em.dk", "12345678", "Toy store", "www.trs.dk","www.facebook.com/test","www.instagram.com/test", Category.PAPE, "street", "123 abd");
        facade.createShop(s);
        User user= new User("user","test");
        Role role = new Role("User");
        user.addRole(role);
        facade.addUser(user);
        facade.setUserToAShop(user, 1);
        String userFromDB = facade.getShopUser(1);
        assertEquals(user.getUserName(), userFromDB);
    }
    
    @Test(expected=Exception.class)
    public void setAUserToAShopInvalidShop() throws PasswordStorage.CannotPerformOperationException{
        User user= new User("user","test");
        Role role = new Role("User");
        user.addRole(role);
        facade.addUser(user);
        facade.setUserToAShop(user, 3);
        Shop expectedShop = em.find(Shop.class, 3);
        assertNull(expectedShop);
    }
    
    
  
  // really stupid test to see if the method to createShop a shop works
  @Test
    public void createShopTest() {
        
        Shop s = new Shop("ToysRUS", "email@em.dk", "12345678", "Toy store", "www.trs.dk","www.facebook.com/test","www.instagram.com/test", Category.PAPE, "street", "123 abd");
        Shop found = facade.createShop(s);
        assertEquals(found.getName(),"ToysRUS");
    }
    
    @Test
    public void getAllShopsTest(){
        List<Shop> list = facade.getAllShops();
        assertEquals(list.size(), 2);
    }
    
  
}
