/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import util.enumeration.FlightScheduleType;

/**
 *
 * @author apple
 */
@Entity
public class FlightSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private FlightScheduleType type;
    @Column(nullable = false)
    private Date departureTime;
    @Column(nullable = false)
    private Duration flightDuration;
    @Column(nullable = false)
    private Date arrivalTime;
    
    
    @ManyToOne (optional = false)
    private FlightSchedulePlan flightSchedulePlan;
    
    @OneToMany (mappedBy = "flightSchedule")
    private List<CabinClass> cabinClass;
    
    @OneToMany
//    @JoinColumn(nullable = false)
    private List<FlightBooking> flightBookings;

    public FlightSchedule() {
    }

    public FlightSchedule(int type, FlightSchedulePlan flightSchedulePlan, List<CabinClass> cabinClassList) {
        this.type = FlightScheduleType.fromValue(type);
        this.flightSchedulePlan = flightSchedulePlan;
        this.cabinClass = cabinClassList;
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
        if (!(object instanceof FlightSchedule)) {
            return false;
        }
        FlightSchedule other = (FlightSchedule) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FlightSchedule[ id=" + id + " ]";
    }
    
    public FlightScheduleType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(FlightScheduleType type) {
        this.type = type;
    }
    
}
