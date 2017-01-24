/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package google;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entity.Shop;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import security.IUserFacade;
import security.UserFacadeFactory;
import utils.ExternalURLRESTCall;

/**
 *
 * @author rasmus
 */
public class GooglePlaceAPI extends Thread {

    public GooglePlaceAPI() {
        facade = UserFacadeFactory.getInstance();
    }

    private final String testKey = "AIzaSyCk7blviPaQ3wPLGzDt7Dndzikj4bNeLI0"; // this is the developer key needed to make the REST call
    private final String rasmusKey = "AIzaSyDXWa6rhasqr55G-AfVZmdjf_lXt6gKjYY";
    private final String timmKey = "AIzaSyCk7blviPaQ3wPLGzDt7Dndzikj4bNe";
    private final String key = rasmusKey;
    private final IUserFacade facade;
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    static boolean isUpdating = false;

    public static void update() {
        if (!isUpdating) {
            isUpdating = true;
            Thread gpa = new GooglePlaceAPI();
            gpa.start();
        }
    }

    @Override
    public void run() {
        try {
            if (facade.needGoogleUpdate()) {
                List<entity.Shop> shops = facade.findShopsToUpdate();
                for (Shop shop : shops) {
                    String placeId = shop.getGooglePlaceId();
                    if (placeId != null) {
                        try {
                        googleUpdateShop(shop, placeId);
                        System.out.println("shop " + shop.getName() + " updated!");
                        }
                        catch (Exception ex){
                            System.out.println("error at shop: "+shop.getName()+ " - error: " + ex.toString());
                        }
                    }
                }
                facade.googleUpdated();
            }
            isUpdating = false;
        } catch (Exception ex) {
            isUpdating = false;
            System.out.println("GoogleUpdate failed: " + ex.toString());
            //fejl
        }
    }

    private void googleUpdateShop(Shop shop, String placeId) throws IOException, Exception {
        String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid="
                + placeId + "&key=" + key;
        JsonObject jsonObject = ExternalURLRESTCall.readJsonFromUrl(url, "UTF-8");
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
        JsonElement addressComponentsElem = result.get("address_components");
        if (addressComponentsElem != null) {
            JsonArray adressComponents = addressComponentsElem.getAsJsonArray();
            for (int i = 0; i < adressComponents.size(); i++) {
                JsonObject addressComponent = adressComponents.get(i).getAsJsonObject();
                String longName = addressComponent.get("long_name").getAsString();
                JsonArray types = addressComponent.get("types").getAsJsonArray();
                for (int j = 0; j < types.size(); j++) {
                    String fieldName = types.get(j).getAsString().toLowerCase();
                    switch (fieldName) {
                        case "street_number":
                            shop.setHouseNumber(longName);
                            break;
                        case "route":
                            shop.setStreet(longName);
                            break;
                        case "postal_code":
                            shop.setCityInfo(facade.findCityInfo(longName));
                            break;
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

                if (open != null) {
                    String openDay = open.get("day").getAsString();
                    int openTime = open.get("time").getAsInt();
                    if (openDay.equalsIgnoreCase("0")) {
                        shop.setDayNullOpen(openTime);
                    } else if (openDay.equalsIgnoreCase("1")) {
                        shop.setDayOneOpen(openTime);
                    } else if (openDay.equalsIgnoreCase("2")) {
                        shop.setDayTwoOpen(openTime);
                    } else if (openDay.equalsIgnoreCase("3")) {
                        shop.setDayThreeOpen(openTime);
                    } else if (openDay.equalsIgnoreCase("4")) {
                        shop.setDayFourOpen(openTime);
                    } else if (openDay.equalsIgnoreCase("5")) {
                        shop.setDayFiveOpen(openTime);
                    } else if (openDay.equalsIgnoreCase("6")) {
                        shop.setDaySixOpen(openTime);
                    }
                }

                JsonObject close = days.get(i).getAsJsonObject().getAsJsonObject("close");
                if (close != null) {
                    String closeDay = close.get("day").getAsString();
                    int closeTime = close.get("time").getAsInt();
                    if (closeDay.equalsIgnoreCase("0")) {
                        shop.setDayNullClose(open.get("time").getAsInt());
                    } else if (closeDay.equalsIgnoreCase("1")) {
                        shop.setDayOneClose(closeTime);
                    } else if (closeDay.equalsIgnoreCase("2")) {
                        shop.setDayTwoClose(closeTime);
                    } else if (closeDay.equalsIgnoreCase("3")) {
                        shop.setDayThreeClose(closeTime);
                    } else if (closeDay.equalsIgnoreCase("4")) {
                        shop.setDayFourClose(closeTime);
                    } else if (closeDay.equalsIgnoreCase("5")) {
                        shop.setDayFiveClose(closeTime);
                    } else if (closeDay.equalsIgnoreCase("6")) {
                        shop.setDaySixClose(closeTime);
                    }
                }
            }
        }
        facade.updateShop(shop);
    }

}
