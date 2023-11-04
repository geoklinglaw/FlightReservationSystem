/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.AircraftConfiguration;
import entity.AircraftType;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author apple
 */
@Stateless
public class FRSManagementSessionBean implements FRSManagementSessionBeanRemote, FRSManagementSessionBeanLocal {

    @EJB(name = "AircraftTypeSessionBeanLocal")
    private AircraftTypeSessionBeanLocal aircraftTypeSessionBeanLocal;

    @EJB
    private AircraftConfigurationSessionBeanLocal aircraftConfigurationSessionBean;
    
    public void createAircraftConfiguration(int aircraftType) {
        
        AircraftType acType = aircraftTypeSessionBeanLocal.retrieveAircraftTypeByValue(aircraftType);
        AircraftConfiguration aircraftConfig = new AircraftConfiguration(acType);
        aircraftConfig.setAircraftType(acType);
        
        Long acConfig = aircraftConfigurationSessionBean.createNewAircraftConfiguration(aircraftConfig);

    }


}
