/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author rasmus
 */
@Entity
public class Invitation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String code;
    
    @OneToOne
    private Shop shop;

    public Invitation(String code, Shop shop) {
        this.code = code;
        this.shop = shop;
    }

    public Invitation() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
 
    
  
}
