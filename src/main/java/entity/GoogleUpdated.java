/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author rasmus
 */
@Entity
public class GoogleUpdated implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date googleUpdated;

    public GoogleUpdated(Date googleUpdated) {
        this.googleUpdated = googleUpdated;
    }

    public GoogleUpdated() {
    }

    public Date getGoogleUpdated() {
        return googleUpdated;
    }

    public void setGoogleUpdated(Date googleUpdated) {
        this.googleUpdated = googleUpdated;
    }

    

}
