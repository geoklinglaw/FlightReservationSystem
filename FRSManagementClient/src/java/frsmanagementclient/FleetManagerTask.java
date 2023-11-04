/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frsmanagementclient;

import ejb.session.stateless.FRSManagementSessionBeanRemote;
import entity.AircraftConfiguration;
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
        
        String aircraftTypeText = "Enter Aircraft Type \n";
        aircraftTypeText += "0: Boeing 737 \n";
        aircraftTypeText += "1: Boeing 747";
        System.out.println(aircraftTypeText);

        System.out.print("Enter Aircraft Type> ");
        int response = sc.nextInt();
        FRSManagementSessionBeanRemote.createAircraftConfiguration(response);
        
        String msg;
        msg = response == 0 ? "Boeing 737" : "Boeing 747";
        
        System.out.println("Successfully created " + msg);  
    }
    
    private void viewAircraftConfigurations() {
        List <AircraftConfiguration> aircraftConfigList = FRSManagementSessionBeanRemote.viewAllAircraftConfiguration();
        String acListString = "List of Aircraft Configurations\n";
        int index = 1;
        
        for (AircraftConfiguration acConfig: aircraftConfigList) {
            acListString += index + ": " + acConfig.getName() + "\n";
            index += 1; 
        }
        
        System.out.println(acListString);
        System.out.println("Total Number of Aircraft Configurations created: " + index);
    }
     
     
     
    
}
