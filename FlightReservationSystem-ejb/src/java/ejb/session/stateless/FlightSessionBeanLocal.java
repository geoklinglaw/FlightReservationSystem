/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Flight;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author apple
 */
@Local
public interface FlightSessionBeanLocal {
    
    public Long createNewFlight(Flight flight);
    
    public List<Flight> viewAllFlight();
    
    public Flight retrieveFlightById(Long id);
    
    public Flight retrieveFlightByNumber(String flightNum);
    
    public void deleteFlight(Flight flight);
    
    public Flight retrieveFlightByNumber(String flightNum, boolean needSeats);
    
}
