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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

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
    
    @ManyToMany (mappedBy = "flightBooking")
    private List<Seat> seatBookings;
    
    @ManyToOne (optional = false)
    private FlightSchedule flightSchedule;

    @ManyToOne
    private FlightReservation flightReservation;
    
    public FlightBooking() {
    }

    public FlightBooking(FlightSchedule flightSchedule, List<Seat> seatList) {
        this.flightSchedule = flightSchedule;
        this.seatBookings = seatList;
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
     * @return the seatBookings
     */
    public List<Seat> getSeatBookings() {
        return seatBookings;
    }

    /**
     * @param seatBookings the seatBookings to set
     */
    public void setSeatBookings(List<Seat> seatBookings) {
        this.seatBookings = seatBookings;
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
     * @return the flightReservation
     */
    public FlightReservation getFlightReservation() {
        return flightReservation;
    }

    /**
     * @param flightReservation the flightReservation to set
     */
    public void setFlightReservation(FlightReservation flightReservation) {
        this.flightReservation = flightReservation;
    }



    
}
