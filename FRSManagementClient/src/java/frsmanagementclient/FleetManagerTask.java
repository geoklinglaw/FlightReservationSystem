/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frsmanagementclient;

import ejb.session.stateless.FRSManagementSessionBeanRemote;
import entity.AircraftConfiguration;
import entity.CabinClass;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        Integer response = 0;
        
        while(true) {
            System.out.println("\n\n*** Fleet Manager ***\n");
            System.out.println("Enter your task: \n");
            System.out.println("1: Create Aircraft Configuration");
            System.out.println("2: View all Aircraft Configurations");
            System.out.println("3: View Aircraft Configuration Details");

            response = 0;
            
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
                else {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
        }
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
        
        boolean selecting = true;
        List<Integer> cabinClassSelection = new ArrayList<>();
        
        while (selecting) {
            String cabinClassText = "Enter Select Cabin Class(es) \n";
            cabinClassText += "0: First Class \n";
            cabinClassText += "1: Business Class \n";
            cabinClassText += "2: Premium Economy \n";
            cabinClassText += "3: Economy \n";
            cabinClassText += "4: Selection Complete\n";
            cabinClassText += "> ";
            System.out.print(cabinClassText);
            Integer cc = sc.nextInt();
            if (cc == 4) {
                selecting = false;
                break;
            }
            cabinClassSelection.add(cc);
        }
        
        for (int i = 0; i < cabinClassSelection.size(); i++) {
            System.out.println(cabinClassSelection.get(i));
        }
        

        FRSManagementSessionBeanRemote.createAircraftConfiguration(type, cabinClassSelection);
        
        String msg;
        msg = type == 0 ? "Boeing 737" : "Boeing 747";
        
        System.out.println("Successfully created " + msg);  
    }
    
    private void viewAircraftConfigurations() {
        List <AircraftConfiguration> aircraftConfigList = FRSManagementSessionBeanRemote.viewAllAircraftConfiguration();
        String acListString = "\nList of Aircraft Configurations\n";
        int index = 1;
        
        for (AircraftConfiguration acConfig: aircraftConfigList) {
            acListString += index + ": " + acConfig.getName() + "\n";
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
            acListString += index + ": " + acConfig.getName() + "\n";
            index += 1; 
        }
        
        System.out.println(acListString);
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
        System.out.println(cabinClassList.size());
        configDetails += "Cabin Class: ";
        for (CabinClass cc: cabinClassList) {
            configDetails += cc.getType().name() + ", ";
        }
        System.out.println(configDetails);
    }

}
