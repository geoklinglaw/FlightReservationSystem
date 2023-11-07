/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB31/SingletonEjbClass.java to edit this template
 */
package ejb.session.singleton;

import ejb.session.stateless.AircraftConfigurationSessionBeanLocal;
import ejb.session.stateless.AircraftTypeSessionBeanLocal;
import ejb.session.stateless.AirportEntitySessionBeanLocal;
import ejb.session.stateless.EmployeeSessionBeanLocal;
import ejb.session.stateless.FareEntitySessionBeanLocal;
import ejb.session.stateless.PersonSessionBeanLocal;
import entity.AircraftConfiguration;
import entity.AircraftType;
import entity.Airport;
import entity.Employee;
import entity.Fare;
import entity.FlightReservation;
import entity.Person;
import java.util.List;
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
    private EmployeeSessionBeanLocal employeeSessionBean;
    
    @EJB
    private PersonSessionBeanLocal personSessionBean;
    
    @EJB
    private AirportEntitySessionBeanLocal airportEntitySessionBean;

    @EJB
    private AircraftConfigurationSessionBeanLocal aircraftConfigurationSessionBean;

    @EJB
    private AircraftTypeSessionBeanLocal aircraftTypeSessionBean;

    @EJB
    private FareEntitySessionBeanLocal fareEntitySessionBean;
    

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;
    

    @PostConstruct
    public void postConstruct() {
        System.out.println("==== Inside Post Construct Method ====");
        
        if(em.find(Employee.class, 1L) == null) {
            
            Employee emp = new Employee("emp1", "sysAdmin", "password", 4);
            employeeSessionBean.createNewEmployee(emp);
            emp = new Employee("emp2", "fleetManager", "password", 0);
            employeeSessionBean.createNewEmployee(emp);
            emp = new Employee("emp3", "routeManager", "password", 1);
            employeeSessionBean.createNewEmployee(emp);
            emp = new Employee("emp4", "schedManager", "password", 2);
            employeeSessionBean.createNewEmployee(emp);
            emp = new Employee("emp5", "salesManager", "password", 3);
            employeeSessionBean.createNewEmployee(emp);
            
             System.out.println("Created all Employees"); 
        }
        
        if(em.find(Person.class, 1L) == null) {
            
            Person person = new Person("Erin", "Chan", "erinchan@gmail.com", "987654321", "sengkang", "erinchan", "password", 0);
            personSessionBean.createNewPerson(person);
            
//            Person visitor1 = new Person("Erin", "Chan", "erinchan@gmail.com", "987654321", "sengkang", "erinchan", "password", 0);
//            Person visitor2 = new Person("Mary", "Lee", "marylee@gmail.com", "123456789", "woodlands", "marylee", "password", 0);
//            Person customer1 = new Person("John", "Lee", "customer1", "12345", 1);
//            Person customer2 = new Person("Aries", "Chua", "customer2", "54321", 1);
//            Person partnerEmp1 = new Person("Nick", "Tan", "partnerEmp1", "444", 2);
//            Person partnerEmp2 = new Person("Belle", "Tan", "partnerEmp2", "555", 2);
//            Person partnerRM1 = new Person("Candice", "Ang", "partnerRM1", "777", 3);
//            
//            personSessionBean.createNewPerson(visitor1);
//            personSessionBean.createNewPerson(visitor2);
//            personSessionBean.createNewPerson(customer1);
//            personSessionBean.createNewPerson(customer2);
//            personSessionBean.createNewPerson(partnerEmp1);
//            personSessionBean.createNewPerson(partnerEmp2);
//            personSessionBean.createNewPerson(partnerRM1);
            
            System.out.println("Created all Persons: Visitor, Customer, Partners"); 
            
        }
        
        
        System.out.println("== Printing out Visitors");
        List<Person> visitors = personSessionBean.retrieveAllVisitors();
        for (Person v : visitors) {
            System.out.println("Visitor Id " + v.getId());
            System.out.println("Full Name " + v.getFirstName() + " " + v.getLastName());
        }
        
        System.out.println("== Printing out Customers");
        List<Person> customers = personSessionBean.retrieveAllCustomers();
        for (Person c : customers) {
            System.out.println("Customer Id " + c.getId());
            System.out.println("Full Name " + c.getFirstName() + " " + c.getLastName());
            System.out.println("  > Flight Reservations:");
            List<FlightReservation> reservations = c.getFlightReservations();
            for (FlightReservation r : reservations) {
                System.out.println("Number of Flight Bookings for FB" + r.getId() + " = " + r.getFlightBooking().size());
            }
        }
        
        System.out.println("== Printing out Partner Employees");
        List<Person> partnerEmployees = personSessionBean.retrieveAllPartnerEmployees();
        for (Person p : partnerEmployees) {
            System.out.println("Partner Employee Id " + p.getId());
            System.out.println("Full Name " + p.getFirstName() + " " + p.getLastName());
        }
        
        System.out.println("== Printing out Partner Reservation Managers");
        List<Person> partnerRMs = personSessionBean.retrieveAllPartnerReservationManagers();
        for (Person p : partnerRMs) {
            System.out.println("Partner RM Id " + p.getId());
            System.out.println("Full Name " + p.getFirstName() + " " + p.getLastName());
            System.out.println("  > Flight Reservations:");
            List<FlightReservation> reservations = p.getFlightReservations();
            for (FlightReservation r : reservations) {
                System.out.println("Number of Flight Bookings for FB" + r.getId() + " = " + r.getFlightBooking().size());
            }
        }
        
        
        if (em.find(AircraftType.class, 1l) == null) {
            AircraftType aircraftType0 = new AircraftType(1);
            AircraftType aircraftType1 = new AircraftType(0);
            
            Long acType0id = aircraftTypeSessionBean.createNewAircraftType(aircraftType0);
            Long acType1id = aircraftTypeSessionBean.createNewAircraftType(aircraftType1);
            
            System.out.println("Created all AircraftTypes");
//            AircraftType acType0 = aircraftTypeSessionBean.retrieveAircraftType(acType0id);
//            AircraftType acType1 = aircraftTypeSessionBean.retrieveAircraftType(acType1id);
//
//            AircraftConfiguration aircraftConfig0 = new AircraftConfiguration(aircraftType0);
//            AircraftConfiguration aircraftConfig1 = new AircraftConfiguration(aircraftType1);
//            aircraftConfig0.setAircraftType(aircraftType0);
//            aircraftConfig1.setAircraftType(aircraftType1);
            
//            Long acConfig0 = aircraftConfigurationSessionBean.createNewAircraftConfiguration(aircraftConfig0);
//            Long acConfig1 = aircraftConfigurationSessionBean.createNewAircraftConfiguration(aircraftConfig1);
        }
        
        if (em.find(Airport.class, 1l) == null) {
            
            Airport changi = new Airport("SIN", "Changi", "Changi", "Singapore", "Singapore");
            Airport denpasar = new Airport("DPS", "Denpasar", "Bali", "Kuta", "Indonesia");
            Airport taoyuan = new Airport("TW", "Taoyuan", "Taoyuan", "Taoyuan", "Taiwan");
            Airport incheon = new Airport("ICN", "Incheon", "Incheon", "Incheon", "Korea");
            Airport perth = new Airport("PER", "Perth", "Perth", "Perth", "Australia");
            Airport haikou = new Airport("HAK", "Haikou", "Haikou", "Hainan", "China");
            Airport munich = new Airport("MUC", "Munich", "Munich", "Munich", "Germany");
            Airport hongkong = new Airport("HKG", "Hongkong", "Hongkong", "Hongkong", "Hongkong");
            Airport narita = new Airport("NRT", "Narita", "Narita", "Narita", "Japan");
            Airport kualalumpur = new Airport("KUL", "KualaLumpur", "KualaLumpur", "KualaLumpur", "Malaysia");
            
            Long changiID = airportEntitySessionBean.createNewAirport(changi);
            Long denpasarID = airportEntitySessionBean.createNewAirport(denpasar);
            Long taoyuanID = airportEntitySessionBean.createNewAirport(taoyuan);
            Long incheonID = airportEntitySessionBean.createNewAirport(incheon);
            Long perthID = airportEntitySessionBean.createNewAirport(perth);
            Long haikouID = airportEntitySessionBean.createNewAirport(haikou);
            Long munichID = airportEntitySessionBean.createNewAirport(munich);
            Long hongkongID = airportEntitySessionBean.createNewAirport(hongkong);
            Long naritaID = airportEntitySessionBean.createNewAirport(narita);
            Long kualalumpurID = airportEntitySessionBean.createNewAirport(kualalumpur);

            System.out.println("Created all Airports");
            
        }
        
    }



}
