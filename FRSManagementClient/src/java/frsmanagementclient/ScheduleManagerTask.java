/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frsmanagementclient;

import ejb.session.stateless.FRSManagementSessionBeanRemote;
import entity.AircraftConfiguration;
import entity.Airport;
import entity.CabinClass;
import entity.Fare;
import entity.Flight;
import entity.FlightRoute;
import entity.FlightSchedule;
import entity.FlightSchedulePlan;
import entity.SinglePlan;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javafx.util.Pair;
import util.enumeration.FlightScheduleStatus;

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
                else if (response == 6) {
                    createFlightSchedulePlan(scanner);
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
        flightDetails += "Flight Route: " + flightRoute.getAirportList().get(0).getCountry() + " --> " + flightRoute.getAirportList().get(1).getCountry();
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
        flightDetails += "Flight Route: " + flightRoute.getAirportList().get(0).getCountry() + " --> " + flightRoute.getAirportList().get(1).getCountry();
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
    
    private void createFlightSchedulePlan(Scanner sc) {
        System.out.println("\n\n*** Creating Flight Schedule Plan *** \n");
        System.out.println("Select your preferred schedule type:");
        
        String schTypeText = "1: Single schedule\n";
        schTypeText += "2: Multiple schedule\n";
        schTypeText += "3: Recurrent schedule every n day\n";
        schTypeText += "3: Recurrent schedule weekly\n";
        schTypeText += "> ";
        System.out.print(schTypeText);
        
        int type = sc.nextInt();
        sc.nextLine();
        System.out.print("\nEnter Flight Number > ");
        String flightNum = sc.nextLine().trim();
        

        switch (type) {
                case 1:
                    Flight flight = viewFlightDetailsCabinClasses(flightNum);
                    String ccText = "\nInput fare for each cabin class:";
                    System.out.println("reached here");
                    

                    for (CabinClass cc: flight.getAircraftConfig().getCabinClassList()) {
                        ccText += "\n" + cc.getId() + ": " + cc.getType() + "\n";
                        ccText += "Enter fare (0.00)> ";
                        System.out.print(ccText);
                        ccText = "";
                        
                        String fareAmt = sc.nextLine().trim();
                        BigDecimal fare = new BigDecimal(fareAmt);
                        createFareforEachCabinClass(flight, cc, fare);
                    }
                    System.out.println(ccText);

                    createSingleFSP(sc, flight, flight.getAircraftConfig().getCabinClassList());
                    System.out.println("Success!");
                
                case 2:
                    
                case 3:
                    
                case 4:
        }
        
        


        
//        String acConfigText = "";
//        for (AircraftConfiguration config: acConfiglist) {
//            acConfigText += "\n(" + config.getId() + ") " + config.getName() + ": ";
//            for (CabinClass cc: config.getCabinClassList()) {
//                acConfigText += cc.getType() + ", ";
//            }
//        }
//        System.out.print("Enter Aircraft Configuration ID: \n> ");
//        Long config = new Long(sc.nextInt());
//        
//        FRSManagementSessionBeanRemote.createFlight(flightNum, routeId, config);
//        System.out.print("Successfully created Flight " + flightNum);
    }
    
    private void createSingleFSP(Scanner sc, Flight flight, List<CabinClass> ccList) {
        System.out.print("FOR SINGLE SCHEDULE --- ");
        System.out.print("\nEnter date of flight (yyyy-MM-dd) > ");
        String singleDate = sc.nextLine().trim();
        System.out.print("\nEnter time of flight (HH:mm:ss) > ");
        String singleTime = sc.nextLine().trim();
        String dateTimeInput= singleDate + " " + singleTime;
        Date date = formatDate(dateTimeInput);
        System.out.print("\nEnter flight duration in terms of HOURS > ");
        int hours = Integer.parseInt(sc.nextLine());
        System.out.print("\nEnter flight duration in terms of MINUTES > ");
        int minutes = Integer.parseInt(sc.nextLine());
        int totalMinutes = (hours * 60) + minutes;
        long seconds = totalMinutes * 60;
        Duration duration = Duration.ofSeconds(seconds);

        
        FlightSchedule flightSch = new FlightSchedule();
        flightSch.setCabinClass(ccList);
        flightSch.setDepartureTime(date);
        flightSch.setFlightDuration(duration);
        Date arrTime = computeArrivalTime(date,duration);
        flightSch.setArrivalTime(arrTime);
        flightSch.setFlightDuration(duration);
        
        FlightSchedulePlan singleFsp = new SinglePlan();
        singleFsp.setType(FlightScheduleStatus.ACTIVE);
        singleFsp.setFlight(flight);
        List<FlightSchedule> fsList = new ArrayList<FlightSchedule>();
        fsList.add(flightSch);
        singleFsp.setFlightSchedule(fsList);
        flightSch.setFlightSchedulePlan(singleFsp);
        flight.setFlightSchedulePlan(singleFsp);

        
        updateDatabaseOnFSPFS(flightSch, singleFsp);

    }
    

    
    private Date formatDate(String dateTimeInput) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeInput, formatter);
        ZoneId zoneId = ZoneId.of("Asia/Singapore");
        Date date = Date.from(dateTime.atZone(zoneId).toInstant());
        return date;
    }
    
    private Date computeArrivalTime(Date departureTime, Duration flightDuration) {
        Instant departureInstant = departureTime.toInstant();
        LocalDateTime departureDateTime = LocalDateTime.ofInstant(departureInstant, ZoneId.systemDefault());
        LocalDateTime arrivalDateTime = departureDateTime.plus(flightDuration);
        Instant arrivalInstant = arrivalDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date arrivalTime = Date.from(arrivalInstant);
    
        return arrivalTime;
    }
    
    private Flight viewFlightDetailsCabinClasses(String flightNum) {
        Flight flight = FRSManagementSessionBeanRemote.viewSpecificFlight(flightNum);
        String flightDetails = "-- Flight Details -- \n";
        flightDetails += "Flight Number: " + flight.getFlightNumber() + "\n";
        flightDetails += "Aircraft Configuration: " + flight.getAircraftConfig().getName() + "\n";
        FlightRoute flightRoute = flight.getFlightRoute();
        flightDetails += "Flight Route: " + flightRoute.getAirportList().get(0).getCountry() + " --> " + flightRoute.getAirportList().get(1).getCountry();
        System.out.println(flightDetails);
        
        return flight;
    }
    
    private void createFareforEachCabinClass(Flight flight, CabinClass cc, BigDecimal fareAmount) {
        Fare fare = new Fare();
        fare.setFareAmount(fareAmount);
        fare.setFareBasisCode(flight.getFlightNumber()+cc.getType());
        FRSManagementSessionBeanRemote.createFareforEachCabinClass(cc.getId(), fare);
    }
    
    private void updateDatabaseOnFSPFS(FlightSchedule fs, FlightSchedulePlan fsp) {
        FRSManagementSessionBeanRemote.createFlightScheduleAndPlan(fs, fsp);
    }
}
