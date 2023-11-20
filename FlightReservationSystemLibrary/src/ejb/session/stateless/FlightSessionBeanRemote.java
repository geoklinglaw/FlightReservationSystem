/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Flight;
import java.util.List;
import javax.ejb.Remote;
import util.exception.FlightExistsException;

/**
 *
 * @author apple
 */
@Remote
public interface FlightSessionBeanRemote {
    
    public Long createNewFlight(Flight flight);
    
    public List<Flight> viewAllFlight();
    
    public Flight retrieveFlightById(Long id);

    public Flight retrieveFlightByNumber(String flightNum);
    
    public void deleteFlight(Flight flight);
    
    public boolean checkFlightByNumber(String flightNum) throws FlightExistsException;


    public Flight retrieveFlightByNumber(String flightNum, boolean needSeats);


    
}
