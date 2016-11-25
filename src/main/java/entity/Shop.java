package entity;

import enums.Category;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.validation.constraints.Size;

/**
 *
 * @author TimmosQuadros
 */
@Entity
public class Shop implements Serializable {

    @ManyToOne
    private User user;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String email;
    private String phone;

    @Column(length = 500)
    private String description;

    private String website;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String googlePlaceId;
    private double rating;
    

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date updated;

    @OneToMany(mappedBy = "shop")
    private List<Picture> pictures;

    @Size(max = 45)
    private String street;

    @Size(max = 45)
    @Column(name = "HOUSENUMBER")
    private String houseNumber;

    @ManyToOne
    // @Column(name = "CITYINFO")
    @JoinColumn(name = "CITYINFO", referencedColumnName = "ZIP")
    private CityInfo cityInfo;
    
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

    public Shop(String name, String email, String phone, String description, String website,
            Category category, Date updated, String street, String houseNumber,
            int dayNullOpen, int dayNullClose, int dayOneOpen, int dayOneClose, int dayTwoOpen, int dayTwoClose,
            int dayThreeOpen, int dayThreeClose, int dayFourOpen, int dayFourClose, int dayFiveOpen,
            int dayFiveClose, int daySixOpen, int daySixClose, String googlePlaceId, double rating, double x, double y, double angle) {
        this(name, email, phone, description, website, category, street, houseNumber, updated);
        this.dayNullOpen = dayNullOpen;
        this.dayNullClose = dayNullClose;
        this.dayOneOpen = dayOneOpen;
        this.dayOneClose = dayOneClose;
        this.dayTwoOpen = dayTwoOpen;
        this.dayTwoClose = dayTwoClose;
        this.dayThreeOpen = dayThreeOpen;
        this.dayThreeClose = dayThreeClose;
        this.dayFourOpen = dayFourOpen;
        this.dayFourClose = dayFourClose;
        this.dayFiveOpen = dayFiveOpen;
        this.dayFiveClose = dayFiveClose;
        this.daySixOpen = daySixOpen;
        this.daySixClose = daySixClose;
        this.googlePlaceId = googlePlaceId;
        this.rating = rating;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public Shop(String name, String email, String phone, String description, String website,
            Category category, String street, String houseNumber, Date updated) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.website = website;
        this.category = category;
        this.street = street;
        this.houseNumber = houseNumber;
        this.updated = updated;
    }

    public Shop() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getGooglePlaceId() {
        return googlePlaceId;
    }

    public void setGooglePlaceId(String googlePlaceId) {
        this.googlePlaceId = googlePlaceId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
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
    
    public void setUsername(String username){
        this.user.setUserName(username);
    }

}
