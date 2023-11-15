package ejb.session.stateless;

import entity.Employee;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.enumeration.EmployeePosition;
import util.exception.EmployeeNotFoundException;
import util.exception.EmployeeUsernameExistException;
import util.exception.EmptyListException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author apple
 */
@Stateless
public class EmployeeSessionBean implements EmployeeSessionBeanRemote, EmployeeSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    public EmployeeSessionBean() {
    }

    
    @Override
    public Long createNewEmployee(Employee newEmployee) {
        em.persist(newEmployee);
        em.flush();

        return newEmployee.getId();
    }
    
    @Override
    public Employee retrieveEmployeeById(Long employeeId) throws EmployeeNotFoundException {
        
        Employee employee = em.find(Employee.class, employeeId);
        
        if (employee != null) {
            return employee;
        } else {
            throw new EmployeeNotFoundException("Employee does not exist: " + employeeId);
        }
    }
    
    @Override
    public Employee retrieveEmployeeByUsername(String username) {
        
        try {
            Query query = em.createQuery("SELECT e FROM Employee e WHERE e.username=:name");
            query.setParameter("inUsername", username);
            Employee employee = (Employee) query.getSingleResult();
            
            return employee;
            
        } catch (NoResultException e) {
            return null;
        }
        
    }
    
    
    @Override
    public List<Employee> retrieveAllEmployees() throws EmptyListException {
        List<Employee> employees;
        Query query = em.createQuery("SELECT e FROM Employee e");
        employees = query.getResultList();
        
        if (employees.isEmpty()) throw new EmptyListException("List of employees is empty.\n");
        
        return employees;
    }
    
    @Override
    public Employee login(String username, String password) throws InvalidLoginCredentialException {
  
        try
        {
            Query query = em.createQuery("SELECT e FROM Employee e WHERE e.username = :inUsername");
            query.setParameter("inUsername", username);
            Employee employee = (Employee)query.getSingleResult();
            
            if(employee.getPassword().equals(password))
            {
                return employee;
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
        

   

