package test;

import entity.Role;
import entity.Shop;
import entity.User;
import enums.Category;
import facades.ShopJpaController;
import facades.UserFacade;
import java.util.Date;
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
        Role r = new Role("User");
        User p1 = new User("vintro_user","test");
        p1.addRole(r);
//        facade.addUser(p1);

        ShopJpaController ctrl = new ShopJpaController(emf);
        Date date = new Date();
        Shop vintro = new Shop("VINTRO", "shop@vintrovin.dk", "51220002", "very nice wineshop", "www.vintrovin.dk", Category.WINE, date, "Ravnsborggade", "5", 2000, 1200, 2000, 1200, 2000, 
1200, 2000, 1200, 2000, 1200, 2000, 1200, 2000, 1200, "ChIJiwsILgdTUkYRPRASlR0END0", 5.0, 30.0, 20.0, 90.0);
        vintro.setUser(p1);
        ctrl.setUserForShop(p1, vintro);
        
    }
}