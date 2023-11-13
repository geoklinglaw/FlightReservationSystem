/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package frsreservationclient;

import ejb.session.stateless.FRSReservationSessionBeanRemote;
import ejb.session.stateless.PersonSessionBeanRemote;
import entity.Person;
import java.util.Scanner;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import util.enumeration.PersonRoleType;

/**
 *
 * @author apple
 */
public class Main {

    @EJB (name = "FRSReservationSessionBeanRemote")
    private static FRSReservationSessionBeanRemote  FRSReservationSessionBean;
    
    @EJB
    private static PersonSessionBeanRemote personSessionBean;
    
    public static void main(String[] args) {
        runApp();
    }
    
    private static void runApp() {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
            System.out.println("*** Welcome to Merlion Airlines Reservation System ***\n");
            System.out.println("Select an action:");
            System.out.println("1. Login");
            System.out.println("2. Register as Customer");
            System.out.println("3: Search for flights");
            System.out.println("4. Exit");
            
            
            
            while(response < 1 || response > 3) {
                System.out.print("> ");
                
                response = sc.nextInt();
                
                if (response == 1) {
                    doLogin();
                    
                } else if (response == 2) {
                    doRegistration();
                    
                } else if (response == 3) {
                    doSearchFlights();
                    
                } else if (response == 4) {
                    System.out.println("You have exited. Goodbye.");
                    break;
                    
                } else {
                    System.out.println("Invalid input. Try again.\n");
                    runApp();
                }
                 
            }
            
        }
    }   
    
    private static void doRegistration() {
        Scanner sc = new Scanner(System.in);
        
        try {
            System.out.println("==== Registration Interface ====");
            System.out.println("Enter your details. To cancel registration at anytime, press 'q'.");
            
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
         
            Long custId = personSessionBean.createNewPerson(newCust);
            System.out.println("You have been successfully registered as a Merlion Airlines customer!\n");
            
            doMenuFeatures(sc, custId);
            
        } catch (EJBTransactionRolledbackException e) {
            System.out.println("Sorry, you have inputted invalid values. Try again.\n");
            runApp();
            
        } catch(Exception ex) {
            System.out.println("An error has occurred.\n");
            runApp();
        }
    }
    
    public static void cancelRegistration(Scanner sc) {
        System.out.println("\nYou have cancelled registration.\n");
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
                Person currPerson = personSessionBean.login(username, password);
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
        Person person = personSessionBean.retrievePersonById(customerId);
        while (true) {
            System.out.println("==== Menu Interface ====");
            System.out.println("You are logged in as " + person.getFirstName() + " " + person.getLastName()+ " \n");
            System.out.println("1. Search for Flights");
            System.out.println("2. Reserve Flights");
            System.out.println("3. View My Flight Reservation Details");
            System.out.println("4. View All My Flight Reservations");
            System.out.println("5. Logout");
            System.out.print("> ");
            response = sc.nextInt();
            
            switch(response) {
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
        
        System.out.println("Enter number of passengers> ");
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

    
