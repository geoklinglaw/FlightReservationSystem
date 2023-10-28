/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
public class RecurrentPlan extends FlightSchedulePlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private FlightScheduleType type;
    private List<FlightSchedule> flightList;
    private Flight flight;
    private Date endDate;
    private BigDecimal frequency;

    public RecurrentPlan() {
        super();
    }


    public RecurrentPlan(int type, Flight flight, Date endDate, BigDecimal frequency) {
        super(type, flight);
        this.endDate = endDate;
        this.frequency = frequency;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecurrentPlan)) {
            return false;
        }
        RecurrentPlan other = (RecurrentPlan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RecurrentPlan[ id=" + id + " ]";
    }
    
}
