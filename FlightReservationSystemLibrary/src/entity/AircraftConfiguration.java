/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import util.enumeration.AircraftName;

/**
 *
 * @author apple
 */
@Entity
@NamedQueries({
    @NamedQuery(
        name = "viewAllAircraftConfigurations",
        query = "SELECT acc FROM AircraftConfiguration acc ORDER BY acc.name, acc.aircraftType.name ASC"
    ),
    @NamedQuery(
        name = "retrieveAircraftConfigurationByNameAndStyle",
        query = "SELECT acc FROM AircraftConfiguration acc WHERE acc.name = :name AND acc.aircraftStyle = :style" 
    )

})
public class AircraftConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String aircraftStyle;
    @OneToMany (mappedBy = "aircraftConfig")
    private List<CabinClass> cabinClassList;
    
    @ManyToOne (optional = false)
    private AircraftType aircraftType;
    
    @OneToMany (mappedBy = "aircraftConf")
//    @JoinColumn(nullable = false) JOIN COLUMN IS NOT USED FOR ONETOMANY?
    private List<Flight> flight;
    

    public AircraftConfiguration() {
    }
    
    public AircraftConfiguration(AircraftType type, String aircraftStyle) {
        this.name = type.getName();
        this.cabinClassList = new ArrayList<CabinClass>();
        this.aircraftType = type;
        this.flight = new ArrayList<Flight>();
        this.aircraftStyle = aircraftStyle;

        
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
        if (!(object instanceof AircraftConfiguration)) {
            return false;
        }
        AircraftConfiguration other = (AircraftConfiguration) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AirportConfiguration[ id=" + id + " ]";
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the cabinClassList
     */
    public List<CabinClass> getCabinClassList() {
        return cabinClassList;
    }

    /**
     * @param cabinClassList the cabinClassList to set
     */
    public void setCabinClassList(List<CabinClass> cabinClassList) {
        this.cabinClassList = cabinClassList;
    }

    /**
     * @return the aircraftType
     */
    public AircraftType getAircraftType() {
        return aircraftType;
    }

    /**
     * @param aircraftType the aircraftType to set
     */
    public void setAircraftType(AircraftType aircraftType) {
        this.aircraftType = aircraftType;
    }

    /**
     * @return the flight
     */
    public List<Flight> getFlight() {
        return flight;
    }

    /**
     * @param flight the flight to set
     */
    public void setFlight(List<Flight> flight) {
        this.flight = flight;
    }



    /**
     * @return the aircraftStyle
     */
    public String getAircraftStyle() {
        return aircraftStyle;
    }

    /**
     * @param aircraftStyle the aircraftStyle to set
     */
    public void setAircraftStyle(String aircraftStyle) {
        this.aircraftStyle = aircraftStyle;
    }
    
}
