/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import util.enumeration.FlightStatus;
import util.enumeration.FlightType;

/**
 *
 * @author apple
 */
@Entity
@NamedQueries({
    @NamedQuery(
        name = "viewAllFlight",
        query = "SELECT f FROM Flight f ORDER BY f.flightNumber"
    ),
    @NamedQuery(
        name = "selectFlight",
        query = "SELECT f FROM Flight f WHERE f.flightNumber = :inNum"
    )

})
public class Flight implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String flightNumber;
    @Column(nullable = false)
    private FlightStatus status;
    
    @ManyToOne 
    private AircraftConfiguration aircraftConf;
    
    @ManyToOne
    private FlightRoute flightRoute;
    
    @ManyToOne 
    @JoinColumn (nullable=true)
    private FlightSchedulePlan flightSchedulePlan;


    public Flight() {
    }

    public Flight(String flightNumber, int status) {
        this.flightNumber = flightNumber;
        this.status = FlightStatus.fromValue(status);
        
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
        if (!(object instanceof Flight)) {
            return false;
        }
        Flight other = (Flight) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Flight[ id=" + id + " ]";
    }

    /**
     * @return the flightNumber
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * @param flightNumber the flightNumber to set
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * @return the status
     */
    public FlightStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    /**
     * @return the aircraftConfig
     */
    public AircraftConfiguration getAircraftConfig() {
        return aircraftConf;
    }

    /**
     * @param aircraftConfig the aircraftConfig to set
     */
    public void setAircraftConfig(AircraftConfiguration aircraftConfig) {
        this.aircraftConf = aircraftConfig;
    }

    /**
     * @return the fightRoute
     */
    public FlightRoute getFlightRoute() {
        return flightRoute;
    }

    /**
     * @param fightRoute the fightRoute to set
     */
    public void setFlightRoute(FlightRoute fightRoute) {
        this.flightRoute = fightRoute;
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
    
}
