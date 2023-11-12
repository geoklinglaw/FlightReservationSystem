/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import util.enumeration.FlightScheduleStatus;


/**
 *
 * @author apple
 */
@Entity
@NamedQueries({
    @NamedQuery(
        name = "viewAllFlightSchedulePlan",
        query = "SELECT fsp FROM FlightSchedulePlan fsp JOIN fsp.flight f ORDER BY f.flightNumber"
    )
})
@Inheritance(strategy = InheritanceType.JOINED)
public class FlightSchedulePlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private FlightScheduleStatus type;
    
    @OneToMany (mappedBy = "flightSchedulePlan")
    private List<FlightSchedule> flightSchedule;
    
    @ManyToOne
    private Flight flight;

    public FlightSchedulePlan() {
    }

    public FlightSchedulePlan(int type, Flight flight) {
        this.type = FlightScheduleStatus.fromValue(type);
        this.flightSchedule = new ArrayList<FlightSchedule>();
        this.flight = flight;
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
        if (!(object instanceof FlightSchedulePlan)) {
            return false;
        }
        FlightSchedulePlan other = (FlightSchedulePlan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FlightSchedulePlan[ id=" + id + " ]";
    }

    /**
     * @return the type
     */
    public FlightScheduleStatus getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(FlightScheduleStatus type) {
        this.type = type;
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
     * @return the flightSchedule
     */
    public List<FlightSchedule> getFlightSchedule() {
        return flightSchedule;
    }

    /**
     * @param flightSchedule the flightSchedule to set
     */
    public void setFlightSchedule(List<FlightSchedule> flightSchedule) {
        this.flightSchedule = flightSchedule;
    }




    
}
