/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group2.g2store;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Orders {
    private final SimpleStringProperty orderId;
    private final SimpleStringProperty customerPhone;
    private final SimpleStringProperty datetimeOrder;
    private final SimpleStringProperty empId;
    private final SimpleDoubleProperty total;

    public Orders(String orderId, String customerPhone, String datetimeOrder, String empId, double total) {
        this.orderId = new SimpleStringProperty(orderId);
        this.customerPhone = new SimpleStringProperty(customerPhone);
        this.datetimeOrder = new SimpleStringProperty(datetimeOrder);
        this.empId = new SimpleStringProperty(empId);
        this.total = new SimpleDoubleProperty(total);
    }

    public String getOrderId() {
        return orderId.get();
    }

    public void setOrderId(String orderId) {
        this.orderId.set(orderId);
    }

    public SimpleStringProperty orderIdProperty() {
        return orderId;
    }

    public String getCustomerPhone() {
        return customerPhone.get();
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone.set(customerPhone);
    }

    public SimpleStringProperty customerPhoneProperty() {
        return customerPhone;
    }

    public String getDatetimeOrder() {
        return datetimeOrder.get();
    }

    public void setDatetimeOrder(String datetimeOrder) {
        this.datetimeOrder.set(datetimeOrder);
    }

    public SimpleStringProperty datetimeOrderProperty() {
        return datetimeOrder;
    }

    public String getEmpId() {
        return empId.get();
    }

    public void setEmpId(String empId) {
        this.empId.set(empId);
    }

    public SimpleStringProperty empIdProperty() {
        return empId;
    }

    public double getTotal() {
        return total.get();
    }

    public void setTotal(double total) {
        this.total.set(total);
    }

    public SimpleDoubleProperty totalProperty() {
        return total;
    }
}
