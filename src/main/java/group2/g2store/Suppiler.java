/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group2.g2store;

/**
 *
 * @author VICTUS
 */
public class Suppiler {
    private Integer SuppilerCode;
    private String  SuppilerName, address, email, phoneNumber;

    public Suppiler() {
    }

    public Suppiler(Integer SuppilerCode, String SuppilerName, String address, String email, String phoneNumber) {
        this.SuppilerCode = SuppilerCode;
        this.SuppilerName = SuppilerName;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Integer getSuppilerCode() {
        return SuppilerCode;
    }

    public String getSuppilerName() {
        return SuppilerName;
    }

   

    public String getAddress() {
        return address;
    } 
    
    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setSuppilerCode(Integer SuppilerCode) {
        this.SuppilerCode = SuppilerCode;
    }

    public void setSuppilerName(String SuppilerName) {
        this.SuppilerName = SuppilerName;
    }

    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Suppiler{" + "SuppilerCode=" + SuppilerCode + ", SuppilerName=" + SuppilerName + ", address=" + address + ", email=" + email + ", phoneNumber=" + phoneNumber + '}';
    }

   

    
    
}
