/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import util.enumeration.SeatStatus;

/**
 *
 * @author apple
 */
@Entity
public class Seat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String seatID;
    @Column(nullable = false)
    private SeatStatus seatStatus;
    
    @ManyToOne (optional = false)
    @JoinColumn(nullable = false)
    private CabinClass cabinClass;
    
    
    @ManyToMany 
    private List<FlightBooking> flightBooking;
    

    public Seat() {
    }

    public Seat(String seatID, int seatStatus) {
        this.seatID = seatID;
        this.seatStatus = SeatStatus.fromValue(seatStatus);
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
        if (!(object instanceof Seat)) {
            return false;
        }
        Seat other = (Seat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Seat[ id=" + id + " ]";
    }

    /**
     * @return the seatID
     */
    public String getSeatID() {
        return seatID;
    }

    /**
     * @param seatID the seatID to set
     */
    public void setSeatID(String seatID) {
        this.seatID = seatID;
    }

    /**
     * @return the seatStatus
     */
    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    /**
     * @param seatStatus the seatStatus to set
     */
    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }


    /**
     * @return the flightBooking
     */
    public List<FlightBooking> getFlightBooking() {
        return flightBooking;
    }

    /**
     * @param flightBooking the flightBooking to set
     */
    public void setFlightBooking(List<FlightBooking> flightBooking) {
        this.flightBooking = flightBooking;
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
    
}

