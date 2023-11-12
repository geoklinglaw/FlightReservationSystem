/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.FlightRoute;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author apple
 */
@Local
public interface FlightRouteSessionBeanLocal {
    
    public void createNewFlightRoute(FlightRoute flightRoute);
    
    public List<FlightRoute> viewAllFlightRoute();
    
    public void deleteFlightRoute(FlightRoute route);
    
    public FlightRoute retrieveFlightRouteById(Long id);
    
}
