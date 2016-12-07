/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import entity.CityInfo;
import entity.Picture;
import entity.Shop;
import entity.User;
import facades.exceptions.NonexistentEntityException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author lam
 */
public interface IUserFacade {

    /*
    Return the Roles if users could be authenticated, otherwise null
     */
    List<String> authenticateUser(String userName, String password);

    IUser getUserByUserId(String id);
    
    public boolean needGoogleUpdate();
    
    public void googleUpdated();
    
    public Shop createShop(Shop shop);
    
    public CityInfo findCityInfo(String zipCode);
    
    public List<Shop> getAllShops();
    
    public List<Shop> findShopsToUpdate();
    
    public void updateShop(Shop shop) throws NonexistentEntityException, Exception;
    
    public void delete(Integer id) throws NonexistentEntityException;
    
    public List<Shop> getShopByUser(String username);
     
    public void addFiles(int shopId, List<Picture> images);
    
}
