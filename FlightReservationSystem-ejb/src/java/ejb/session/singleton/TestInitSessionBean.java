/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB31/SingletonEjbClass.java to edit this template
 */
package ejb.session.singleton;

import ejb.session.stateless.AircraftConfigurationSessionBeanLocal;
import ejb.session.stateless.AircraftTypeSessionBeanLocal;
import ejb.session.stateless.AirportEntitySessionBeanLocal;
import ejb.session.stateless.CabinClassSessionBeanLocal;
import ejb.session.stateless.EmployeeSessionBeanLocal;
import ejb.session.stateless.FareEntitySessionBeanLocal;
import ejb.session.stateless.FlightRouteSessionBeanLocal;
import ejb.session.stateless.FlightSchedulePlanSessionBeanLocal;
import ejb.session.stateless.FlightSessionBeanLocal;
import ejb.session.stateless.SeatEntitySessionBeanLocal;
import entity.AircraftConfiguration;
import entity.AircraftType;
import entity.Airport;
import entity.CabinClass;
import entity.Employee;
import entity.Flight;
import entity.FlightReservation;
import entity.FlightRoute;
import entity.Partner;
import entity.Person;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.AircraftName;
import util.enumeration.FlightRouteStatus;
import util.enumeration.FlightStatus;

/**
 *
 * @author apple
 */
//@Startup
//@Singleton
//@LocalBean
public class TestInitSessionBean implements TestInitSessionBeanLocal {

    @EJB(name = "EmployeeSessionBeanLocal")
    private EmployeeSessionBeanLocal employeeSessionBeanLocal;

    @EJB
    private AirportEntitySessionBeanLocal airportEntitySessionBean;
    
    @EJB(name = "FareEntitySessionBeanLocal")
    private FareEntitySessionBeanLocal fareEntitySessionBeanLocal;

    @EJB(name = "FlightRouteSessionBeanLocal")
    private FlightRouteSessionBeanLocal flightRouteSessionBeanLocal;

    @EJB(name = "FlightSessionBeanLocal")
    private FlightSessionBeanLocal flightSessionBeanLocal;
    
    @EJB(name = "AirportEntitySessionBeanLocal")
    private AirportEntitySessionBeanLocal airportEntitySessionBeanLocal;

    @EJB(name = "SeatEntitySessionBeanLocal")
    private SeatEntitySessionBeanLocal seatEntitySessionBeanLocal;

    @EJB(name = "CabinClassSessionBeanLocal")
    private CabinClassSessionBeanLocal cabinClassSessionBeanLocal;

    @EJB(name = "AircraftTypeSessionBeanLocal")
    private AircraftTypeSessionBeanLocal aircraftTypeSessionBeanLocal;

    @EJB(name = "FlightSchedulePlanSessionBeanLocal")
    private FlightSchedulePlanSessionBeanLocal flightSchedulePlanSessionBeanLocal;


    @EJB
    private AircraftConfigurationSessionBeanLocal aircraftConfigurationSessionBean;
    
    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;
    
    @PostConstruct
    public void postConstruct() {
        System.out.println("==== Inside Post Construct Method ====");
        
        if (em.find(Employee.class, 1L) == null) {
            Employee emp1 = new Employee("Fleet Manager", "fleetmanager", "password", 0);
            employeeSessionBeanLocal.createNewEmployee(emp1);

            Employee emp2 = new Employee("Route Planner", "routeplanner", "password", 1);
            employeeSessionBeanLocal.createNewEmployee(emp1);
            
            Employee emp3 = new Employee("Schedule Manager", "schedulemanager", "password", 2);
            employeeSessionBeanLocal.createNewEmployee(emp1);
            
            Employee emp4 = new Employee("Sales Manager", "salesmanager", "password", 3);
            
            employeeSessionBeanLocal.createNewEmployee(emp1);
            employeeSessionBeanLocal.createNewEmployee(emp2);
            employeeSessionBeanLocal.createNewEmployee(emp3);
            employeeSessionBeanLocal.createNewEmployee(emp4);
        }
        
        if (em.find(Partner.class, 1L) == null) {
           Partner partnerEmp1 = new Partner("PartnerName", "username1", "password1");
           Partner partnerEm2 = new Partner("Holiday", "username2", "password2");

        }
       
        if (em.find(Airport.class, 1L) == null) {
            Airport changi = new Airport("SIN", "Changi", "Changi", "Singapore", "Singapore");
            Airport taoyuan = new Airport("TPE", "Taoyuan", "Taoyuan", "Taoyuan", "Taiwan");
            Airport perth = new Airport("PER", "Perth", "Perth", "Perth", "Australia");
            Airport hongkong = new Airport("HKG", "Hongkong", "Hongkong", "Hongkong", "Hongkong");
            Airport narita = new Airport("NRT", "Narita", "Narita", "Narita", "Japan");
            
            Long changiID = airportEntitySessionBean.createNewAirport(changi);
            Long taoyuanID = airportEntitySessionBean.createNewAirport(taoyuan);
            Long perthID = airportEntitySessionBean.createNewAirport(perth);
            Long hongkongID = airportEntitySessionBean.createNewAirport(hongkong);
            Long naritaID = airportEntitySessionBean.createNewAirport(narita);
        }
        
        if (em.find(AircraftConfiguration.class, 1L) == null) {
            AircraftType boeing737 = new AircraftType(AircraftName.B737.getValue());
            AircraftType boeing747 = new AircraftType(AircraftName.B747.getValue());
            
            AircraftConfiguration boeing737AllEconomy = new AircraftConfiguration(boeing737, "All Economy");
            boeing737AllEconomy.getCabinClassList().add(new CabinClass("Y", new BigDecimal(180), new BigDecimal(1), new BigDecimal(30), new BigDecimal(6), "3-3"));

            AircraftConfiguration boeing737ThreeClasses = new AircraftConfiguration(boeing737, "Three Classes");
            boeing737ThreeClasses.getCabinClassList().add(new CabinClass("F", new BigDecimal(10), new BigDecimal(1), new BigDecimal(5), new BigDecimal(2), "1-1"));
            boeing737ThreeClasses.getCabinClassList().add(new CabinClass("J", new BigDecimal(20), new BigDecimal(1), new BigDecimal(5), new BigDecimal(4), "2-2"));
            boeing737ThreeClasses.getCabinClassList().add(new CabinClass("Y", new BigDecimal(150), new BigDecimal(1), new BigDecimal(25), new BigDecimal(6), "3-3"));

            

            AircraftConfiguration boeing747AllEconomy = new AircraftConfiguration(boeing747, "All Economy");
            boeing747AllEconomy.getCabinClassList().add(new CabinClass("Y", new BigDecimal(380), new BigDecimal(2), new BigDecimal(38), new BigDecimal(10), "3-4-3"));


            AircraftConfiguration boeing747ThreeClasses = new AircraftConfiguration(boeing747, "Three Classes");
            boeing747ThreeClasses.getCabinClassList().add(new CabinClass("F", new BigDecimal(10), new BigDecimal(1), new BigDecimal(5), new BigDecimal(2), "1-1"));
            boeing747ThreeClasses.getCabinClassList().add(new CabinClass("J", new BigDecimal(30), new BigDecimal(2), new BigDecimal(5), new BigDecimal(6), "2-2-2"));
            boeing747ThreeClasses.getCabinClassList().add(new CabinClass("Y", new BigDecimal(320), new BigDecimal(2), new BigDecimal(32), new BigDecimal(10), "3-4-3"));

        }   
        
        if (em.find(FlightRoute.class, 1L) == null) {
            Airport sin = airportEntitySessionBean.retrieveAirportByCode("SIN");
            Airport hkg = airportEntitySessionBean.retrieveAirportByCode("HKG");
            Airport tpe = airportEntitySessionBean.retrieveAirportByCode("TPE");
            Airport nrt = airportEntitySessionBean.retrieveAirportByCode("NRT");
            Airport syd = airportEntitySessionBean.retrieveAirportByCode("SYD");
            
            FlightRoute sinToHkg = new FlightRoute(sin, hkg, FlightRouteStatus.ACTIVE.getValue());
            FlightRoute hkgToSin = new FlightRoute(hkg, sin, FlightRouteStatus.ACTIVE.getValue());
        }
        
        if (em.find(Flight.class, 1L) == null) {

        }
        
    }
}
