/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private CabinClassType type;
    private int seatingCapacity;
    private int numAisles;
    private int numRows;
    private int numAbreastSide;
    private int numAbreastCenter;

    public CabinClass() {
    }

    public CabinClass(int type, int seatingCapacity, int numAisles, int numRows, int numAbreastSide, int numAbreastCenter) {
        this.type = CabinClassType.fromValue(type);
        this.seatingCapacity = seatingCapacity;
        this.numAisles = numAisles;
        this.numRows = numRows;
        this.numAbreastSide = numAbreastSide;
        this.numAbreastCenter = numAbreastCenter;
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
