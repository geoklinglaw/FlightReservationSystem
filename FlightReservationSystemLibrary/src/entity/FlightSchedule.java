/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import util.enumeration.FlightScheduleStatus;


/**
 *
 * @author apple
 */
@Entity
public class FlightSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Date departureTime;
    @Column(nullable = false)
    private double flightDuration;
    @Column(nullable = false)
    private Date arrivalTime;
    
    
    @ManyToOne (optional = false)
    private FlightSchedulePlan flightSchedulePlan;
    
    @OneToMany (mappedBy= "flightSchedule", fetch = FetchType.EAGER)
    private List<FlightCabinClass> flightCabinClass;
    
    @OneToMany (mappedBy= "flightSchedule")
    private List<FlightBooking> flightBookings;

    public FlightSchedule() {
//        this.cabinClass = new ArrayList<>();
    }

    public FlightSchedule(FlightSchedulePlan flightSchedulePlan) {
        this.flightSchedulePlan = flightSchedulePlan;
//        this.cabinClass = cabinClassList;
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
        if (!(object instanceof FlightSchedule)) {
            return false;
        }
        FlightSchedule other = (FlightSchedule) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FlightSchedule[ id=" + id + " ]";
    }

    /**
     * @return the departureTime
     */
    public Date getDepartureTime() {
        return departureTime;
    }

    /**
     * @param departureTime the departureTime to set
     */
    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * @return the flightDuration
     */
    public double getFlightDuration() {
        return flightDuration;
    }

    /**
     * @param flightDuration the flightDuration to set
     */
    public void setFlightDuration(Duration duration) {
        long totalMinutes = duration.toMinutes();
        double hours = totalMinutes / 60;
        double remainingMinutes = totalMinutes % 60;
        this.flightDuration = hours + remainingMinutes / 60.0;
    }


    /**
     * @return the arrivalTime
     */
    public Date getArrivalTime() {
        return arrivalTime;
    }

    /**
     * @param arrivalTime the arrivalTime to set
     */
    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * @return the flightSchedulePlan
     */
    public FlightSchedulePlan getFlightSchedulePlan() {
        return flightSchedulePlan;
    }

    /**
     * @param flightSchedulePlan the flightSchedulePlan to set
     */
    public void setFlightSchedulePlan(FlightSchedulePlan flightSchedulePlan) {
        this.flightSchedulePlan = flightSchedulePlan;
    }

//    /**
//     * @return the cabinClass
//     */
//    public List<CabinClass> getCabinClass() {
//        return cabinClass;
//    }
//
//    /**
//     * @param cabinClass the cabinClass to set
//     */
//    public void setCabinClass(List<CabinClass> cabinClass) {
//        this.cabinClass = cabinClass;
//    }

    /**
     * @return the flightBookings
     */
    public List<FlightBooking> getFlightBookings() {
        return flightBookings;
    }

    /**
     * @param flightBookings the flightBookings to set
     */
    public void setFlightBookings(List<FlightBooking> flightBookings) {
        this.flightBookings = flightBookings;
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
   
    
}
