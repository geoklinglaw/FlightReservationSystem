/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package util.enumeration;

/**
 *
 * @author apple
 */
public enum FlightStatus {
    DISABLED(0),
    ACTIVE(1);
    
    private final int value;
    
    FlightStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static FlightStatus fromValue(int value) {
        for (FlightStatus type : FlightStatus.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("No Flight Status with value " + value);
    }
    
}
