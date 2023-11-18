/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.AircraftConfiguration;
import entity.Airport;
import entity.CabinClass;
import entity.Fare;
import entity.Flight;
import entity.FlightCabinClass;
import entity.FlightRoute;
import entity.FlightSchedule;
import entity.FlightSchedulePlan;
import entity.Seat;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import javax.ejb.Remote;

/**
 *
 * @author apple
 */
@Remote
public interface FRSManagementSessionBeanRemote {
    
    public void createAircraftConfiguration(String style, int aircraftType, int maxSeats, List<CabinClass> ccList);
    
    public List<AircraftConfiguration> viewAllAircraftConfiguration();
    
    public FlightCabinClass createSeatsPerCabinClass(FlightCabinClass fcc) ;
    
    public List<Airport> viewAllAirports();
    
    public FlightRoute createFlightRoute(Long originId, Long destId);
    
    public List<FlightRoute> viewAllFlightRoutes();
    
    public ArrayList<Airport> deleteFlightRoute(String originCode, String destCode);
    
    public Pair<List<FlightRoute>, List<AircraftConfiguration>> enquireFlightRequirements();
    
    public Flight createFlight(String flightNum, Long routeId, Long configId);

    public List<Flight> viewAllFlight();
    
    public Flight updateFlight(String flightNum, int routeId, int configId);
    
    public void deleteFlight(String flightNum);
    
    public Flight viewSpecificFlight(String flightNum);
    
    public void createFlightCabinClassSeats(List<FlightCabinClass> fccList);

    public Long createFlightScheduleAndPlan(List<FlightSchedule> fsList, FlightSchedulePlan fsp, Flight flight, List<Fare> fareList, List<List<FlightCabinClass>> fccList);
    
    public List<FlightSchedulePlan> viewAllFlightSchedulePlan();
    
    public List<FlightCabinClass> viewSeatsInventory(Long fsId);
    
    public Flight retrieveFlightByNumber(String flightNum);
    
    public FlightCabinClass viewCabinClass(Long fsId, Long ccId);
    
    public FlightRoute viewFlightRoute(Airport origin, Airport destination);
    
    public FlightRoute retrieveFlightRouteById(Long id);
    
    public void createComplementaryFSP(Long fspID, Long flightID, int layover);
    
    public List<Flight> checkComplementaryFlightExistence(Airport origin, Airport destination, Long configId);
    
    




}
