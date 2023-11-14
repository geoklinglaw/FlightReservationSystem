/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.FlightSchedule;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import util.enumeration.CabinClassType;

/**
 *
 * @author apple
 */
@Remote
public interface FlightReservationSystemSessionBeanRemote {
    
    public List<List<FlightSchedule>> searchFlightsOneWay(Date startDate, CabinClassType ccType, String originCode, String destCode);

    public List<FlightSchedule> searchFlightsByDays(Date startDate, CabinClassType ccType, String originCode, String destCode, int daysBefore);

}
