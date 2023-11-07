/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.AircraftConfiguration;
import entity.Airport;
import entity.CabinClass;
import entity.FlightRoute;
import entity.Seat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author apple
 */
@Local
public interface FRSManagementSessionBeanLocal {
    
    public void createAircraftConfiguration(int aircraftType, List<Integer> ccList);
    
    public List<AircraftConfiguration> viewAllAircraftConfiguration();
    
    public List<Seat> createSeatsPerCabinClass(int numSeatAbreast, int numRows, CabinClass cabinClass);
    
    public List<Airport> viewAllAirports();
    
    public void createFlightRoute(Long originId, Long destId);
    
    public List<FlightRoute> viewAllFlightRoutes();
    
    public ArrayList<Airport> deleteFlightRoute(String originCode, String destCode);
    
    
}
