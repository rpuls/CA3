
package control;

import entity.Shop;
import facades.ShopJpaController;
import facades.UserFacade;
import javax.persistence.Persistence;
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

    
    
    
}
