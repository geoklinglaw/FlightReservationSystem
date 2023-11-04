/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package frsmanagementclient;

import ejb.session.stateless.FRSManagementSessionBeanRemote;
import java.util.Scanner;
import javax.ejb.EJB;

/**
 *
 * @author apple
 */
public class Main {

    @EJB(name = "FRSManagementSessionBeanRemote")
    private static FRSManagementSessionBeanRemote FRSManagementSessionBeanRemote;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        runApp();

    }
    
    private static void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
            System.out.println("*** Welcome to Merlin Airlines Management System ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");
            response = 0;
            
            while(response < 1 || response > 2) {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1){       
                    doLogin();
                }
                else if (response == 2) {
                    break;
                }
                else {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 2) {
                System.out.println("Logging out now...");
                break;
            }
        }
    }
    
    private static void doLogin() {
        System.out.println("Logged in!");
        performTasks();
        
    }
    
    private static void performTasks() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
            System.out.println("\n\nEnter your role (will be auto next time) \n");
            System.out.println("1: Fleet Manager");
            System.out.println("2: Route Planner");
            System.out.println("3: Schedule Manager");
            System.out.println("4: Sales Manager");

            response = 0;
            
            while(response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1){       
                    FleetManagerTask fleetManagerTasks = new FleetManagerTask(FRSManagementSessionBeanRemote);
                    fleetManagerTasks.getTasks();
                    
                }
                else if (response == 2) {
                    performRoutePlannerTasks();
                }
                else if (response == 3) {
                    performScheduleManagerTasks();
                }
                else if (response == 4) {
                    performSalesManagerTasks();
                }
                else {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
        }
        
    }
    

    private static void performRoutePlannerTasks() {
        
    }
    
    private static void performScheduleManagerTasks() {
        
    }
    
    private static void performSalesManagerTasks() {
        
    }
}
