/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.CabinClass;
import entity.FlightCabinClass;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author apple
 */
@Stateless
public class CabinClassSessionBean implements CabinClassSessionBeanRemote, CabinClassSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;
    
    
    @Override
    public void createNewCabinClass(CabinClass cc) {
        em.persist(cc);
    }
    
    
    public Long createNewFlightCabinClass(FlightCabinClass fcc) {
        em.persist(fcc);
        return fcc.getId();
        
    }
    
    public CabinClass retrieveCabinClassById(Long id) {
        CabinClass cc = em.find(CabinClass.class, id);
        
        return cc;
    }
    
    public FlightCabinClass retrieveFlightCabinClassById(Long id) {
        FlightCabinClass fcc = em.find(FlightCabinClass.class, id);
        
        return fcc;
    }

    public FlightCabinClass retrieveFlightCabinClassById(Long id, boolean needSeats) {
        FlightCabinClass cc = em.find(FlightCabinClass.class, id);
        int size = cc.getSeatList().size();
        
        return cc;
    } 
}
