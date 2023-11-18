/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Airport;
import entity.FlightRoute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.util.Pair;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author apple
 */
@Stateless
public class FlightRouteSessionBean implements FlightRouteSessionBeanRemote, FlightRouteSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em; 


    @Override
    public void createNewFlightRoute(FlightRoute flightRoute) {
        em.persist(flightRoute);
    }
    
    @Override
    public List<FlightRoute> viewAllFlightRoute() {
        em.setProperty ("javax.persistence.cache.storeMode", "REFRESH"); 
        List<FlightRoute> flightRoutes = em.createNamedQuery("viewAllFlightRoutes").getResultList();

        // Sort the entire list based on the country of the origin airport
        flightRoutes.sort(Comparator.comparing(route -> route.getOrigin().getCountry()));
      
        return flightRoutes;
    }



    public void deleteFlightRoute(FlightRoute route) {
        em.remove(route);
    }
    
    public FlightRoute retrieveFlightRouteById(Long id) {
        FlightRoute route = em.find(FlightRoute.class, id);
        int size = route.getFlightList().size();
        
        return route;
    }
    
    public FlightRoute findSpecificFlightRoute(Airport origin, Airport destination) {
        String iataO = origin.getAirportCode();
        String iataD = destination.getAirportCode();

        Query query = em.createQuery("SELECT fr FROM FlightRoute fr WHERE fr.origin.airportCode = :inOrigin AND fr.destination.airportCode = :inDestination")
                    .setParameter("inOrigin", iataO)
                    .setParameter("inDestination", iataD);

        return (FlightRoute) query.getSingleResult();
    }

    public List<FlightRoute> findOriginFlightRoute(String originIATA) {
        Query query = em.createQuery("SELECT fr FROM FlightRoute fr WHERE fr.origin.airportCode = :inOrigin")
                    .setParameter("inOrigin", originIATA);

        return (List<FlightRoute>) query.getResultList();
    }

    public List<FlightRoute> findDestFlightRoute(String destIATA) {
        Query query = em.createQuery("SELECT fr FROM FlightRoute fr WHERE fr.origin.airportCode = :inDestination")
                    .setParameter("inOrigin", destIATA);

        return (List<FlightRoute>) query.getResultList();
    }

//    public FlightRoute findSpecificFlightRouteWithCode(String iataO, String destinatiiataDon) {
//        String iataO = origin.getAirportCode();
//        String iataD = destination.getAirportCode();
//
//        Query query = em.createQuery("SELECT fr FROM FlightRoute fr WHERE fr.origin.airportCode = :inOrigin AND fr.destination.airportCode = :inDestination")
//                    .setParameter("inOrigin", iataO)
//                    .setParameter("inDestination", iataD);
//
//        return (FlightRoute) query.getSingleResult();
//    }
}
