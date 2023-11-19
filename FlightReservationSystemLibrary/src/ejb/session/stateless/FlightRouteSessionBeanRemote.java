/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Airport;
import entity.FlightRoute;
import entity.FlightSchedule;
import java.util.List;
import javafx.util.Pair;
import javax.ejb.Remote;

/**
 *
 * @author apple
 */
@Remote
public interface FlightRouteSessionBeanRemote {
    
    public void createNewFlightRoute(FlightRoute flightRoute);
    
    public List<FlightRoute> viewAllFlightRoute();
    
    public FlightRoute retrieveFlightRouteById(Long id);
    
    public FlightRoute findSpecificFlightRoute(Airport origin, Airport destination);
    
    public List<FlightRoute> findOriginFlightRoute(String destIATA);
    
    public List<FlightRoute> findDestFlightRoute(String destIATA);
    
    public FlightRoute findSpecificFlightRouteWithCode(String iataO, String iataD);
    
    public List<Pair<FlightSchedule, FlightSchedule>> filterConnectingFS();


}
