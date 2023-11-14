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
        List<FlightRoute> flightRoutes = em.createNamedQuery("viewAllFlightRoutes").getResultList();

        // Sort the entire list based on the country of the origin airport
        flightRoutes.sort(Comparator.comparing(route -> route.getAirportList().get(0).getCountry()));

        List<FlightRoute> sortedRoutes = new ArrayList<>();
        Set<Long> processedRoutes = new HashSet<>(); // Set to track processed routes

        // Process each route to find and pair with its return route
        for (FlightRoute directRoute : flightRoutes) {
            if (processedRoutes.contains(directRoute.getId())) {
                continue; // Skip if this route is already processed
            }

            Airport origin = directRoute.getAirportList().get(0);
            Airport destination = directRoute.getAirportList().get(1);

            // Add the direct route
            sortedRoutes.add(directRoute);
            processedRoutes.add(directRoute.getId()); // Mark this route as processed

            // Find and add its return route, if exists
            for (FlightRoute potentialReturnRoute : flightRoutes) {
                if (processedRoutes.contains(potentialReturnRoute.getId())) {
                    continue; // Skip if this route is already processed
                }

                Airport returnOrigin = potentialReturnRoute.getAirportList().get(0);
                Airport returnDestination = potentialReturnRoute.getAirportList().get(1);

                // Check if it's a return route
                if (origin.equals(returnDestination) && destination.equals(returnOrigin)) {
                    sortedRoutes.add(potentialReturnRoute);
                    processedRoutes.add(potentialReturnRoute.getId()); // Mark return route as processed
                    break; // Break after adding the return route
                }
            }
        }

        return sortedRoutes;
    }



    public void deleteFlightRoute(FlightRoute route) {
        em.remove(route);
    }
    
    public FlightRoute retrieveFlightRouteById(Long id) {
        FlightRoute route = em.find(FlightRoute.class, id);
        
        return route;
    }



}
