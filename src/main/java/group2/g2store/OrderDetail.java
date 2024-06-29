/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group2.g2store;

/**
 *
 * @author Dangm
 */
public class OrderDetail {

    private String productID;
    private String productName;
    private int nkID;
    private int soldQuantity;
    private double productPrice;
    private double totalPrice;

    public OrderDetail(String productID, String productName, int nkID, int soldQuantity, double productPrice) {
        this.productID = productID;
        this.productName = productName;
        this.nkID = nkID;
        this.soldQuantity = soldQuantity;
        this.productPrice = productPrice;
        setTotalPrice(); // Tính toán totalPrice ngay khi khởi tạo
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setTotalPrice() {
        this.totalPrice = productPrice * soldQuantity;
    }

    // Các phương thức getter và setter khác nếu cần
    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getNkID() {
        return nkID;
    }

    public void setNkID(int nkID) {
        this.nkID = nkID;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
        setTotalPrice(); // Cập nhật totalPrice khi soldQuantity thay đổi
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
        setTotalPrice(); // Cập nhật totalPrice khi productPrice thay đổi
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String toString() {
        return "ProductID:" + productID +" ,ProductName:"+productName+ ", NKid:" + nkID + ",soldQuantity:" + soldQuantity + ",ProductPrice:" + productPrice + ", totalPrice:" + totalPrice;
    }
}
