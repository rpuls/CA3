/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import entity.Shop;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jsonmappers.ShopMapper;
import security.IUserFacade;
import security.UserFacadeFactory;
import utils.ExternalURLRESTCall;

/**
 * REST Web Service
 *
 * @author TimmosQuadros
 */
@Path("shop")
public class ShopRest {

    @Context
    private UriInfo context;
    private String key = "AIzaSyCk7blviPaQ3wPLGzDt7Dndzikj4bNeLI0"; // this is the developer key needed to make the REST call
    private IUserFacade facade;
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Creates a new instance of Shop
     */
    public ShopRest() {
        facade = UserFacadeFactory.getInstance();
    }

    /**
     * Retrieves representation of an instance of rest.ShopRest
     *
     * @param placeId
     * @return an instance of java.lang.String
     * @throws java.io.IOException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get/rating/{placeId}")
    public String getGoogleData(@PathParam("placeId") String placeId) throws IOException {
        JsonObject jsonObject;
        JsonObject rating;
        String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid="
                + placeId + "&key=AIzaSyCk7blviPaQ3wPLGzDt7Dndzikj4bNeLI0";
        jsonObject = ExternalURLRESTCall.readJsonFromUrl(url);
        return jsonObject.get("result").getAsJsonObject().get("rating").toString();
    }

    public void googleUpdate() throws IOException, Exception {
        
        if (facade.needGoogleUpdate()) {
            List<entity.Shop> shops = facade.getAllShops();
            for (Shop shop : shops) {
                String placeId = shop.getGooglePlaceId();
                if (placeId != null) {
                    String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid="
                            + placeId + "&key=AIzaSyCk7blviPaQ3wPLGzDt7Dndzikj4bNeLI0";
                    JsonObject jsonObject = ExternalURLRESTCall.readJsonFromUrl(url);
                    JsonObject result = jsonObject.get("result").getAsJsonObject();
                    shop.setRating(result.get("rating").getAsDouble());
//                    shop.setDayNullOpen(result.get("place.opening_hours.periods[1].open.time").getAsInt());
//                    shop.setDayNullClose(0);
//                    shop.getDayOneOpen();
//                    shop.getDayOneClose();
//                    shop.getDayTwoOpen();
//                    shop.getDayTwoClose();
//                    shop.getDayThreeOpen();
//                    shop.getDayThreeClose();
//                    shop.getDayFourOpen();
//                    shop.getDayFourClose();
//                    shop.getDayFiveOpen();
//                    shop.getDayFiveClose();
//                    shop.getDaySixOpen();
//                    shop.getDaySixClose();
                    facade.updateShop(shop);
                }
            }
            facade.googleUpdated();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String getAllShops() throws IOException, Exception {
        googleUpdate();
        List<entity.Shop> shops = facade.getAllShops();
        List<jsonmappers.ShopMapper> shopmappers = new ArrayList<>();

        for (entity.Shop shop : shops) {
            shopmappers.add(new ShopMapper(shop));
        }

        return gson.toJson(shopmappers);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("usershop/{user}")
    public String getUserShop(@PathParam("user") String user) throws IOException, Exception {
        googleUpdate();
        List<entity.Shop> shops = facade.getShopByUser(user);
        List<jsonmappers.ShopMapper> shopmappers = new ArrayList<>();

        for (entity.Shop shop : shops) {
            shopmappers.add(new ShopMapper(shop));
        }

        return gson.toJson(shopmappers);
    }

    /**
     * PUT method for updating or creating an instance of ShopRest
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    

}
