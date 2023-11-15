/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Fare;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author apple
 */
@Stateless
public class FareEntitySessionBean implements FareEntitySessionBeanRemote, FareEntitySessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    public Long createNewFare(Fare fare) {
        em.persist(fare);
        em.flush();
        return fare.getId();
    }

    public Fare retrieveFareById(Long id) {
        Fare fare = em.find(Fare.class, id);
        
        return fare;
    }
    
    public Fare retrieveFareByFBC(String fbc) {
        Fare fare = (Fare) em.createQuery("SELECT f FROM Fare f WHERE f.fareBasisCode = :fareCode").setParameter("fareCode", fbc).getSingleResult();
        
        return fare;
    }
}
