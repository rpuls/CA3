
package control;

import entity.Role;
import entity.Shop;
import entity.User;
import security.IUserFacade;
import security.UserFacadeFactory;

/**
 *
 * @author Cherry Rose Semeña
 */
public class Mail {
    private String message;
    private String subject;
    private int shopId;
    private String emailTo;
    private Shop shop;
    private IUserFacade facade = UserFacadeFactory.getInstance();

    public Mail(String message, String subject, int shopId, String emailTo) {
        this.message = message;
        this.subject = subject;
        this.shopId = shopId;
        this.emailTo = emailTo;
        shop = facade.findShop(shopId);
    }

    public String getMessage() {
        return message;
    }

    public String getSubject() {
        return subject;
    }

    public Shop getShop() {
        return shop;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void createUser(){
        String username = shop.getName() + "_user";
        String password = "test";
        this.message = this.message + "<br><br>You can now login with the user name <b>" + username +
                "</b> and password <b>" + password + "</b>.<br><a href = 'nbrog.dk'> nbrog.dk </a>";
        try{
        User user = new User(username, password);
        Role userRole = new Role("User");
        user.addRole(userRole);
        facade.addUser(user, shopId);
        }catch(Exception ex){
            System.out.println("MAIL: CREATE USER ERROR "+ex.getMessage());
        }
        
    }

}
