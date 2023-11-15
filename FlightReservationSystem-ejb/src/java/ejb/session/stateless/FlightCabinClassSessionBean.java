/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.FlightCabinClass;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author apple
 */
@Stateless
public class FlightCabinClassSessionBean implements FlightCabinClassSessionBeanRemote, FlightCabinClassSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;
    
    @Override
    public void createNewCabinClass(FlightCabinClass cc) {
        em.persist(cc);
    }
    
    public FlightCabinClass retrieveFlightCabinClassById(Long id) {
        FlightCabinClass cc = em.find(FlightCabinClass.class, id);
        
        return cc;
    }
}
