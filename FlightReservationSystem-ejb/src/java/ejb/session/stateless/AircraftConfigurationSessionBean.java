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
import javax.persistence.NoResultException;
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
        System.out.println("successully persisted ac ");
        em.flush();
        
        return aircraftConfig.getId();
    }
    
    public List<AircraftConfiguration> viewAllAircraftConfiguration() {
        List<AircraftConfiguration> aircraftConfig = em.createNamedQuery("viewAllAircraftConfigurations").getResultList();
        for (AircraftConfiguration config : aircraftConfig) {
            config.getCabinClassList().size();
        }
        return aircraftConfig;
    }
    
    public AircraftConfiguration retrieveAircraftConfigurationById(Long id) {
        AircraftConfiguration config = em.find(AircraftConfiguration.class, id);
        
        return config;
    }
    
    @Override
    public AircraftConfiguration retrieveAircraftConfigurationByNameAndStyle(String name, String style) {
        Query query = em.createNamedQuery("retrieveAircraftConfigurationByNameAndStyle");
        query.setParameter("name", name);
        query.setParameter("style", style);

        return (AircraftConfiguration) query.getSingleResult();

    }

}
