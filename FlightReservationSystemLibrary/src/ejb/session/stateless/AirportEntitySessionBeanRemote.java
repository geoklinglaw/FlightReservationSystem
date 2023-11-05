/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Airport;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author apple
 */
@Remote
public interface AirportEntitySessionBeanRemote {
    
    public Long createNewAirport(Airport airport);
    
    public Airport retrieveAirport(Long id);
    
    public List<Airport> retrieveAllAirports();
    
    public Airport retrieveAirportByCode(String code);
    
}
