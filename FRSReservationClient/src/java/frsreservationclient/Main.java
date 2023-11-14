/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package frsreservationclient;

import ejb.session.stateless.FRSManagementSessionBeanRemote;
import ejb.session.stateless.FlightReservationSystemSessionBeanRemote;
import entity.CabinClass;
import entity.FlightSchedule;
import entity.Person;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.ejb.EJB;
import util.enumeration.CabinClassType;

/**
 *
 * @author apple
 */
public class Main {

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
                    doMenuFeatures(sc);
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

    private static void doMenuFeatures(Scanner sc) {
        System.out.println("\n==== Menu ====");
        Integer response = 0;
//        Person person = personSessionBean.retrievePersonById(customerId);
        while (true) {
            System.out.println("==== Menu Interface ====");
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
        int ccType = sc.nextInt();
        CabinClassType cabinType = CabinClassType.fromValue(ccType - 1);
        
        if (startDate != null) {
            List<List <FlightSchedule>> listofFSList = flightReservationSystemSessionBeanRemote.searchFlightsOneWay(startDate, cabinType, originCode, destCode);
            
            String fsText = "";
            int index = 0;
            for (List <FlightSchedule> fsList: listofFSList) { 
                System.out.print("== " + index + " NUMBER OF DAYS BEFORE REQUESTED DATE ==");
                if (fsList.size() == 0) {
                    System.out.print("no flights found.");
                } else {
                    for (FlightSchedule fs: fsList) {
//                        for (CabinClass cc: fs.getCabinClass()) {
//                            if (cc.getType().equals(cabinType)) {
                                System.out.println(fs.getFlightSchedulePlan().getFlight().getFlightNumber() + ": " + fs.getDepartureTime() + " --> " + fs.getArrivalTime());
                                for (CabinClass cc: fs.getCabinClass()) {
                                   System.out.print(cc.getType());
                                }

//                            }
//                        }
                    }
                }
                index += 1;
            }

        }
        
        
    }
        
    
        
    
    
    
        

    


//       private static void doRegistration(Scanner sc) {
//           System.out.println("==== Registration Interface ====");
//           System.out.println("Enter your details. To cancel registration at anytime, press 'q'.");
//
//           System.out.print("> First Name: ");
//           System.out.print("First Name: ");
//           String firstName = sc.nextLine();
//           if (firstName.equals("q")) {
//               cancelRegistration(sc);
//               return;          
//           }
//
//           System.out.print("> Last Name: ");
//           System.out.print("Last Name: ");
//           String lastName = sc.nextLine();
//           if (lastName.equals("q")) {
//               cancelRegistration(sc);
//               return;
//           }
//
//           System.out.print("> Email: ");
//           System.out.print("Email: ");
//           String email = sc.nextLine();
//           if (email.equals("q")) {
//               cancelRegistration(sc);
//               return;
//           }
//
//           System.out.print("> Contact Number: ");
//           System.out.print("Contact Number: ");
//           String contactNum = sc.nextLine();
//           if (contactNum.equals("q")) {
//               cancelRegistration(sc);
//               return;
//           }
//
//           System.out.print("> Address: ");
//           System.out.print("Address: ");
//           String address = sc.nextLine();
//           if (address.equals("q")) {
//               cancelRegistration(sc);
//               return;
//           }
//
//           System.out.print("> Create Username: ");
//           System.out.print("Create Username: ");
//           String username = sc.nextLine();
//           if (username.equals("q")) {
//               cancelRegistration(sc);
//               return;
//           }
//
//           System.out.print("> Create Password: ");
//           System.out.print("Create Password: ");
//           String password = sc.nextLine();
//           if (password.equals("q")) {
//               cancelRegistration(sc);
//           }
//        }
//       }
//               
//        private static void doLogin() {
//           Scanner sc = new Scanner(System.in);
//           System.out.println("==== Login Interface ====");
//           System.out.println("Enter login details:");
//           System.out.print("> Enter Username: ");
//           System.out.print("Enter Username: ");
//           String username = sc.nextLine().trim();
//           System.out.print("> Enter Password: ");
//           System.out.print("Enter Password: ");
//           String password = sc.nextLine().trim();
//           
//       }
//       
//        public static void cancelRegistration(Scanner sc) {
//            System.out.println("\nYou have cancelled registration.\n");
//            runApp();
//        }
//    


}
