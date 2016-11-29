/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import entity.Shop;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.annotation.security.RolesAllowed;
import static rest.ShopRest.gson;
import security.IUserFacade;
import security.UserFacadeFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
/**
 * REST Web Service
 *
 * @author TimmosQuadros
 */
@Path("shop")
@RolesAllowed("User")
public class ShopUser {

    @Context
    private UriInfo context;
    private IUserFacade facade;

    /**
     * Creates a new instance of ShopUser
     */
    public ShopUser() {
        facade = UserFacadeFactory.getInstance();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("user/edit")
    public void editShop(String content) throws Exception {
        Shop s = gson.fromJson(content, Shop.class);

        if (s.getCategory() == null && s.getName() == null && s.getStreet() == null && s.getHouseNumber() == null && s.getGooglePlaceId() == null && s.getRating() == 0.0) {
            facade.updateShop(s);
        }
    }
    @POST
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.MULTIPART_FORM_DATA})
    @Path("upload")
    public void uploadFile(String content) throws Exception {
        //create a folder
        //get the path
        //store in db
    }

  
}
