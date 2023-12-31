/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.FlightBooking;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author apple
 */
@Stateless
public class FlightBookingSessionBean implements FlightBookingSessionBeanRemote, FlightBookingSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    public Long createBooking(FlightBooking booking ) {
        em.persist(booking);
        
        em.flush();
        return booking.getId();
    }


}
