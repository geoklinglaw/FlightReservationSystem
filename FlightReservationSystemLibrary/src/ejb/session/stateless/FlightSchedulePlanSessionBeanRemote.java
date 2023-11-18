/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.FlightSchedule;
import entity.FlightSchedulePlan;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author apple
 */
@Remote
public interface FlightSchedulePlanSessionBeanRemote {
    
    public Long createNewFlightSchedulePlan(FlightSchedulePlan fsp);
    
    public void createNewFlightSchedule(FlightSchedule fs);
    
    public List<FlightSchedulePlan> viewAllFlightSchedulePlan();

    public FlightSchedule retrieveFlightScheduleById(Long id);
    
    public void deleteFlightSchedule(FlightSchedule fs);



    
}
