/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package group2.g2store;

import java.sql.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author VICTUS
 */
public class KhachhangController implements Initializable {

    @FXML
    private TableColumn<Customer, String> colCustomerId;
    @FXML
    private TableColumn<Customer, String> colCustomerName;
    @FXML
    private TableColumn<Customer, String> colCustomerPhone;
    @FXML
    private TableColumn<Customer, String> colCustomerDob;
    @FXML
    private TableColumn<Customer, Integer> colCustomerPoint;
    @FXML
    private TableView<Customer> tvCustomer;
    ConnectDB con;
    Connection cn;
    @FXML
    private AnchorPane viewCustomer;
    @FXML
    private AnchorPane formAddCustomer;
    @FXML
    private Button btnAddCustomer;
    @FXML
    private AnchorPane updateCustomer;
    @FXML
    private Button btnCustomerAdd;

    private TextField tfCustomerId;
    @FXML
    private TextField tfCustomerName;
    @FXML
    private TextField tfCustomerPhone;
    @FXML
    private DatePicker tfCustomerDob;
    @FXML
    private TextField tfCustomerPoint;
    @FXML
    private Button showUpdateCustomer;
    @FXML
    private TextField tfCustomerUId;
    @FXML
    private TextField tfCustomerUName;
    @FXML
    private TextField tfCustomerUPhone;
    @FXML
    private DatePicker tfCustomerUDob;
    @FXML
    private TextField tfCustomerUPoint;
    @FXML
    private Button resetCustomer;
    @FXML
    private Button newCustomer;

    @FXML
    private TableView<Customer> tvCustomerView;
    @FXML
    private TableColumn<Customer, String> colCustomerId1;
    @FXML
    private TableColumn<Customer, String> colCustomerName1;
    @FXML
    private TableColumn<Customer, String> colCustomerPhone1;
    @FXML
    private TableColumn<Customer, String> colCustomerDob1;
    @FXML
    private TableColumn<Customer, Integer> colCustomerPoint1;
//    @FXML
//    private Button showDetailCustomer1;
    
//    @FXML
//    private TableColumn<Customer, String> colCustomerId2;
//    @FXML
//    private TableColumn<Customer, String> colCustomerName2;
//    @FXML
//    private TableColumn<Customer, String> colCustomerPhone2;
//    @FXML
//    private TableColumn<Customer, String> colCustomerDob2;
//    @FXML
//    private TableColumn<Customer, Integer> colCustomerPoint2;
//    @FXML
    @FXML
    private TextField tfCustomerDId1;
    @FXML
    private TextField tfCustomerDName1;
    @FXML
    private TextField tfCustomerDPhone1;
    @FXML
    private DatePicker tfCustomerDDob1;
    @FXML
    private TextField tfCustomerDPoint1;
    private TableView<Customer> tvCustomer2;
    @FXML
    private Button btnHome1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = new ConnectDB();
        cn = con.getConnect();
        showCustomers();
        showCustomers1();
    }

    @FXML
    private void handleHome(MouseEvent event) {
    }

    public ObservableList getCustomers() {

        //tao ds chua cac Product
        ObservableList<Customer> list = FXCollections.observableArrayList();
        //tao doi tuong
        list.clear();

        //viet cau lenh truy van
        String query = "SELECT * FROM Customer";

        //thuc thi
        Statement st;
        ResultSet rs;

        try {
            st = cn.createStatement();
            rs = st.executeQuery(query);
            Customer c;
            while (rs.next()) {
                String customerId = rs.getString("CustomerId");
                String customerName = rs.getString("CustomerName");
                String customerPhone = rs.getString("CustomerPhone");
                String Dob = rs.getString("Dob").substring(0, 10);
                int Point = rs.getInt("Point");

                //tao doi tuong Customer
                c = new Customer(customerId, customerName, customerPhone, Dob, Point);
                //System.out.println(p);
                list.add(c);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public void showCustomers() {
        ObservableList<Customer> cList = getCustomers();

        //show ds ra tableview
        colCustomerId1.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        colCustomerName1.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        colCustomerPhone1.setCellValueFactory(new PropertyValueFactory<>("CustomerPhone"));
        colCustomerDob1.setCellValueFactory(new PropertyValueFactory<>("Dob"));
        colCustomerPoint1.setCellValueFactory(new PropertyValueFactory<>("Point"));

        tvCustomerView.setItems(cList);
    }

    public void showCustomers1() {
        ObservableList<Customer> cList = getCustomers();

        //show ds ra tableview
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        colCustomerPhone.setCellValueFactory(new PropertyValueFactory<>("CustomerPhone"));
        colCustomerDob.setCellValueFactory(new PropertyValueFactory<>("Dob"));
        colCustomerPoint.setCellValueFactory(new PropertyValueFactory<>("Point"));

        tvCustomer.setItems(cList);
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
            showCustomers1();
            showCustomers();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.getMessage());
            alert.show();
            //System.out.println("loi: " + ex.getMessage());
        }
    }

    @FXML
    private void showAddCustomer(ActionEvent event) {
        formAddCustomer.setVisible(true);
        viewCustomer.setVisible(false);
        updateCustomer.setVisible(false);
        //DetailCustomer1.setVisible(false);
    }

    @FXML
    private void AddCustomer(ActionEvent event) {
        //String customerId  = tfCustomerId.getText();

        //String Dob = tfCustomerDob.getValue().toString();
        try {
            if (tfCustomerName.getText().isEmpty()) {
                throw new Exception("Customer Name is not null");
            }
            String customerName = tfCustomerName.getText();

            String customerPhone = tfCustomerPhone.getText();
            Pattern p;
            Matcher m;

            if (tfCustomerPhone.getText().isEmpty()) {
                throw new Exception("Customer Phone is not null");
            }
            p = Pattern.compile("0[0-9]{9}");

            m = p.matcher(tfCustomerPhone.getText());
            if (m.matches()) {

            } else {
                throw new Exception("Customer phone number must be 10 digits and correct format 0xxxxxxxx");
            }

            if (tfCustomerDob.getValue().toString().isEmpty()) {
                throw new Exception("Customer Dob is not null");
            }
            String Dob = tfCustomerDob.getValue().toString();

            if (tfCustomerPoint.getText().isEmpty()) {
                throw new Exception("Customer point is not null");
            }

            int Point = Integer.parseInt(tfCustomerPoint.getText());
            String sql = "INSERT INTO Customer "
                    + "VALUES('" + customerName + "',"
                    + "'" + customerPhone + "',"
                    + "'" + Dob + "',"
                    + +Point + ")";

            //System.out.println(sql);
            executeSQL(sql);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }

    @FXML
    private void resetCustomerAdd(ActionEvent event) {
        tfCustomerId.setText("");
        tfCustomerName.setText("");
        tfCustomerPhone.setText("");
        tfCustomerDob.setValue(null);
        tfCustomerPoint.setText("");
    }

    @FXML
    private void showUpdateCustomer(ActionEvent event) {
        formAddCustomer.setVisible(false);
        viewCustomer.setVisible(false);
        updateCustomer.setVisible(true);
        //DetailCustomer1.setVisible(false);
    }

    @FXML
    private void onSelectCustomer(MouseEvent event) {
        Customer c = tvCustomer.getSelectionModel().getSelectedItem();
        //xuat du lieu
        tfCustomerUId.setText(c.getCustomerId());
        tfCustomerUName.setText(c.getCustomerName());
        tfCustomerUPhone.setText(c.getCustomerPhone());

        tfCustomerUPoint.setText("" + c.getPoint());
        tfCustomerUDob.setValue(LocalDate.parse(c.getDob()));
    }

    @FXML
    private void CustomerUpdate(ActionEvent event) {
        try {

            if (tfCustomerUName.getText().isEmpty()) {
                throw new Exception("Customer Name is not null");
            }
            String customerName = tfCustomerUName.getText();

            String customerPhone = tfCustomerUPhone.getText();
            Pattern p;
            Matcher m;

            if (tfCustomerUPhone.getText().isEmpty()) {
                throw new Exception("Customer Phone is not null");
            }
            p = Pattern.compile("0[0-9]{9}");

            m = p.matcher(tfCustomerUPhone.getText());
            if (m.matches()) {

            } else {
                throw new Exception("Customer phone number must be 10 digits and correct format 0xxxxxxxx");
            }

            if (tfCustomerUDob.getValue().toString().isEmpty()) {
                throw new Exception("Customer Dob is not null");
            }
            String Dob = tfCustomerUDob.getValue().toString();

            if (tfCustomerUPoint.getText().isEmpty()) {
                throw new Exception("Customer point is not null");
            }

            int Point = Integer.parseInt(tfCustomerUPoint.getText());
            String customerId = tfCustomerUId.getText();
            String sql = "UPDATE Customer SET "
                    + "customerName='" + customerName + "',"
                    + "customerPhone= " + customerPhone + ","
                    + "Dob='" + Dob + "',"
                    + "Point=" + Point
                    + " WHERE customerId=" + customerId;

            System.out.println(sql);
            executeSQL(sql);

            //load lai du lieu
            showCustomers1();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }

//        String customerId = tfCustomerUId.getText();
//        String customerName = tfCustomerUName.getText();
//        String customerPhone = tfCustomerUPhone.getText();
//        String Dob = tfCustomerUDob.getValue().toString();
//        
//        int Point = Integer.parseInt(tfCustomerUPoint.getText());
    }

    @FXML
    private void resetCustomer(ActionEvent event) {
        tfCustomerUId.setText("");
        tfCustomerUName.setText("");
        tfCustomerUPhone.setText("");
        tfCustomerUDob.setValue(null);
        tfCustomerUPoint.setText("");

    }

    @FXML
    private void newCustomer(ActionEvent event) {
        formAddCustomer.setVisible(false);
        viewCustomer.setVisible(true);
        updateCustomer.setVisible(false);
        //DetailCustomer1.setVisible(false);
    }
    
//    public void showCustomers2() {
//        ObservableList<Customer> cList = getCustomers();
//
//        //show ds ra tableview
//        colCustomerId2.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
//        colCustomerName2.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
//        colCustomerPhone2.setCellValueFactory(new PropertyValueFactory<>("CustomerPhone"));
//        colCustomerDob2.setCellValueFactory(new PropertyValueFactory<>("Dob"));
//        colCustomerPoint2.setCellValueFactory(new PropertyValueFactory<>("Point"));
//
//        tvCustomer2.setItems(cList);
//    }

    @FXML
    private void onSelectCustomer1(MouseEvent event) {
        Customer c = (Customer) tvCustomerView.getSelectionModel().getSelectedItem();
        //xuat du lieu
        tfCustomerDId1.setText(c.getCustomerId());
        tfCustomerDName1.setText(c.getCustomerName());
        tfCustomerDPhone1.setText(c.getCustomerPhone());

        tfCustomerDPoint1.setText("" + c.getPoint());
        tfCustomerDDob1.setValue(LocalDate.parse(c.getDob()));
    }
//
//    @FXML
//    private void showDetailCustomer(ActionEvent event) {
//        formAddCustomer.setVisible(false);
//        viewCustomer.setVisible(false);
//        updateCustomer.setVisible(false);
//        DetailCustomer1.setVisible(true);
//    }
//
//    private void CustomerDetail(ActionEvent event) {
//        String customerId = tfCustomerDId1.getText();
//        String customerName = tfCustomerDName1.getText();
//        String customerPhone = tfCustomerDPhone1.getText();
//        String Dob = tfCustomerDDob1.getValue().toString();
//
//        int Point = Integer.parseInt(tfCustomerDPoint1.getText());
//
//        
//    }
    

}
