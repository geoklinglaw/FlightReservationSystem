/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.AircraftConfiguration;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author apple
 */
@Local
public interface AircraftConfigurationSessionBeanLocal {
    
    public Long createNewAircraftConfiguration(AircraftConfiguration aircraftConfig);
    
    public List<AircraftConfiguration> viewAllAircraftConfiguration();
    
    public AircraftConfiguration retrieveAircraftConfigurationById(Long id);
    
    public AircraftConfiguration retrieveAircraftConfigurationByNameAndStyle(String name, String style);
    
}
