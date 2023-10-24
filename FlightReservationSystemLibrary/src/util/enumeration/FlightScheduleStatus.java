/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package util.enumeration;

/**
 *
 * @author apple
 */
public enum FlightScheduleStatus {
    DISABLED(0),
    ACTIVE(1);
    
    private final int value;
    
    FlightScheduleStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static FlightScheduleStatus fromValue(int value) {
        for (FlightScheduleStatus type : FlightScheduleStatus.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("No FlightScheduleStatus with value " + value);
    }
    
}
