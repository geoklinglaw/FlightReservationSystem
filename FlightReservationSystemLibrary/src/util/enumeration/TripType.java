/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package util.enumeration;

/**
 *
 * @author apple
 */
public enum TripType {
    ONEWAY(0),
    TWOWAY(1);
    
    private final int value;
    
    TripType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static TripType fromValue(int value) {
        for (TripType type : TripType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("No TripType with value " + value);
    }
}