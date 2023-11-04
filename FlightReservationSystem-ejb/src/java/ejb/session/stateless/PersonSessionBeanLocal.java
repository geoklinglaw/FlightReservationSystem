/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Person;
import javax.ejb.Local;
import util.exception.InvalidLoginCredentialException;
import util.exception.PersonNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UsernameExistException;

/**
 *
 * @author erin
 */
@Local
public interface PersonSessionBeanLocal {

    public boolean checkPersonExists(String username);

    public Long registerAsCustomer(Person newPerson) throws UsernameExistException, UnknownPersistenceException;
    
    public Person retrievePersonById(Long id) throws PersonNotFoundException;
    
    public Person retrievePersonByUsername(String username) throws PersonNotFoundException;

    public Person login(String username, String password) throws InvalidLoginCredentialException;
    
}
