/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import javax.ejb.Local;
import javax.persistence.NoResultException;
import util.exception.AirportNotAvailableException;
import util.exception.ExceedSeatCapacityException;
import util.exception.FlightExistsException;
import util.exception.FlightExistsForFlightRouteException;
import util.exception.FlightRouteExistsException;
import util.exception.FlightRouteNotFoundException;
import util.exception.FlightScheduleBookedException;
import util.exception.OverlappedSchedules;

/**
 *
 * @author apple
 */
@Local
public interface FRSManagementSessionBeanLocal {
    
    public void createAircraftConfiguration(String style, int aircraftType, int maxSeats, List<CabinClass> ccList);
    
    public List<AircraftConfiguration> viewAllAircraftConfiguration();
    
    public FlightCabinClass createSeatsPerCabinClass(FlightCabinClass fcc) ;
    
    public List<Airport> viewAllAirports();
    

    
    public List<FlightRoute> viewAllFlightRoutes();
    
    
    public Pair<List<FlightRoute>, List<AircraftConfiguration>> enquireFlightRequirements();
    
    public Flight createFlight(String flightNum, String origin, String destination, Long configId) throws FlightRouteNotFoundException;


    public List<Flight> viewAllFlight();
    
    
    public void deleteFlight(String flightNum);
    
    public Flight viewSpecificFlight(String flightNum);
    
    public void createFlightCabinClassSeats(List<FlightCabinClass> fccList);

    public Long createFlightScheduleAndPlan(List<FlightSchedule> fsList, FlightSchedulePlan fsp, Flight flight, List<Fare> fareList, List<List<FlightCabinClass>> fccList);
    
    public List<FlightSchedulePlan> viewAllFlightSchedulePlan();
    
    public List<FlightCabinClass> viewSeatsInventory(Long fsId);
    
    public Flight retrieveFlightByNumber(String flightNum);
    
    public FlightCabinClass viewCabinClass(Long fsId, Long ccId);
    
    public FlightRoute viewFlightRoute(Airport origin, Airport destination) throws FlightRouteNotFoundException;
    
    public FlightRoute retrieveFlightRouteById(Long id);
    
    public List<Flight> checkComplementaryFlightExistence(Airport origin, Airport destination, Long configId) throws FlightRouteNotFoundException;

    public Boolean checkFlightNumber(String flightNum);

    
    public void createComplementaryFSP(Long fspID, Long flightID, int layover);
    
    
    public FlightSchedulePlan updateFaresFSP(FlightSchedulePlan fsp, List<Fare> newFarelist);
    
    public ArrayList<Airport> deleteFlightRoute(String originCode, String destCode) throws AirportNotAvailableException, FlightRouteNotFoundException, FlightExistsForFlightRouteException;

    public Boolean checkForOverlappingFlightSchedule(List<FlightSchedule> fsList) throws OverlappedSchedules;


            
    public FlightSchedulePlan retrieveFlightSchedulePlan(Long id);
    
    public FlightSchedule MakeNewFS(FlightSchedulePlan fsp, FlightSchedule fs, List<FlightCabinClass> fccList, List<Fare> fareList);
    
    public FlightSchedule retrieveFlightScheduleById(Long id);
    
    public Flight changeFlightNumber(Flight chosenFlight, String oldFNum);
    
    public Flight updateFlight(Flight chosenFlight, String oldFNum, int routeId);
    
    public Flight updateFlight(Flight chosenFlight, String oldFNum, int routeId, int configId);
    
    public FlightSchedule updateFlightSchedule(FlightSchedule oldFs);
    
    public void deleteFS(Long id);
    
    public FlightRoute createFlightRoute(Long originId, Long destId) throws FlightRouteExistsException, AirportNotAvailableException ;

    public FlightRoute checkForFlightRoutes(String origin, String destination) throws FlightRouteNotFoundException;
    
    public Boolean checkForACConfigurationIssues(int type, BigDecimal pax) throws ExceedSeatCapacityException;
    
    public void deleteFlightSchedulePlan(String flightNum) throws NoResultException, FlightScheduleBookedException;

    
}
