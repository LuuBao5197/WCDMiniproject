/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package group2.g2store;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author Luu Bao
 */
public class StaticsController implements Initializable {

    private static int COLUMN_INDEX_PRODUCTID = 0;
    private static int COLUMN_INDEX_PRODUCTNAME = 1;
    private static int COLUMN_INDEX_IMPORTQUANTITY = 2;
    private static int COLUMN_INDEX_SOLDQUANTITY = 3;
    private static int COLUMN_INDEX_INVENTORY = 4;
    private static Object cellStyleFormatNumber = null;

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
    @FXML
    private BarChart<String, Number> BarChatforYear;
    @FXML
    private TableView<Statics> tvRevenue;
    @FXML
    private TableColumn<Statics, Integer> YearRevenueCol;
    @FXML
    private TableColumn<Statics, Number> FundsColFundYear;
    @FXML
    private TableColumn<Statics, Number> RevenueColRevenueYear;
    @FXML
    private TableColumn<Statics, Number> ProfitColRevenueYear;
    @FXML
    private TextField tfFromYear;
    @FXML
    private TextField tfToYear;

    int fromYear, ToYear;
    @FXML
    private Spinner<Integer> ChoiceYearSpinner;
    @FXML
    private BarChart<String, Number> BarChartForMonth;
    @FXML
    private TableView<Statics> tvRevenue1;
    @FXML
    private TableColumn<Statics, String> MonthRevenue;
    @FXML
    private TableColumn<Statics, Number> FundsColFundMonth;
    @FXML
    private TableColumn<Statics, Number> RevenueColRevenueMonth;
    @FXML
    private TableColumn<Statics, Number> ProfitColRevenueMonth;
    @FXML
    private Spinner<Integer> ChoiceMonthSpinner;
    @FXML
    private Spinner<Integer> ChoiceYearSpinner3;
    @FXML
    private BarChart<String, Number> BarChartForMonth1;
    @FXML
    private TableColumn<?, ?> MonthRevenue1;
    @FXML
    private TableColumn<?, ?> FundsColFundMonth1;
    @FXML
    private TableColumn<?, ?> RevenueColRevenueMonth1;
    @FXML
    private TableColumn<?, ?> ProfitColRevenueMonth1;
    @FXML
    private TableView<Statics> tvRevenue2;
    @FXML
    private DatePicker DatePickerDateFrom;
    @FXML
    private DatePicker DatePickerDateTo;
    @FXML
    private TableColumn<?, ?> MonthRevenue11;
    @FXML
    private TableColumn<?, ?> FundsColFundMonth11;
    @FXML
    private TableColumn<?, ?> RevenueColRevenueMonth11;
    @FXML
    private TableColumn<?, ?> ProfitColRevenueMonth11;
    @FXML
    private TableView<Statics> tvRevenue3;

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
        showRevenueTab(2020, LocalDate.now().getYear());
        showRevenuebyMonth(2024);
        showRevenuebyDay(LocalDate.now().getMonthValue(), LocalDate.now().getYear());

        // HIEN THI TABLE VIEW IN TAB OVERVIEW 
    }

    public void showRevenueTab(int YearFrom, int YearTo) {

        // ==========================TAB THỐNG KÊ THEO NĂM ====================
        XYChart.Series<String, Number> funds = new XYChart.Series<>();
        XYChart.Series<String, Number> revenue = new XYChart.Series<>();
        XYChart.Series<String, Number> profit = new XYChart.Series<>();
        ObservableList<XYChart.Data<String, Number>> itemFundList = FXCollections.observableArrayList();
        itemFundList.clear();
        ObservableList<XYChart.Data<String, Number>> itemRevenueList = FXCollections.observableArrayList();
        itemRevenueList.clear();
        ObservableList<XYChart.Data<String, Number>> itemProfitList = FXCollections.observableArrayList();
        itemProfitList.clear();

        ObservableList<Statics> StaticsListByYearList = FXCollections.observableArrayList();

        StaticsListByYearList.clear();
        for (int i = YearFrom; i <= YearTo; i++) {
            XYChart.Data<String, Number> fundsYear = new XYChart.Data<>(i + "", getFundsYear(i));
            itemFundList.add(fundsYear);
            XYChart.Data<String, Number> revenueYear = new XYChart.Data<>(i + "", getRevenueYear(i));
            itemRevenueList.add(revenueYear);
            XYChart.Data<String, Number> profitYear = new XYChart.Data<>(i + "", getProfitYear(i));
            itemProfitList.add(profitYear);
            StaticsListByYearList.add(new Statics(i, getFundsYear(i), getRevenueYear(i), getProfitYear(i)));

        }
        funds.setData(itemFundList);
        revenue.setData(itemRevenueList);
        profit.setData(itemProfitList);
        funds.setName("Vốn");
        profit.setName("Lợi nhuận");
        revenue.setName("Doanh thu");
        BarChatforYear.getData().addAll(revenue, funds, profit);

        // HIEN THI TABLE VIEW IN TAB Revenue
        tvRevenue.setItems(StaticsListByYearList);
        YearRevenueCol.setCellValueFactory(new PropertyValueFactory<>("YearStatics"));
        FundsColFundYear.setCellValueFactory(new PropertyValueFactory<>("funds"));
        RevenueColRevenueYear.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        ProfitColRevenueYear.setCellValueFactory(new PropertyValueFactory<>("profit"));

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2019, LocalDate.now().getYear(), LocalDate.now().getYear());
        ChoiceYearSpinner.setValueFactory(valueFactory);

        SpinnerValueFactory<Integer> monthListFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, LocalDate.now().getMonthValue());

        ChoiceYearSpinner3.setValueFactory(valueFactory);
        ChoiceMonthSpinner.setValueFactory(monthListFactory);

    }

    public void showRevenuebyDay(int Month, int Year) {
        XYChart.Series<String, Number> funds = new XYChart.Series<>();
        XYChart.Series<String, Number> revenue = new XYChart.Series<>();
        XYChart.Series<String, Number> profit = new XYChart.Series<>();
        ObservableList<XYChart.Data<String, Number>> itemFundList = FXCollections.observableArrayList();
        itemFundList.clear();
        ObservableList<XYChart.Data<String, Number>> itemRevenueList = FXCollections.observableArrayList();
        itemRevenueList.clear();
        ObservableList<XYChart.Data<String, Number>> itemProfitList = FXCollections.observableArrayList();
        itemProfitList.clear();

        ObservableList<Statics> StaticsListByYearList = FXCollections.observableArrayList();

        StaticsListByYearList.clear();
        for (int i = 1; i <= 31; i = i + 3) {
            if (i >= 28) {
                XYChart.Data<String, Number> fundsYear = new XYChart.Data<>(i + "-" + (i + 3), getFundsDayInMonth(Month, Year, i, i + 3));
                itemFundList.add(fundsYear);
                XYChart.Data<String, Number> revenueYear = new XYChart.Data<>(i + "-" + (i + 3), getRevenueDayInMonth(Month, Year, i, i + 3));
                itemRevenueList.add(revenueYear);
                XYChart.Data<String, Number> profitYear = new XYChart.Data<>(i + "-" + (i + 3), getRevenueDayInMonth(Month, Year, i, i + 3) - getFundsDayInMonth(Month, Year, i, i + 3));
                itemProfitList.add(profitYear);
                StaticsListByYearList.add(new Statics(i + "-" + (i + 3), Year, getFundsDayInMonth(Month, Year, i, i + 3), getRevenueDayInMonth(Month, Year, i, i + 3), getRevenueDayInMonth(Month, Year, i, i + 3) - getFundsDayInMonth(Month, Year, i, i + 3)));
                break;
            } else {
                XYChart.Data<String, Number> fundsYear = new XYChart.Data<>(i + "-" + (i + 2), getFundsDayInMonth(Month, Year, i, i + 2));
                itemFundList.add(fundsYear);
                XYChart.Data<String, Number> revenueYear = new XYChart.Data<>(i + "-" + (i + 2), getRevenueDayInMonth(Month, Year, i, i + 2));
                itemRevenueList.add(revenueYear);
                XYChart.Data<String, Number> profitYear = new XYChart.Data<>(i + "-" + (i + 2), getRevenueDayInMonth(Month, Year, i, i + 2) - getFundsDayInMonth(Month, Year, i, i + 2));
                itemProfitList.add(profitYear);
                StaticsListByYearList.add(new Statics(i + "-" + (i + 2), Year, getFundsDayInMonth(Month, Year, i, i + 2), getRevenueDayInMonth(Month, Year, i, i + 2), getRevenueDayInMonth(Month, Year, i, i + 2) - getFundsDayInMonth(Month, Year, i, i + 2)));
            }

        }
        funds.setData(itemFundList);
        revenue.setData(itemRevenueList);
        profit.setData(itemProfitList);
        funds.setName("Vốn");
        profit.setName("Lợi nhuận");
        revenue.setName("Doanh thu");
        BarChartForMonth1.getData().addAll(profit, funds, revenue);

        tvRevenue2.setItems(StaticsListByYearList);
        MonthRevenue1.setCellValueFactory(new PropertyValueFactory<>("MonthStatics"));
        FundsColFundMonth1.setCellValueFactory(new PropertyValueFactory<>("funds"));
        RevenueColRevenueMonth1.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        ProfitColRevenueMonth1.setCellValueFactory(new PropertyValueFactory<>("profit"));
    }

    public void showRevenueFromDaytoDay(LocalDate fromDay, LocalDate toDay) {
        long daysBetween = ChronoUnit.DAYS.between(fromDay, toDay);

        ObservableList<Statics> StaticsListByYearList = FXCollections.observableArrayList();

        StaticsListByYearList.clear();
        for (int i = 0; i <= daysBetween; i++) {

            StaticsListByYearList.add(new Statics(fromDay + "", getFunds(fromDay.toString()), getRevenue(fromDay.toString()), getProfit(fromDay.toString())));
            fromDay = fromDay.plusDays(1);
        }

        tvRevenue3.setItems(StaticsListByYearList);
        MonthRevenue11.setCellValueFactory(new PropertyValueFactory<>("dateStatics"));
        FundsColFundMonth11.setCellValueFactory(new PropertyValueFactory<>("funds"));
        RevenueColRevenueMonth11.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        ProfitColRevenueMonth11.setCellValueFactory(new PropertyValueFactory<>("profit"));
    }

    public void showRevenuebyMonth(int Year) {
        XYChart.Series<String, Number> funds = new XYChart.Series<>();
        XYChart.Series<String, Number> revenue = new XYChart.Series<>();
        XYChart.Series<String, Number> profit = new XYChart.Series<>();
        ObservableList<XYChart.Data<String, Number>> itemFundList = FXCollections.observableArrayList();
        itemFundList.clear();
        ObservableList<XYChart.Data<String, Number>> itemRevenueList = FXCollections.observableArrayList();
        itemRevenueList.clear();
        ObservableList<XYChart.Data<String, Number>> itemProfitList = FXCollections.observableArrayList();
        itemProfitList.clear();
        ObservableList<Statics> StaticsListByYearList = FXCollections.observableArrayList();
        StaticsListByYearList.clear();
//   
        for (int i = 1; i < 13; i++) {
            XYChart.Data<String, Number> fundsYear = new XYChart.Data<>(i + "/" + Year, getFundsMonth(i, Year));
            itemFundList.add(fundsYear);
            XYChart.Data<String, Number> revenueYear = new XYChart.Data<>(i + "/" + Year, getRevenueMonth(i, Year));
            itemRevenueList.add(revenueYear);
            XYChart.Data<String, Number> profitYear = new XYChart.Data<>(i + "/" + Year, getRevenueMonth(i, Year) - getFundsMonth(i, Year));
            itemProfitList.add(profitYear);
            StaticsListByYearList.add(new Statics(i + "", Year, getFundsMonth(i, Year), getRevenueMonth(i, Year), getRevenueMonth(i, Year) - getFundsMonth(i, Year)));
        }
        funds.setData(itemFundList);
        revenue.setData(itemRevenueList);
        profit.setData(itemProfitList);
        funds.setName("Vốn");
        profit.setName("Lợi nhuận");
        revenue.setName("Doanh thu");
        BarChartForMonth.getData().addAll(profit, funds, revenue);

        tvRevenue1.setItems(StaticsListByYearList);
        MonthRevenue.setCellValueFactory(new PropertyValueFactory<>("MonthStatics"));
        FundsColFundMonth.setCellValueFactory(new PropertyValueFactory<>("funds"));
        RevenueColRevenueMonth.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        ProfitColRevenueMonth.setCellValueFactory(new PropertyValueFactory<>("profit"));

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
                + "JOIN Orders O ON OD.OrderId = O.OrderId\n"
                + " WHERE CONVERT(date,O.DateTimeOrder) = ?";
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
        String query = "SELECT SUM(DISTINCT(O.TOTAL)) \n"
                + "FROM Orders O JOIN OrderDetail OD ON O.OrderId = OD.OrderId \n"
                + "\n"
                + "\n"
                + " WHERE CONVERT(date,O.DateTimeOrder) = ? ";
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

    public float getFundsYear(Integer Year) {
        Float fund = 0f;
        String query = "SELECT SUM(OD.Quantity * PD.ImportPrice) FROM OrderDetail OD JOIN ProductDetail PD ON OD.NKId = PD.NKId AND OD.ProductId = PD.ProductId\n"
                + "									 JOIN Orders O ON OD.OrderId = O.OrderId\n"
                + "									 WHERE YEAR(O.DateTimeOrder) = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, Year);
            rs = ps.executeQuery();

            while (rs.next()) {
                fund = rs.getFloat(1);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return fund;
    }

    public float getRevenueYear(Integer Year) {
        Float Revenue = 0f;
        String query = "SELECT SUM(DISTINCT(O.TOTAL)) \n"
                + "FROM Orders O JOIN OrderDetail OD ON O.OrderId = OD.OrderId \n"
                + "\n"
                + "\n"
                + "WHERE YEAR(O.DateTimeOrder) = ? ";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, Year);
            rs = ps.executeQuery();

            while (rs.next()) {
                Revenue = rs.getFloat(1);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Revenue;
    }

    public float getProfitYear(Integer Year) {
        return getRevenueYear(Year) - getFundsYear(Year);
    }

    public float getFundsMonth(Integer Month, Integer Year) {
        Float fund = 0f;
        String query = "SELECT SUM(OD.Quantity * PD.ImportPrice) FROM OrderDetail OD JOIN ProductDetail PD ON OD.NKId = PD.NKId AND OD.ProductId = PD.ProductId\n"
                + "JOIN Orders O ON OD.OrderId = O.OrderId\n"
                + "WHERE YEAR(O.DateTimeOrder) = ? AND MONTH(O.DateTimeOrder) = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, Year);
            ps.setInt(2, Month);
            rs = ps.executeQuery();

            while (rs.next()) {
                fund = rs.getFloat(1);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return fund;
    }

    public float getRevenueMonth(Integer Month, Integer Year) {
        Float Revenue = 0f;
        String query = "SELECT SUM(DISTINCT(O.TOTAL)) \n"
                + "FROM Orders O JOIN OrderDetail OD ON O.OrderId = OD.OrderId \n"
                + "\n"
                + "\n"
                + "WHERE YEAR(O.DateTimeOrder) = ? AND MONTH(O.DateTimeOrder) = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, Year);
            ps.setInt(2, Month);
            rs = ps.executeQuery();

            while (rs.next()) {
                Revenue = rs.getFloat(1);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Revenue;
    }

    public float getFundsDayInMonth(Integer Month, Integer Year, Integer day1, Integer day2) {
        Float fund = 0f;
        String query = "SELECT SUM(OD.Quantity * PD.ImportPrice) FROM OrderDetail OD JOIN ProductDetail PD ON OD.NKId = PD.NKId AND OD.ProductId = PD.ProductId\n"
                + "                 									 JOIN Orders O ON OD.OrderId = O.OrderId\n"
                + "                 									 WHERE YEAR(O.DateTimeOrder) = ? AND MONTH(O.DateTimeOrder) = ? AND DAY(O.DateTimeOrder) BETWEEN ? AND ?";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, Year);
            ps.setInt(2, Month);
            ps.setInt(3, day1);
            ps.setInt(4, day2);
            rs = ps.executeQuery();

            while (rs.next()) {
                fund = rs.getFloat(1);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return fund;
    }

    public float getRevenueDayInMonth(Integer Month, Integer Year, Integer day1, Integer day2) {
        Float Revenue = 0f;
        String query = "SELECT SUM(DISTINCT(O.TOTAL)) \n"
                + "FROM Orders O JOIN OrderDetail OD ON O.OrderId = OD.OrderId \n"
                + "\n"
                + "\n"
                + "WHERE YEAR(O.DateTimeOrder) = ? AND MONTH(O.DateTimeOrder) = ? AND DAY(O.DateTimeOrder) BETWEEN ? AND ?";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, Year);
            ps.setInt(2, Month);
            ps.setInt(3, day1);
            ps.setInt(4, day2);
            rs = ps.executeQuery();

            while (rs.next()) {
                Revenue = rs.getFloat(1);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Revenue;
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

    @FXML
    private void handleStatisticRevenuForYear(ActionEvent event) {
        int FromYear;
        int ToYear;
        try {
            if (tfFromYear.getText().isBlank() || tfToYear.getText().isBlank()) {
                throw new Exception("Year From and Year To is not blank");
            }
            FromYear = Integer.parseInt(tfFromYear.getText());
            if (FromYear < 2020 || FromYear > LocalDate.now().getYear()) {
                throw new Exception("From year is must be < 2020 and <= CurrentYear");
            }
            ToYear = Integer.parseInt(tfToYear.getText());
            if (ToYear < 2020 || ToYear > LocalDate.now().getYear()) {
                throw new Exception("From year is must be > 2020 and <= CurrentYear");
            }
            if (ToYear <= FromYear) {
                throw new Exception("To Year must be > FromYear");
            }
            BarChatforYear.getData().clear();
            showRevenueTab(FromYear, ToYear);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }

    @FXML
    private void handleChoiceYearStaticsRevenue(MouseEvent event) {
        int Year = ChoiceYearSpinner.getValue();
        BarChartForMonth.getData().clear();
        tvRevenue1.getItems().clear();
        showRevenuebyMonth(Year);
    }

    @FXML
    private void handleStatisticsforDay(ActionEvent event) {
        try {
            if (ChoiceMonthSpinner.getValue() == null || ChoiceYearSpinner3.getValue() == null) {
                throw new Exception("Please select Month and Year");
            }
            int month = ChoiceMonthSpinner.getValue();
            int year = ChoiceYearSpinner3.getValue();
            BarChartForMonth1.getData().clear();
            tvRevenue2.getItems().clear();
            showRevenuebyDay(month, year);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }

    @FXML
    private void handleRevenueDaytoDay(ActionEvent event) {
        try {
            if (DatePickerDateFrom.getValue() == null || DatePickerDateTo.getValue() == null) {

                throw new Exception("Please select a date");
            }
            LocalDate.now().minusYears(14);
            if (DatePickerDateFrom.getValue().compareTo(LocalDate.now()) > 0 || DatePickerDateTo.getValue().compareTo(LocalDate.now()) > 0) {

                throw new Exception("The selected date cannot be greater than the current date");
            }
            if (DatePickerDateTo.getValue().compareTo(DatePickerDateFrom.getValue()) < 0) {
                throw new Exception("Date to must be > Date from");
            }
            showRevenueFromDaytoDay(DatePickerDateFrom.getValue(), DatePickerDateTo.getValue());

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }

    @FXML
    private void ExportInventoryProduct(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            String excelFilePath = file.getPath();

            try {
                Workbook workbook = new XSSFWorkbook() {

                    // Tạo một số dữ liệu mẫu
                };
                // Ghi dữ liệu vào file
                try (FileOutputStream fileOut = new FileOutputStream(file)) {
                    workbook.write(fileOut);
                }
                try {
                    writeExcel(getProduct(), file);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Export Excell successfully");
                    alert.show();
                } catch (IOException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Export Excell failed because " + ex.getMessage());
                    alert.show();

                }

            } catch (IOException ex) {
            }
        }

    }

    public static void writeExcel(ObservableList<Product> list, File file) throws IOException {
        //Create Workbook
        XSSFWorkbook workbook = getWorkbook(file.getPath());
        // Create Sheet
        XSSFSheet sheet = workbook.createSheet("Inventory");
        int rowIndex = 0;
        // Write header
        writeHeader(sheet, rowIndex);
        // Write data
        rowIndex++;
        for (Product product : list) {
//                // Create row
            XSSFRow row = sheet.createRow(rowIndex);
// Write data on row
            writeProduct(product, row);
            rowIndex++;
        }
        // Write footer
        writeFooter(sheet, rowIndex);
        // Auto resize column witdth
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autosizeColumn(sheet, numberOfColumn);
        // Create file excel
        createOutputFile(workbook, file.getPath());
        System.out.println("Done!!!");
    }

    private static XSSFWorkbook getWorkbook(String excelFilePath) throws IOException {
        XSSFWorkbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }
    // Write header with format

    private static void writeHeader(XSSFSheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);

        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_PRODUCTID);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("ProductID");

        cell = row.createCell(COLUMN_INDEX_PRODUCTNAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("ProductName");

        cell = row.createCell(COLUMN_INDEX_IMPORTQUANTITY);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Import Quantity");

        cell = row.createCell(COLUMN_INDEX_SOLDQUANTITY);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Sold Quantity");

        cell = row.createCell(COLUMN_INDEX_INVENTORY);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Inventory");
    }

    // Write data
    private static void writeProduct(Product product, XSSFRow row) {
        if (cellStyleFormatNumber == null) {
            // Format number
            short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");
            // DataFormat df = workbook.createDataFormat();
            // short format = df.getFormat("#,##0");

            //Create CellStyle
            XSSFWorkbook workbook = row.getSheet().getWorkbook();
            XSSFCellStyle cellStyleFormatNumber = row.getSheet().getWorkbook().createCellStyle();
            cellStyleFormatNumber.setDataFormat(format);

        }

        XSSFCell cell = row.createCell(COLUMN_INDEX_PRODUCTID);
        cell.setCellValue(product.getProductID());

        cell = row.createCell(COLUMN_INDEX_PRODUCTNAME);
        cell.setCellValue(product.getProductName());

        cell = row.createCell(COLUMN_INDEX_IMPORTQUANTITY);
        cell.setCellValue(product.getImportQuantity());
        cell.setCellStyle((CellStyle) cellStyleFormatNumber);

        cell = row.createCell(COLUMN_INDEX_SOLDQUANTITY);
        cell.setCellValue(product.getSoldQuantity());

        // Create cell formula
        // totalMoney = price * quantity
        cell = row.createCell(COLUMN_INDEX_INVENTORY, CellType.FORMULA);
        cell.setCellStyle((CellStyle) cellStyleFormatNumber);
        int currentRow = row.getRowNum() + 1;
        String columnImportQuantity = CellReference.convertNumToColString(COLUMN_INDEX_IMPORTQUANTITY);
        String columnSoldQuantity = CellReference.convertNumToColString(COLUMN_INDEX_SOLDQUANTITY);
        cell.setCellFormula(columnImportQuantity + currentRow + "-" + columnSoldQuantity + currentRow);
    }

    // Create CellStyle for header
    private static CellStyle createStyleForHeader(XSSFSheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }

    // Write footer
    private static void writeFooter(XSSFSheet sheet, int rowIndex) {
        // Create row
        Row row = sheet.createRow(rowIndex);
        Cell cell = row.createCell(COLUMN_INDEX_INVENTORY, CellType.FORMULA);
        cell.setCellFormula("SUM(E2:E6)");
    }

    // Auto resize column width
    private static void autosizeColumn(XSSFSheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }
    // Create output file

    private static void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
        try (OutputStream os = new FileOutputStream(excelFilePath)) {
            workbook.write(os);
        }
    }
}
