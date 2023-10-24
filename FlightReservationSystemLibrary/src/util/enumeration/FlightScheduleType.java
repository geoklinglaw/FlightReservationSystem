/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package util.enumeration;

/**
 *
 * @author apple
 */
public enum FlightScheduleType {
    SINGLE(0),
    MULTIPLE(1),
    RECURRENT(2);
    
    private final int value;
    
    FlightScheduleType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static FlightScheduleType fromValue(int value) {
        for (FlightScheduleType status : FlightScheduleType.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("No FlightScheduleType with value " + value);
    }
}
