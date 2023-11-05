/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.FlightRoute;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author apple
 */
@Stateless
public class FlightRouteSessionBean implements FlightRouteSessionBeanRemote, FlightRouteSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    @Override
    public void createNewFlightRoute(FlightRoute flightRoute) {
        em.persist(flightRoute);
    }
    
    @Override
    public List<FlightRoute> viewAllFlightRoute() {
        List<FlightRoute> flightRoutes = em.createNamedQuery("viewAllFlightRoutes").getResultList();
        
        for (FlightRoute flightRoute: flightRoutes) {
            flightRoute.getAirportList().size();
        }
        
        return flightRoutes;
    }
    
    public void deleteFlightRoute(FlightRoute route) {
        em.remove(route);
    }


}
