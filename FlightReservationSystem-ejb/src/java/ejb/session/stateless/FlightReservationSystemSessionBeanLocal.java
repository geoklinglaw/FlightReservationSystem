/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.FlightSchedule;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import util.enumeration.CabinClassType;

/**
 *
 * @author apple
 */
@Local
public interface FlightReservationSystemSessionBeanLocal {
    
    public List<FlightSchedule> searchFlights(Date startDate, CabinClassType ccType, String originCode, String destCode);

    
    public List<FlightSchedule> searchFlights0(Date startDate, CabinClassType ccType, String originCode, String destCode, int daysBefore);

    
}
