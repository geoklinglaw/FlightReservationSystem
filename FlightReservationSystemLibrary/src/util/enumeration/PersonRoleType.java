/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package util.enumeration;

/**
 *
 * @author apple
 */
public enum PersonRoleType {
    VISITOR(0),
    CUSTOMER(1);
    
    private final int value;
    
    PersonRoleType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static PersonRoleType fromValue(int value) {
        for (PersonRoleType type : PersonRoleType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("No Role Type with value " + value);
    }
}
