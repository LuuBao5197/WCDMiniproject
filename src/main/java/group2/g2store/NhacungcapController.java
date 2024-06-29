/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package group2.g2store;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author VICTUS
 */
public class NhacungcapController implements Initializable {

    @FXML
    private TableView<Suppiler> tvSuppiler;
    @FXML
    private TableColumn<Suppiler, Integer> colSuppilerCode;
    @FXML
    private TableColumn<Suppiler, String> colSuppilerName;
    @FXML
    private TableColumn<Suppiler, String> colSuppilerAddress;
    @FXML
    private TableColumn<Suppiler, String> colSuppilerEmail;
    @FXML
    private TableColumn<Suppiler, String> colSuppilerPhonnumber;
    ConnectDB con;
    Connection cn;
    @FXML
    private TextField tfSuppilerCode;
    @FXML
    private TextField tfSuppilerName;
    @FXML
    private TextField tfSuppilerAddress;
    @FXML
    private TextField tfSuppilerEmail;
    @FXML
    private TextField tfSuppilerPhone;
    @FXML
    private Button newSuppiler;
    @FXML
    private Button btnHome1;
    @FXML
    private TextField tfSearchSupplier;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = new ConnectDB();
        cn = con.getConnect();
        showSuppilers();
    }

    @FXML
    private void handleHome(MouseEvent event) {
    }

    public ObservableList<Suppiler> getSuppilers() {

        //tao ds chua cac Suppiler
        ObservableList<Suppiler> list = FXCollections.observableArrayList();
        //tao doi tuong
        list.clear();

        //viet cau lenh truy van
        String query = "SELECT * FROM Suppiler";

        //thuc thi
        Statement st;
        ResultSet rs;

        try {
            st = cn.createStatement();
            rs = st.executeQuery(query);
            Suppiler s;
            while (rs.next()) {
                int SuppilerCode = rs.getInt("SuppilerCode");
                String SuppilerName = rs.getString("SuppilerName");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");

                //tao doi tuong Suppiler
                s = new Suppiler(SuppilerCode, SuppilerName, address, email, phoneNumber);
                //System.out.println(s);
                list.add(s);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public void showSuppilers() {
        ObservableList<Suppiler> sList = getSuppilers();

        //show ds ra tableview
        colSuppilerCode.setCellValueFactory(new PropertyValueFactory<>("SuppilerCode"));
        colSuppilerName.setCellValueFactory(new PropertyValueFactory<>("SuppilerName"));
        colSuppilerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSuppilerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSuppilerPhonnumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        tvSuppiler.setItems(sList);
    }

    private void executeSQL(String query) {
        Statement st;
        try {
            st = cn.createStatement();

            //thuc thi
            st.executeUpdate(query);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("result");
            alert.setContentText("Operation successful!");
            alert.show();
            //load du lieu
            showSuppilers();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.getMessage());
            alert.show();
//            System.out.println("loi: " + ex.getMessage());
        }
    }

    @FXML
    private void SuppilerAdd(ActionEvent event) {

        //get gtri cua cua field
//        int SuppilerCode  = Integer.parseInt(tfSuppilerCode.getText());
        try {
            if (tfSuppilerName.getText().isEmpty()) {
                throw new Exception("Suppiler Name is not null");
            }
            String SuppilerName = tfSuppilerName.getText();
            if (tfSuppilerAddress.getText().isEmpty()) {
                throw new Exception("Suppiler Address is not null");
            }
            String address = tfSuppilerAddress.getText();
            Pattern p;
            Matcher m;

            //id: EXX: bat dau la chu E tiep thep la 2 ky so
            p = Pattern.compile("[a-zA-Z0-9]{1,}@[a-zA-Z0-9]{1,}\\.(com)");

            m = p.matcher(tfSuppilerEmail.getText());
            if (m.matches()) {

            } else {
                throw new Exception("Suppiler Email incorrect format");
            }

            if (tfSuppilerEmail.getText().isEmpty()) {
                throw new Exception("Suppiler Email is not null");
            }
            String email = tfSuppilerEmail.getText();
            if (tfSuppilerPhone.getText().isEmpty()) {
                throw new Exception("Suppiler Phone is not null");
            }
            p = Pattern.compile("0[0-9]{9}");

            m = p.matcher(tfSuppilerPhone.getText());
            if (m.matches()) {

            } else {
                throw new Exception("Suppiler phone number must be 10 digits and correct format 0xxxxxxxx ");
            }

            String phoneNumber = tfSuppilerPhone.getText();
            String sql = "INSERT INTO Suppiler "
                    + "VALUES('" + SuppilerName + "',"
                    + "'" + address + "',"
                    + "'" + email + "',"
                    + "'" + phoneNumber + "')";

            //System.out.println(sql);
            executeSQL(sql);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }

    @FXML
    private void onSelectSuppiler(MouseEvent event) {
        Suppiler s = tvSuppiler.getSelectionModel().getSelectedItem();
        //xuat du lieu
        tfSuppilerCode.setText("" + s.getSuppilerCode());
        tfSuppilerName.setText(s.getSuppilerName());
        tfSuppilerAddress.setText(s.getAddress());
        tfSuppilerEmail.setText(s.getEmail());
        tfSuppilerPhone.setText(s.getPhoneNumber());
    }

    @FXML
    private void SuppilerUpdate(ActionEvent event) {

        //get gtri cua cua field
        //int SuppilerCode = Integer.parseInt(tfSuppilerCode.getText());
//        String SuppilerName = tfSuppilerName.getText();
//        String address = tfSuppilerAddress.getText();
//        String email = tfSuppilerEmail.getText();
//        String phoneNumber = tfSuppilerPhone.getText();
        try {
            int SuppilerCode = Integer.parseInt(tfSuppilerCode.getText());

            if (tfSuppilerName.getText().isEmpty()) {
                throw new Exception("Suppiler Name is not null");
            }
            String SuppilerName = tfSuppilerName.getText();
            if (tfSuppilerAddress.getText().isEmpty()) {
                throw new Exception("Suppiler Address is not null");
            }
            String address = tfSuppilerAddress.getText();
            Pattern p;
            Matcher m;

            //id: EXX: bat dau la chu E tiep thep la 2 ky so
            p = Pattern.compile("[a-zA-Z0-9]{1,}@[a-zA-Z0-9]{1,}\\.(com)");

            m = p.matcher(tfSuppilerEmail.getText());
            if (m.matches()) {

            } else {
                throw new Exception("Suppiler Email incorrect format");
            }

            if (tfSuppilerEmail.getText().isEmpty()) {
                throw new Exception("Suppiler Email is not null");
            }
            String email = tfSuppilerEmail.getText();
            if (tfSuppilerPhone.getText().isEmpty()) {
                throw new Exception("Suppiler Phone is not null");
            }
            p = Pattern.compile("0[0-9]{9}");

            m = p.matcher(tfSuppilerPhone.getText());
            if (m.matches()) {

            } else {
                throw new Exception("Suppiler phone number must be 10 digits and correct format 0xxxxxxxx");
            }
            String phoneNumber = tfSuppilerPhone.getText();

            String sql = "UPDATE Suppiler SET "
                    + "SuppilerName='" + SuppilerName + "',"
                    + "address='" + address + "',"
                    + "email='" + email + "',"
                    + "phoneNumber='" + phoneNumber + "'"
                    + "WHERE SuppilerCode=" + SuppilerCode + "";

            //System.out.println(sql);
            executeSQL(sql);

            //load lai du lieu
            showSuppilers();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
        @FXML
        private void newSuppiler
        (ActionEvent event
        
            ) {
        tfSuppilerCode.setText("");
            tfSuppilerName.setText("");
            tfSuppilerAddress.setText("");
            tfSuppilerEmail.setText("");
            tfSuppilerPhone.setText("");
        }

    @FXML
    private void handleSearchSupplier(KeyEvent event) {
        String keyword = tfSearchSupplier.getText();
        searchSupplierbyName(keyword);
        
    
    }
    public void searchSupplierbyName(String keyword){
        List<Suppiler> pList;
        pList = getSuppilers().stream().filter(m -> m.getSuppilerName().toLowerCase().contains(keyword.toLowerCase())||m.getAddress().toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList());
        ObservableList<Suppiler> resultList = FXCollections.observableArrayList(pList);
        tvSuppiler.setItems(resultList);
    }
}

