package facades;

import entity.GoogleUpdated;
import entity.Shop;
import security.IUserFacade;
import entity.User;
import facades.exceptions.NonexistentEntityException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import security.IUser;
import security.PasswordStorage;

public class UserFacade implements IUserFacade {

    EntityManagerFactory emf;
    ShopJpaController shopCtrl;
    GoogleUpdatedJpaController googleUpdated;
    final static int updateIntervalHours = 12;

    public UserFacade(EntityManagerFactory emf) {
        this.emf = emf;
        shopCtrl = new ShopJpaController(emf);
        googleUpdated = new GoogleUpdatedJpaController(emf);
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public IUser getUserByUserId(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    /*
  Return the Roles if users could be authenticated, otherwise null
     */
    @Override
    public List<String> authenticateUser(String userName, String password) {
        IUser user = getUserByUserId(userName);
        try {
            return user != null && PasswordStorage.verifyPassword(password, user.getPassword()) ? user.getRolesAsStrings() : null;
        } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException ex) {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    //Create
    public Shop createShop(Shop shop) {
        return shopCtrl.create(shop);
    }

    //Retrieve
    public List<Shop> getAllShops() {
        return shopCtrl.findShopEntities();
    }

    //Update
    public void updateShop(Shop shop) throws NonexistentEntityException, Exception {
        shopCtrl.edit(shop);
    }

    //Delete
    public void delete(Integer id) throws NonexistentEntityException {
        shopCtrl.destroy(id);
    }

    @Override
    public boolean needGoogleUpdate() {
        List<GoogleUpdated> updateDates = googleUpdated.findGoogleUpdatedEntities();
        if (updateDates.isEmpty()) {
            return true;
        } else {
            Long lastUpdate = updateDates.get(0).getGoogleUpdated().getTime();
            Long now = new Date().getTime();
            int hours = (int) ((now - lastUpdate) / (1000 * 60 * 60)); //VIRKER IKKE OPTIMALT
            return hours > updateIntervalHours;
        }

    }

    @Override
    public void googleUpdated() {
        try {
            List<GoogleUpdated> updateDates = googleUpdated.findGoogleUpdatedEntities();

            for (GoogleUpdated ud : updateDates) {
                googleUpdated.destroy(ud.getGoogleUpdated());
            }
            googleUpdated.create(new GoogleUpdated(new Date()));
        }  catch (Exception ex) {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
