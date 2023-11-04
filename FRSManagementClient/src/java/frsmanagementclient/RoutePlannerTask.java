/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frsmanagementclient;

import ejb.session.stateless.FRSManagementSessionBeanRemote;
import java.util.Scanner;

/**
 *
 * @author apple
 */
public class RoutePlannerTask {
    private FRSManagementSessionBeanRemote FRSManagementSessionBeanRemote;

    public RoutePlannerTask(FRSManagementSessionBeanRemote FRSManagementSessionBeanRemote) {
        this.FRSManagementSessionBeanRemote = FRSManagementSessionBeanRemote;
    }
    
    public void getTasks() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
            System.out.println("\n\n*** Route Planner ***\n");
            System.out.println("Enter your task: \n");
            System.out.println("1: Create Flight Route");
            System.out.println("2: View all Flight Route");
            System.out.println("3: Delete Flight Route");

            response = 0;
            
            while(response < 1 || response > 3) {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1){
                    createFlightRoute(scanner);
                    
                }
                else if (response == 2) {
                }
                else if (response == 3) {
                }
                else {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
        }
    }
    
    private void createFlightRoute(Scanner sc) {
        
        
    }
}
