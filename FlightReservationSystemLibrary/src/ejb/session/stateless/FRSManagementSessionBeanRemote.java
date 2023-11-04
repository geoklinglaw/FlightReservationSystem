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
public interface FRSManagementSessionBeanRemote {
    
    public void createAircraftConfiguration(int aircraftType, List<Integer> ccList);
    
    public List<AircraftConfiguration> viewAllAircraftConfiguration();
    
}
