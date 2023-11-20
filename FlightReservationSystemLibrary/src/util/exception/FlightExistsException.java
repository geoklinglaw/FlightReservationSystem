/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package util.exception;

/**
 *
 * @author apple
 */
public class FlightExistsException extends Exception {

    /**
     * Creates a new instance of <code>FlightExistsException</code> without
     * detail message.
     */
    public FlightExistsException() {
    }

    /**
     * Constructs an instance of <code>FlightExistsException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public FlightExistsException(String msg) {
        super(msg);
    }
}
