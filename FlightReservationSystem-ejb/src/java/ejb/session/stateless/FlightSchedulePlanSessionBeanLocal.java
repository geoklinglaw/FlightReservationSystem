/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.FlightSchedule;
import entity.FlightSchedulePlan;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author apple
 */
@Local
public interface FlightSchedulePlanSessionBeanLocal {
    
    public void createNewFlightSchedulePlan(FlightSchedulePlan fsp);
    
    public void createNewFlightSchedule(FlightSchedule fs);
    
    public List<FlightSchedulePlan> viewAllFlightSchedulePlan();
    
    public FlightSchedule retrieveFlightScheduleById(Long id);
}
