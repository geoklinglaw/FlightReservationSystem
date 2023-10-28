/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import util.enumeration.CabinClassType;

/**
 *
 * @author apple
 */
@Entity
public class CabinClass implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private CabinClassType type;
    @Column(nullable = false)
    private BigDecimal seatingCapacity;
    @Column(nullable = false)
    private BigDecimal numAisles;
    @Column(nullable = false)
    private BigDecimal numRows;
    @Column(nullable = false)
    private BigDecimal numAbreastSide;
    @Column(nullable = false)
    private BigDecimal numAbreastCenter;
    @Column(nullable = false)
    private BigDecimal numAvailableSeats;
    @Column(nullable = false)
    private BigDecimal numReservedSeats;
    @Column(nullable = false)
    private BigDecimal numBalanceSeats;
    
    @ManyToOne 
    private FlightSchedule flightSchedule;
    
    public CabinClass() {
    }

    public CabinClass(int type, BigDecimal seatingCapacity, BigDecimal numAisles, BigDecimal numRows, BigDecimal numAbreastSide, BigDecimal numAbreastCenter, BigDecimal numAvailableSeats, BigDecimal numReservedSeats, BigDecimal numBalanceSeats) {
        this.type = CabinClassType.fromValue(type);
        this.seatingCapacity = seatingCapacity;
        this.numAisles = numAisles;
        this.numRows = numRows;
        this.numAbreastSide = numAbreastSide;
        this.numAbreastCenter = numAbreastCenter;
        this.numAvailableSeats = numAvailableSeats;
        this.numBalanceSeats = numBalanceSeats;
        this.numReservedSeats = numReservedSeats;
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
        if (!(object instanceof CabinClass)) {
            return false;
        }
        CabinClass other = (CabinClass) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CabinClass[ id=" + id + " ]";
    }
    
}
