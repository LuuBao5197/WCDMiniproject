/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group2.g2store;

/**
 *
 * @author VICTUS
 */
public class Customer {
    private String customerId, customerName, customerPhone, Dob;
    private int  Point;

    public Customer() {
    }

    public Customer(String customerId, String customerName, String customerPhone, String Dob, int Point) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.Dob = Dob;
        this.Point = Point;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getDob() {
        return Dob;
    }

    public int getPoint() {
        return Point;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public void setDob(String Dob) {
        this.Dob = Dob;
    }

    public void setPoint(int Point) {
        this.Point = Point;
    }

    @Override
    public String toString() {
        return "Customer{" + "customerId=" + customerId + ", customerName=" + customerName + ", customerPhone=" + customerPhone + ", Dob=" + Dob + ", Point=" + Point + '}';
    }

    
}
