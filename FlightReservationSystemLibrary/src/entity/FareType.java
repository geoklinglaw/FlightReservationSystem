/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author apple
 */
@Entity
public class FareType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fareBasisCode;
    private String fareTypeDetails;

    public FareType() {
    }

    public FareType(String fareBasisCode, String fareTypeDetails) {
        this.fareBasisCode = fareBasisCode;
        this.fareTypeDetails = fareTypeDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FareType)) {
            return false;
        }
        FareType other = (FareType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FareType[ id=" + id + " ]";
    }

    /**
     * @return the fareBasisCode
     */
    public String getFareBasisCode() {
        return fareBasisCode;
    }

    /**
     * @param fareBasisCode the fareBasisCode to set
     */
    public void setFareBasisCode(String fareBasisCode) {
        this.fareBasisCode = fareBasisCode;
    }

    /**
     * @return the fareTypeDetails
     */
    public String getFareTypeDetails() {
        return fareTypeDetails;
    }

    /**
     * @param fareTypeDetails the fareTypeDetails to set
     */
    public void setFareTypeDetails(String fareTypeDetails) {
        this.fareTypeDetails = fareTypeDetails;
    }
    
}
