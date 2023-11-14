/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Airport;
import entity.Flight;
import entity.FlightRoute;
import entity.FlightSchedule;
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

/**
 *
 * @author apple
 */
@Stateless
public class FlightReservationSystemSessionBean implements FlightReservationSystemSessionBeanRemote, FlightReservationSystemSessionBeanLocal {

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



        return combinedList;
    }
    
    public List<FlightSchedule> searchFlightsByDays(Date startDate, CabinClassType ccType, String originCode, String destCode, int daysBefore) {

        List <FlightRoute> routes = flightRouteSessionBeanLocal.viewAllFlightRoute();
        List <FlightRoute> selectedRoutes = new ArrayList<FlightRoute>();
        List <Flight> flightListWithFSP = new ArrayList<Flight>();

        for (FlightRoute r: routes) {
            if (r.getAirportList().get(0).getAirportCode().equals(originCode) && r.getAirportList().get(1).getAirportCode().equals(destCode)) {
                selectedRoutes.add(r);
                
                for (Flight f: r.getFlightList()) {
                    if (f.getFlightSchedulePlan() != null) {
                        flightListWithFSP.add(f);
                    }
                }
            }
        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH, -daysBefore);
        Date rangeStartDate = calendar.getTime();
        
        List<FlightSchedule> resultSchedules = new ArrayList<>();
        
        for (Flight flight : flightListWithFSP) {
            System.out.println(flight.getFlightNumber() + " " + flight.getFlightRoute().getId());
            Query query = em.createQuery("SELECT fs FROM FlightSchedule fs WHERE fs.flightSchedulePlan.flight.id = :flightId AND fs.departureTime BETWEEN :rangeStartDate AND :endDate");
            query.setParameter("flightId", flight.getId());
            query.setParameter("rangeStartDate", rangeStartDate);
            query.setParameter("endDate", startDate);
            List<FlightSchedule> fsList = (List<FlightSchedule>) query.getResultList();
            
            for (FlightSchedule fs: fsList) {
                
                int size = fs.getCabinClass().size();
                System.out.println(fs.getId() + "  " + size);
                int size1 = fs.getFlightSchedulePlan().getFare().size();

            }
            
            resultSchedules.addAll(fsList);
        }

        return resultSchedules;
    }
}
