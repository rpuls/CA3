/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import facades.UserFacade;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
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
public class Shop {

    @Context
    private UriInfo context;
    
    private IUserFacade facade;
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Creates a new instance of Shop
     */
    public Shop() {
        facade = UserFacadeFactory.getInstance();
    }

    /**
     * Retrieves representation of an instance of rest.Shop
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("update/all")
    public String getGoogleData() throws IOException {
        JsonObject jsonObject;
        String result = "{}";
//        try {
            jsonObject = ExternalURLRESTCall.readJsonFromUrl("https://maps.googleapis.com/maps/api/place/textsearch/json?query=shops+in+Norrebro&key=AIzaSyCk7blviPaQ3wPLGzDt7Dndzikj4bNeLI0");
            result = jsonObject.toString();
            //return "";
//        } catch (IOException ex) {
//            System.err.println(ex.getStackTrace());
//        } catch (JsonException ex) {
//            System.err.println(ex.getStackTrace());
//        } catch (Exception ex){
//            System.err.println(ex.getStackTrace());
//        }
        return result;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String getAllShops() {
        
        
        
        List<entity.Shop> shops = facade.getAllShops();
        List<jsonmappers.ShopMapper> shopmappers = new ArrayList<>();
        
        for(entity.Shop shop : shops) {
            shopmappers.add(new ShopMapper(shop));
        }
        
        return gson.toJson(shopmappers);
    }

    /**
     * PUT method for updating or creating an instance of Shop
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    
}