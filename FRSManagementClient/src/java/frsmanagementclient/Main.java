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

    public static String ASCII_ART1 = "  __  __ _____ ____  _     ___ _   _ _____      _    ___ ____  _     ___ _   _ _____ ____  \n" +
" |  \\/  | ____|  _ \\| |   |_ _| \\ | | ____|    / \\  |_ _|  _ \\| |   |_ _| \\ | | ____/ ___| \n" +
" | |\\/| |  _| | |_) | |    | ||  \\| |  _|     / _ \\  | || |_) | |    | ||  \\| |  _| \\___ \\ \n" +
" | |  | | |___|  _ <| |___ | || |\\  | |___   / ___ \\ | ||  _ <| |___ | || |\\  | |___ ___) |\n" +
" |_|  |_|_____|_| \\_\\_____|___|_| \\_|_____| /_/   \\_\\___|_| \\_\\_____|___|_| \\_|_____|____/ \n" +
"                                                                                           ";
    
    public static String ASCII_ART2 = "                                                              _                       _                 \n" +
"  _ __ ___   __ _ _ __   __ _  __ _  ___ _ __ ___   ___ _ __ | |_       ___ _   _ ___| |_ ___ _ __ ___  \n" +
" | '_ ` _ \\ / _` | '_ \\ / _` |/ _` |/ _ \\ '_ ` _ \\ / _ \\ '_ \\| __|     / __| | | / __| __/ _ \\ '_ ` _ \\ \n" +
" | | | | | | (_| | | | | (_| | (_| |  __/ | | | | |  __/ | | | |_      \\__ \\ |_| \\__ \\ ||  __/ | | | | |\n" +
" |_| |_| |_|\\__,_|_| |_|\\__,_|\\__, |\\___|_| |_| |_|\\___|_| |_|\\__|     |___/\\__, |___/\\__\\___|_| |_| |_|\n" +
"                              |___/                                         |___/                       ";
    
    public static String PLANE_ART = " __\n" +
" \\  \\     _ _\n" +
"  \\**\\ ___\\/ \\\n" +
"X*#####*+^^\\_\\\n" +
"  o/\\  \\\n" +
"     \\__\\";
    
    @EJB(name = "EmployeeSessionBeanRemote")
    private static EmployeeSessionBeanRemote employeeSessionBeanRemote;

    @EJB(name = "FRSManagementSessionBeanRemote")
    private static FRSManagementSessionBeanRemote FRSManagementSessionBeanRemote;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        runApp();

    }
    
    public static void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
            System.out.println(PLANE_ART);
            System.out.println(ASCII_ART1);
            System.out.println(ASCII_ART2);

            System.out.println("\n1: Login");
            System.out.println("2: Exit\n");
            response = 0;
            
            while(response < 1 || response > 2) {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1){  
                    performTasks();
                    try {
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
            Employee currEmployee = employeeSessionBeanRemote.login(username, password);
            
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
                    SalesManagerTask salesManagerTasks = new SalesManagerTask(FRSManagementSessionBeanRemote);
                    salesManagerTasks.getTasks();
                }
                else {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
        }
        
    }
    


}
