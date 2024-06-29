package group2.g2store;

import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author Luu Bao
 */
public class ProductQ {

    private String productID;
    private String productName;
    private String productBrand;
    private String productCategory;
    private int productPrice; // Thêm trường giá sản phẩm vào class Product
    private String productImage; // Thêm trường hình ảnh sản phẩm
    private int stockQuantity; // Thêm trường số lượng tồn kho vào class Product
    private int selectedQuantity; // Số lượng sản phẩm được chọn

    public ProductQ(String productName, String productBrand, String productImage, String productID, String productCategory) {
        this.productID = productID;
        this.productName = productName;
        this.productBrand = productBrand;
        this.productImage = productImage;
        this.productCategory = productCategory;
    }

    // Constructor thứ hai với tham số mới
    public ProductQ(String productName, String productBrand, String productCategory, int productPrice, String productImage, String productID) {
        this.productName = productName;
        this.productBrand = productBrand;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.productID = productID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductPrice() {
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        return formatter.format(productPrice);
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getSelectedQuantity() {
        return selectedQuantity;
    }

    public void setSelectedQuantity(int selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }
}
