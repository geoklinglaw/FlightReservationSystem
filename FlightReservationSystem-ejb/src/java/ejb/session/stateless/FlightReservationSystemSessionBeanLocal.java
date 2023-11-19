/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.FlightRoute;
import entity.FlightSchedule;
import java.util.Date;
import java.util.List;
import javafx.util.Pair;
import javax.ejb.Local;
import util.enumeration.CabinClassType;

/**
 *
 * @author apple
 */
@Local
public interface FlightReservationSystemSessionBeanLocal {
    
    public List<List<FlightSchedule>> searchFlightsOneWay(Date startDate, CabinClassType ccType, String originCode, String destCode);

    public List<FlightSchedule> searchFlightsByDays(Date startDate, CabinClassType ccType, String originCode, String destCode, int daysBefore);

    public FlightSchedule findFS(Long id) ;
    
    public void bookSeats(List<String> seatNumList, Long ccId);
    
    public List<Pair<FlightSchedule, FlightSchedule>> searchFlightsWithTwo(Date startDate, CabinClassType ccType, String originIATA, String destIATA);
}
