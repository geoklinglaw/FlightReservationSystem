/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.CabinClass;
import entity.Flight;
import entity.FlightRoute;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import util.exception.FlightExistsException;

/**
 *
 * @author apple
 */
@Stateless
public class FlightSessionBean implements FlightSessionBeanRemote, FlightSessionBeanLocal {



    @EJB(name = "CabinClassSessionBeanLocal")
    private CabinClassSessionBeanLocal cabinClassSessionBeanLocal;

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewFlight(Flight flight) {
        em.persist(flight);
        em.flush();
        return flight.getId();
    }
    
    public List<Flight> viewAllFlight() {
        List<Flight> flightList = em.createNamedQuery("viewAllFlight").getResultList();
        for (Flight flight: flightList) {
            FlightRoute route = flight.getFlightRoute();
        }
        return flightList;
    }
    
    public Flight retrieveFlightById(Long id) {
        Flight flight = em.find(Flight.class, id);
        return flight;
    }
    
            
    public Flight retrieveFlightByNumber(String flightNum) {
        Flight flight = (Flight) em.createNamedQuery("selectFlight").setParameter("inNum", flightNum).getSingleResult();
        if (flight.getFlightSchedulePlan() != null) {
            int size = flight.getFlightSchedulePlan().getFlightSchedule().size();
        }

        return flight;
    }
    
    
    public Flight retrieveFlightByNumber(String flightNum, boolean needSeats) {
        Flight flight = (Flight) em.createNamedQuery("selectFlight").setParameter("inNum", flightNum).getSingleResult();
//        int size2 = flight.getFlightSchedulePlan().getFlightSchedule().get(0).getCabinClass().size();
//        List<CabinClass> ccList = flight.getFlightSchedulePlan().getFlightSchedule().get(0).getCabinClass();
        
//        for (CabinClass cc: ccList) {
//            CabinClass managedCC = cabinClassSessionBeanLocal.retrieveCabinClassById(cc.getId());
//            int size3 = managedCC.getSeatList().size();
//        }

        return flight;
    }
    
    public boolean checkFlightByNumber(String flightNum, boolean needSeats) throws FlightExistsException {
    try {
        Flight flight = (Flight) em.createNamedQuery("selectFlight").setParameter("inNum", flightNum).getSingleResult();

        // If a flight is found, throw an exception
        throw new FlightExistsException("Flight already exists!");
    } catch (NoResultException ex) {
        return false;
    } 
    }
    
    public void deleteFlight(Flight flight) {
        em.remove(flight);
    }
    

}
