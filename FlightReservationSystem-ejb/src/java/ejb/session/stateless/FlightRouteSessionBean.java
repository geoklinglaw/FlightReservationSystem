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
        List<FlightRoute> sortedFlightRoutes = flightRoutes;
        
        // Sort the entire list based on the country of the origin airport
        sortedFlightRoutes.sort(Comparator.comparing(route -> route.getAirportList().get(0).getCountry()));
        
        return sortedFlightRoutes;
    }



    public void deleteFlightRoute(FlightRoute route) {
        em.remove(route);
    }
    
    public FlightRoute retrieveFlightRouteById(Long id) {
        FlightRoute route = em.find(FlightRoute.class, id);
        
        return route;
    }



}
