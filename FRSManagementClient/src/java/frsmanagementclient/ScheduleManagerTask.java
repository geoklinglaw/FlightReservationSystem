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
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    
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
                else if (response == 7) {
                    viewFlightSchedulePlan();
                }                
                else if (response == 8){
                    viewFlightSchedulePlanDetails(scanner);
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

        for (FlightRoute route: routeList) {
            routeText += route.getId() + ": " + route.getAirportList().get(0).getCountry() + " --> " + route.getAirportList().get(1).getCountry() +"\n";
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
//        sc.nextLine();
        System.out.println("\n\n*** Creating Flight Schedule Plan *** \n");
//        System.out.println("Enter 'Y' if you would like to create a complementary flight schedule plan:");
//        int num = 1;
//        if (sc.nextLine().equals("Y")) {
//            num = 2;
//        }

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
        
        

        switch (type) {
                case 1:
                    Pair<Flight, List<Fare>> pair1 = handlingFSPCreation(flightNum, sc);
                    Flight sFlight = pair1.getKey();
                    List<Fare> fare1 = pair1.getValue();
                    for (CabinClass cc: sFlight.getAircraftConfig().getCabinClassList()) {
                        System.out.println("printing cc... " + cc.getType());
                    }
                    createSingleFSP(sc, sFlight, sFlight.getAircraftConfig().getCabinClassList(), fare1);
                    System.out.println("Success!");
                    break;
                    
                case 2:
                    Pair<Flight, List<Fare>> pair2 = handlingFSPCreation(flightNum, sc);
                    Flight mFlight = pair2.getKey();
                    List<Fare> fare2 = pair2.getValue();
                    createMultipleFSP(sc, mFlight, mFlight.getAircraftConfig().getCabinClassList(), fare2);
                    System.out.println("Success!");
                    break;
                    
                case 3:
                    Pair<Flight, List<Fare>> pair3 = handlingFSPCreation(flightNum, sc);
                    Flight rNFlight = pair3.getKey();
                    List<Fare> fare3 = pair3.getValue();
                    createRecurrentNFSP(sc, rNFlight, rNFlight.getAircraftConfig().getCabinClassList(), fare3);
                    System.out.println("Success!");  
                    break;
                    
                case 4:
                    Pair<Flight, List<Fare>> pair4 = handlingFSPCreation(flightNum, sc);
                    Flight rWFlight = pair4.getKey();
                    List<Fare> fare4 = pair4.getValue();
                    createRecurrentWeeklyFSP(sc, rWFlight, rWFlight.getAircraftConfig().getCabinClassList(), fare4);
                    System.out.println("Success!");  
                    break;
        }
        
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
            
        }
        System.out.println(ccText);
        
        return new Pair<>(flight, fareList);
    }
    
    private void createSingleFSP(Scanner sc, Flight flight, List<CabinClass> ccList, List<Fare> fare) {
        System.out.print("FOR SINGLE SCHEDULE --- ");
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
        singleFsp.setFare(fare);
        
        for (int i = 0; i < fare.size(); i++) {
            fare.get(i).setFlightSchedulePlan(singleFsp);
            fare.get(i).setCabinClass(ccList.get(i));
        }
        
//        updateDatabaseOnFSPFS(fsList, singleFsp, flight, fare);
//        for (int i = 0; i < ccList.size(); i++) {
//            createFareforEachCabinClass(flight, ccList.get(i), fare.get(i));
//        }

    }
    
    private void createMultipleFSP(Scanner sc, Flight flight, List<CabinClass> ccList, List<Fare> fare) {
        System.out.print("FOR MULTIPLE SCHEDULE --- ");
        System.out.print("\nEnter the number of flight schedules you want to create > ");
        int num = sc.nextInt();
        sc.nextLine();
        List<FlightSchedule> fsList = new ArrayList<FlightSchedule>();
        FlightSchedulePlan multipleFsp = new MultiplePlan();
        multipleFsp.setType(FlightScheduleStatus.ACTIVE);
        multipleFsp.setFlight(flight);
        
        for (int i = 0; i < num; i ++) {
            if (i > 0) {
                System.out.println(); // Add a new line only between schedule entries
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

            FlightSchedule flightSch = new FlightSchedule();
            flightSch.setCabinClass(ccList);
            flightSch.setDepartureTime(date);
            flightSch.setFlightDuration(duration);
            Date arrTime = computeArrivalTime(date,duration);
            flightSch.setArrivalTime(arrTime);
            flightSch.setFlightDuration(duration);
            fsList.add(flightSch);
            flightSch.setFlightSchedulePlan(multipleFsp);
        }

        multipleFsp.setFlightSchedule(fsList);
        multipleFsp.setFare(fare);
        
        for (int i = 0; i < fare.size(); i++) {
            fare.get(i).setFlightSchedulePlan(multipleFsp);
            fare.get(i).setCabinClass(ccList.get(i));
        }
        
//        updateDatabaseOnFSPFS(fsList, multipleFsp, flight, fare);
//        
//        
//        for (int i = 0; i < ccList.size(); i++) {
//            createFareforEachCabinClass(flight, ccList.get(i), fare.get(i));
//        }

    }
    

    private void createRecurrentNFSP(Scanner sc, Flight flight, List<CabinClass> ccList, List<Fare> fare) {
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
            
        int index = 1;
        System.out.println("\n\nFlight Routes created: ");
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(freq)) {
            LocalDateTime dateTime = LocalDateTime.of(date, startTime);
            Date formattedDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
            System.out.println(index + ": "+ dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            index += 1;

            FlightSchedule flightSch = new FlightSchedule();
            for (CabinClass cc: ccList) { // REMEMBER TO DO FOR OTHERS!!!!!!!
                System.out.println(cc.getId());
            }
            flightSch.setCabinClass(ccList);
            flightSch.setDepartureTime(formattedDate);
            flightSch.setFlightDuration(duration);
            Date arrTime = computeArrivalTime(formattedDate,duration);
            flightSch.setArrivalTime(arrTime);
            flightSch.setFlightDuration(duration);
            fsList.add(flightSch);
            flightSch.setFlightSchedulePlan(recNFsp);
            for (CabinClass cc: ccList) {
                cc.getFlightSchedule().add(flightSch);
            }
        }

        for (int i = 0; i < fare.size(); i++) {
            fare.get(i).setFlightSchedulePlan(recNFsp);
            fare.get(i).setCabinClass(ccList.get(i));
        }
        
        recNFsp.setFlightSchedule(fsList);
        recNFsp.setFare(fare);
//        updateDatabaseOnFSPFS(fsList, recNFsp, flight, fare);
//        for (int i = 0; i < ccList.size(); i++) {
//            createFareforEachCabinClass(flight, ccList.get(i), fare.get(i));
//        }

    }
    
    private void createRecurrentWeeklyFSP(Scanner sc, Flight flight, List<CabinClass> ccList, List<Fare> fare) {
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
            
        int index = 1;
        System.out.println("\n\nFlight Routes created: ");
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusWeeks(1)) {
            LocalDateTime dateTime = LocalDateTime.of(date, startTime);
            Date formattedDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
            System.out.println("DateTime: " + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            index += 1;

            FlightSchedule flightSch = new FlightSchedule();
            flightSch.setCabinClass(ccList);
            flightSch.setDepartureTime(formattedDate);
            flightSch.setFlightDuration(duration);
            Date arrTime = computeArrivalTime(formattedDate,duration);
            flightSch.setArrivalTime(arrTime);
            flightSch.setFlightDuration(duration);
            fsList.add(flightSch);
            flightSch.setFlightSchedulePlan(recWFsp);
            
            System.out.println("Checking for cabin class.. " + flightSch.getId());
            for (CabinClass cc: flightSch.getCabinClass()) {
                System.out.println(cc.getType());
            }
        }
        
        
        
        for (int i = 0; i < fare.size(); i++) {
            fare.get(i).setFlightSchedulePlan(recWFsp);
            fare.get(i).setCabinClass(ccList.get(i));
        }
        
        recWFsp.setFlightSchedule(fsList);
        recWFsp.setFare(fare);
        recWFsp.setDayOfWeek(new BigDecimal(dayOfWeekInt));
        createPersistAll(fsList, recWFsp, flight, fare, ccList);
        
//        for (int i = 0; i < ccList.size(); i++) {
//            createFareforEachCabinClass(flight, ccList.get(i), fare.get(i));
//        }

    }
    
    private void createPersistAll(List<FlightSchedule> fsList, FlightSchedulePlan fsp, Flight flight, List<Fare> fareList, List<CabinClass> ccList) {
        FRSManagementSessionBeanRemote.createFlightScheduleAndPlan(fsList, fsp, flight, fareList, ccList);
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
    
    private Fare createFareforEachCabinClass(Flight flight, CabinClass cc, Fare fare) {
        FRSManagementSessionBeanRemote.createFareforEachCabinClass(cc, fare.getFareBasisCode());
        return fare;
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
}
