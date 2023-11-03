/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.AircraftType;
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
public class AircraftTypeSessionBean implements AircraftTypeSessionBeanRemote, AircraftTypeSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    public Long createNewAircraftType(AircraftType aircraftType) {
        em.persist(aircraftType);
        em.flush();
        
        return aircraftType.getId();
    }
    
    public AircraftType retrieveAircraftType(Long id) {
        AircraftType aircraftType = em.find(AircraftType.class, id);
        return aircraftType;

    }
    
    public AircraftType retrieveAircraftTypeByValue(int value) {
        String jpql = "SELECT act FROM AircraftType act WHERE act.name = :Type";
        Query query = em.createQuery(jpql);
        query.setParameter("Type", (String) value);
        Person result = (Person) query.getSingleResult();
    }
        

}
