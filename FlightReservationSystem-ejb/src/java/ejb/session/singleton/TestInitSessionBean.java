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
@Startup
@Singleton
@LocalBean
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
    
    @EJB
    private FlightRouteSessionBeanLocal flightRouteSessionBean;
    
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
            Airport taoyuan = new Airport("TPE", "Taoyuan", "Taoyuan", "Taipei", "Taiwan R.O.C");
            Airport sydney = new Airport("SYD", "Sydney", "Sydney", "New South Wales", "Australia");
            Airport hongkong = new Airport("HKG", "Hong Kong", "Chek Lap Kok", "Hongkong", "Hongkong");
            Airport narita = new Airport("NRT", "Narita", "Narita", "Chiba", "Japan");
            
        
//            Airport bangkok = new Airport("BKK", "Bangkok", "Bangkok", "Bangkok", "Thailand");
            
            Long changiID = airportEntitySessionBean.createNewAirport(changi);
            Long taoyuanID = airportEntitySessionBean.createNewAirport(taoyuan);
            Long sydneyID = airportEntitySessionBean.createNewAirport(sydney);
            Long hongkongID = airportEntitySessionBean.createNewAirport(hongkong);
            Long naritaID = airportEntitySessionBean.createNewAirport(narita);
//            Long bangkokID = airportEntitySessionBean.createNewAirport(bangkok);
            
        }
        
        if (em.find(AircraftConfiguration.class, 1L) == null) {
            AircraftType boeing737 = new AircraftType(AircraftName.B737.getValue(), 200);
            AircraftType boeing747 = new AircraftType(AircraftName.B747.getValue(), 400);
            
            aircraftTypeSessionBeanLocal.createNewAircraftType(boeing737);
            aircraftTypeSessionBeanLocal.createNewAircraftType(boeing747);
            
            AircraftConfiguration boeing737AllEconomy = new AircraftConfiguration(boeing737, "All Economy");
            aircraftConfigurationSessionBean.createNewAircraftConfiguration(boeing737AllEconomy);
            
            CabinClass cc0 = new CabinClass("Y", new BigDecimal(180), new BigDecimal(1), new BigDecimal(30), new BigDecimal(6), "3-3");
            cabinClassSessionBeanLocal.createNewCabinClass(cc0);
            boeing737AllEconomy.getCabinClassList().add(cc0);
            
            for (CabinClass cc: boeing737AllEconomy.getCabinClassList()) {
                cc.setAircraftConfig(boeing737AllEconomy);
            }
            

            AircraftConfiguration boeing737ThreeClasses = new AircraftConfiguration(boeing737, "Three Classes");
            
            System.out.println(boeing737ThreeClasses.getName() + "  " + boeing737ThreeClasses.getAircraftStyle());
            aircraftConfigurationSessionBean.createNewAircraftConfiguration(boeing737ThreeClasses);
            
            
            CabinClass cc1 = new CabinClass("F", new BigDecimal(10), new BigDecimal(1), new BigDecimal(5), new BigDecimal(2), "1-1");
            CabinClass cc2 = new CabinClass("J", new BigDecimal(20), new BigDecimal(1), new BigDecimal(5), new BigDecimal(4), "2-2");
            CabinClass cc3 = new CabinClass("Y", new BigDecimal(150), new BigDecimal(1), new BigDecimal(25), new BigDecimal(6), "3-3");
            
            cabinClassSessionBeanLocal.createNewCabinClass(cc3);
            cabinClassSessionBeanLocal.createNewCabinClass(cc2);
            cabinClassSessionBeanLocal.createNewCabinClass(cc1);
            
            boeing737ThreeClasses.getCabinClassList().add(cc1);
            boeing737ThreeClasses.getCabinClassList().add(cc2);
            boeing737ThreeClasses.getCabinClassList().add(cc3);
            
            for (CabinClass cc: boeing737ThreeClasses.getCabinClassList()) {
                cc.setAircraftConfig(boeing737ThreeClasses);
            }

            AircraftConfiguration boeing747AllEconomy = new AircraftConfiguration(boeing747, "All Economy");
            aircraftConfigurationSessionBean.createNewAircraftConfiguration(boeing747AllEconomy);
            CabinClass cc4 = new CabinClass("Y", new BigDecimal(380), new BigDecimal(2), new BigDecimal(38), new BigDecimal(10), "3-4-3");
            cabinClassSessionBeanLocal.createNewCabinClass(cc4);
            boeing747AllEconomy.getCabinClassList().add(cc4);
            

            for (CabinClass cc: boeing747AllEconomy.getCabinClassList()) {
                cc.setAircraftConfig(boeing747AllEconomy);
            }
            
            AircraftConfiguration boeing747ThreeClasses = new AircraftConfiguration(boeing747, "Three Classes");
            aircraftConfigurationSessionBean.createNewAircraftConfiguration(boeing747ThreeClasses);
            CabinClass cc5 = new CabinClass("F", new BigDecimal(10), new BigDecimal(1), new BigDecimal(5), new BigDecimal(2), "1-1");
            CabinClass cc6 = new CabinClass("J", new BigDecimal(30), new BigDecimal(2), new BigDecimal(5), new BigDecimal(6), "2-2-2");
            CabinClass cc7 = new CabinClass("Y", new BigDecimal(320), new BigDecimal(2), new BigDecimal(32), new BigDecimal(10), "3-4-3");
            
            cabinClassSessionBeanLocal.createNewCabinClass(cc5);
            cabinClassSessionBeanLocal.createNewCabinClass(cc6);
            cabinClassSessionBeanLocal.createNewCabinClass(cc7);
            
            
            boeing747ThreeClasses.getCabinClassList().add(cc5);
            boeing747ThreeClasses.getCabinClassList().add(cc6);
            boeing747ThreeClasses.getCabinClassList().add(cc7);
            
            for (CabinClass cc: boeing747ThreeClasses.getCabinClassList()) {
                cc.setAircraftConfig(boeing747ThreeClasses);
            }
        }   
        
        if (em.find(FlightRoute.class, 1L) == null) {
            Airport sin = airportEntitySessionBean.retrieveAirportByIATA("SIN");
            Airport hkg = airportEntitySessionBean.retrieveAirportByIATA("HKG");
            Airport tpe = airportEntitySessionBean.retrieveAirportByIATA("TPE");
            Airport nrt = airportEntitySessionBean.retrieveAirportByIATA("NRT");
            Airport syd = airportEntitySessionBean.retrieveAirportByIATA("SYD");
            
            
            FlightRoute sinToHkg = new FlightRoute(sin, hkg, FlightRouteStatus.ACTIVE.getValue());
            FlightRoute hkgToSin = new FlightRoute(hkg, sin, FlightRouteStatus.ACTIVE.getValue());
            FlightRoute sinToTpe = new FlightRoute(sin, tpe, FlightRouteStatus.ACTIVE.getValue());
            FlightRoute tpeToSin = new FlightRoute(tpe, sin, FlightRouteStatus.ACTIVE.getValue());
            FlightRoute sinToNrt = new FlightRoute(sin, nrt, FlightRouteStatus.ACTIVE.getValue());
            FlightRoute nrtToSin = new FlightRoute(nrt, sin, FlightRouteStatus.ACTIVE.getValue());
            FlightRoute hkgToNrt = new FlightRoute(hkg, nrt, FlightRouteStatus.ACTIVE.getValue());
            FlightRoute nrtToHkg = new FlightRoute(nrt, hkg, FlightRouteStatus.ACTIVE.getValue());
            FlightRoute tpeToNrt = new FlightRoute(tpe, nrt, FlightRouteStatus.ACTIVE.getValue());
            FlightRoute nrtToTpe = new FlightRoute(nrt, tpe, FlightRouteStatus.ACTIVE.getValue());
            FlightRoute sinToSyd = new FlightRoute(sin, syd, FlightRouteStatus.ACTIVE.getValue());
            FlightRoute sydToSin = new FlightRoute(syd, sin, FlightRouteStatus.ACTIVE.getValue());
            FlightRoute sydToNrt = new FlightRoute(syd, nrt, FlightRouteStatus.ACTIVE.getValue());
            FlightRoute nrtToSyd = new FlightRoute(nrt, syd, FlightRouteStatus.ACTIVE.getValue());
        
        
            flightRouteSessionBean.createNewFlightRoute(sinToHkg, true);
            flightRouteSessionBean.createNewFlightRoute(hkgToSin, true);
            flightRouteSessionBean.createNewFlightRoute(sinToTpe, true);
            flightRouteSessionBean.createNewFlightRoute(tpeToSin, true);
            flightRouteSessionBean.createNewFlightRoute(sinToNrt, true);
            flightRouteSessionBean.createNewFlightRoute(nrtToSin, true);
            flightRouteSessionBean.createNewFlightRoute(hkgToNrt, true);
            flightRouteSessionBean.createNewFlightRoute(nrtToHkg, true);
            flightRouteSessionBean.createNewFlightRoute(tpeToNrt, true);
            flightRouteSessionBean.createNewFlightRoute(nrtToTpe, true);
            flightRouteSessionBean.createNewFlightRoute(sinToSyd, true);
            flightRouteSessionBean.createNewFlightRoute(sydToSin, true);
            flightRouteSessionBean.createNewFlightRoute(sydToNrt, true);
            flightRouteSessionBean.createNewFlightRoute(nrtToSyd, true);

            
            em.flush();


        if (em.find(Flight.class, 1L) == null) {
            AircraftConfiguration ac1 = aircraftConfigurationSessionBean.retrieveAircraftConfigurationByNameAndStyle("Boeing 737", "Three Classes");
            
            Flight ML111 = new Flight("ML111", 1);
            ML111.setFlightRoute(sinToHkg);
            ML111.setAircraftConfig(ac1);
           
            
            Flight ML112 = new Flight("ML112", 1);
            ML112.setFlightRoute(hkgToSin);
            ML112.setAircraftConfig(ac1);
            
            Flight ML211 = new Flight("ML211", 1);
            ML211.setFlightRoute(sinToTpe);
            ML211.setAircraftConfig(ac1);
            
            Flight ML212 = new Flight("ML212", 1);
            ML212.setFlightRoute(tpeToSin);
            ML212.setAircraftConfig(ac1);
            
            AircraftConfiguration ac2 = aircraftConfigurationSessionBean.retrieveAircraftConfigurationByNameAndStyle("Boeing 747", "Three Classes");
            
            Flight ML311 = new Flight("ML311", 1);
            ML311.setFlightRoute(sinToNrt);
            ML311.setAircraftConfig(ac2);
            
            Flight ML312 = new Flight("ML312", 1);
            ML312.setFlightRoute(nrtToSin);
            ML312.setAircraftConfig(ac2);
            
            Flight ML411 = new Flight("ML411", 1);
            ML411.setFlightRoute(hkgToNrt);
            ML411.setAircraftConfig(ac1);
            
            Flight ML412 = new Flight("ML412", 1);
            ML412.setFlightRoute(nrtToHkg);
            ML412.setAircraftConfig(ac1);
            
            Flight ML511 = new Flight("ML511", 1);
            ML511.setFlightRoute(tpeToNrt);
            ML511.setAircraftConfig(ac1);
            
            Flight ML512 = new Flight("ML512", 1);
            ML512.setFlightRoute(nrtToTpe);
            ML512.setAircraftConfig(ac1);
            
            Flight ML611 = new Flight("ML611", 1);
            ML611.setFlightRoute(sinToSyd);
            ML611.setAircraftConfig(ac1);
            
            Flight ML612 = new Flight("ML612", 1);
            ML612.setFlightRoute(sydToSin);
            ML612.setAircraftConfig(ac1);
            
            AircraftConfiguration ac3 = aircraftConfigurationSessionBean.retrieveAircraftConfigurationByNameAndStyle("Boeing 737", "All Economy");
            
            Flight ML621 = new Flight("ML621", 1);
            ML621.setFlightRoute(sinToSyd);
            ML621.setAircraftConfig(ac3);
            
            Flight ML622 = new Flight("ML622", 1);
            ML622.setFlightRoute(sydToSin);
            ML622.setAircraftConfig(ac3);
            
            Flight ML711 = new Flight("ML711", 1);
            ML711.setFlightRoute(sydToNrt);
            ML711.setAircraftConfig(ac2);
            
            Flight ML712 = new Flight("ML712", 1);
            ML712.setFlightRoute(nrtToSyd);
            ML712.setAircraftConfig(ac2);
            
            flightSessionBeanLocal.createNewFlight(ML111);
            flightSessionBeanLocal.createNewFlight(ML112);
            flightSessionBeanLocal.createNewFlight(ML211);
            flightSessionBeanLocal.createNewFlight(ML212);
            flightSessionBeanLocal.createNewFlight(ML311);
            flightSessionBeanLocal.createNewFlight(ML312);
            flightSessionBeanLocal.createNewFlight(ML411);
            flightSessionBeanLocal.createNewFlight(ML412);
            flightSessionBeanLocal.createNewFlight(ML511);
            flightSessionBeanLocal.createNewFlight(ML512);
            flightSessionBeanLocal.createNewFlight(ML611);
            flightSessionBeanLocal.createNewFlight(ML612);
            flightSessionBeanLocal.createNewFlight(ML621);
            flightSessionBeanLocal.createNewFlight(ML622);
            flightSessionBeanLocal.createNewFlight(ML711);
            flightSessionBeanLocal.createNewFlight(ML712);
            
            
        }
    }
    }
}

