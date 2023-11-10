/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frsmanagementclient;

import ejb.session.stateless.FRSManagementSessionBeanRemote;
import entity.AircraftConfiguration;
import entity.Airport;
import entity.CabinClass;
import entity.Flight;
import entity.FlightRoute;
import java.util.List;
import java.util.Scanner;
import javafx.util.Pair;

/**
 *
 * @author apple
 */
public class ScheduleManagerTask {
    
    private FRSManagementSessionBeanRemote FRSManagementSessionBeanRemote;

    public ScheduleManagerTask(FRSManagementSessionBeanRemote FRSManagementSessionBeanRemote) {
        this.FRSManagementSessionBeanRemote = FRSManagementSessionBeanRemote;
    }
    
    public void getTasks() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
            System.out.println("\n\n*** Route Planner ***\n");
            
            System.out.print("Enter your task: \n");
            System.out.println("1: Create Flight");
            System.out.println("2: View all Flights");
            System.out.println("3: View Flight Details");
            System.out.println("4: Update flight");
            System.out.println("5: Delete flight");
            System.out.println("6: Create Flight Schedule Plan");
            System.out.println("7: View all Flight Plans");
            System.out.println("8: View all Flight Plan Details");
            System.out.println("9: Update Flight Schedule Plan");
            System.out.println("10: Delete Flight Schedule Plan");

            response = 0;
            
            while(response < 1 || response > 10) {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1){
                    createFlight(scanner);
                }
                else if (response == 2) {
                    viewAllFlight();
                }
                else if (response == 3) {
                    viewFlightDetails(scanner);
                }
                else if (response == 4) { 
                    updateFlight(scanner);
                } 
                else if (response == 5) {
                    deleteFlight(scanner);
                } 
                else {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
        }
    }
    
    private void createFlight(Scanner sc) {
        System.out.println("\n\n*** Creating Flight *** \n");
        System.out.println("List of Flight Routes:");
        
        Pair<List<FlightRoute>, List<AircraftConfiguration>> pair = FRSManagementSessionBeanRemote.enquireFlightRequirements();
        List<FlightRoute> routeList = pair.getKey();
        List<AircraftConfiguration> acConfiglist = pair.getValue();
        
        String routeText = "";
        int index = 1;
        for (FlightRoute route: routeList) {
            routeText += index + ": " + route.getAirportList().get(0).getCountry() + " --> " + route.getAirportList().get(1).getCountry() +"\n";
            index += 1;
        }
        System.out.print(routeText);

        sc.nextLine();
        System.out.print("\nEnter Flight Number: \n> ");
        String flightNum = sc.nextLine().trim();
        System.out.print("\nEnter Flight Route: \n> ");
        Long routeId = new Long(sc.nextInt());

        
        String acConfigText = "";
        for (AircraftConfiguration config: acConfiglist) {
            acConfigText += "\n(" + config.getId() + ") " + config.getName() + ": ";
            for (CabinClass cc: config.getCabinClassList()) {
                acConfigText += cc.getType() + ", ";
            }
        }
        System.out.print("Enter Aircraft Configuration ID: \n> ");
        Long config = new Long(sc.nextInt());
        
        FRSManagementSessionBeanRemote.createFlight(flightNum, routeId, config);
        System.out.print("Successfully created Flight " + flightNum);
    }
    
    private List<Flight> viewAllFlight() {
        System.out.println("\n\n*** Creating Flights *** \n");
        List<Flight> flights = FRSManagementSessionBeanRemote.viewAllFlight();

        String flightText = "List of Flights:\n";
        int index = 1;
        for (Flight flight: flights) {
            flightText += index + ": " + flight.getFlightNumber() + " (" + flight.getAircraftConfig().getName() + ")\n";
            index += 1;
        }
        
        System.out.print(flightText);
        return flights;
    }
    
    private void viewFlightDetails(Scanner sc) {
        List<Flight> flightList = viewAllFlight();
        System.out.print("Select which flight details you would like to know:\n> ");
        int response = sc.nextInt();
        Flight flight = flightList.get(response - 1);
        
        String flightDetails = "-- Flight Details -- \n";
        flightDetails += "Flight Number: " + flight.getFlightNumber() + "\n";
        flightDetails += "Aircraft Configuration: " + flight.getAircraftConfig().getName() + "\n";
        FlightRoute flightRoute = flight.getFlightRoute();
        flightDetails += "Flight Route: " + flightRoute.getAirportList().get(0).getCountry() + "-->" + flightRoute.getAirportList().get(1).getCountry();
        System.out.println(flightDetails);
    }
    
    
    private void updateFlight(Scanner sc) {
        System.out.println("\n\n*** Updating Flight *** \n");
        System.out.print("Select the fields that you want to update (enter '0' if there are no changes)! ");
        List<Flight> flights = FRSManagementSessionBeanRemote.viewAllFlight();
        String flightText = "\nList of Flight:\n";
        int idx = 1;
        for (Flight flight: flights) {
            flightText += idx + ": " + flight.getFlightNumber() + " (" + flight.getAircraftConfig().getName() + ")\n";
            idx += 1;
        }
        System.out.print(flightText);
        System.out.print("\nEnter Flight Number to be changed:\n> ");
        sc.nextLine();
        String flightNum = sc.nextLine().trim();

   
        Pair<List<FlightRoute>, List<AircraftConfiguration>> pair = FRSManagementSessionBeanRemote.enquireFlightRequirements();
        List<FlightRoute> routeList = pair.getKey();
        List<AircraftConfiguration> acConfiglist = pair.getValue();

        String routeText = "\nList of Flight Routes: \n";
        int index = 1;
        for (FlightRoute route: routeList) {
            routeText += index + ": " + route.getAirportList().get(0).getCountry() + " --> " + route.getAirportList().get(1).getCountry() +"\n";
            index += 1;
        }
        System.out.print(routeText);
        System.out.print("\nEnter Flight Route ID: \n> ");
        int routeId = sc.nextInt();

        
        String acConfigText = "List of Aircraft Configuration:";
        for (AircraftConfiguration config: acConfiglist) {
            acConfigText += "\n(" + config.getId() + ") " + config.getName() + ": ";
            for (CabinClass cc: config.getCabinClassList()) {
                acConfigText += cc.getType() + ", ";
            }
        }
        System.out.print(acConfigText);
        System.out.print("\nEnter Aircraft Configuration ID: \n> ");
        int configId = sc.nextInt();
        
        Flight updatedFlight = FRSManagementSessionBeanRemote.updateFlight(flightNum, routeId, configId);
        
        String flightDetails = "-- Updated Flight Details -- \n";
        flightDetails += "Flight Number: " + updatedFlight.getFlightNumber() + "\n";
        flightDetails += "Aircraft Configuration: " + updatedFlight.getAircraftConfig().getName() + "\n";
        FlightRoute flightRoute = updatedFlight.getFlightRoute();
        flightDetails += "Flight Route: " + flightRoute.getAirportList().get(0).getCountry() + "-->" + flightRoute.getAirportList().get(1).getCountry();
        System.out.println(flightDetails);
    }
    
    private void deleteFlight(Scanner sc) {
        System.out.println("\n\n*** Deleting Flight *** \n");
        System.out.print("Enter Flight Number: \n> ");
        sc.nextLine();
        String flightNumber = sc.nextLine().trim();

        FRSManagementSessionBeanRemote.deleteFlight(flightNumber);
        System.out.println("Successfully deleted flight " + flightNumber + "!");
    }
}
