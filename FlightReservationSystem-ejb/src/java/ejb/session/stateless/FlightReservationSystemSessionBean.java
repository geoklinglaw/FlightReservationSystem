/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Airport;
import entity.CabinClass;
import entity.Flight;
import entity.FlightCabinClass;
import entity.FlightRoute;
import entity.FlightSchedule;
import entity.Seat;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.CabinClassType;
import util.enumeration.SeatStatus;

/**
 *
 * @author apple
 */
@Stateless
public class FlightReservationSystemSessionBean implements FlightReservationSystemSessionBeanRemote, FlightReservationSystemSessionBeanLocal {

    @EJB(name = "CabinClassSessionBeanLocal")
    private CabinClassSessionBeanLocal cabinClassSessionBeanLocal;

    @EJB(name = "FlightSchedulePlanSessionBeanLocal")
    private FlightSchedulePlanSessionBeanLocal flightSchedulePlanSessionBeanLocal;

    @EJB(name = "FlightRouteSessionBeanLocal")
    private FlightRouteSessionBeanLocal flightRouteSessionBeanLocal;

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    @EJB(name = "AirportEntitySessionBeanLocal")
    private AirportEntitySessionBeanLocal airportEntitySessionBeanLocal;
    
    @EJB
    private FareEntitySessionBeanLocal fareEntitySessionBean;
    
    private static final int MIN_LAYOVER_HOURS = 0; // Minimum layover duration in hours
    private static final int MAX_LAYOVER_HOURS = 24;

    @Override
    public List<List<FlightSchedule>> searchFlightsOneWay(Date startDate, CabinClassType ccType, String originCode, String destCode) {
        System.out.println("entered searchFlightsOneWay");
        List<List<FlightSchedule>> combinedList = new ArrayList<List<FlightSchedule>>();
        List<FlightSchedule> fs0 = searchFlightsByDays(startDate, ccType, originCode, destCode, 0); // exact day
        combinedList.add(fs0);
        List<FlightSchedule> fs1 = searchFlightsByDays(startDate, ccType, originCode, destCode, 1); // one day before
        combinedList.add(fs1);
        List<FlightSchedule> fs2 = searchFlightsByDays(startDate, ccType, originCode, destCode, 2); // two day before
        combinedList.add(fs2);
        List<FlightSchedule> fs3 = searchFlightsByDays(startDate, ccType, originCode, destCode, 3); // three day before
        combinedList.add(fs3);
        
        for (List<FlightSchedule> fslist: combinedList) {
            for (FlightSchedule fs: fslist) {
                System.out.print("its hereeeee " + fs.getFlightSchedulePlan().getFlight().getFlightNumber() + " : " + fs.getDepartureTime() + "  " + fs.getArrivalTime());
            }
        }

        return combinedList;
    }
    
//    public List<FlightSchedule> searchFlightsByDays(Date startDate, CabinClassType ccType, String originCode, String destCode, int daysBefore) {
//
//        List <FlightRoute> routes = flightRouteSessionBeanLocal.viewAllFlightRoute();
//        List <FlightRoute> selectedRoutes = new ArrayList<FlightRoute>();
//        List <Flight> flightListWithFSP = new ArrayList<Flight>();
//        System.out.println(startDate + " " + ccType + " " + originCode + " " + destCode + " " + daysBefore);
//
//        for (FlightRoute r: routes) {
//            System.out.println("curr1: " + r.getOrigin().getAirportCode() + "  " + originCode);
//            System.out.println("curr2: " + r.getDestination().getAirportCode() + "  " + destCode);
//
//            if (r.getOrigin().getAirportCode().equals(originCode) && r.getDestination().getAirportCode().equals(destCode)) {
//                selectedRoutes.add(r);
//                
//                for (Flight f: r.getFlightList()) {
//                    if (f.getFlightSchedulePlan() != null) {
//                        System.out.println("flight " + f.getFlightNumber() + f.getFlightSchedulePlan().getId());
//
//                        System.out.println("selected  "  + f.getFlightNumber());
//                        flightListWithFSP.add(f);
//                    }
//                }
//            }
//        }
//        
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(startDate);
//        calendar.add(Calendar.DAY_OF_MONTH, -daysBefore);
//        Date rangeStartDate = calendar.getTime();
//        
//        List<FlightSchedule> resultSchedules = new ArrayList<>();
//
//        for (Flight flight : flightListWithFSP) {
//            System.out.println(flight.getFlightNumber() + " " + flight.getFlightRoute().getId());
//            Query query = em.createQuery("SELECT fs FROM FlightSchedule fs WHERE fs.flightSchedulePlan.flight.id = :flightId AND fs.departureTime BETWEEN :rangeStartDate AND :endDate");
//            query.setParameter("flightId", flight.getId());
//            query.setParameter("rangeStartDate", rangeStartDate);
//            query.setParameter("endDate", startDate);
//            List<FlightSchedule> fsList = (List<FlightSchedule>) query.getResultList();
//            
//            for (FlightSchedule fs: fsList) {
//                
//                int size = fs.getCabinClass().size();
//                System.out.println(fs.getId() + "  " + size);
//                int size1 = fs.getFlightSchedulePlan().getFare().size();
//                int size3 = fs.getCabinClass().size();
//                if (fs.getCabinClass().size() != 0){
//                    for (CabinClass cc: fs.getCabinClass()) {
//                        System.out.print(cc.getType());
//                    }
//                } else {
//                    System.out.print(" is 0");
//                }
//            }
//            
//            resultSchedules.addAll(fsList);
//        }
//
//        return resultSchedules;
//    }
//}

    public List<FlightSchedule> searchFlightsByDays(Date startDate, CabinClassType ccType, String originCode, String destCode, int daysBefore) {

        List <FlightRoute> routes = flightRouteSessionBeanLocal.viewAllFlightRoute();
        
        for (FlightRoute r: routes) {
            System.out.println("printing flight ");
            for (Flight f: r.getFlightList()){ 
                System.out.println("printing flight " + f.getFlightNumber());

            }
        }
        List <FlightRoute> selectedRoutes = new ArrayList<FlightRoute>();
        List <Flight> flightListWithFSP = new ArrayList<Flight>();
        System.out.println(startDate + " " + ccType + " " + originCode + " " + destCode + " " + daysBefore);

        for (FlightRoute r: routes) {
            System.out.println("curr1: " + r.getOrigin().getAirportCode() + "  " + originCode);
            System.out.println("curr2: " + r.getDestination().getAirportCode() + "  " + destCode);

            if (r.getOrigin().getAirportCode().equals(originCode) && r.getDestination().getAirportCode().equals(destCode)) {
                selectedRoutes.add(r);
                System.out.println("entered here selected routes");                
                System.out.println("FLIGHT " + r.getFlightList().size());


                
                for (Flight flight: r.getFlightList()) {
                    System.out.println("get selected flight " + flight.getFlightNumber());
                    Flight f = em.find(Flight.class, flight.getId());
                    if (f.getFlightSchedulePlan() != null) {
                        System.out.println("flight " + f.getFlightNumber() + f.getFlightSchedulePlan().getId());

                        System.out.println("selected  "  + f.getFlightNumber());
                        flightListWithFSP.add(f);
                    }
                }
            }
        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH, -daysBefore);
        Date targetDate = calendar.getTime(); // This is the specific date we are interested in
            
        
        List<FlightSchedule> resultSchedules = new ArrayList<>();

        for (Flight flight : flightListWithFSP) {
            System.out.println(flight.getFlightNumber() + " " + flight.getFlightRoute().getId());
            Query query = em.createQuery("SELECT fs FROM FlightSchedule fs WHERE fs.flightSchedulePlan.flight.id = :flightId AND fs.departureTime >= :targetDateStart AND fs.departureTime < :targetDateEnd");
            query.setParameter("flightId", flight.getId());
            query.setParameter("targetDateStart", getStartOfDay(targetDate));
            query.setParameter("targetDateEnd", getStartOfNextDay(targetDate));
            List<FlightSchedule> fsList = (List<FlightSchedule>) query.getResultList();
            
            System.out.println("fs list size " + fsList.size());

            
            for (FlightSchedule fs: fsList) {
                int size = fs.getFlightSchedulePlan().getFlight().getAircraftConfig().getCabinClassList().size();
                int size2 = fs.getFlightSchedulePlan().getFare().size();
            }
            
            
            resultSchedules.addAll(fsList);
        }

        return resultSchedules;
    }


    private Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date getStartOfNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return getStartOfDay(calendar.getTime());
    }
    
    public FlightSchedule findFS(Long id) {
        FlightSchedule fs = flightSchedulePlanSessionBeanLocal.retrieveFlightScheduleById(id);
        int size = fs.getFlightSchedulePlan().getFlight().getAircraftConfig().getCabinClassList().size();
        int size2 = fs.getFlightSchedulePlan().getFare().size();
        int size3 = fs.getFlightCabinClass().size();
        System.out.println("flight cabin class size " + size3);
        
        return fs;
    }
   
    public void bookSeats(List<String> seatNumList, Long fccId) {
        FlightCabinClass flightCabin = cabinClassSessionBeanLocal.retrieveFlightCabinClassById(fccId, true);
        int size = flightCabin.getSeatList().size();
        List<Seat> seatList = flightCabin.getSeatList();
        

        for (String seatNum: seatNumList) {
            for (Seat s: seatList) {
                if (seatNum.equals(s.getSeatID())) {
                    s.setSeatStatus(SeatStatus.SELECTED);
                }
            }
        }
        
        flightCabin.setNumReservedSeats(new BigDecimal(flightCabin.getNumReservedSeats().intValue() + seatList.size()));
        flightCabin.setNumReservedSeats(flightCabin.getNumAvailableSeats().subtract(flightCabin.getNumBalanceSeats()));

    }
    
    public List<Pair<FlightSchedule, FlightSchedule>> searchFlightsWithTwo(Date startDate, CabinClassType ccType, String originIATA, String destIATA) {
        System.out.println("entered");
        List<Pair<FlightSchedule, FlightSchedule>> obj = flightRouteSessionBeanLocal.filterConnectingFS();
        if (obj == null) {
            System.out.println("oh no null");
            return null;
        }
        
        return obj;
        
        
    }
    
//    public List<Pair<FlightRoute, FlightRoute>> searchFlightsWithTwoWay(Date startDate, CabinClassType ccType, String originIATA, String destIATA) {
//        // Fetching lists of flight routes based on origin and destination IATA codes
//        List<FlightRoute> originList1 = flightRouteSessionBeanLocal.findOriginFlightRoute(originIATA);
//        List<FlightRoute> destList1 = flightRouteSessionBeanLocal.findDestFlightRoute(destIATA);
//
//        Map<String, List<FlightSchedule>> originScheduleMap = new HashMap<>();
//        Map<String, List<FlightSchedule>> destScheduleMap = new HashMap<>();
//
//        // Populate originScheduleMap
//        for (FlightRoute originRoute : originList1) {
//            for (Flight flight : originRoute.getFlightList()) {
//                for (FlightSchedule schedule : flight.getFlightSchedulePlan().getFlightSchedule()) {
//                    originScheduleMap.computeIfAbsent(originRoute.getDestination().getAirportCode(), k -> new ArrayList<>()).add(schedule);
//                }
//            }
//        }
//
//        // Populate destScheduleMap
//        for (FlightRoute destRoute : destList1) {
//            for (Flight flight : destRoute.getFlightList()) {
//                for (FlightSchedule schedule : flight.getFlightSchedulePlan().getFlightSchedule()) {
//                    destScheduleMap.computeIfAbsent(destRoute.getOrigin().getAirportCode(), k -> new ArrayList<>()).add(schedule);
//                }
//            }
//        }
//
//        List<Pair<FlightRoute, FlightRoute>> pairList = new ArrayList<>();
//
//        for (String originAirportCode : originScheduleMap.keySet()) {
//            List<FlightSchedule> originSchedules = originScheduleMap.get(originAirportCode);
//            for (FlightSchedule originSchedule : originSchedules) {
//                List<FlightSchedule> connectingSchedules = destScheduleMap.get(originSchedule.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getAirportCode());
//                if (connectingSchedules != null) {
//                    for (FlightSchedule connectingSchedule : connectingSchedules) {
//                        if (isValidConnection(originSchedule, connectingSchedule, startDate)) {
//                            FlightRoute originFlightRoute = originSchedule.getFlightSchedulePlan().getFlight().getFlightRoute();
//                            FlightRoute destFlightRoute = connectingSchedule.getFlightSchedulePlan().getFlight().getFlightRoute();
//                            pairList.add(new Pair<>(originFlightRoute, destFlightRoute));
//                        }
//                    }
//                }
//            }
//        }
//
//        return pairList;
//    }
//
//        
//    
//    private boolean isValidConnection(FlightSchedule originSchedule, FlightSchedule connectingSchedule, Date startDate) {
//        // Check if both flights are on the same date as the startDate
//        if (!isSameDate(originSchedule.getDepartureTime(), startDate) || !isSameDate(connectingSchedule.getDepartureTime(), startDate)) {
//            return false;
//        }
//
//        // Check if the layover time between flights is within a reasonable range
//        long layoverDurationInHours = getHoursDifference(originSchedule.getArrivalTime(), connectingSchedule.getDepartureTime());
//        return layoverDurationInHours >= MIN_LAYOVER_HOURS && layoverDurationInHours <= MAX_LAYOVER_HOURS;
//    }
//
//    private boolean isSameDate(Date date1, Date date2) {
//        // Simple date comparison logic, assuming date1 and date2 are not null
//        // This compares only the day, month, and year parts of the dates
//        java.util.Calendar cal1 = java.util.Calendar.getInstance();
//        java.util.Calendar cal2 = java.util.Calendar.getInstance();
//        cal1.setTime(date1);
//        cal2.setTime(date2);
//        return cal1.get(java.util.Calendar.YEAR) == cal2.get(java.util.Calendar.YEAR) &&
//               cal1.get(java.util.Calendar.DAY_OF_YEAR) == cal2.get(java.util.Calendar.DAY_OF_YEAR);
//    }
//
//    private long getHoursDifference(Date date1, Date date2) {
//        // Calculate the difference in hours between two dates manually
//        long diffInMillies = Math.abs(date2.getTime() - date1.getTime());
//        return diffInMillies / (60 * 60 * 1000); // Convert milliseconds to hours
//    }

    
    


}    


