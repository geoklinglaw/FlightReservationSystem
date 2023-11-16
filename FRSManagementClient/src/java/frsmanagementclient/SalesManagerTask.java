/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frsmanagementclient;

import ejb.session.stateless.FRSManagementSessionBeanRemote;
import ejb.session.stateless.FlightSessionBeanRemote;
import entity.CabinClass;
import entity.Flight;
import entity.Seat;
import java.util.List;
import java.util.Scanner;
import javax.ejb.EJB;

/**
 *
 * @author apple
 */
public class SalesManagerTask {
    private FRSManagementSessionBeanRemote FRSManagementSessionBeanRemote;

    @EJB(name = "FlightSessionBeanLocal")
    private FlightSessionBeanRemote flightSessionBeanRemote;
    
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
        
        String aircraftTypeText = "Enter Flight Number\n";
        String flightNum = sc.nextLine().trim();
        List<CabinClass> ccList = FRSManagementSessionBeanRemote.viewSeatsInventory(flightNum);
        for (CabinClass cc: ccList) {
            for (Seat seat: cc.getSeatList()) {
                
            }
        }
//        


    }
}
