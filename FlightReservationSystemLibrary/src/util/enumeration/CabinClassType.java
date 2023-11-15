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
    FIRST_CLASS("F"),
    BUSINESS("J"),
    PREMIUM_ECONOMY("W"),
    ECONOMY("Y");
    
    private final String value;

    CabinClassType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static CabinClassType fromValue(String value) {
        for (CabinClassType type : CabinClassType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No Cabin Class Type with value " + value);
    }
}

