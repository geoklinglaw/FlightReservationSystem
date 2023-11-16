/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.CabinClass;
import entity.FlightCabinClass;
import javax.ejb.Remote;

/**
 *
 * @author apple
 */
@Remote
public interface CabinClassSessionBeanRemote {
    
    public void createNewCabinClass(CabinClass cc);
    
    public void createNewFlightCabinClass(FlightCabinClass fcc);
    
    public FlightCabinClass retrieveFlightCabinClassById(Long id);

    
    public CabinClass retrieveCabinClassById(Long id);
    
    public FlightCabinClass retrieveFlightCabinClassById(Long id, boolean needSeats);


    
}
