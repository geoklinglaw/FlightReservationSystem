/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.FlightSchedule;
import entity.FlightSchedulePlan;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author apple
 */
@Stateless
public class FlightSchedulePlanSessionBean implements FlightSchedulePlanSessionBeanRemote, FlightSchedulePlanSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewFlightSchedulePlan(FlightSchedulePlan fsp) {
        em.persist(fsp);
        em.flush();
        return fsp.getId();
    }
   
    @Override
    public void createNewFlightSchedule(FlightSchedule fs) {
        em.persist(fs);
    }
    
    public List<FlightSchedulePlan> viewAllFlightSchedulePlan() {
        List<FlightSchedulePlan> fspList = em.createNamedQuery("viewAllFlightSchedulePlan").getResultList();
        for (FlightSchedulePlan fsp: fspList) {
            int size = fsp.getFlightSchedule().size();
        }
        return fspList;
    }
    
    public FlightSchedule retrieveFlightScheduleById(Long id) {
        FlightSchedule fs = em.find(FlightSchedule.class, id);
        
        return fs;
    }
    
    public FlightSchedulePlan retrieveFlightSchedulePlanById(Long id) {
        em.setProperty ("javax.persistence.cache.storeMode", "REFRESH");
        FlightSchedulePlan fsp = em.find(FlightSchedulePlan.class, id);
        int size1 = fsp.getFlightSchedule().size();
        int size2 = fsp.getFare().size();
        
        return fsp;
    }
    
    public void deleteFlightSchedule(FlightSchedule fs) {
        em.remove(fs);
    }
}
