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
import util.enumeration.AircraftName;

/**
 *
 * @author apple
 */
@Entity
public class AirportConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private AircraftName name;
    private int maxSeatCapacity;

    public AirportConfiguration() {
    }
    
    public AirportConfiguration(AircraftType type) {
        String name = type.getName();
        int maxSeatCapacity = type.getMaxSeatCapacity();

        
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
        if (!(object instanceof AirportConfiguration)) {
            return false;
        }
        AirportConfiguration other = (AirportConfiguration) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AirportConfiguration[ id=" + id + " ]";
    }

    /**
     * @return the name
     */
    public AircraftName getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(AircraftName name) {
        this.name = name;
    }

    /**
     * @return the maxSeatCapacity
     */
    public int getMaxSeatCapacity() {
        return maxSeatCapacity;
    }

    /**
     * @param maxSeatCapacity the maxSeatCapacity to set
     */
    public void setMaxSeatCapacity(int maxSeatCapacity) {
        this.maxSeatCapacity = maxSeatCapacity;
    }
    
}
