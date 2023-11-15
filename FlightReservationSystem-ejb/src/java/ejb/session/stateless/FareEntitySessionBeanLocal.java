/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Fare;
import javax.ejb.Local;

/**
 *
 * @author apple
 */
@Local
public interface FareEntitySessionBeanLocal {
    
    public Long createNewFare(Fare fare);
    
    public Fare retrieveFareById(Long id);
    
    public Fare retrieveFareByFBC(String fbc);
    
}
