/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.AircraftType;
import entity.Fare;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.AircraftName;
import util.exception.ExceedSeatCapacityException;

/**
 *
 * @author apple
 */
@Stateless
public class AircraftTypeSessionBean implements AircraftTypeSessionBeanRemote, AircraftTypeSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewAircraftType(AircraftType aircraftType) {
        em.persist(aircraftType);
        em.flush();
        
        return aircraftType.getId();
    }
    
    @Override
    public AircraftType retrieveAircraftType(Long id) {
        AircraftType aircraftType = em.find(AircraftType.class, id);
        return aircraftType;

    }
    
    @Override
    public AircraftType retrieveAircraftTypeByValue(int value) {
        
        AircraftName aircraftNameEnum = AircraftName.fromValue(value);
        
        Query query = em.createNamedQuery("selectAircraftTypeByName");
        query.setParameter("inName", aircraftNameEnum);
        
        return (AircraftType) query.getSingleResult();
    }
    
    public Boolean checkForCapacity(int type, BigDecimal pax) throws ExceedSeatCapacityException {
        AircraftName aircraftNameEnum = AircraftName.fromValue(type);
        
        BigDecimal query = (BigDecimal) em.createNamedQuery("selectMaxSeatCapacityByName").setParameter("inName", aircraftNameEnum).getSingleResult();
        
        if (pax.compareTo(query) < 0) {
            return true;
        } else if (pax.compareTo(query) == 0) {
            return true;
        } else {
            throw new ExceedSeatCapacityException("Seat capacity is exceed!");
            
        }
       
    }
        

}
