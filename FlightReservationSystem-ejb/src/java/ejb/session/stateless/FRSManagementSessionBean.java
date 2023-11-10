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
import entity.FlightRoute;
import entity.FlightSchedule;
import entity.Seat;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import util.enumeration.CabinClassType;

/**
 *
 * @author apple
 */
@Stateless
public class FRSManagementSessionBean implements FRSManagementSessionBeanRemote, FRSManagementSessionBeanLocal {

    @EJB(name = "FlightRouteSessionBeanLocal")
    private FlightRouteSessionBeanLocal flightRouteSessionBeanLocal;

    @EJB(name = "AirportEntitySessionBeanLocal")
    private AirportEntitySessionBeanLocal airportEntitySessionBeanLocal;

    @EJB(name = "SeatEntitySessionBeanLocal")
    private SeatEntitySessionBeanLocal seatEntitySessionBeanLocal;

    @EJB(name = "CabinClassSessionBeanLocal")
    private CabinClassSessionBeanLocal cabinClassSessionBeanLocal;

    @EJB(name = "AircraftTypeSessionBeanLocal")
    private AircraftTypeSessionBeanLocal aircraftTypeSessionBeanLocal;

    @EJB
    private AircraftConfigurationSessionBeanLocal aircraftConfigurationSessionBean;
    
    @Override
    public void createAircraftConfiguration(int aircraftType, List<Integer> ccList) {
        
        AircraftType acType = aircraftTypeSessionBeanLocal.retrieveAircraftTypeByValue(aircraftType);
        List<CabinClass> cabinClassList = new ArrayList<CabinClass>();
        
        int seatCapacityForEachCabinClass = acType.getMaxSeatCapacity().intValue() / ccList.size();
        
        // Logic for Assigning Number of Rows
        int numRows;
        int numSRow = 0;

        if (ccList.size() == 1) {
            numRows = 20;
        } else if (ccList.size() == 2) {
            numRows = 10;
        } else if (ccList.size() == 3) {
            numRows = 7;
            numSRow = 6;
        } else {
            numRows = 5;
        }

        
        for (int i = 0; i < ccList.size(); i++) {
            if (acType.getName() == "Boeing 737") {
               if (i == ccList.size() - 1 && numSRow != 0) {
                   CabinClass cc = new CabinClass((int) ccList.get(i), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal("2"), new BigDecimal(numSRow), new BigDecimal("6"), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal(seatCapacityForEachCabinClass));
                   List<Seat> seatList = createSeatsPerCabinClass(6, numSRow, cc);
                   cc.setSeatList(seatList);
                   cabinClassList.add(cc);
               } else {
                   CabinClass cc = new CabinClass((int) ccList.get(i), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal("2"), new BigDecimal(numRows), new BigDecimal("6"), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal(seatCapacityForEachCabinClass));
                   List<Seat> seatList = createSeatsPerCabinClass(6, numRows, cc);
                   cc.setSeatList(seatList);
                   cabinClassList.add(cc);
               }
            } else {
               if (i == ccList.size() - 1 && numSRow != 0) {
                   CabinClass cc = new CabinClass((int) ccList.get(i), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal("2"), new BigDecimal(numSRow), new BigDecimal("9"), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal(seatCapacityForEachCabinClass));
                   List<Seat> seatList = createSeatsPerCabinClass(6, numSRow, cc);
                   cc.setSeatList(seatList);
                   cabinClassList.add(cc);
               } else {
                   CabinClass cc = new CabinClass((int) ccList.get(i), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal("2"), new BigDecimal(numRows), new BigDecimal("9"), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal(seatCapacityForEachCabinClass));
                   List<Seat> seatList = createSeatsPerCabinClass(6, numRows, cc);
                   cc.setSeatList(seatList);
                   cabinClassList.add(cc);
               }
            }
        }
        
        AircraftConfiguration aircraftConfig = new AircraftConfiguration(acType);
        aircraftConfig.setAircraftType(acType);
        aircraftConfig.setCabinClassList(cabinClassList);
        
        for (CabinClass cc: cabinClassList) {
            cc.setAircraftConfig(aircraftConfig);
            cabinClassSessionBeanLocal.createNewCabinClass(cc);
        }
            

        Long acConfig = aircraftConfigurationSessionBean.createNewAircraftConfiguration(aircraftConfig);   
    }
    
    public List<AircraftConfiguration> viewAllAircraftConfiguration() {
        List<AircraftConfiguration> aircraftConfigList = aircraftConfigurationSessionBean.viewAllAircraftConfiguration();
        
        return aircraftConfigList;
    }
    
    public List<Seat> createSeatsPerCabinClass(int numSeatAbreast, int numRows, CabinClass cabinClass) {
        String[] charArr = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I"};
        String seatName = "";
        List<Seat> seatList = new ArrayList<>();
        
        for (int i = 1; i <= numRows; i++) {
            for (int j = 0; j < numSeatAbreast - 1; j++) {
                seatName = i + charArr[j];
                Seat seat = new Seat(seatName, 0);
                seat.setCabinClass(cabinClass);
                seatEntitySessionBeanLocal.createNewSeat(seat);
                seatList.add(seat);
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
        
        List<Airport> odPair = new ArrayList<Airport>();
        odPair.add(origin);
        odPair.add(destination);
        
        FlightRoute flightRoute = new FlightRoute(odPair, 1);
        flightRouteSessionBeanLocal.createNewFlightRoute(flightRoute);
        origin.getFlightRoute().add(flightRoute);
        destination.getFlightRoute().add(flightRoute);
    }
    
    public List<FlightRoute> viewAllFlightRoutes() {
        List<FlightRoute> flightRoute = flightRouteSessionBeanLocal.viewAllFlightRoute();
        return flightRoute;
    }


    @Override
    public ArrayList<Airport> deleteFlightRoute(String originCode, String destCode) {
        Airport originAirport = airportEntitySessionBeanLocal.retrieveAirportByCode(originCode);
        Airport destinationAirport = airportEntitySessionBeanLocal.retrieveAirportByCode(destCode);

        FlightRoute intendedRoute = null;
        for (FlightRoute originRoute: originAirport.getFlightRoute()) {
            for (FlightRoute destRoute: destinationAirport.getFlightRoute()) {
                if (originRoute.getId().equals(destRoute.getId())) {
                    intendedRoute = originRoute;
                    break;
                } 
            }
            if (intendedRoute != null) {
                break; // Stop searching if we found the route
            }
        }

        intendedRoute.setAirportList(new ArrayList<Airport>());
        originAirport.getFlightRoute().remove(intendedRoute);
        destinationAirport.getFlightRoute().remove(intendedRoute);
        flightRouteSessionBeanLocal.deleteFlightRoute(intendedRoute);

        return new ArrayList<Airport>(Arrays.asList(originAirport, destinationAirport));

    }
}
