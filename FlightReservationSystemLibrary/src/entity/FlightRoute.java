/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import util.enumeration.FlightRouteStatus;

/**
 *
 * @author apple
 */
//@Cacheable(false)
@Entity
@NamedQueries({
    @NamedQuery(
        name = "viewAllFlightRoutes",
        query = "SELECT fr FROM FlightRoute fr"
    ),
    @NamedQuery(
            name = "checkForExistingFlightRoute",
            query = "SELECT fr FROM FlightRoute fr WHERE fr.origin = :origin AND fr.destination = :destination"
    )
})

public class FlightRoute implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private FlightRouteStatus status;
    
    @OneToMany (mappedBy = "flightRoute")
    @JoinColumn(nullable = false)
    private List<Flight> flightList;
    
    @ManyToOne 
    private Airport origin;
    
    @ManyToOne 
    private Airport destination;
    
    public FlightRoute() {
    }

    public FlightRoute(Airport origin, Airport destination,int routeStatus) {
        this.origin = origin;
        this.destination = destination;
        this.status = FlightRouteStatus.fromValue(routeStatus);
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
        if (!(object instanceof FlightRoute)) {
            return false;
        }
        FlightRoute other = (FlightRoute) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FlightRoute[ id=" + id + " ]";
    }


    /**
     * @return the status
     */
    public FlightRouteStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(FlightRouteStatus status) {
        this.status = status;
    }

    /**
     * @return the flightList
     */
    public List<Flight> getFlightList() {
        return flightList;
    }

    /**
     * @param flightList the flightList to set
     */
    public void setFlightList(List<Flight> flightList) {
        this.flightList = flightList;
    }

    /**
     * @return the origin
     */
    public Airport getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    /**
     * @return the destination
     */
    public Airport getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(Airport destination) {
        this.destination = destination;
    }


    
}
