/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package group2.g2store;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author Luu Bao
 */
public class StaticsController implements Initializable {

    ConnectDB cnDB;
    private Connection con;
    private Statement stm;
    private PreparedStatement ps;
    private ResultSet rs;

    @FXML
    private Button btnHome;
    @FXML
    private LineChart<String, Number> OverviewLineChart;
    @FXML
    private TableView<Statics> tvOverview;
    @FXML
    private Label lbQuantityProduct;
    @FXML
    private Label lbNumberCustormer;
    @FXML
    private Label lbQuantityEmp;
    @FXML
    private TableColumn<Statics, String> DayColOverview;
    @FXML
    private TableColumn<Statics, Float> FundsColOverview;
    @FXML
    private TableColumn<Statics, Float> RevenueCol;
    @FXML
    private TableColumn<Statics, Float> ProfitCol;
    private ObservableList<Statics> StaticsList;
    private ObservableList<Product> ProductList;
    @FXML
    private TableView<Product> tvInventory;
    @FXML
    private TableColumn<Product, Integer> STTColumnTvInventory;
    @FXML
    private TableColumn<Product, String> ProductIDCol;
    @FXML
    private TableColumn<Product, String> ProductNameCol;
    @FXML
    private TableColumn<Product, Integer> NKIDCol;
    @FXML
    private TableColumn<Product, Integer> ImportCol;
    @FXML
    private TableColumn<Product, Integer> InventoryCol;
    @FXML
    private TableColumn<Product, Integer> SoldCol;
    @FXML
    private TextField tfSearchInventory;
    @FXML
    private ComboBox<String> cbCatInvetory;
    private ObservableList<String> CatList;
    @FXML
    private Button btnReLoad;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        // ket noi database 
        cnDB = new ConnectDB();
        con = cnDB.getConnect();
        showOverViewTab();
        showInventoryTab();
        // HIEN THI TABLE VIEW IN TAB OVERVIEW 
    }

    public void showOverViewTab() {
        String Day4 = LocalDate.now().toString();
        String Day3 = LocalDate.now().minusDays(1).toString();
        String Day2 = LocalDate.now().minusDays(2).toString();
        String Day1 = LocalDate.now().minusDays(3).toString();

        // vốn
        XYChart.Series<String, Number> funds = new XYChart.Series<>();
        XYChart.Data<String, Number> day1 = new XYChart.Data<>(Day1, getFunds(Day1));
        XYChart.Data<String, Number> day2 = new XYChart.Data<>(Day2, getFunds(Day2));
        XYChart.Data<String, Number> day3 = new XYChart.Data<>(Day3, getFunds(Day3));
        XYChart.Data<String, Number> day4 = new XYChart.Data<>(Day4, getFunds(Day4));

        funds.getData().addAll(day1, day2, day3, day4);
        funds.setName("Vốn");

        XYChart.Series<String, Number> revenue = new XYChart.Series<>();
        XYChart.Data<String, Number> rday1 = new XYChart.Data<>(Day1, getRevenue(Day1));
        XYChart.Data<String, Number> rday2 = new XYChart.Data<>(Day2, getRevenue(Day2));
        XYChart.Data<String, Number> rday3 = new XYChart.Data<>(Day3, getRevenue(Day3));
        XYChart.Data<String, Number> rday4 = new XYChart.Data<>(Day4, getRevenue(Day4));
        revenue.getData().addAll(rday1, rday2, rday3, rday4);
        revenue.setName("Doanh thu");

        XYChart.Series<String, Number> profit = new XYChart.Series<>();
        XYChart.Data<String, Number> pday1 = new XYChart.Data<>(Day1, getProfit(Day1));
        XYChart.Data<String, Number> pday2 = new XYChart.Data<>(Day2, getProfit(Day2));
        XYChart.Data<String, Number> pday3 = new XYChart.Data<>(Day3, getProfit(Day3));
        XYChart.Data<String, Number> pday4 = new XYChart.Data<>(Day4, getProfit(Day4));

        profit.getData().addAll(pday1, pday2, pday3, pday4);
        profit.setName("Lợi nhuận");
        OverviewLineChart.getData().addAll(revenue, funds, profit);

        lbQuantityProduct.setText(getNumberProduct() + "");

        lbNumberCustormer.setText(getNumberCustomer() + "");

        lbQuantityEmp.setText(getNumberEmployee() + "");

        // HIEN THI TABLE VIEW IN TAB OVERVIEW 
        StaticsList = FXCollections.observableArrayList();

        StaticsList.clear();
        StaticsList.add(new Statics(Day1, getFunds(Day1), getRevenue(Day1), getProfit(Day1)));
        StaticsList.add(new Statics(Day2, getFunds(Day2), getRevenue(Day2), getProfit(Day2)));
        StaticsList.add(new Statics(Day3, getFunds(Day3), getRevenue(Day3), getProfit(Day3)));
        StaticsList.add(new Statics(Day4, getFunds(Day4), getRevenue(Day4), getProfit(Day4)));

        tvOverview.setItems(StaticsList);
        DayColOverview.setCellValueFactory(new PropertyValueFactory<>("dateStatics"));
        FundsColOverview.setCellValueFactory(new PropertyValueFactory<>("funds"));
        RevenueCol.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        ProfitCol.setCellValueFactory(new PropertyValueFactory<>("profit"));

    }

    public void showInventoryTab() {
        STTColumnTvInventory.setCellValueFactory(data -> {
            Product product = data.getValue();
            int rowIndex = tvInventory.getItems().indexOf(product) + 1;
            return new SimpleIntegerProperty(rowIndex).asObject();

        });

        tvInventory.setItems(getProduct());
        ProductIDCol.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        ProductNameCol.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        NKIDCol.setCellValueFactory(new PropertyValueFactory<>("NKId"));
        ImportCol.setCellValueFactory(new PropertyValueFactory<>("ImportQuantity"));
        SoldCol.setCellValueFactory(new PropertyValueFactory<>("SoldQuantity"));
        InventoryCol.setCellValueFactory(new PropertyValueFactory<>("Inventory"));

        tfSearchInventory.setOnKeyReleased(event -> {
            String keyword = tfSearchInventory.getText().trim();
//            filterCategory.setValue(null); // Reset giá trị của filterCategory
//            filterBrand.setValue(null); // Reset giá trị của filterBrand
            searchProductsbyName(keyword);

        });

        cbCatInvetory.setItems(getCategory());
        cbCatInvetory.setOnAction(event -> {
            if (!(cbCatInvetory.getValue().isEmpty())) {
                String keyword = cbCatInvetory.getValue();
                searchProductsbyCategory(keyword);
            }

        });
        btnReLoad.setOnAction(event -> {
            tvInventory.setItems(getProduct());
            tvInventory.scrollTo(0);
        });
    }

    public int getNumberProduct() {
        int NumberProduct = 0;
        String query = "SELECT COUNT(ProductId) FROM Product";
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(query);
            while (rs.next()) {
                NumberProduct = rs.getInt(1);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return NumberProduct;

    }

    public int getNumberCustomer() {
        int NumberCustomer = 0;
        String query = "SELECT COUNT(CustomerID) FROM Customer";
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(query);
            while (rs.next()) {
                NumberCustomer = rs.getInt(1);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return NumberCustomer;
    }

    public int getNumberEmployee() {
        int NumberEmp = 0;
        String query = "SELECT COUNT(EmpID) FROM Employee";
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(query);
            while (rs.next()) {
                NumberEmp = rs.getInt(1);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return NumberEmp;

    }

    public float getFunds(String Day) {
        Float fund = 0f;
        String query = "SELECT SUM(OD.Quantity * PD.ImportPrice) FROM OrderDetail OD JOIN ProductDetail PD ON OD.NKId = PD.NKId AND OD.ProductId = PD.ProductId\n"
                + "									 JOIN Orders O ON OD.OrderId = O.OrderId\n"
                + "									 WHERE O.DateTimeOrder = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, Day);
            rs = ps.executeQuery();

            while (rs.next()) {
                fund = rs.getFloat(1);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return fund;
    }

    public float getRevenue(String Day) {
        Float Revenue = 0f;
        String query = "SELECT DISTINCT(O.TOTAL) \n"
                + "FROM Orders O JOIN OrderDetail OD ON O.OrderId = OD.OrderId \n"
                + "\n"
                + "\n"
                + "WHERE O.DateTimeOrder = ? ";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, Day);
            rs = ps.executeQuery();

            while (rs.next()) {
                Revenue = rs.getFloat(1);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Revenue;
    }

    public float getProfit(String Day) {
        return getRevenue(Day) - getFunds(Day);
    }

    public ObservableList<Product> getProduct() {
        ProductList = FXCollections.observableArrayList();
        ProductList.clear();
        String sql = "SELECT P.ProductId, P.ProductName, PD.NKId, PD.ImportQuantity, PD.SoldQuantity, (PD.ImportQuantity - PD.SoldQuantity) AS INVENTORY, C.CategoryName\n"
                + "\n"
                + "                FROM Product P JOIN ProductDetail PD ON P.ProductId = PD.ProductId\n"
                + "                JOIN Category C ON P.CategoryID = C.CategoryID";
        try {

            stm = con.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                Product p = new Product(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getString(7));
                ProductList.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ProductList;
    }

    public void searchProductsbyName(String keyword) {

        List<Product> pList;

        pList = getProduct().stream().filter(p -> p.getProductName().contains(keyword)).collect(Collectors.toList());
        ProductList = FXCollections.observableArrayList(pList);
        tvInventory.setItems(ProductList);
    }

    public ObservableList<String> getCategory() {
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
                String CategoryName = rs.getString("CategoryName");
                CatList.add(CategoryName);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return CatList;
    }

    public void searchProductsbyCategory(String keyword) {

        List<Product> pList;

        pList = getProduct().stream().filter(p -> p.getCategory().equalsIgnoreCase(keyword)).collect(Collectors.toList());
        ProductList = FXCollections.observableArrayList(pList);
        tvInventory.setItems(ProductList);
    }

    @FXML
    private void handleHome(MouseEvent event) {
    }

}
