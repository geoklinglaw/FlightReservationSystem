/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package util.enumeration;

/**
 *
 * @author apple
 */
public enum EmployeePosition {
    FM("Fleet Manager", 0),
    RM("Route Manager", 1),
    SCM("Schedule Manager", 2),
    SAM("Sales Manager", 3),
    SA("System Administrator", 4);
    
    private final String position;
    private final int value;
    
    EmployeePosition(String position, int value) {
        this.position = position;
        this.value = value;
    }

    public String getPosition() {
        return this.position;
    }

    public int getValue() {
        return this.value;
    }

    public static EmployeePosition fromValue(int value) {
        for (EmployeePosition type : EmployeePosition.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("No Employee Position with value " + value);
    }
}
