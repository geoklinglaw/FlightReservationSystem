/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package util.exception;

/**
 *
 * @author apple
 */
public class FlightRouteExistsException extends Exception {

    /**
     * Creates a new instance of <code>FlightRouteExistsException</code> without
     * detail message.
     */
    public FlightRouteExistsException() {
    }

    /**
     * Constructs an instance of <code>FlightRouteExistsException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public FlightRouteExistsException(String msg) {
        super(msg);
    }
}
