/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Airport;
import entity.CabinClass;
import entity.Flight;
import entity.FlightRoute;
import entity.FlightSchedule;
import entity.Seat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    
    
    public List<List<FlightSchedule>> searchFlightsOneWay(Date startDate, CabinClassType ccType, String originCode, String destCode) {
        List<List<FlightSchedule>> combinedList = new ArrayList<List<FlightSchedule>>();
        List<FlightSchedule> fs0 = searchFlightsByDays(startDate, ccType, originCode, destCode, 0); // exact day
        combinedList.add(fs0);
        List<FlightSchedule> fs1 = searchFlightsByDays(startDate, ccType, originCode, destCode, 1); // one day before
        combinedList.add(fs1);
        List<FlightSchedule> fs2 = searchFlightsByDays(startDate, ccType, originCode, destCode, 2); // two day before
        combinedList.add(fs2);
        List<FlightSchedule> fs3 = searchFlightsByDays(startDate, ccType, originCode, destCode, 3); // three day before
        combinedList.add(fs3);

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
        List <FlightRoute> selectedRoutes = new ArrayList<FlightRoute>();
        List <Flight> flightListWithFSP = new ArrayList<Flight>();
        System.out.println(startDate + " " + ccType + " " + originCode + " " + destCode + " " + daysBefore);

        for (FlightRoute r: routes) {
            System.out.println("curr1: " + r.getOrigin().getAirportCode() + "  " + originCode);
            System.out.println("curr2: " + r.getDestination().getAirportCode() + "  " + destCode);

            if (r.getOrigin().getAirportCode().equals(originCode) && r.getDestination().getAirportCode().equals(destCode)) {
                selectedRoutes.add(r);
                System.out.println("entered here selected routes");

                
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
            
            for (FlightSchedule fs: fsList) {
                int size = fs.getFlightSchedulePlan().getFlight().getAircraftConfig().getCabinClassList().size();
                int size2 = fs.getFlightSchedulePlan().getFare().size();
            }
            
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
        
        return fs;
    }
   
    public void bookSeats(List<String> seatNumList, Long ccId) {
        CabinClass cabin = cabinClassSessionBeanLocal.retrieveCabinClassById(ccId, true);
        int size = cabin.getSeatList().size();
        
        for (String seatNum: seatNumList) {
            Seat seat = (Seat) em.createQuery("SELECT s FROM Seat s WHERE s.seatID = :inSeatNum").setParameter("inSeatNum", seatNum).getSingleResult();
            seat.setSeatStatus(SeatStatus.SELECTED);
        }
    }
    
    
}