/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonmappers;

import entity.CityInfo;

/**
 *
 * @author TimmosQuadros
 */
public class CityInfoMapper {
    
    String city;
    String zipcode;

    public CityInfoMapper(CityInfo cityInfo) {
        city = cityInfo.getCity();
        zipcode = cityInfo.getZipCode();
    }

    public CityInfoMapper() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    
    
    
    
    
}
