/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Airport;
import entity.FlightRoute;
import entity.FlightSchedule;
import java.util.List;
import javafx.util.Pair;
import javax.ejb.Local;
import util.exception.FlightExistsForFlightRouteException;
import util.exception.FlightRouteExistsException;
import util.exception.FlightRouteNotFoundException;

/**
 *
 * @author apple
 */
@Local
public interface FlightRouteSessionBeanLocal {
    
    public void createNewFlightRoute(FlightRoute flightRoute) throws FlightRouteExistsException;

    public void createNewFlightRoute(FlightRoute flightRoute, boolean data);  
    
    public List<FlightRoute> viewAllFlightRoute();
    
    public void deleteFlightRoute(FlightRoute route) throws FlightExistsForFlightRouteException;
    
    public FlightRoute retrieveFlightRouteById(Long id);
    
    
    public List<FlightRoute> findOriginFlightRoute(String destIATA);
    
    public List<FlightRoute> findDestFlightRoute(String destIATA);
    
    public List<Pair<FlightSchedule, FlightSchedule>> filterConnectingFS();
    
    public FlightRoute findSpecificFlightRoute(Airport origin, Airport destination) throws FlightRouteNotFoundException;

    public FlightRoute findSpecificFlightRouteWithCode(String iataO, String iataD) throws FlightRouteNotFoundException;


    
}
