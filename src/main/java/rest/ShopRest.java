/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entity.CityInfo;
import entity.Picture;
import entity.Shop;
import google.GooglePlaceAPI;
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
import jsonmappers.TinyShopMapper;
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
    private String testKey = "AIzaSyCk7blviPaQ3wPLGzDt7Dndzikj4bNeLI0"; // this is the developer key needed to make the REST call
    private String rasmusKey = "AIzaSyDXWa6rhasqr55G-AfVZmdjf_lXt6gKjYY";
    private String timmKey = "AIzaSyCk7blviPaQ3wPLGzDt7Dndzikj4bNe";
    private String key = rasmusKey;
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
                + placeId + "&key=" + key;
        jsonObject = ExternalURLRESTCall.readJsonFromUrl(url, "UTF-8");
        return jsonObject.get("result").getAsJsonObject().get("rating").toString();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String getAllShops() throws IOException, Exception {
        GooglePlaceAPI.update();
        List<entity.Shop> shops = facade.getAllShops();
        List<ShopMapper> shopmappers = new ArrayList<>();

        for (entity.Shop shop : shops) {
            shopmappers.add(new ShopMapper(shop));
        }

        return gson.toJson(shopmappers);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("images/{shopID}")
    public String getAllImagesFromShop(@PathParam("shopID") int shopID) throws IOException, Exception {
        List<Picture> pictures = facade.getFilesByShop(shopID);
        if (!pictures.isEmpty()) {
            Shop s = facade.findShop(shopID);
            s.setPictures(pictures);
            List<String> filePaths = s.getImagesAsFilePaths();
            for (String filePath : filePaths) {
                System.out.println("PATHS:" + filePath);
            }
            return gson.toJson(filePaths);
        }
        return "{Error: 'No images'}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("compressed")
    public String getCompressedShops() throws IOException, Exception {
        List<TinyShopMapper> tinyShops = facade.getTinyShops();
        return gson.toJson(tinyShops);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("usershop/{user}")
    public String getUserShop(@PathParam("user") String user) throws IOException, Exception {
        //googleUpdate();
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
