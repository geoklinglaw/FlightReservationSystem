/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author erin
 */
@Stateless
public class FRSReservationSessionBean implements FRSReservationSessionBeanRemote, FRSReservationSessionBeanLocal {

    @EJB
    private PersonSessionBeanRemote personSessionBean;

//    public Long createNewPerson() {
//        
//    }
//            
//    public void login() {
//        
//    }
//    
    
}
