package test;

import entity.Role;
import entity.Shop;
import entity.User;
import facades.ShopJpaController;
import facades.UserFacade;
import java.util.HashMap;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import security.IUser;
import security.PasswordStorage;

public class Structure
{
    public static void main(String[] args) throws PasswordStorage.CannotPerformOperationException
    {
//        HashMap<String, Object> puproperties = new HashMap();
//        
//        //puproperties.put("javax.persistence.sql-load-script-source", "scripts/ClearDB.sql");
//        
//        //Persistence.generateSchema("pu", puproperties);
//        
//        Persistence.generateSchema("PU", null);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
        UserFacade facade = new UserFacade(emf);
//        Role r = new Role("User");
//        User p1 = new User("vintro_user","test");
//        p1.addRole(r);
//        facade.addUser(p1);

        ShopJpaController ctrl = new ShopJpaController(emf);
        User user = (User) facade.getUserByUserId("vintro_user");
//        Shop shop = ctrl.findShopByName("VINTRO");
        Shop shop = ctrl.findShop(124);
        user.addShop(shop);
        ctrl.setUserForShop(user, 124);
        
    }
}