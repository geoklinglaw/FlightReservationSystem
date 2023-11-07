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
import javax.persistence.TypedQuery;

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
    
    public Airport retrieveAirport(Long id) {
        Airport airport = em.find(Airport.class, id);
        return airport;
    }
    
    public List<Airport> retrieveAllAirports() {
        List<Airport> airportList = em.createNamedQuery("viewAllAirports").getResultList();
        return airportList;
      
    }
    
    public Airport retrieveAirportByCode(String code) {
        Query query = em.createNamedQuery("retrieveAirportByCode", Airport.class);
        query.setParameter("airportCode", code);
        Airport airport = (Airport) query.getSingleResult();
        airport.getFlightRoute().size();
        
        return airport;
        
    }
        


}
