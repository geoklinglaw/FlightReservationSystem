/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Person;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.InvalidLoginCredentialException;
import util.exception.PersonNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UsernameExistException;

/**
 *
 * @author erin
 */
@Stateless
public class PersonSessionBean implements PersonSessionBeanRemote, PersonSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    @Override
    public boolean checkPersonExists(String username) {
        
        Query query = em.createQuery("SELECT p from Person p WHERE p.username = :username");
        query.setParameter("username", username);
        
        try 
        {
            Person person = (Person) query.getSingleResult();
            
            return true;
        } catch (NoResultException e) {
            return false;
        }
        
    }
    
    @Override
    public Long registerAsCustomer(Person newPerson) throws UsernameExistException, UnknownPersistenceException {
        try
        {   
            em.persist(newPerson);
            em.flush();

            return newPerson.getId();
        } 
        catch (PersistenceException ex) {
            if(ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
            {
                if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                {
                    throw new UsernameExistException();
                }
                else
                {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
            else
            {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }
    
    
    @Override
    public Person retrievePersonById(Long id) throws PersonNotFoundException {
        Person person = em.find(Person.class, id);
        
        if (person != null) {
            return person;
        } else {
            throw new PersonNotFoundException("User does not exist: " + id);
        }
        
    }
    
    @Override
    public Person retrievePersonByUsername(String username) throws PersonNotFoundException {
        
        try {
            Query query = em.createQuery("SELECT p FROM Person p WHERE p.username=:name");
            query.setParameter("inUsername", username);
            Person person = (Person) query.getSingleResult();
            
            return person;
            
        } catch (NoResultException e) {
            throw new PersonNotFoundException("User does not exist: " + username);
        }
        
    }
    
    @Override
    public Person login(String username, String password) throws InvalidLoginCredentialException {
      
        try
        {
            Query query = em.createQuery("SELECT p FROM Person p WHERE p.username = :inUsername");
            query.setParameter("inUsername", username);
            Person person = (Person)query.getSingleResult();
            
            if(person.getPassword().equals(password))
            {
                return person;
            }
            else
            {
                throw new InvalidLoginCredentialException("Invalid login credential");
            }
        }
        catch(NoResultException ex)
        {
            throw new InvalidLoginCredentialException("Invalid login credential");
        }
    }
        
    
    
}
