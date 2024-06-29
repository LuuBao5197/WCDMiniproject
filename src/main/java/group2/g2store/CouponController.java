package group2.g2store;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.commons.lang3.StringUtils;

/**
 * FXML Controller class
 *
 * @author Dangm
 */
public class CouponController implements Initializable {

    @FXML
    private Button btnHome;
    @FXML
    private TableView<Coupon> tbCoupon;
    @FXML
    private TableColumn<Coupon, Number> stt;
    @FXML
    private TableColumn<Coupon, String> colCouponID;
    @FXML
    private TableColumn<Coupon, String> colValue;
    @FXML
    private TableColumn<Coupon, String> colExpiryDate;
    @FXML
    private TableColumn<Coupon, String> colStatus;
    @FXML
    private AnchorPane pageCoupon;
    @FXML
    private TextField txtSearch;
    @FXML
    private ComboBox<String> cbFilter;

    private Connection con;
    private Statement stm;
    private ResultSet rs;
    private ObservableList<Coupon> CouponList;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ConnectDB cnDB = new ConnectDB();
        con = cnDB.getConnect();
        String sql = "SELECT * FROM coupon";

        try {
            // Tạo statement
            stm = con.createStatement();
            // Truy vấn
            rs = stm.executeQuery(sql);

            // Tạo ObservableList để lưu dữ liệu từ ResultSet
            CouponList = FXCollections.observableArrayList();

            CouponList.clear();

            while (rs.next()) {
                String couponID = rs.getString("couponid");
                int value = rs.getInt("value");
                String expiryDate = rs.getString("expirydate");
                int status = rs.getInt("status");

                CouponList.add(new Coupon(couponID, value, expiryDate, status));
            }
            tbCoupon.setItems(CouponList);

            // Thiết lập giá trị cho các cột
            stt.setCellValueFactory(data -> {
                Coupon c = data.getValue();
                int rowIndex = tbCoupon.getItems().indexOf(c) + 1;
                return new SimpleIntegerProperty(rowIndex);
            });

            colCouponID.setCellValueFactory(new PropertyValueFactory<>("couponid"));
            colValue.setCellValueFactory(data -> {
                Coupon c = data.getValue();
                return new SimpleStringProperty(String.format("%,d", c.getValue()));
            });
            colExpiryDate.setCellValueFactory(new PropertyValueFactory<>("expirydate"));
            colStatus.setCellValueFactory(data -> {
                Coupon c = data.getValue();
                return new SimpleStringProperty(c.getStatusText());
            });

        } catch (SQLException ex) {
            Logger.getLogger(CouponController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Đóng ResultSet, Statement và Connection
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CouponController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Set giá trị cho cbFilter
        cbFilter.setItems(FXCollections.observableArrayList("See all", "Coupon unexpired", "Coupon ready"));
        cbFilter.getSelectionModel().select("See all");

        // Xử lý sự kiện khi người dùng chọn giá trị từ ComboBox
        cbFilter.setOnAction(e -> {
//            showAlert("OK");
            handleCBFilter();
        });

        //Xử lý sự kiện search 
        txtSearch.setOnKeyReleased(event -> {
            handleSearch();
        });

        //Xử lý chọn và btnEdit
        tbCoupon.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Xử lý khi có dòng được chọn
        });

        tbCoupon.setFixedCellSize(40);

    }

    @FXML
    private void handleHome(MouseEvent event) {
        // Xử lý sự kiện click vào nút Home
    }

    // Xử lý sự kiện click vào nút Add
    @FXML
    private void btnAdd(ActionEvent event) {
        // Tạo dialog để nhập thông tin Coupon
        Dialog<Coupon> dialog = new Dialog<>();
        dialog.setTitle("Add Coupon");
        dialog.setHeaderText("Enter coupon details:");
        dialog.getDialogPane().setMinWidth(500);
        dialog.getDialogPane().setMinHeight(100);
        // Thiết lập nút (các) bấm
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Tạo một grid pane để sắp xếp các thành phần nhập liệu
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Controls cho nhập liệu
        TextField txtCouponID = new TextField();
        txtCouponID.setMinWidth(400);
        TextField txtValue = new TextField();
        txtValue.setMinWidth(400);
        DatePicker datePicker = new DatePicker();
        datePicker.setDisable(true);
        datePicker.setMinWidth(400);
        datePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date) {
                return (date != null) ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty()) ? LocalDate.parse(string, dateFormatter) : null;
            }
        });
        datePicker.setValue(LocalDate.now()); // Set giá trị mặc định là ngày hiện tại
        Label lblStatus = new Label("Ready"); // Giá trị mặc định cho Status

        // Đưa các control vào grid pane
        grid.add(new Label("Coupon ID:"), 0, 0);
        grid.add(txtCouponID, 1, 0);
        grid.add(new Label("Value:"), 0, 1);
        grid.add(txtValue, 1, 1);
        grid.add(new Label("Expiry Date:"), 0, 2);
        grid.add(datePicker, 1, 2);
        grid.add(new Label("Status:"), 0, 3);
        grid.add(lblStatus, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Xử lý sự kiện khi người dùng nhấn OK
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    String couponID = txtCouponID.getText().trim().toUpperCase();
                    int value = Integer.parseInt(txtValue.getText().trim());
                    LocalDate expiryDate = datePicker.getValue();
                    int status = 0; // Mặc định là "Ready"

                    // Kiểm tra nếu couponID hoặc value không hợp lệ
                    if (couponID.isEmpty() || value <= 0) {
                        showAlert("Invalid input. Please enter valid Coupon ID and Value.");
                        return null;
                    }

                    // Tạo một đối tượng Coupon mới và trả về
                    return new Coupon(couponID, value, expiryDate.toString(), status);

                } catch (NumberFormatException e) {
                    showAlert("Invalid input. Please enter a valid numeric value for 'Value'.");
                    return null;
                }
            }
            return null;
        });

        // Hiển thị dialog và xử lý kết quả
        dialog.showAndWait().ifPresent(coupon -> {
            // Nếu coupon không null, thêm vào danh sách và cập nhật TableView
            if (coupon != null) {
                //Thêm vào db
                ConnectDB cnDB = new ConnectDB();
                con = cnDB.getConnect();
                String sql = "INSERT INTO coupon (couponid, value, expirydate, status) VALUES (?, ?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = con.prepareStatement(sql);

                    preparedStatement.setString(1, coupon.getCouponid());
                    preparedStatement.setInt(2, coupon.getValue());
                    preparedStatement.setString(3, coupon.getExpirydate());
                    preparedStatement.setInt(4, coupon.getStatus());

                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        showAlert("Coupon added successfully!");
                        initialize(null, null);
                    } else {
                        showAlert("Failed to add coupon!");
                    }
                } catch (SQLException ex) {
                    showAlert("Error occurred while adding coupon: " + ex.getMessage());
                }
            }
        });
    }

    @FXML
    private void btnEdit(ActionEvent event) {
        // Lấy coupon được chọn từ TableView
        Coupon selectedCoupon = tbCoupon.getSelectionModel().getSelectedItem();

        if (selectedCoupon == null) {
            showAlert("Please select a coupon to edit.");
            return;
        }

        // Tạo dialog để chỉnh sửa thông tin Coupon
        Dialog<Coupon> dialog = new Dialog<>();
        dialog.setTitle("Edit Coupon");
        dialog.setHeaderText("Edit coupon details:");
        dialog.getDialogPane().setMinWidth(500);
        // Thiết lập nút (các) bấm
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Tạo một grid pane để sắp xếp các thành phần nhập liệu
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Controls cho nhập liệu
        TextField txtCouponID = new TextField(selectedCoupon.getCouponid());
        TextField txtValue = new TextField(String.valueOf(selectedCoupon.getValue()));

        txtCouponID.setDisable(true);
        txtValue.setDisable(true);

        // Phần cập nhật: sử dụng DateTimeFormatter để phân tích chuỗi ngày tháng
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime expiryDateTime = LocalDateTime.parse(selectedCoupon.getExpirydate(), dateFormatter);
        LocalDate expiryDate = expiryDateTime.toLocalDate();
        DatePicker datePicker = new DatePicker(expiryDate);

        txtCouponID.setMinWidth(400);
        txtValue.setMinWidth(400);
        datePicker.setMinWidth(400);
        // RadioButtons
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton radioReady = new RadioButton("Ready");
        RadioButton radioUsed = new RadioButton("Expired");
        radioReady.setToggleGroup(toggleGroup);
        radioUsed.setToggleGroup(toggleGroup);
        if (selectedCoupon.getStatus() == 0) {
            radioReady.setSelected(true);
        } else {
            radioUsed.setSelected(true);
        }

        // Đưa các control vào grid pane
        grid.add(new Label("Coupon ID:"), 0, 0);
        grid.add(txtCouponID, 1, 0);
        grid.add(new Label("Value:"), 0, 1);
        grid.add(txtValue, 1, 1);
        grid.add(new Label("Expiry Date:"), 0, 2);
        grid.add(datePicker, 1, 2);
        grid.add(new Label("Status:"), 0, 3);

        // Sử dụng HBox để chứa hai nút radio này để chúng có thể được hiển thị cạnh nhau
        HBox statusBox = new HBox(10); // 10 là khoảng cách giữa các phần tử trong HBox
        statusBox.getChildren().addAll(radioReady, radioUsed);
        grid.add(statusBox, 1, 3); // Chỉ số hàng 3 là hợp lệ với GridPane có 4 hàng (0-3)

        dialog.getDialogPane().setContent(grid);

        // Xử lý sự kiện khi người dùng nhấn OK
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    String couponID = txtCouponID.getText().trim();
                    int value = Integer.parseInt(txtValue.getText().trim());
                    LocalDate newExpiryDate = datePicker.getValue();
                    int status = (radioReady.isSelected()) ? 0 : 1;

                    // Kiểm tra nếu couponID hoặc value không hợp lệ
                    if (couponID.isEmpty() || value <= 0) {
                        showAlert("Invalid input. Please enter valid Coupon ID and Value.");
                        return null;
                    }

                    // Tạo một đối tượng Coupon mới và trả về
                    return new Coupon(couponID, value, newExpiryDate.toString(), status);

                } catch (NumberFormatException e) {
                    showAlert("Invalid input. Please enter a valid numeric value for 'Value'.");
                    return null;
                }
            }
            return null;
        });

        // Hiển thị dialog và xử lý kết quả
        dialog.showAndWait().ifPresent(newCoupon -> {
            // Nếu coupon không null, cập nhật vào danh sách và cơ sở dữ liệu
            if (newCoupon != null) {
                // Cập nhật vào cơ sở dữ liệu
                ConnectDB c = new ConnectDB();
                c.getConnect();
                String sql = "UPDATE coupon SET expirydate = ?, status = ? WHERE couponid = ?";

                try {
                    PreparedStatement preparedStatement = con.prepareStatement(sql);

                    preparedStatement.setString(1, newCoupon.getExpirydate()); // Sửa lỗi, chỉ số 1 thay vì 2
                    preparedStatement.setInt(2, newCoupon.getStatus()); // Sửa lỗi, chỉ số 2 thay vì 3
                    preparedStatement.setString(3, selectedCoupon.getCouponid()); // Chỉnh sửa dựa trên coupon ID cũ

                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        // Cập nhật TableView
                        int index = tbCoupon.getSelectionModel().getSelectedIndex();
                        CouponList.set(index, newCoupon);
                        tbCoupon.refresh();
                        showAlert("Coupon updated successfully!");
                        initialize(null, null);
                    } else {
                        showAlert("Failed to update coupon!");
                    }
                } catch (SQLException ex) {
                    showAlert("Error occurred while updating coupon: " + ex.getMessage());
                }
            }
        });
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
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            String[] columnHeaders = {"STT", "Coupon ID", "Value", "Expiry Date", "Status"};
            for (int i = 0; i < columnHeaders.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnHeaders[i]);
                cell.setCellStyle(headerStyle);
            }

            // Điền dữ liệu từ tbCoupon vào sheet
            for (int i = 0; i < tbCoupon.getItems().size(); i++) {
                Row row = sheet.createRow(i + 1);
                Coupon coupon = tbCoupon.getItems().get(i);

                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(coupon.getCouponid());
                row.createCell(2).setCellValue(coupon.getValue());
                row.createCell(3).setCellValue(coupon.getExpirydate().toString()); // Assuming getExpirydate() returns LocalDate
                row.createCell(4).setCellValue(coupon.getStatusText());
            }

            // Tự động điều chỉnh kích thước các cột
            for (int i = 0; i < columnHeaders.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Hiển thị hộp thoại FileChooser để người dùng chọn vị trí và tên file
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Excel File");
            fileChooser.setInitialFileName("Coupons.xlsx");
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
    // Hiển thị cảnh báo xác nhận trước khi xuất PDF
    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
    confirmAlert.setTitle("Confirm Export");
    confirmAlert.setHeaderText(null);
    confirmAlert.setContentText("Are you sure you want to export the data to PDF?");
    Optional<ButtonType> result = confirmAlert.showAndWait();

    if (result.isPresent() && result.get() == ButtonType.OK) {
        // Tạo một FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF File");
        fileChooser.setInitialFileName("coupons.pdf");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

        // Hiển thị hộp thoại chọn vị trí và tên tệp và lấy đường dẫn tệp được chọn
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try {
                // Tạo một tài liệu PDF mới
                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page);

                // Tạo content stream để viết vào tài liệu
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);

                // Tạo tiêu đề cho bảng
                String[] headers = {"STT", "Coupon ID", "Value", "Expiry Date", "Status"};
                contentStream.showText("COUPON LIST");
                contentStream.newLine();
                contentStream.newLine();

                // In tiêu đề bảng
                for (String header : headers) {
                    contentStream.showText(StringUtils.rightPad(header, 25));
                    contentStream.showText("     "); // Khoảng cách giữa các cột
                }
                contentStream.newLine();

                // Duyệt qua danh sách couponList và ghi thông tin vào tài liệu PDF
                for (int i = 0; i < CouponList.size(); i++) {
                    Coupon coupon = CouponList.get(i);
                    contentStream.showText(StringUtils.rightPad(Integer.toString(i + 1), 25));
                    contentStream.showText("     ");
                    contentStream.showText(StringUtils.rightPad(coupon.getCouponid(), 25));
                    contentStream.showText("     ");
                    contentStream.showText(StringUtils.rightPad(Double.toString(coupon.getValue()), 25));
                    contentStream.showText("     ");

                    // Sử dụng chuỗi expirydate trực tiếp
                    String formattedDate = coupon.getExpirydate();
                    contentStream.showText(StringUtils.rightPad(formattedDate, 25));

                    contentStream.showText("     ");
                    contentStream.showText(coupon.getStatusText());
                    
                    // Di chuyển đến hàng tiếp theo
                    contentStream.newLine();
                }

                // Kết thúc viết nội dung và đóng stream
                contentStream.endText();
                contentStream.close();

                // Lưu tài liệu vào tệp đã chọn
                document.save(file);
                document.close();

                // Hiển thị thông báo khi xuất PDF thành công
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Export PDF");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Export PDF successfully!");
                successAlert.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error occurred while exporting PDF file: " + e.getMessage());
            }
        }
    }
}
  
    @FXML
    private void btnDelete(ActionEvent event
    ) {
        // Lấy coupon được chọn từ TableView
        Coupon selectedCoupon = tbCoupon.getSelectionModel().getSelectedItem();

        if (selectedCoupon == null) {
            showAlert("Please select a coupon to delete.");
            return;
        }

        // Xác nhận xóa
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Xóa coupon khỏi cơ sở dữ liệu
            ConnectDB c = new ConnectDB();
            c.getConnect();
            String sql = "DELETE FROM coupon WHERE couponid = ?";

            try {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, selectedCoupon.getCouponid());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    // Xóa khỏi danh sách hiện tại và cập nhật TableView
                    CouponList.remove(selectedCoupon);
                    tbCoupon.refresh();
                    showAlert("Coupon deleted successfully!");
                    initialize(null, null);
                } else {
                    showAlert("Failed to delete coupon!");
                }
            } catch (SQLException ex) {
                showAlert("Error occurred while deleting coupon: " + ex.getMessage());
            }
        }
    }

    @FXML
    private void btnReset(ActionEvent event
    ) {
        initialize(null, null);
        showAlert("Reload successfully !");
    }

    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Message");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    //handle search 
    private void handleSearch() {
        String searchText = txtSearch.getText().trim().toLowerCase();

        if (searchText.isEmpty()) {

            tbCoupon.setItems(CouponList);
        } else {
            // Tạo một danh sách mới để lưu các mục tìm kiếm phù hợp
            ObservableList<Coupon> searchResults = FXCollections.observableArrayList();

            // Lặp qua danh sách hiện tại và thêm các mục phù hợp vào danh sách kết quả
            for (Coupon c : CouponList) {
                if (c.getCouponid().toLowerCase().contains(searchText)) {
                    searchResults.add(c);
                }
            }
            // Đặt lại dữ liệu cho bảng hiển thị
            tbCoupon.setItems(searchResults);
        }
    }

    //handle select comboBox Filter Coupon
    private void handleCBFilter() {
        String selectedItem = cbFilter.getValue();
        ConnectDB cnDB = new ConnectDB();
        con = cnDB.getConnect();
        if (selectedItem == null) {
            return; // Bỏ qua nếu giá trị được chọn là null
        }
        switch (selectedItem) {
            case "See all":
                // Hiển thị tất cả các coupon
                String sqlAll = "SELECT * FROM coupon";
                try {
                    stm = con.createStatement();
                    rs = stm.executeQuery(sqlAll);

                    ObservableList<Coupon> couponListAll = FXCollections.observableArrayList();

                    while (rs.next()) {
                        String couponID = rs.getString("couponid");
                        int value = rs.getInt("value");
                        String expiryDate = rs.getString("expirydate");
                        int status = rs.getInt("status");

                        couponListAll.add(new Coupon(couponID, value, expiryDate, status));
                    }

                    tbCoupon.setItems(couponListAll);

                } catch (SQLException ex) {
                    Logger.getLogger(CouponController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case "Coupon unexpired":
                // Lọc và hiển thị các coupon còn hạn
                LocalDate currentDate = LocalDate.now();
                String sqlExpired = "SELECT * FROM coupon WHERE expirydate > ?";

                try (PreparedStatement pstmt = con.prepareStatement(sqlExpired)) {
                    pstmt.setDate(1, java.sql.Date.valueOf(currentDate));
                    rs = pstmt.executeQuery();

                    ObservableList<Coupon> expiredCoupons = FXCollections.observableArrayList();

                    while (rs.next()) {
                        String couponID = rs.getString("couponid");
                        int value = rs.getInt("value");
                        String expiryDate = rs.getString("expirydate");
                        int status = rs.getInt("status");

                        expiredCoupons.add(new Coupon(couponID, value, expiryDate, status));
                    }

                    tbCoupon.setItems(expiredCoupons);

                } catch (SQLException ex) {
                    Logger.getLogger(CouponController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case "Coupon ready":
                // Lọc và hiển thị các coupon sẵn sàng sử dụng
                String sqlReady = "SELECT * FROM coupon where status = 'false'";
                try {
                    stm = con.createStatement();
                    rs = stm.executeQuery(sqlReady);

                    ObservableList<Coupon> couponListReady = FXCollections.observableArrayList();

                    while (rs.next()) {
                        String couponID = rs.getString("couponid");
                        int value = rs.getInt("value");
                        String expiryDate = rs.getString("expirydate");
                        int status = rs.getInt("status");

                        couponListReady.add(new Coupon(couponID, value, expiryDate, status));
                    }

                    tbCoupon.setItems(couponListReady);

                } catch (SQLException ex) {
                    Logger.getLogger(CouponController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            default:
                showAlert("Unknown filter selected!");
                break;
        }
    }

}
