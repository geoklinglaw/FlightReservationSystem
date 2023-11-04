/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.AircraftConfiguration;
import entity.AircraftType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author apple
 */
@Stateless
public class AircraftConfigurationSessionBean implements AircraftConfigurationSessionBeanRemote, AircraftConfigurationSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    public Long createNewAircraftConfiguration(AircraftConfiguration aircraftConfig) {
        em.persist(aircraftConfig);
        em.flush();
        
        return aircraftConfig.getId();
    }
    
    public List<AircraftConfiguration> viewAllAircraftConfiguration() {
        Query query = em.createNamedQuery("viewAllAircraftConfigurations");
        
        return query.getResultList();
    }
    

}
