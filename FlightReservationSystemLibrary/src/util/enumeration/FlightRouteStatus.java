/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package util.enumeration;

/**
 *
 * @author apple
 */
public enum FlightRouteStatus {
    DISABLED(0),
    ACTIVE(1);
    
    private final int value;
    
    FlightRouteStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static FlightRouteStatus fromValue(int value) {
        for (FlightRouteStatus type : FlightRouteStatus.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("No FlightRouteStatus with value " + value);
    }
    
}
