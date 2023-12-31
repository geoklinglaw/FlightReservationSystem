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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author apple
 */
@Entity
public class FlightCabinClass implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private BigDecimal numAvailableSeats;
    @Column(nullable = false)
    private BigDecimal numReservedSeats;
    @Column(nullable = false)
    private BigDecimal numBalanceSeats;
    
    @ManyToOne
    private CabinClass cabinClass;
    
    @ManyToOne 
    private FlightSchedule flightSchedule;
    
    @OneToMany (mappedBy = "flightCabinClass")
    private List<Seat> seatList;

    @OneToOne (mappedBy= "flightCabinClass") 
    private Fare fare;
    
    
    public FlightCabinClass() {
    }

    public FlightCabinClass(BigDecimal numAvailableSeats, BigDecimal numReservedSeats, BigDecimal numBalanceSeats) {
        this.numAvailableSeats = numAvailableSeats;
        this.numReservedSeats = numReservedSeats;
        this.numBalanceSeats = numBalanceSeats;
        this.seatList = new ArrayList<>();
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
        if (!(object instanceof FlightCabinClass)) {
            return false;
        }
        FlightCabinClass other = (FlightCabinClass) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FlightCabinClass[ id=" + id + " ]";
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
     * @return the cabinClass
     */
    public CabinClass getCabinClass() {
        return cabinClass;
    }

    /**
     * @param cabinClass the cabinClass to set
     */
    public void setCabinClass(CabinClass cabinClass) {
        this.cabinClass = cabinClass;
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
     * @return the fare
     */
    public Fare getFare() {
        return fare;
    }

    /**
     * @param fare the fare to set
     */
    public void setFare(Fare fare) {
        this.fare = fare;
    }
    
}
