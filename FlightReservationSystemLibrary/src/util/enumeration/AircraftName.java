/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package util.enumeration;

/**
 *
 * @author apple
 */
public enum AircraftName {
    B737("Boeing 737", 0),
    B747("Boeing 747", 1);

    private final String name;
    private final int value;

    AircraftName(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public int getValue() {
        return this.value;
    }

    public static AircraftName fromValue(int value) {
        for (AircraftName type : AircraftName.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("No Aircraft Name with value " + value);
    }
}

