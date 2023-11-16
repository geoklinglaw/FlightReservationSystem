/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frsmanagementclient;

import ejb.session.stateless.FRSManagementSessionBeanRemote;
import ejb.session.stateless.FlightSessionBeanRemote;
import entity.CabinClass;
import entity.Flight;
import entity.FlightRoute;
import entity.FlightSchedule;
import entity.Seat;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import javax.ejb.EJB;

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
            System.out.println("2: View all Flight Route");
            System.out.println("3: Delete Flight Route");
            System.out.println("To go back, please press '0'.");            

            response = 0;
            
            while(response < 1 || response > 3) {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1){
                   viewSeatsInventory(scanner);
                }
                else if (response == 2) {
                    
                }
                else if (response == 3) {
                   
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
        
        List <CabinClass> ccList = FRSManagementSessionBeanRemote.viewSeatsInventory(new Long(fsId));
        int totalAvailSeats = 0;
        int totalReservedSeats = 0;
        int totalBalanceSeats = 0;
        
        
        for (CabinClass cc : ccList) {
            System.out.println("\n\n\nCabin Class: " + cc.getType() + "");
            System.out.println("Number of Available Seats: " + cc.getNumAvailableSeats());
            System.out.println("Number of Reserved Seats: " + cc.getNumBalanceSeats());
            System.out.println("Number of Balance Seats: " + cc.getNumReservedSeats() + "\n");
            
            totalAvailSeats += cc.getNumAvailableSeats().intValue();
            totalReservedSeats += cc.getNumReservedSeats().intValue();
            totalBalanceSeats += cc.getNumBalanceSeats().intValue();


            // Retrieve the seat list for each cabin class
            List<Seat> seatList = cc.getSeatList(); // Ensure this list is sorted as per the seat labels

            // Print the seat configuration for each cabin class
            printCabinClassSeats(cc);
        }
        
        System.out.println("\n" + ANSI_BOLD + "Across all cabin classes --" + ANSI_RESET);
        System.out.print("Total Available Seats: " + totalAvailSeats);
        System.out.print("\nTotal Reserved Seats " + totalReservedSeats);
        System.out.print("\nTotal Balance Seats: " + totalBalanceSeats);

    }
    
    
    private void printCabinClassSeats(CabinClass cabinClass) {
        String seatConfig = cabinClass.getSeatConfig(); // e.g., "3-3", "2-1-1", "2-2-2-2"
        BigDecimal numRows = cabinClass.getNumRows();
        List<Seat> seatList = cabinClass.getSeatList();

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
