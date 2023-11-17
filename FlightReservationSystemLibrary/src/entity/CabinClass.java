/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import util.enumeration.CabinClassType;

/**
 *
 * @author apple
 */
@Entity
public class CabinClass implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private CabinClassType type;
    @Column(nullable = false)
    private BigDecimal seatingCapacity;
    @Column(nullable = false)
    private BigDecimal numAisles;
    @Column(nullable = false)
    private BigDecimal numRows;
    @Column(nullable = false)
    private BigDecimal numSeatAbreast; 
    @Column(nullable = false)
    private String seatConfig;
    
    @ManyToOne
    private AircraftConfiguration aircraftConfig;
    
    
    @OneToMany(mappedBy = "cabinClass")
    private List<FlightCabinClass> flightCabinClass;
    
    public CabinClass() {
//        this.flightSchedule = new ArrayList<>()
    }

    public CabinClass(String type, BigDecimal seatingCapacity, BigDecimal numAisles, BigDecimal numRows, BigDecimal numSeatAbreast, String seatConfig) {
        this.type = CabinClassType.fromValue(type);
        this.seatingCapacity = seatingCapacity;
        this.numAisles = numAisles;
        this.numRows = numRows;
        this.numSeatAbreast = numSeatAbreast;
        this.seatConfig = seatConfig;
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
        if (!(object instanceof CabinClass)) {
            return false;
        }
        CabinClass other = (CabinClass) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CabinClass[ id=" + id + " ]";
    }

    /**
     * @return the type
     */
    public CabinClassType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(CabinClassType type) {
        this.type = type;
    }

    /**
     * @return the seatingCapacity
     */
    public BigDecimal getSeatingCapacity() {
        return seatingCapacity;
    }

    /**
     * @param seatingCapacity the seatingCapacity to set
     */
    public void setSeatingCapacity(BigDecimal seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    /**
     * @return the numAisles
     */
    public BigDecimal getNumAisles() {
        return numAisles;
    }

    /**
     * @param numAisles the numAisles to set
     */
    public void setNumAisles(BigDecimal numAisles) {
        this.numAisles = numAisles;
    }

    /**
     * @return the numRows
     */
    public BigDecimal getNumRows() {
        return numRows;
    }

    /**
     * @param numRows the numRows to set
     */
    public void setNumRows(BigDecimal numRows) {
        this.numRows = numRows;
    }

    /**
     * @return the aircraftConfig
     */
    public AircraftConfiguration getAircraftConfig() {
        return aircraftConfig;
    }

    /**
     * @param aircraftConfig the aircraftConfig to set
     */
    public void setAircraftConfig(AircraftConfiguration aircraftConfig) {
        this.aircraftConfig = aircraftConfig;
    }

    /**
     * @return the fareList
     */


    /**
     * @return the numSeatAbreast
     */
    public BigDecimal getNumSeatAbreast() {
        return numSeatAbreast;
    }

    /**
     * @param numSeatAbreast the numSeatAbreast to set
     */
    public void setNumSeatAbreast(BigDecimal numSeatAbreast) {
        this.numSeatAbreast = numSeatAbreast;
    }

    /**
     * @return the seatConfig
     */
    public String getSeatConfig() {
        return seatConfig;
    }

    /**
     * @param seatConfig the seatConfig to set
     */
    public void setSeatConfig(String seatConfig) {
        this.seatConfig = seatConfig;
    }

    /**
     * @return the flightCabinClass
     */
    public List<FlightCabinClass> getFlightCabinClass() {
        return flightCabinClass;
    }

    /**
     * @param flightCabinClass the flightCabinClass to set
     */
    public void setFlightCabinClass(List<FlightCabinClass> flightCabinClass) {
        this.flightCabinClass = flightCabinClass;
    }



    /**
     * @return the fare
     */

}
