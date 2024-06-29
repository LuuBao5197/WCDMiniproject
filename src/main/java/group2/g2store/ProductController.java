/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package group2.g2store;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.sql.*;
import java.util.List;

import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
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
    Dialog<Product> dialogAddProduct;
    Dialog<Product> dialogEditProduct;
    Dialog<Product> dialogViewDetailProduct;
    @FXML
    private TableColumn<Product, String> imageColumn;
    private AnchorPane APProduct;

    private File SelectedImageFile = null;
    private ObservableList<Pair<String, Integer>> brandList;
    private ObservableList<Pair<String, Integer>> CatList;
    Dialog<String> dialogAddBrand;
    ComboBox<Pair<String, Integer>> cbCategoryAddProduct = new ComboBox<>();
    ComboBox<Pair<String, Integer>> cbCategoryEditProduct = new ComboBox<>();

    ComboBox<Pair<String, Integer>> cbBrandAddProduct = new ComboBox<>();
    ComboBox<Pair<String, Integer>> cbBrandEditProduct = new ComboBox<>();
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
    TextField productIDAdd, productNameAdd, productBrandAdd, productCatAdd, productImageAdd;
    TextField productIDEdit, productNameEdit, productBrandEdit, productCatEdit, productImageEdit;
    @FXML
    private Button btnHome1;
    @FXML
    private Button btnDeleteProduct;
    @FXML
    private Button btnDetailProduct;
    boolean isError = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cnDB = new ConnectDB();
        con = cnDB.getConnect();
        showProducts();
//        showDialogAddProduct();
        tfSearchProduct.setOnKeyReleased(event -> {
            String keyword = tfSearchProduct.getText().trim();
//            filterCategory.setValue(null); // Reset giá trị của filterCategory
//            filterBrand.setValue(null); // Reset giá trị của filterBrand
            searchProductsbyName(keyword);
        });
        cbFilterCategory.setItems(getCategory());
        cbFilterCategory.setOnAction(event -> {

            try {
                searchProductsbyCategory(cbFilterCategory.getValue().getKey() + "");

            } catch (Exception e) {

            }

        });
        btnRefresh.setOnAction(event -> {
            showProducts();
            tbProductView.scrollTo(0);
            btnEditProduct.setDisable(true);
            btnDetailProduct.setDisable(true);
            btnDeleteProduct.setDisable(true);
        });

    }

//    ================================== XEM SAN PHAM VA THEM SAN PHAM MOI
    public Dialog<Product> showDialogAddProduct() {
        // ==================DIALOG THEM SAN PHAM =================
        dialogAddProduct = new Dialog<>();
        dialogAddProduct.setTitle("Add product");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        ButtonType resetButtonType = new ButtonType("Reset", ButtonBar.ButtonData.APPLY);

        dialogAddProduct.getDialogPane().getButtonTypes().addAll(addButtonType, resetButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();

        grid.setHgap(20);
        grid.setVgap(20);
        grid.autosize();

        grid.setPadding(new Insets(50));
        productIDAdd = new TextField();
        productIDAdd.setPromptText("ProductID");
        productNameAdd = new TextField();
        productNameAdd.setPromptText("Product Name");
        productBrandAdd = new TextField();
        productBrandAdd.setPromptText("Brand");

        productCatAdd = new TextField();

        productImageAdd = new TextField();
        cbBrandAddProduct = new ComboBox();
        productImageAdd.setPromptText("URL Image");
//        cbBrand.setItems();
        cbBrandAddProduct.setItems(getBrand());
        cbBrandAddProduct.setOnAction((event) -> {
            if (cbBrandAddProduct.getValue() != null) {
                productBrandAdd.setText(cbBrandAddProduct.getValue().getValue() + "");
            }

        });
        productBrandAdd.setEditable(false);
        productCatAdd.setEditable(false);
        Button btnAddBrand = new Button("Add brand");
        btnAddBrand.setStyle("-fx-background-color: blue; -fx-text-fill: #fff");
        Button btnAddCategory = new Button("Add Category");
        btnAddCategory.setStyle("-fx-background-color: blue; -fx-text-fill: #fff");
        cbCategoryAddProduct = new ComboBox<>();
        cbCategoryAddProduct.setItems(getCategory());

        cbCategoryAddProduct.setOnAction((event) -> {
            try {
                productCatAdd.setText(cbCategoryAddProduct.getValue().getValue() + "");
            } catch (Exception e) {
            }

        });

        Button ChooseImage = new Button("Choice Image");
        ChooseImage.setStyle("-fx-background-color: blue; -fx-text-fill: #fff");

        FileChooser fc = new FileChooser();
        fc.setTitle("Choose a image");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        fc.getExtensionFilters().add(imageFilter);

        ImageView imv = new ImageView();

        productCatAdd.setPromptText("Cat");
        grid.add(new Label("ProductID"), 0, 0);
        grid.add(productIDAdd, 1, 0);
        grid.add(new Label("ProductName"), 0, 1);
        grid.add(productNameAdd, 1, 1);
        grid.add(new Label("Brand"), 0, 2);
        grid.add(productBrandAdd, 1, 2);
        grid.add(cbBrandAddProduct, 2, 2);
        grid.add(btnAddBrand, 3, 2);
        grid.add(new Label("Category"), 0, 3);
        grid.add(productCatAdd, 1, 3);
        grid.add(cbCategoryAddProduct, 2, 3);
        grid.add(btnAddCategory, 3, 3);
        grid.add(new Label("Image"), 0, 4);
        grid.add(productImageAdd, 1, 4);
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
            File file = fc.showOpenDialog(null);
            if (file != null) {
                SelectedImageFile = file;

                Image image = new Image(file.toURI().toString(), 200, 120, false, true);
                imv.setImage(image);

                String fileName = file.getName();
                productImageAdd.setText(fileName);

            }

        });
        dialogAddProduct.setResizable(true);
        dialogAddProduct.getDialogPane().setContent(grid);
        dialogAddProduct.getDialogPane().setMinHeight(500);

        //  XU LY SU KIEN THEM SAN PHAM MOI 
        Button addProductButton = (Button) dialogAddProduct.getDialogPane().lookupButton(addButtonType);
        addProductButton.addEventFilter(ActionEvent.ACTION, eh -> {
            // validate các trường dữ liệu

            try {
                if (productIDAdd.getText().isEmpty()) {
                    throw new Exception("ProductID is not null");
                }
                Matcher m;
                Pattern p;
                p = Pattern.compile("P[\\d]{3}");
                m = p.matcher(productIDAdd.getText());
                if (!m.matches()) {
                    throw new Exception("ProductID is wrong format (PXXX) with X is digit");
                }
                List<Product> pList = getProducts().stream().filter(predicate -> predicate.getProductID().equals(productIDAdd.getText().trim())).collect(Collectors.toList());
                if (!pList.isEmpty()) {
                    throw new Exception("ProductID must be not duplicate");
                }

                if (productNameAdd.getText().isEmpty()) {
                    throw new Exception("ProductName is not null");
                }
                List<Product> p1List = getProducts().stream().filter(predicate -> predicate.getProductName().equals(productNameAdd.getText().trim())).collect(Collectors.toList());
                if (!p1List.isEmpty()) {
                    throw new Exception("ProductName must be not duplicate");
                }
                if (productBrandAdd.getText().isEmpty()) {
                    throw new Exception("ProductBrand is not null");
                }
                if (productCatAdd.getText().isEmpty()) {
                    throw new Exception("ProductCategory is not null");
                }
                if (productImageAdd.getText().isEmpty()) {
                    throw new Exception("ProductImage is not null");
                }
              
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
                eh.consume();

            }

        });

        dialogAddProduct.setResultConverter((var dialogButton) -> {
            try {
                if (dialogButton == addButtonType) {

                    // validate các trường dữ liệu
                    if (productIDAdd.getText().isEmpty()) {
                        throw new Exception("ProductID is not null");
                    }
                    Matcher m;
                    Pattern p;
                    p = Pattern.compile("P[\\d]{3}");
                    m = p.matcher(productIDAdd.getText());
                    if (!m.matches()) {
                        throw new Exception("ProductID is wrong format (PXXX) with X is digit");
                    }
                    List<Product> pList = getProducts().stream().filter(predicate -> predicate.getProductID().equals(productIDAdd.getText().trim())).collect(Collectors.toList());
                    if (!pList.isEmpty()) {
                        throw new Exception("ProductID must be not duplicate");
                    }

                    if (productNameAdd.getText().isEmpty()) {
                        throw new Exception("ProductName is not null");
                    }
                    List<Product> p1List = getProducts().stream().filter(predicate -> predicate.getProductName().equals(productNameAdd.getText().trim())).collect(Collectors.toList());
                    if (!p1List.isEmpty()) {
                        throw new Exception("ProductName must be not duplicate");
                    }
                    if (productBrandAdd.getText().isEmpty()) {
                        throw new Exception("ProductBrand is not null");
                    }
                    if (productCatAdd.getText().isEmpty()) {
                        throw new Exception("ProductCategory is not null");
                    }
                    if (productImageAdd.getText().isEmpty()) {
                        throw new Exception("ProductImage is not null");
                    }

                    return new Product(productIDAdd.getText(), productNameAdd.getText(), productBrandAdd.getText(), productCatAdd.getText(), productImageAdd.getText());

                }

                if (dialogButton == resetButtonType) {
                    resetDialogAddProduct();
                    imv.setImage(null);
                    return null;
                }
                return null;
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
               

            }
//
            return null;
//
        });
        // END DIALOG THEM SAN PHAM
        // TODO
        return dialogAddProduct;
    }

// HAM BAT DIALOG VA XU LY THEM SAN PHAM 
    @FXML
    private void handleAddProduct(MouseEvent event) {
        showDialogAddProduct();
        Optional<Product> result = dialogAddProduct.showAndWait();

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
                    System.out.println("Add product failed");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
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
                            Files.copy(SelectedImageFile.toPath(), targetPath, REPLACE_EXISTING);
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }

                    alert.show();
                    resetDialogAddProduct();
                    showProducts();

                }
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Add product failed because " + ex.getMessage());
                alert.setResizable(true);
                alert.show();
            }

        }
        );

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

        pList = getProducts().stream().filter(p -> p.getProductName().toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList());
        ProductList = FXCollections.observableArrayList(pList);
        tbProductView.setItems(ProductList);
    }

    public void searchProductsbyCategory(String keyword) {

        List<Product> pList;

        pList = getProducts().stream().filter(p -> p.getProductCategory().equalsIgnoreCase(keyword)).collect(Collectors.toList());
        ProductList = FXCollections.observableArrayList(pList);
        tbProductView.setItems(ProductList);
    }

    public void handleSelectProduct() {

        tbProductView.getSelectionModel()
                .selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {

                        SelectProduct = newSelection;

                        btnEditProduct.setDisable(false);
                        btnDeleteProduct.setDisable(false);
                        btnDetailProduct.setDisable(false);

                    }

                });

    }

    @FXML
    private void handleHome(MouseEvent event) {

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
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialogAddBrand.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();

        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(50, 150, 50, 50));

        TextField tfBrandName = new TextField();
        tfBrandName.setPromptText("Enter brand name: ");
        grid.add(new Label("Brand Name"), 0, 0);
        grid.add(tfBrandName, 1, 0);
        dialogAddBrand.getDialogPane().setMinHeight(300);
        dialogAddBrand.getDialogPane().setMinWidth(500);
        dialogAddBrand.setResizable(true);
        dialogAddBrand.setGraphic(grid);

        dialogAddBrand.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                String BrandName;
                BrandName = tfBrandName.getText();
                if (!BrandName.isEmpty()) {
                    addBrand(BrandName);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Brand name is not null");
                    alert.show();
                }

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
                System.out.println("Adding product failded");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("KET QUA");
                alert.setContentText("Adding brand failed");
                alert.show();
            } else {
                dialogAddBrand.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Add brand successfully");
                alert.show();

                cbBrandAddProduct.setItems(getBrand());
                cbBrandEditProduct.setItems(getBrand());

            }

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Add product failed because ProductID is duplicated");
            alert.show();
        }

    }

    public void showDialogAddCategory() {
        dialogAddCategory = new Dialog<>();
        dialogAddCategory.setTitle("Add Category");
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialogAddCategory.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();

        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(50, 150, 50, 50));

        TextField tfCategoryName = new TextField();
        tfCategoryName.setPromptText("Enter category name");
        grid.add(new Label("Category Name"), 0, 0);
        grid.add(tfCategoryName, 1, 0);
        dialogAddCategory.getDialogPane().setMinHeight(300);
        dialogAddCategory.getDialogPane().setMinWidth(500);
        dialogAddCategory.setResizable(true);
        dialogAddCategory.setGraphic(grid);

        dialogAddCategory.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {

                } catch (Exception e) {
                }
                String CategoryName;
                CategoryName = tfCategoryName.getText();
                if (CategoryName.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Category name is not null");
                    alert.show();

                } else {
                    addCategory(CategoryName);
                }

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

                alert.setContentText("Add category failed");
                alert.show();
            } else {
                dialogAddCategory.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Add Category successfully ");
                alert.show();
                cbCategoryAddProduct.setItems(getCategory());
                cbCategoryEditProduct.setItems(getCategory());

            }

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Add Category failed because could not connect to database");
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
        dialogEditProduct.setTitle("Edit information about product");

        ButtonType editButtonType = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
        dialogEditProduct.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();

        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(50));
        grid.autosize();

        productIDEdit = new TextField();
        productIDEdit.setPromptText("ProductID");
        productIDEdit.setText(SelectProduct.getProductID());
        productIDEdit.setEditable(false);
        productNameEdit = new TextField();
        productNameEdit.setPromptText("Product Name");
        productNameEdit.setText(SelectProduct.getProductName());
        productBrandEdit = new TextField();
        productBrandEdit.setEditable(false);
        productBrandEdit.setPromptText("Brand");
        productBrandEdit.setText(String.valueOf(getBrand().filtered(m -> m.getKey().equals(SelectProduct.getProductBrand())).get(0).getValue()));
        productCatEdit = new TextField();
        productCatEdit.setText(getCategory().filtered(m -> m.getKey().equalsIgnoreCase(SelectProduct.getProductCategory())).get(0).getValue() + "");
        productCatEdit.setEditable(false);

        productImageEdit = new TextField();
        productImageEdit.setText(SelectProduct.getImages());
        cbBrandEditProduct = new ComboBox();
        productImageEdit.setPromptText("URL Image");
//        cbBrand.setItems();
        cbBrandEditProduct.setItems(getBrand());
        cbBrandEditProduct.setValue(getBrand().filtered(item -> item.getKey().equals(SelectProduct.getProductBrand())).get(0));
        cbBrandEditProduct.setOnAction((event) -> {
            if (cbBrandEditProduct.getValue() != null) {
                productBrandEdit.setText(cbBrandEditProduct.getValue().getValue() + "");
            }

        });
        Button btnAddBrand = new Button("Add brand");
        btnAddBrand.setStyle("-fx-background-color: green; -fx-text-fill: #fff");
        Button btnAddCategory = new Button("Add Category");
        btnAddCategory.setStyle("-fx-background-color: green; -fx-text-fill: #fff");
        cbCategoryEditProduct = new ComboBox();
        cbCategoryEditProduct.setItems(getCategory());
        cbCategoryEditProduct.setValue(getCategory().filtered(item -> item.getKey().equals(SelectProduct.getProductCategory())).get(0));

        cbCategoryEditProduct.setOnAction((event) -> {
            try {
                productCatEdit.setText(cbCategoryEditProduct.getValue().getValue() + "");
            } catch (Exception e) {

            }
        });

        Button ChooseImage = new Button("Choice Image");
        ChooseImage.setStyle("-fx-background-color: green; -fx-text-fill: #fff");

        FileChooser fc = new FileChooser();
        fc.setTitle("Choose a image");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        fc.getExtensionFilters().add(imageFilter);

        // hien thi anh san pham tu lay duong dan anh tu database
        ImageView imv = new ImageView();

        String imagePath = "src/main/resources/group2/imageProduct/" + SelectProduct.getImages();

        Image imageProduct = new Image(new File(imagePath).toURI().toString(), 100, 80, false, true);
        imv.setImage(imageProduct);

        productCatEdit.setPromptText("Cat");
        grid.add(new Label("ProductID"), 0, 0);
        grid.add(productIDEdit, 1, 0);
        grid.add(new Label("ProductName"), 0, 1);
        grid.add(productNameEdit, 1, 1);
        grid.add(new Label("Brand"), 0, 2);
        grid.add(productBrandEdit, 1, 2);
        grid.add(cbBrandEditProduct, 2, 2);
        grid.add(btnAddBrand, 3, 2);
        grid.add(new Label("Category"), 0, 3);
        grid.add(productCatEdit, 1, 3);
        grid.add(cbCategoryEditProduct, 2, 3);
        grid.add(btnAddCategory, 3, 3);
        grid.add(new Label("Image"), 0, 4);
        grid.add(productImageEdit, 1, 4);
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
            File file = fc.showOpenDialog(null);
            if (file != null) {
                SelectedImageFile = file;

                Image image = new Image(file.toURI().toString(), 200, 150, false, true);
                imv.setImage(image);

                String fileName = file.getName();
                productImageEdit.setText(fileName);

            }

        });

        dialogEditProduct.setResizable(true);

//        dialogEditProduct.getDialogPane().setMinWidth(600);
        // thiet lap backgroynd cho dialogEditProduct
        dialogEditProduct.getDialogPane().setStyle("-fx-font-size: 16px; -fx-background-image: url('file:/D:/G2Store/src/main/resources/group2/Icon/logoG2Store.jpg'); -fx-opacity: 0.8");
//        dialogEditProduct.getDialogPane().setStyle("-fx-background-color: lightblue;");
//        dialogEditProduct.getDialogPane().setMinHeight(300);
        grid.setStyle("-fx-background-color: #fff ; -fx-opacity: 1");
        dialogEditProduct.getDialogPane().setContent(grid);

        dialogEditProduct.setResultConverter(dialogEditProductButton -> {
            // xu ly su kien chuyen anh duoc chon den thu muc chua file anh san pham cua du an
            try {
                if (dialogEditProductButton == editButtonType) {
                    // validate các trường dữ liệu
                    if (productIDEdit.getText().isEmpty()) {
                        throw new Exception("ProductID is not null");
                    }
                    Matcher m;
                    Pattern p;
                    p = Pattern.compile("P[\\d]{3}");
                    m = p.matcher(productIDEdit.getText());
                    if (!m.matches()) {
                        throw new Exception("ProductName is wrong format (PXXX) with X is digit");
                    }
                    if (productNameEdit.getText().isEmpty()) {
                        throw new Exception("ProductName is not null");
                    }
                    if (productBrandEdit.getText().isEmpty()) {
                        throw new Exception("ProductBrand is not null");
                    }
                    if (productCatEdit.getText().isEmpty()) {
                        throw new Exception("ProductCategory is not null");
                    }
                    if (productImageEdit.getText().isEmpty()) {
                        throw new Exception("ProductImage is not null");
                    }
                    return new Product(productIDEdit.getText(), productNameEdit.getText(), productBrandEdit.getText(), productCatEdit.getText(), productImageEdit.getText());

                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
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
                            Files.copy(SelectedImageFile.toPath(), targetPath, REPLACE_EXISTING);
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

    @FXML
    private void handleDeleteProduct(ActionEvent event) {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);

        deleteAlert.setContentText("Are you sure to delete this product? ");
        deleteAlert.show();
        deleteAlert.setResultConverter(m -> {
            if (m == ButtonType.OK) {
                ObservableList<Product> list = FXCollections.observableArrayList();
                list.clear();
                try {

                    String sql = "SELECT * FROM Product where ? in (select ProductId from ProductDetail)";
                    PreparedStatement ps;
                    ps = con.prepareStatement(sql);
                    ps.setString(1, SelectProduct.getProductID());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        Product p = new Product("1", "1", "1", "1", "1");
                        list.add(p);
                    }
                    if (!list.isEmpty()) {
                        throw new Exception("Khong xoa duoc vvi anh huong nhieu du lieu khac");
                    }

                    sql = "DELETE  FROM PRODUCT WHERE ProductID = ?";

                    try {
                        ps = con.prepareStatement(sql);
                        ps.setString(1, SelectProduct.getProductID());

                        if (ps.executeUpdate() != 1) {

                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("KET QUA");
                            alert.setContentText("Không xoá được sản phẩm");
                            alert.show();
                        } else {

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("KET QUA");
                            alert.setContentText("Xoá sản phẩm thành công");
                            alert.show();

                            showProducts();

                        }
                    } catch (SQLException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Canh bao");
                        alert.setContentText("Không xoá được sản phẩm vì " + ex.getMessage());
                        alert.setResizable(true);
                        alert.show();
                    }
                } catch (SQLException ex) {
                    System.out.println("Không thể xoá");
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Canh bao");
                    alert.setContentText(ex.getMessage());
                    alert.setResizable(true);
                    alert.show();
                }
            }
            return null;

        });
    }

    public void showDialogViewDetailProduct() {
        dialogViewDetailProduct = new Dialog<>();
        dialogViewDetailProduct.setTitle("Detail information about product");
        dialogViewDetailProduct.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
//        grid.setPadding(new Insets(50, 50, 50, 50));
        TableView<Product> tvDetailProduct = new TableView<>();
        tvDetailProduct.setPadding(new Insets(50));
        tvDetailProduct.setMinSize(1200, 500);
        tvDetailProduct.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tvDetailProduct.setStyle("-fx-font-size: 16px; -fx-background-image: url('file:/D:/G2Store/src/main/resources/group2/Icon/logoG2Store.jpg'); ");

        TableColumn<Product, String> colProductID = new TableColumn<>("Product ID");
        colProductID.setStyle("-fx-alignment: center;");
        TableColumn<Product, String> colProductName = new TableColumn<>("Product Name");
        colProductName.setStyle("-fx-alignment: center");
        TableColumn<Product, String> colNKId = new TableColumn<>("EnterID");
        colNKId.setStyle("-fx-alignment: center");
        TableColumn<Product, Float> colImportPrice = new TableColumn<>("Import Price");
        colImportPrice.setStyle("-fx-alignment: center; -fx-text-fill: red");
        TableColumn<Product, Float> colExportPrice = new TableColumn<>("Sold Price");
        colExportPrice.setStyle("-fx-alignment: center; -fx-text-fill: blue");
        TableColumn<Product, Integer> colImportQuantity = new TableColumn<>("Import Quantity");
        colImportQuantity.setStyle("-fx-alignment: center");
        TableColumn<Product, Integer> colSoldQuantity = new TableColumn<>("Sold Quantity");
        colSoldQuantity.setStyle("-fx-alignment: center");
        TableColumn<Product, String> colHSD = new TableColumn<>("HSD");

        tvDetailProduct.getColumns().addAll(colProductID, colProductName, colNKId, colImportPrice, colExportPrice, colImportQuantity, colSoldQuantity, colHSD);

        tvDetailProduct.setItems(getProductDetail());
        colProductID.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        colNKId.setCellValueFactory(new PropertyValueFactory<>("NKId"));
        colImportPrice.setCellValueFactory(new PropertyValueFactory<>("ImportPrice"));
        colExportPrice.setCellValueFactory(new PropertyValueFactory<>("SoldPrice"));
        colImportQuantity.setCellValueFactory(new PropertyValueFactory<>("ImportQuantity"));
        colSoldQuantity.setCellValueFactory(new PropertyValueFactory<>("SoldQuantity"));
        colHSD.setCellValueFactory(new PropertyValueFactory<>("HSD"));
        dialogViewDetailProduct.getDialogPane().setContent(tvDetailProduct);
        dialogViewDetailProduct.show();

    }

    @FXML
    private void handleViewDetailProduct(ActionEvent event) {
        showDialogViewDetailProduct();
    }

    public ObservableList<Product> getProductDetail() {

        String sql = "SELECT P.ProductId, P.ProductName, PD.NKId, PD.ImportPrice, PD.ProductPrice, PD.ImportQuantity, PD.SoldQuantity, PD.HSD \n"
                + "FROM Product P JOIN ProductDetail PD ON P.ProductId = PD.ProductId\n"
                + "WHERE P.ProductId = '" + SelectProduct.getProductID() + "'";
        try {
            // Tạo satement
            stm = con.createStatement();
            // truy vấn
            rs = stm.executeQuery(sql);

            // tạo observableList để lưu dữ liệu từ ResultSet
            ProductList = FXCollections.observableArrayList();

            ProductList.clear();

            while (rs.next()) {
                ProductList.add(new Product(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(6), rs.getInt(7), rs.getFloat(4), rs.getFloat(5), rs.getString(8).substring(0, 10)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ProductList;
    }

    private void resetDialogAddProduct() {
        productIDAdd.setText("");
        productNameAdd.setText("");
        productBrandAdd.setText("");
        productCatAdd.setText("");
        productImageAdd.setText("");
        cbCategoryAddProduct.getSelectionModel().clearSelection();
        cbBrandAddProduct.getSelectionModel().clearSelection();

    }

}
