/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package frsmanagementclient;

import ejb.session.stateless.EmployeeSessionBeanRemote;
import ejb.session.stateless.FRSManagementSessionBeanRemote;
import entity.Employee;
import java.util.Scanner;
import javax.ejb.EJB;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author apple
 */
public class Main {

    @EJB
    private static EmployeeSessionBeanRemote employeeSessionBean;

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
            System.out.println("*** Welcome to Merlion Airlines Management System ***\n");
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
        Scanner sc = new Scanner(System.in);
        System.out.println("==== Employee Login Interface ====");
        System.out.println("Enter login details:");
        System.out.print("> Enter Username: ");
        String username = sc.nextLine().trim();
        System.out.print("> Enter Password: ");
        String password = sc.nextLine().trim();
        try 
        {
            if (username.length() > 0 && password.length() > 0)
            {
                Employee currEmp = employeeSessionBean.login(username, password);
                System.out.println("Welcome " + currEmp.getEmployeeName() + ", you're logged in!\n");
                performTasks();
                
            } else 
            {
                System.out.println("No matching account found or wrong login details. Please try again.\n");
                doLogin();
            } 
            
        } catch (InvalidLoginCredentialException ex) {
                System.out.println("Oh no... An error has occurred.\n");
                runApp();
            }
                    
    }
        
        
 
    
    private static void performTasks() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
            System.out.println("\n\nEnter your role (will be auto next time)");
            System.out.println("1: Fleet Manager");
            System.out.println("2: Route Planner");
            System.out.println("3: Schedule Manager");
            System.out.println("4: Sales Manager");
            System.out.println("5: Logout");

            response = 0;
            
            while(response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1){       
                    FleetManagerTask fleetManagerTasks = new FleetManagerTask(FRSManagementSessionBeanRemote);
                    fleetManagerTasks.getTasks();
                    
                }
                else if (response == 2) {
                    RoutePlannerTask routeManagerTasks = new RoutePlannerTask(FRSManagementSessionBeanRemote);
                    routeManagerTasks.getTasks();
                }
                else if (response == 3) {
                    performScheduleManagerTasks();
                }
                else if (response == 4) {
                    performSalesManagerTasks();
                }
                else if (response == 5) {
                    System.out.println("You have logged out.\n");
                    runApp();
                }
                else {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
        }
        
    }
    

    
    private static void performScheduleManagerTasks() {
        
    }
    
    private static void performSalesManagerTasks() {
        
    }
}
