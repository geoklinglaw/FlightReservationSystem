/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.AircraftType;
import javax.ejb.Remote;

/**
 *
 * @author apple
 */
@Remote
public interface AircraftTypeSessionBeanRemote {
    
    public Long createNewAircraftType(AircraftType aircraftType);
    
    public AircraftType retrieveAircraftType(Long id);
    
    public AircraftType retrieveAircraftTypeByValue(int value);
    
}
