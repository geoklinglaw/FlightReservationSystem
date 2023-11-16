/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.AircraftConfiguration;
import entity.AircraftType;
import entity.Airport;
import entity.CabinClass;
import entity.Fare;
import entity.Flight;
import entity.FlightRoute;
import entity.FlightSchedule;
import entity.FlightSchedulePlan;
import entity.Seat;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.util.Pair;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import util.enumeration.CabinClassType;
import util.enumeration.FlightStatus;

/**
 *
 * @author apple
 */
@Stateless
public class FRSManagementSessionBean implements FRSManagementSessionBeanRemote, FRSManagementSessionBeanLocal {

    @EJB(name = "FareEntitySessionBeanLocal")
    private FareEntitySessionBeanLocal fareEntitySessionBeanLocal;

    @EJB(name = "FlightRouteSessionBeanLocal")
    private FlightRouteSessionBeanLocal flightRouteSessionBeanLocal;

    @EJB(name = "FlightSessionBeanLocal")
    private FlightSessionBeanLocal flightSessionBeanLocal;
    
    @EJB(name = "AirportEntitySessionBeanLocal")
    private AirportEntitySessionBeanLocal airportEntitySessionBeanLocal;

    @EJB(name = "SeatEntitySessionBeanLocal")
    private SeatEntitySessionBeanLocal seatEntitySessionBeanLocal;

    @EJB(name = "CabinClassSessionBeanLocal")
    private CabinClassSessionBeanLocal cabinClassSessionBeanLocal;

    @EJB(name = "AircraftTypeSessionBeanLocal")
    private AircraftTypeSessionBeanLocal aircraftTypeSessionBeanLocal;

    @EJB(name = "FlightSchedulePlanSessionBeanLocal")
    private FlightSchedulePlanSessionBeanLocal flightSchedulePlanSessionBeanLocal;


    @EJB
    private AircraftConfigurationSessionBeanLocal aircraftConfigurationSessionBean;
    
    
    public Pair<List<FlightRoute>, List<AircraftConfiguration>> enquireFlightRequirements() {
        List<AircraftConfiguration> aircraftList = viewAllAircraftConfiguration();
        List<FlightRoute> routeList = viewAllFlightRoutes();
        Pair<List<FlightRoute>, List<AircraftConfiguration>> pair = new Pair<>(routeList, aircraftList);
        return pair;   
    }
    
    @Override
    public void createAircraftConfiguration(int aircraftType, int maxSeats, List<CabinClass> ccList) {
        
        AircraftType acType = aircraftTypeSessionBeanLocal.retrieveAircraftTypeByValue(aircraftType);
        
        AircraftConfiguration aircraftConfig = new AircraftConfiguration(acType);
        aircraftConfig.setAircraftType(acType);
        aircraftConfig.setCabinClassList(ccList);
        aircraftConfig.setMaxSeatCapacity(new BigDecimal(maxSeats));
        
        for (CabinClass cc: ccList) {
            cc.setAircraftConfig(aircraftConfig);
            createSeatsPerCabinClass(cc);
            cabinClassSessionBeanLocal.createNewCabinClass(cc);
        }
            
        Long acConfig = aircraftConfigurationSessionBean.createNewAircraftConfiguration(aircraftConfig);   
    }
    
    public List<AircraftConfiguration> viewAllAircraftConfiguration() {
        List<AircraftConfiguration> aircraftConfigList = aircraftConfigurationSessionBean.viewAllAircraftConfiguration();
        
        return aircraftConfigList;
    }
    
    public List<Seat> createSeatsPerCabinClass(CabinClass cabinClass) {
        String[] charArr = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
        List<Seat> seatList = new ArrayList<>();
        int numRows = cabinClass.getNumRows().intValue();
        String seatConfig = cabinClass.getSeatConfig();

        // Split the seat configuration to identify aisles
        String[] seatConfigParts = seatConfig.split("-");
        int totalSeatsInRow = Arrays.stream(seatConfigParts).mapToInt(Integer::parseInt).sum();

        for (int row = 1; row <= numRows; row++) {
            int seatCounter = 0;
            for (int part = 0; part < seatConfigParts.length; part++) {
                int seatsInPart = Integer.parseInt(seatConfigParts[part]);
                for (int seat = 0; seat < seatsInPart; seat++) {
                    String seatName = row + charArr[seatCounter++];
                    Seat seatObj = new Seat(seatName, 0); // Assuming constructor Seat(String, int)
                    seatObj.setCabinClass(cabinClass);
                    seatEntitySessionBeanLocal.createNewSeat(seatObj);
                    seatList.add(seatObj);
                }
                if (part < seatConfigParts.length - 1) {
                    // Add an aisle after each part except the last one
                    seatCounter++; 
                }
            }
        }

        return seatList;
    }

    
    public List<Airport> viewAllAirports() {
        List<Airport> airportList = airportEntitySessionBeanLocal.retrieveAllAirports();
        return airportList;
    }
    
    public void createFlightRoute(Long originId, Long destId) {
        Airport origin = airportEntitySessionBeanLocal.retrieveAirport(originId);
        Airport destination = airportEntitySessionBeanLocal.retrieveAirport(destId);
       
        
        FlightRoute flightRoute = new FlightRoute(origin, destination, 1);
        flightRouteSessionBeanLocal.createNewFlightRoute(flightRoute);
        origin.getFlightRouteOrigin().add(flightRoute);
        destination.getFlightRouteDestination().add(flightRoute);
    }
    
    public List<FlightRoute> viewAllFlightRoutes() {
        List<FlightRoute> flightRoute = flightRouteSessionBeanLocal.viewAllFlightRoute();

        List<FlightRoute> sortedRoutes = new ArrayList<>();
        Set<Long> processedRoutes = new HashSet<>(); // Set to track processed routes

        // Process each route to find and pair with its return route
        
        for (FlightRoute directRoute : flightRoute) {
            if (processedRoutes.contains(directRoute.getId())) {
                continue; // Skip if this route is already processed
            }

            

            System.out.println(" directroute : " + directRoute.getId() + " : " + directRoute.getOrigin().getAirportCode() + " --> " + directRoute.getDestination().getAirportCode());

            
            Airport origin = directRoute.getOrigin();
            Airport destination = directRoute.getDestination();

            // Add the direct route
            sortedRoutes.add(directRoute);
            processedRoutes.add(directRoute.getId()); // Mark this route as processed

            // Find and add its return route, if exists
            for (FlightRoute potentialReturnRoute : flightRoute) {
                if (processedRoutes.contains(potentialReturnRoute.getId())) {
                    continue; // Skip if this route is already processed
                }

                Airport returnOrigin = potentialReturnRoute.getOrigin();
                Airport returnDestination = potentialReturnRoute.getDestination();
                System.out.println(" potentialReturnRoute : " + potentialReturnRoute.getId() + " : " + potentialReturnRoute.getOrigin().getAirportCode() + " --> " + potentialReturnRoute.getDestination().getAirportCode());

                // Check if it's a return route
                if (origin.equals(returnDestination) && destination.equals(returnOrigin)) {
                    sortedRoutes.add(potentialReturnRoute);
                    processedRoutes.add(potentialReturnRoute.getId()); // Mark return route as processed
                    break; // Break after adding the return route
                }
            }
        }
        System.out.println("in sortedRoutes ");
        for (FlightRoute fr: sortedRoutes) {
            System.out.println(fr.getId() + " : " + fr.getOrigin().getAirportCode() + " --> " + fr.getDestination().getAirportCode());
        }
        return sortedRoutes;
    }


    @Override
    public ArrayList<Airport> deleteFlightRoute(String originCode, String destCode) {
        Airport originAirport = airportEntitySessionBeanLocal.retrieveAirportByCode(originCode);
        Airport destinationAirport = airportEntitySessionBeanLocal.retrieveAirportByCode(destCode);

        FlightRoute intendedRoute = null;
        for (FlightRoute originRoute: originAirport.getFlightRouteOrigin()) {
            for (FlightRoute destRoute: destinationAirport.getFlightRouteDestination()) {
                if (originRoute.getId().equals(destRoute.getId())) {
                    intendedRoute = originRoute;
                    break;
                } 
            }
            if (intendedRoute != null) {
                break; // Stop searching if we found the route
            }
        }

        intendedRoute.setOrigin(null);
        originAirport.getFlightRouteOrigin().remove(intendedRoute);
        destinationAirport.getFlightRouteDestination().remove(intendedRoute);
        flightRouteSessionBeanLocal.deleteFlightRoute(intendedRoute);

        return new ArrayList<Airport>(Arrays.asList(originAirport, destinationAirport));

    }
    
    
    public void createFlight(String flightNum, Long routeId, Long configId) {
        AircraftConfiguration config = aircraftConfigurationSessionBean.retrieveAircraftConfigurationById(configId);
        FlightRoute route = flightRouteSessionBeanLocal.retrieveFlightRouteById(routeId);
        Flight flight = new Flight(flightNum, 1);
        flight.setFlightRoute(route);
        flight.setAircraftConfig(config);
        
        flightSessionBeanLocal.createNewFlight(flight);
        

    }

    public List<Flight> viewAllFlight() {
        List<Flight> flights= flightSessionBeanLocal.viewAllFlight();
        return flights;
    }
    
    public List<FlightSchedulePlan> viewAllFlightSchedulePlan() {
        List<FlightSchedulePlan> fspList = flightSchedulePlanSessionBeanLocal.viewAllFlightSchedulePlan();
        return fspList;
    }
    
    public Flight updateFlight(String flightNum, int routeId, int configId) {
        
        Flight managedFlight = flightSessionBeanLocal.retrieveFlightByNumber(flightNum);
        
        if (routeId != 0) {
            Long routeID = new Long(routeId);
            FlightRoute route = flightRouteSessionBeanLocal.retrieveFlightRouteById(routeID);
            managedFlight.setFlightRoute(route);
        }
        
        if (configId != 0) {
            Long configID = new Long(configId);
            AircraftConfiguration config = aircraftConfigurationSessionBean.retrieveAircraftConfigurationById(configID);
            managedFlight.setAircraftConfig(config);
        }
        
        return managedFlight;
    }
    
    public void deleteFlight(String flightNum) {
        Flight managedFlight = flightSessionBeanLocal.retrieveFlightByNumber(flightNum);
        
        if (managedFlight.getFlightSchedulePlan() == null) {
            flightSessionBeanLocal.deleteFlight(managedFlight);
            
        } else {
            managedFlight.setStatus(FlightStatus.DISABLED);
        }
    }
    
    public Flight viewSpecificFlight(String flightNum) {
        Flight flight = flightSessionBeanLocal.retrieveFlightByNumber(flightNum);
        int size = flight.getAircraftConfig().getCabinClassList().size();
        return flight;
    }
    
    public void createFareforEachCabinClass(CabinClass cc, String fareBasisCode) {
        Fare managedFare = fareEntitySessionBeanLocal.retrieveFareByFBC(fareBasisCode);
        CabinClass cabinClass = cabinClassSessionBeanLocal.retrieveCabinClassById(cc.getId());
        cabinClass.setFlightSchedule(cc.getFlightSchedule());
        cabinClass.setFare(managedFare);
        managedFare.setCabinClass(cabinClass);
    }
    

    
    public void createFlightScheduleAndPlan(List<FlightSchedule> fsList, FlightSchedulePlan fsp, Flight flight, List<Fare> fareList, List<CabinClass> ccList) {
        
        for (FlightSchedule fs: fsList) {
            flightSchedulePlanSessionBeanLocal.createNewFlightSchedule(fs);
            
        }
        
        flightSchedulePlanSessionBeanLocal.createNewFlightSchedulePlan(fsp);
        
        List<Fare> managedFareList = new ArrayList<>();
        for (Fare f: fareList) {
            Long id = fareEntitySessionBeanLocal.createNewFare(f);
            Fare tempFare = fareEntitySessionBeanLocal.retrieveFareById(id);
            
            managedFareList.add(tempFare);
        }
        
        
        Flight managedFlight = flightSessionBeanLocal.retrieveFlightById(flight.getId());
        managedFlight.setFlightSchedulePlan(fsp);
        
        for (int i = 0; i < ccList.size(); i++) {
            CabinClass tempCC = ccList.get(i);
            Fare tempFare = managedFareList.get(i);
            CabinClass cabinClass = cabinClassSessionBeanLocal.retrieveCabinClassById(tempCC.getId());
            cabinClass.setFlightSchedule(tempCC.getFlightSchedule());
            cabinClass.setFare(tempFare);
            tempFare.setCabinClass(cabinClass);
            
        }
        
        
    }
    
    public List<CabinClass> viewSeatsInventory(String flightNum) {
       Flight flight = flightSessionBeanLocal.retrieveFlightByNumber(flightNum, true);
       int size1 = flight.getFlightSchedulePlan().getFlightSchedule().get(0).getCabinClass().size();
       List<CabinClass> ccList = flight.getFlightSchedulePlan().getFlightSchedule().get(0).getCabinClass();
       
        for (CabinClass cc: ccList) {
            CabinClass managedCC = cabinClassSessionBeanLocal.retrieveCabinClassById(cc.getId());
            int size2 = managedCC.getSeatList().size();

        }
        
        return ccList;
    }
   
    
}
