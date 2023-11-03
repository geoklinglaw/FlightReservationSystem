/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB31/SingletonEjbClass.java to edit this template
 */
package ejb.session.singleton;

import ejb.session.stateless.AircraftConfigurationSessionBeanLocal;
import ejb.session.stateless.AircraftTypeSessionBeanLocal;
import ejb.session.stateless.FareEntitySessionBeanLocal;
import entity.AircraftConfiguration;
import entity.AircraftType;
import entity.Fare;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author apple
 */
@Startup
@Singleton
@LocalBean
public class DataInitSessionBean implements DataInitSessionBeanLocal {

    @EJB
    private AircraftConfigurationSessionBeanLocal aircraftConfigurationSessionBean;

    @EJB
    private AircraftTypeSessionBeanLocal aircraftTypeSessionBean;

    @EJB
    private FareEntitySessionBeanLocal fareEntitySessionBean;

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;
    

    @PostConstruct
    public void postConstruct() {
        if (em.find(AircraftConfiguration.class, 1l) == null) {
            AircraftType aircraftType0 = new AircraftType(1);
            AircraftType aircraftType1 = new AircraftType(0);
            
            Long acType0id = aircraftTypeSessionBean.createNewAircraftType(aircraftType0);
            Long acType1id = aircraftTypeSessionBean.createNewAircraftType(aircraftType1);
            
            AircraftType acType0 = aircraftTypeSessionBean.retrieveAircraftType(acType0id);
            AircraftType acType1 = aircraftTypeSessionBean.retrieveAircraftType(acType1id);

            AircraftConfiguration aircraftConfig0 = new AircraftConfiguration(aircraftType0);
            AircraftConfiguration aircraftConfig1 = new AircraftConfiguration(aircraftType1);
            aircraftConfig0.setAircraftType(aircraftType0);
            aircraftConfig1.setAircraftType(aircraftType1);
            
            Long acConfig0 = aircraftConfigurationSessionBean.createNewAircraftConfiguration(aircraftConfig0);
            Long acConfig1 = aircraftConfigurationSessionBean.createNewAircraftConfiguration(aircraftConfig1);
            
            

            
            



        }
        
    }



}
