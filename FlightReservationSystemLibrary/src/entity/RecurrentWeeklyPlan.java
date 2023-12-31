/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author apple
 */
@Entity
public class RecurrentWeeklyPlan extends FlightSchedulePlan implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(nullable = false)
    private Date endDate;
    @Column(nullable = false)
    private BigDecimal dayOfWeek;
    
    public RecurrentWeeklyPlan() {
    }

    
    public RecurrentWeeklyPlan(int type, Flight flight, Date endDate, BigDecimal dayOfWeek) {
        super(type, flight);
        this.endDate = endDate;
        this.dayOfWeek = dayOfWeek;
    }


    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the frequency
     */
    public BigDecimal getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * @param frequency the frequency to set
     */
    public void setDayOfWeek(BigDecimal frequency) {
        this.dayOfWeek = frequency;
    }
    
}
