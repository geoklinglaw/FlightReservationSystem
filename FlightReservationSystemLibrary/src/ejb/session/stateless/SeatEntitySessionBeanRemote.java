/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Seat;
import javax.ejb.Remote;

/**
 *
 * @author apple
 */
@Remote
public interface SeatEntitySessionBeanRemote {
    
    public void createNewSeat(Seat seat);
    
    public Seat retrieveSeatById(Long id);

    
}
