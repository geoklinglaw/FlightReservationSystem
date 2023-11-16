/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.CabinClass;
import entity.FlightCabinClass;
import javax.ejb.Local;

/**
 *
 * @author apple
 */
@Local
public interface CabinClassSessionBeanLocal {
    
    public void createNewCabinClass(CabinClass cc);
    
    public Long createNewFlightCabinClass(FlightCabinClass fcc);
    
    public CabinClass retrieveCabinClassById(Long id);
    
    public FlightCabinClass retrieveFlightCabinClassById(Long id);
    
    public FlightCabinClass retrieveFlightCabinClassById(Long id, boolean needSeats);
    
}
