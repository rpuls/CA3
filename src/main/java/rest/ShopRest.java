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
            List<entity.Shop> shops = facade.findShopsToUpdate();
            for (Shop shop : shops) {
                String placeId = shop.getGooglePlaceId();
                if (placeId != null) {
                    String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid="
                            + placeId + "&key=AIzaSyCk7blviPaQ3wPLGzDt7Dndzikj4bNeLI0";
                    JsonObject jsonObject = ExternalURLRESTCall.readJsonFromUrl(url);
                    JsonObject result = jsonObject.get("result").getAsJsonObject();
                    if (result.get("rating") != null) {
                        shop.setRating(result.get("rating").getAsDouble());
                    }
                    if (result.get("international_phone_number") != null) {
                        shop.setPhone(result.get("international_phone_number").getAsString());
                    }
                    if (result.get("website") != null) {
                        shop.setWebsite(result.get("website").getAsString());
                    }

                    //FETCHING ADDRESS COMPONENTS, street, housenumber, zip code.
                    if (result.get("address_components") != null) {
                        JsonArray adressComponents = result.get("address_components").getAsJsonArray();
                        for (int i = 0; i < adressComponents.size(); i++) {
                            JsonArray types = adressComponents.get(i).getAsJsonObject().get("types").getAsJsonArray();
                            for (int j = 0; j < types.size(); j++) {
                                if (types.get(j).getAsString().equalsIgnoreCase("street_number")) {
                                    String houseNumber = adressComponents.get(i).getAsJsonObject().get("long_name").getAsString();
                                    shop.setHouseNumber(houseNumber);
                                }
                                if (types.get(j).getAsString().equalsIgnoreCase("route")) {
                                    String streetName = adressComponents.get(i).getAsJsonObject().get("long_name").getAsString();
                                    shop.setStreet(streetName);
                                }
                                if (types.get(j).getAsString().equalsIgnoreCase("postal_code")) {
                                    String zipCode = adressComponents.get(i).getAsJsonObject().get("long_name").getAsString();
                                    shop.setCityInfo(facade.findCityInfo(zipCode));
                                }
                            }
                        }
                    }

                    JsonElement hoursElement = result.get("opening_hours");
                    if (hoursElement != null) {
                        JsonObject openingHours = result.get("opening_hours").getAsJsonObject();
                        JsonArray days = openingHours.get("periods").getAsJsonArray();
                        for (int i = 0; i < days.size(); i++) {
                            JsonObject open = (JsonObject) days.get(i).getAsJsonObject().getAsJsonObject("open");
                            JsonObject close = days.get(i).getAsJsonObject().getAsJsonObject("close");

                            if (close.get("day").getAsString().equalsIgnoreCase("0")) {
                                shop.setDayNullClose(open.get("time").getAsInt());
                            } else if (close.get("day").getAsString().equalsIgnoreCase("1")) {
                                shop.setDayOneClose(close.get("time").getAsInt());
                            } else if (close.get("day").getAsString().equalsIgnoreCase("2")) {
                                shop.setDayTwoClose(close.get("time").getAsInt());
                            } else if (close.get("day").getAsString().equalsIgnoreCase("3")) {
                                shop.setDayThreeClose(close.get("time").getAsInt());
                            } else if (close.get("day").getAsString().equalsIgnoreCase("4")) {
                                shop.setDayFourClose(close.get("time").getAsInt());
                            } else if (close.get("day").getAsString().equalsIgnoreCase("5")) {
                                shop.setDayFiveClose(close.get("time").getAsInt());
                            } else if (close.get("day").getAsString().equalsIgnoreCase("6")) {
                                shop.setDaySixClose(close.get("time").getAsInt());
                            }

                            if (open.get("day").getAsString().equalsIgnoreCase("0")) {
                                shop.setDayNullOpen(open.get("time").getAsInt());
                            } else if (open.get("day").getAsString().equalsIgnoreCase("1")) {
                                shop.setDayOneOpen(open.get("time").getAsInt());
                            } else if (open.get("day").getAsString().equalsIgnoreCase("2")) {
                                shop.setDayTwoOpen(open.get("time").getAsInt());
                            } else if (open.get("day").getAsString().equalsIgnoreCase("3")) {
                                shop.setDayThreeOpen(open.get("time").getAsInt());
                            } else if (open.get("day").getAsString().equalsIgnoreCase("4")) {
                                shop.setDayFourOpen(open.get("time").getAsInt());
                            } else if (open.get("day").getAsString().equalsIgnoreCase("5")) {
                                shop.setDayFiveOpen(open.get("time").getAsInt());
                            } else if (open.get("day").getAsString().equalsIgnoreCase("6")) {
                                shop.setDaySixOpen(open.get("time").getAsInt());
                            }
                        }
                    }
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
        List<ShopMapper> shopmappers = new ArrayList<>();

        for (entity.Shop shop : shops) {
            shopmappers.add(new ShopMapper(shop));
        }

        return gson.toJson(shopmappers);
    }
    
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("compressed")
//    public String getCompressedShops() throws IOException, Exception {
//        List<entity.Shop> shops = facade.getTinyShops();
//        List<jsonmappers.TinyShopMapper> tinyShops = new ArrayList<>();
//
//        for (entity.Shop shop : shops) {
//            tinyShops.add(new jsonmappers.TinyShopMapper(shop));
//        }
//
//        return gson.toJson(tinyShops);
//    }

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
