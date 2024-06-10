/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package group2.g2store;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
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
    ConnectDB cnDB;
    private Connection con;
    private Statement stm;
    private ResultSet rs;
    private ObservableList<Product> ProductList;
    @FXML
    private TableColumn<Product, Integer> stt;
    Dialog<Product> dialog = new Dialog<>();
    Dialog<Product> dialogEditProduct;
    @FXML
    private TableColumn<Product, String> imageColumn;
    private AnchorPane APProduct;

    private File SelectedImageFile = null;

    private ObservableList<Pair<String, Integer>> brandList;
    private ObservableList<Pair<String, Integer>> CatList;
    Dialog<String> dialogAddBrand;
    ComboBox<Pair<String, Integer>> cbCategoryAddProduct;
    ComboBox<Pair<String, Integer>> cbCategoryEditProduct;

    ComboBox<Pair<String, Integer>> cbBrandAddProduct;
    ComboBox<Pair<String, Integer>> cbBrandEditProduct;
    Dialog<String> dialogAddCategory;
    @FXML
    private Button btnEditProduct;
    private Product SelectProduct = null;
    @FXML
    private TextField tfSearchProduct;
    @FXML
    private Button btnRefresh;
    @FXML
    private ComboBox<Pair<String, Integer>> cbFilterCategory;
    @FXML
    private Button BtnAdd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cnDB = new ConnectDB();
        con = cnDB.getConnect();
        showProducts();
        showDialogAddProduct();
        tfSearchProduct.setOnKeyReleased(event -> {
            String keyword = tfSearchProduct.getText().trim();
//            filterCategory.setValue(null); // Reset giá trị của filterCategory
//            filterBrand.setValue(null); // Reset giá trị của filterBrand
            searchProductsbyName(keyword);
        });
        cbFilterCategory.setItems(getCategory());
        cbFilterCategory.setOnAction(event -> {
            String keyword = cbFilterCategory.getValue().getKey();
            searchProductsbyCategory(keyword);
        });
        btnRefresh.setOnAction(event -> {
            showProducts();
            tbProductView.scrollTo(0);
        });

    }

//    ================================== XEM SAN PHAM VA THEM SAN PHAM MOI
    public Dialog<Product> showDialogAddProduct() {
        // ==================DIALOG THEM SAN PHAM =================
        dialog.setTitle("Thêm sản phẩm");

        ButtonType addButtonType = new ButtonType("Thêm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();

        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(50, 150, 50, 50));
        TextField productID = new TextField();
        productID.setPromptText("ProductID");
        TextField productName = new TextField();
        productName.setPromptText("Product Name");
        TextField productBrand = new TextField();
        productBrand.setPromptText("Brand");
        TextField productCat = new TextField();

        TextField productImage = new TextField();
        cbBrandAddProduct = new ComboBox();
        productImage.setPromptText("URL Image");
//        cbBrand.setItems();
        cbBrandAddProduct.setItems(getBrand());
        cbBrandAddProduct.setOnAction((event) -> {
            productBrand.setText(cbBrandAddProduct.getValue().getValue() + "");
        });
        Button btnAddBrand = new Button("Thêm thương hiệu");
        Button btnAddCategory = new Button("Thêm danh mục sản phẩm");
        cbCategoryAddProduct = new ComboBox<>();
        cbCategoryAddProduct.setItems(getCategory());

        cbCategoryAddProduct.setOnAction((event) -> {
            productCat.setText(cbCategoryAddProduct.getValue().getValue() + "");
        });

        Button ChooseImage = new Button("Chon anh");

        FileChooser fc = new FileChooser();
        fc.setTitle("Choose a image");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        fc.getExtensionFilters().add(imageFilter);

        ImageView imv = new ImageView();

        productCat.setPromptText("Cat");
        grid.add(new Label("ProductID"), 0, 0);
        grid.add(productID, 1, 0);
        grid.add(new Label("ProductName"), 0, 1);
        grid.add(productName, 1, 1);
        grid.add(new Label("Brand"), 0, 2);
        grid.add(productBrand, 1, 2);
        grid.add(cbBrandAddProduct, 2, 2);
        grid.add(btnAddBrand, 3, 2);
        grid.add(new Label("Category"), 0, 3);
        grid.add(productCat, 1, 3);
        grid.add(cbCategoryAddProduct, 2, 3);
        grid.add(btnAddCategory, 3, 3);
        grid.add(new Label("Image"), 0, 4);
        grid.add(productImage, 1, 4);
        grid.add(imv, 2, 4);
        grid.add(ChooseImage, 3, 4);

        btnAddBrand.setOnAction((event) -> {
            showDialogAddBrand();
        });

        btnAddCategory.setOnAction((event) -> {
            showDialogAddCategory();
        });

        ChooseImage.setOnAction(event -> {
            // xu ly su kien khi nut chon anh  duoc click
            File file = fc.showOpenDialog(APProduct.getScene().getWindow());
            if (file != null) {
                SelectedImageFile = file;

                Image image = new Image(file.toURI().toString(), 200, 150, false, true);
                imv.setImage(image);

                String fileName = file.getName();
                productImage.setText(fileName);

            }

        });
//        dialog.getDialogPane().setContent(grid);
        dialog.setResizable(true);
//        DialogPane dialogPane = dialog.getDialogPane();
        dialog.getDialogPane().setMinWidth(800);
        // thiet lap backgroynd cho dialog
        dialog.getDialogPane().setStyle("-fx-background-color: lightblue;");

        dialog.getDialogPane().setMinHeight(500);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                // xu ly su kien chuyen anh duoc chon den thu muc chua file anh san pham cua du an

                return new Product(productID.getText(), productName.getText(), productBrand.getText(), productCat.getText(), productImage.getText());
            }

            return null;
        });

        return dialog;
        // END DIALOG THEM SAN PHAM
        // TODO
    }

    public ObservableList<Product> getProducts() {

        String sql = "SELECT P.ProductId, P.ProductName, B.BrandName, C.CategoryName, P.ProductImage\n"
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

                String ProductImage = rs.getString("ProductImage");

                ProductList.add(new Product(ProductID, ProductName, ProductBrand, ProductCategory, ProductImage));
                index++;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ProductList;
    }

    public void showProducts() {
        ObservableList<Product> list = getProducts();
        // thiet lap gia tri cho cot so thu tu
        stt.setCellValueFactory(data -> {
            Product product = data.getValue();
            int rowIndex = tbProductView.getItems().indexOf(product) + 1;
            return new SimpleIntegerProperty(rowIndex).asObject();

        });

        tbProductView.setItems(list);
// thiet lap gia tri cho cac cot
        ProductID.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProductID()));
        ProductName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProductName()));
        ProductBrand.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProductBrand()));
        ProductCategory.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProductCategory()));

        imageColumn.setCellValueFactory(new PropertyValueFactory<>("images"));

        imageColumn.setCellFactory(param -> new TableCell<Product, String>() {
            private final ImageView imageView = new ImageView();

            {
                imageView.setFitWidth(100); // Cài đặt chiều rộng hình ảnh
                imageView.setFitHeight(100); // Cài đặt chiều cao hình ảnh
//                      
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    try {

                        String imagePath = "src\\main\\resources\\group2\\imageProduct\\" + item;

                        Image image = new Image(new File(imagePath).toURI().toString());
                        imageView.setImage(image);
                        setGraphic(imageView);
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Lỗi thao tác chọn ảnh");
                        alert.setContentText(e.getMessage());
                        setGraphic(null);
                    }
                }
            }
        });
        handleSelectProduct();

    }

    public void searchProductsbyName(String keyword) {

        List<Product> pList;

        pList = getProducts().stream().filter(p -> p.getProductName().contains(keyword)).collect(Collectors.toList());
        ProductList = FXCollections.observableArrayList(pList);
        tbProductView.setItems(ProductList);
    }

    public void searchProductsbyCategory(String keyword) {

        List<Product> pList;

        pList = getProducts().stream().filter(p -> p.getProductCategory().contains(keyword)).collect(Collectors.toList());
        ProductList = FXCollections.observableArrayList(pList);
        tbProductView.setItems(ProductList);
    }

    public void handleSelectProduct() {

        tbProductView.getSelectionModel()
                .selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {

                        SelectProduct = newSelection;

                        btnEditProduct.setDisable(false);

                    }

                });

    }

    @FXML
    private void handleHome(MouseEvent event) {

    }

// HAM BAT DIALOG VA XU LY THEM SAN PHAM 
    @FXML
    private void handleAddProduct(MouseEvent event) {
        Optional<Product> result = dialog.showAndWait();

        result.ifPresent(item -> {
            String sql = "Insert INTO PRODUCT VALUES (?,?,?,?,?)";
            PreparedStatement ps;
            try {
                ps = con.prepareStatement(sql);
                ps.setString(1, item.getProductID());
                ps.setString(2, item.getProductName());
                ps.setString(3, item.getImages());
                ps.setInt(4, Integer.parseInt(item.getProductBrand()));
                ps.setInt(5, Integer.parseInt(item.getProductCategory()));

                if (ps.executeUpdate() != 1) {
                    System.out.println("Khong them duoc san pham");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("KET QUA");
                    alert.setContentText("Khong them duoc san pham");
                    alert.show();
                } else {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("KET QUA");
                    alert.setContentText("Them san pham thanh cong");
                    if (SelectedImageFile != null) {
                        String fileName = SelectedImageFile.getName();
                        Path targetPath = Paths.get("src/main/resources/group2/imageProduct/", fileName);
                        try {
                            Files.move(SelectedImageFile.toPath(), targetPath);
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    alert.show();

                    showProducts();

                }
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Canh bao");
                alert.setContentText("Khong them duoc san pham vi " + ex.getMessage());
                alert.setResizable(true);
                alert.show();
            }

        }
        );

    }

    public ObservableList<Pair<String, Integer>> getBrand() {
        String sql = "SELECT * from Brand";

        try {
            // Tạo satement
            stm = con.createStatement();
            // truy vấn
            rs = stm.executeQuery(sql);

            // tạo observableList để lưu dữ liệu từ ResultSet
            brandList = FXCollections.observableArrayList();

            brandList.clear();

            while (rs.next()) {
                int BrandId = rs.getInt("BrandID");
                String BrandName = rs.getString("BrandName");

                Pair<String, Integer> brandItem = new Pair<>(BrandName, BrandId);
                brandList.add(brandItem);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return brandList;
    }

    public ObservableList<Pair<String, Integer>> getCategory() {
        String sql = "SELECT * from Category";

        try {
            // Tạo satement
            stm = con.createStatement();
            // truy vấn
            rs = stm.executeQuery(sql);

            // tạo observableList để lưu dữ liệu từ ResultSet
            CatList = FXCollections.observableArrayList();

            CatList.clear();

            while (rs.next()) {
                int CategoryId = rs.getInt("CategoryID");
                String CategoryName = rs.getString("CategoryName");

                Pair<String, Integer> CatItem = new Pair<>(CategoryName, CategoryId);
                CatList.add(CatItem);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return CatList;
    }

    public void showDialogAddBrand() {
        dialogAddBrand = new Dialog<>();
        dialogAddBrand.setTitle("Them thuong hieu");
        ButtonType addButtonType = new ButtonType("Thêm", ButtonBar.ButtonData.OK_DONE);
        dialogAddBrand.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();

        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(50, 150, 50, 50));

        TextField tfBrandName = new TextField();
        tfBrandName.setPromptText("Nhap ten thuong hieu");
        grid.add(new Label("Ten thuong hieu"), 0, 0);
        grid.add(tfBrandName, 1, 0);
        dialogAddBrand.getDialogPane().setMinHeight(300);
        dialogAddBrand.getDialogPane().setMinWidth(500);
        dialogAddBrand.setResizable(true);
        dialogAddBrand.setGraphic(grid);

        dialogAddBrand.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                String BrandName;
                BrandName = tfBrandName.getText();
                addBrand(BrandName);

            }
            return null;

        });
        dialogAddBrand.show();

    }

    public void addBrand(String BrandName) {
        String sql = "INSERT INTO Brand VALUES (" + " ' " + BrandName + " ' )";

        try {
            // Tạo satement
            stm = con.createStatement();
            // truy vấn
            if (stm.executeUpdate(sql) != 1) {
                System.out.println("Khong them duoc san pham");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("KET QUA");
                alert.setContentText("Khong them duoc thuong hieu");
                alert.show();
            } else {
                cbBrandAddProduct.setItems(getBrand());
                cbBrandEditProduct.setItems(getBrand());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("KET QUA");
                alert.setContentText("Them thuong hieu thanh cong");
                alert.show();

            }

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Canh bao");
            alert.setContentText("Khong them duoc san pham vi " + ex.getMessage());
            alert.setResizable(true);
            alert.show();
        }

    }

    public void showDialogAddCategory() {
        dialogAddCategory = new Dialog<>();
        dialogAddCategory.setTitle("Thêm danh mục sản phẩm");
        ButtonType addButtonType = new ButtonType("Thêm", ButtonBar.ButtonData.OK_DONE);
        dialogAddCategory.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();

        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(50, 150, 50, 50));

        TextField tfCategoryName = new TextField();
        tfCategoryName.setPromptText("Nhập tên danh mục sản phẩm");
        grid.add(new Label("Tên danh mục"), 0, 0);
        grid.add(tfCategoryName, 1, 0);
        dialogAddCategory.getDialogPane().setMinHeight(300);
        dialogAddCategory.getDialogPane().setMinWidth(500);
        dialogAddCategory.setResizable(true);
        dialogAddCategory.setGraphic(grid);

        dialogAddCategory.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                String CategoryName;
                CategoryName = tfCategoryName.getText();
                addCategory(CategoryName);

            }
            return null;

        });
        dialogAddCategory.show();

    }

    private void addCategory(String CategoryName) {
        String sql = "INSERT INTO Category VALUES (" + " ' " + CategoryName + " ' )";

        try {
            // Tạo satement
            stm = con.createStatement();
            // truy vấn
            if (stm.executeUpdate(sql) != 1) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Kết quả");
                alert.setContentText("Không thêm được danh mục sản phẩm");
                alert.show();
            } else {
                cbCategoryAddProduct.setItems(getCategory());
                cbCategoryEditProduct.setItems(getCategory());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Kết quả");
                alert.setContentText("Thêm danh mục sản phẩm thành công");
                alert.show();

            }

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cảnh báo");
            alert.setContentText("Không thêm được sản phẩm " + ex.getMessage());
            alert.setResizable(true);
            alert.show();
        }
    }

//    ===============================================================================
//    ================END XEM DANH SACH SAN PHAM VA THEM SAN PHAM MOI ================
//    ==============================================================================-
//    ======================SUA SAN PHAM ============================================
    public Dialog<Product> showDialogEditProduct() {
        // ==================DIALOG SUA SAN PHAM =================
        dialogEditProduct = new Dialog<>();
        dialogEditProduct.setTitle("Chỉnh sửa thông tin sản phẩm");

        ButtonType editButtonType = new ButtonType("Sửa", ButtonBar.ButtonData.OK_DONE);
        dialogEditProduct.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();

        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(50, 150, 50, 50));
        TextField productID = new TextField();
        productID.setPromptText("ProductID");
        productID.setText(SelectProduct.getProductID());
        productID.setEditable(false);
        TextField productName = new TextField();
        productName.setPromptText("Product Name");
        productName.setText(SelectProduct.getProductName());
        TextField productBrand = new TextField();
        productBrand.setPromptText("Brand");
        productBrand.setText(String.valueOf(getBrand().filtered(m -> m.getKey().equals(SelectProduct.getProductBrand())).get(0).getValue()));
        TextField productCat = new TextField();
        productCat.setText(getCategory().filtered(m -> m.getKey().equalsIgnoreCase(SelectProduct.getProductCategory())).get(0).getValue() + "");

        TextField productImage = new TextField();
        productImage.setText(SelectProduct.getImages());
        cbBrandEditProduct = new ComboBox();
        productImage.setPromptText("URL Image");
//        cbBrand.setItems();
        cbBrandEditProduct.setItems(getBrand());
        cbBrandEditProduct.setValue(getBrand().filtered(item -> item.getKey().equals(SelectProduct.getProductBrand())).get(0));
        cbBrandEditProduct.setOnAction((event) -> {
            productBrand.setText(cbBrandEditProduct.getValue().getValue() + "");
        });
        Button btnAddBrand = new Button("Thêm thương hiệu");
        Button btnAddCategory = new Button("Thêm danh mục sản phẩm");
        cbCategoryEditProduct = new ComboBox();
        cbCategoryEditProduct.setItems(getCategory());
        cbCategoryEditProduct.setValue(getCategory().filtered(item -> item.getKey().equals(SelectProduct.getProductCategory())).get(0));

        cbCategoryEditProduct.setOnAction((event) -> {
            productCat.setText(cbCategoryEditProduct.getValue().getValue() + "");
        });

        Button ChooseImage = new Button("Chon anh");

        FileChooser fc = new FileChooser();
        fc.setTitle("Choose a image");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        fc.getExtensionFilters().add(imageFilter);

        // hien thi anh san pham tu lay duong dan anh tu database
        ImageView imv = new ImageView();

        String imagePath = "src/main/resources/group2/imageProduct/" + SelectProduct.getImages();

        Image imageProduct = new Image(new File(imagePath).toURI().toString(), 100, 80, false, true);
        imv.setImage(imageProduct);

        productCat.setPromptText("Cat");
        grid.add(new Label("ProductID"), 0, 0);
        grid.add(productID, 1, 0);
        grid.add(new Label("ProductName"), 0, 1);
        grid.add(productName, 1, 1);
        grid.add(new Label("Brand"), 0, 2);
        grid.add(productBrand, 1, 2);
        grid.add(cbBrandEditProduct, 2, 2);
        grid.add(btnAddBrand, 3, 2);
        grid.add(new Label("Category"), 0, 3);
        grid.add(productCat, 1, 3);
        grid.add(cbCategoryEditProduct, 2, 3);
        grid.add(btnAddCategory, 3, 3);
        grid.add(new Label("Image"), 0, 4);
        grid.add(productImage, 1, 4);
        grid.add(imv, 2, 4);
        grid.add(ChooseImage, 3, 4);

        btnAddBrand.setOnAction((event) -> {
            showDialogAddBrand();
        });

        btnAddCategory.setOnAction((event) -> {
            showDialogAddCategory();
        });

        ChooseImage.setOnAction(event -> {
            // xu ly su kien khi nut chon anh  duoc click
            File file = fc.showOpenDialog(APProduct.getScene().getWindow());
            if (file != null) {
                SelectedImageFile = file;

                Image image = new Image(file.toURI().toString(), 200, 150, false, true);
                imv.setImage(image);

                String fileName = file.getName();
                productImage.setText(fileName);

            }

        });
//        dialogEditProduct.getDialogPane().setContent(grid);
        dialogEditProduct.setResizable(true);
//        DialogPane dialogEditProductPane = dialogEditProduct.getDialogPane();
        dialogEditProduct.getDialogPane().setMinWidth(800);
        // thiet lap backgroynd cho dialogEditProduct
        dialogEditProduct.getDialogPane().setStyle("-fx-background-color: lightblue;");

        dialogEditProduct.getDialogPane().setMinHeight(500);
        dialogEditProduct.getDialogPane().setContent(grid);

        dialogEditProduct.setResultConverter(dialogEditProductButton -> {
            if (dialogEditProductButton == editButtonType) {
                // xu ly su kien chuyen anh duoc chon den thu muc chua file anh san pham cua du an

                return new Product(productID.getText(), productName.getText(), productBrand.getText(), productCat.getText(), productImage.getText());
            }

            return null;
        });

        return dialogEditProduct;
        // END DIALOG THEM SAN PHAM
        // TODO
    }

    @FXML
    private void handleEditProduct(ActionEvent event) {
        showDialogEditProduct();
        Optional<Product> result = dialogEditProduct.showAndWait();
        System.out.println(result);
        result.ifPresent(item -> {
            String sql = "UPDATE PRODUCT SET ProductName = ? , ProductImage = ?, BrandID = ?, CategoryId = ? WHERE ProductID = ?";
            PreparedStatement ps;
            try {
                ps = con.prepareStatement(sql);
                ps.setString(1, item.getProductName());
                ps.setString(2, item.getImages());
                ps.setInt(3, Integer.parseInt(item.getProductBrand()));
                ps.setInt(4, Integer.parseInt(item.getProductCategory()));
                ps.setString(5, item.getProductID());

                if (ps.executeUpdate() != 1) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("KET QUA");
                    alert.setContentText("Không sửa được thông tin sản phẩm");
                    alert.show();
                } else {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("KET QUA");
                    alert.setContentText("Sửa thông tin sản phẩm thành công");
                    if (SelectedImageFile != null) {
                        String fileName = SelectedImageFile.getName();
                        Path targetPath = Paths.get("src/main/resources/group2/imageProduct/", fileName);
                        try {
                            Files.move(SelectedImageFile.toPath(), targetPath);
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    alert.show();

                    showProducts();

                }
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Canh bao");
                alert.setContentText("Không sửa được sản phẩm vì " + ex.getMessage());
                alert.setResizable(true);
                alert.show();
            }

        }
        );

    }

}
