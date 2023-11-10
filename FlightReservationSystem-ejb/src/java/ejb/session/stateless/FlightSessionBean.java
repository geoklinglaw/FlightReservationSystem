/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Flight;
import entity.FlightRoute;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author apple
 */
@Stateless
public class FlightSessionBean implements FlightSessionBeanRemote, FlightSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    @Override
    public void createNewFlight(Flight flight) {
        em.persist(flight);
    }
    
    public List<Flight> viewAllFlight() {
        List<Flight> flightList = em.createNamedQuery("viewAllFlight").getResultList();
        for (Flight flight: flightList) {
            FlightRoute route = flight.getFlightRoute();
            int size1 = route.getAirportList().size();
        }
        return flightList;
    }
    
    public Flight retrieveFlightById(Long id) {
        Flight flight = em.find(Flight.class, id);
        int size1 = flight.getFlightRoute().getAirportList().size();

        return flight;
    }
    
            
    public Flight retrieveFlightByNumber(String flightNum) {
        Flight flight = (Flight) em.createNamedQuery("selectFlight").setParameter("inNum", flightNum).getSingleResult();
        int size1 = flight.getFlightRoute().getAirportList().size();

        return flight;
    }
    
    public void deleteFlight(Flight flight) {
        em.remove(flight);
    }
}
