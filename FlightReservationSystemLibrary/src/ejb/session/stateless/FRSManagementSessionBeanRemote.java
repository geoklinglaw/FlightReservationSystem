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
    
    public void createAircraftConfiguration(int aircraftType, List<Integer> ccList);
    
    public List<AircraftConfiguration> viewAllAircraftConfiguration();
    
    public List<Seat> createSeatsPerCabinClass(int numSeatAbreast, int numRows, CabinClass cabinClass);
    
    public List<Airport> viewAllAirports();
    
    public void createFlightRoute(Long originId, Long destId);
    
    public List<FlightRoute> viewAllFlightRoutes();
    
    public ArrayList<Airport> deleteFlightRoute(String originCode, String destCode);

    public Pair<List<FlightRoute>, List<AircraftConfiguration>> enquireFlightRequirements();
    
    public void createFlight(String flightNum, Long routeId, Long configId);

    public List<Flight> viewAllFlight();
    
    public Flight updateFlight(String flightNum, int routeId, int configId);
    
    public void deleteFlight(String flightNum);
    
    public Flight viewSpecificFlight(String flightNum);

    public void createFareforEachCabinClass(Long ccId, Fare fare);

    public void createFlightScheduleAndPlan(List<FlightSchedule> fsList, FlightSchedulePlan fsp, Flight flight);

    public List<FlightSchedulePlan> viewAllFlightSchedulePlan();



}
