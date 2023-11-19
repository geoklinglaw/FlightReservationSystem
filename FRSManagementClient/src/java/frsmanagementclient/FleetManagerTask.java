/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frsmanagementclient;

import ejb.session.stateless.FRSManagementSessionBeanRemote;
import entity.AircraftConfiguration;
import entity.CabinClass;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import util.enumeration.CabinClassType;

/**
 *
 * @author apple
 */
public class FleetManagerTask {
     private FRSManagementSessionBeanRemote FRSManagementSessionBeanRemote;

    public FleetManagerTask(FRSManagementSessionBeanRemote FRSManagementSessionBeanRemote) {
        this.FRSManagementSessionBeanRemote = FRSManagementSessionBeanRemote;
    }
    
    public void getTasks() {
        Scanner scanner = new Scanner(System.in);
        Integer response = -1;
        
        while(true) {
            System.out.println("\n\n*** Fleet Manager ***\n");
            System.out.print("Enter your task: \n");
            System.out.println("1: Create Aircraft Configuration");
            System.out.println("2: View all Aircraft Configurations");
            System.out.println("3: View Aircraft Configuration Details");
            System.out.println("To go back, please press '0'.");

            response = -1;
            
            while(response < 1 || response > 3) {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1){
                    createAircraftConfiguration(scanner);
                }
                else if (response == 2) {
                    viewAircraftConfigurations();
                }
                else if (response == 3) {
                    viewConfigurationDetails(scanner);
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
    
    private void createAircraftConfiguration(Scanner sc) {
        System.out.println("\n\n*** Creating Aircraft Configuration *** \n");
        
        AircraftConfiguration aircraftConfig = new AircraftConfiguration();
        
        String aircraftTypeText = "Enter Aircraft Type\n";
        aircraftTypeText += "0: Boeing 737 \n";
        aircraftTypeText += "1: Boeing 747";
        System.out.println(aircraftTypeText);

        System.out.print("Enter Aircraft Type \n> ");
        int type = sc.nextInt();
        System.out.print("Enter aircraft style \n> ");
        sc.nextLine();
        String style = sc.nextLine().trim();
        System.out.print("Enter number of cabin classes \n> ");
        int num = sc.nextInt();
        System.out.print("Enter maximum seat capacity \n> ");
        int maxSeats = sc.nextInt();
        sc.nextLine();

        List<CabinClass> cabinClasses = new ArrayList<CabinClass>();
        
        for (int i = 0; i < num; i++) {
            String cabinClassText = "Enter Select Cabin Class(es) \n";
            cabinClassText += "F: First Class \n";
            cabinClassText += "J: Business Class \n";
            cabinClassText += "W: Premium Economy \n";
            cabinClassText += "Y: Economy \n";
            cabinClassText += "> ";
            System.out.print(cabinClassText);
            String ccType = sc.nextLine().trim();
            System.out.print("Selected Cabin Class: " + CabinClassType.fromValue(ccType));
            
            System.out.print("\nEnter number of seats \n> ");
            int numSeats = sc.nextInt();
            System.out.print("Enter number of rows \n> ");
            int numRows = sc.nextInt();
            System.out.print("Enter number of seats abreast \n> ");
            int numSeatsAbreast = sc.nextInt();
            System.out.print("Enter number of aisles \n> ");
            int numAisles = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter seating configuration per column \n> ");
            String seatConfig = sc.nextLine().trim();
            
            CabinClass cabinClass = new CabinClass(ccType, new BigDecimal(numSeats), new BigDecimal(numSeatsAbreast), new BigDecimal(numRows), new BigDecimal(numAisles), seatConfig);
            cabinClasses.add(cabinClass);
            
        }
        
        FRSManagementSessionBeanRemote.createAircraftConfiguration(style, type, maxSeats, cabinClasses);
        
        String msg;
        msg = type == 0 ? "Boeing 737" : "Boeing 747";
        
        System.out.println("Successfully created " + msg);  
    }
    
    private void viewAircraftConfigurations() {
        List <AircraftConfiguration> aircraftConfigList = FRSManagementSessionBeanRemote.viewAllAircraftConfiguration();
        String acListString = "\nList of Aircraft Configurations\n";
        int index = 1;
        
        for (AircraftConfiguration acConfig: aircraftConfigList) {
            acListString += index + ": " + acConfig.getName() + " " + acConfig.getAircraftStyle() + "\n";
            index += 1; 
        }
        
        System.out.println(acListString);
        index -= 1;
        System.out.println("Total Number of Aircraft Configurations created: " + index);
    }
    
    private List <AircraftConfiguration> viewAircraftConfigurationList() {
        List <AircraftConfiguration> aircraftConfigList = FRSManagementSessionBeanRemote.viewAllAircraftConfiguration();
        String acListString = "\nList of Aircraft Configurations\n";
        int index = 1;
        
        for (AircraftConfiguration acConfig: aircraftConfigList) {
            acListString += index + ": " + acConfig.getName() + " (" + acConfig.getName()+ " " + ")\n";
            index += 1; 
        }
        
        System.out.println(acListString);
        index -= 1;
        System.out.println("Total Number of Aircraft Configurations created: " + index);
        
        return aircraftConfigList;
    }
    
    private void viewConfigurationDetails(Scanner sc) {
        List<AircraftConfiguration> aircraftconfigList = viewAircraftConfigurationList();
        System.out.print("Select which aircraft configuration details you would like to know:\n> ");
        int response = sc.nextInt();
        AircraftConfiguration selectedACConfig = aircraftconfigList.get(response - 1);
        
        String configDetails = "-- Aircraft Configuration Details -- \n";
        configDetails += "Name: " + selectedACConfig.getName() + "\n";
        configDetails += "Max Seat Capacity: " + selectedACConfig.getAircraftType().getMaxSeatCapacity();
        List<CabinClass> cabinClassList = selectedACConfig.getCabinClassList();
        configDetails += "\nCabin Class: ";
        for (CabinClass cc: cabinClassList) {
            configDetails += cc.getType().name() + "(" + cc.getSeatingCapacity() + ")" + ", ";
        }
        System.out.println(configDetails);
    }

}
