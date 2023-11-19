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
import entity.FlightCabinClass;
import entity.FlightRoute;
import entity.FlightSchedule;
import entity.FlightSchedulePlan;
import entity.MultiplePlan;
import entity.RecurrentPlan;
import entity.RecurrentWeeklyPlan;
import entity.SinglePlan;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import javafx.util.Pair;
import util.enumeration.CabinClassType;
import util.enumeration.FlightScheduleStatus;

/**
 *
 * @author apple
 */
public class ScheduleManagerTask {
    
    private FRSManagementSessionBeanRemote FRSManagementSessionBeanRemote;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    public ScheduleManagerTask(FRSManagementSessionBeanRemote FRSManagementSessionBeanRemote) {
        this.FRSManagementSessionBeanRemote = FRSManagementSessionBeanRemote;
    }
    
    public void getTasks() {
        Scanner scanner = new Scanner(System.in);
        Integer response = -1;
        
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
            System.out.println("To go back, please press '0'.");

            response = -1;
            
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
                else if (response == 7) {
                    viewFlightSchedulePlan();
                }                
                else if (response == 8){
                    viewFlightSchedulePlanDetails(scanner);
                } 
                else if (response == 9) {
                    updateFlightSchedulePlan(scanner);
                }
                
                else if (response == 10){
                    
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
    
    
    private void goBack() {
        Main.runApp();
    }
    
    private void createFlight(Scanner sc) {
        System.out.println("\n\n*** Creating Flight *** \n");
        System.out.println("List of Flight Routes:");
        
        Pair<List<FlightRoute>, List<AircraftConfiguration>> pair = FRSManagementSessionBeanRemote.enquireFlightRequirements();
        List<FlightRoute> routeList = pair.getKey();
        List<AircraftConfiguration> acConfiglist = pair.getValue();
        
        String routeText = "";

        for (FlightRoute route: routeList) {
            routeText += route.getId() + ": " + route.getOrigin().getCountry() + " --> " + route.getDestination().getCountry() +"\n";
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
        
        sc.nextLine();
        
        createComplementaryFlight(routeId, config, sc);
    }
    
    private List<Flight> viewAllFlight() {
        System.out.println("\n\n*** View Flights *** \n");
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
        flightDetails += "Flight Route: " + flightRoute.getOrigin().getCountry() + " --> " + flightRoute.getDestination().getCountry();
        System.out.println(flightDetails);
    }
    
    
    private void updateFlight(Scanner sc) {
        System.out.println("\n\n*** Updating Flight *** \n");
        System.out.print("Select the fields that you want to update (leave blank if there are no changes)! ");
        List<Flight> flights = FRSManagementSessionBeanRemote.viewAllFlight();
        String flightText = "\nList of Flight:\n";
        int idx = 1;
        for (Flight flight: flights) {
            flightText += idx + ": " + flight.getFlightNumber() + " (" + flight.getAircraftConfig().getName() + ")\n";
            idx += 1;
        }
        
        System.out.print(flightText);
        
        
        System.out.print("\nEnter the Flight to change > ");
        int response = sc.nextInt();
        Flight chosenFlight = flights.get(response - 1);
        String oldFNum = chosenFlight.getFlightNumber();
        
        System.out.print("\nEnter new Flight Number > ");
        sc.nextLine();
        String flightNum = sc.nextLine().trim();
        if (flightNum.length() > 0) {
            chosenFlight.setFlightNumber(flightNum);
        }
   
        Pair<List<FlightRoute>, List<AircraftConfiguration>> pair = FRSManagementSessionBeanRemote.enquireFlightRequirements();
        List<FlightRoute> routeList = pair.getKey();
        List<AircraftConfiguration> acConfiglist = pair.getValue();

        String routeText = "\nList of Flight Routes: \n";
        int index = 1;
        for (FlightRoute route: routeList) {
            routeText += index + ": " + route.getOrigin().getCountry() + " --> " + route.getDestination().getCountry() +"\n";
            index += 1;
        }
        System.out.print(routeText);
        
        System.out.print("\nEnter new Flight Route ID >");
        String routeId = sc.nextLine().trim();
        if (routeId.length() > 0) {
            Integer.parseInt(routeId);
        }
        
        String acConfigText = "List of Aircraft Configuration:";
        for (AircraftConfiguration config: acConfiglist) {
            acConfigText += "\n(" + config.getId() + ") " + config.getName() + ": ";
            for (CabinClass cc: config.getCabinClassList()) {
                acConfigText += cc.getType() + ", ";
            }
        }
        System.out.print(acConfigText);
        
        System.out.print("\nEnter new Aircraft Configuration ID > ");
        String configId = sc.nextLine().trim();
        
        
        Flight updatedFlight = chosenFlight;
        if (configId.length() == 0 && routeId.length() == 0) {
            updatedFlight = FRSManagementSessionBeanRemote.changeFlightNumber(chosenFlight, oldFNum);
        } else if (configId.length() == 0 && routeId.length() > 0) {
            int rID = Integer.parseInt(routeId);
            updatedFlight = FRSManagementSessionBeanRemote.updateFlight(chosenFlight, oldFNum, rID);
        } else {
            int cID = Integer.parseInt(configId);
            int rID = Integer.parseInt(routeId);
            updatedFlight = FRSManagementSessionBeanRemote.updateFlight(chosenFlight, oldFNum, rID, cID);
        }
        
        
        String flightDetails = "-- Updated Flight Details -- \n";
        flightDetails += "Flight Number: " + updatedFlight.getFlightNumber() + "\n";
        flightDetails += "Aircraft Configuration: " + updatedFlight.getAircraftConfig().getName() + "\n";
        FlightRoute flightRoute = updatedFlight.getFlightRoute();
        flightDetails += "Flight Route: " + flightRoute.getOrigin().getCountry() + " --> " + flightRoute.getDestination().getCountry();
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
        
        sc.nextLine();
        int num = 1;
        
        System.out.println("Select your preferred schedule type:");
        
        String schTypeText = "1: Single schedule\n";
        schTypeText += "2: Multiple schedule\n";
        schTypeText += "3: Recurrent schedule every n day\n";
        schTypeText += "4: Recurrent schedule weekly\n";
        schTypeText += "> ";
        System.out.print(schTypeText);
        
        int type = sc.nextInt();
        sc.nextLine();
        System.out.print("\nEnter Flight Number > ");
        String flightNum = sc.nextLine().trim();
        
//        System.out.print("Enter 'Y' if you would like to create a complementary flight route > ");
//        if (sc.nextLine().equals("Y")) {
//            num = 2;
//        }
        

        switch (type) {
            case 1:
                Pair<Flight, List<Fare>> pair1 = handlingFSPCreation(flightNum, sc);
                Flight sFlight = pair1.getKey();
                List<Fare> fare1 = pair1.getValue();
                Long sfspID = createSingleFSP(sc, sFlight, sFlight.getAircraftConfig().getCabinClassList(), fare1);
                System.out.println("Successfully created single flight schedule plan :)" ); 
                

                Long id1 = checkForComplementaryFlight(sFlight.getFlightRoute().getId(), sFlight.getAircraftConfig().getId(), sc);

                if (id1 != null) {
                    System.out.print("Enter the number of layover hours > ");
                    int layover = sc.nextInt();                    
                    createComplementaryReturnFSP(sfspID, id1, layover);
                    System.out.print("\n created Complementary Flight Schedule Plan!");
                } else {
                    System.out.print("\n Alright, no complementary flight schedule plan is created.");
                }
                
                break;

            case 2:
                Pair<Flight, List<Fare>> pair2 = handlingFSPCreation(flightNum, sc);
                Flight mFlight = pair2.getKey();
                List<Fare> fare2 = pair2.getValue();
                Long mfspID = createMultipleFSP(sc, mFlight, mFlight.getAircraftConfig().getCabinClassList(), fare2);
                System.out.println("Successfully created multiple flight schedule plan :)" );  
                
                 // check if flight has comp flight route 
                Long id2 = checkForComplementaryFlight(mFlight.getFlightRoute().getId(), mFlight.getAircraftConfig().getId(), sc);
                if (id2 != null) {
                    System.out.print("Enter the number of layover hours > ");
                    int layover = sc.nextInt();                    
                    createComplementaryReturnFSP(mfspID, id2, layover);
                    System.out.print("\n created Complementary Flight Schedule Plan!");

                } else {
                    System.out.print("\n Alright, no complementary flight schedule plan is created.");
                }
                break;

            case 3:
                Pair<Flight, List<Fare>> pair3 = handlingFSPCreation(flightNum, sc);
                Flight rNFlight = pair3.getKey();
                List<Fare> fare3 = pair3.getValue();
                Long rnfspID = createRecurrentNFSP(sc, rNFlight, rNFlight.getAircraftConfig().getCabinClassList(), fare3);
                System.out.println("Successfully created  n recurrent flight schedule plan :)" ); 
                
                // check if flight has comp flight route 
                Long id3 = checkForComplementaryFlight(rNFlight.getFlightRoute().getId(), rNFlight.getAircraftConfig().getId(), sc);
                if (id3 != null) {
                    System.out.print("Enter the number of layover hours > ");
                    int layover = sc.nextInt();
                    createComplementaryReturnFSP(rnfspID, id3, layover);
                    System.out.print("\n created Complementary Flight Schedule Plan!");

                } else {
                    System.out.print("\n Alright, no complementary flight schedule plan is created.");
                }
                break;

            case 4:
                Pair<Flight, List<Fare>> pair4 = handlingFSPCreation(flightNum, sc);
                Flight rWFlight = pair4.getKey();
                List<Fare> fare4 = pair4.getValue();
                Long rwfspID = createRecurrentWeeklyFSP(sc, rWFlight, rWFlight.getAircraftConfig().getCabinClassList(), fare4);
                System.out.println("Successfully created weekly recurrent flight schedule plan :)" );  
                
                 // check if flight has comp flight route 
                Long id4 = checkForComplementaryFlight(rWFlight.getFlightRoute().getId(), rWFlight.getAircraftConfig().getId(), sc);
                if (id4 != null) {
                    System.out.print("Enter the number of layover hours > ");
                    int layover = sc.nextInt();
                    createComplementaryReturnFSP(rwfspID, id4, layover);
                    System.out.print("\n created Complementary Flight Schedule Plan!");

                } else {
                    System.out.print("\n Alright, no complementary flight schedule plan is created.");
                }
                
                break;
        }
        
    }
    
    
    private Flight createComplementaryFlight(Long routeId, Long configId, Scanner sc) {
        FlightRoute route = FRSManagementSessionBeanRemote.retrieveFlightRouteById(routeId);
        Airport origin = route.getOrigin();
        Airport destination = route.getDestination();
        
        try {
            FlightRoute fr = FRSManagementSessionBeanRemote.viewFlightRoute(destination, origin);

            if (fr != null) {
                System.out.print("\nThere's an existing complementary flight route, enter 'Y' to create complementary flight > ");
                if (sc.nextLine().equals("Y")) {
                    System.out.print("\nEnter another Flight Number: \n> ");
                    String flightNum = sc.nextLine().trim();

                    Flight flight = FRSManagementSessionBeanRemote.createFlight(flightNum, fr.getId(), configId);
                    System.out.print("\nSuccessfully created complementary flight " + flightNum + "!");
                    return flight;
                } else {
                    System.out.println("Alright, no complementary flight is created!");
                }
            }
        } catch (Exception ex) {
            System.out.println("\nThere are no existing complementary flight routes.");
            return null;
        }
        
        return null;
    }
    
    private Long checkForComplementaryFlight(Long routeId, Long configId, Scanner sc) {
        FlightRoute route = FRSManagementSessionBeanRemote.retrieveFlightRouteById(routeId);
        Airport origin = route.getOrigin();
        Airport destination = route.getDestination();
        
        List<Flight> selectedFlights = FRSManagementSessionBeanRemote.checkComplementaryFlightExistence(destination, origin, configId);
        

        if (selectedFlights.size() != 0) {

            System.out.print("There are existing complementary flights.\nEnter 'Y' if you would like to create a complementary flight schedule plan > ");
            if (sc.nextLine().equals("Y")) {
                
                String flightText = "\n Select which flight you want to create complementary FSP for: \n";
                for (Flight f: selectedFlights) {
                    flightText += f.getId() + ": " +  f.getFlightNumber() + "\n";
                }
                flightText += "> ";
                System.out.print(flightText);
                int flightID = sc.nextInt();
                sc.nextLine();
                
                return new Long(flightID);
            }
        } else {
       
            System.out.print("There are no existing complementary flights.\nEnter 'Y' if you would still like to create a complementary flight schedule plan > ");
            if (sc.nextLine().equals("Y")) {
                Long originID = new Long(origin.getId());
                Long destID = new Long(destination.getId());
                FlightRoute newFR = FRSManagementSessionBeanRemote.createFlightRoute(originID, destID);
                System.out.print("\nComplementary flight route from " + origin.getCountry() + " to " + destination.getCountry() + " has been created!");
                System.out.println("\n *** Creating Complementary Flight now ***");

                Flight flight = createComplementaryFlight(newFR.getId(), configId, sc);

                if (flight != null) {
                    System.out.print("\nComplementary flight " + flight.getFlightNumber() + " has been created!");
                    return flight.getId();
                }
            }
        }

        return null; 
    }
        

    
    private void createComplementaryReturnFSP(Long fspID, Long flightID, int layover) {
        FRSManagementSessionBeanRemote.createComplementaryFSP(fspID, flightID, layover);
    }
    
    
    
    private Pair<Flight, List<Fare>> handlingFSPCreation(String flightNum, Scanner sc) {
        Flight flight = viewFlightDetailsCabinClasses(flightNum);
        String ccText = "\nInput fare for each cabin class:";
        List<Fare> fareList = new ArrayList<Fare>();


        for (CabinClass cc: flight.getAircraftConfig().getCabinClassList()) {
            ccText += "\n" + cc.getId() + ": " + cc.getType() + "\n";
            ccText += "Enter fare (0.00)> ";
            System.out.print(ccText);
            ccText = "";

            String fareAmt = sc.nextLine().trim();
            BigDecimal fareAmount = new BigDecimal(fareAmt);
            Fare fare = new Fare();
            fare.setFareAmount(fareAmount);
            fare.setFareBasisCode(flight.getFlightNumber()+cc.getType());
            fareList.add(fare);
            
            int maxSeats = cc.getSeatingCapacity().intValue();
        }
        
        System.out.println(ccText);
        return new Pair<>(flight, fareList);
    }
    
    private Long createSingleFSP(Scanner sc, Flight flight, List<CabinClass> ccList, List<Fare> fare) {
        System.out.print("FOR SINGLE SCHEDULE --- ");
        System.out.print("\nEnter date of flight (yyyy-MM-dd) > ");
        String singleDate = sc.nextLine().trim();
        System.out.print("Enter time of flight (HH:mm:ss) > ");
        String singleTime = sc.nextLine().trim();
        String dateTimeInput= singleDate + " " + singleTime;
        Date date = formatDate(dateTimeInput);
        System.out.print("Enter flight duration in terms of HOURS > ");
        int hours = Integer.parseInt(sc.nextLine());
        System.out.print("Enter flight duration in terms of MINUTES > ");
        int minutes = Integer.parseInt(sc.nextLine());
        int totalMinutes = (hours * 60) + minutes;
        long seconds = totalMinutes * 60;
        Duration duration = Duration.ofSeconds(seconds);
        
        List<List<FlightCabinClass>> flightccList = new ArrayList<List<FlightCabinClass>>();

        List<FlightCabinClass> fccList = new ArrayList<FlightCabinClass>();
        for (CabinClass cc: ccList) {
            BigDecimal maxSeats = cc.getSeatingCapacity();
            FlightCabinClass fcc = new FlightCabinClass(maxSeats, maxSeats, maxSeats);
            fcc.setCabinClass(cc);
            fccList.add(fcc);
        }

        FlightSchedule flightSch = new FlightSchedule();
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
        
        Long id = createPersistAll(fsList, singleFsp, flight, fare, flightccList);
        return id;
    }
    
    private Long createMultipleFSP(Scanner sc, Flight flight, List<CabinClass> ccList, List<Fare> fare) {
        System.out.print("FOR MULTIPLE SCHEDULE --- ");
        System.out.print("\nEnter the number of flight schedules you want to create > ");
        int num = sc.nextInt();
        sc.nextLine();
        List<FlightSchedule> fsList = new ArrayList<FlightSchedule>();
        FlightSchedulePlan multipleFsp = new MultiplePlan();
        multipleFsp.setType(FlightScheduleStatus.ACTIVE);
        multipleFsp.setFlight(flight);
        
        List<List<FlightCabinClass>> flightccList = new ArrayList<List<FlightCabinClass>>();
        for (int i = 0; i < num; i ++) {
            if (i > 0) {
                System.out.println(); 
            }
            System.out.print("Enter date of flight (yyyy-MM-dd) > ");
            String singleDate = sc.nextLine().trim();
            System.out.print("Enter time of flight (HH:mm:ss) > ");
            String singleTime = sc.nextLine().trim();
            String dateTimeInput= singleDate + " " + singleTime;
            Date date = formatDate(dateTimeInput);
            System.out.print("Enter flight duration in terms of HOURS > ");
            int hours = Integer.parseInt(sc.nextLine());
            System.out.print("Enter flight duration in terms of MINUTES > ");
            int minutes = Integer.parseInt(sc.nextLine());
            int totalMinutes = (hours * 60) + minutes;
            long seconds = totalMinutes * 60;
            Duration duration = Duration.ofSeconds(seconds);

            List<FlightCabinClass> fccList = new ArrayList<FlightCabinClass>();
            for (CabinClass cc: ccList) {
                BigDecimal maxSeats = cc.getSeatingCapacity();
                FlightCabinClass fcc = new FlightCabinClass(maxSeats, maxSeats, maxSeats);
                fcc.setCabinClass(cc);
                fccList.add(fcc);
            }
            
            flightccList.add(fccList);
            FlightSchedule flightSch = new FlightSchedule();
            flightSch.setDepartureTime(date);
            flightSch.setFlightDuration(duration);
            Date arrTime = computeArrivalTime(date,duration);
            flightSch.setArrivalTime(arrTime);
            flightSch.setFlightDuration(duration);
            fsList.add(flightSch);
        }

        Long id = createPersistAll(fsList, multipleFsp, flight, fare, new ArrayList<>(flightccList));
        
        return id;
    }
    

    private Long createRecurrentNFSP(Scanner sc, Flight flight, List<CabinClass> ccList, List<Fare> fare) {
        
        System.out.print("FOR RECURRENT N SCHEDULE --- ");
        System.out.print("\nEnter frequency > ");
        int freq = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter start date of schedule (yyyy-MM-dd) > ");
        String startDateStr = sc.nextLine().trim();
        LocalDate startDate = LocalDate.parse(startDateStr, dateFormatter);
        System.out.print("Enter time of flight (HH:mm:ss) > ");
        String startTimeStr = sc.nextLine().trim();
        LocalTime startTime = LocalTime.parse(startTimeStr, timeFormatter);
        
        System.out.print("Enter end date of schedule (yyyy-MM-dd) > ");
        String endDateStr = sc.nextLine().trim();
        LocalDate endDate = LocalDate.parse(endDateStr, dateFormatter);
        String endTimeStr = "23:59:00";
        String endDateTimeInput= endDateStr + " " + endTimeStr;
        Date endFormattedDate = formatDate(endDateTimeInput);
        
        System.out.print("Enter flight duration in terms of HOURS > ");
        int hours = Integer.parseInt(sc.nextLine());
        System.out.print("Enter flight duration in terms of MINUTES > ");
        int minutes = Integer.parseInt(sc.nextLine());
        int totalMinutes = (hours * 60) + minutes;
        long seconds = totalMinutes * 60;
        Duration duration = Duration.ofSeconds(seconds);
        
        RecurrentPlan recNFsp = new RecurrentPlan();
        recNFsp.setType(FlightScheduleStatus.ACTIVE);
        recNFsp.setFlight(flight);
        recNFsp.setFrequency(new BigDecimal(freq));
        recNFsp.setEndDate(endFormattedDate);
        
        List<FlightSchedule> fsList = new ArrayList<FlightSchedule>();
        List<List<FlightCabinClass>> flightccList = new ArrayList<List<FlightCabinClass>>();
            
        int index = 1;
        System.out.println("\n\nFlight Routes created: ");
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(freq)) {
            LocalDateTime dateTime = LocalDateTime.of(date, startTime);
            Date formattedDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
            System.out.println(index + ": "+ dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            index += 1;
            
            List<FlightCabinClass> fccList = new ArrayList<FlightCabinClass>();
            for (CabinClass cc: ccList) {
                BigDecimal maxSeats = cc.getSeatingCapacity();
                FlightCabinClass fcc = new FlightCabinClass(maxSeats, maxSeats, maxSeats);
                fcc.setCabinClass(cc);
                fccList.add(fcc);
            }
            
            flightccList.add(fccList);
            FlightSchedule flightSch = new FlightSchedule();
            flightSch.setDepartureTime(formattedDate);
            flightSch.setFlightDuration(duration);
            Date arrTime = computeArrivalTime(formattedDate,duration);
            flightSch.setArrivalTime(arrTime);
            flightSch.setFlightDuration(duration);
            fsList.add(flightSch);
           
        }
        
        Long id = createPersistAll(fsList, recNFsp, flight, fare, flightccList);
        return id;
    }
    
    private Long createRecurrentWeeklyFSP(Scanner sc, Flight flight, List<CabinClass> ccList, List<Fare> fare) {
        
        System.out.print("FOR RECURRENT WEEKLY SCHEDULE --- ");
        System.out.print("\nEnter dayOfWeek (1=Monday, 7=Sunday) > ");
        int dayOfWeekInt = sc.nextInt();
        sc.nextLine();

        DayOfWeek dayOfWeek = DayOfWeek.of(dayOfWeekInt);
        System.out.print("Enter start date of schedule (yyyy-MM-dd) > ");
        String startDateStr = sc.nextLine().trim();
        LocalDate startDate = LocalDate.parse(startDateStr, dateFormatter).with(TemporalAdjusters.next(dayOfWeek));
        System.out.print("Enter time of flight (HH:mm:ss) > ");
        String startTimeStr = sc.nextLine().trim();
        LocalTime startTime = LocalTime.parse(startTimeStr, timeFormatter);
        
        System.out.print("Enter end date of schedule (yyyy-MM-dd) > ");
        String endDateStr = sc.nextLine().trim();
        LocalDate endDate = LocalDate.parse(endDateStr, dateFormatter);
        String endTimeStr = "23:59:00";
        String endDateTimeInput= endDateStr + " " + endTimeStr;
        Date endFormattedDate = formatDate(endDateTimeInput);
        
        System.out.print("Enter flight duration in terms of HOURS > ");
        int hours = Integer.parseInt(sc.nextLine());
        System.out.print("Enter flight duration in terms of MINUTES > ");
        int minutes = Integer.parseInt(sc.nextLine());
        int totalMinutes = (hours * 60) + minutes;
        long seconds = totalMinutes * 60;
        Duration duration = Duration.ofSeconds(seconds);
        
        RecurrentWeeklyPlan recWFsp = new RecurrentWeeklyPlan();
        recWFsp.setType(FlightScheduleStatus.ACTIVE);
        recWFsp.setFlight(flight);
        recWFsp.setEndDate(endFormattedDate);
        
        List<FlightSchedule> fsList = new ArrayList<FlightSchedule>();
        List<List<FlightCabinClass>> flightccList = new ArrayList<List<FlightCabinClass>>();
            
        int index = 1;
        System.out.println("\n\nFlight Routes created: ");
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusWeeks(1)) {
            LocalDateTime dateTime = LocalDateTime.of(date, startTime);
            Date formattedDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
            System.out.println("DateTime: " + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            index += 1;

            List<FlightCabinClass> fccList = new ArrayList<FlightCabinClass>();
            for (CabinClass cc: ccList) {
                BigDecimal maxSeats = cc.getSeatingCapacity();
                FlightCabinClass fcc = new FlightCabinClass(maxSeats, maxSeats, maxSeats);
                fcc.setCabinClass(cc);
                fccList.add(fcc);
            }
            
            flightccList.add(fccList);
            FlightSchedule flightSch = new FlightSchedule();
            flightSch.setDepartureTime(formattedDate);
            flightSch.setFlightDuration(duration);
            Date arrTime = computeArrivalTime(formattedDate,duration);
            flightSch.setArrivalTime(arrTime);
            flightSch.setFlightDuration(duration);
            fsList.add(flightSch);
           
        }
        
        recWFsp.setDayOfWeek(new BigDecimal(dayOfWeekInt));
        Long id = createPersistAll(fsList, recWFsp, flight, fare, flightccList);
        return id;
    }
    
    private Long createPersistAll(List<FlightSchedule> fsList, FlightSchedulePlan fsp, Flight flight, List<Fare> fareList, List<List<FlightCabinClass>>  FCCList) {
        Long id = FRSManagementSessionBeanRemote.createFlightScheduleAndPlan(fsList, fsp, flight, fareList, FCCList);
        return id;
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
        String flightDetails = "\n-- Flight Details -- \n";
        flightDetails += "Flight Number: " + flight.getFlightNumber() + "\n";
        flightDetails += "Aircraft Configuration: " + flight.getAircraftConfig().getName() + "\n";
        FlightRoute flightRoute = flight.getFlightRoute();
        flightDetails += "Flight Route: " + flightRoute.getOrigin().getCountry() + " --> " + flightRoute.getDestination().getCountry();
        System.out.println(flightDetails);
        
        return flight;
    }
    
    
    
    private List<FlightSchedulePlan> viewFlightSchedulePlan() {
        System.out.println("\n\n*** View All Flight Schedule Plan *** \n");
        List<FlightSchedulePlan> fspList = FRSManagementSessionBeanRemote.viewAllFlightSchedulePlan();

        String fspText = "List of Flight Schedule Plans:\n";
        int index = 1;
        for (FlightSchedulePlan fsp: fspList) {
            if (fsp instanceof SinglePlan) {
                fspText += index + ": Single Plan" + " (" + fsp.getFlight().getFlightNumber() + ")\n";
            } else if (fsp instanceof MultiplePlan) {
                fspText += index + ": Multiple Plan" + " (" + fsp.getFlight().getFlightNumber() + ")\n";
            } else if (fsp instanceof MultiplePlan) {
                fspText += index + ": Recurrent N Plan" + " (" + fsp.getFlight().getFlightNumber() + ")\n";
            } else {
                fspText += index + ": Reccurent Weekly Plan" + " (" + fsp.getFlight().getFlightNumber() + ")\n";
            }
            index += 1;
        }
        
        System.out.print(fspText);
        return fspList;
    }
    
    private void viewFlightSchedulePlanDetails(Scanner sc) {
        List<FlightSchedulePlan> fspList = viewFlightSchedulePlan();
        System.out.print("Select which flight schedule plan details you would like to know:\n> ");
        int response = sc.nextInt();
        FlightSchedulePlan fsp = fspList.get(response - 1);

        String fspDetails = "-- Flight Schedule Plan Details -- \n";
        fspDetails += "Flight Number: " + fsp.getFlight().getFlightNumber() + "\n";
        if (fsp instanceof SinglePlan) {
            fspDetails += "Plan Type: Single Plan \n";
        } else if (fsp instanceof MultiplePlan) {
            fspDetails += "Plan Type: Multiple Plan \n";
        } else if (fsp instanceof MultiplePlan) {
            fspDetails += "Plan Type: Recurrent N Plan \n";
        } else {
            fspDetails += "Plan Type: Recurrent Weekly Plan \n";
        }

        fspDetails += "| List of flight schedules | \n";
        int index = 1;
        List<FlightSchedule> sortedFS = fsp.getFlightSchedule();
        sortedFS.sort((fs1, fs2) -> fs1.getDepartureTime().compareTo(fs2.getDepartureTime()));

        for (FlightSchedule fs: sortedFS) {
            fspDetails += index + ": " + fs.getDepartureTime() + " --> " + fs.getArrivalTime() + "\n";
            index += 1;
        }

        System.out.println(fspDetails);
    }
    
    private void updateFlightSchedulePlan(Scanner sc) {
        List<FlightSchedulePlan> fspList = viewFlightSchedulePlan();
        System.out.print("Select which flight schedule plan details you would like to update:\n> ");
        int response = sc.nextInt();
        FlightSchedulePlan fsp = fspList.get(response - 1);

        String fspDetails = "-- Flight Schedule Plan Details -- \n";
        fspDetails += "Flight Number: " + fsp.getFlight().getFlightNumber() + "\n";
        if (fsp instanceof SinglePlan) {
            fspDetails += "Plan Type: Single Plan \n";
        } else if (fsp instanceof MultiplePlan) {
            fspDetails += "Plan Type: Multiple Plan \n";
        } else if (fsp instanceof MultiplePlan) {
            fspDetails += "Plan Type: Recurrent N Plan \n";
        } else {
            fspDetails += "Plan Type: Recurrent Weekly Plan \n";
        }

        fspDetails += "| List of flight schedules | \n";
        int index = 1;
        List<FlightSchedule> sortedFS = fsp.getFlightSchedule();
        sortedFS.sort((fs1, fs2) -> fs1.getDepartureTime().compareTo(fs2.getDepartureTime()));

        for (FlightSchedule fs: sortedFS) {
            fspDetails += index + ": " + fs.getDepartureTime() + " --> " + fs.getArrivalTime() + "\n";
            index += 1;
        }

        System.out.println(fspDetails);
        
        
        sc.nextLine();
        System.out.print("\n*** Updating Flight Schedule Plan *** ");
        System.out.println("You may update flight, fares and flight schedules. ");

        
        System.out.print("\nEnter 'Y' to update Flight (blank if no change)> ");
        String flightOpt = sc.nextLine().trim();
        if(flightOpt.equals("Y")) {
            updateFlightWithFSP(sc, fsp);
        }
        
        System.out.print("Enter 'Y' to update fares (blank if no change)> ");
        String fareOpt = sc.nextLine().trim();
        if(fareOpt.equals("Y")) {
            updateFare(fsp);
        }
        
        System.out.print("Enter 'Y' to update Flight Schedule (blank if no change)> ");
        String flightSch = sc.nextLine().trim();
        if(flightSch.equals("Y")) {
            updateFlightSchedule(sortedFS, fsp);
        }
        
        FlightSchedulePlan newFSP = FRSManagementSessionBeanRemote.retrieveFlightSchedulePlan(fsp.getId());
        
        String fspDetails2 = "-- Flight Schedule Plan Details -- \n";
        fspDetails += "Flight Number: " + newFSP.getFlight().getFlightNumber() + "\n";
        if (fsp instanceof SinglePlan) {
            fspDetails += "Plan Type: Single Plan \n";
        } else if (fsp instanceof MultiplePlan) {
            fspDetails += "Plan Type: Multiple Plan \n";
        } else if (fsp instanceof MultiplePlan) {
            fspDetails += "Plan Type: Recurrent N Plan \n";
        } else {
            fspDetails += "Plan Type: Recurrent Weekly Plan \n";
        }
        
        fspDetails2 += "| List of flight schedules | \n";
        int index2 = 1;
        List<FlightSchedule> sortedFS2 = newFSP.getFlightSchedule();
        sortedFS.sort((fs1, fs2) -> fs1.getDepartureTime().compareTo(fs2.getDepartureTime()));

        for (FlightSchedule fs: sortedFS) {
            fspDetails2 += index + ": " + fs.getDepartureTime() + " --> " + fs.getArrivalTime() + "\n";
            index2 += 1;
        }

        System.out.println(fspDetails2);
    }
    
    private void updateFlightSchedule(List<FlightSchedule> sortedFS, FlightSchedulePlan fsp) {
        Scanner sc = new Scanner(System.in);
        String output = "Select the flight schedules that you would like to change: \n";
        List<Integer> fsidList = new ArrayList<Integer>();
        for (FlightSchedule fs: sortedFS) {
            output += fs.getId() + ": " + fs.getDepartureTime() + " --> " + fs.getArrivalTime() + "\n";

        }
        
        output += "Enter the number of flight schedules you would like to change >";
        System.out.print(output);
        int num = sc.nextInt();
        
        for (int i = 0; i < num; i++) {
            System.out.print("Enter flight schedule id > ");
            fsidList.add(new Integer(sc.nextInt()));
        }
        
        
        for (Integer id: fsidList) {
            System.out.print("For flight schedule ID " + id + ", enter ");
            System.out.print("1: Create New Flight Schedule \n");
            System.out.print("2: Delete Flight Schedule \n");
            System.out.print("3: Make chanfes to a Flight Schedule \n");
            
            int response = sc.nextInt();
            
            switch(response){
                case 1:
                    createNewFS(fsp, sc);
                    break;
                case 2:
                    deleteFS(Long.parseLong(String.valueOf(id)), sc);
                    break;
                case 3:
                    makeChangesToFS(Long.parseLong(String.valueOf(id)), sc);
                    break;
                   
            }
        }
    }
    
    private void deleteFS(Long fsID, Scanner sc) {
        
        
    }
    
    private void createNewFS(FlightSchedulePlan fsp, Scanner sc) {
        System.out.print("\nEnter date of flight (yyyy-MM-dd) > ");
        String singleDate = sc.nextLine().trim();
        System.out.print("Enter time of flight (HH:mm:ss) > ");
        String singleTime = sc.nextLine().trim();
        String dateTimeInput= singleDate + " " + singleTime;
        Date date = formatDate(dateTimeInput);
        System.out.print("Enter flight duration in terms of HOURS > ");
        int hours = Integer.parseInt(sc.nextLine());
        System.out.print("Enter flight duration in terms of MINUTES > ");
        int minutes = Integer.parseInt(sc.nextLine());
        int totalMinutes = (hours * 60) + minutes;
        long seconds = totalMinutes * 60;
        Duration duration = Duration.ofSeconds(seconds);
        
        List<List<FlightCabinClass>> flightccList = new ArrayList<List<FlightCabinClass>>();
        Flight flight = fsp.getFlight();

        List<FlightCabinClass> fccList = fsp.getFlightSchedule().get(0).getFlightCabinClass();
        List<Fare> fareList = new ArrayList<Fare>();
        
        for (int i = 0; i < fccList.size(); i++) {
            FlightCabinClass cc = fccList.get(i);
            BigDecimal maxSeats = cc.getNumAvailableSeats();
            FlightCabinClass fcc = new FlightCabinClass(maxSeats, maxSeats, maxSeats);
            fcc.setCabinClass(cc.getCabinClass());
            fccList.add(fcc);
            
            Fare fare = new Fare();
            fare.setFareAmount(cc.getFare().getFareAmount());
            fare.setFareBasisCode(cc.getFare().getFareBasisCode());
            fare.setFareID(cc.getFare().getFareID());
            fareList.add(fare);
            
        }
        
        FlightSchedule flightSch = new FlightSchedule();
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
        
        FRSManagementSessionBeanRemote.MakeNewFS(fsp, flightSch, fccList, fareList); 
    }
    
    private void makeChangesToFS(Long id, Scanner sc) {
        FlightSchedule fs = FRSManagementSessionBeanRemote.retrieveFlightScheduleById(id);
        
        Date date = fs.getDepartureTime();
        Duration duration = Duration.ofSeconds((long) fs.getFlightDuration());

        // Change Departure Date and Time
        System.out.print("Enter new departure date (dd-MM-yy) (blank if no change)> ");
        String deptDateInput = sc.nextLine().trim();
        if (!deptDateInput.isEmpty()) {
            System.out.print("Enter time of flight (HH:mm:ss) > ");
            String deptTimeInput = sc.nextLine().trim();
            String dateTimeInput = deptDateInput + " " + deptTimeInput;
            date = formatDate(dateTimeInput); // Assuming formatDate method exists and returns a Date object
            fs.setDepartureTime(date);
        }

        // Change Duration
        System.out.print("Enter 'Y' to change duration (blank if no change)> ");
        String durText = sc.nextLine().trim();
        if (durText.equalsIgnoreCase("Y")) {
            System.out.print("Enter flight duration in terms of HOURS > ");
            int hours = Integer.parseInt(sc.nextLine());
            System.out.print("Enter flight duration in terms of MINUTES > ");
            int minutes = Integer.parseInt(sc.nextLine());
            int totalMinutes = (hours * 60) + minutes;
            long seconds = totalMinutes * 60;
            duration = Duration.ofSeconds(seconds);
            fs.setFlightDuration(duration);
        }
        
        Date arrTime = computeArrivalTime(date, duration);
        fs.setArrivalTime(arrTime);
        
//        System.out.print("Add new cabin class (blank if no change)> ");
//        String cabin = sc.nextLine().trim();
//        if(cabin.equals("Y")) {
//            for (CabinClassType cabinClass : CabinClassType.values()) {
//                System.out.println(cabinClass + " " + cabinClass.getValue());
//            }
//            System.out.print("Enter cabin class type > ");
//            String newCC = sc.nextLine().trim();
//            fs.setFlightCabinClass(flightCabinClass);
//        }

        FlightSchedule newFS = FRSManagementSessionBeanRemote.updateFlightSchedule(fs);
    }
    
    private FlightSchedulePlan updateFare(FlightSchedulePlan fsp) {
        Scanner sc = new Scanner(System.in);
        FlightSchedulePlan newFSP = FRSManagementSessionBeanRemote.retrieveFlightSchedulePlan(fsp.getId());
        
        HashMap<Integer, Fare> hashmap = new HashMap<Integer, Fare>();
        List<Fare> newFareList = new ArrayList<Fare>();
        
        String output = "\n\nCabin Class and Corresponding Fares: \n";
        for (int i = 0; i < newFSP.getFare().size(); i++) {
            CabinClassType ccType = newFSP.getFare().get(i).getFlightCabinClass().getCabinClass().getType();
            output += i + ": " + ccType + " -- $" + newFSP.getFare().get(i).getFareAmount() + "\n";
            hashmap.put(new Integer(i), newFSP.getFare().get(i));
        }
        System.out.print(output);
        
        System.out.print("How many fares would you like to change? > ");
        int num = sc.nextInt();
        sc.nextLine();
        
        for (int i = 0; i < num; i++) {
            System.out.print("Enter the index of the fare you would like to change > ");
            Fare fare = hashmap.get(sc.nextInt());
            newFareList.add(fare);
        }
        
        
        FlightSchedulePlan updatedFSP = FRSManagementSessionBeanRemote.updateFaresFSP(newFSP, newFareList);
        return updatedFSP;
    }
    
    private void updateFlightWithFSP(Scanner sc, FlightSchedulePlan fsp) {
        System.out.println("\n\n*** Updating Flight *** \n");
        System.out.print("Select the fields that you want to update (leave blank if there are no changes)! ");
        List<Flight> flights = FRSManagementSessionBeanRemote.viewAllFlight();
        String flightText = "\nList of Flight:\n";
        int idx = 1;
        for (Flight flight: flights) {
            flightText += idx + ": " + flight.getFlightNumber() + " (" + flight.getAircraftConfig().getName() + ")\n";
            idx += 1;
        }
        
        System.out.print(flightText);
        
        
        System.out.print("\nEnter the Flight to change > ");
        int response = sc.nextInt();
        Flight chosenFlight = flights.get(response - 1);
        String oldFNum = chosenFlight.getFlightNumber();
        
        System.out.print("\nEnter new Flight Number > ");
        sc.nextLine();
        String flightNum = sc.nextLine().trim();
        if (flightNum.length() > 0) {
            chosenFlight.setFlightNumber(flightNum);
        }
   
        Pair<List<FlightRoute>, List<AircraftConfiguration>> pair = FRSManagementSessionBeanRemote.enquireFlightRequirements();
        List<FlightRoute> routeList = pair.getKey();
        List<AircraftConfiguration> acConfiglist = pair.getValue();

        String routeText = "\nList of Flight Routes: \n";
        int index = 1;
        for (FlightRoute route: routeList) {
            routeText += index + ": " + route.getOrigin().getCountry() + " --> " + route.getDestination().getCountry() +"\n";
            index += 1;
        }
        System.out.print(routeText);
        
        System.out.print("\nEnter new Flight Route ID >");
        String routeId = sc.nextLine().trim();
        if (routeId.length() > 0) {
            Integer.parseInt(routeId);
        }
        
        String acConfigText = "List of Aircraft Configuration:";
        for (AircraftConfiguration config: acConfiglist) {
            acConfigText += "\n(" + config.getId() + ") " + config.getName() + ": ";
            for (CabinClass cc: config.getCabinClassList()) {
                acConfigText += cc.getType() + ", ";
            }
        }
        System.out.print(acConfigText);
        
        System.out.print("\nEnter new Aircraft Configuration ID > ");
        String configId = sc.nextLine().trim();
        
        
        Flight updatedFlight = chosenFlight;
        if (configId.length() == 0 && routeId.length() == 0) {
            updatedFlight = FRSManagementSessionBeanRemote.changeFlightNumber(chosenFlight, oldFNum);
        } else if (configId.length() == 0 && routeId.length() > 0) {
            int rID = Integer.parseInt(routeId);
            updatedFlight = FRSManagementSessionBeanRemote.updateFlight(chosenFlight, oldFNum, rID);
        } else {
            int cID = Integer.parseInt(configId);
            int rID = Integer.parseInt(routeId);
            updatedFlight = FRSManagementSessionBeanRemote.updateFlight(chosenFlight, oldFNum, rID, cID);
        }
        
        String flightDetails = "-- Updated Flight Details -- \n";
        flightDetails += "Flight Number: " + updatedFlight.getFlightNumber() + "\n";
        flightDetails += "Aircraft Configuration: " + updatedFlight.getAircraftConfig().getName() + "\n";
        FlightRoute flightRoute = updatedFlight.getFlightRoute();
        flightDetails += "Flight Route: " + flightRoute.getOrigin().getCountry() + " --> " + flightRoute.getDestination().getCountry();
        System.out.println(flightDetails);
    }
}
