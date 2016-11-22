package test;

import entity.Role;
import entity.User;
import facades.UserFacade;
import java.util.HashMap;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
//        User p = new User("VINTRO_user","test");
        Role r = facade.getUserRole("User");
//        p.addRole(r);
//        facade.addUser(p);
        User p1 = new User("TEMPLE_user","test");
        p1.addRole(r);
        facade.addUser(p1);
        
    }
}