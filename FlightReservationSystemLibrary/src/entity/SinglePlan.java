/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import util.enumeration.FlightScheduleType;

/**
 *
 * @author apple
 */
@Entity
public class SinglePlan extends FlightSchedulePlan implements Serializable {

    private static final long serialVersionUID = 1L;


    public SinglePlan() {
        super();
    }

    public SinglePlan(int type, Flight flight) {
        super(type, flight);
    }
    
    


}
