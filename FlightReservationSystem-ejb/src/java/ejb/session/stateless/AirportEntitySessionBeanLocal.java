/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Airport;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author apple
 */
@Local
public interface AirportEntitySessionBeanLocal {
    
    public Long createNewAirport(Airport airport);
    
    public Airport retrieveAirport(Long id);
    
    public List<Airport> retrieveAllAirports();
    
}
