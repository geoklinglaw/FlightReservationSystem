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
import entity.FlightCabinClass;
import entity.FlightRoute;
import entity.FlightSchedule;
import entity.FlightSchedulePlan;
import entity.MultiplePlan;
import entity.RecurrentPlan;
import entity.RecurrentWeeklyPlan;
import entity.Seat;
import entity.SinglePlan;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import util.enumeration.AircraftName;
import util.enumeration.CabinClassType;
import util.enumeration.FlightScheduleStatus;
import util.enumeration.FlightStatus;
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
    
    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;


    @EJB
    private AircraftConfigurationSessionBeanLocal aircraftConfigurationSessionBean;
    
    
    public Pair<List<FlightRoute>, List<AircraftConfiguration>> enquireFlightRequirements() {
        List<AircraftConfiguration> aircraftList = viewAllAircraftConfiguration();
        List<FlightRoute> routeList = viewAllFlightRoutes();
        Pair<List<FlightRoute>, List<AircraftConfiguration>> pair = new Pair<>(routeList, aircraftList);
        return pair;   
    }
    
    @Override
    public void createAircraftConfiguration(String style, int aircraftType, int maxSeats, List<CabinClass> ccList) {
        
        AircraftType acType = aircraftTypeSessionBeanLocal.retrieveAircraftTypeByValue(aircraftType);
        
        AircraftConfiguration aircraftConfig = new AircraftConfiguration(acType, style);
        aircraftConfig.setAircraftType(acType);
        aircraftConfig.setCabinClassList(ccList);
        
        for (CabinClass cc: ccList) {
            cc.setAircraftConfig(aircraftConfig);
            cabinClassSessionBeanLocal.createNewCabinClass(cc);
        }
            
        Long acConfig = aircraftConfigurationSessionBean.createNewAircraftConfiguration(aircraftConfig);   
    }
    
    public List<AircraftConfiguration> viewAllAircraftConfiguration() {
        List<AircraftConfiguration> aircraftConfigList = aircraftConfigurationSessionBean.viewAllAircraftConfiguration();
        
        return aircraftConfigList;
    }
   
    
    @Override
    public FlightCabinClass createSeatsPerCabinClass(FlightCabinClass fcc) {
        String[] charArr = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
        List<Seat> seatList = new ArrayList<>();
        int numRows = fcc.getCabinClass().getNumRows().intValue();
        String seatConfig = fcc.getCabinClass().getSeatConfig();

        // Split the seat configuration to identify aisles
        String[] seatConfigParts = seatConfig.split("-");
        Long fccID = cabinClassSessionBeanLocal.createNewFlightCabinClass(fcc);
        FlightCabinClass newFCC = null;
        if (fccID == null) {
            System.out.println("fccID is null");
        } else {
            newFCC = cabinClassSessionBeanLocal.retrieveFlightCabinClassById(fccID);
        }
        
        for (int row = 1; row <= numRows; row++) {
            int seatCounter = 0; // Reset seat counter for each row
            for (String seatsInPartStr : seatConfigParts) {
                int seatsInPart = Integer.parseInt(seatsInPartStr);
                for (int seat = 0; seat < seatsInPart; seat++) {
                    String seatName = row + charArr[seatCounter++];
                    Seat seatObj = new Seat(seatName, 0);
                    seatObj.setFlightCabinClass(newFCC);
                    seatEntitySessionBeanLocal.createNewSeat(seatObj);
                    seatList.add(seatObj);
                }
                // Increase the seatCounter to skip the aisle
                seatCounter = Math.min(seatCounter, charArr.length - 1); // Prevent going out of bounds
            }
        }

        newFCC.setSeatList(seatList);
        return newFCC;
    }


    
    @Override
    public List<Airport> viewAllAirports() {

        List<Airport> airportList = airportEntitySessionBeanLocal.retrieveAllAirports();
        return airportList;
        
    }
    
    public FlightRoute createFlightRoute(Long originId, Long destId) throws FlightRouteExistsException, AirportNotAvailableException {
        Airport origin = airportEntitySessionBeanLocal.retrieveAirport(originId);
        if (origin == null) {
            throw new AirportNotAvailableException("Origin airport with ID " + originId + " not found.");
        }

        Airport destination = airportEntitySessionBeanLocal.retrieveAirport(destId);
        if (destination == null) {
            throw new AirportNotAvailableException("Destination airport with ID " + destId + " not found.");
        }

        FlightRoute flightRoute = new FlightRoute(origin, destination, 1);
        try {
            flightRouteSessionBeanLocal.createNewFlightRoute(flightRoute);
        } catch (FlightRouteExistsException ex) {
            throw new FlightRouteExistsException("Flight route with the same origin and destination already exists: " + ex.getMessage());
        }

        origin.getFlightRouteOrigin().add(flightRoute);
        destination.getFlightRouteDestination().add(flightRoute);
        return flightRoute;
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

                // Check if it's a return route
                if (origin.equals(returnDestination) && destination.equals(returnOrigin)) {
                    sortedRoutes.add(potentialReturnRoute);
                    processedRoutes.add(potentialReturnRoute.getId()); // Mark return route as processed
                    break; // Break after adding the return route
                }
            }
        }

        return sortedRoutes;
    }


  @Override
    public ArrayList<Airport> deleteFlightRoute(String originCode, String destCode) throws AirportNotAvailableException, FlightExistsForFlightRouteException, FlightRouteNotFoundException {

        Airport originAirport;
        Airport destinationAirport;

        try {
            originAirport = airportEntitySessionBeanLocal.retrieveAirportByCode(originCode);
            destinationAirport = airportEntitySessionBeanLocal.retrieveAirportByCode(destCode); 
            FlightRoute intendedRoute = flightRouteSessionBeanLocal.findSpecificFlightRouteWithCode(originCode, destCode);
            
            flightRouteSessionBeanLocal.deleteFlightRoute(intendedRoute);
//            intendedRoute.setOrigin(null);
//            intendedRoute.setDestination(null);
            originAirport.getFlightRouteOrigin().remove(intendedRoute);
            destinationAirport.getFlightRouteDestination().remove(intendedRoute);
            
        } catch (AirportNotAvailableException ex) {
            throw new AirportNotAvailableException("Airport not available: " + ex.getMessage());
        } catch (FlightExistsForFlightRouteException ex) {
            throw new FlightExistsForFlightRouteException(ex.getMessage());
        } catch (FlightRouteNotFoundException ex) {
            throw new FlightRouteNotFoundException(ex.getMessage());
        }

        return new ArrayList<Airport>(Arrays.asList(originAirport, destinationAirport));
    }

    
    @Override
    public Flight createFlight(String flightNum, String origin, String destination, Long configId) throws FlightRouteNotFoundException {
        try {
            AircraftConfiguration config = aircraftConfigurationSessionBean.retrieveAircraftConfigurationById(configId);
            FlightRoute route = flightRouteSessionBeanLocal.findSpecificFlightRouteWithCode(origin, destination);
            Flight flight = new Flight(flightNum, 1);
            flight.setFlightRoute(route);
            flight.setAircraftConfig(config);

            Long flightID = flightSessionBeanLocal.createNewFlight(flight);
            Flight managedFlight = flightSessionBeanLocal.retrieveFlightById(flightID);
            route.getFlightList().add(managedFlight);
            return flight;
            
        } catch (FlightRouteNotFoundException ex) {
            throw new FlightRouteNotFoundException();
        }

    }

    public List<Flight> viewAllFlight() {
        List<Flight> flights= flightSessionBeanLocal.viewAllFlight();
        return flights;
    }
    
    public List<FlightSchedulePlan> viewAllFlightSchedulePlan() {
        List<FlightSchedulePlan> fspList = flightSchedulePlanSessionBeanLocal.viewAllFlightSchedulePlan();
        return fspList;
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
    
    public void createFlightCabinClassSeats(List<FlightCabinClass> fccList) {
        for (FlightCabinClass fcc: fccList) {
            FlightCabinClass managedFCC = createSeatsPerCabinClass(fcc);
        }
    }
    
    public Long createFlightScheduleAndPlan(List<FlightSchedule> fsList, FlightSchedulePlan fsp, Flight flight, List<Fare> fareList, List<List<FlightCabinClass>> fccList) {
        
//        if (fsp instanceof RecurrentWeeklyPlan || fsp instanceof RecurrentPlan) {
            for (List<FlightCabinClass> fccs: fccList) {
                createFlightCabinClassSeats(fccs);
            }

            for (int i = 0; i < fsList.size(); i++) {
                FlightSchedule fs = fsList.get(i);
                flightSchedulePlanSessionBeanLocal.createNewFlightSchedule(fs);

                for (int j = 0; j < fccList.size(); j++) {
                    fs.setFlightCabinClass(fccList.get(i));

                    for (int k = 0; k < fccList.get(i).size(); k++) { // individual flightcabinclass
                        Long id = fareEntitySessionBeanLocal.createNewFare(fareList.get(k));
                        fareList.get(k).setFlightCabinClass(fccList.get(j).get(k));
                        fccList.get(j).get(k).setFlightSchedule(fs);
                        fccList.get(j).get(k).setFare(fareList.get(k));
                    }
                }
            }
//        } 
        
        Long fspID = flightSchedulePlanSessionBeanLocal.createNewFlightSchedulePlan(fsp);
        Flight managedFlight = flightSessionBeanLocal.retrieveFlightById(flight.getId());
        managedFlight.setFlightSchedulePlan(fsp);
        
        for (FlightSchedule fs: fsList) {
            fs.setFlightSchedulePlan(fsp);
        }
        fsp.setFlightSchedule(fsList);
        
        for (Fare f: fareList) {
            f.setFlightSchedulePlan(fsp);
        }
        
        return fspID;
    }
    
    @Override
    public void createComplementaryFSP(Long fspID, Long flightID, int layover) {
        
        FlightSchedulePlan fsp = flightSchedulePlanSessionBeanLocal.retrieveFlightSchedulePlanById(fspID);
      
        
        List<Fare> oldFareList = fsp.getFare();
        List<Fare> newFareList = new ArrayList<Fare>();
        Flight originalFlight = fsp.getFlight();
        Flight compFlight = flightSessionBeanLocal.retrieveFlightById(flightID);
        
        List<FlightSchedule> flightschList = fsp.getFlightSchedule();        
        List<FlightSchedule> newFSList = new ArrayList<FlightSchedule>();
        List<List<FlightCabinClass>> combinedFCCList = new ArrayList<List<FlightCabinClass>>();

        System.out.println("checking for fsp complementary " + fsp.getFlight().getFlightNumber() + fsp.getId());

        
        for (FlightSchedule fs: flightschList) {
            
            List<FlightCabinClass> newFCCList = new ArrayList<FlightCabinClass>();
            List<FlightCabinClass> fccList = fs.getFlightCabinClass();
            for (int i = 0; i < fccList.size(); i++) {
                CabinClass cabin = fccList.get(i).getCabinClass();
                BigDecimal maxSeats = fccList.get(i).getNumAvailableSeats();
                FlightCabinClass newFCC = new FlightCabinClass(maxSeats, maxSeats, maxSeats);
                
                newFCC.setCabinClass(cabin);
                newFCCList.add(newFCC);
                
                Fare fare = new Fare();
                fare.setFareAmount(oldFareList.get(i).getFareAmount());
                fare.setFareBasisCode(compFlight.getFlightNumber() + cabin.getType());
//                fare.setFlightCabinClass(newFCC);
                newFareList.add(fare);
                
            }
            
            FlightSchedule flightSch = new FlightSchedule();
            FlightRoute flightroute = fs.getFlightSchedulePlan().getFlight().getFlightRoute();
            
            flightSch.setDepartureTime(addLayoverDuration(fs.getArrivalTime(), layover));
            System.out.println("duration " + flightSch.getId() + " " + fs.getFlightDuration() + "  " + Duration.ofSeconds((long) fs.getFlightDuration()));
            flightSch.setFlightDuration(Duration.ofSeconds((long) fs.getFlightDuration()));
            Date initialArrivalTime = computeArrivalTime(addLayoverDuration(fs.getArrivalTime(), layover), Duration.ofSeconds((long) fs.getFlightDuration()));
            flightSch.setArrivalTime(initialArrivalTime);
            newFSList.add(flightSch);
            
            
            combinedFCCList.add(newFCCList);
        }
        
        FlightSchedulePlan complementaryFSP;
        
        if (fsp instanceof SinglePlan) {
            SinglePlan compSinglePlan = new SinglePlan();
            complementaryFSP = compSinglePlan; 
        } else if (fsp instanceof MultiplePlan) {
            MultiplePlan compMultiplePlan = new MultiplePlan();
            complementaryFSP = compMultiplePlan; 
        } else if (fsp instanceof RecurrentPlan) {
            RecurrentPlan compRecurrentPlan = new RecurrentPlan();
            compRecurrentPlan.setEndDate(((RecurrentPlan) fsp).getEndDate());
            compRecurrentPlan.setFrequency(((RecurrentPlan) fsp).getFrequency());
            complementaryFSP = compRecurrentPlan;
        } else {
            RecurrentWeeklyPlan compRecurrentWeeklyPlan = new RecurrentWeeklyPlan();
            compRecurrentWeeklyPlan.setEndDate(((RecurrentWeeklyPlan) fsp).getEndDate());
            compRecurrentWeeklyPlan.setDayOfWeek(((RecurrentWeeklyPlan) fsp).getDayOfWeek());
            complementaryFSP = compRecurrentWeeklyPlan; 
        }
        
        complementaryFSP.setType(fsp.getType());
        complementaryFSP.setFlight(compFlight);

        createFlightScheduleAndPlan(newFSList, complementaryFSP, compFlight, newFareList, combinedFCCList);
    }
    
    private Date addLayoverDuration(Date date, int layover) {
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.HOUR_OF_DAY, layover);

        Date tenDaysLater = calendar.getTime();
        return tenDaysLater;
    }

    private Date computeArrivalTime(Date departureTime, Duration flightDuration) {
        Instant departureInstant = departureTime.toInstant();
        LocalDateTime departureDateTime = LocalDateTime.ofInstant(departureInstant, ZoneId.systemDefault());
        LocalDateTime arrivalDateTime = departureDateTime.plus(flightDuration);
        Instant arrivalInstant = arrivalDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date arrivalTime = Date.from(arrivalInstant);
    
        return arrivalTime;
    }
    
    public FlightRoute viewFlightRoute(Airport origin, Airport destination) throws FlightRouteNotFoundException {
        FlightRoute route = flightRouteSessionBeanLocal.findSpecificFlightRouteWithCode(origin.getAirportCode(), destination.getAirportCode());
        if (route == null || route.getFlightList().isEmpty()) {
            throw new FlightRouteNotFoundException("No flight route or flights found for the specified airports: Origin - " 
                                                  + origin.getAirportCode() + ", Destination - " + destination.getAirportCode());
        }
        return route;
    }

    
    public List<FlightCabinClass> viewSeatsInventory(Long fsId) {
       FlightSchedule fs = flightSchedulePlanSessionBeanLocal.retrieveFlightScheduleById(fsId);
       List<FlightCabinClass> fccList = fs.getFlightCabinClass();
       
        for (FlightCabinClass fcc: fccList) {
           FlightCabinClass managedFCC = cabinClassSessionBeanLocal.retrieveFlightCabinClassById(fcc.getId(), true);
           int size1 = managedFCC.getSeatList().size();
        }
       
       return fccList;
    }

    public FlightCabinClass viewCabinClass(Long fsId, Long fccId) {
//       FlightSchedule fs = flightSchedulePlanSessionBeanLocal.retrieveFlightScheduleById(fsId);
       FlightCabinClass managedFCC = cabinClassSessionBeanLocal.retrieveFlightCabinClassById(fccId, true);
       int size = managedFCC.getSeatList().size();
       
       return managedFCC;
    }
   
    public Flight retrieveFlightByNumber(String flightNum) {
        Flight flight = flightSessionBeanLocal.retrieveFlightByNumber(flightNum);
        int size = flight.getFlightSchedulePlan().getFlightSchedule().size();
        return flight;

    }
    
    public FlightRoute retrieveFlightRouteById(Long id) {
        return flightRouteSessionBeanLocal.retrieveFlightRouteById(id);
    }
    
    @Override
    public List<Flight> checkComplementaryFlightExistence(Airport origin, Airport destination, Long configId) throws FlightRouteNotFoundException {
        FlightRoute route = flightRouteSessionBeanLocal.findSpecificFlightRoute(origin, destination);
        int size = route.getFlightList().size();
        
        List<Flight> selectedFlights = new ArrayList<Flight>();
        AircraftConfiguration acConfig = aircraftConfigurationSessionBean.retrieveAircraftConfigurationById(configId);
        
        for (Flight f: route.getFlightList()) {
            if (f.getAircraftConfig().getAircraftStyle().equals(acConfig.getAircraftStyle()) && f.getAircraftConfig().getName().equals(acConfig.getName())) {
                selectedFlights.add(f);
            }
        }
        
        return selectedFlights;
    }
    
    public Flight changeFlightNumber(Flight chosenFlight, String oldFNum) {
        Flight flight = flightSessionBeanLocal.retrieveFlightByNumber(oldFNum);
        flight.setFlightNumber(chosenFlight.getFlightNumber());
        
        return flight;
    }
    
    public Flight updateFlight(Flight chosenFlight, String oldFNum, int routeId) {
        Flight managedFlight = flightSessionBeanLocal.retrieveFlightByNumber(oldFNum);
        
        Long routeID = new Long(routeId);
        FlightRoute route = flightRouteSessionBeanLocal.retrieveFlightRouteById(routeID);
        managedFlight.setFlightRoute(route);
        managedFlight.setFlightNumber(chosenFlight.getFlightNumber());
        
        return managedFlight;
    }
       
    public Flight updateFlight(Flight chosenFlight, String oldFNum, int routeId, int configId) {
        Flight managedFlight = flightSessionBeanLocal.retrieveFlightByNumber(oldFNum);
        Long routeID = new Long(routeId);
        FlightRoute route = flightRouteSessionBeanLocal.retrieveFlightRouteById(routeID);
        managedFlight.setFlightRoute(route);
        managedFlight.setFlightNumber(chosenFlight.getFlightNumber());
        
        return managedFlight;
        
    }
    
    public FlightSchedulePlan retrieveFaresFSP(FlightSchedulePlan fsp) {
        FlightSchedulePlan newFSP = flightSchedulePlanSessionBeanLocal.retrieveFlightSchedulePlanById(fsp.getId());
        int size = newFSP.getFare().size();
        int size1 = newFSP.getFlightSchedule().size();
        int size2 = newFSP.getFlightSchedule().get(0).getFlightCabinClass().size();

        return newFSP; 
    }


    public FlightSchedulePlan updateFaresFSP(FlightSchedulePlan fsp, List<Fare> newFarelist) {
        FlightSchedulePlan newFSP = flightSchedulePlanSessionBeanLocal.retrieveFlightSchedulePlanById(fsp.getId());
        int size = newFSP.getFare().size();
        int size1 = newFSP.getFlightSchedule().size();
        
        List<FlightSchedule> fsList = newFSP.getFlightSchedule();
        List<FlightSchedule> newFSList = new ArrayList<FlightSchedule>();
        
        for (int i = 0; i < fsList.size(); i++) {
            flightSchedulePlanSessionBeanLocal.retrieveFlightScheduleById(fsList.get(i).getId());
            List<FlightCabinClass> fccList = fsList.get(i).getFlightCabinClass();
            for (int j = 0; j < fccList.size(); j++) { 
                System.out.println(newFarelist.get(j));
                cabinClassSessionBeanLocal.retrieveFlightCabinClassById(fccList.get(j).getId());
                fareEntitySessionBeanLocal.retrieveFareById(newFarelist.get(j).getId());
                fccList.get(j).setFare(newFarelist.get(j));
                newFarelist.get(j).setFlightCabinClass(fccList.get(j));
            }  
        }
        
        newFSP.setFare(newFarelist);
        return newFSP; 
    }
    
    
    public FlightSchedulePlan retrieveFlightSchedulePlan(Long id) {
        FlightSchedulePlan fsp = flightSchedulePlanSessionBeanLocal.retrieveFlightSchedulePlanById(id);
        int size = fsp.getFlightSchedule().size();
        int size1 = fsp.getFare().size();
        return fsp;
    }
    
    public FlightSchedule updateFlightSchedule(FlightSchedule oldFs) {
        FlightSchedule managedFS = flightSchedulePlanSessionBeanLocal.retrieveFlightScheduleById(oldFs.getId());
        
        managedFS.setArrivalTime(oldFs.getArrivalTime());
        managedFS.setDepartureTime(oldFs.getDepartureTime());
        managedFS.setFlightDuration(Duration.ofSeconds((long)oldFs.getFlightDuration()));
        
        return managedFS;
    }
    
    public FlightSchedule MakeNewFS(FlightSchedulePlan fsp, FlightSchedule fs, List<FlightCabinClass> fccList, List<Fare> fareList) {

        createFlightCabinClassSeats(fccList);
        
        flightSchedulePlanSessionBeanLocal.createNewFlightSchedule(fs);

        fs.setFlightCabinClass(fccList);

        for (int k = 0; k < fccList.size(); k++) { // individual flightcabinclass
            Long id = fareEntitySessionBeanLocal.createNewFare(fareList.get(k));
            fareList.get(k).setFlightCabinClass(fccList.get(k));
            fccList.get(k).setFlightSchedule(fs);
            fccList.get(k).setFare(fareList.get(k));
        }

        Long fspID = flightSchedulePlanSessionBeanLocal.createNewFlightSchedulePlan(fsp);
        fs.setFlightSchedulePlan(fsp);
        fsp.getFlightSchedule().add(fs);
        
        for (Fare f: fareList) {
            f.setFlightSchedulePlan(fsp);
        }
        
        return fs;
    }
    
    public FlightSchedule retrieveFlightScheduleById(Long id) {
        return flightSchedulePlanSessionBeanLocal.retrieveFlightScheduleById(id);
        
    }
    
    public void deleteFS(Long id) {
        FlightSchedule fs = flightSchedulePlanSessionBeanLocal.retrieveFlightScheduleById(id);
        if (fs.getFlightBookings().size() != 0) {
            fs.setFlightBookings(new ArrayList<>());
            List<FlightCabinClass> fcclist = fs.getFlightCabinClass();
            for (FlightCabinClass fcc: fcclist) {
                fcc.setFlightSchedule(null);
            }
            
            fs.setFlightCabinClass(new ArrayList<>());
            FlightSchedulePlan fsp = fs.getFlightSchedulePlan();
            
            flightSchedulePlanSessionBeanLocal.deleteFlightSchedule(fs);
            
        }
    }
    
    public FlightRoute checkForFlightRoutes(String origin, String destination) throws FlightRouteNotFoundException {
        FlightRoute route = flightRouteSessionBeanLocal.findSpecificFlightRouteWithCode(origin, destination);
        if (route != null) {
            return route;
        } else {
            throw new FlightRouteNotFoundException("No flight route for " + origin + " and " + destination);
        }
        
    }
    
    
    public Boolean checkForACConfigurationIssues(int type, BigDecimal pax) throws ExceedSeatCapacityException {
        return aircraftTypeSessionBeanLocal.checkForCapacity(type, pax);
    }
    
    public Boolean checkFlightNumber(String flightNum) {
        
        return flightSessionBeanLocal.checkFlightByNumber(flightNum);
        
    }
    
    
    public Boolean checkForOverlappingFlightSchedule(List<FlightSchedule> fsList) throws OverlappedSchedules {
    Boolean overlapped = false;
    
    for (int i = 0; i < fsList.size(); i++) {
        for (int j = 0; j < fsList.size(); j++) {
            if (i != j) {
                if (!fsList.get(i).getDepartureTime().before(fsList.get(j).getDepartureTime()) &&
                    !fsList.get(i).getDepartureTime().after(fsList.get(j).getArrivalTime())) {
                    overlapped = true;
                }
            }
        }
    }
    
    if (overlapped) {
        throw new OverlappedSchedules("There are overlapping flight schedules!");
    }
    
    return overlapped;
}

    
    
    public void deleteFlightSchedulePlan(String flightNum) throws NoResultException, FlightScheduleBookedException {
        
        Flight flight = flightSessionBeanLocal.retrieveFlightByNumber(flightNum);
        FlightSchedulePlan fsp = flightSchedulePlanSessionBeanLocal.retrieveFlightSchedulePlanById(flight.getFlightSchedulePlan().getId());

        List<FlightSchedule> fsList = fsp.getFlightSchedule();
        
        for (FlightSchedule flightSch: fsList) {
            if (flightSch.getFlightBookings().size() > 0) {
                fsp.setType(FlightScheduleStatus.DISABLED);
                throw new FlightScheduleBookedException("One of the flight schedule has been booked!");
            }
        }
        
        if (flight != null) {
            flight.setFlightSchedulePlan(null);
            em.merge(flight); // Persist the disassociation
        }
        
        // Delete associated FlightSchedules
        for (FlightSchedule fs : fsList) {
            for (FlightCabinClass fcc: fs.getFlightCabinClass()) {
                for (Seat s: fcc.getSeatList()) {
                    em.remove(s);
                }
                fcc.setFlightSchedule(null);
                em.remove(fcc);
            }
            
            fs.setFlightCabinClass(new ArrayList<>());
            em.remove(fs);
        }

        for (Fare fare : fsp.getFare()) {
            em.remove(fare);
        }

        em.remove(fsp);
    }



        
 
}
