/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import util.enumeration.FlightStatus;
import util.enumeration.FlightType;

/**
 *
 * @author apple
 */
@Entity
public class Flight implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String flightNumber;
    private FlightType type;
    private Date deptDate;
    private Time deptTime;
    private Date returnDate;
    private Time returnTime;
    private FlightStatus status;

    public Flight() {
    }

    public Flight(String flightNumber, FlightType type, Date deptDate, Time deptTime, Date returnDate, Time returnTime, int status) {
        this.flightNumber = flightNumber;
        this.type = type;
        this.deptDate = deptDate;
        this.deptTime = deptTime;
        this.returnDate = returnDate;
        this.returnTime = returnTime;
        this.status = FlightStatus.fromValue(status);
        
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
        if (!(object instanceof Flight)) {
            return false;
        }
        Flight other = (Flight) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Flight[ id=" + id + " ]";
    }
    
}
