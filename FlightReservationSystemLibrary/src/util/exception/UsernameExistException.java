/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.exception;

/**
 *
 * @author erin
 */
public class UsernameExistException extends Exception {
    
    public UsernameExistException() {
        
    }
    
    public UsernameExistException(String msg) {
        super(msg);
    }
    
}
