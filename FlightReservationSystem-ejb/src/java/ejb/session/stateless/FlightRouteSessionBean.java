/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Airport;
import entity.FlightRoute;
import entity.FlightSchedule;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.util.Pair;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.FlightExistsForFlightRouteException;
import util.exception.FlightRouteExistsException;
import util.exception.FlightRouteNotFoundException;

/**
 *
 * @author apple
 */
@Stateless
public class FlightRouteSessionBean implements FlightRouteSessionBeanRemote, FlightRouteSessionBeanLocal {

    @EJB(name = "FlightSchedulePlanSessionBeanLocal")
    private FlightSchedulePlanSessionBeanLocal flightSchedulePlanSessionBeanLocal;

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em; 


    @Override
    public void createNewFlightRoute(FlightRoute flightRoute) throws FlightRouteExistsException {
        Airport origin = flightRoute.getOrigin();
        Airport destination = flightRoute.getDestination();
        
        Query query = em.createNamedQuery("checkForExistingFlightRoute");
        query.setParameter("origin", origin);
        query.setParameter("destination", destination);
        
        try {
            FlightRoute existingRoute = (FlightRoute) query.getSingleResult();
            throw new FlightRouteExistsException("Flight route with the same origin and destination already exists!");
        } catch (NoResultException ex) {
            em.persist(flightRoute);
        }
    }
    
    
    @Override
    public void createNewFlightRoute(FlightRoute flightRoute, boolean data) {
        em.persist(flightRoute);
    }
    
    @Override
    public List<FlightRoute> viewAllFlightRoute() {
//        em.setProperty ("javax.persistence.cache.storeMode", "REFRESH"); 
        List<FlightRoute> flightRoutes = em.createNamedQuery("viewAllFlightRoutes").getResultList();
        System.out.println(flightRoutes.size());

        // Sort the entire list based on the country of the origin airport
        flightRoutes.sort(Comparator.comparing(route -> route.getOrigin().getCountry()));
      
        return flightRoutes;
    }



    public void deleteFlightRoute(FlightRoute route) throws FlightExistsForFlightRouteException {
        
        FlightRoute r = em.find(FlightRoute.class, route.getId());
        if (r.getFlightList().size() > 0) {
            throw new FlightExistsForFlightRouteException("Flight route has a flight!");
        } else {
            em.remove(route);
        }
        
    }
    
    public FlightRoute retrieveFlightRouteById(Long id) {
        FlightRoute route = em.find(FlightRoute.class, id);
        int size = route.getFlightList().size();
        
        return route;
    }
    
    @Override
    public FlightRoute findSpecificFlightRoute(Airport origin, Airport destination) throws FlightRouteNotFoundException {
        try {
            String iataO = origin.getAirportCode();
            String iataD = destination.getAirportCode();

            Query query = em.createQuery("SELECT fr FROM FlightRoute fr WHERE fr.origin.airportCode = :inOrigin AND fr.destination.airportCode = :inDestination")
                        .setParameter("inOrigin", iataO)
                        .setParameter("inDestination", iataD);

            if (query == null) {
                throw new FlightRouteNotFoundException("Cannot find flight route!");
                
            }
            return (FlightRoute) query.getSingleResult();
        } catch (FlightRouteNotFoundException ex) {
            throw new FlightRouteNotFoundException("Cannot find flight route!");
        }
        

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

    public FlightRoute findSpecificFlightRouteWithCode(String iataO, String iataD) throws FlightRouteNotFoundException {
        try {
            Query query = em.createQuery("SELECT fr FROM FlightRoute fr WHERE fr.origin.airportCode = :inOrigin AND fr.destination.airportCode = :inDestination")
                    .setParameter("inOrigin", iataO)
                    .setParameter("inDestination", iataD);

            return (FlightRoute) query.getSingleResult();
        } catch (NoResultException ex) {
            throw new FlightRouteNotFoundException("Cannot find flight route with specified origin and destination codes.");
        }
    }

    
    public List<Pair<FlightSchedule, FlightSchedule>> filterConnectingFS() {
        Query query = em.createQuery(
            "SELECT A, B FROM FlightSchedule A " +
            "JOIN A.flightSchedulePlan Asp " +
            "JOIN Asp.flight Af " +
            "JOIN Af.flightRoute Afr " +
            "JOIN FlightSchedule B " +
            "JOIN B.flightSchedulePlan Bsp " +
            "JOIN Bsp.flight Bf " +
            "JOIN Bf.flightRoute Bfr " +
            "WHERE Afr.destination = Bfr.origin " +
            "AND A.arrivalTime < B.departureTime "
//            "FUNCTION('HOUR_DIFF', B.departureTime, A.arrivalTime) < 24"
        );

        List<Object[]> results = query.getResultList();
        List<Pair<FlightSchedule, FlightSchedule>> filteredResults = filterFlightsWithin24Hours(results);
        
        for (Object[] result : results) {
            FlightSchedule flightA = (FlightSchedule) result[0];
            FlightSchedule flightB = (FlightSchedule) result[1];

            System.out.println("Flight A Schedule: " + flightA.getDepartureTime());
            System.out.println("Flight B Schedule: " + flightB.getDepartureTime());
        }
        
        return filteredResults;
    
    }
    
    public List<Pair<FlightSchedule, FlightSchedule>> filterFlightsWithin24Hours(List<Object[]> results) {
        List<Pair<FlightSchedule, FlightSchedule>> filteredPairs = new ArrayList<>();

        for (Object[] result : results) {
            FlightSchedule flightA = (FlightSchedule) result[0];
//            flightSchedulePlanSessionBeanLocal.retrieveFlightScheduleById(flightA.getId());
            
            FlightSchedule flightB = (FlightSchedule) result[1];
//            flightSchedulePlanSessionBeanLocal.retrieveFlightScheduleById(flightB.getId());

            // Calculate the time difference in hours between arrivalTime of flightA and departureTime of flightB
            long timeDifferenceHours = getTimeDifferenceInHours(flightA.getArrivalTime(), flightB.getDepartureTime());

            if (timeDifferenceHours < 24) {
                filteredPairs.add(new Pair<>(flightA, flightB));
            }
        }

        return filteredPairs;
    }

    private long getTimeDifferenceInHours(Date startTime, Date endTime) {
        long millisecondsDifference = endTime.getTime() - startTime.getTime();
        return millisecondsDifference / (60 * 60 * 1000); // Convert milliseconds to hours
    }

}
