/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB31/SingletonEjbClass.java to edit this template
 */
package ejb.session.singleton;

import ejb.session.stateless.FareEntitySessionBeanLocal;
import entity.Fare;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author apple
 */
@Startup
@Singleton
@LocalBean
public class DataInitSessionBean implements DataInitSessionBeanLocal {

    @EJB
    private FareEntitySessionBeanLocal fareEntitySessionBean;

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    @PostConstruct
    public void postConstruct() {
        if (em.find(Fare.class, 1l) == null) {
            
//            Fare newFare = new Fare(E)
//            fareEntitySessionBean.createNewFare();
            

        }
        
    }



}
