/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frsmanagementclient;

import ejb.session.stateless.FRSManagementSessionBeanRemote;
import ejb.session.stateless.FlightSessionBeanRemote;
import entity.CabinClass;
import entity.Flight;
import entity.FlightBooking;
import entity.FlightCabinClass;
import entity.FlightRoute;
import entity.FlightSchedule;
import entity.FlightSchedulePlan;
import entity.Seat;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import javax.ejb.EJB;
import util.enumeration.CabinClassType;

/**
 *
 * @author apple
 */
public class SalesManagerTask {
    private FRSManagementSessionBeanRemote FRSManagementSessionBeanRemote;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BOLD = "\u001B[1m";

    public SalesManagerTask(FRSManagementSessionBeanRemote FRSManagementSessionBeanRemote) {
        this.FRSManagementSessionBeanRemote = FRSManagementSessionBeanRemote;
    }
    
    public void getTasks() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
            System.out.println("\n\n*** Sales Manager ***\n");
            System.out.print("Enter your task: \n");
            System.out.println("1: View Seat Inventory");
            System.out.println("2: View all reservations");
            System.out.println("To go back, please press '0'.");            

            response = -1;
            
            while(response < 1 || response > 3) {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1){
                   viewSeatsInventory(scanner);
                }
                else if (response == 2) {
                   viewReservation(scanner);
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
    
    private void viewReservation(Scanner sc) {
        System.out.println("\n\n*** Viewing Flight Reservations *** \n");

        sc.nextLine();
        System.out.print("Enter Flight Number > ");
        String flightNum = sc.nextLine().trim();
        
        Flight flight = FRSManagementSessionBeanRemote.retrieveFlightByNumber(flightNum);
        

        System.out.println("List of flight schedules associated with this flight:");
        System.out.printf("%30s%30s%30s\n", "ID", "Departure Time", "Arrival Time");
        for (int counter = 0; counter < flight.getFlightSchedulePlan().getFlightSchedule().size(); counter++) {
            FlightSchedule flightSchedule = flight.getFlightSchedulePlan().getFlightSchedule().get(counter);
            System.out.printf("%30s%30s%30s\n", counter, flightSchedule.getDepartureTime().getTime(), flightSchedule.getArrivalTime().getTime());
        }

        System.out.print("Select Flight Schedule ID > ");
        Long scheduleId = sc.nextLong();

        FlightSchedule selectedSchedule = FRSManagementSessionBeanRemote.retrieveFlightScheduleById(scheduleId);

        Map<String, List<FlightBooking>> cabinClassBookings = new TreeMap<>();
        
        for (FlightBooking booking : selectedSchedule.getFlightBookings()) {
            for (Seat s: booking.getSeatBookings()) {
                String seatNumber = s.getSeatID();
                cabinClassBookings.computeIfAbsent(seatNumber, k -> new ArrayList<>()).add(booking);
            }
        }

        System.out.printf("%20s%20s%20s\n", "Seat Number", "Passenger Name", "Fare Basis Code");
        for (Map.Entry<String, List<FlightBooking>> entry : cabinClassBookings.entrySet()) {
            for (FlightBooking booking : entry.getValue()) {
                String passengerName = booking.getFlightReservation().getPerson().getFirstName(); 
                BigDecimal fare = booking.getFareAmount(); 
                System.out.printf("%20s%20s%20s\n", entry.getKey(), passengerName, fare);
            }
        }
        
    }
    
    private void viewSeatsInventory(Scanner sc) {
        System.out.println("\n\n*** Viewing Seat Inventory *** \n");
        
        sc.nextLine();
        System.out.print("Enter Flight Number > ");
        String flightNum = sc.nextLine().trim();
        
        Flight flight = FRSManagementSessionBeanRemote.retrieveFlightByNumber(flightNum);
        
        String fsText = "\n\nList of Flight Schedules for " + flight.getFlightNumber() + "\n";
        FlightRoute fr = flight.getFlightRoute();
        fsText += "from " + fr.getOrigin().getCountry() + "(" + fr.getOrigin().getAirportCode() + ") --> " + fr.getDestination().getCountry() + "(" + fr.getDestination().getAirportCode() + ")\n";
        fsText += "No.            Departure Time                  Arrival Time \n";
        for (FlightSchedule fs: flight.getFlightSchedulePlan().getFlightSchedule()) {
            fsText += fs.getId() + ":   " + fs.getDepartureTime() + "     " + fs.getArrivalTime() + "\n";
        }
        System.out.println(fsText);
        
        System.out.print("\nEnter flight schedule ID for seat viewing >");
        int fsId = sc.nextInt();
        
        List <FlightCabinClass> fccList = FRSManagementSessionBeanRemote.viewSeatsInventory(new Long(fsId));
        int totalAvailSeats = 0;
        int totalReservedSeats = 0;
        int totalBalanceSeats = 0;
        
        
        for (FlightCabinClass fcc : fccList) {
            System.out.println("\n\n\nCabin Class: " + fcc.getCabinClass().getType() + "");
            System.out.println("Number of Available Seats: " + fcc.getNumAvailableSeats());
            System.out.println("Number of Reserved Seats: " + fcc.getNumBalanceSeats());
            System.out.println("Number of Balance Seats: " + fcc.getNumReservedSeats() + "\n");
            
            totalAvailSeats += fcc.getNumAvailableSeats().intValue();
            totalReservedSeats += fcc.getNumReservedSeats().intValue();
            totalBalanceSeats += fcc.getNumBalanceSeats().intValue();


            // Retrieve the seat list for each cabin class
            List<Seat> seatList = fcc.getSeatList(); // Ensure this list is sorted as per the seat labels

            // Print the seat configuration for each cabin class
            printCabinClassSeats(fcc);
        }
        
        System.out.println("\n" + ANSI_BOLD + "Across all cabin classes --" + ANSI_RESET);
        System.out.print("Total Available Seats: " + totalAvailSeats);
        System.out.print("\nTotal Reserved Seats " + totalReservedSeats);
        System.out.print("\nTotal Balance Seats: " + totalBalanceSeats);

    }
    
    
    private void printCabinClassSeats(FlightCabinClass flightCabinClass) {
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

    private boolean isAisle(int position, String[] parts) {
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

    private String getSeatLabel(int seatNumber, String[] parts) {
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

    private Seat findSeat(List<Seat> seatList, String seatLabel) {
        // Assuming Seat class has a method getLabel() to return the seat label (e.g., "1A", "2B")
        return seatList.stream()
                .filter(seat -> seat.getSeatID().equals(seatLabel))
                .findFirst()
                .orElse(null);
    }
    
    private String formatSeatLabel(String seatLabel, int status) {
        // Assuming status 0 means available, and any other value means taken
        if (status == 0) {
            return ANSI_GREEN + seatLabel + ANSI_RESET; // Green for available
        } else {
            return ANSI_RED + seatLabel + ANSI_RESET; // Red for taken
        }
    }
}
