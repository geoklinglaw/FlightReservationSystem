/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package util.enumeration;

/**
 *
 * @author apple
 */
public enum CabinClassType {
    FIRST(0),
    BUSINESS(1),
    PREMIUM_ECONOMY(2),
    ECONOMY(3);
    
    private final int value;

    CabinClassType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static CabinClassType fromValue(int value) {
        for (CabinClassType type : CabinClassType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("No Cabin Class Type with value " + value);
    }
}

