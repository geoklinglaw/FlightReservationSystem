/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.AircraftConfiguration;
import entity.AircraftType;
import entity.CabinClass;
import entity.Fare;
import entity.FlightSchedule;
import entity.Seat;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import util.enumeration.CabinClassType;

/**
 *
 * @author apple
 */
@Stateless
public class FRSManagementSessionBean implements FRSManagementSessionBeanRemote, FRSManagementSessionBeanLocal {

    @EJB(name = "CabinClassSessionBeanLocal")
    private CabinClassSessionBeanLocal cabinClassSessionBeanLocal;

    @EJB(name = "AircraftTypeSessionBeanLocal")
    private AircraftTypeSessionBeanLocal aircraftTypeSessionBeanLocal;

    @EJB
    private AircraftConfigurationSessionBeanLocal aircraftConfigurationSessionBean;
    
    @Override
    public void createAircraftConfiguration(int aircraftType, List<Integer> ccList) {
        
        AircraftType acType = aircraftTypeSessionBeanLocal.retrieveAircraftTypeByValue(aircraftType);
        List<CabinClass> cabinClassList = new ArrayList<CabinClass>();
        
        int seatCapacityForEachCabinClass = acType.getMaxSeatCapacity().intValue() / ccList.size();
        
        // Logic for Assigning Number of Rows
        int numRows;
        int numSRow = 0;

        if (ccList.size() == 1) {
            numRows = 20;
        } else if (ccList.size() == 2) {
            numRows = 10;
        } else if (ccList.size() == 3) {
            numRows = 7;
            numSRow = 6;
        } else {
            numRows = 5;
        }

        
        for (int i = 0; i < ccList.size(); i++) {
            
            if (acType.getName() == "Boeing 737") {
               if (i == ccList.size() - 1 && numSRow != 0) {
                   CabinClass cc = new CabinClass((int) ccList.get(i), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal("2"), new BigDecimal(numSRow), new BigDecimal("6"), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal(seatCapacityForEachCabinClass));
                   cabinClassList.add(cc);
               } else {
                   CabinClass cc = new CabinClass((int) ccList.get(i), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal("2"), new BigDecimal(numRows), new BigDecimal("6"), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal(seatCapacityForEachCabinClass));
                   cabinClassList.add(cc);
               }
            } else {
               if (i == ccList.size() - 1 && numSRow != 0) {
                   CabinClass cc = new CabinClass((int) ccList.get(i), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal("2"), new BigDecimal(numSRow), new BigDecimal("6"), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal(seatCapacityForEachCabinClass));
                   cabinClassList.add(cc);
               } else {
                   CabinClass cc = new CabinClass((int) ccList.get(i), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal("2"), new BigDecimal(numRows), new BigDecimal("6"), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal(seatCapacityForEachCabinClass), new BigDecimal(seatCapacityForEachCabinClass));
                   cabinClassList.add(cc);
               }
            }
        }
        
        AircraftConfiguration aircraftConfig = new AircraftConfiguration(acType);
        aircraftConfig.setAircraftType(acType);
        aircraftConfig.setCabinClassList(cabinClassList);
        
        for (CabinClass cc: cabinClassList) {
            cc.setAircraftConfig(aircraftConfig);
            cabinClassSessionBeanLocal.createNewCabinClass(cc);
        }
            

        Long acConfig = aircraftConfigurationSessionBean.createNewAircraftConfiguration(aircraftConfig);   
    }
    
    public List<AircraftConfiguration> viewAllAircraftConfiguration() {
        List<AircraftConfiguration> aircraftConfigList = aircraftConfigurationSessionBean.viewAllAircraftConfiguration();
        return aircraftConfigList;
    }


}
