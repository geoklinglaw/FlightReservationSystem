/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal maxSeatCapacity;
    @Column(nullable = false)
    
    @OneToMany
    private List<CabinClass> cabinClassList;
    

    public AirportConfiguration() {
    }
    
    public AirportConfiguration(AircraftType type) {
        this.name = type.getName();
        this.maxSeatCapacity = type.getMaxSeatCapacity();
        this.cabinClassList = new ArrayList<CabinClass>();

        
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
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the maxSeatCapacity
     */
    public BigDecimal getMaxSeatCapacity() {
        return maxSeatCapacity;
    }

    /**
     * @param maxSeatCapacity the maxSeatCapacity to set
     */
    public void setMaxSeatCapacity(BigDecimal maxSeatCapacity) {
        this.maxSeatCapacity = maxSeatCapacity;
    }
    
}
