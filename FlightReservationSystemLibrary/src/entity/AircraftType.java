/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import util.enumeration.AircraftName;

/**
 *
 * @author apple
 */
@Entity
public class AircraftType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private AircraftName name;
    @Column(nullable = false)
    private BigDecimal maxSeatCapacity;
    
    @OneToMany
//    @JoinColumn(nullable = false)
    private List<AircraftConfiguration> aircraftConfig;
    
    public static final String B737_SEATCAPACITY = "120"; // 20 rows of 3-3
    public static final String B747_SEATCAPACITY = "180"; // 20 rows of 3-3-3

    public AircraftType() {
    }

    public AircraftType(int aircraftValue) {
        this.name = AircraftName.fromValue(aircraftValue);
        if (this.name == AircraftName.B737) {
            BigDecimal seatCap = new BigDecimal(B737_SEATCAPACITY);
            this.maxSeatCapacity = seatCap;
        } else {
            BigDecimal seatCap = new BigDecimal(B747_SEATCAPACITY);
            this.maxSeatCapacity = seatCap;
        }
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
        if (!(object instanceof AircraftType)) {
            return false;
        }
        AircraftType other = (AircraftType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AircraftType[ id=" + id + " ]";
    }

    /**
     * @return the name
     */
    public String getName() {
        return name.getName();
    }

    /**
     * @param name the name to set
     */
    public void setName(AircraftName name) {
        this.name = name;
    }

    /**
     * @return the maxSeatCapacity
     */
    public BigDecimal getMaxSeatCapacity() {
        return maxSeatCapacity;
    }

    /**
     * @param maxSeatCapacity the maxSeatCapacity to set
     */
    public void setMaxSeatCapacity(BigDecimal maxSeatCapacity) {
        this.maxSeatCapacity = maxSeatCapacity;
    }
    
}
