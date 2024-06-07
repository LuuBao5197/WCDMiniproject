/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package group2.g2store;

import java.io.Console;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author Luu Bao
 */
public class ProductController implements Initializable {

    @FXML
    private Button btnHome;
    @FXML
    private TableView<Product> tbProductView;
    @FXML
    private TableColumn<Product, String> ProductID;
    @FXML
    private TableColumn<Product, String> ProductName;
    @FXML
    private TableColumn<Product, String> ProductBrand;
    @FXML
    private TableColumn<Product, String> ProductCategory;

    private Connection con;
    private Statement stm;
    private ResultSet rs;
    private ObservableList<Product> ProductList;
    @FXML
    private TableColumn<Product, Integer> stt;
    @FXML
    private Button BtnAdd;
    Dialog<Product> dialog = new Dialog<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // ==================DIALOG THEM SAN PHAM =================
       
        dialog.setTitle("Add Product");
        dialog.setHeaderText("Thêm sản phẩm");

       
        ButtonType addButtonType = new ButtonType("Thêm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField productID = new TextField();
        productID.setPromptText("ProductID");
        TextField productName = new TextField();
        productName.setPromptText("Product Name");
        TextField productBrand = new TextField();
        productBrand.setPromptText("Brand");
        TextField productCat = new TextField();
        productCat.setPromptText("Cat");
        grid.add(new Label("ProductID"), 0, 0);
        grid.add(productID, 1, 0);
        grid.add(new Label("ProductName"), 0, 1);
        grid.add(productName, 1, 1);
        grid.add(new Label("Brand"), 0, 2);
        grid.add(productBrand, 1, 2);
        grid.add(new Label("Category"), 0, 3);
        grid.add(productCat, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Product(productID.getText(), productName.getText(), productBrand.getText(), productCat.getText());
            }
            return null;

        });

        // END DIALOG THEM SAN PHAM
        // TODO
        ConnectDB cnDB = new ConnectDB();
        con = cnDB.getConnect();
        String sql = "SELECT P.ProductId, P.ProductName, B.BrandName, C.CategoryName\n"
                + "FROM \n"
                + "Product P JOIN Brand B ON P.BrandId = B.BrandId\n"
                + "			JOIN Category C ON p.CategoryId = C.CategoryId\n"
                + "					";
        try {
            // Tạo satement
            stm = con.createStatement();
            // truy vấn
            rs = stm.executeQuery(sql);

            // tạo observableList để lưu dữ liệu từ ResultSet
            ProductList = FXCollections.observableArrayList();

            ProductList.clear();
            int index = 1; // bien de luu STT
            while (rs.next()) {
                String ProductID = rs.getString("ProductID");

                String ProductName = rs.getString("ProductName");

                String ProductBrand = rs.getString("BrandName");

                String ProductCategory = rs.getString("CategoryName");

                ProductList.add(new Product(ProductID, ProductName, ProductBrand, ProductCategory));
                index++;
            }
            tbProductView.setItems(ProductList);

            // thiet lap gia tri cho cac cot
            stt.setCellValueFactory(data -> {
                Product product = data.getValue();
                int rowIndex = tbProductView.getItems().indexOf(product) + 1;
                return new SimpleIntegerProperty(rowIndex).asObject();

            });

            ProductID.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProductID()));
            ProductName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProductName()));
            ProductBrand.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProductBrand()));
            ProductCategory.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProductCategory()));

            tbProductView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    // Handle row selection, for example, show detail information
                    System.out.println("Khong lay duoc du lieu");
                }
            });

        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleHome(MouseEvent event) {
    }

    @FXML
    // HAM BAT DIALOG VA XU LY THEM SAN PHAM 
    private void handleAddProduct(MouseEvent event) {

        Optional<Product> result = dialog.showAndWait();

        result.ifPresent(item -> {
            String sql = "Insert INTO PRODUCT VALUES (?,?,?,?,?)";
            ConnectDB cnDB = new ConnectDB();
            con = cnDB.getConnect();
            PreparedStatement ps;
            try {
               ps = con.prepareStatement(sql);
               ps.setString(1, item.getProductID());
               ps.setString(2, item.getProductName());
                 ps.setString(3, "P051.jpg");
               ps.setInt(4,1);
               ps.setInt(5, 1);
                if (ps.executeUpdate() != 1) {
                    System.out.println("Khong them duoc san pham");
                    
                 

                } else {
                    System.out.println("Them san pham thanh cong");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("KET QUA");
                    alert.setContentText("Them san pham thanh cong");
                    alert.show();
                }
            } catch (SQLException ex) {
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Canh bao");
                    alert.setContentText("Khong them duoc san pham vi " + ex.getMessage());
                    alert.setResizable(true);
                    alert.show();
            }

        });

    }

}
