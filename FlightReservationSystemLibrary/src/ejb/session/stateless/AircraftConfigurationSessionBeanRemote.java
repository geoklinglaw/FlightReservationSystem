/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.AircraftConfiguration;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author apple
 */
@Remote
public interface AircraftConfigurationSessionBeanRemote {
    
    public Long createNewAircraftConfiguration(AircraftConfiguration aircraftConfig);
    
    public List<AircraftConfiguration> viewAllAircraftConfiguration();
    
    public AircraftConfiguration retrieveAircraftConfigurationById(Long id);
    
    public AircraftConfiguration retrieveAircraftConfigurationByNameAndStyle(String name, String style);


    
}
