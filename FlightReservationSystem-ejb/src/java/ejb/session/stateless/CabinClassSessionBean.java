/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.CabinClass;
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
    
    public CabinClass retrieveCabinClassById(Long id) {
        CabinClass cc = em.find(CabinClass.class, id);
        int size = cc.getFlightSchedule().size();
        
        return cc;
    }

    
}
