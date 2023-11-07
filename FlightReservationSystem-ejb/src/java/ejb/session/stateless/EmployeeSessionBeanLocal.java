/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Employee;
import java.util.List;
import javax.ejb.Local;
import util.enumeration.EmployeePosition;
import util.exception.EmployeeNotFoundException;
import util.exception.EmployeeUsernameExistException;
import util.exception.EmptyListException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author erin
 */
@Local
public interface EmployeeSessionBeanLocal {
    
//    public Long createNewEmployee(Employee newEmployee) throws EmployeeUsernameExistException, UnknownPersistenceException;
    public Long createNewEmployee(Employee newEmployee);
    
    public Employee retrieveEmployeeById(Long employeeId) throws EmployeeNotFoundException;
    
    public Employee retrieveEmployeeByUsername(String username);
    
    public List<Employee> retrieveAllEmployees() throws EmptyListException;
    
    public Employee login(String username, String password) throws InvalidLoginCredentialException;
}
