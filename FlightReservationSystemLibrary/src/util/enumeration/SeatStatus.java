/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package util.enumeration;

/**
 *
 * @author apple
 */
public enum SeatStatus {
    AVAILABLE(0),
    SELECTED(1),
    RESERVED(2);
    
    private final int value;
    
    SeatStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static SeatStatus fromValue(int value) {
        for (SeatStatus status : SeatStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("No SeatStatus with value " + value);
    }
}