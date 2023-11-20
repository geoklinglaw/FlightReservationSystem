/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.AircraftType;
import entity.Airport;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.AirportNotAvailableException;

/**
 *
 * @author apple
 */
@Stateless
public class AirportEntitySessionBean implements AirportEntitySessionBeanRemote, AirportEntitySessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    public Long createNewAirport(Airport airport) {
        em.persist(airport);
        em.flush();
        
        return airport.getId();
    }
    
    public Airport retrieveAirport(Long id) throws AirportNotAvailableException {
        if (id == null) {
            throw new AirportNotAvailableException("There is no existing Airport ID !");
        }

        Airport airport = em.find(Airport.class, id);
        if (airport == null) {
            throw new AirportNotAvailableException("Airport with ID " + id + " not found.");
        }
        return airport;
    }



    
    public List<Airport> retrieveAllAirports() {
        List<Airport> airportList = em.createNamedQuery("viewAllAirports").getResultList();
        return airportList;
      
    }

    public Airport retrieveAirportByIATA(String code) {
        
        Query query = em.createNamedQuery("retrieveAirportByCode", Airport.class);
        query.setParameter("airportCode", code);
        Airport airport =  (Airport)query.getSingleResult();
        
        airport.getFlightRouteDestination().size();
        airport.getFlightRouteOrigin().size();

        return airport;

    }
    
    public Airport retrieveAirportByCode(String code) throws AirportNotAvailableException {
        Query query = em.createNamedQuery("retrieveAirportByCode", Airport.class);
        query.setParameter("airportCode", code);
        Airport airport =  (Airport)query.getSingleResult();
        
        if (airport == null) {
            throw new AirportNotAvailableException("There are no such airport registered!");
        }
        airport.getFlightRouteDestination().size();
        airport.getFlightRouteOrigin().size();

        return airport;

    }
}
