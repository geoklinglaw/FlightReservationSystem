/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package frsreservationclient;

import ejb.session.stateless.FRSManagementSessionBeanRemote;
import ejb.session.stateless.FlightReservationSystemSessionBeanRemote;
import ejb.session.stateless.PersonSessionBeanRemote;
import entity.Airport;
import entity.CabinClass;
import entity.FlightSchedule;
import entity.Person;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import util.enumeration.CabinClassType;
import util.enumeration.PersonRoleType;

/**
 *
 * @author apple
 */
public class Main {

    @EJB(name = "PersonSessionBeanRemote")
    private static PersonSessionBeanRemote personSessionBeanRemote;

    @EJB(name = "FlightReservationSystemSessionBeanRemote")
    private static FlightReservationSystemSessionBeanRemote flightReservationSystemSessionBeanRemote;

    @EJB(name = "FRSManagementSessionBeanRemote")
    private static FRSManagementSessionBeanRemote fRSManagementSessionBeanRemote;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        runApp();
    }

    private static void runApp() {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to Merlion Airlines Reservation System ***\n");
            System.out.println("Select an action:");
            System.out.println("1. Login");
            System.out.println("2. Register as Customer");
            System.out.println("3. Search for flights");
            System.out.println("4. Exit");
            System.out.println(">");


            response = sc.nextInt();
            switch (response) {
                case 1:
                    doLogin();
                    break;
                case 2:
                    doRegistration();
                    break;
                case 3:
                    // Search for flights logic
                    break;
                case 4:
                    System.out.println("You have exited. Goodbye.");
                    return; // Exit the application
                default:
                    System.out.println("Invalid input. Try again.\n");
            }
        }
    }

    private static void doMenuFeatures(Scanner sc) {
        Integer response = 0;
//        Person person = personSessionBean.retrievePersonById(customerId);
        while (true) {
            System.out.println("\n==== Menu Interface ====");
//            System.out.println("You are logged in as " + person.getFirstName() + " " + person.getLastName()+ " \n");
            System.out.println("> 1. Search for Flights");
            System.out.println("> 2. Reserve Flights");
            System.out.println("> 3. View My Flight Reservation Details");
            System.out.println("> 4. View All My Flight Reservations");
            System.out.println("> 5. Logout");
            System.out.print("> ");
            sc.nextLine();
            response = sc.nextInt();
            switch (response) {
                case 1:
                    doSearchFlights(sc);
                    break;
                case 2:
                    // Registration logic
                    break;
                case 3:
                    // Search for flights logic
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return; // Exit the application
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
        
    private static void doSearchFlights(Scanner sc) {
        sc.nextLine();
        
        System.out.print("Enter trip type (1: one way, 2: twoway) > ");
        int tripType = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter your departure airport IATA code> ");
        String originCode = sc.nextLine().trim();
        
        System.out.print("Enter your arrival airport IATA code > ");
        String destCode = sc.nextLine().trim();
        
        System.out.print("Enter your departure date date of schedule (yyyy-MM-dd) > ");
        String startDateStr = sc.nextLine().trim();
        Date startDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate = formatter.parse(startDateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
        }
        
        
        if (tripType == 2) {
            System.out.print("Enter your return date date of schedule (yyyy-MM-dd) > ");
            String endDateStr = sc.nextLine().trim();
        }
        
        System.out.print("Enter number of passengers > ");
        int numPass = sc.nextInt();
        
        System.out.print("Enter direct or connecting flights (1: direct, 2: connecting) > ");
        int flightType = sc.nextInt();
        
//        int index = 1;
        for (CabinClassType cabinClass : CabinClassType.values()) {
            System.out.println(cabinClass + " " + cabinClass.getValue());
        }
        
        System.out.print("Select your preference for cabin class > ");
        sc.nextLine();
        String ccType = sc.nextLine().trim();
        CabinClassType cabinType = CabinClassType.fromValue(ccType);

        if (startDate != null) {
            List<List<FlightSchedule>> listofFSList = flightReservationSystemSessionBeanRemote.searchFlightsOneWay(startDate, cabinType, originCode, destCode);

            for (int index = 0; index < listofFSList.size(); index++) {
                List<FlightSchedule> fsList = listofFSList.get(index);
                System.out.printf("\n\n                               == %d DAY(S) BEFORE REQUESTED DATE ==\n", index);
                if (fsList.isEmpty()) {
                    System.out.println("No flights found.");
                } else {
                    System.out.printf("%-12s %-20s %-8s %-25s %-25s %-15s\n", "Flight", "Cabin Class", "Fare", "Departure", "Arrival", "Duration");


                    for (FlightSchedule fs : fsList) {
                        System.out.println(fs.getCabinClass().size());

                        for (int i = 0; i < fs.getCabinClass().size(); i++) {
                            String flightNumber = fs.getFlightSchedulePlan().getFlight().getFlightNumber();
                            BigDecimal fareAmount = fs.getFlightSchedulePlan().getFare().get(i).getFareAmount();
                            String departureTime = new SimpleDateFormat("EEE, MMM dd, yyyy, hh:mm a").format(fs.getDepartureTime());
                            String arrivalTime = new SimpleDateFormat("EEE, MMM dd, yyyy, hh:mm a").format(fs.getArrivalTime());
                            double durationInHours = fs.getFlightDuration();
                            int hours = (int) durationInHours;
                            int minutes = (int) ((durationInHours - hours) * 60);

                            System.out.printf("%-5d %-10s %-15s $%-9.2f %-20s %-20s %d hrs %d mins\n", i, flightNumber, fs.getCabinClass().get(i).getType(), fareAmount, departureTime, arrivalTime, hours, minutes);

                        }
                    }
                }
            }
            
            System.out.print("Enter the number of days before requested date (0-3)> ");
            int days = sc.nextInt();
            System.out.print("Enter the section index > ");
            int idx = sc.nextInt();

            FlightSchedule selectedFS = listofFSList.get(days).get(idx);
            String fsText = "*** Selected Flight Information *** \n";
            List<Airport> airportList = selectedFS.getFlightSchedulePlan().getFlight().getFlightRoute().getAirportList();
            fsText += "Flight " + selectedFS.getFlightSchedulePlan().getFlight().getFlightNumber() + " " + selectedFS.getCabinClass().get(idx) + "\n ";
            fsText += "Departing from " + airportList.get(0) + " on " + selectedFS.getDepartureTime() + "\n ";
            fsText += "Arriving at " + airportList.get(1) + " on " + selectedFS.getArrivalTime() + "\n ";
            fsText += "Enter 'Y' to proceed to select your seats > ";
            
            System.out.print(fsText);


        }


        

    }

    private static void doRegistration() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("==== Registration Interface ====");
            System.out.println("Enter your details. To cancel registration at any time, press 'q'.");

            System.out.print("First Name: ");
            String firstName = sc.nextLine();
            if (firstName.equals("q")) {
                cancelRegistration(sc);
                return;
            }

            System.out.print("Last Name: ");
            String lastName = sc.nextLine();
            if (lastName.equals("q")) {
                cancelRegistration(sc);
                return;
            }

            System.out.print("Email: ");
            String email = sc.nextLine();
            if (email.equals("q")) {
                cancelRegistration(sc);
                return;
            }

            System.out.print("Contact Number: ");
            String contactNum = sc.nextLine();
            if (contactNum.equals("q")) {
                cancelRegistration(sc);
                return;
            }

            System.out.print("Address: ");
            String address = sc.nextLine();
            if (address.equals("q")) {
                cancelRegistration(sc);
                return;
            }

            System.out.print("Create Username: ");
            String username = sc.nextLine();
            if (username.equals("q")) {
                cancelRegistration(sc);
                return;
            }

            System.out.print("Create Password: ");
            String password = sc.nextLine();
            if (password.equals("q")) {
                cancelRegistration(sc);
                return;
            }

            Person newCust = new Person();

            newCust.setFirstName(firstName);
            newCust.setLastName(lastName);
            newCust.setEmail(email);
            newCust.setContactNum(contactNum);
            newCust.setAddress(address);
            newCust.setUsername(username);
            newCust.setPassword(password);
            newCust.setRole(PersonRoleType.CUSTOMER);

            Long custId = personSessionBeanRemote.createNewPerson(newCust);
            System.out.println("You have been successfully registered as a Merlion Airlines customer!\n");

            doMenuFeatures(sc, custId);

        } catch (EJBTransactionRolledbackException e) {
            System.out.println("Sorry, you have inputted invalid values. Try again.\n");
            runApp();

        } catch (Exception ex) {
            System.out.println("An error has occurred.\n");
            runApp();
        }
    }

    public static void cancelRegistration(Scanner sc) {
        System.out.println("\nYou have canceled registration.\n");
        runApp();
    }

    private static void doLogin() {
        Scanner sc = new Scanner(System.in);
        System.out.println("==== Login Interface ====");
        System.out.println("Enter login details:");
        System.out.print("Enter Username: ");
        String username = sc.nextLine().trim();
        System.out.print("Enter Password: ");
        String password = sc.nextLine().trim();

        try {
            if (username.length() > 0 && password.length() > 0) {
                Person currPerson = personSessionBeanRemote.login(username, password);
                System.out.println("Welcome " + currPerson.getFirstName() + ", you're logged in!\n");

                doMenuFeatures(sc, currPerson.getId());

            } else {
                System.out.println("No matching account found or wrong login details. Please try again.\n");
                doLogin();
            }

        } catch (Exception ex) {
            System.out.println("Oh no... An error has occurred.\n");
            runApp();
        }
    }

    private static void doMenuFeatures(Scanner sc, Long customerId) {
        Integer response = 0;
        Person person = personSessionBeanRemote.retrievePersonById(customerId);
        while (true) {
            System.out.println("==== Menu Interface ====");
            System.out.println("You are logged in as " + person.getFirstName() + " " + person.getLastName() + " \n");
            System.out.println("1. Search for Flights");
            System.out.println("2. Reserve Flights");
            System.out.println("3. View My Flight Reservation Details");
            System.out.println("4. View All My Flight Reservations");
            System.out.println("5. Logout");
            System.out.print("> ");
            response = sc.nextInt();

            switch (response) {
                case 1:
                    System.out.println("You have selected 'Search for Flights'\n");
                    doSearchFlights();
                    break;
                case 2:
                    System.out.println("You have selected 'Reserve Flights'\n");
                    doReserveFlight(sc, customerId);
                    break;

                case 3:
                    System.out.println("You have selected 'View My Flight Reservation Details'\n");
                    doViewFlightReservationDetails(sc, customerId);
                    break;

                case 4:
                    System.out.println("You have selected 'View All My Flight Reservations'\n");
                    doViewAllFlightReservations(sc, customerId);
                    break;

                case 5:
                    System.out.println("You have logged out.\n");
                    runApp();
                    break;

                default:
                    System.out.println("Invalid input. Please try again.\n");
                    doMenuFeatures(sc, customerId);
                    break;
            }
        }

    }

    private static void doSearchFlights() {

    }

    public static void doReserveFlight(Scanner sc, Long custId) {
        System.out.println("Enter the flight ID that you want to reserve> ");
        Long flightId = sc.nextLong();

        System.out.println("Enter the number of passengers> ");
        int numPassenger = sc.nextInt();
        sc.nextLine();

        System.out.println("Select trip type: ");
        System.out.println("1: One-way");
        System.out.println("2: Round-trip");
    }

    private static void doViewFlightReservationDetails(Scanner sc, Long custId) {

    }

    private static void doViewAllFlightReservations(Scanner sc, Long custId) {

    }

  }

        
    
        
    
    
    
        

    