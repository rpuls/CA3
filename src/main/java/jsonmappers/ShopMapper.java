/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonmappers;

//import entity.Address;
import entity.CityInfo;
import entity.Shop;
import entity.User;
import enums.Category;
import java.util.Date;

/**
 *
 * @author TimmosQuadros
 */
public class ShopMapper {

    private int id;
    private String name;
    private String street;
    private String houseNumber;
    private String phone;
    private String email;
    private String website;
    private Category category;
    private String description;
    private String googlePlaceId;
    private double rating;
    private CityInfo cityInfo;
    private UserMapper user;
    //open/close hours
    private int dayNullOpen;
    private int dayNullClose;
    private int dayOneOpen;
    private int dayOneClose;
    private int dayTwoOpen;
    private int dayTwoClose;
    private int dayThreeOpen;
    private int dayThreeClose;
    private int dayFourOpen;
    private int dayFourClose;
    private int dayFiveOpen;
    private int dayFiveClose;
    private int daySixOpen;
    private int daySixClose;
    //mapposition
    private double x;
    private double y;
    private double angle;

    public ShopMapper(Shop shop) {
        id = shop.getId();
        name = shop.getName();
        street = shop.getStreet();
        houseNumber = shop.getHousenumber();
        phone = shop.getPhone();
        email = shop.getEmail();
        cityInfo = shop.getCityInfo();
        user = new UserMapper(shop.getUser());
        website = shop.getWebsite();
        if(website == null){
            website="NA";
        }    
        category = shop.getCategory();
        description = shop.getDescription();
        googlePlaceId = shop.getGooglePlaceId();
        rating = shop.getRating();
        dayNullOpen = shop.getDayNullOpen();
        dayNullClose = shop.getDayNullClose();
        dayOneOpen = shop.getDayOneOpen();
        dayOneClose = shop.getDayOneClose();
        dayTwoOpen = shop.getDayTwoOpen();
        dayTwoClose = shop.getDayTwoClose();
        dayThreeOpen = shop.getDayThreeOpen();
        dayThreeClose = shop.getDayThreeClose();
        dayFourOpen = shop.getDayFourOpen();
        dayFourClose = shop.getDayFourClose();
        dayFiveOpen = shop.getDayFiveOpen();
        dayFiveClose = shop.getDayFiveClose();
        daySixOpen = shop.getDaySixOpen();
        daySixClose = shop.getDaySixClose();
        x = shop.getX();
        y = shop.getY();
        angle = shop.getAngle();

    }

    public String getGooglePlaceId() {
        return googlePlaceId;
    }

    public void setGooglePlaceId(String googleShopId) {
        this.googlePlaceId = googleShopId;

    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHousenumber() {
        return houseNumber;
    }

    public void setHousenumber(String housenumber) {
        this.houseNumber = housenumber;
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    public UserMapper getUser() {
        return user;
    }

    public void setUser(UserMapper user) {
        this.user = user;
    }

    public int getDayNullOpen() {
        return dayNullOpen;
    }

    public void setDayNullOpen(int dayNullOpen) {
        this.dayNullOpen = dayNullOpen;
    }

    public int getDayNullClose() {
        return dayNullClose;
    }

    public void setDayNullClose(int dayNullClose) {
        this.dayNullClose = dayNullClose;
    }

    public int getDayOneOpen() {
        return dayOneOpen;
    }

    public void setDayOneOpen(int dayOneOpen) {
        this.dayOneOpen = dayOneOpen;
    }

    public int getDayOneClose() {
        return dayOneClose;
    }

    public void setDayOneClose(int dayOneClose) {
        this.dayOneClose = dayOneClose;
    }

    public int getDayTwoOpen() {
        return dayTwoOpen;
    }

    public void setDayTwoOpen(int dayTwoOpen) {
        this.dayTwoOpen = dayTwoOpen;
    }

    public int getDayTwoClose() {
        return dayTwoClose;
    }

    public void setDayTwoClose(int dayTwoClose) {
        this.dayTwoClose = dayTwoClose;
    }

    public int getDayThreeOpen() {
        return dayThreeOpen;
    }

    public void setDayThreeOpen(int dayThreeOpen) {
        this.dayThreeOpen = dayThreeOpen;
    }

    public int getDayThreeClose() {
        return dayThreeClose;
    }

    public void setDayThreeClose(int dayThreeClose) {
        this.dayThreeClose = dayThreeClose;
    }

    public int getDayFourOpen() {
        return dayFourOpen;
    }

    public void setDayFourOpen(int dayFourOpen) {
        this.dayFourOpen = dayFourOpen;
    }

    public int getDayFourClose() {
        return dayFourClose;
    }

    public void setDayFourClose(int dayFourClose) {
        this.dayFourClose = dayFourClose;
    }

    public int getDayFiveOpen() {
        return dayFiveOpen;
    }

    public void setDayFiveOpen(int dayFiveOpen) {
        this.dayFiveOpen = dayFiveOpen;
    }

    public int getDayFiveClose() {
        return dayFiveClose;
    }

    public void setDayFiveClose(int dayFiveClose) {
        this.dayFiveClose = dayFiveClose;
    }

    public int getDaySixOpen() {
        return daySixOpen;
    }

    public void setDaySixOpen(int daySixOpen) {
        this.daySixOpen = daySixOpen;
    }

    public int getDaySixClose() {
        return daySixClose;
    }

    public void setDaySixClose(int daySixClose) {
        this.daySixClose = daySixClose;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

}
