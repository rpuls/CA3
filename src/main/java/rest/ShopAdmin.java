/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import control.EmailSender;
import control.Mail;
import entity.Shop;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import static rest.ShopRest.gson;
import security.IUserFacade;
import security.UserFacadeFactory;
import javax.annotation.security.RolesAllowed;

/**
 * REST Web Service
 *
 * @author TimmosQuadros
 */
@Path("shop")
@RolesAllowed("Admin")
public class ShopAdmin {

    @Context
    private UriInfo context;
    private IUserFacade facade;
     EmailSender sender = new EmailSender();

    /**
     * Creates a new instance of ShopAdmin
     */
    public ShopAdmin() {
        facade = UserFacadeFactory.getInstance();
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("add")
    public String addShop(String content) {
        Shop s = gson.fromJson(content, Shop.class);
        Shop newShop = facade.createShop(s);
        return gson.toJson(newShop);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("edit")
    public void editShop(String content) throws Exception {
        Shop s = gson.fromJson(content, Shop.class);
        s.setNeedGoogle(false);
        facade.updateShop(s);
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("email")
    public void sendEmail(String content) throws Exception {
        Mail i = gson.fromJson(content, Mail.class);
        System.out.println("EMAIL:" + i.getEmailTo());
        sender.sendEmail(i);
    }

    
}
