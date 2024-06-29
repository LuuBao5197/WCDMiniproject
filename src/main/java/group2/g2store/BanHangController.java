package group2.g2store;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;

/**
 * FXML Controller class
 *
 * @author Dangm
 */
public class BanHangController implements Initializable {

    @FXML
    private Button btnHome;
    @FXML
    private TableView<Orders> tbOrder;
    @FXML
    private TableColumn<Orders, Number> stt;
    @FXML
    private TableColumn<Orders, String> madonhang;
    @FXML
    private TableColumn<Orders, String> somaykhachhang;
    @FXML
    private TableColumn<Orders, String> manhanvien;
    @FXML
    private TableColumn<Orders, String> thoigian;
    @FXML
    private TableColumn<Orders, Number> tongtien;
    private Connection con;
    private Statement stm;
    private ResultSet rs;
    private ObservableList<Orders> ordersList;
    private ObservableList<ProductQ> ProductList;
    private ObservableList<ProductQ> selectedProducts = FXCollections.observableArrayList();
    private ObservableList<OrderDetail> orderDetailListGlobal = FXCollections.observableArrayList();

    //chua cho phep thanh toan khi chua bam chon lo hang 
    private Map<ProductQ, Boolean> batchSelectedMap = new HashMap<>();

    @FXML
    private AnchorPane pageBanHang;
    @FXML
    private AnchorPane pageTaoPhieu;
    @FXML
    private TableView<ProductQ> tbProduct;
    @FXML
    private TableColumn<ProductQ, String> tensanpham;
    @FXML
    private TableColumn<ProductQ, String> thuonghieu;
    @FXML
    private TableColumn<ProductQ, Number> sothutu;
    @FXML
    private TableColumn<ProductQ, String> anh;
    @FXML
    private VBox listdachon;
    private AnchorPane pageEmpty;
    @FXML
    private Label txtEmpty;
    private TableColumn<ProductQ, String> masanpham;
    @FXML
    private Label totalPrice;
    @FXML
    private ComboBox<String> filterCategory;
    @FXML
    private TextField txtSearch;
    @FXML
    private ComboBox<String> filterBrand;
    @FXML
    private TextField txtCoupon;
    @FXML
    private ComboBox<String> listCustomer;
    private String couponUsed;
    @FXML
    private ComboBox<String> comboBoxCustomer;
    @FXML
    private ComboBox<String> comboBoxEmployee;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;
    @FXML
    private Slider sliderPrice;
    @FXML
    private Label lbSlider;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Fill dữ liệu vào tbOrder orders

        ConnectDB cnDB = new ConnectDB();
        con = cnDB.getConnect();

        String sql = "SELECT orderid, customerPhone, empid, datetimeorder, total FROM Orders";

        try {
            // Tạo statement
            stm = con.createStatement();
            // Truy vấn
            rs = stm.executeQuery(sql);

            // Tạo ObservableList để lưu dữ liệu từ ResultSet
            ordersList = FXCollections.observableArrayList();

            ordersList.clear();

            while (rs.next()) {
                String orderId = rs.getString("orderid");
                String customerPhone = rs.getString("customerPhone");
                String datetimeOrder = rs.getString("datetimeorder");
                String empId = rs.getString("empid");
                double total = rs.getDouble("total");

                ordersList.add(new Orders(orderId, customerPhone, datetimeOrder, empId, total));
            }
            tbOrder.setItems(ordersList);

            // Thiết lập giá trị cho các cột
            stt.setCellValueFactory(data -> {
                Orders o = data.getValue();
                int rowIndex = tbOrder.getItems().indexOf(o) + 1;
                return new SimpleIntegerProperty(rowIndex);
            });

            madonhang.setCellValueFactory(new PropertyValueFactory<>("orderId"));
            somaykhachhang.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
            manhanvien.setCellValueFactory(new PropertyValueFactory<>("empId"));
            thoigian.setCellValueFactory(new PropertyValueFactory<>("datetimeOrder"));
            tongtien.setCellValueFactory(new PropertyValueFactory<>("total"));

        } catch (SQLException ex) {
            Logger.getLogger(BanHangController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //fill dữ liệu cho tbProduct product
        String sql1 = "SELECT DISTINCT p.productname, b.brandname,c.CategoryName, p.productimage, p.productid FROM "
                + "Product p INNER JOIN Brand b ON p.brandid = b.brandid INNER JOIN ProductDetail pd ON "
                + "p.productid = pd.productid join category c on p.categoryid = c.categoryid where pd.importquantity - pd.soldquantity > 0";

        try {
            PreparedStatement stm1 = con.prepareStatement(sql1);
            // Truy vấn
            ResultSet rs1 = stm1.executeQuery();

            // Tạo ObservableList để lưu dữ liệu từ ResultSet
            ProductList = FXCollections.observableArrayList();

            while (rs1.next()) {
                String productName = rs1.getString("productname");
                String brandName = rs1.getString("brandname");
                String productImage = rs1.getString("productimage");
                String productID = rs1.getString("productID");
                String categoryName = rs1.getString("categoryname");
                ProductList.add(new ProductQ(productName, brandName, productImage, productID, categoryName));
            }
            tbProduct.setItems(ProductList);

            // Thiết lập giá trị cho các cột
            sothutu.setCellValueFactory(data -> {
                ProductQ p = data.getValue();
                int rowIndex = tbProduct.getItems().indexOf(p) + 1;
                return new SimpleIntegerProperty(rowIndex);
            });
            tensanpham.setCellValueFactory(new PropertyValueFactory<>("productName"));
            thuonghieu.setCellValueFactory(new PropertyValueFactory<>("ProductBrand"));
            anh.setCellValueFactory(new PropertyValueFactory<>("ProductImage"));
            anh.setCellFactory(param -> new TableCell<ProductQ, String>() {
                private final ImageView imageView = new ImageView();

                {
                    imageView.setFitWidth(40); // Cài đặt chiều rộng hình ảnh
                    imageView.setFitHeight(40); // Cài đặt chiều cao hình ảnh
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        try {
                            String imagePath = "src/main/resources/group2/imageProduct/" + item;
                            Image image = new Image(new File(imagePath).toURI().toString());
                            imageView.setImage(image);
                            setGraphic(imageView);
                        } catch (Exception e) {
                            setGraphic(null);
                        }
                    }
                }
            });

        } catch (SQLException ex) {
            Logger.getLogger(BanHangController.class.getName()).log(Level.SEVERE, null, ex);
        }

// Đặt chiều cao của mỗi dòng là 40px
        tbOrder.setFixedCellSize(
                40);
        tbProduct.setFixedCellSize(
                40);

// Thêm event lắng nghe khi chọn sản phẩm trong bảng tbProduct (bảng sản phẩm)
        tbProduct.getSelectionModel()
                .selectedItemProperty().addListener(new ChangeListener<ProductQ>() {
                    @Override
                    public void changed(ObservableValue<? extends ProductQ> observable, ProductQ oldValue,
                            ProductQ newValue
                    ) {
                        if (newValue != null) {
                            addProductToList(newValue);
                        }
                    }
                }
                );

        //cho danh sách khách hàng vào listCustomer
        ObservableList<String> customerList = FXCollections.observableArrayList();

        // Thực hiện truy vấn để lấy danh sách khách hàng từ cơ sở dữ liệu
        String query = "SELECT * FROM Customer";

        try {
            Statement stm3 = con.createStatement();
            ResultSet rs3 = stm3.executeQuery(query);
            while (rs3.next()) {
                String customerName = rs3.getString("customerName");
                customerList.add(customerName);
            }

        } catch (SQLException ex) {
            Logger.getLogger(BanHangController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Đặt danh sách khách hàng vào ComboBox
        listCustomer.setItems(customerList);
        listCustomer.setValue(customerList.get(4));

        listCustomer.setOnAction(e -> {
            String CustomerName = listCustomer.getValue();
            getCustomerPhone();
        }
        );

        //get database->filterCategory and filterBrand
        //filterCategory
        String queryCategory = "select * from category";
        try {
            Statement stm8 = con.createStatement();
            // Truy vấn
            ResultSet rs8 = stm8.executeQuery(queryCategory);

            // Tạo ObservableList để lưu dữ liệu từ ResultSet
            ObservableList<String> CategoryList = FXCollections.observableArrayList();
            CategoryList.add("See all");
            while (rs8.next()) {
                String ct = rs8.getString("categoryname");
                CategoryList.add(ct);
            }
            filterCategory.setItems(CategoryList);
        } catch (SQLException ex) {
            Logger.getLogger(BanHangController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //filterBrand comboBox
        String queryBrand = "select * from brand";
        try {

            Statement stm9 = con.createStatement();
            // Truy vấn
            ResultSet rs9 = stm9.executeQuery(queryBrand);

            // Tạo ObservableList để lưu dữ liệu từ ResultSet
            ObservableList<String> BrandList = FXCollections.observableArrayList();
            BrandList.add("See all");
            while (rs9.next()) {
                String bn = rs9.getString("brandname");
                BrandList.add(bn);
            }
            filterBrand.setItems(BrandList);

        } catch (SQLException ex) {
            Logger.getLogger(BanHangController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Bắt sự kiện khi chọn danh mục trong filterCategory
        filterCategory.setOnAction(event -> {
            filterBrand.setPromptText("Thương hiệu"); // Reset giá trị của filterBrand
            txtSearch.clear(); // Xóa nội dung trong txtSearch
            String selectedCategory = filterCategory.getValue();
            filterCategory(selectedCategory); // Cập nhật tbProduct với danh mục đã chọn và brand là null
        });

        // Bắt sự kiện khi chọn danh mục trong filterBrand
        filterBrand.setOnAction(event -> {
            filterCategory.setPromptText("Danh mục"); // Reset giá trị của filterCategory
            txtSearch.clear(); // Xóa nội dung trong txtSearch
            String selectedBrand = filterBrand.getValue();
            filterBrand(selectedBrand); // Cập nhật tbProduct với brand đã chọn và category là null
        });

        // Filter theo từ khóa tên sản phẩm trong txtSearch
        txtSearch.setOnKeyReleased(event
                -> {
            String keyword = txtSearch.getText().trim();
            filterCategory.setValue(null); // Reset giá trị của filterCategory
            filterBrand.setValue(null); // Reset giá trị của filterBrand
            searchProduct(keyword);
        }
        );

        //Bắt sự kiện kéo slider 
        sliderPrice.valueProperty().addListener((observable, oldValue, newValue) -> {
            lbSlider.setText("( 0 - " + String.format("%,.0f", newValue.doubleValue()) + " )");
            filterOrderByPrice(newValue.doubleValue());
            dateFrom.setValue(null);
            dateTo.setValue(null);
        });

        //set giá trị cho comboBoxCustomer( lấy giá trị từ db)
        String queryCus = "SELECT customerid, customername, customerphone FROM customer";
        ObservableList<String> listNameCus = FXCollections.observableArrayList();
        listNameCus.add("See all");

        try (
                Statement stm5 = con.createStatement(); ResultSet rs5 = stm5.executeQuery(queryCus)) {

            while (rs5.next()) {
                String customerName = rs5.getString("customername");
                listNameCus.add(customerName);
            }
            comboBoxCustomer.setItems(listNameCus); // Set items to the ComboBox
        } catch (SQLException ex) {
            Logger.getLogger(BanHangController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //set giá trị cho comboBoxEmployee( lấy giá trị từ db)
        String queryEmp = "SELECT empid,empname FROM employee";
        ObservableList<String> listNameEmp = FXCollections.observableArrayList();
        listNameEmp.add("See all");
        try (
                Statement stm6 = con.createStatement(); ResultSet rs6 = stm6.executeQuery(queryEmp)) {

            while (rs6.next()) {
                String EmpName = rs6.getString("empname");
                String EmpID = rs6.getString("empid");
                listNameEmp.add(EmpName);
            }

            comboBoxEmployee.setItems(listNameEmp); // Set items to the ComboBox

        } catch (SQLException ex) {
            Logger.getLogger(BanHangController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Bắt sự kiện khi chọn cômboBoxCustomer
        comboBoxCustomer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String selectedItem = comboBoxCustomer.getValue();
                filterCBCustomer(selectedItem);
                dateFrom.setValue(null);
                dateTo.setValue(null);
            }
        });

        //Bắt sự kiện khi chọn CBEmployee
        comboBoxEmployee.setOnAction(event -> {
            String selectedItem = comboBoxEmployee.getValue();
            filterCBEmployee(selectedItem);
            dateFrom.setValue(null);
            dateTo.setValue(null);
        });

        // Handle DatePicker From value change
        dateFrom.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filterDate(newValue, dateTo.getValue());
                comboBoxCustomer.getSelectionModel().select("See all");
                comboBoxEmployee.getSelectionModel().select("See all");
            }
        });

        // Handle DatePicker To value change
        dateTo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filterDate(dateFrom.getValue(), newValue);
                comboBoxCustomer.getSelectionModel().select("See all");
                comboBoxEmployee.getSelectionModel().select("See all");
            }
        });

    }

    //lọc brand 
    private void filterBrand(String item) {
        if (item == null || item.isEmpty() || item.contains("See all")) {
            tbProduct.setItems(ProductList);
            return;
        } else {
            ObservableList<ProductQ> rs = FXCollections.observableArrayList();
            for (ProductQ p : ProductList) {
                if (p.getProductBrand().contains(item)) {
                    rs.add(p);
                }
            }
            tbProduct.setItems(rs);
        }
    }

    //filter category
    private void filterCategory(String item) {
        if (item == null || item.isEmpty() || item.contains("See all")) {
            tbProduct.setItems(ProductList);
            return;
        } else {
            ObservableList<ProductQ> rs = FXCollections.observableArrayList();
            for (ProductQ p : ProductList) {
                if (p.getProductCategory().contains(item)) {
                    rs.add(p);
                }
            }
            tbProduct.setItems(rs);
        }
    }

    private void filterDate(LocalDate dateFrom, LocalDate dateTo) {
        List<Orders> filteredList = ordersList.stream()
                .filter(o -> {
                    LocalDateTime orderDateTime = LocalDateTime.parse(o.getDatetimeOrder(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    LocalDate orderDate = orderDateTime.toLocalDate(); // Chuyển đổi thành LocalDate để chỉ lấy ngày

                    boolean isAfterOrEqualFromDate = dateFrom == null || orderDate.isEqual(dateFrom) || orderDate.isAfter(dateFrom);
                    boolean isBeforeOrEqualToDate = dateTo == null || orderDate.isEqual(dateTo) || orderDate.isBefore(dateTo);

                    return isAfterOrEqualFromDate && isBeforeOrEqualToDate;
                })
                .collect(Collectors.toList());

        ObservableList<Orders> rs = FXCollections.observableArrayList(filteredList);
        tbOrder.setItems(rs);
    }

    //Search theo name Product
    private void searchProduct(String txt) {
        if (txt.isEmpty()) {
            tbProduct.setItems(ProductList);
        } else {
            ObservableList<ProductQ> searchResults = FXCollections.observableArrayList();
            // Lặp qua danh sách hiện tại và thêm các mục phù hợp vào danh sách kết quả
            for (ProductQ p : ProductList) {
                if (p.getProductName().toLowerCase().contains(txt.toLowerCase())) {
                    searchResults.add(p);
                }
            }
            // Đặt lại dữ liệu cho bảng hiển thị
            tbProduct.setItems(searchResults);
        }
    }

    @FXML
    private void handleHome(MouseEvent event) {

    }

    @FXML
    private void btnAdd(ActionEvent event) {
        pageBanHang.setVisible(false);
        pageTaoPhieu.setVisible(true);
    }

    @FXML
    public void btnDetail(ActionEvent event) {
        int totalBill = 0;
        Orders selectedOrder = tbOrder.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            showAlert("Please select a bill first !");
            return;
        }
        String queryDetail = "SELECT od.OrderId,p.ProductName,NKId,ProductPrice,Quantity,CustomerName,EmpID,o.DateTimeOrder,ProductPrice*Quantity as total,Total as totalBill from OrderDetail od join Orders o on od.OrderId = o.OrderId join Product p on od.ProductId = p.ProductId join Customer c on o.CustomerPhone = c.CustomerPhone where od.orderid = '" + selectedOrder.getOrderId() + "'";
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(queryDetail);

            // Tạo dialog để hiển thị chi tiết đơn hàng
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Bill Details");
            dialog.setHeaderText(null);

            // Tạo VBox để chứa các thông tin sản phẩm
            VBox vbox = new VBox();
            vbox.setSpacing(10);
            vbox.setPadding(new Insets(20, 150, 10, 10));

            String dateTimeOrder = selectedOrder.getDatetimeOrder(); // Replace with actual parsed datetime
            Label time = new Label("Time: " + dateTimeOrder);
            Label cPhone = new Label("Phone Number: " + selectedOrder.getCustomerPhone()); // Replace with actual phone number
            vbox.getChildren().addAll(time, cPhone);

            vbox.getChildren().add(new Separator(Orientation.HORIZONTAL));

            // Tạo GridPane để hiển thị danh sách sản phẩm
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(5);

            // Định dạng tiền tệ Việt Nam
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

            // Tiêu đề cột
            Label productNameHeader = new Label("Product");
            Label quantityHeader = new Label("Quantity");
            Label totalHeader = new Label("Total");

            productNameHeader.setStyle("-fx-font-weight: bold");
            quantityHeader.setStyle("-fx-font-weight: bold");
            totalHeader.setStyle("-fx-font-weight: bold");

            gridPane.add(productNameHeader, 0, 0);
            gridPane.add(quantityHeader, 1, 0);
            gridPane.add(totalHeader, 2, 0);

            int row = 1;
            while (rs.next()) {
                String productName = rs.getString("ProductName");
                int quantity = rs.getInt("Quantity");
                int total = rs.getInt("total");
                totalBill = rs.getInt("totalBill");

                // Hiển thị thông tin sản phẩm vào GridPane
                Label productNameLabel = new Label(productName);
                Label quantityLabel = new Label(Integer.toString(quantity));
                Label totalLabel = new Label(currencyFormat.format(total));

                gridPane.add(productNameLabel, 0, row);
                gridPane.add(quantityLabel, 1, row);
                gridPane.add(totalLabel, 2, row);

                row++;
            }

            // Thêm GridPane vào VBox
            vbox.getChildren().add(gridPane);

            // Thêm dòng kẻ ngang sau danh sách sản phẩm
            vbox.getChildren().add(new Separator(Orientation.HORIZONTAL));

            // Thêm dòng hiển thị tổng hóa đơn  
            Label totalBillLabel = new Label("Total Bill:  " + currencyFormat.format(totalBill));
            totalBillLabel.setStyle("-fx-font-weight: bold");
            vbox.getChildren().add(totalBillLabel);

            // Nếu không có sản phẩm nào được tìm thấy
            if (row == 1) { // Kiểm tra số lượng sản phẩm để hiển thị thông báo phù hợp
                Label noDetailsLabel = new Label("No details found for selected order.");
                vbox.getChildren().add(noDetailsLabel);
            }

            // Đặt VBox vào content của dialog
            dialog.getDialogPane().setContent(vbox);

            // Thêm nút "Close" vào dialog (trước nút "Print Bill Details")
            ButtonType closeButtonType = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().add(closeButtonType);

// Thêm nút "Print Bill Details" vào dialog (sau nút "Close")
            ButtonType printButtonType = new ButtonType("Print Bill Details", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(printButtonType);

// Xử lý sự kiện khi nhấn nút "Print Bill Details"
            dialog.setResultConverter(buttonType -> {
                if (buttonType == printButtonType) {
                    showAlert("Printing...");
                }
                return null;
            });

            dialog.showAndWait();

        } catch (SQLException ex) {
            Logger.getLogger(BanHangController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnExportExcel(ActionEvent event) {
        // Hiển thị cảnh báo xác nhận trước khi xuất Excel
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Export Confirmation");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to export data to Excel?");
        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Tạo workbook mới
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Coupons");

            // Tạo tiêu đề cho các cột
            Row headerRow = sheet.createRow(0);
            CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            String[] columnHeaders = {"STT", "Order ID", "Phone Customer", "Employee ID", "Time", "Total Price"};
            for (int i = 0; i < columnHeaders.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnHeaders[i]);
                cell.setCellStyle(headerStyle);
            }

            // Điền dữ liệu từ tbCoupon vào sheet
            for (int i = 0; i < tbOrder.getItems().size(); i++) {
                Row row = sheet.createRow(i + 1);
                Orders o = tbOrder.getItems().get(i);

                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(o.getEmpId());
                row.createCell(2).setCellValue(o.getCustomerPhone());
                row.createCell(3).setCellValue(o.getEmpId());
                row.createCell(4).setCellValue(o.getDatetimeOrder().toString());
                row.createCell(5).setCellValue(o.getTotal());

            }

            // Tự động điều chỉnh kích thước các cột
            for (int i = 0; i < columnHeaders.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Hiển thị hộp thoại FileChooser để người dùng chọn vị trí và tên file
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Excel File");
            fileChooser.setInitialFileName("Orders.xlsx");
            File file = fileChooser.showSaveDialog(new Stage());

            // Nếu người dùng chọn đúng và không null
            if (file != null) {
                try (FileOutputStream fileOut = new FileOutputStream(file)) {
                    workbook.write(fileOut);
                    showAlert("Excel file exported successfully!");
                } catch (IOException e) {
                    showAlert("Error occurred while exporting Excel file: " + e.getMessage());
                }
            }
        }
    }

    @FXML
    private void btnExportPDF(ActionEvent event) {
        showAlert("Updating...");
    }

    //method thêm sản phẩm vào cột thanh toán 
    private void addProductToList(ProductQ product) {
        if (selectedProducts.contains(product)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Sản phẩm đã được chọn trước đó. Vui lòng chọn sản phẩm khác.");
            alert.showAndWait();
            return;
        }

        txtEmpty.setVisible(false);

        AnchorPane newPane = new AnchorPane();
        newPane.setPrefSize(300, 90);

        Label productName = new Label(product.getProductName());
        Label productPrice = new Label("");
        productPrice.setVisible(false);

        productName.setStyle("-fx-font-weight: bold;-fx-font-size:15px");
        productPrice.setStyle("-fx-text-fill: red;-fx-font-weight: bold");

        ImageView productImage = new ImageView();
        productImage.setFitWidth(90);
        productImage.setFitHeight(90);
        String imagePath = "src/main/resources/group2/imageProduct/" + product.getProductImage();
        File file = new File(imagePath);
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            productImage.setImage(image);
        }

        String query = "SELECT nkid,productPrice, importquantity-soldquantity AS quantity FROM productdetail WHERE productID = '" + product.getProductID() + "' and importquantity-soldquantity > 0";
        ObservableList<String> listComboBox = FXCollections.observableArrayList();
        try {
            Statement stm2 = con.createStatement();
            ResultSet rs2 = stm2.executeQuery(query);
            while (rs2.next()) {
                String warehouseDetail = rs2.getString("nkid");
                int quantity = rs2.getInt("quantity");
                listComboBox.add("Lô " + warehouseDetail + "  ( Tồn: " + quantity + " )");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BanHangController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ComboBox<String> comboBox = new ComboBox<>(listComboBox);
        if (!listComboBox.isEmpty()) {
            comboBox.setValue("Chọn lô hàng");
        }
        comboBox.setStyle("-fx-background-color:white;-fx-border-color:green;");

        TextField quantityField = new TextField("1");
        HBox quantityBox = new HBox();
        quantityBox.setVisible(false);
        quantityBox.setSpacing(5);

        // Xử lý khi chọn comboBox lô hàng
        comboBox.setOnAction(event -> {
            batchSelectedMap.put(product, true); // Cập nhật khi lô hàng được chọn cho sản phẩm này
            quantityField.setText("1");
            String selectedItemId = comboBox.getValue();
            if (selectedItemId != null && !selectedItemId.isEmpty()) {
                String[] parts = selectedItemId.split(" ");
                String nkid = parts[1]; // Lấy mã lô hàng từ phần tử đã chọn trong comboBox
                //SELECT ProductName, nkid, ProductPrice,  ImportQuantity-soldQuantity as quantity FROM ProductDetail pd join Product p on pd.ProductId= p.ProductId WHERE p.ProductId = 'P001' and NKId=1
                String query1 = "SELECT productname,nkid, ProductPrice,  ImportQuantity-soldQuantity as quantity FROM ProductDetail pd join product p on pd.ProductId= p.ProductId WHERE p.ProductId = '" + product.getProductID() + "' and nkid = '" + nkid + "'";
                try (Statement statement = con.createStatement(); ResultSet rs5 = statement.executeQuery(query1)) {
                    if (rs5.next()) {
                        String pname = rs5.getString("productname");
                        int quantity = rs5.getInt("quantity"); // Lấy số lượng nhập về
                        product.setStockQuantity(quantity); // Cập nhật số lượng tồn kho của sản phẩm
                        int price = rs5.getInt("ProductPrice"); // Lấy giá sản phẩm
                        product.setProductPrice(price); // Cập nhật giá sản phẩm

                        // Hiển thị thông tin giá sản phẩm và số lượng bán được
                        productPrice.setVisible(true);
                        String formattedPrice = String.format("%,d", price);
                        productPrice.setText("Thành tiền: " + formattedPrice + " VND");
                        quantityBox.setVisible(true);

                        // Cập nhật danh sách chi tiết đơn hàng
                        orderDetailListGlobal.removeIf(od -> od.getProductID().equals(product.getProductID())); // Xóa OrderDetail cũ nếu có
                        orderDetailListGlobal.add(new OrderDetail(product.getProductID(), pname, Integer.parseInt(nkid), 1, price));
                        updateProductPrice(quantityField, productPrice, product);
                        updateTotalPrice();
                        System.out.println(orderDetailListGlobal);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(BanHangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Button decreaseButton = new Button("-");
        decreaseButton.setStyle("-fx-background-color: orange; -fx-text-fill: white;");
        quantityField.setPrefWidth(40);
        quantityField.setEditable(false);
        quantityField.setStyle("-fx-alignment: center");
        decreaseButton.setOnAction(event -> {
            int currentQuantity = Integer.parseInt(quantityField.getText());
            if (currentQuantity > 1) {
                quantityField.setText(String.valueOf(currentQuantity - 1));
                updateProductPrice(quantityField, productPrice, product);
                updateTotalPrice();

                // Cập nhật số lượng trong danh sách chi tiết đơn hàng
                orderDetailListGlobal.stream()
                        .filter(od -> od.getProductID().equals(product.getProductID()))
                        .forEach(od -> od.setSoldQuantity(currentQuantity - 1));
                System.out.println(orderDetailListGlobal);

            } else {
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Xác nhận");
                confirmAlert.setHeaderText(null);
                confirmAlert.setContentText("Bạn có chắc chắn muốn xóa sản phẩm này khỏi danh sách?");
                Optional<ButtonType> result = confirmAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    listdachon.getChildren().remove(newPane);
                    selectedProducts.remove(product);
                    batchSelectedMap.remove(product); // Xóa trạng thái chọn lô hàng khi sản phẩm bị xóa
                    updateTotalPrice();
                    orderDetailListGlobal.removeIf(od -> od.getProductID().equals(product.getProductID()));
                    System.out.println(orderDetailListGlobal);
                }
            }
        });

        // Icon +
        Button increaseButton = new Button("+");
        increaseButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        increaseButton.setOnAction(event -> {
            int currentQuantity = Integer.parseInt(quantityField.getText());
            if (currentQuantity < product.getStockQuantity()) {
                quantityField.setText(String.valueOf(currentQuantity + 1));
                updateProductPrice(quantityField, productPrice, product);
                updateTotalPrice();
                // Cập nhật số lượng trong danh sách chi tiết đơn hàng
                orderDetailListGlobal.stream()
                        .filter(od -> od.getProductID().equals(product.getProductID()))
                        .forEach(od -> od.setSoldQuantity(currentQuantity + 1));
                System.out.println(orderDetailListGlobal);

            }
        });

        quantityBox.getChildren().addAll(decreaseButton, quantityField, increaseButton);

        VBox vbox = new VBox(productName, comboBox, quantityBox, productPrice);
        vbox.setSpacing(10);

        HBox hbox = new HBox(productImage, vbox);
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10));
        newPane.getChildren().add(hbox);

        listdachon.getChildren().add(newPane);

        selectedProducts.add(product);

        // Đặt trạng thái mặc định là chưa chọn lô hàng
        batchSelectedMap.put(product, false);

        updateTotalPrice();
    }

    private void updateProductPrice(TextField quantityField, Label productPrice, ProductQ product) {
        int currentQuantity = Integer.parseInt(quantityField.getText());

        // Ensure that the product price is fetched correctly and handle formatting issues
        String productPriceString = product.getProductPrice().trim().replace(",", ""); // Remove commas
        int unitPrice = Integer.parseInt(productPriceString);

        int updatedPrice = unitPrice * currentQuantity;
        String formattedPrice = String.format("%,d", updatedPrice);
        productPrice.setText("Thành tiền: " + formattedPrice + " VND");
    }

    private void updateTotalPrice() {
        int total = 0;

        for (Node node : listdachon.getChildren()) {
            if (node instanceof AnchorPane) {
                AnchorPane pane = (AnchorPane) node;

                // Hãy chắc chắn rằng pane có children và đúng cấu trúc bạn mong muốn
                if (!pane.getChildren().isEmpty()) {
                    HBox hbox = (HBox) pane.getChildren().get(0);
                    VBox vbox = (VBox) hbox.getChildren().get(1);

                    // Hãy chắc chắn rằng vbox có children và đúng cấu trúc bạn mong muốn
                    if (!vbox.getChildren().isEmpty()) {
                        Label productPriceLabel = (Label) vbox.getChildren().get(3);
                        int productPriceValue = getPriceValue(productPriceLabel.getText());

                        total += productPriceValue;
                    }
                }
            }
        }

        int couponValue = getCouponValue(txtCoupon.getText().trim());
        total -= couponValue;

        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        String formattedTotal = formatter.format(total);
        totalPrice.setText(formattedTotal + " VND");

        //gọi lại method totalBill
        totalBill();
    }

    private int getCouponValue(String couponCode) {
        int couponValue = 0;
        try {
            String query = "SELECT value FROM Coupon WHERE couponid = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, couponCode);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                couponValue = resultSet.getInt("value");
            }
            statement.close();

        } catch (SQLException ex) {
            Logger.getLogger(BanHangController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return couponValue;
    }

    @FXML
    private void btnCheckCoupon(ActionEvent event) {
        String couponCode = txtCoupon.getText().trim();
        // Kiểm tra xem mã phiếu mua hàng có được nhập vào không
        if (couponCode.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập mã phiếu mua hàng.");
            alert.showAndWait();
            return;
        }

        // Thực hiện truy vấn trong cơ sở dữ liệu để kiểm tra mã phiếu mua hàng
        String query = "SELECT * FROM Coupon WHERE couponid = ?";
        try {
            PreparedStatement stm4 = con.prepareStatement(query);
            stm4.setString(1, couponCode);
            ResultSet rs4 = stm4.executeQuery();

            if (rs4.next()) {
                // Lấy dữ liệu từ cột status và expirydate
                boolean status = rs4.getBoolean("status");
                Date expiryDate = rs4.getDate("expirydate");
                int value = rs4.getInt("value");

                // Chuyển đổi expiryDate từ java.sql.Date sang java.time.LocalDate
                LocalDate expiryLocalDate = expiryDate.toLocalDate();

                // Lấy ngày hiện tại
                LocalDate currentDate = LocalDate.now();

                // Kiểm tra xem status có là true không (true là phiếu đã được sử dụng)
                if (status) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Phiếu mua hàng đã được sử dụng.");
                    txtCoupon.clear();
                    alert.showAndWait();
                    return;
                } else if (expiryLocalDate != null && expiryLocalDate.isBefore(currentDate)) {
                    // Kiểm tra xem expiryDate đã hết hạn chưa
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Phiếu mua hàng đã hết hạn.");
                    txtCoupon.clear();
                    alert.showAndWait();
                    return;
                } else {
                    // Tính tổng đơn hàng
                    int totalBill = totalBill();

                    // Kiểm tra tổng đơn hàng có lớn hơn hoặc bằng 200,000 không
                    if (totalBill < 200000) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Thông báo");
                        alert.setHeaderText(null);
                        alert.setContentText("Phiếu mua hàng chỉ áp dụng với đơn hàng từ 200,000 VND trở lên.");
                        alert.showAndWait();
                        txtCoupon.clear();
                        return;
                    }

                    // Nếu không có vấn đề gì, hiển thị thông báo áp dụng thành công
                    // Thực hiện cập nhật tổng bill và đổi trạng thái phiếu -> true
                    updateTotalPrice();
                    couponUsed = couponCode;
                    try {
                        String queryUpdate = "UPDATE Coupon SET status = 'true' WHERE couponid = ?";
                        PreparedStatement statement = con.prepareStatement(queryUpdate);
                        statement.setString(1, couponCode);
                        statement.executeUpdate();
                        statement.close();

                    } catch (SQLException ex) {
                        Logger.getLogger(BanHangController.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }

                    // Chỉ hiển thị thông báo áp dụng thành công nếu phiếu mua hàng chưa được áp dụng
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Áp dụng phiếu mua hàng thành công!");
                    alert.showAndWait();
                    txtCoupon.clear(); // Reset txtCoupon
                }
            } else {
                // Nếu không tìm thấy phiếu mua hàng
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Phiếu mua hàng không tồn tại.");
                alert.showAndWait();
                txtCoupon.clear();
            }
            stm4.close();

        } catch (SQLException ex) {
            Logger.getLogger(BanHangController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    //method làm mới cột thanh toán
    @FXML
    private void btnDeleteAll(MouseEvent event) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Thông báo");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Bạn có chắc chắn muốn xóa tất cả các sản phẩm?");
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            listdachon.getChildren().clear();
            selectedProducts.clear();
            totalPrice.setText("0 VND");
//        tbProduct.setFocusTraversable(false);
            txtEmpty.setVisible(true);
        }

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //method parse chuỗi -> số
    private int getPriceValue(String priceString) {
        // Kiểm tra nếu chuỗi rỗng hoặc null, trả về giá trị mặc định (ví dụ: 0)
        if (priceString == null || priceString.trim().isEmpty()) {
            return 0; // hoặc xử lý theo cách phù hợp với ứng dụng của bạn
        }
        try {
            // Chuyển đổi chuỗi thành số nguyên và trả về giá trị
            return Integer.parseInt(priceString.replaceAll("[^\\d]", ""));
        } catch (NumberFormatException e) {
            // Xử lý trường hợp ngoại lệ khi không thể chuyển đổi thành số nguyên
            e.printStackTrace(); // hoặc xử lý theo cách phù hợp với ứng dụng của bạn
            return 0; // hoặc giá trị mặc định khác
        }
    }

    //mehtod lấy tổng tiền
    public int totalBill() {
        int total = 0;

        for (Node node : listdachon.getChildren()) {
            if (node instanceof AnchorPane) {
                AnchorPane pane = (AnchorPane) node;

                // Hãy chắc chắn rằng pane có children và đúng cấu trúc bạn mong muốn
                if (!pane.getChildren().isEmpty()) {
                    HBox hbox = (HBox) pane.getChildren().get(0);
                    VBox vbox = (VBox) hbox.getChildren().get(1);

                    // Hãy chắc chắn rằng vbox có children và đúng cấu trúc bạn mong muốn
                    if (!vbox.getChildren().isEmpty()) {
                        Label productPriceLabel = (Label) vbox.getChildren().get(3);
                        int productPriceValue = getPriceValue(productPriceLabel.getText());

                        total += productPriceValue;
                    }
                }
            }
        }
        return total;
    }

    //Method lay ten KH da chon listCustomer
    public String getCustomerPhone() {
        String customerPhone = "";
        String selectedCustomerName = listCustomer.getValue();
        if (selectedCustomerName == null) {
            System.out.println("No customer selected");
        } else if (selectedCustomerName.toLowerCase().equals("None")) {

        } else {
            String query = "SELECT customerphone FROM customer WHERE customerName = ?";

            try {
                PreparedStatement stm = con.prepareStatement(query);
                stm.setString(1, selectedCustomerName);
                ResultSet rs = stm.executeQuery();

                if (rs.next()) {
                    customerPhone = rs.getString("customerPhone");
                } else {
                    System.out.println("Customer not found: " + selectedCustomerName);
                }
                rs.close();
                stm.close();

            } catch (SQLException ex) {
                Logger.getLogger(BanHangController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        return customerPhone;
    }

    @FXML
    private void btnThanhToan(ActionEvent event) throws IOException {
        // Kiểm tra nếu chưa chọn sản phẩm nào
        if (selectedProducts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn sản phẩm trước khi thanh toán.");
            alert.showAndWait();
            return;
        }

        // Kiểm tra nếu chưa chọn lô hàng cho tất cả các sản phẩm
        for (Boolean isBatchSelected : batchSelectedMap.values()) {
            if (!isBatchSelected) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng chọn lô hàng cho tất cả các sản phẩm trước khi thanh toán.");
                alert.showAndWait();
                return;
            }
        }

        // Kiểm tra nếu chưa chọn khách hàng
        String selectedCus = listCustomer.getValue();
        if (selectedCus == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn khách hàng trước khi thanh toán.");
            alert.showAndWait();
            return;
        }

        // 1. Thêm mới thông tin phiếu xuất vào bảng order
        String CusName = "";
        String customerPhone = getCustomerPhone(); // Thay thế bằng hàm lấy số điện thoại khách hàng thực tế
        LocalDateTime dateTimeOrder = LocalDateTime.now(); // Lấy ngày giờ hiện tại
        int status = 1; // Trạng thái mặc định, có thể thay đổi tùy theo nhu cầu
        int empid = 1; // Thay thế bằng hàm lấy ID nhân viên thực tế
        double total = totalBill(); // Lấy tổng số tiền từ hàm tính toán
        int ordID = 0;
        String insertOrderQuery = "INSERT INTO orders (CustomerPhone, DateTimeOrder, Status, Couponid, Empid, Total) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stm = con.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, customerPhone);
            stm.setObject(2, dateTimeOrder);
            stm.setInt(3, status);
            stm.setString(4, couponUsed);
            stm.setInt(5, empid);
            stm.setDouble(6, total);

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Thanh toán thành công");
                // Lấy ID đơn hàng mới tạo
                try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int orderId = ordID = generatedKeys.getInt(1);

                        // 2. Thêm chi tiết đơn hàng vào bảng orderdetail và cập nhật số lượng đã bán trong bảng productdetail
                        for (OrderDetail od : orderDetailListGlobal) {
                            String insertOrderDetail = "INSERT INTO orderdetail (orderid, productid, quantity, nkid,productprice) VALUES (?, ?, ?, ?,?)";
                            String updateProductDetail = "UPDATE productdetail SET soldquantity = soldquantity + ? WHERE productid = ? AND nkid = ?";

                            try (PreparedStatement pstmtOrder = con.prepareStatement(insertOrderDetail); PreparedStatement pstmtProduct = con.prepareStatement(updateProductDetail)) {

                                // Thêm vào bảng orderdetail
                                pstmtOrder.setInt(1, orderId);
                                pstmtOrder.setString(2, od.getProductID());
                                pstmtOrder.setInt(3, od.getSoldQuantity());
                                pstmtOrder.setInt(4, od.getNkID());
                                pstmtOrder.setDouble(5, od.getProductPrice());
                                pstmtOrder.executeUpdate();

                                // Cập nhật bảng productdetail
                                pstmtProduct.setInt(1, od.getSoldQuantity());
                                pstmtProduct.setString(2, od.getProductID());
                                pstmtProduct.setInt(3, od.getNkID());
                                pstmtProduct.executeUpdate();
                            }
                        }

                        // 3. Cập nhật điểm khách hàng nếu không phải khách vãng lai
                        if (!"0".equals(customerPhone)) {
                            int cusPoint = (int) ((int) total * 0.01);
                            String updatePoint = "UPDATE customer SET point = point + ? WHERE customerphone = ?";

                            try (PreparedStatement stmUpdatePoint = con.prepareStatement(updatePoint)) {
                                stmUpdatePoint.setInt(1, cusPoint);
                                stmUpdatePoint.setString(2, customerPhone);
                                stmUpdatePoint.executeUpdate();

                                // Thông báo điểm tích lũy mới của khách hàng
                                String queryCustomer = "SELECT customername, point FROM customer WHERE customerphone = ?";
                                try (PreparedStatement stmCustomer = con.prepareStatement(queryCustomer)) {
                                    stmCustomer.setString(1, customerPhone);
                                    try (ResultSet rsCustomer = stmCustomer.executeQuery()) {
                                        if (rsCustomer.next()) {
                                            String cName = CusName = rsCustomer.getString("customername");
                                            int point = rsCustomer.getInt("point");
                                            showAlert("Điểm tích lũy của khách hàng " + cName + " là " + cusPoint + " VND" + "(Tổng: " + point + ")");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Xác nhận in hóa đơn 
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to print the bill details ?");

                // Show the alert and wait for the user's response
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    createBillDetails(CusName, customerPhone, ordID);
                }

                // Reset dữ liệu sau khi thanh toán
                selectedProducts.clear();
                listdachon.getChildren().clear();
                listCustomer.getSelectionModel().clearSelection();
                txtEmpty.setVisible(true);
                updateTotalPrice();
            } else {
                showAlert("Thanh toán thất bại");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BanHangController.class.getName()).log(Level.SEVERE, "Error inserting order: " + ex.getMessage(), ex);
        }
    }

    @FXML
    private void btnBack(MouseEvent event) {
        pageBanHang.setVisible(true);
        pageTaoPhieu.setVisible(false);
        initialize(null, null);
    }

    @FXML
    private void btnReload(ActionEvent event) {
        initialize(null, null);
        dateFrom.setValue(null);
        dateTo.setValue(null);
        showAlert("Reload successfully !");
    }

    //method xử lý khi kéo filter Price slider
    private void filterOrderByPrice(Double price) {
        if (price == 0 || price == null) {

            tbOrder.setItems(ordersList);
        } else {
            // Tạo một danh sách mới để lưu các mục tìm kiếm phù hợp
            ObservableList<Orders> result = FXCollections.observableArrayList();

            // Lặp qua danh sách hiện tại và thêm các mục phù hợp vào danh sách kết quả
            for (Orders o : ordersList) {
                if (o.getTotal() > 0 && o.getTotal() <= price) {
                    result.add(o);
                }
            }
            // Đặt lại dữ liệu cho bảng hiển thị
            tbOrder.setItems(result);
        }

    }

    private void filterCBCustomer(String item) {
        if (item == null || item.toLowerCase().contains("see all")) {
            tbOrder.setItems(ordersList);
            return;
        }

        try {
            String queryCustomer = "SELECT customerphone FROM customer WHERE customername = ?";
            ConnectDB c = new ConnectDB();
            con = c.getConnect(); // Lấy kết nối từ ConnectDB

            PreparedStatement stm = con.prepareStatement(queryCustomer);
            stm.setString(1, item); // Thiết lập tham số cho câu truy vấn

            ResultSet rs = stm.executeQuery();

            ObservableList<Orders> result = FXCollections.observableArrayList(); // Sử dụng ObservableList<Customer> thay vì Orders

            while (rs.next()) {
                String customerPhone = rs.getString("customerphone");
                for (Orders o : ordersList) {
                    if (o.getCustomerPhone().equals(customerPhone)) {
                        result.add(o);
                    }
                }

            }

            tbOrder.setItems(result); // Đặt lại dữ liệu của tbOrder

        } catch (SQLException ex) {
            Logger.getLogger(BanHangController.class.getName()).log(Level.SEVERE, null, ex);
            // Xử lý ngoại lệ SQL
        }
    }

    private void filterCBEmployee(String item) {
        if (item == null || item.toLowerCase().contains("see all")) {
            tbOrder.setItems(ordersList);
            return;
        }

        try {
            String queryEmp = "SELECT empid FROM employee WHERE empname = ?";
            ConnectDB c = new ConnectDB();
            con = c.getConnect(); // Lấy kết nối từ ConnectDB

            PreparedStatement stm = con.prepareStatement(queryEmp);
            stm.setString(1, item); // Thiết lập tham số cho câu truy vấn

            ResultSet rs = stm.executeQuery();

            ObservableList<Orders> result = FXCollections.observableArrayList();

            // Lặp qua các dòng trong ResultSet
            while (rs.next()) {
                String empid = rs.getString("empid");

                // Lọc danh sách Orders dựa trên empid
                for (Orders o : ordersList) {
                    if (o.getEmpId().equals(empid)) {
                        result.add(o);
                    }
                }
            }

            tbOrder.setItems(result); // Đặt lại dữ liệu của tbOrder

        } catch (SQLException ex) {
            Logger.getLogger(BanHangController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getSelectedNKID(ProductQ product) {
        for (Node node : listdachon.getChildren()) {
            if (node instanceof AnchorPane) {
                AnchorPane anchorPane = (AnchorPane) node;
                for (Node childNode : anchorPane.getChildren()) {
                    if (childNode instanceof HBox) {
                        HBox hBox = (HBox) childNode;
                        for (Node boxNode : hBox.getChildren()) {
                            if (boxNode instanceof ComboBox) {
                                ComboBox<String> comboBox = (ComboBox<String>) boxNode;
                                if (comboBox.getSelectionModel().getSelectedItem() != null) {
                                    String selectedItemId = comboBox.getSelectionModel().getSelectedItem();
                                    String[] parts = selectedItemId.split(" ");
                                    if (parts.length > 1) {
                                        return parts[1];
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private double getPrice(ObservableList<OrderDetail> odl) {
        double totalPrice = 0;
        for (OrderDetail od : odl) {
            totalPrice += od.getTotalPrice();
        }
        return totalPrice;
    }

    public void createBillDetails(String name, String phone, int oid) throws IOException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        String formattedDateTime = dtf.format(LocalDateTime.now());

        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Word Document");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Word documents (*.docx)", "*.docx");
        fileChooser.getExtensionFilters().add(extFilter);

        fileChooser.setInitialFileName("BillDetails(" + oid + ")");

        // Show save file dialog
        Stage tempStage = new Stage();
        File file = fileChooser.showSaveDialog(tempStage);

        if (file != null) {
            // Create Blank document
            XWPFDocument document = new XWPFDocument();

            // Add title to the document
            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = titleParagraph.createRun();
            titleRun.setText("Bill Details");
            titleRun.setColor("0000FF"); // Set font color to blue
            titleRun.setFontSize(20); // Set font size
            titleRun.setBold(true); // Set bold

            // Add date and customer information
            XWPFParagraph infoParagraph = document.createParagraph();
            XWPFRun infoRun = infoParagraph.createRun();
            infoRun.setText("Time: " + formattedDateTime);
            infoRun.addBreak();
            infoRun.setText("Customer Name: " + name);
            infoRun.addBreak();
            infoRun.setText("Customer Phone: " + phone);

            // Add line separator after customer information
            XWPFParagraph lineParagraph1 = document.createParagraph();
            XWPFRun lineRun1 = lineParagraph1.createRun();
            lineRun1.setText("------------------------------------------------------------------------------------------------------------------------------------------");

            // Create a table without borders
            XWPFTable table = document.createTable(orderDetailListGlobal.size() + 1, 3);
            table.getCTTbl().getTblPr().unsetTblBorders();

            // Set column widths
            table.setWidth("100%");
            table.getRow(0).getCell(0).setText("Product Name");
            table.getRow(0).getCell(1).setText("Quantity");
            table.getRow(0).getCell(2).setText("Total Price");

            // Format for displaying numbers with commas
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");

            // Populate the table with data
            int rowIdx = 1;
            double totalBill = 0.0;
            for (OrderDetail od : orderDetailListGlobal) {
                table.getRow(rowIdx).getCell(0).setText(od.getProductName());
                table.getRow(rowIdx).getCell(1).setText(String.valueOf(od.getSoldQuantity()));
                double totalPrice = od.getProductPrice() * od.getSoldQuantity();
                table.getRow(rowIdx).getCell(2).setText(decimalFormat.format(totalPrice)); // Format total price
                totalBill += totalPrice;
                rowIdx++;
            }

            // Add line separator after table
            XWPFParagraph lineParagraph2 = document.createParagraph();
            XWPFRun lineRun2 = lineParagraph2.createRun();
            lineRun2.setText("------------------------------------------------------------------------------------------------------------------------------------------");

            // Add total bill paragraph
            XWPFParagraph totalBillParagraph = document.createParagraph();
            totalBillParagraph.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun runTotalBill = totalBillParagraph.createRun();
            runTotalBill.setText("Total Bill:                    " + decimalFormat.format(totalBill) + " VND");
            runTotalBill.setBold(true);

            // Write the Document in file system
            try (FileOutputStream out = new FileOutputStream(file)) {
                document.write(out);
                showAlert("Export bill details successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                document.close();
            }
        } else {
            System.out.println("Save operation cancelled.");
        }
    }
}
