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
    
    
    public List<FlightSchedule> searchFlights(Date startDate, CabinClassType ccType, String originCode, String destCode) {
        List<FlightSchedule> fs0 = searchFlights0(startDate, ccType, originCode, destCode, 0);
        return fs0;
    }
    
    public List<FlightSchedule> searchFlights0(Date startDate, CabinClassType ccType, String originCode, String destCode, int daysBefore) {
//        Airport originAirport = airportEntitySessionBeanLocal.retrieveAirportByCode(originCode);
//        Airport destinationAirport = airportEntitySessionBeanLocal.retrieveAirportByCode(destCode);
 
        
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
        
        for (Flight flight: flightListWithFSP) {
            Query query = em.createQuery("SELECT fs FROM FlightSchedule fs WHERE fs.flight = :flight AND fs.departureTime BETWEEN :rangeStartDate AND :startDate");
            query.setParameter("flight", flight);
            query.setParameter("rangeStartDate", rangeStartDate);
            query.setParameter("startDate", startDate);
            resultSchedules.addAll(query.getResultList());
        }
        
//        
//        
//
//        
//        
//        
//        
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(startDate);
//        calendar.add(Calendar.DAY_OF_MONTH, -daysBefore);
//        Date rangeStartDate = calendar.getTime();
//
//        Query query = em.createQuery("SELECT fs FROM FlightSchedule fs WHERE fs.departureTime BETWEEN :rangeStartDate AND :startDate");
//        query.setParameter("rangeStartDate", rangeStartDate);
//        query.setParameter("startDate", startDate);
//
//        return query.getResultList();
        return resultSchedules;
    }






}
