/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Person;
import java.util.List;
import javax.ejb.Remote;
import util.exception.InvalidLoginCredentialException;
import util.exception.PersonNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UsernameExistException;

/**
 *
 * @author erin
 */
@Remote
public interface PersonSessionBeanRemote {
    
    public boolean checkPersonExists(String username);
    
    public Long createNewPerson(Person newPerson);
    
//    public Person retrievePersonById(Long id) throws PersonNotFoundException;
    
    public Person retrievePersonById(Long id);
    
    public Person retrievePersonByUsername(String username) throws PersonNotFoundException;
    
    public Person login(String username, String password) throws InvalidLoginCredentialException;
    
    public List<Person> retrieveAllVisitors();
    
    public List<Person> retrieveAllCustomers();
    
    public List<Person> retrieveAllPartnerEmployees();
    
    public List<Person> retrieveAllPartnerReservationManagers();
    
}
