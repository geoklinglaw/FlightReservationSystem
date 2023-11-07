/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Person;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.enumeration.PersonRoleType;
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
    public Long createNewPerson(Person newPerson) {
        
            em.persist(newPerson);
            em.flush();

            return newPerson.getId();
            
    }
    
    
//    @Override
//    public Person retrievePersonById(Long id) throws PersonNotFoundException {
//        Person person = em.find(Person.class, id);
//        
//        if (person != null) {
//            return person;
//        } else {
//            throw new PersonNotFoundException("User does not exist: " + id);
//        }
//        
//    }
    
    @Override
    public Person retrievePersonById(Long id) {
        Person person = em.find(Person.class, id);
        if (person != null) {
            return person;
        } else {
            return null;
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
    public Person login(String username, String password) {
        
        Query query = em.createQuery("SELECT p FROM Person p WHERE p.username = :inUsername");
            query.setParameter("inUsername", username);
            Person person = (Person)query.getSingleResult();

            if(person.getPassword().equals(password))
            {
                return person;
            }
            else
            {
                return null;
            }

//        try
//        {
//            Query query = em.createQuery("SELECT p FROM Person p WHERE p.username = :inUsername");
//            query.setParameter("inUsername", username);
//            Person person = (Person)query.getSingleResult();
//            
//            if(person.getPassword().equals(password))
//            {
//                return person;
//            }
//            else
//            {
//                throw new InvalidLoginCredentialException("Invalid login credential");
//            }
//        }
//        catch(NoResultException ex)
//        {
//            throw new InvalidLoginCredentialException("Invalid login credential");
//        }
    }
    
    @Override
    public List<Person> retrieveAllVisitors() {
        
        Query query = em.createQuery("SELECT p from Person p WHERE p.role = :role");
        query.setParameter("role", PersonRoleType.VISITOR);
        List<Person> visitors = query.getResultList();

        return visitors;
        
    }
    
    @Override
    public List<Person> retrieveAllCustomers() {
        
       Query query = em.createQuery("SELECT p from Person p WHERE p.role = :role");
       query.setParameter("role", PersonRoleType.CUSTOMER);
       List<Person> customers = query.getResultList();
//       if (customers.isEmpty()) throw new EmptyListException("List of customers is empty.\n");
        for (Person c : customers) {
            c.getFlightReservations().size();
        }
        
       return customers;
    }
    
    @Override
    public List<Person> retrieveAllPartnerEmployees() {
        
        Query query = em.createQuery("SELECT p from Person p WHERE p.role = :role");
        query.setParameter("role", PersonRoleType.PARTNER_EMPLOYEE);
        List<Person> partnerEmployees = query.getResultList();
        
        return partnerEmployees;
    }
    
    
    @Override
    public List<Person> retrieveAllPartnerReservationManagers() {
        
       Query query = em.createQuery("SELECT p from Person p WHERE p.role = :role");
       query.setParameter("role", PersonRoleType.PARTNER_RESERVATION_MANAGER);
       List<Person> partnerRMs = query.getResultList();
//       if (partnerRMs.isEmpty()) throw new EmptyListException("List of partner reservation managers is empty.\n");
        for (Person p : partnerRMs) {
            p.getFlightReservations().size();
        }
        
       return partnerRMs;
    }
    
}
