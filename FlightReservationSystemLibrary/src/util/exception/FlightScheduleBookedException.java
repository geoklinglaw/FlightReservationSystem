/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package util.exception;

/**
 *
 * @author apple
 */
public class FlightScheduleBookedException extends Exception {

    /**
     * Creates a new instance of <code>FlightScheduleBookedException</code>
     * without detail message.
     */
    public FlightScheduleBookedException() {
    }

    /**
     * Constructs an instance of <code>FlightScheduleBookedException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public FlightScheduleBookedException(String msg) {
        super(msg);
    }
}
