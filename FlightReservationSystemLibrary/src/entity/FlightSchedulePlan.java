/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
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
public class FlightSchedulePlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private FlightScheduleType type;
    private List<FlightSchedule> flightList;

    public FlightSchedulePlan() {
    }

    public FlightSchedulePlan(int type, List<FlightSchedule> flightList) {
        this.type = FlightScheduleType.fromValue(type);
        this.flightList = flightList;
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
        if (!(object instanceof FlightSchedulePlan)) {
            return false;
        }
        FlightSchedulePlan other = (FlightSchedulePlan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FlightSchedulePlan[ id=" + id + " ]";
    }

    /**
     * @return the type
     */
    public FlightScheduleType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(FlightScheduleType type) {
        this.type = type;
    }

    /**
     * @return the flightList
     */
    public List<FlightSchedule> getFlightList() {
        return flightList;
    }

    /**
     * @param flightList the flightList to set
     */
    public void setFlightList(List<FlightSchedule> flightList) {
        this.flightList = flightList;
    }


    
}
