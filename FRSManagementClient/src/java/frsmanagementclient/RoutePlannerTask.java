/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frsmanagementclient;

import ejb.session.stateless.FRSManagementSessionBeanRemote;
import entity.Airport;
import entity.FlightRoute;
import java.util.List;
import java.util.Scanner;
import util.exception.AirportNotAvailableException;
import util.exception.FlightExistsForFlightRouteException;
import util.exception.FlightRouteExistsException;
import util.exception.FlightRouteNotFoundException;

/**
 *
 * @author apple
 */
public class RoutePlannerTask {
    private FRSManagementSessionBeanRemote FRSManagementSessionBeanRemote;

    public RoutePlannerTask(FRSManagementSessionBeanRemote FRSManagementSessionBeanRemote) {
        this.FRSManagementSessionBeanRemote = FRSManagementSessionBeanRemote;
    }
    
    private void goBack() {
        Main.runApp();
    }
    
    public void getTasks() {
        Scanner scanner = new Scanner(System.in);
        Integer response = -1;
        
        while(true) {
            System.out.println("\n\n*** Route Planner ***\n");
            System.out.print("Enter your task: \n");
            System.out.println("1: Create Flight Route");
            System.out.println("2: View all Flight Route");
            System.out.println("3: Delete Flight Route");
            System.out.println("To log out, please press '0'.");

            response = -1;
            
            while(response < 1 || response > 3) {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1){
                    createFlightRoute(scanner);
                }
                else if (response == 2) {
                    viewAllFlightRoutes();
                }
                else if (response == 3) {
                    deleteFlightRoute(scanner);
                }
                else if (response == 0) {
                    goBack();
                }
                else {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
        }
    }
    
    public void createFlightRoute(Scanner sc) {
        sc.nextLine();
        System.out.println("\n\n*** Creating Flight Route *** \n");
        int num = 1;
        System.out.print("Enter 'Y' if you would like to create a complementary flight route > ");
        if (sc.nextLine().equals("Y")) {
            num = 2;
        }
        
        System.out.println("\n\nList of Airports:");
        
        List<Airport> airportList =  FRSManagementSessionBeanRemote.viewAllAirports();
        String airportText = "";
        int index = 1;
        for (Airport airport: airportList) {
            airportText += index + ": " + airport.getCountry() + " -- " + airport.getAirportName() + " (" + airport.getAirportCode() + ")\n";
            index += 1;
        }

        
        System.out.print(airportText);
        System.out.print("\nEnter IATA code of ORIGIN location: \n> ");
        String origin = sc.nextLine().trim();
        System.out.print("Enter IATA code of DESTINATION location: \n> ");
        String destination = sc.nextLine().trim();
        
        try {
            Long originID = null;
            Long destID = null;
            for (Airport airport: airportList) {
                if (origin.equals(airport.getAirportCode())) {
                    originID = airport.getId();
                    System.out.println("Your Chosen Origin is " + airport.getCountry());

                }
                if (destination.equals(airport.getAirportCode())) {
                    destID = airport.getId();
                    System.out.println("Your Chosen Destination is " + airport.getCountry());

                }
            }

            FRSManagementSessionBeanRemote.createFlightRoute(originID, destID);

            if (num == 2) {
                FRSManagementSessionBeanRemote.createFlightRoute(destID, originID); 
            }
            
            System.out.print("\n Flight route successfully created!");
            
        } catch (FlightRouteExistsException ex) {
            System.out.println(ex.getMessage());
        } catch (AirportNotAvailableException ex1) {
            System.out.println(ex1.getMessage());
        }
        
        
        
    }
    
    private void viewAllFlightRoutes() {
        System.out.println("\n\n*** Creating Flight Route *** \n");
        List<FlightRoute> flightRoutes = FRSManagementSessionBeanRemote.viewAllFlightRoutes();
        
        String routeText = "List of Flight Routes:\n";
        
        for (FlightRoute flightRoute: flightRoutes) {
            routeText += flightRoute.getOrigin().getCountry()+ " (" + flightRoute.getOrigin().getAirportCode() + ") --> " + flightRoute.getDestination().getCountry() + " (" + flightRoute.getDestination().getAirportCode() +")\n";
            
        }
        System.out.print(routeText);
        
    }
    
    private void deleteFlightRoute(Scanner sc) {
        System.out.println("\n\n*** Deleting Flight Route *** \n");
        System.out.print("Enter IATA code of ORIGIN location: \n> ");
        sc.nextLine();
        String origin = sc.nextLine().trim();
        System.out.print("Enter IATA code of DESTINATION location: \n> ");
        String destination = sc.nextLine().trim();

        try {
            List<Airport> airportList = FRSManagementSessionBeanRemote.deleteFlightRoute(origin, destination);
            System.out.println("Successfully deleted flight route: " + airportList.get(0).getCountry() + " --> " + airportList.get(1).getCountry());
        } catch (AirportNotAvailableException ex1) {
            System.out.println(ex1.getMessage());
        } catch (FlightRouteNotFoundException ex2) {
            System.out.println(ex2.getMessage());
        } catch (FlightExistsForFlightRouteException ex2) {
            System.out.println(ex2.getMessage() + " Hence it cannot be deleted");
        }

    }
}
