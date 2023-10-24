/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package util.enumeration;

/**
 *
 * @author apple
 */
public enum FlightType {
    DIRECT(0),
    CONNECTING(1);
    
    private final int value;
    
    FlightType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static FlightType fromValue(int value) {
        for (FlightType type : FlightType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("No Flight Type with value " + value);
    }
}
