/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.AircraftConfiguration;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author apple
 */
@Stateless
public class FRSManagementSessionBean implements FRSManagementSessionBeanRemote, FRSManagementSessionBeanLocal {

    @EJB
    private AircraftConfigurationSessionBeanLocal aircraftConfigurationSessionBean;
    
    public void createAircraftConfiguration(int aircraftType) {
              
//            AircraftType acType0 = aircraftTypeSessionBean.retrieveAircraftType(acType0id);
//            AircraftType acType1 = aircraftTypeSessionBean.retrieveAircraftType(acType1id);
//
//            AircraftConfiguration aircraftConfig0 = new AircraftConfiguration(aircraftType0);
//            AircraftConfiguration aircraftConfig1 = new AircraftConfiguration(aircraftType1);
//            aircraftConfig0.setAircraftType(aircraftType0);
//            aircraftConfig1.setAircraftType(aircraftType1);
        
        
    }


}
