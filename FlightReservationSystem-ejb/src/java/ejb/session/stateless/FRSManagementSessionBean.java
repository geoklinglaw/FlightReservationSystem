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
    public void createAircraftConfiguration(String style, int aircraftType, int maxSeats, List<CabinClass> ccList) {
        
        AircraftType acType = aircraftTypeSessionBeanLocal.retrieveAircraftTypeByValue(aircraftType);
        
        AircraftConfiguration aircraftConfig = new AircraftConfiguration(acType, style);
        aircraftConfig.setAircraftType(acType);
        aircraftConfig.setCabinClassList(ccList);
        aircraftConfig.setMaxSeatCapacity(new BigDecimal(maxSeats));
        
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


    
    public List<Airport> viewAllAirports() {
        List<Airport> airportList = airportEntitySessionBeanLocal.retrieveAllAirports();
        return airportList;
    }
    
    public FlightRoute createFlightRoute(Long originId, Long destId) {
        Airport origin = airportEntitySessionBeanLocal.retrieveAirport(originId);
        Airport destination = airportEntitySessionBeanLocal.retrieveAirport(destId);
       
        
        FlightRoute flightRoute = new FlightRoute(origin, destination, 1);
        flightRouteSessionBeanLocal.createNewFlightRoute(flightRoute);
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
    
    
    public Flight createFlight(String flightNum, Long routeId, Long configId) {
        AircraftConfiguration config = aircraftConfigurationSessionBean.retrieveAircraftConfigurationById(configId);
        FlightRoute route = flightRouteSessionBeanLocal.retrieveFlightRouteById(routeId);
        Flight flight = new Flight(flightNum, 1);
        flight.setFlightRoute(route);
        flight.setAircraftConfig(config);
        
        Long flightID = flightSessionBeanLocal.createNewFlight(flight);
        Flight managedFlight = flightSessionBeanLocal.retrieveFlightById(flightID);
        route.getFlightList().add(managedFlight);
        return flight;
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
            compRecurrentWeeklyPlan.setEndDate(((RecurrentPlan) fsp).getEndDate());
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
    
    public FlightRoute viewFlightRoute(Airport origin, Airport destination) {
        FlightRoute route = flightRouteSessionBeanLocal.findSpecificFlightRoute(origin, destination);
        int size = route.getFlightList().size();
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
    
    public List<Flight> checkComplementaryFlightExistence(Airport origin, Airport destination, Long configId) {
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
        
    
}
