/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import util.enumeration.PersonRoleType;

/**
 *
 * @author apple
 */
@Entity
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 30)
    private String firstName;
    @Column(nullable = false, length = 30)
    private String lastName;
    @Column(nullable = true, length = 50)
    private String email;
    @Column(nullable = true)
    private String contactNum;
    @Column(nullable = true, length = 100)
    private String address;
    @Column(nullable = true, length = 30)
    private String username;
    @Column(nullable = true, length = 30)
    private String password;
    @Column(nullable = false)
    private PersonRoleType role;
    
    @OneToMany (mappedBy= "person")
    private List<FlightReservation> flightReservations;
    

    public Person() {
    }

    
    //for customer log in
    public Person(String firstName, String lastName, String username, String password, int role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = PersonRoleType.fromValue(role);
    }
    
    //for visitor registration
    public Person(String firstName, String lastName, String email, String contactNum, String address, int role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNum = contactNum;
        this.address = address;
        this.role = PersonRoleType.fromValue(role);
    }

    public Person(String firstName, String lastName, String email, String contactNum, String address, String username, String password, PersonRoleType role, List<FlightReservation> flightReservations) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNum = contactNum;
        this.address = address;
        this.username = username;
        this.password = password;
        this.role = role;
        this.flightReservations = flightReservations;
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
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Person[ id=" + id + " ]";
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the role
     */
    public PersonRoleType getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(PersonRoleType role) {
        this.role = role;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the flightReservations
     */
    public List<FlightReservation> getFlightReservations() {
        return flightReservations;
    }

    /**
     * @param flightReservations the flightReservations to set
     */
    public void setFlightReservations(List<FlightReservation> flightReservations) {
        this.flightReservations = flightReservations;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the contactNum
     */
    public String getContactNum() {
        return contactNum;
    }

    /**
     * @param contactNum the contactNum to set
     */
    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
}
