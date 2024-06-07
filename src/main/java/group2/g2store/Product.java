/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group2.g2store;

/**
 *
 * @author Luu Bao
 */
public class Product {
    private String ProductID;
    private String ProductName;
    private String ProductBrand;
    private String ProductCategory;
   
    
    //constructor

    public Product(String ProductID, String ProductName, String ProductBrand, String ProductCategory) {
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.ProductBrand = ProductBrand;
        this.ProductCategory = ProductCategory;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String ProductID) {
        this.ProductID = ProductID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public String getProductBrand() {
        return ProductBrand;
    }

    public void setProductBrand(String ProductBrand) {
        this.ProductBrand = ProductBrand;
    }

    public String getProductCategory() {
        return ProductCategory;
    }

    public void setProductCategory(String ProductCategory) {
        this.ProductCategory = ProductCategory;
    }
    
    
    
}
