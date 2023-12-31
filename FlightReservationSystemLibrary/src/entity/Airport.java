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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author apple
 */
@Entity
@NamedQueries({
    @NamedQuery(
        name = "viewAllAirports",
        query = "SELECT ap FROM Airport ap"
    ),
    @NamedQuery(
        name = "retrieveAirportByCode",
        query = "SELECT ap FROM Airport ap WHERE ap.airportCode = :airportCode"
    )

})
public class Airport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String airportCode;
    @Column(nullable = false)
    private String airportName;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String country;
    
    @OneToMany (mappedBy = "origin")
    private List<FlightRoute> flightRouteOrigin;
    
    @OneToMany (mappedBy = "destination")
    private List<FlightRoute> flightRouteDestination;

    public Airport() {
    }

    public Airport(String airportCode, String airportName, String city, String state, String country) {
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.city = city;
        this.state = state;
        this.country = country;
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
        if (!(object instanceof Airport)) {
            return false;
        }
        Airport other = (Airport) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Airport[ id=" + id + " ]";
    }

    /**
     * @return the airportCode
     */
    public String getAirportCode() {
        return airportCode;
    }

    /**
     * @param airportCode the airportCode to set
     */
    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    /**
     * @return the airportName
     */
    public String getAirportName() {
        return airportName;
    }

    /**
     * @param airportName the airportName to set
     */
    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the flightRouteOrigin
     */
    public List<FlightRoute> getFlightRouteOrigin() {
        return flightRouteOrigin;
    }

    /**
     * @param flightRouteOrigin the flightRouteOrigin to set
     */
    public void setFlightRouteOrigin(List<FlightRoute> flightRouteOrigin) {
        this.flightRouteOrigin = flightRouteOrigin;
    }

    /**
     * @return the flightRouteDestination
     */
    public List<FlightRoute> getFlightRouteDestination() {
        return flightRouteDestination;
    }

    /**
     * @param flightRouteDestination the flightRouteDestination to set
     */
    public void setFlightRouteDestination(List<FlightRoute> flightRouteDestination) {
        this.flightRouteDestination = flightRouteDestination;
    }


    
}
