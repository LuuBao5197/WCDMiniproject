/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group2.g2store;

import java.util.List;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;

/**
 *
 * @author Luu Bao
 */
public class Product {
    private String ProductID;
    private String ProductName;
    private String ProductBrand;
    private String ProductCategory;
    private String  images;
    private String Category;
    private int NKId;
    private int ImportQuantity;
    private int SoldQuantity;
    private int Inventory;
    private float ImportPrice;
    private float SoldPrice;
    
    private String HSD;

    public Product(String ProductID, String ProductName, String ProductBrand, String ProductCategory, String images) {
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.ProductBrand = ProductBrand;
        this.ProductCategory = ProductCategory;
        this.images  = images;
    }

    public Product(String ProductID, String ProductName, int NKId, int ImportQuantity, int SoldQuantity, int Inventory, String Category) {
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.NKId = NKId;
        this.ImportQuantity = ImportQuantity;
        this.SoldQuantity = SoldQuantity;
        this.Inventory = Inventory;
        this.Category = Category;
    }

    public Product(String ProductID, String ProductName, int NKId, int ImportQuantity, int SoldQuantity, float ImportPrice, float SoldPrice, String HSD) {
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.NKId = NKId;
        this.ImportQuantity = ImportQuantity;
        this.SoldQuantity = SoldQuantity;
        this.ImportPrice = ImportPrice;
        this.SoldPrice = SoldPrice;
        this.HSD = HSD;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Product{");
        sb.append("ProductID=").append(ProductID);
        sb.append(", ProductName=").append(ProductName);
        sb.append(", ProductBrand=").append(ProductBrand);
        sb.append(", ProductCategory=").append(ProductCategory);
        sb.append(", images=").append(images);
        sb.append(", Category=").append(Category);
        sb.append(", NKId=").append(NKId);
        sb.append(", ImportQuantity=").append(ImportQuantity);
        sb.append(", SoldQuantity=").append(SoldQuantity);
        sb.append(", Inventory=").append(Inventory);
        sb.append(", ImportPrice=").append(ImportPrice);
        sb.append(", SoldPrice=").append(SoldPrice);
        sb.append(", HSD=").append(HSD);
        sb.append('}');
        return sb.toString();
    }

    public float getImportPrice() {
        return ImportPrice;
    }

    public void setImportPrice(float ImportPrice) {
        this.ImportPrice = ImportPrice;
    }

    public float getSoldPrice() {
        return SoldPrice;
    }

    public void setSoldPrice(float SoldPrice) {
        this.SoldPrice = SoldPrice;
    }

    public String getHSD() {
        return HSD;
    }

    public void setHSD(String HSD) {
        this.HSD = HSD;
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getNKId() {
        return NKId;
    }

    public void setNKId(int NKId) {
        this.NKId = NKId;
    }

    public int getImportQuantity() {
        return ImportQuantity;
    }

    public void setImportQuantity(int ImportQuantity) {
        this.ImportQuantity = ImportQuantity;
    }

    public int getSoldQuantity() {
        return SoldQuantity;
    }

    public void setSoldQuantity(int SoldQuantity) {
        this.SoldQuantity = SoldQuantity;
    }

    public int getInventory() {
        return Inventory;
    }

    public void setInventory(int Inventory) {
        this.Inventory = Inventory;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    
   



 
    
    
    
}
