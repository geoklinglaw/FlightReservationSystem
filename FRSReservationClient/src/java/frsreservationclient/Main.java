/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package frsreservationclient;

import ejb.session.stateless.FRSManagementSessionBeanRemote;
import ejb.session.stateless.FlightReservationSystemSessionBeanRemote;
import ejb.session.stateless.FlightSessionBeanRemote;
import ejb.session.stateless.PersonSessionBeanRemote;
import entity.Airport;
import entity.CabinClass;
import entity.Fare;
import entity.FlightCabinClass;
import entity.FlightRoute;
import entity.FlightSchedule;
import entity.Person;
import entity.Seat;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

    public static String ASCII_ART1 = "  __  __ _____ ____  _     ___ _   _ _____      _    ___ ____  _     ___ _   _ _____ ____  \n" +
" |  \\/  | ____|  _ \\| |   |_ _| \\ | | ____|    / \\  |_ _|  _ \\| |   |_ _| \\ | | ____/ ___| \n" +
" | |\\/| |  _| | |_) | |    | ||  \\| |  _|     / _ \\  | || |_) | |    | ||  \\| |  _| \\___ \\ \n" +
" | |  | | |___|  _ <| |___ | || |\\  | |___   / ___ \\ | ||  _ <| |___ | || |\\  | |___ ___) |\n" +
" |_|  |_|_____|_| \\_\\_____|___|_| \\_|_____| /_/   \\_\\___|_| \\_\\_____|___|_| \\_|_____|____/ \n" +
"                                                                                           ";
    
    public static String ASCII_ART2 = "                                     _   _                                 _                 \n" +
"  _ __ ___  ___  ___ _ ____   ____ _| |_(_) ___  _ __        ___ _   _ ___| |_ ___ _ __ ___  \n" +
" | '__/ _ \\/ __|/ _ \\ '__\\ \\ / / _` | __| |/ _ \\| '_ \\      / __| | | / __| __/ _ \\ '_ ` _ \\ \n" +
" | | |  __/\\__ \\  __/ |   \\ V / (_| | |_| | (_) | | | |     \\__ \\ |_| \\__ \\ ||  __/ | | | | |\n" +
" |_|  \\___||___/\\___|_|    \\_/ \\__,_|\\__|_|\\___/|_| |_|     |___/\\__, |___/\\__\\___|_| |_| |_|\n" +
"                                                                 |___/                       ";
    
    public static String PLANE_ART = " __\n" +
" \\  \\     _ _\n" +
"  \\**\\ ___\\/ \\\n" +
"X*#####*+^^\\_\\\n" +
"  o/\\  \\\n" +
"     \\__\\";
    
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    
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
            System.out.println(PLANE_ART);
            System.out.println(ASCII_ART1);
            System.out.println(ASCII_ART2);
            System.out.println("Select an action:");
            System.out.println("1. Login");
            System.out.println("2. Register as Customer");
            System.out.println("3. Search for flights");
            System.out.println("4. Exit");
            System.out.println(">");


            response = sc.nextInt();
            switch (response) {
                case 1:
//                    doLogin();
                      doMenuFeatures(sc);
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
            System.out.println("\n\n==== Menu Interface ====");
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
    
    
    private static void doMenuFeatures(Scanner sc) {
        Integer response = 0;
//        Person person = personSessionBean.retrievePersonById(customerId);
        while (true) {
            System.out.println("\n==== Menu Interface ====");
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
        Date endDate = null;
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate = formatter.parse(startDateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
        }
        
        
        if (tripType == 2) {
            System.out.print("Enter your return date date of schedule (yyyy-MM-dd) > ");
            String endDateStr = sc.nextLine().trim();
            try {
                endDate = formatter.parse(endDateStr);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
            }
        }
        
        System.out.print("Enter number of passengers > ");
        int numPass = sc.nextInt();
        
        System.out.print("Enter direct or connecting flights (1: direct, 2: connecting) > ");
        int flightType = sc.nextInt();
        

        for (CabinClassType cabinClass : CabinClassType.values()) {
            System.out.println(cabinClass + " " + cabinClass.getValue());
        }
        
        System.out.print("Select your preference for cabin class > ");
        sc.nextLine();
        String ccType = sc.nextLine().trim();
        CabinClassType cabinType = CabinClassType.fromValue(ccType);
        HashMap<Long, Integer> mapOne = new HashMap<Long, Integer>();
        HashMap<Long, Integer> mapTwo = new HashMap<Long, Integer>();


        if (startDate != null) {
            if (tripType == 1 && flightType == 1) {

                List<List<FlightSchedule>> listofFSList = flightReservationSystemSessionBeanRemote.searchFlightsOneWay(startDate, cabinType, originCode, destCode);
                handleOneWayFlight(listofFSList, tripType, originCode, destCode, startDate, numPass, cabinType, mapOne);
                printSelectedFlightSchedule(sc, mapOne, numPass);
            } else if (tripType == 2 && flightType == 1) {
                List<List<FlightSchedule>> listofFSList = flightReservationSystemSessionBeanRemote.searchFlightsOneWay(startDate, cabinType, originCode, destCode);
                handleOneWayFlight(listofFSList, tripType, originCode, destCode, startDate, numPass, cabinType, mapOne);
                printSelectedFlightSchedule(sc, mapOne, numPass);
                handleOneWayFlight(listofFSList, tripType, destCode, originCode, endDate, numPass, cabinType, mapTwo);
                printSelectedFlightSchedule(sc, mapTwo, numPass);
            }
        } 

    }
    
    private static void printSelectedFlightSchedule(Scanner sc, HashMap<Long, Integer> map, int numPass) {
            System.out.print("\nEnter the flight schedule ID > ");
            int id = sc.nextInt();
            Long fsId = new Long(id);
            Integer index = map.get(fsId);
            
            FlightSchedule selectedFS = flightReservationSystemSessionBeanRemote.findFS(fsId);
            String fsText = "\n*** Selected Flight Information *** \n";
            FlightRoute fr = selectedFS.getFlightSchedulePlan().getFlight().getFlightRoute();
            CabinClass cc = selectedFS.getFlightSchedulePlan().getFlight().getAircraftConfig().getCabinClassList().get(index);
            Fare fare = selectedFS.getFlightSchedulePlan().getFare().get(index);
            double total = fare.getFareAmount().doubleValue() * numPass;
            fsText += "Flight " + selectedFS.getFlightSchedulePlan().getFlight().getFlightNumber() + " " + cc.getType() + " $" + fare.getFareAmount() + "x" + numPass + " = " +  total + "\n";
            fsText += "Departing from " + fr.getOrigin().getCountry() + "("  + fr.getOrigin().getAirportCode() + ") on " + selectedFS.getDepartureTime() + "\n";
            fsText += "Arriving at " + fr.getDestination().getCountry() + "(" + fr.getDestination().getAirportCode() + ") on " + selectedFS.getArrivalTime() + "\n";
            fsText += "**************************************";
            
            fsText += "\n\nEnter 'Y' to proceed to select your seats > ";
            System.out.print(fsText);
            
            sc.nextLine();
            String ans = sc.nextLine().trim();
            
            if (ans.equals("Y")) {
                FlightCabinClass flightCabinClass = viewSeats(fsId, cc.getFlightCabinClass().getId());
                System.out.print("Select " + numPass + " seat(s) by entering the seat number ('11A'): \n");
                List<String> seatNumList = new ArrayList<>();
                for (int i = 0; i < numPass; i++) {
                    System.out.print("Seat " + (i+1) + " > "); 
                    String seatNum = sc.nextLine().trim();
                    seatNumList.add(seatNum);
                }
                
                flightReservationSystemSessionBeanRemote.bookSeats(seatNumList, flightCabinClass.getId());
                
                String seatsSelectedText = "You have successfully booked seats ";
                for (String seatNum: seatNumList) {
                    seatsSelectedText += seatNum + " ";
                }
                seatsSelectedText += "!";
                System.out.print(seatsSelectedText);
               
            }
    }
    
    
    private static void handleOneWayFlight(List<List<FlightSchedule>> listofFSList, int tripType, String originCode, String destCode, Date startDate, int numPass, CabinClassType cabinType, HashMap<Long, Integer> map) {
        for (int index = 0; index < listofFSList.size(); index++) {
            List<FlightSchedule> fsList = listofFSList.get(index);
            System.out.printf(ANSI_BLUE + "\n\n                                   == %d DAY(S) BEFORE REQUESTED DATE ==\n" + ANSI_RESET, index);
            if (fsList.isEmpty()) {
                System.out.println("No flights found.");
            } else {
                System.out.printf("%-4s %-10s %-10s %2s %4s %-40s %-27s %-15s\n", 
                    "No", "Flight", "Cabin Class", "Single Fare", "Total Fare", "       Departure", "Arrival", "Duration");


                for (FlightSchedule fs : fsList) {
                    List<CabinClass> ccList = fs.getFlightSchedulePlan().getFlight().getAircraftConfig().getCabinClassList();
                    for (int i = 0; i < ccList.size(); i++) {
                        // Loop through to print out the preferred cabin class type first
                        if (ccList.get(i).getType().equals(cabinType)) {
                            String flightNumber = fs.getFlightSchedulePlan().getFlight().getFlightNumber();
                            BigDecimal fareAmount = fs.getFlightSchedulePlan().getFare().get(i).getFareAmount();
                            String departureTime = new SimpleDateFormat("EEE, MMM dd, yyyy, hh:mm a").format(fs.getDepartureTime());
                            String arrivalTime = new SimpleDateFormat("EEE, MMM dd, yyyy, hh:mm a").format(fs.getArrivalTime());
                            double durationInHours = fs.getFlightDuration();
                            int hours = (int) durationInHours;
                            int minutes = (int) ((durationInHours - hours) * 60);
                            map.put(fs.getId(), i);
                            CabinClass cc = fs.getFlightSchedulePlan().getFlight().getAircraftConfig().getCabinClassList().get(i);;
                            double totalFare = fareAmount.doubleValue() * numPass;
                            System.out.printf("%-4d %-12s %-10s $%8.2f $%8.2f      %-30s %-30s %d hrs %d mins\n", 
                                fs.getId(), flightNumber, cc.getType(), fareAmount, totalFare, departureTime, arrivalTime, hours, minutes);
                        }
                    }

                    // Loop again to print out the remaining cabin class types
                    for (int i = 0; i < ccList.size(); i++) {
                        // 
                        if (!ccList.get(i).getType().equals(cabinType)) {
                            String flightNumber = fs.getFlightSchedulePlan().getFlight().getFlightNumber();
                            BigDecimal fareAmount = fs.getFlightSchedulePlan().getFare().get(i).getFareAmount();
                            String departureTime = new SimpleDateFormat("EEE, MMM dd, yyyy, hh:mm a").format(fs.getDepartureTime());
                            String arrivalTime = new SimpleDateFormat("EEE, MMM dd, yyyy, hh:mm a").format(fs.getArrivalTime());
                            double durationInHours = fs.getFlightDuration();
                            int hours = (int) durationInHours;
                            int minutes = (int) ((durationInHours - hours) * 60);
                            map.put(fs.getId(), i);
                            CabinClass cc = fs.getFlightSchedulePlan().getFlight().getAircraftConfig().getCabinClassList().get(i);;
                            double totalFare = fareAmount.doubleValue() * numPass;
                            System.out.printf("%-2d %-10s %-15s $%-3.2f $%-3.2f %-20s %-20s %d hrs %d mins\n", fs.getId(), flightNumber, cc.getType(), fareAmount, totalFare, departureTime, arrivalTime, hours, minutes);
                        }
                    }
                }
            }
        }

    }
       
    
    private static FlightCabinClass viewSeats(Long fsId, Long fccId) {
        FlightCabinClass fcc = fRSManagementSessionBeanRemote.viewCabinClass(fsId, fccId);
        
        System.out.println("\n\n\nCabin Class: " + fcc.getCabinClass().getType() + "");
        System.out.println("Number of Available Seats: " + fcc.getNumAvailableSeats());
        System.out.println("Number of Reserved Seats: " + fcc.getNumBalanceSeats());
        System.out.println("Number of Balance Seats: " + fcc.getNumReservedSeats() + "\n");

        List<Seat> seatList = fcc.getSeatList(); 
        printCabinClassSeats(fcc);
            
        return fcc;
    }
    
    private static void printCabinClassSeats(FlightCabinClass flightCabinClass) {
        String seatConfig = flightCabinClass.getCabinClass().getSeatConfig(); // e.g., "3-3", "2-1-1", "2-2-2-2"
        BigDecimal numRows = flightCabinClass.getCabinClass().getNumRows();
        List<Seat> seatList = flightCabinClass.getSeatList();

        // Split the seat configuration and calculate the total seats per row including aisles
        String[] parts = seatConfig.split("-");
        int totalSeatsPerRow = 0;
        for (String part : parts) {
            totalSeatsPerRow += Integer.parseInt(part);
        }

        // Adding aisles to the total seats per row
        totalSeatsPerRow += parts.length - 1;

        // Generate the seat layout
        for (int row = 1; row <= numRows.intValue(); row++) {
            int seatCounter = 0;
            for (int i = 0; i < totalSeatsPerRow; i++) {
                if (isAisle(i, parts)) {
                    System.out.print("   "); // Space for aisle
                } else {
                    String seatLabel = row + getSeatLabel(seatCounter, parts);
                    Seat seat = findSeat(seatList, seatLabel);
                    if (seat != null) {
                        String formattedSeatLabel = formatSeatLabel(seatLabel, seat.getSeatStatus().getValue());
                        System.out.print(formattedSeatLabel + " ");
                    }
                    seatCounter++;
                }
            }
            System.out.println();
        }
    }

    private static boolean isAisle(int position, String[] parts) {
        int count = 0;
        for (String part : parts) {
            count += Integer.parseInt(part);
            if (position == count) {
                return true;
            }
            count++; // Adding aisle
        }
        return false;
    }

    private static String getSeatLabel(int seatNumber, String[] parts) {
        char seatChar = 'A';
        int count = 0;
        for (String part : parts) {
            int seats = Integer.parseInt(part);
            if (seatNumber < count + seats) {
                return "" + (char) (seatChar + seatNumber - count);
            }
            count += seats;
            seatChar += seats;
        }
        return "";
    }

    private static Seat findSeat(List<Seat> seatList, String seatLabel) {
        // Assuming Seat class has a method getLabel() to return the seat label (e.g., "1A", "2B")
        return seatList.stream()
                .filter(seat -> seat.getSeatID().equals(seatLabel))
                .findFirst()
                .orElse(null);
    }
    
    private static String formatSeatLabel(String seatLabel, int status) {
        // Assuming status 0 means available, and any other value means taken
        if (status == 0) {
            return ANSI_GREEN + seatLabel + ANSI_RESET; // Green for available
        } else {
            return ANSI_RED + seatLabel + ANSI_RESET; // Red for taken
        }
    }

  }

        
    
        
    
    
    
        

    