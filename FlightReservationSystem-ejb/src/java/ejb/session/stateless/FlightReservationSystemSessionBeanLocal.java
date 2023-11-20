/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Fare;
import entity.FlightBooking;
import entity.FlightRoute;
import entity.FlightSchedule;
import entity.Seat;
import java.util.Date;
import java.util.List;
import javafx.util.Pair;
import javax.ejb.Local;
import util.enumeration.CabinClassType;
import util.exception.PersonNotFoundException;

/**
 *
 * @author apple
 */
@Local
public interface FlightReservationSystemSessionBeanLocal {
    
    public List<FlightSchedule> searchFlightsByDays(Date startDate, CabinClassType ccType, String originCode, String destCode, int daysBefore, boolean isBefore);

    public List<List<FlightSchedule>> searchFlightsOneWay(Date startDate, CabinClassType ccType, String originCode, String destCode);

    public FlightSchedule findFS(Long id) ;
    
    public List<Seat> bookSeats(List<String> seatNumList, Long fccId);

    
    public List<Pair<FlightSchedule, FlightSchedule>> searchFlightsWithTwo(Date startDate, CabinClassType ccType, String originIATA, String destIATA);

    public Long makeReservation(Long fsID, String name, List<String> seatNumList, Long fccID, int numPass, Fare fare) throws PersonNotFoundException;
    
}
