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
    
    private static Employee currEmployee;
    

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
                    try 
                    {
                        doLogin();
                        System.out.println("You are logged in!");
                         performTasks();
                        
                    } catch (InvalidLoginCredentialException ex) {
                        System.out.println("Invalid login credentials: " + ex.getMessage() + "\n");
                    }
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
    
    private static void doLogin() throws InvalidLoginCredentialException {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("*** FRS System :: Login ***\n");
        System.out.print("Enter username> ");
        String username = sc.nextLine().trim();
        System.out.print("Enter password> ");
        String password = sc.nextLine().trim();
        
        if (username.length() > 0 && password.length() > 0) {
            currEmployee = employeeSessionBean.login(username, password);
            
        } else {
            throw new InvalidLoginCredentialException("Invalid login credentials!");
        }
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
                    RoutePlannerTask routeManagerTasks = new RoutePlannerTask(FRSManagementSessionBeanRemote);
                    routeManagerTasks.getTasks();
                }
                else if (response == 3) {
                    ScheduleManagerTask scheduleManagerTasks = new ScheduleManagerTask(FRSManagementSessionBeanRemote);
                    scheduleManagerTasks.getTasks();

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
    

    
    
    private static void performSalesManagerTasks() {
        
    }
}
