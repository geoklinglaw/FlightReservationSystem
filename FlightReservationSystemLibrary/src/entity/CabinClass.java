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
    private BigDecimal numAvailableSeats;
    @Column(nullable = false)
    private BigDecimal numReservedSeats;
    @Column(nullable = false)
    private BigDecimal numBalanceSeats;
    
    @ManyToOne 
    private FlightSchedule flightSchedule;
    
    @ManyToOne
    private AircraftConfiguration aircraftConfig;
    
    @OneToMany (mappedBy = "cabinClass")
    private List<Seat> seatList;
    
    @OneToMany (mappedBy= "cabinClass") 
    private List<Fare> fare;
    
    public CabinClass() {
    }

    public CabinClass(int type, BigDecimal seatingCapacity, BigDecimal numAisles, BigDecimal numRows, BigDecimal numSeatAbreast, BigDecimal numAvailableSeats, BigDecimal numReservedSeats, BigDecimal numBalanceSeats) {
        this.type = CabinClassType.fromValue(type);
        this.seatingCapacity = seatingCapacity;
        this.numAisles = numAisles;
        this.numRows = numRows;
        this.numSeatAbreast = numSeatAbreast;
        this.numAvailableSeats = numAvailableSeats;
        this.numReservedSeats = numReservedSeats;
        this.numBalanceSeats = numBalanceSeats;
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
     * @return the numAvailableSeats
     */
    public BigDecimal getNumAvailableSeats() {
        return numAvailableSeats;
    }

    /**
     * @param numAvailableSeats the numAvailableSeats to set
     */
    public void setNumAvailableSeats(BigDecimal numAvailableSeats) {
        this.numAvailableSeats = numAvailableSeats;
    }

    /**
     * @return the numReservedSeats
     */
    public BigDecimal getNumReservedSeats() {
        return numReservedSeats;
    }

    /**
     * @param numReservedSeats the numReservedSeats to set
     */
    public void setNumReservedSeats(BigDecimal numReservedSeats) {
        this.numReservedSeats = numReservedSeats;
    }

    /**
     * @return the numBalanceSeats
     */
    public BigDecimal getNumBalanceSeats() {
        return numBalanceSeats;
    }

    /**
     * @param numBalanceSeats the numBalanceSeats to set
     */
    public void setNumBalanceSeats(BigDecimal numBalanceSeats) {
        this.numBalanceSeats = numBalanceSeats;
    }

    /**
     * @return the flightSchedule
     */
    public FlightSchedule getFlightSchedule() {
        return flightSchedule;
    }

    /**
     * @param flightSchedule the flightSchedule to set
     */
    public void setFlightSchedule(FlightSchedule flightSchedule) {
        this.flightSchedule = flightSchedule;
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
     * @return the seatList
     */
    public List<Seat> getSeatList() {
        return seatList;
    }

    /**
     * @param seatList the seatList to set
     */
    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
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
     * @return the fare
     */
    public List<Fare> getFare() {
        return fare;
    }

    /**
     * @param fare the fare to set
     */
    public void setFare(List<Fare> fare) {
        this.fare = fare;
    }

    /**
     * @return the fare
     */

}
