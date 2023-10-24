/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author apple
 */
@Entity
public class FlightBooking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Flight flight;
    private List<Person> passengerList;
    private BigDecimal price;
    private CabinClass cabin;
    private List<Seat> seatList;
    private String fareBasisCode;

    public FlightBooking() {
    }

    public FlightBooking(Flight flight, List<Person> passengerList, BigDecimal price, CabinClass cabin, List<Seat> seatList, String fareBasisCode) {
        this.flight = flight;
        this.passengerList = passengerList;
        this.price = price;
        this.cabin = cabin;
        this.seatList = seatList;
        this.fareBasisCode = fareBasisCode;
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
        if (!(object instanceof FlightBooking)) {
            return false;
        }
        FlightBooking other = (FlightBooking) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FlightBooking[ id=" + id + " ]";
    }

    /**
     * @return the flight
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * @param flight the flight to set
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * @return the passengerList
     */
    public List<Person> getPassengerList() {
        return passengerList;
    }

    /**
     * @param passengerList the passengerList to set
     */
    public void setPassengerList(List<Person> passengerList) {
        this.passengerList = passengerList;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the cabin
     */
    public CabinClass getCabin() {
        return cabin;
    }

    /**
     * @param cabin the cabin to set
     */
    public void setCabin(CabinClass cabin) {
        this.cabin = cabin;
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
    
}
