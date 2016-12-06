package test;

import entity.Role;
import entity.Shop;
import entity.User;
import enums.Category;
import facades.UserFacade;
import org.junit.BeforeClass;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.parsing.Parser;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import org.apache.catalina.LifecycleException;
import static org.hamcrest.Matchers.*;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;
import security.IUserFacade;
import security.UserFacadeFactory;
import test.utils.EmbeddedTomcat;

public class InitialSeedRestIntegrationTest {

  private static final int SERVER_PORT = 9999; //running instance of tomcat already using 8080
  private static final String APP_CONTEXT = "/seed";
  private static EmbeddedTomcat tomcat;
  
    private static EntityManager em;
    private static IUserFacade facade;

  public InitialSeedRestIntegrationTest() {
  }
  private static String securityToken;

  //Utility method to login and set the securityToken
  private static void login(String role, String password) {
    String json = String.format("{username: \"%s\", password: \"%s\"}",role,password);
    System.out.println(json);
    securityToken = given()
            .contentType("application/json")
            .body(json)
            .when().post("/api/login")
            .then()
            .extract().path("token");
    System.out.println("Token: " + securityToken);

  }
 
  private void logOut(){
    securityToken = null;
  }

  @BeforeClass
  public static void setUpBeforeAll() throws ServletException, MalformedURLException, LifecycleException {
    tomcat = new EmbeddedTomcat();
    tomcat.start(SERVER_PORT, APP_CONTEXT);
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = SERVER_PORT;
    RestAssured.basePath = APP_CONTEXT;
    RestAssured.defaultParser = Parser.JSON;
    /*
    Insert test users to DB
    */
    em = Persistence.createEntityManagerFactory("PU_TEST").createEntityManager();
    facade = UserFacadeFactory.getInstance();
    try {
      System.out.println("Creating TEST Users");
      if (em.find(User.class, "user") == null) {
        em.getTransaction().begin();
        Role userRole = new Role("User");
        Role adminRole = new Role("Admin");
        User user = new User("user", "test");
        user.addRole(userRole);
        User admin = new User("admin", "test");
        admin.addRole(adminRole);
        User both = new User("user_admin", "test");
        both.addRole(userRole);
        both.addRole(adminRole);
        em.persist(userRole);
        em.persist(adminRole);
        em.persist(user);
        em.persist(admin);
        em.persist(both);
        em.getTransaction().commit();
        System.out.println("Created TEST Users");
      }
    } catch (Exception ex) {
      Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
      em.getTransaction().rollback();
    } finally {
      em.close();
    }
    
    
  }

  @AfterClass
  public static void after() throws ServletException, MalformedURLException, LifecycleException {
    tomcat.stop();
  }

  @Test
  public void testRestNoAuthenticationRequired() {
    given()
            .contentType("application/json")
            .when()
            .get("/api/demoall").then()
            .statusCode(200)
            .body("message", equalTo("result for all"));
  }

  @Test
  public void tesRestForAdmin() {
    login("admin","test");
    given()
            .contentType("application/json")
            .header("Authorization", "Bearer " + securityToken)
            .when()
            .get("/api/demoadmin").then()
            .statusCode(200)
            .body("message", equalTo("REST call accesible by only authenticated ADMINS"))
            .body("serverTime",notNullValue());
  }

  @Test
  public void testRestForUser() {
    login("user","test");
    given()
            .contentType("application/json")
            .header("Authorization", "Bearer " + securityToken)
            .when()
            .get("/api/demouser").then()
            .statusCode(200)
            .body("message", equalTo("REST call accesible by only authenticated USERS"));
  }
  
  @Test
  public void userNotAuthenticated() {
    logOut();
    given()
            .contentType("application/json")
            .when()
            .get("/api/demouser").then()
            .statusCode(401)
            .body("error.message", equalTo("No authorization header provided"));
  }
  
  @Test
  public void adminNotAuthenticated() {
    logOut();
    given()
            .contentType("application/json")
            .when()
            .get("/api/demoadmin").then()
            .statusCode(401)
            .body("error.message", equalTo("No authorization header provided"));

  }
  
    /**
     * Test of getAllShops method, of class ShopRest.
     */
    @Test
    public void testGetJSONShops() {
        List<Shop> list = facade.getAllShops();
        int expSize = list.size();
        Shop[] shops = 
                given().
                when().get("/api/shop/all").as(Shop[].class);
        assertEquals(expSize,shops.length);

    }

    /**
     * Test of getAllShops method with invalid path, of class ShopRest.
     */
    @Test
    public void testGetJSONShopsInvalidPath() {
                given().
                when().get("/api/shops").then()
                .statusCode(404)
                .body("error.message", equalTo("The requested resource was not found on our server"));

    }
    
    /**
     * Test for adding a shop of class ShopRest
     */
    @Test
    @Ignore //This is only ignored as we don't want to add same new shop everytime we run the test
    public void testAddShop(){
        Category c = null;
        Shop newshop = new Shop("Bog & Ide", "bogide@mail.dk", "35 85 52 77", "Book Shop", "www.bog-ide.dk","fbURL","instaURL", c.HAND, "Nørrebrogade", "163", new Date());
        given()
        .contentType("application/json")
        .body(newshop)
        .when().post("/api/shop/add").then()
        .statusCode(200);
    }
    
    /**
     * Test for getting shops by a user
     */
    @Test
    public void testGetShopByUser() {
                given().
                pathParam("user", "vintro_user")
                .when()
                .get("/api/shop/usershop/{user}")
                .then()
                .statusCode(200)
                .body("name", equalTo(Arrays.asList("VINTRO")));

    }
    
}
