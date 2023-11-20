/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package util.exception;

/**
 *
 * @author apple
 */
public class FlightSchedulePlanExists extends Exception {

    /**
     * Creates a new instance of <code>FlightSchedulePlanExists</code> without
     * detail message.
     */
    public FlightSchedulePlanExists() {
    }

    /**
     * Constructs an instance of <code>FlightSchedulePlanExists</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public FlightSchedulePlanExists(String msg) {
        super(msg);
    }
}
