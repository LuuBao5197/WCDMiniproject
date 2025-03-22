/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package group2.g2store;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.util.StringConverter;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * FXML Controller class
 *
 * @author Luu Bao
 */
public class MainController implements Initializable {

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
    private ObservableList<Product> ProductListofProductPage;
    @FXML
    private TableColumn<Product, Integer> stt;
    Dialog<Product> dialogAddProduct;
    Dialog<Product> dialogEditProduct;
    Dialog<Product> dialogViewDetailProduct;
    @FXML
    private TableColumn<Product, String> imageColumn;
    private AnchorPane APProduct;

    private File SelectedImageFileofProductPage = null;
    private ObservableList<Pair<String, Integer>> brandListofProductPage;
    private ObservableList<Pair<String, Integer>> CatListofProductPage;
    Dialog<String> dialogAddBrand;
    ComboBox<Pair<String, Integer>> cbCategoryAddProduct = new ComboBox<>();
    ComboBox<Pair<String, Integer>> cbCategoryEditProduct = new ComboBox<>();

    ComboBox<Pair<String, Integer>> cbBrandAddProduct = new ComboBox<>();
    ComboBox<Pair<String, Integer>> cbBrandEditProduct = new ComboBox<>();
    Dialog<String> dialogAddCategory;
    @FXML
    private Button btnEditProduct;
    private Product SelectProductofProductPage = null;
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

    @FXML
    private AnchorPane productInterface;

    @FXML
    private TabPane StaticsInterface;

    //=======================STATICS INTERFACE=================================
    private static int COLUMN_INDEX_PRODUCTID = 0;
    private static int COLUMN_INDEX_PRODUCTNAME = 1;
    private static int COLUMN_INDEX_IMPORTQUANTITY = 2;
    private static int COLUMN_INDEX_SOLDQUANTITY = 3;
    private static int COLUMN_INDEX_INVENTORY = 4;
    private static Object cellStyleFormatNumber = null;
    private PreparedStatement ps;

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

//    private ObservableList<Product> ProductList;
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
    private TableColumn<Product, Float> SaleRateCol;
    @FXML
    private TextField tfSearchInventory;
    @FXML
    private ComboBox<String> cbCatInvetory;

    @FXML
    private Button btnReLoad;
    @FXML
    private BarChart<String, Number> BarChatforYear;
    @FXML
    private TableView<Statics> tvRevenue;
    @FXML
    private TableColumn<Statics, Integer> YearRevenueCol;
    @FXML
    private TableColumn<Statics, Float> FundsColFundYear;
    @FXML
    private TableColumn<Statics, Float> RevenueColRevenueYear;
    @FXML
    private TableColumn<Statics, Float> ProfitColRevenueYear;
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
    private TableColumn<Statics, Float> FundsColFundMonth;
    @FXML
    private TableColumn<Statics, Float> RevenueColRevenueMonth;
    @FXML
    private TableColumn<Statics, Float> ProfitColRevenueMonth;
    @FXML
    private Spinner<Integer> ChoiceMonthSpinner;
    @FXML
    private Spinner<Integer> ChoiceYearSpinner3;
    @FXML
    private BarChart<String, Number> BarChartForMonth1;
    @FXML
    private TableColumn<Statics, Float> MonthRevenue1;
    @FXML
    private TableColumn<Statics, Float> FundsColFundMonth1;
    @FXML
    private TableColumn<Statics, Float> RevenueColRevenueMonth1;
    @FXML
    private TableColumn<Statics, Float> ProfitColRevenueMonth1;
    @FXML
    private TableView<Statics> tvRevenue2;
    @FXML
    private DatePicker DatePickerDateFrom;
    @FXML
    private DatePicker DatePickerDateTo;
    @FXML
    private TableColumn<?, ?> MonthRevenue11;
    @FXML
    private TableColumn<Statics, Float> FundsColFundMonth11;
    @FXML
    private TableColumn<Statics, Float> RevenueColRevenueMonth11;
    @FXML
    private TableColumn<Statics, Float> ProfitColRevenueMonth11;
    @FXML
    private TableView<Statics> tvRevenue3;
    private ObservableList<Product> ProductListofStaticsInterface;
    private ObservableList<String> CatListofStaticsPage;
    @FXML
    private StackPane StackPaneMainPage;

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
    private TextField tfSuppilerSearch;
    @FXML
    private AnchorPane SupplierInterface;

    // ========================CUSTOMER INTERFACE ======================
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
    private TextField tfCustomerSearch;
    @FXML
    private Button okUpdateCustomer;
    @FXML
    private Button okaddCustomer;

    @FXML
    private AnchorPane CustomerInterface;

    private TableView<Customer> tvCustomer;
    @FXML
    private AnchorPane viewCustomer;
    @FXML
    private Button btnAddCustomer;
    @FXML
    private Button showUpdateCustomer;
    @FXML
    private AnchorPane employeeInterface;

    //===============ADD STAFF ===============
    @FXML
    private TextField StIdAdd;
    @FXML
    private TextField StNumAdd;
    @FXML
    private TextField StNameAdd;
    @FXML
    private TextField StEmailAdd;
    @FXML
    private DatePicker StDobAff;
    private TextField StLevelAdd;
    @FXML
    private TextField StCCAdd;
    @FXML
    private AnchorPane ImageAddSt;
    @FXML
    private ImageView ImvAddEmployee;
    @FXML
    private Button btnImageAdd;
    @FXML
    private RadioButton CkMale;
    @FXML
    private RadioButton CkFemale;

    private final String IMAGE_DIR = "src\\main\\resources\\group2\\imageProduct\\";
    private String selectImageName;
    private Path from, to;
    private String selectImageURL;
    private File selectedFile;
    private File selectedFileAvatarProfile;
    @FXML
    private Button btmChange;

    @FXML
    private Button AddSt;

    // ================END ADD STAFF CONTROLLER======================
    // =======================VIEW STAFF =======================
    @FXML
    private TableColumn<Staff, String> colIdStaff;
    @FXML
    private TableColumn<Staff, String> colNameStaff;
    @FXML
    private TableColumn<Staff, String> colImageStaff;
    @FXML
    private TableColumn<Staff, String> colEmailStaff;
    @FXML
    private TableColumn<Staff, Integer> colPhoneStaff;
    @FXML
    private TableColumn<Staff, String> colGenderStaff;
    @FXML
    private TableView<Staff> tvStaff;

    // ==========================UPDATE STAFF ===========================
    @FXML
    private TextField StIdUpdate;
    @FXML
    private TextField StNameUpdate;
    @FXML
    private TextField StNumUpdate;
    @FXML
    private TextField StEmailUpdate;
    @FXML
    private TextField StGenderUpdate;
    @FXML
    private DatePicker StDobUpdate;
    private TextField StLevelUpdate;
    @FXML
    private TextField StCCUpdate;
    @FXML
    private ImageView StImsgeUpdate;

    //==============================DETAIL STAFF =============================
    @FXML
    private Text StIdDetail;
    @FXML
    private Text StNameDetail;
    @FXML
    private Text StNumDetail;
    @FXML
    private Text StEmailDetail;
    @FXML
    private Text StGenderDetail;

    @FXML
    private Text StDobDetail;
    @FXML
    private Text StCCDetail;
    @FXML
    private Text StPositionDetail;
    @FXML
    private ImageView StImageDetail;

    @FXML
    private AnchorPane StaffViewInterface;
    @FXML
    private AnchorPane StaffAddInterface;
    @FXML
    private AnchorPane StaffUpdateInterface;
    @FXML
    private AnchorPane StaffDetailInterface;
    @FXML
    private AnchorPane StaffDeleteInterface;
    @FXML
    private TableView<Staff> tvStaffofDelete;
    @FXML
    private TableColumn<?, ?> colIdStaffofDelete;
    @FXML
    private TableColumn<?, ?> colNameStaffofDelete;
    @FXML
    private TableColumn<?, ?> colImageStaffofDelete;
    @FXML
    private TableColumn<?, ?> colEmailStaffofDelete;
    @FXML
    private TableColumn<?, ?> colGenderStaffofDelete;
    @FXML
    private TableColumn<?, ?> colPhoneStaffofDelete;
    @FXML
    private TableView<Staff> tvStaffofDetail;
    @FXML
    private TableColumn<?, ?> colIdStaffofDetail;
    @FXML
    private TableColumn<?, ?> colNameStaffofDetail;
    @FXML
    private TableColumn<?, ?> colImageStaffofDetail;
    @FXML
    private TableColumn<?, ?> colEmailStaffofDetail;
    @FXML
    private TableColumn<?, ?> colGenderStaffofDetail;
    @FXML
    private TableColumn<?, ?> colPhoneStaffofDetail;
    @FXML
    private TableView<Staff> tvStaffOfUpdate;
    @FXML
    private TableColumn<?, ?> colIdStaffofUpdate;
    @FXML
    private TableColumn<?, ?> colNameStaffofUpdate;
    @FXML
    private TableColumn<?, ?> colImageStaffofUpdate;
    @FXML
    private TableColumn<?, ?> colEmailStaffofUpdate;
    @FXML
    private TableColumn<?, ?> colGenderStaffofUpdate;
    @FXML
    private TableColumn<?, ?> colPhoneStaffofUpdate;
    @FXML
    private TextField StIdDelete;
    @FXML
    private TextField StNameDelete;
    @FXML
    private TextField StNumDelete;
    @FXML
    private TextField StEmailDelete;
    @FXML
    private TextField StGenderDelete;
    @FXML
    private TextField StLevelDelete;
    @FXML
    private TextField StCCDelete;
    @FXML
    private DatePicker StDobDelete;
    @FXML
    private ImageView StImsgeDelete;
    @FXML
    private StackPane StackPaneStaffInterface;
    @FXML
    private ToggleGroup GenderEmployeeAdd;

    private User LoginUser;
    @FXML
    private Label PositionUser;
    @FXML
    private Label lbNameUser;
    @FXML
    private Button btnOpenEmployee;
    @FXML
    private Button btnOpenAccount;
    @FXML
    private Button btnOpenStatistics;
    @FXML
    private ImageView imvUserLogin;

    Label IDUser = new Label();
    private ObservableList<OrdersQ> ordersList;
    private ObservableList<ProductQ> ProductList;
    private ObservableList<ProductQ> selectedProducts = FXCollections.observableArrayList();
    private ObservableList<OrderDetailQ> orderDetailListGlobal = FXCollections.observableArrayList();
    private Map<ProductQ, Boolean> batchSelectedMap = new HashMap<>();

    private Label totalPrice;
    private String couponUsed;

    @FXML
    private ComboBox<String> comboBoxCustomerPageEP;
    @FXML
    private ComboBox<String> comboBoxEmployeePageEP;
    @FXML
    private DatePicker dateFromPageEP;
    @FXML
    private DatePicker dateToPageEP;
    @FXML
    private Slider sliderPricePageEP;
    @FXML
    private Label lbSliderPageEP;
    @FXML
    private TableView<OrdersQ> tbOrderPageEP;
    @FXML
    private TableColumn<OrdersQ, Number> sttPageEP;
    @FXML
    private TableColumn<OrdersQ, String> madonhangPageEP;
    @FXML
    private TableColumn<OrdersQ, String> somaykhachhangPageEP;
    @FXML
    private TableColumn<OrdersQ, String> manhanvienPageEP;
    @FXML
    private TableColumn<OrdersQ, String> thoigianPageEP;
    @FXML
    private TableColumn<OrdersQ, Number> tongtienPageEP;
    @FXML
    private ComboBox<String> filterCategoryPageEP2;
    @FXML
    private TableView<ProductQ> tbProductPageEP2;
    @FXML
    private TableColumn<ProductQ, String> tensanphamPageEP2;
    @FXML
    private TableColumn<ProductQ, String> thuonghieuPageEP2;
    @FXML
    private TableColumn<ProductQ, String> anhPageEP2;
    @FXML
    private VBox listdachonPageEP2;
    @FXML
    private Label totalBillPageEP2;
    @FXML
    private TextField txtCouponPageEP2;
    @FXML
    private ComboBox<String> listCustomerPageEP2;
    @FXML
    private TableColumn<ProductQ, Number> sothutuPageEP2;
    @FXML
    private AnchorPane pageBanHang;
    @FXML
    private AnchorPane pageTaoPhieu;
    @FXML
    private Label txtEmptyPageEP2;
    @FXML
    private TextField txtSearchPageEP2;
    @FXML
    private ComboBox<String> filterBrandPageEP2;
    @FXML
    private Button btnApplyCouponPageEP2;
    @FXML
    private Label btnDeleteAllPageEP2;
    @FXML
    private AnchorPane OrderInterface;
    @FXML
    private AnchorPane HomeInterface;
    @FXML
    private MenuButton menuButtonOther;

    //===================COUPON INTERFACE =============
    private ObservableList<CouponQ> CouponList;

    @FXML
    private TextField txtSearchPageCoupon;
    @FXML
    private ComboBox<String> cbFilterPageCoupon;
    @FXML
    private TableView<CouponQ> tbCouponPageCoupon;
    @FXML
    private TableColumn<CouponQ, Number> sttPageCoupon;
    @FXML
    private TableColumn<CouponQ, String> colCouponIDPageCoupon;
    @FXML
    private TableColumn<CouponQ, String> colValuePageCoupon;
    @FXML
    private TableColumn<CouponQ, String> colExpiryDatePageCoupon;
    @FXML
    private TableColumn<CouponQ, String> colStatusPageCoupon;
    @FXML
    private AnchorPane pageCoupon;

    // =======================WAREHOUSE INTERFACE ==============================
    @FXML
    private AnchorPane nhaphangInterface;
    @FXML
    private AnchorPane viewWarehouseReceiptInterface;
    @FXML
    private TextField tfSearchSupplierOfWareHouse;
    @FXML
    private DatePicker dpDateFromofWareHouse;
    @FXML
    private DatePicker dpDateToOfWareHouse;
    @FXML
    private Button btnSearchByDateOfWareHouse;
    @FXML
    private TableView<WareHouse> tvWareHouseReceipt;
    @FXML
    private TableColumn<WareHouse, Number> colSttPageWH;
    @FXML
    private TableColumn<WareHouse, Integer> colWRIPageWH;
    @FXML
    private TableColumn<WareHouse, String> colEmployeeNamePageWH;
    @FXML
    private TableColumn<WareHouse, String> colSupplierNamePageWH;
    @FXML
    private TableColumn<WareHouse, String> colDateTimePageWH;
    @FXML
    private TableColumn<WareHouse, Float> colTotalPageWH;
    private ObservableList<WareHouse> WHRListPageNHI;
    private ObservableList<ProductPageNHI> ProductListPageNHI;
    private ObservableList<ProductSelectedPageNHI> ProductSelectedListPageNHI = FXCollections.observableArrayList();
    private ObservableList<SuppilerQ> SuppilerListPageNHI;

    @FXML
    private TableView<ProductPageNHI> tvProductPageNHI;
    @FXML
    private TableColumn<ProductPageNHI, String> colProductIDPageNHI;
    @FXML
    private TableColumn<ProductPageNHI, String> colProductNamePageNHI;
    @FXML
    private TableView<ProductSelectedPageNHI> tvProductSelectedPageNHI;
    @FXML
    private TableColumn<ProductSelectedPageNHI, Number> colSttSelectedPageNHI;
    @FXML
    private TableColumn<ProductSelectedPageNHI, String> colProductIdSelectedPageNHI;
    @FXML
    private TableColumn<ProductSelectedPageNHI, String> colProductNameSelectedPageNHI;
    @FXML
    private TableColumn<ProductSelectedPageNHI, Integer> colQuantitySelectedPageNHI;
    @FXML
    private TableColumn<ProductSelectedPageNHI, Float> colImportPriceSelectedPageNHI;
    @FXML
    private TableColumn<ProductSelectedPageNHI, String> colExpiryDateSelectedPageNHI;
    @FXML
    private TextField txtWRIPageNHI;
    @FXML
    private TextField txtEmployeeIDPageNHI;
    @FXML
    private ComboBox<SuppilerQ> cbSupplierPageNHI;
    @FXML
    private TextField txtTotalPageNHI;
    @FXML
    private TextField lbProductIDPageNHI;
    @FXML
    private TextField lbProductNamePageNHI;
    @FXML
    private TextField lbImportPricePageNHI;
    @FXML
    private DatePicker DatepickerExpiryPageNHI;
    @FXML
    private TextField lbQuantityPageNHI;
    @FXML
    private ImageView iconDeletePageNHI;
    @FXML
    private StackPane WareHouseInterface;

    @FXML
    private TextField NameAcountAdd;
    @FXML
    private TextField PassAcountAdd;
    private TableColumn<Acount, Integer> AcountID;
    private TableColumn<Acount, String> UsernameAcount;
    private TableColumn<Acount, String> StatusCount;

    private AnchorPane AcountHome;

    @FXML
    private TableView<Acount> tvAcount;
    private TableColumn<Acount, Boolean> activeStatus;
    @FXML
    private TableColumn<Acount, Integer> IDColOfAccountPage;
    @FXML
    private TableColumn<Acount, String> UsernameColOfAcountPage;
    @FXML
    private TableColumn<Acount, String> PasswordColOfAccountPage;
    @FXML
    private TableColumn<Acount, Boolean> StatusColOfAccountPage;
    @FXML
    private AnchorPane addAccountInterface;
    @FXML
    private AnchorPane EditAccountInterface;
    @FXML
    private TextField tfUsernameOfEditAccount;
    @FXML
    private ToggleButton toggleBtnStatusEdit;
    @FXML
    private TableColumn<?, ?> RoleColOfAccountPage;
    private Acount SelectAccountOfAccountInterFace;
    private RadioButton radioButtonManagerRole;
    private RadioButton radioButtonStaffRole;

    @FXML
    private AnchorPane AccountInterface;
    private ComboBox<String> cbPositionOfAddEmployee;
    @FXML
    private TextField cbPositionOfUpdateEmployee;
    @FXML
    private Button btnAddSuppiler;
    @FXML
    private Label lbCustomerID;
    @FXML
    private Label lbCustomerPoint;
    @FXML
    private AnchorPane pageEditProfile;
    @FXML
    private ImageView imgEmpEP;
    @FXML
    private TextField txtNameEP;
    @FXML
    private ToggleGroup radioGender;
    @FXML
    private DatePicker DatepickerYOBEP;
    @FXML
    private TextField txtPhoneEP;
    @FXML
    private TextField txtidEP;
    @FXML
    private TextField txtEmailEP;
    @FXML
    private Label txtEmpidEP;
    @FXML
    private RadioButton MaleGenderRadioBtnofEditProfile;
    @FXML
    private RadioButton FeMaleGenderRadioBtnofEditProfile;

    Dialog<User> EditProfileDialog;
    private String selectImageURLofProfile;
    private String selectImageNameOfProfile;
    @FXML
    private Button btnChangeAvatarProfile;
    @FXML
    private Button btnSaveEditProfile;
    @FXML
    private TextField tfPositionOfAddEmployee;

    // BRAND AND CATEGORY INTERFACE
    @FXML
    private TableView<CategoryQ> tvCategoryofBC;
    @FXML
    private TableView<BrandQ> tvBrandofBC;

    ObservableList<BrandQ> brandListofBC = FXCollections.observableArrayList();
    ObservableList<CategoryQ> categoryListofBC = FXCollections.observableArrayList();

    @FXML
    private AnchorPane pageBrandandCat;
    @FXML
    private TableColumn<CategoryQ, Integer> colCategoryIDofBC;
    @FXML
    private TableColumn<CategoryQ, String> colCategoryNameofBC;
    @FXML
    private TableColumn<BrandQ, Integer> colBrandIDofBC;
    @FXML
    private TableColumn<BrandQ, String> colBrandNameofBC;
    @FXML
    private ImageView btnAddBrand;
    @FXML
    private ImageView btnEditBrand;
    @FXML
    private ImageView btnAddCategory;
    @FXML
    private ImageView btnEditCategory;
    private AnchorPane BrandAndCategoryInterface;
    @FXML
    private MenuItem btnOpenCouponInterface;
    @FXML
    private MenuItem btnOpenBrandAndCat;
    private Staff SelectedStaff;
    @FXML
    private TextField tfSearchStaff;
    private ObservableList<Staff> StaffListOfStaffInterface;
    @FXML
    private TableColumn<Suppiler, Float> colSuppilerTotal;
    private Customer CustomerSelected;
    private Suppiler SelectedSuppiler;
    private Dialog<Object> BrandAndCategoryDialog;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (cnDB == null) {
            cnDB = new ConnectDB();
        }
        con = cnDB.getConnect();

        // ===================account interface =========
        showAcount();
        SelectAccount();

//         }
        //============product page ==============
        showProducts();

        tfSearchProduct.setOnKeyReleased(event -> {
            String keyword = tfSearchProduct.getText().trim();
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

        // ==============statics page ===========
        showOverViewTab();
        showInventoryTab();
        showRevenueTab(2020, LocalDate.now().getYear());
        showRevenuebyMonth(2024);
        showRevenuebyDay(LocalDate.now().getMonthValue(), LocalDate.now().getYear());

        //=============Supplier Page ==================
        showSuppilers();

        //=============CustomerPage==================
        showCustomers();

        // =============EMPLOYEE PAGE ===============
        showStaffofView();
        //=====================ORDER PAGE =================
        String sql = "SELECT orderid, customerPhone, empid, datetimeorder,couponid, total FROM Orders";

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
                String couponId = rs.getString("couponid");
                ordersList.add(new OrdersQ(orderId, customerPhone, datetimeOrder, empId, couponId, total));
            }
            tbOrderPageEP.setItems(ordersList);

            // Thiết lập giá trị cho các cột
            sttPageEP.setCellValueFactory(data -> {
                OrdersQ o = data.getValue();
                int rowIndex = tbOrderPageEP.getItems().indexOf(o) + 1;
                return new SimpleIntegerProperty(rowIndex);
            });

            madonhangPageEP.setCellValueFactory(new PropertyValueFactory<>("orderId"));
            somaykhachhangPageEP.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
            manhanvienPageEP.setCellValueFactory(new PropertyValueFactory<>("empId"));
            thoigianPageEP.setCellValueFactory(new PropertyValueFactory<>("datetimeOrder"));
            tongtienPageEP.setCellValueFactory(new PropertyValueFactory<>("total"));

            somaykhachhangPageEP.setCellFactory(column -> new TableCell<OrdersQ, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item.equals("0")) {
                        setText(null);
                    } else {
                        setText(item);
                    }
                }

            });

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());;
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
            tbProductPageEP2.setItems(ProductList);

            // Thiết lập giá trị cho các cột
            sothutuPageEP2.setCellValueFactory(data -> {
                ProductQ p = data.getValue();
                int rowIndex = tbProductPageEP2.getItems().indexOf(p) + 1;
                return new SimpleIntegerProperty(rowIndex);
            });
            tensanphamPageEP2.setCellValueFactory(new PropertyValueFactory<>("productName"));
            thuonghieuPageEP2.setCellValueFactory(new PropertyValueFactory<>("ProductBrand"));
            anhPageEP2.setCellValueFactory(new PropertyValueFactory<>("ProductImage"));
            anhPageEP2.setCellFactory(param -> new TableCell<ProductQ, String>() {
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
            System.out.println(ex.getMessage());;

        }

// Đặt chiều cao của mỗi dòng là 40px
        tbOrderPageEP.setFixedCellSize(
                40);
        tbProductPageEP2.setFixedCellSize(
                40);

// Thêm event lắng nghe khi chọn sản phẩm trong bảng tbProduct (bảng sản phẩm)
        tbProductPageEP2.getSelectionModel()
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
        String query = "SELECT * FROM Customer WHERE NOT CustomerName like 'None' ";

        try {
            Statement stm3 = con.createStatement();
            ResultSet rs3 = stm3.executeQuery(query);
            while (rs3.next()) {
                String customerName = rs3.getString("customerName");
                customerList.add(customerName);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());;
        }

        // Đặt danh sách khách hàng vào ComboBox
        listCustomerPageEP2.setItems(customerList);
        listCustomerPageEP2.setOnAction(e -> {
            String CustomerName = listCustomerPageEP2.getValue();
            getCustomerPhone();
        }
        );

        //get database->filterCategoryPageEP2 and filterBrandPageEP2
        //filterCategoryPageEP2
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
            filterCategoryPageEP2.setItems(CategoryList);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());;
        }

        //filterBrandPageEP2 comboBox
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
            filterBrandPageEP2.setItems(BrandList);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());;
        }
        // Bắt sự kiện khi chọn danh mục trong filterCategoryPageEP2
        filterCategoryPageEP2.setOnAction(event -> {
            filterBrandPageEP2.setPromptText("Brand"); // Reset giá trị của filterBrandPageEP2
            txtSearchPageEP2.clear(); // Xóa nội dung trong txtSearch
            String selectedCategory = filterCategoryPageEP2.getValue();
            filterCategoryPageEP2(selectedCategory); // Cập nhật tbProduct với danh mục đã chọn và brand là null
        });

        // Bắt sự kiện khi chọn danh mục trong filterBrandPageEP2
        filterBrandPageEP2.setOnAction(event -> {
            filterCategoryPageEP2.setPromptText("Category"); // Reset giá trị của filterCategoryPageEP2
            txtSearchPageEP2.clear(); // Xóa nội dung trong txtSearch
            String selectedBrand = filterBrandPageEP2.getValue();
            filterBrandPageEP2(selectedBrand); // Cập nhật tbProduct với brand đã chọn và category là null
        });

        // Filter theo từ khóa tên sản phẩm trong txtSearch
        txtSearchPageEP2.setOnKeyReleased(event
                -> {
            String keyword = txtSearchPageEP2.getText().trim();
            filterCategoryPageEP2.setValue(null); // Reset giá trị của filterCategoryPageEP2
            filterBrandPageEP2.setValue(null); // Reset giá trị của filterBrandPageEP2
            searchProduct(keyword);
        }
        );

        //Bắt sự kiện kéo slider 
        sliderPricePageEP.valueProperty().addListener((observable, oldValue, newValue) -> {
            lbSliderPageEP.setText("( 0 - " + String.format("%,.0f", newValue.doubleValue()) + " )");
            filterOrderByPrice(newValue.doubleValue());
            dateFromPageEP.setValue(null);
            dateToPageEP.setValue(null);
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
            comboBoxCustomerPageEP.setItems(listNameCus); // Set items to the ComboBox
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());;
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

            comboBoxEmployeePageEP.setItems(listNameEmp); // Set items to the ComboBox

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());;
        }

        //Bắt sự kiện khi chọn cômboBoxCustomer
        comboBoxCustomerPageEP.setOnAction(event -> {
            String selectedItem = comboBoxCustomerPageEP.getValue();
            filterCBCustomer(selectedItem);
            dateFromPageEP.setValue(null);
            dateToPageEP.setValue(null);
        });

        //Bắt sự kiện khi chọn CBEmployee
        comboBoxEmployeePageEP.setOnAction(event -> {
            String selectedItem = comboBoxEmployeePageEP.getValue();
            filterCBEmployee(selectedItem);
            dateFromPageEP.setValue(null);
            dateToPageEP.setValue(null);
        });

        // Handle DatePicker From value change
        dateFromPageEP.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filterDate(newValue, dateToPageEP.getValue());
                comboBoxCustomerPageEP.getSelectionModel().select("See all");
                comboBoxEmployeePageEP.getSelectionModel().select("See all");
            }
        });

        // Handle DatePicker To value change
        dateToPageEP.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filterDate(dateFromPageEP.getValue(), newValue);
                comboBoxCustomerPageEP.getSelectionModel().select("See all");
                comboBoxEmployeePageEP.getSelectionModel().select("See all");
            }
        });

        //===========================COUPON INTERFACE ==========================
        sql = "SELECT * FROM coupon ";
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

                CouponList.add(new CouponQ(couponID, value, expiryDate, status));
            }
            tbCouponPageCoupon.setItems(CouponList);

            // Thiết lập giá trị cho các cột
            sttPageCoupon.setCellValueFactory(data -> {
                CouponQ c = data.getValue();
                int rowIndex = tbCouponPageCoupon.getItems().indexOf(c) + 1;
                return new SimpleIntegerProperty(rowIndex);
            });

            colCouponIDPageCoupon.setCellValueFactory(new PropertyValueFactory<>("couponid"));
            colValuePageCoupon.setCellValueFactory(data -> {
                CouponQ c = data.getValue();
                return new SimpleStringProperty(String.format("%,d", c.getValue()));
            });
            colExpiryDatePageCoupon.setCellValueFactory(new PropertyValueFactory<>("expirydate"));
            colStatusPageCoupon.setCellValueFactory(data -> {
                CouponQ c = data.getValue();
                return new SimpleStringProperty(c.getStatusText());
            });

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
//        finally {
//            // Đóng ResultSet, Statement và Connection
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (stm != null) {
//                    stm.close();
//                }
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(CouponController.class.getName()).log(Level.SEVERE, null, ex);
//            }

        //Set giá trị cho cbFilter
        cbFilterPageCoupon.setItems(FXCollections.observableArrayList("See all", "Coupon unexpired", "Coupon ready"));
        cbFilterPageCoupon.getSelectionModel().select("See all");

        // Xử lý sự kiện khi người dùng chọn giá trị từ ComboBox
        cbFilterPageCoupon.setOnAction(e -> {
//            showAlert("OK");
            handleCBFilter();
        });

        //Xử lý sự kiện search 
        txtSearchPageCoupon.setOnKeyReleased(event -> {
            handleSearch();
        });

        //Xử lý chọn và btnEdit
        tbCouponPageCoupon.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Xử lý khi có dòng được chọn
        });

        tbCouponPageCoupon.setFixedCellSize(40);

        //==================================WAREHOUSE INTERFACE =============================
        getDataWHR();
        getDataTBProductPageNHI();
        getDataSupplierPageNHI();

        getWHRIDPageNHI();

        //xu ly chon bang Product
        tvProductPageNHI.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProductPageNHI>() {
            @Override
            public void changed(ObservableValue<? extends ProductPageNHI> observable, ProductPageNHI oldValue, ProductPageNHI newValue) {
                if (newValue != null) {
                    handleSelectProductPageNHI(newValue);
                }
            }
        });

        //xu ly txtSearch
        tfSearchSupplierOfWareHouse.setOnKeyReleased(event
                -> {
            String keyword = tfSearchSupplierOfWareHouse.getText().trim();
            handleSearchSupplier(keyword);
        });

        //xu ly khi nhap import & productPrice
        lbImportPricePageNHI.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                // Chỉ cho phép nhập các ký tự số
                lbImportPricePageNHI.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        lbQuantityPageNHI.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                // Chỉ cho phép nhập các ký tự số
                lbQuantityPageNHI.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        ///=================================BRAND AND CATEGORY INTERFACE ==============
        getBrandofBC();
        getCategoryofBC();
        setToolTipofBC();

    }

    //=============================================================================
    // ================================HANDLE PRODUCT FUNCTION ====================
    //=============================================================================
    //    ================================== XEM SAN PHAM VA THEM SAN PHAM MOI
    public User getLoginUser() {
        return LoginUser;
    }

    public void setLoginUser(User LoginUser) {
        System.out.println("In MainController:" + LoginUser);
        this.LoginUser = LoginUser;
    }

    public void setInfoUser() {
        // ------------------- Lấy thông tin user đăng hập từ LoginController --------------------------;
        txtEmpidEP.setText(getLoginUser().getEmpID() + "");
        txtNameEP.setText(getLoginUser().getEmpName());
        txtPhoneEP.setText(getLoginUser().getPhoneNumber());
        txtidEP.setText(getLoginUser().getCCCDNumber());
        txtEmailEP.setText(getLoginUser().getEmail());
        DatepickerYOBEP.setValue(LocalDate.parse(getLoginUser().getDOB()));
        selectImageNameOfProfile = getLoginUser().getUrlImage();
        if (getLoginUser().getGender().equals("Female")) {
            FeMaleGenderRadioBtnofEditProfile.setSelected(true);
        } else {
            MaleGenderRadioBtnofEditProfile.setSelected(true);
        }

        txtEmployeeIDPageNHI.setText(getLoginUser().getEmpID() + "");
        lbNameUser.setMaxWidth( 140);
        lbNameUser.setText(getLoginUser().getEmpName());
        PositionUser.setText((getLoginUser().getRole() != 1) ? "Management" : "Staff");
        System.out.println(getLoginUser());
        if (getLoginUser().getRole() == 1) {
            btnOpenAccount.setVisible(false);
            btnOpenEmployee.setVisible(false);
            btnOpenStatistics.setVisible(false);
            btnOpenCouponInterface.setVisible(false);

            IDUser.setText(getLoginUser().getEmpID() + "");

            String imagePath = "src\\main\\resources\\group2\\imageProduct\\" + getLoginUser().getUrlImage();
            System.out.println(imagePath);

            Image image = new Image(new File(imagePath).toURI().toString());
            imvUserLogin.setImage(image);
            imgEmpEP.setImage(image);

        } else {
            String imagePath = "src\\main\\resources\\group2\\imageProduct\\" + getLoginUser().getUrlImage();
            System.out.println(imagePath);

            Image image = new Image(new File(imagePath).toURI().toString());
            imvUserLogin.setImage(image);

            imgEmpEP.setImage(image);
        }
    }

    public Dialog<Product> showDialogAddProduct() {
        // ==================DIALOG THEM SAN PHAM =================
        System.err.println(getLoginUser().getEmpID() + "");
        dialogAddProduct = new Dialog<>();
        dialogAddProduct.setTitle("Add product");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        ButtonType resetButtonType = new ButtonType("Reset", ButtonBar.ButtonData.APPLY);

        dialogAddProduct.getDialogPane().getButtonTypes().addAll(addButtonType, resetButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();

        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(50));
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
        btnAddBrand.setStyle("-fx-background-color: green; -fx-text-fill: #fff");
        Button btnAddCategory = new Button("Add Category");
        btnAddCategory.setStyle("-fx-background-color: green; -fx-text-fill: #fff");
        cbCategoryAddProduct = new ComboBox<>();
        cbCategoryAddProduct.setItems(getCategory());

        cbCategoryAddProduct.setOnAction((event) -> {
            try {
                productCatAdd.setText(cbCategoryAddProduct.getValue().getValue() + "");
            } catch (Exception e) {
            }

        });

        Button ChooseImage = new Button("Choice Image");
        ChooseImage.setStyle("-fx-background-color: green; -fx-text-fill: #fff");

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
                SelectedImageFileofProductPage = file;

                Image image = new Image(file.toURI().toString(), 200, 120, false, true);
                imv.setImage(image);

                String fileName = file.getName();
                productImageAdd.setText(fileName);

            }

        });
        dialogAddProduct.setResizable(true);
        dialogAddProduct.getDialogPane().setContent(grid);
        dialogAddProduct.getDialogPane().setMinHeight(700);
        

        // thiet lap backgroynd cho dialogEditProduct
        dialogAddProduct.getDialogPane().setStyle("-fx-font-size: 16px; -fx-background-image: url('file:/D:/G2Store/src/main/resources/group2/Icon/logoG2Store.jpg'); -fx-opacity: 0.8");
//        dialogEditProduct.getDialogPane().setStyle("-fx-background-color: lightblue;");
//        dialogEditProduct.getDialogPane().setMinHeight(300);
        grid.setStyle("-fx-background-color: #fff ; -fx-opacity: 1");

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
                    alert.setContentText("Add product failed");
                    alert.show();
                } else {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("KET QUA");
                    alert.setContentText("Add product successfully");
                    if (SelectedImageFileofProductPage != null) {
                        String fileName = SelectedImageFileofProductPage.getName();
                        Path targetPath = Paths.get("src/main/resources/group2/imageProduct/", fileName);
                        try {
                            Files.copy(SelectedImageFileofProductPage.toPath(), targetPath, REPLACE_EXISTING);
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }

                    alert.show();
                    resetDialogAddProduct();
                    showProducts();
                    getDataTBProductPageNHI();
                    showOverViewTab();

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
            ProductListofProductPage = FXCollections.observableArrayList();

            ProductListofProductPage.clear();
            int index = 1; // bien de luu STT
            while (rs.next()) {
                String ProductID = rs.getString("ProductID");

                String ProductName = rs.getString("ProductName");

                String ProductBrand = rs.getString("BrandName");

                String ProductCategory = rs.getString("CategoryName");

                String ProductImage = rs.getString("ProductImage");

                ProductListofProductPage.add(new Product(ProductID, ProductName, ProductBrand, ProductCategory, ProductImage));
                index++;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return ProductListofProductPage;
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
        ProductListofProductPage = FXCollections.observableArrayList(pList);
        tbProductView.setItems(ProductListofProductPage);
    }

    public void searchProductsbyCategory(String keyword) {

        List<Product> pList;

        pList = getProducts().stream().filter(p -> p.getProductCategory().equalsIgnoreCase(keyword)).collect(Collectors.toList());
        ProductListofProductPage = FXCollections.observableArrayList(pList);
        tbProductView.setItems(ProductListofProductPage);
    }

    public void handleSelectProduct() {

        tbProductView.getSelectionModel()
                .selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {

                        SelectProductofProductPage = newSelection;

                        btnEditProduct.setDisable(false);
                        btnDeleteProduct.setDisable(false);
                        btnDetailProduct.setDisable(false);

                    }

                });

    }

    public ObservableList<Pair<String, Integer>> getBrand() {
        String sql = "SELECT * from Brand";

        try {
            // Tạo satement
            stm = con.createStatement();
            // truy vấn
            rs = stm.executeQuery(sql);

            // tạo observableList để lưu dữ liệu từ ResultSet
            brandListofProductPage = FXCollections.observableArrayList();

            brandListofProductPage.clear();

            while (rs.next()) {
                int BrandId = rs.getInt("BrandID");
                String BrandName = rs.getString("BrandName");

                Pair<String, Integer> brandItem = new Pair<>(BrandName, BrandId);
                brandListofProductPage.add(brandItem);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return brandListofProductPage;
    }

    public ObservableList<Pair<String, Integer>> getCategory() {
        String sql = "SELECT * from Category";

        try {
            // Tạo satement
            stm = con.createStatement();
            // truy vấn
            rs = stm.executeQuery(sql);

            // tạo observableList để lưu dữ liệu từ ResultSet
            CatListofProductPage = FXCollections.observableArrayList();

            CatListofProductPage.clear();

            while (rs.next()) {
                int CategoryId = rs.getInt("CategoryID");
                String CategoryName = rs.getString("CategoryName");

                Pair<String, Integer> CatItem = new Pair<>(CategoryName, CategoryId);
                CatListofProductPage.add(CatItem);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return CatListofProductPage;
    }

    public void showDialogAddBrand() {
        dialogAddBrand = new Dialog<>();
        dialogAddBrand.setTitle("Add Brand");
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

        dialogAddBrand.getDialogPane().lookupButton(addButtonType).addEventFilter(ActionEvent.ACTION, eh -> {

            String BrandName;
            BrandName = tfBrandName.getText().trim();
            try {
                if (BrandName.isEmpty()) {
                    throw new Exception("Brand name must be not empty");
                }
                for (BrandQ b : brandListofBC) {
                    if (b.getBName().equalsIgnoreCase(BrandName)) {
                        throw new Exception("Brand name must be not duplicate");
                    }
                }
                addBrand(BrandName);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
                eh.consume();
            }

        });

//        dialogAddBrand.setResultConverter(dialogButton -> {
//            if (dialogButton == addButtonType) {
//                String BrandName;
//                BrandName = tfBrandName.getText();
//                if (!BrandName.isEmpty()) {
//                    addBrand(BrandName);
//                } else {
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setContentText("Brand name must be not empty");
//                    alert.show();
//                }
//
//            }
//            return null;
//
//        });
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

        dialogAddCategory.getDialogPane().lookupButton(addButtonType).addEventFilter(ActionEvent.ACTION, eh -> {
            try {
                if (tfCategoryName.getText().trim().isEmpty()) {
                    throw new Exception("Category must be not empty");
                }
                for (CategoryQ c : categoryListofBC) {
                    if (c.getCName().equalsIgnoreCase(tfCategoryName.getText().trim())) {
                        throw new Exception("Category name must be not duplicate");
                    }
                }
                String CategoryProduct = tfCategoryName.getText();
                addCategory(CategoryProduct);

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
                eh.consume();

            }
        });
//        dialogAddCategory.setResultConverter(dialogButton -> {
//            if (dialogButton == addButtonType) {
//                try {
//                    if (tfCategoryName.getText().trim().isEmpty()) {
//                        throw new Exception("Category must be not empty");
//                    }
//
//                    String CategoryProduct = tfCategoryName.getText();
//                    addCategory(CategoryProduct);
//
//                } catch (Exception e) {
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setContentText(e.getMessage());
//                    alert.show();
//
//                }
//
//            }
//            return null;
//
//        });
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
        productIDEdit.setText(SelectProductofProductPage.getProductID());
        productIDEdit.setEditable(false);
        productNameEdit = new TextField();
        productNameEdit.setPromptText("Product Name");
        productNameEdit.setText(SelectProductofProductPage.getProductName());
        productBrandEdit = new TextField();
        productBrandEdit.setEditable(false);
        productBrandEdit.setPromptText("Brand");
        productBrandEdit.setText(String.valueOf(getBrand().filtered(m -> m.getKey().equals(SelectProductofProductPage.getProductBrand())).get(0).getValue()));
        productCatEdit = new TextField();
        productCatEdit.setText(getCategory().filtered(m -> m.getKey().equalsIgnoreCase(SelectProductofProductPage.getProductCategory())).get(0).getValue() + "");
        productCatEdit.setEditable(false);

        productImageEdit = new TextField();
        productImageEdit.setText(SelectProductofProductPage.getImages());
        productImageEdit.setDisable(true);
        cbBrandEditProduct = new ComboBox();
        productImageEdit.setPromptText("URL Image");
//        cbBrand.setItems();
        cbBrandEditProduct.setItems(getBrand());
        cbBrandEditProduct.setValue(getBrand().filtered(item -> item.getKey().equals(SelectProductofProductPage.getProductBrand())).get(0));
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
        cbCategoryEditProduct.setValue(getCategory().filtered(item -> item.getKey().equals(SelectProductofProductPage.getProductCategory())).get(0));

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

        String imagePath = "src/main/resources/group2/imageProduct/" + SelectProductofProductPage.getImages();

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
                SelectedImageFileofProductPage = file;

                Image image = new Image(file.toURI().toString(), 200, 150, false, true);
                imv.setImage(image);

                String fileName = file.getName();
                productImageEdit.setText(fileName);

            }

        });

        dialogEditProduct.setResizable(true);
        dialogEditProduct.getDialogPane().setMinHeight(700);
        // thiet lap backgroynd cho dialogEditProduct
        dialogEditProduct.getDialogPane().setStyle("-fx-font-size: 16px; -fx-background-image: url('file:/D:/G2Store/src/main/resources/group2/Icon/logoG2Store.jpg'); -fx-opacity: 0.8");
//        dialogEditProduct.getDialogPane().setStyle("-fx-background-color: lightblue;");
//        dialogEditProduct.getDialogPane().setMinHeight(300);
        grid.setStyle("-fx-background-color: #fff ; -fx-opacity: 1");
        dialogEditProduct.getDialogPane().setContent(grid);
        dialogEditProduct.getDialogPane().lookupButton(editButtonType).addEventFilter(ActionEvent.ACTION, eh -> {
            try {
                // validate các trường dữ liệu

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
                List<Product> p1List = getProducts().stream().filter(predicate -> predicate.getProductName().equals(productNameEdit.getText().trim()) && !predicate.getProductName().equals(SelectProductofProductPage.getProductName())).collect(Collectors.toList());
                if (!p1List.isEmpty()) {
                    throw new Exception("ProductName must be not duplicate");
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
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
                eh.consume();
            }
        });

        dialogEditProduct.setResultConverter(dialogEditProductButton -> {

            try {
                if (dialogEditProductButton == editButtonType) {

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
                    alert.setContentText("Editing product information failed");
                    alert.show();
                } else {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Successfully edited product information");
                    if (SelectedImageFileofProductPage != null) {
                        String fileName = SelectedImageFileofProductPage.getName();
                        Path targetPath = Paths.get("src/main/resources/group2/imageProduct/", fileName);
                        try {
                            Files.copy(SelectedImageFileofProductPage.toPath(), targetPath, REPLACE_EXISTING);
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    alert.show();

                    showProducts();
                    getDataTBProductPageNHI();

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
                    ps.setString(1, SelectProductofProductPage.getProductID());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        Product p = new Product("1", "1", "1", "1", "1");
                        list.add(p);
                    }
                    if (!list.isEmpty()) {
                        throw new Exception("This product cannot be deleted because it is related to many other software data");
                    }

                    sql = "DELETE  FROM PRODUCT WHERE ProductID = ?";

                    try {
                        ps = con.prepareStatement(sql);
                        ps.setString(1, SelectProductofProductPage.getProductID());

                        if (ps.executeUpdate() != 1) {

                            Alert alert = new Alert(Alert.AlertType.ERROR);

                            alert.setContentText("Delete product failed");
                            alert.show();
                        } else {

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);

                            alert.setContentText("Delete product successfully");
                            alert.show();

                            showProducts();
                            getDataTBProductPageNHI();
                            btnEditProduct.setDisable(true);
                            btnDeleteProduct.setDisable(true);
                            btnDetailProduct.setDisable(true);
                            showOverViewTab();

                        }
                    } catch (SQLException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Product deletion failed because the database did not allow it");
                        alert.setResizable(true);
                        alert.show();
                    }
                } catch (SQLException ex) {

                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(null);
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
                + "WHERE P.ProductId = '" + SelectProductofProductPage.getProductID() + "'";
        try {
            // Tạo satement
            stm = con.createStatement();
            // truy vấn
            rs = stm.executeQuery(sql);

            // tạo observableList để lưu dữ liệu từ ResultSet
            ProductListofProductPage = FXCollections.observableArrayList();

            ProductListofProductPage.clear();

            while (rs.next()) {
                ProductListofProductPage.add(new Product(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(6), rs.getInt(7), rs.getFloat(4), rs.getFloat(5), rs.getString(8).substring(0, 10)));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return ProductListofProductPage;
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
    //======================================================================
    // ============================HANDLE STATICS PAGE ======================
    //=======================================================================

    public void showRevenueTab(int YearFrom, int YearTo) {

        // ==========================TAB THỐNG KÊ THEO NĂM ====================
        BarChatforYear.getData().clear();
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
        funds.setName("Funds");
        profit.setName("Profit");
        revenue.setName("Revenue");
        BarChatforYear.getData().addAll(revenue, funds, profit);

        // HIEN THI TABLE VIEW IN TAB Revenue
        tvRevenue.setItems(StaticsListByYearList);
        YearRevenueCol.setCellValueFactory(new PropertyValueFactory<>("YearStatics"));
        FundsColFundYear.setCellValueFactory(new PropertyValueFactory<>("funds"));
        RevenueColRevenueYear.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        ProfitColRevenueYear.setCellValueFactory(new PropertyValueFactory<>("profit"));

        formatFloatNumberinTableView(FundsColFundYear, "###,###.#");
        formatFloatNumberinTableView(RevenueColRevenueYear, "###,###.#");
        formatFloatNumberinTableView(ProfitColRevenueYear, "###,###.#");
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2019, LocalDate.now().getYear(), LocalDate.now().getYear());
        ChoiceYearSpinner.setValueFactory(valueFactory);

        SpinnerValueFactory<Integer> monthListFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, LocalDate.now().getMonthValue());

        ChoiceYearSpinner3.setValueFactory(valueFactory);
        ChoiceMonthSpinner.setValueFactory(monthListFactory);

    }

    public void showRevenuebyDay(int Month, int Year) {
        BarChartForMonth1.getData().clear();
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
        funds.setName("Funds");
        profit.setName("Profit");
        revenue.setName("Revenue");
        BarChartForMonth1.getData().addAll(profit, funds, revenue);

        tvRevenue2.setItems(StaticsListByYearList);
        MonthRevenue1.setCellValueFactory(new PropertyValueFactory<>("MonthStatics"));
        FundsColFundMonth1.setCellValueFactory(new PropertyValueFactory<>("funds"));
        RevenueColRevenueMonth1.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        ProfitColRevenueMonth1.setCellValueFactory(new PropertyValueFactory<>("profit"));
        formatFloatNumberinTableView(FundsColFundMonth1, "###,###.#");
        formatFloatNumberinTableView(RevenueColRevenueMonth1, "###,###.#");
        formatFloatNumberinTableView(ProfitColRevenueMonth1, "###,###.#");
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

        formatFloatNumberinTableView(RevenueColRevenueMonth11, "###,###.#");
        formatFloatNumberinTableView(ProfitColRevenueMonth11, "###,###.#");
        formatFloatNumberinTableView(FundsColFundMonth11, "###,###.#");
    }

    public void showRevenuebyMonth(int Year) {
        BarChartForMonth.getData().clear();
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
        funds.setName("Funds");
        profit.setName("Profit");
        revenue.setName("Revenue");
        BarChartForMonth.getData().addAll(profit, funds, revenue);

        tvRevenue1.setItems(StaticsListByYearList);
        MonthRevenue.setCellValueFactory(new PropertyValueFactory<>("MonthStatics"));
        FundsColFundMonth.setCellValueFactory(new PropertyValueFactory<>("funds"));
        RevenueColRevenueMonth.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        ProfitColRevenueMonth.setCellValueFactory(new PropertyValueFactory<>("profit"));
        formatFloatNumberinTableView(FundsColFundMonth, "###,###.#");
        formatFloatNumberinTableView(RevenueColRevenueMonth, "###,###.#");
        formatFloatNumberinTableView(ProfitColRevenueMonth, "###,###.#");
    }

    public void showOverViewTab() {
        OverviewLineChart.getData().clear();
        String Day7 = LocalDate.now().toString();
        String Day6 = LocalDate.now().minusDays(1).toString();
        String Day5 = LocalDate.now().minusDays(2).toString();
        String Day4 = LocalDate.now().minusDays(3).toString();
        String Day3 = LocalDate.now().minusDays(4).toString();
        String Day2 = LocalDate.now().minusDays(5).toString();
        String Day1 = LocalDate.now().minusDays(6).toString();

        // vốn
        XYChart.Series<String, Number> funds = new XYChart.Series<>();
        funds.getData().clear();
        XYChart.Data<String, Number> day1 = new XYChart.Data<>(Day1, getFunds(Day1));
        XYChart.Data<String, Number> day2 = new XYChart.Data<>(Day2, getFunds(Day2));
        XYChart.Data<String, Number> day3 = new XYChart.Data<>(Day3, getFunds(Day3));
        XYChart.Data<String, Number> day4 = new XYChart.Data<>(Day4, getFunds(Day4));
        XYChart.Data<String, Number> day5 = new XYChart.Data<>(Day5, getFunds(Day5));
        XYChart.Data<String, Number> day6 = new XYChart.Data<>(Day6, getFunds(Day6));
        XYChart.Data<String, Number> day7 = new XYChart.Data<>(Day7, getFunds(Day7));
        funds.getData().addAll(day1, day2, day3, day4, day5, day6, day7);
        funds.setName("Funds");

        XYChart.Series<String, Number> revenue = new XYChart.Series<>();
        revenue.getData().clear();
        XYChart.Data<String, Number> rday1 = new XYChart.Data<>(Day1, getRevenue(Day1));
        XYChart.Data<String, Number> rday2 = new XYChart.Data<>(Day2, getRevenue(Day2));
        XYChart.Data<String, Number> rday3 = new XYChart.Data<>(Day3, getRevenue(Day3));
        XYChart.Data<String, Number> rday4 = new XYChart.Data<>(Day4, getRevenue(Day4));
        XYChart.Data<String, Number> rday5 = new XYChart.Data<>(Day5, getRevenue(Day5));
        XYChart.Data<String, Number> rday6 = new XYChart.Data<>(Day6, getRevenue(Day6));
        XYChart.Data<String, Number> rday7 = new XYChart.Data<>(Day7, getRevenue(Day7));
        revenue.getData().addAll(rday1, rday2, rday3, rday4, rday5, rday6, rday7);
        revenue.setName("Revenue");

        XYChart.Series<String, Number> profit = new XYChart.Series<>();
        profit.getData().clear();
        XYChart.Data<String, Number> pday1 = new XYChart.Data<>(Day1, getProfit(Day1));
        XYChart.Data<String, Number> pday2 = new XYChart.Data<>(Day2, getProfit(Day2));
        XYChart.Data<String, Number> pday3 = new XYChart.Data<>(Day3, getProfit(Day3));
        XYChart.Data<String, Number> pday4 = new XYChart.Data<>(Day4, getProfit(Day4));
        XYChart.Data<String, Number> pday5 = new XYChart.Data<>(Day5, getProfit(Day5));
        XYChart.Data<String, Number> pday6 = new XYChart.Data<>(Day6, getProfit(Day6));
        XYChart.Data<String, Number> pday7 = new XYChart.Data<>(Day7, getProfit(Day7));

        profit.getData().addAll(pday1, pday2, pday3, pday4, pday5, pday6, pday7);
        profit.setName("Profit");
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
        StaticsList.add(new Statics(Day5, getFunds(Day5), getRevenue(Day5), getProfit(Day5)));
        StaticsList.add(new Statics(Day6, getFunds(Day6), getRevenue(Day6), getProfit(Day6)));
        StaticsList.add(new Statics(Day7, getFunds(Day7), getRevenue(Day7), getProfit(Day7)));
        tvOverview.setItems(StaticsList);
        DayColOverview.setCellValueFactory(new PropertyValueFactory<>("dateStatics"));
        FundsColOverview.setCellValueFactory(new PropertyValueFactory<>("funds"));
        RevenueCol.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        ProfitCol.setCellValueFactory(new PropertyValueFactory<>("profit"));

        String pattern = "###,###.#";

        formatFloatNumberinTableView(FundsColOverview, pattern);
        formatFloatNumberinTableView(RevenueCol, pattern);
        formatFloatNumberinTableView(ProfitCol, pattern);
    }

    public void formatFloatNumberinTableView(TableColumn<Statics, Float> colFormat, String pattern) {
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        colFormat.setCellFactory(column -> new TableCell<Statics, Float>() {
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(decimalFormat.format(item));
                }
            }
        });
    }

    public void showInventoryTab() {
        STTColumnTvInventory.setCellValueFactory(data -> {
            Product product = data.getValue();
            int rowIndex = tvInventory.getItems().indexOf(product) + 1;
            return new SimpleIntegerProperty(rowIndex).asObject();

        });

        tvInventory.setItems(getProductofStaticsInterface());
        ProductIDCol.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        ProductNameCol.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        NKIDCol.setCellValueFactory(new PropertyValueFactory<>("NKId"));
        ImportCol.setCellValueFactory(new PropertyValueFactory<>("ImportQuantity"));
        SoldCol.setCellValueFactory(new PropertyValueFactory<>("SoldQuantity"));
        InventoryCol.setCellValueFactory(new PropertyValueFactory<>("Inventory"));
        SaleRateCol.setCellValueFactory(new PropertyValueFactory<>("SaleRate"));

        SaleRateCol.setCellFactory(column -> new TableCell<Product, Float>() {
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", item));
                }
            }
        });

        tfSearchInventory.setOnKeyReleased(event -> {
            String keyword = tfSearchInventory.getText().trim();
//            filterCategory.setValue(null); // Reset giá trị của filterCategory
//            filterBrand.setValue(null); // Reset giá trị của filterBrand
            searchProductsbyNameofStaticsPage(keyword);

        });

        cbCatInvetory.setItems(getCategoryofStaticsPage());
        cbCatInvetory.setOnAction(event -> {
            if (!(cbCatInvetory.getValue().isEmpty())) {
                String keyword = cbCatInvetory.getValue();
                searchProductsbyCategoryofStaticsPage(keyword);
            }

        });
        btnReLoad.setOnAction(event -> {
            tvInventory.setItems(getProductofStaticsInterface());
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
        String query = "SELECT COUNT(CustomerID) FROM Customer where not CustomerName like 'NONE'";
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
        String query = "SELECT COUNT(E.EmpID) FROM Employee E JOIN Account A ON E.EmpID = A.EmpID WHERE A.Status = 1";
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

    public ObservableList<Product> getProductofStaticsInterface() {
        ProductListofStaticsInterface = FXCollections.observableArrayList();
        ProductListofStaticsInterface.clear();
        String sql = "SELECT P.ProductId, P.ProductName, PD.NKId, PD.ImportQuantity, PD.SoldQuantity, (PD.ImportQuantity - PD.SoldQuantity) AS INVENTORY, C.CategoryName\n"
                + "\n"
                + "                FROM Product P JOIN ProductDetail PD ON P.ProductId = PD.ProductId\n"
                + "                JOIN Category C ON P.CategoryID = C.CategoryID";
        try {

            stm = con.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                Product p = new Product(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getString(7));
                ProductListofStaticsInterface.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ProductListofStaticsInterface;
    }

    public void searchProductsbyNameofStaticsPage(String keyword) {

        List<Product> pList;

        pList = getProductofStaticsInterface().stream().filter(p -> p.getProductName().contains(keyword)).collect(Collectors.toList());
        ProductListofStaticsInterface = FXCollections.observableArrayList(pList);
        tvInventory.setItems(ProductListofStaticsInterface);
    }

    public ObservableList<String> getCategoryofStaticsPage() {
        String sql = "SELECT * from Category";

        try {
            // Tạo satement
            stm = con.createStatement();
            // truy vấn
            rs = stm.executeQuery(sql);

            // tạo observableList để lưu dữ liệu từ ResultSet
            CatListofStaticsPage = FXCollections.observableArrayList();

            CatListofStaticsPage.clear();

            while (rs.next()) {
                String CategoryName = rs.getString("CategoryName");
                CatListofStaticsPage.add(CategoryName);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return CatListofStaticsPage;
    }

    public void searchProductsbyCategoryofStaticsPage(String keyword) {

        List<Product> pList;

        pList = getProductofStaticsInterface().stream().filter(p -> p.getCategory().equalsIgnoreCase(keyword)).collect(Collectors.toList());
        ProductListofStaticsInterface = FXCollections.observableArrayList(pList);
        tvInventory.setItems(ProductListofStaticsInterface);
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
                throw new Exception("From year is must be > 2020 and <= CurrentYear");
            }
            ToYear = Integer.parseInt(tfToYear.getText());
            if (ToYear < 2020 || ToYear > LocalDate.now().getYear()) {
                throw new Exception("To year is must be > 2020 and <= CurrentYear");
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
                    writeExcel(getProductofStaticsInterface(), file);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Export Excell successfully");
                    alert.show();
                    Desktop.getDesktop().open(file);
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

    //===================================================================================
    //===================================HANDLE SUPPLIER INTERFACE =======================
    //===================================================================================
    @FXML
    private void SuppilerAdd(ActionEvent event) {

        //get gtri cua cua field
//        int SuppilerCode  = Integer.parseInt(tfSuppilerCode.getText());
        try {
            if (tfSuppilerName.getText().isBlank()) {
                throw new Exception("Suppiler Name is not null");
            }
            String SuppilerName = tfSuppilerName.getText();
            if (tfSuppilerAddress.getText().isBlank()) {
                throw new Exception("Suppiler Address is not null");
            }
            List<Suppiler> sList = getSuppilers().stream().filter(predicate -> predicate.getSuppilerName().equals(tfSuppilerName.getText().trim())).collect(Collectors.toList());
            if (!sList.isEmpty()) {
                throw new Exception("SuppilerName must be not duplicate");
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

            if (tfSuppilerEmail.getText().isBlank()) {
                throw new Exception("Suppiler Email is not null");
            }
            String email = tfSuppilerEmail.getText();
            sList = getSuppilers().stream().filter(predicate -> predicate.getEmail().equals(tfSuppilerEmail.getText().trim())).collect(Collectors.toList());
            if (!sList.isEmpty()) {
                throw new Exception("SuppilerEmail must be not duplicate");
            }
            if (tfSuppilerPhone.getText().isBlank()) {
                throw new Exception("Suppiler Phone is not null");
            }

            p = Pattern.compile("0[0-9]{9}");

            m = p.matcher(tfSuppilerPhone.getText());
            if (m.matches()) {

            } else {
                throw new Exception("Suppiler phone number must be 10 digits and correct format 0xxxxxxxx ");
            }
            sList = getSuppilers().stream().filter(predicate -> predicate.getPhoneNumber().equals(tfSuppilerPhone.getText().trim())).collect(Collectors.toList());
            if (!sList.isEmpty()) {
                throw new Exception("SuppilerPhone must be not duplicate");
            }

            String phoneNumber = tfSuppilerPhone.getText();
            String sql = "INSERT INTO Suppiler "
                    + "VALUES(N'" + SuppilerName + "',"
                    + "N'" + address + "',"
                    + "'" + email + "',"
                    + "'" + phoneNumber + "')";

            //System.out.println(sql);
            executeSQL(sql);
            newSuppiler();
            getDataSupplierPageNHI();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void onSelectSuppiler(MouseEvent event) {
        SelectedSuppiler = tvSuppiler.getSelectionModel().getSelectedItem();
        if (SelectedSuppiler != null) {
            //xuat du lieu
            tfSuppilerCode.setText("" + SelectedSuppiler.getSuppilerCode());
            tfSuppilerName.setText(SelectedSuppiler.getSuppilerName());
            tfSuppilerAddress.setText(SelectedSuppiler.getAddress());
            tfSuppilerEmail.setText(SelectedSuppiler.getEmail());
            tfSuppilerPhone.setText(SelectedSuppiler.getPhoneNumber());
            btnAddSuppiler.setDisable(true);
        }
    }

    @FXML
    private void SuppilerUpdate(ActionEvent event) {
        btnAddSuppiler.setDisable(false);

        //get gtri cua cua field
        //int SuppilerCode = Integer.parseInt(tfSuppilerCode.getText());
//        String SuppilerName = tfSuppilerName.getText();
//        String address = tfSuppilerAddress.getText();
//        String email = tfSuppilerEmail.getText();
//        String phoneNumber = tfSuppilerPhone.getText();
        try {
            int SuppilerCode = Integer.parseInt(tfSuppilerCode.getText());

            if (tfSuppilerName.getText().isBlank()) {
                throw new Exception("Suppiler Name is not null");
            }
            List<Suppiler> s1List = getSuppilers().stream().filter(predicate -> predicate.getSuppilerName().equals(tfSuppilerName.getText().trim()) && !predicate.getSuppilerName().equals(SelectedSuppiler.getSuppilerName())).collect(Collectors.toList());
            if (!s1List.isEmpty()) {
                throw new Exception("SuppilerName must be not duplicate");
            }
            String SuppilerName = tfSuppilerName.getText();
            if (tfSuppilerAddress.getText().isBlank()) {
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

            if (tfSuppilerEmail.getText().isBlank()) {
                throw new Exception("Suppiler Email is not null");
            }
            s1List = getSuppilers().stream().filter(predicate -> predicate.getEmail().equals(tfSuppilerEmail.getText().trim()) && !predicate.getEmail().equals(SelectedSuppiler.getEmail())).collect(Collectors.toList());
            if (!s1List.isEmpty()) {
                throw new Exception("SuppilerEmail must be not duplicate");
            }
            String email = tfSuppilerEmail.getText();
            if (tfSuppilerPhone.getText().isBlank()) {
                throw new Exception("Suppiler Phone is not null");
            }
            p = Pattern.compile("0[0-9]{9}");

            m = p.matcher(tfSuppilerPhone.getText());
            if (m.matches()) {

            } else {
                throw new Exception("Suppiler phone number must be 10 digits and correct format 0xxxxxxxx");
            }
            String phoneNumber = tfSuppilerPhone.getText();
            s1List = getSuppilers().stream().filter(predicate -> predicate.getPhoneNumber().equals(tfSuppilerPhone.getText().trim()) && !predicate.getPhoneNumber().equals(SelectedSuppiler.getPhoneNumber())).collect(Collectors.toList());
            if (!s1List.isEmpty()) {
                throw new Exception("SuppilerPhone must be not duplicate");
            }

            String sql = "UPDATE Suppiler SET "
                    + "SuppilerName=N'" + SuppilerName + "',"
                    + "address=N'" + address + "',"
                    + "email='" + email + "',"
                    + "phoneNumber='" + phoneNumber + "'"
                    + "WHERE SuppilerCode=" + SuppilerCode + "";

            //System.out.println(sql);
            executeSQL(sql);

            //load lai du lieu
            showSuppilers();
            getDataSupplierPageNHI();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
            btnAddSuppiler.setDisable(true);
        }
    }

    public ObservableList<Suppiler> getSuppilers() {

        //tao ds chua cac Suppiler
        ObservableList<Suppiler> list = FXCollections.observableArrayList();
        //tao doi tuong
        list.clear();

        //viet cau lenh truy van
        String query = "SELECT s.SuppilerCode, s.SuppilerName, s.phoneNumber, s.email, s.address, COALESCE(SUM(w.total), 0) as total\n"
                + "FROM Suppiler s\n"
                + "LEFT JOIN WareHouseReceipt w ON s.SuppilerCode = w.SupplierCode\n"
                + "GROUP BY s.SuppilerCode, s.SuppilerName, s.phoneNumber, s.email, s.address\n"
                + "ORDER BY total DESC;";

        //thuc thi
        Statement st;
        ResultSet rs;

        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            Suppiler s;
            while (rs.next()) {
                int SuppilerCode = rs.getInt("SuppilerCode");
                String SuppilerName = rs.getString("SuppilerName");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                float total = rs.getFloat("total");

                //tao doi tuong Suppiler
                s = new Suppiler(SuppilerCode, SuppilerName, address, email, phoneNumber, total);
                //System.out.println(s);
                list.add(s);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
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
        colSuppilerTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tvSuppiler.setItems(sList);

        tvSuppiler.setFixedCellSize(30);
    }

    private void executeSQL(String query) {
        Statement st;
        try {
            st = con.createStatement();

            //thuc thi
            st.executeUpdate(query);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("result");
            alert.setContentText("Operation successful!");
            alert.show();

//            newSuppiler();
//            getDataSupplierPageNHI();
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
    private void newSuppiler() {
        tfSuppilerCode.setText("");
        tfSuppilerName.setText("");
        tfSuppilerAddress.setText("");
        tfSuppilerEmail.setText("");
        tfSuppilerPhone.setText("");
        btnAddSuppiler.setDisable(false);
    }

    @FXML
    private void handleSearchSuppiler(KeyEvent event) {
        String keyword = tfSuppilerSearch.getText();
        SearchSuppiler(keyword);
//        SearchSuppilerByAddress(keyword);
//        SearchSuppilerByPhone(keyword);
    }

    public void SearchSuppiler(String keyword) {

        List<Suppiler> sList;
        sList = (List<Suppiler>) getSuppilers().stream().filter(s -> s.getSuppilerName().toLowerCase().contains(keyword.toLowerCase()) || s.getAddress().toLowerCase().contains(keyword.toLowerCase()) || s.getEmail().toLowerCase().contains(keyword.toLowerCase()) || s.getPhoneNumber().toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList());
        ObservableList<Suppiler> SuppilerList = FXCollections.observableArrayList(sList);
        tvSuppiler.setItems(SuppilerList);
    }

    //=====================================================
    //=========================HANDLE CUSTOMER INTERFACE=================
    //====================================================================
    public ObservableList<Customer> getCustomers() {

        //tao ds chua cac Product
        ObservableList<Customer> list = FXCollections.observableArrayList();
        //tao doi tuong
        list.clear();

        //viet cau lenh truy van
        String query = "SELECT * FROM Customer WHERE NOT CustomerName like 'NONE' ";

        //thuc thi
        Statement st;
        ResultSet rs;

        try {
            st = con.createStatement();
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
            System.out.println(ex.getMessage());
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

    @FXML
    private void okaddCustomer(ActionEvent event) {
        //String customerId  = tfCustomerId.getText();

        //String Dob = tfCustomerDob.getValue().toString();
        try {
            if (tfCustomerDName1.getText().isBlank()) {
                throw new Exception("Customer Name is not null");
            }
            String customerName = tfCustomerDName1.getText();
//            List<Customer> cList = getCustomers().stream().filter(predicate -> predicate.getCustomerName().equals(tfCustomerDName1.getText().trim())).collect(Collectors.toList());
//                    if (!cList.isEmpty()) {
//                        throw new Exception("CustomerName must be not duplicate");
//                    }

            String customerPhone = tfCustomerDPhone1.getText();
            Pattern p;
            Matcher m;

            if (tfCustomerDPhone1.getText().isBlank()) {
                throw new Exception("Customer Phone is not null");
            }
            p = Pattern.compile("0[0-9]{9}");

            m = p.matcher(tfCustomerDPhone1.getText());
            if (m.matches()) {

            } else {
                throw new Exception("Customer phone number must be 10 digits and correct format 0xxxxxxxx");
            }
            List<Customer> cList = getCustomers().stream().filter(predicate -> predicate.getCustomerPhone().equals(tfCustomerDPhone1.getText().trim())).collect(Collectors.toList());
            if (!cList.isEmpty()) {
                throw new Exception("CustomerPhone must be not duplicate");
            }

            if (tfCustomerDDob1.getValue() == null) {
                throw new Exception("Customer Dob is not null");
            }
            if (tfCustomerDDob1.getValue().compareTo(LocalDate.now().minusYears(14)) <= 0
                    && tfCustomerDDob1.getValue().compareTo(LocalDate.now().minusYears(70)) >= 0) {
            } else {
                throw new Exception("Please enter the age from 14 to 70 years old.");
            }

            String Dob = tfCustomerDDob1.getValue().toString();

//            if (tfCustomerPoint.getText().isEmpty()) {
//                throw new Exception("Customer point is not null");
//            }
            int Point = 0;
            String sql = "INSERT INTO Customer "
                    + "VALUES(N'" + customerName + "',"
                    + "'" + customerPhone + "',"
                    + "'" + Dob + "',"
                    + Point + ")";

            //System.out.println(sql);
            executeSQL(sql);
            backCustomer(event);
            showCustomers();
            showOverViewTab();

            tfCustomerDId1.setDisable(true);
            tfCustomerDName1.setDisable(true);
            tfCustomerDPhone1.setDisable(true);
            tfCustomerDDob1.setDisable(true);
            tfCustomerDPoint1.setDisable(true);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }

    @FXML
    private void onSelectCustomer1(MouseEvent event) {
        CustomerSelected = tvCustomerView.getSelectionModel().getSelectedItem();
        //xuat du lieu
        if (CustomerSelected != null) {
            tfCustomerDId1.setText(CustomerSelected.getCustomerId());
            tfCustomerDName1.setText(CustomerSelected.getCustomerName());
            tfCustomerDPhone1.setText(CustomerSelected.getCustomerPhone());

            tfCustomerDPoint1.setText("" + CustomerSelected.getPoint());
            tfCustomerDDob1.setValue(LocalDate.parse(CustomerSelected.getDob()));
        }

    }

    @FXML
    private void okUpdateCustomer(ActionEvent event) {

        try {

            if (tfCustomerDName1.getText().isBlank()) {
                throw new Exception("Customer Name is not null");
            }
            String customerName = tfCustomerDName1.getText();
            //getCustomers().stream().filter(predicate -> predicate.getCustomerName().equals(tfCustomerDName1.getText().trim())).collect(Collectors.toList())

            String customerPhone = tfCustomerDPhone1.getText();
            List<Customer> c1List = getCustomers().stream().filter(predicate -> predicate.getCustomerPhone().equals(tfCustomerDPhone1.getText().trim()) && !predicate.getCustomerPhone().equals(CustomerSelected.getCustomerPhone())).collect(Collectors.toList());
            if (!c1List.isEmpty()) {
                throw new Exception("CustomerPhone must be not duplicate");
            }
            Pattern p;
            Matcher m;

            if (tfCustomerDPhone1.getText().isBlank()) {
                throw new Exception("Customer Phone is not null");
            }
            p = Pattern.compile("0[0-9]{9}");

            m = p.matcher(tfCustomerDPhone1.getText());
            if (m.matches()) {

            } else {
                throw new Exception("Customer phone number must be 10 digits and correct format 0xxxxxxxx");
            }

            if (tfCustomerDDob1.getValue().toString().isBlank()) {
                throw new Exception("Customer Dob is not null");
            }
            if (tfCustomerDDob1.getValue().compareTo(LocalDate.now().minusYears(14)) <= 0
                    && tfCustomerDDob1.getValue().compareTo(LocalDate.now().minusYears(70)) >= 0) {
            } else {
                throw new Exception("Please enter the age from 14 to 70 years old.");
            }
            String Dob = tfCustomerDDob1.getValue().toString();

//            if (tfCustomerUPoint.getText().isEmpty()) {
//                throw new Exception("Customer point is not null");
//            }
            int Point = Integer.parseInt(tfCustomerDPoint1.getText());
            String customerId = tfCustomerDId1.getText();
            String sql = "UPDATE Customer SET "
                    + "customerName=N'" + customerName + "',"
                    + "customerPhone= '" + customerPhone + "',"
                    + "Dob='" + Dob + "',"
                    + "Point=" + Point
                    + " WHERE customerId=" + customerId;

            //System.out.println(sql);
            executeSQL(sql);

            //load lai du lieu
            showCustomers();
            backCustomer(event);
            //showCustomers1();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }

    @FXML
    private void handleSearchCustomer(KeyEvent event) {
        String keyword = tfCustomerSearch.getText();
        SearchCustomer(keyword);
    }

    public void SearchCustomer(String keyword) {

        List<Customer> cList;
        cList = (List<Customer>) getCustomers().stream().filter(c -> c.getCustomerName().toLowerCase().contains(keyword.toLowerCase()) || c.getCustomerPhone().toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList());
        ObservableList<Customer> CustomerList = FXCollections.observableArrayList(cList);
        tvCustomerView.setItems(CustomerList);

    }

    @FXML
    private void backCustomer(ActionEvent event) {
        tvCustomerView.setDisable(false);
        tfCustomerDId1.setDisable(true);
        tfCustomerDName1.setDisable(true);
        tfCustomerDPhone1.setDisable(true);
        tfCustomerDDob1.setDisable(true);
        tfCustomerDPoint1.setDisable(true);
        tfCustomerDId1.setVisible(true);
        tfCustomerDName1.setVisible(true);
        tfCustomerDPhone1.setVisible(true);
        tfCustomerDDob1.setVisible(true);
        tfCustomerDPoint1.setVisible(true);
        okaddCustomer.setVisible(false);
        okUpdateCustomer.setVisible(false);

        tfCustomerDId1.setText("");
        tfCustomerDName1.setText("");
        tfCustomerDPhone1.setText("");
        tfCustomerDDob1.setValue(null);
        tfCustomerDPoint1.setText("");
    }

    @FXML
    private void CustomerUpdate(ActionEvent event) {
        tvCustomerView.setDisable(false);
        lbCustomerID.setVisible(true);
        tfCustomerDId1.setVisible(true);
        tfCustomerDPoint1.setEditable(false);
        tfCustomerDName1.setDisable(false);
        tfCustomerDPhone1.setDisable(false);
        tfCustomerDDob1.setDisable(false);
        lbCustomerPoint.setVisible(true);
        tfCustomerDPoint1.setVisible(true);
        okUpdateCustomer.setVisible(true);
        okaddCustomer.setVisible(false);
    }

    @FXML
    private void AddCustomer(ActionEvent event) {
        lbCustomerID.setVisible(false);
        tfCustomerDId1.setVisible(false);
        tfCustomerDName1.clear();
        tfCustomerDPhone1.clear();
        tfCustomerDDob1.setValue(null);

        tvCustomerView.setDisable(true);
//        Customerid.setVisible(false);
        tfCustomerDName1.setDisable(false);
        tfCustomerDPhone1.setDisable(false);
        tfCustomerDDob1.setDisable(false);
        lbCustomerPoint.setVisible(false);
        tfCustomerDPoint1.setVisible(false);
//        CustomerPoint.setVisible(false);
        okUpdateCustomer.setVisible(false);
        okaddCustomer.setVisible(true);
    }

    @FXML
    private void openProductInterface(ActionEvent event) {
        StackPaneMainPage.getChildren().filtered(predicate -> predicate.isVisible()).forEach(node -> node.setVisible(false));
        productInterface.setVisible(true);
    }

    @FXML
    private void openStaticsInterface(ActionEvent event) {
        StackPaneMainPage.getChildren().filtered(predicate -> predicate.isVisible()).forEach(node -> node.setVisible(false));
        StaticsInterface.setVisible(true);
    }

    @FXML
    private void openSupplierInterface(ActionEvent event) {
        StackPaneMainPage.getChildren().filtered(predicate -> predicate.isVisible()).forEach(node -> node.setVisible(false));
        SupplierInterface.setVisible(true);
    }

    @FXML
    private void openCustomerInterface(ActionEvent event) {
        StackPaneMainPage.getChildren().filtered(predicate -> predicate.isVisible()).forEach(node -> node.setVisible(false));
        CustomerInterface.setVisible(true);
    }

    @FXML
    private void openEmployeeInterface(ActionEvent event) {
        StackPaneMainPage.getChildren().filtered(predicate -> predicate.isVisible()).forEach(node -> node.setVisible(false));
        StackPaneStaffInterface.getChildren().filtered(predicate -> predicate.isVisible()).forEach(node -> node.setVisible(false));
        StaffViewInterface.setVisible(true);
        employeeInterface.setVisible(true);
    }

    //========================================================================
    // ====================HANDLE EMPLOYEE INTERFACE ==========================
    //========================================================================
    //======================HANDLE DELETE STAFF =================
    @FXML
    private void handleSearchStaff(KeyEvent event) {

        String keyword = tfSearchStaff.getText().trim();
        searchStaffsbyName(keyword);
    }

    public void searchStaffsbyName(String keyword) {

        List<Staff> sList;

        sList = getStaff().stream().filter(p -> p.getSName().contains(keyword) || p.getSId().equals(keyword)).collect(Collectors.toList());
        StaffListOfStaffInterface = FXCollections.observableArrayList(sList);
        tvStaff.getItems().clear();
        tvStaffOfUpdate.getItems().clear();
        tvStaffofDetail.getItems().clear();
        tvStaff.setItems(StaffListOfStaffInterface);
        tvStaffOfUpdate.setItems(StaffListOfStaffInterface);
        tvStaffofDetail.setItems(StaffListOfStaffInterface);
    }

    public ObservableList<Staff> getStaff() {
        //tao ds chua cac product
        ObservableList<Staff> list = FXCollections.observableArrayList();

        //tao doi tuong
        //viet lenh truy van
        String query = "SELECT * FROM Employee";

        //thuc thi
        Statement st;
        ResultSet rs;

        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            Staff s;
            while (rs.next()) {
                String SId = rs.getString("EmpID");
                String SName = rs.getString("EmpName");
                String SEmail = rs.getString("SEmail");
                String SImage = rs.getString("SImage");

                Image img = new Image(new File(IMAGE_DIR + "\\" + SImage).toURI().toString());
                ImageView imgView = new ImageView(img);
                imgView.setFitHeight(40);
                imgView.setFitWidth(50);
                String SGender = rs.getString("Gender");
                String SCCCD = rs.getString("CCCD");
                String SPhone = rs.getString("PhoneNumber");
                String SPosition = rs.getString("SPosition");
                String SDob = rs.getString("YOB");

                s = new Staff(SId, SName, SEmail, SGender, SPosition, SCCCD, imgView, SPhone, SDob);
                //System.out.println(p);
                //them san pham moi vao list
                list.add(s);
            }
        } catch (SQLException ex) {
            System.out.println("kkk");
        }
        return list;

    }

    public void showStaffofDelete() {
        ObservableList<Staff> lisr = getStaff();

        //show ds
        colIdStaffofDelete.setCellValueFactory(new PropertyValueFactory<>("SId"));
        colNameStaffofDelete.setCellValueFactory(new PropertyValueFactory<>("SName"));
        colImageStaffofDelete.setCellValueFactory(new PropertyValueFactory<>("SImage"));
        colEmailStaffofDelete.setCellValueFactory(new PropertyValueFactory<>("SEmail"));
        colGenderStaffofDelete.setCellValueFactory(new PropertyValueFactory<>("SGender"));
        colPhoneStaffofDelete.setCellValueFactory(new PropertyValueFactory<>("SPhone"));

        tvStaffofDelete.setItems(lisr);
    }

    @FXML
    private void OnSelectStaffOfDelete(MouseEvent event) {
        Staff p = tvStaffofDelete.getSelectionModel().getSelectedItem();

        //xuat du lieu
        StIdDelete.setText(p.getSId());
        StNameDelete.setText(p.getSName());
        StEmailDelete.setText(p.getSEmail());
        StGenderDelete.setText(p.getSGender());
        StNumDelete.setText(p.getSPhone());
        StDobDelete.setValue(LocalDate.parse(p.getSDob()));
        StLevelDelete.setText(p.getSPosition());
        StCCDelete.setText(p.getSCCCD());

        ImageView img = p.getSImage(); //method getImage() cua product class
        Image image = img.getImage(); //method getImage() cua ImageView class
        StImsgeDelete.setImage(image);
    }

    @FXML
    private void DeleteStaff(ActionEvent event) {
        int EmpID = Integer.parseInt(StIdDelete.getText());
        String sql = "DELETE FROM Employee  "
                + "WHERE EmpID = " + EmpID;

        //System.out.println(sql);
        executeSQL(sql);

        //xoa du lieu
        resetOfDelete();

        //load lai du lieu
        showStaffofDelete();

    }

    private void resetOfDelete() {
        StIdDelete.setText("");
        StNameDelete.setText("");
        StEmailDelete.setText("");
        StGenderDelete.setText("");
        StNumDelete.setText("");
        //StDobDelete.setValue("" );
        StLevelDelete.setText("");
        StCCDelete.setText("");

    }

    //=========================HANDLE VIEW STAFF ==================
    public void showStaffofView() {
        ObservableList<Staff> lisr = getStaff();

        //show ds
        colIdStaff.setCellValueFactory(new PropertyValueFactory<>("SId"));
        colNameStaff.setCellValueFactory(new PropertyValueFactory<>("SName"));
        colImageStaff.setCellValueFactory(new PropertyValueFactory<>("SImage"));
        colEmailStaff.setCellValueFactory(new PropertyValueFactory<>("SEmail"));
        colGenderStaff.setCellValueFactory(new PropertyValueFactory<>("SGender"));
        colPhoneStaff.setCellValueFactory(new PropertyValueFactory<>("SPhone"));

        tvStaff.setItems(lisr);

        ObservableList<String> positionList = FXCollections.observableArrayList();
        positionList.clear();
        positionList.add("Staff");
        positionList.add("Manager");

    }

    // ======================HANDLE ADD STAFF ================= 
    @FXML
    private void OnAdd(ActionEvent event) throws IOException {
        String id = StIdAdd.getText();
        String name = StNameAdd.getText();
        String email = StEmailAdd.getText();
        String gender;

        if (CkFemale.isSelected()) {
            gender = CkFemale.getText();
        } else {
            gender = CkMale.getText();
        }

        String img = selectImageName;

        String num = StNumAdd.getText();
        //String gender = StGenderAdd.getText();

        String Position = tfPositionOfAddEmployee.getText();

        String CCCD = StCCAdd.getText();

        Pattern p;
        Matcher m;
        try {
            p = Pattern.compile("\\d{1,}");
            if (id.isEmpty()) {
                throw new Exception("ID is not null");
            }
            m = p.matcher(id);
            if (m.matches()) {
            } else {
                throw new Exception("ID must be a number.");
            }
            // kiem tra khoa ngoai 
            int EmpID = Integer.parseInt(id);
            String sql = "SELECT * FROM Account where ? in (select EmpID from Account)";
            PreparedStatement ps;
            ObservableList<Acount> accountlList = FXCollections.observableArrayList();
            accountlList.clear();
            ps = con.prepareStatement(sql);
            ps.setInt(1, EmpID);
            rs = ps.executeQuery();
            while (rs.next()) {
                accountlList.add(new Acount());
            }
            if (accountlList.isEmpty()) {
                throw new Exception("Cannot add employee because this employee code has not been assigned to the account");
            }
            // kiem tra trung id 
            List<Staff> c1List = getStaff().stream().filter(predicate -> predicate.getSId().equals(id)).collect(Collectors.toList());
            if (!c1List.isEmpty()) {
                throw new Exception("Employee ID must be not duplicate");
            }

            p = Pattern.compile("^[A-Za-zÀ-ÖØ-öø-ÿ]+(?:[-'.\\s][A-Za-zÀ-ÖØ-öø-ÿ]+)*$");
            if (name.isEmpty()) {
                throw new Exception("Name is not null");
            }

            m = p.matcher(name);
            if (m.matches()) {
            } else {
                throw new Exception("Name is not correct format");
            }

            p = Pattern.compile("[\\w.+\\-]+@gmail\\.com");
            if (email.isEmpty()) {
                throw new Exception("Email is not null");
            }

            m = p.matcher(email);
            if (m.matches()) {
            } else {
                throw new Exception("Email format is incorrect.");
            }

            if (gender.isEmpty()) {
                throw new Exception("Gender is not null");
            }
            List<Staff> emailList = getStaff().stream().filter(predicate -> predicate.getSEmail().equals(email)).collect(Collectors.toList());
            if (!emailList.isEmpty()) {
                throw new Exception("Email must be not duplicate");
            }
            p = Pattern.compile("^0\\d{9}$");
            m = p.matcher(num);
            if (!m.matches()) {
                throw new Exception("PhoneNumber format is incorrect");
            }
            if (num.isEmpty()) {
                throw new Exception("PhoneNumber is not null");
            }
            List<Staff> c2List = getStaff().stream().filter(predicate -> predicate.getSPhone().equals(num)).collect(Collectors.toList());
            if (!c2List.isEmpty()) {
                throw new Exception("Phone Number must be not duplicate");
            }

            if (Position.isEmpty()) {
                throw new Exception("Position is not null");
            }

            p = Pattern.compile("^(Management)|(Staff)$", Pattern.CASE_INSENSITIVE);

            m = p.matcher(Position);
            if (m.matches()) {
            } else {
                throw new Exception("Position can only be Management or Staff");
            }

            p = Pattern.compile("^0\\d{11}$");
            if (CCCD.isEmpty()) {
                throw new Exception("Citizen identification number is not null");
            }

            m = p.matcher(CCCD);
            if (!m.matches()) {
                throw new Exception("Citizen identification number format is incorrect.");
            }
            List<Staff> c3List = getStaff().stream().filter(predicate -> predicate.getSCCCD().equals(CCCD)).collect(Collectors.toList());
            if (!c2List.isEmpty()) {
                throw new Exception("Citizen identification number must be not duplicate");
            }
            if (StDobAff.getValue() == null) {
                throw new Exception("Please choice date of Birth of you");
            }
            String Dob = StDobAff.getValue().toString();
            if (StDobAff.getValue().compareTo(LocalDate.now().minusYears(18)) >= 0) {
                throw new Exception("DateOfBirth must be >= 18 year old");
            }

            sql = "INSERT INTO Employee " + "VALUES("
                    + id
                    + ",'" + name + "',"
                    + "'" + CCCD + "',"
                    + "'" + Dob + "',"
                    + "'" + num + "',"
                    + "'" + gender + "',"
                    + "'" + img + "',"
                    + "'" + email + "','"
                    + Position + "')";

            System.out.println(sql);
            executeSQL(sql);

//            App.setRoot("Cusmoter");
            StackPaneStaffInterface.getChildren().filtered(predicate -> predicate.isVisible()).forEach(node -> node.setVisible(false));
            showStaffofView();
            StaffViewInterface.setVisible(true);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());

            alert.show();
            alert.setResizable(true);

        }

    }

    @FXML
    private void HandleChoiceImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            selectImageURL = selectedFile.toURI().toString();
            selectImageName = selectedFile.getName();
            //System.out.println(selectImageName);
            //copy file anh da chon ve folder duong dan anh IMAGE_DIR trong project
            from = Paths.get(selectedFile.toURI());
            to = Paths.get(IMAGE_DIR + selectImageName);

            CopyOption options = StandardCopyOption.REPLACE_EXISTING;
            try {
                Files.copy(from, to, options);
            } catch (IOException ex) {
                System.out.println("Error: cannot copy " + ex.getMessage());
            }

//            if (event.getSource() == btmSelect) {
//                StImsgeUpdate.setImage(new Image(selectImageURL));
//            } else 
            if (event.getSource() == btnImageAdd) {
                ImvAddEmployee.setImage(new Image(selectImageURL));
            }
        }
    }

    @FXML
    private void SelectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            selectImageURL = selectedFile.toURI().toString();

            selectImageName = selectedFile.getName();
            //System.out.println(selectImageName);
            //copy file anh da chon ve folder duong dan anh IMAGE_DIR trong project
            from = Paths.get(selectedFile.toURI());
            to = Paths.get(IMAGE_DIR + selectImageName);

            CopyOption options = StandardCopyOption.REPLACE_EXISTING;
            try {
                Files.copy(from, to, options);
            } catch (IOException ex) {
                System.out.println("Error: cannot copy " + ex.getMessage());
            }

            if (event.getSource() == btmChange) {
                StImsgeUpdate.setImage(new Image(selectImageURL));
            }
        }
    }

    // ===================HANDLE DETAIL STAFF =================
    public void showStaffofDetail() {
        ObservableList<Staff> lisr = getStaff();

        //show ds
        colIdStaffofDetail.setCellValueFactory(new PropertyValueFactory<>("SId"));
        colNameStaffofDetail.setCellValueFactory(new PropertyValueFactory<>("SName"));
        colImageStaffofDetail.setCellValueFactory(new PropertyValueFactory<>("SImage"));
        colEmailStaffofDetail.setCellValueFactory(new PropertyValueFactory<>("SEmail"));
        colGenderStaffofDetail.setCellValueFactory(new PropertyValueFactory<>("SGender"));
        colPhoneStaffofDetail.setCellValueFactory(new PropertyValueFactory<>("SPhone"));

        tvStaffofDetail.setItems(lisr);
    }

    @FXML
    private void OnSelectStaffOfDetail(MouseEvent event) {
        Staff p = tvStaffofDetail.getSelectionModel().getSelectedItem();

        //xuat du lieu
        StIdDetail.setText(p.getSId());
        StNameDetail.setText(p.getSName());
        StEmailDetail.setText(p.getSEmail());
        StGenderDetail.setText(p.getSGender());
        StNumDetail.setText("" + p.getSPhone());
        StDobDetail.setText("" + p.getSDob());
        StPositionDetail.setText("" + p.getSPosition());
        StCCDetail.setText(p.getSCCCD());

        ImageView img = p.getSImage(); //method getImage() cua product class
        Image image = img.getImage(); //method getImage() cua ImageView class
        StImageDetail.setImage(image);
    }

    // =========================HANDLE UPDATE STAFF ====================
    @FXML
    private void OnSelectStaffofUpdate(MouseEvent event) {
        SelectedStaff = tvStaffOfUpdate.getSelectionModel().getSelectedItem();

        //xuat du lieu
        StIdUpdate.setText(SelectedStaff.getSId());
        StNameUpdate.setText(SelectedStaff.getSName());
        StEmailUpdate.setText(SelectedStaff.getSEmail());
        StGenderUpdate.setText(SelectedStaff.getSGender());
        StNumUpdate.setText(SelectedStaff.getSPhone());
        StDobUpdate.setValue(LocalDate.parse(SelectedStaff.getSDob()));

        cbPositionOfUpdateEmployee.setText(SelectedStaff.getSPosition());

        StCCUpdate.setText(SelectedStaff.getSCCCD());

        ImageView img = SelectedStaff.getSImage(); //method getImage() cua product class
        Image image = img.getImage(); //method getImage() cua ImageView class
        StImsgeUpdate.setImage(image);

    }

    public void showStaffofUpdate() {
        ObservableList<Staff> lisr = getStaff();

        //show ds
        colIdStaffofUpdate.setCellValueFactory(new PropertyValueFactory<>("SId"));
        colNameStaffofUpdate.setCellValueFactory(new PropertyValueFactory<>("SName"));
        colImageStaffofUpdate.setCellValueFactory(new PropertyValueFactory<>("SImage"));
        colEmailStaffofUpdate.setCellValueFactory(new PropertyValueFactory<>("SEmail"));
        colGenderStaffofUpdate.setCellValueFactory(new PropertyValueFactory<>("SGender"));
        colPhoneStaffofUpdate.setCellValueFactory(new PropertyValueFactory<>("SPhone"));

        tvStaffOfUpdate.setItems(lisr);
    }

    @FXML
    private void UpdateStaff(ActionEvent event) {
        String id = StIdUpdate.getText();
        String name = StNameUpdate.getText().trim();
        String email = StEmailUpdate.getText().trim();
        String num = StNumUpdate.getText().trim();
        String gender = StGenderUpdate.getText().trim();
        String Position = cbPositionOfUpdateEmployee.getText();
        String Dob = StDobUpdate.getValue().toString();

        String CCCD = StCCUpdate.getText();

        Pattern p;
        Matcher m;
        try {

            p = Pattern.compile("^[A-Za-zÀ-ÖØ-öø-ÿ]+(?:[-'.\\s][A-Za-zÀ-ÖØ-öø-ÿ]+)*$");
            if (name.isEmpty()) {
                throw new Exception("Name is not null");
            }

//            m = p.matcher(name);
//            if (m.matches()) {
//            } else {
//                throw new Exception("Name chua dung dinh dang.");
//            }
            p = Pattern.compile("[\\w.+\\-]+@gmail\\.com");
            if (email.isEmpty()) {
                throw new Exception("Email is not null");
            }

            m = p.matcher(email);
            if (m.matches()) {
            } else {
                throw new Exception("Email is not correct format.");
            }

            List<Staff> emailList = getStaff().stream().filter(predicate -> predicate.getSEmail().equals(email) && !predicate.getSEmail().equals(SelectedStaff.getSEmail())).collect(Collectors.toList());
            if (!emailList.isEmpty()) {
                throw new Exception("Email must be not duplicate");
            }

            p = Pattern.compile("^(Male)|(Female)$", Pattern.CASE_INSENSITIVE);
            if (gender.isEmpty()) {
                throw new Exception("Gender is not null");
            }

            m = p.matcher(gender);
            if (m.matches()) {
            } else {
                throw new Exception("Gender is not correct format.");
            }

            if (StDobUpdate.getValue().compareTo(LocalDate.now().minusYears(18)) >= 0) {
                throw new Exception("DateOfBirth must be >= 18 year old");
            }
            p = Pattern.compile("^0\\d{9}$");
            if (num.isEmpty()) {
                throw new Exception("PhoneNumber is not null");
            }

            m = p.matcher(num);
            if (m.matches()) {
            } else {
                throw new Exception("PhoneNumber is not correct format.");
            }
            List<Staff> c2List = getStaff().stream().filter(predicate -> predicate.getSPhone().equals(num) && !predicate.getSPhone().equals(SelectedStaff.getSPhone())).collect(Collectors.toList());
            if (!c2List.isEmpty()) {
                throw new Exception("Phone Number must be not duplicate");
            }

            p = Pattern.compile("^0\\d{11}$");
            if (CCCD.isEmpty()) {
                throw new Exception("Citizen identification number is not null");
            }
            List<Staff> c3List = getStaff().stream().filter(predicate -> predicate.getSCCCD().equals(CCCD) && !predicate.getSCCCD().equals(SelectedStaff.getSCCCD())).collect(Collectors.toList());
            if (!c3List.isEmpty()) {
                throw new Exception("Citizen identification must be not duplicate");
            }

            m = p.matcher(CCCD);
            if (m.matches()) {
            } else {
                throw new Exception("Citizen identification number is not in the correct format (0XXXXXXXXXXX with X is digits)");
            }
            String sql = "UPDATE Employee SET "
                    + "EmpName = '" + name + "',"
                    + "SEmail='" + email + "',"
                    + "Gender='" + gender + "',"
                    + "PhoneNumber='" + num + "',"
                    + "SPosition='" + Position + "',"
                    + "CCCD='" + CCCD + "'," + "YOB='" + Dob + "',"
                    + "SImage='" + selectImageName + "'"
                    + "WHERE EmpID='" + id + "'";

            System.out.println(sql);
            executeSQL(sql);

            //load lai du lieu
            showStaffofUpdate();
            showStaffofView();
            setInfoUser();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }

    @FXML
    private void goAddStaff(ActionEvent event) {
        StackPaneStaffInterface.getChildren().filtered(predicate -> predicate.isVisible()).forEach(node -> node.setVisible(false));
        StaffAddInterface.setVisible(true);
        showStaffofView();
    }

    private void goRemoveStaff(ActionEvent event) {
        StackPaneStaffInterface.getChildren().filtered(predicate -> predicate.isVisible()).forEach(node -> node.setVisible(false));
        StaffDeleteInterface.setVisible(true);
        showStaffofDelete();
    }

    @FXML
    private void goEditStaff(ActionEvent event) {
        StackPaneStaffInterface.getChildren().filtered(predicate -> predicate.isVisible()).forEach(node -> node.setVisible(false));
        StaffUpdateInterface.setVisible(true);
        showStaffofUpdate();
    }

    @FXML
    private void goDetailStaff(ActionEvent event) {
        StackPaneStaffInterface.getChildren().filtered(predicate -> predicate.isVisible()).forEach(node -> node.setVisible(false));
        StaffDetailInterface.setVisible(true);
        showStaffofDetail();
    }

    @FXML
    private void OnSelectStaff(MouseEvent event) {

    }
    // ====================================================================================
    // ================================HANDLE ORDER INTERFACE ============================
    // ====================================================================================

    //lọc brand 
    private void filterBrandPageEP2(String item) {
        if (item == null || item.isEmpty() || item.contains("See all")) {
            tbProductPageEP2.setItems(ProductList);
            return;
        } else {
            ObservableList<ProductQ> rs = FXCollections.observableArrayList();
            for (ProductQ p : ProductList) {
                if (p.getProductBrand().contains(item)) {
                    rs.add(p);
                }
            }
            tbProductPageEP2.setItems(rs);
        }
    }

    //filter category
    private void filterCategoryPageEP2(String item) {
        if (item == null || item.isEmpty() || item.contains("See all")) {
            tbProductPageEP2.setItems(ProductList);
            return;
        } else {
            ObservableList<ProductQ> rs = FXCollections.observableArrayList();
            for (ProductQ p : ProductList) {
                if (p.getProductCategory().contains(item)) {
                    rs.add(p);
                }
            }
            tbProductPageEP2.setItems(rs);
        }
    }

    private void filterDate(LocalDate dateFrom, LocalDate dateTo) {
        List<OrdersQ> filteredList = ordersList.stream()
                .filter(o -> {
                    LocalDateTime orderDateTime = LocalDateTime.parse(o.getDatetimeOrder(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    LocalDate orderDate = orderDateTime.toLocalDate(); // Chuyển đổi thành LocalDate để chỉ lấy ngày

                    boolean isAfterOrEqualFromDate = dateFrom == null || orderDate.isEqual(dateFrom) || orderDate.isAfter(dateFrom);
                    boolean isBeforeOrEqualToDate = dateTo == null || orderDate.isEqual(dateTo) || orderDate.isBefore(dateTo);

                    return isAfterOrEqualFromDate && isBeforeOrEqualToDate;
                })
                .collect(Collectors.toList());

        ObservableList<OrdersQ> rs = FXCollections.observableArrayList(filteredList);
        tbOrderPageEP.setItems(rs);
    }

    //Search theo name ProductQ
    private void searchProduct(String txt) {
        if (txt.isEmpty()) {
            tbProductPageEP2.setItems(ProductList);
        } else {
            ObservableList<ProductQ> searchResults = FXCollections.observableArrayList();
            // Lặp qua danh sách hiện tại và thêm các mục phù hợp vào danh sách kết quả
            for (ProductQ p : ProductList) {
                if (p.getProductName().toLowerCase().contains(txt.toLowerCase())) {
                    searchResults.add(p);
                }
            }
            // Đặt lại dữ liệu cho bảng hiển thị
            tbProductPageEP2.setItems(searchResults);
        }
    }

    @FXML
    private void btnAddPageEP(ActionEvent event) {
        pageBanHang.setVisible(false);
        pageTaoPhieu.setVisible(true);
    }

    @FXML
    public void btnDetailPageEP(ActionEvent event) {
        OrdersQ selectedOrder = tbOrderPageEP.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            showAlert("Please select a bill first !");
            return;
        }

        String queryDetail = "SELECT od.OrderId, p.ProductName, NKId, ProductPrice, Quantity, CustomerName, EmpID, o.DateTimeOrder, ProductPrice * Quantity AS total, Total AS totalBill "
                + "FROM OrderDetail od "
                + "JOIN Orders o ON od.OrderId = o.OrderId "
                + "JOIN Product p ON od.ProductId = p.ProductId "
                + "JOIN Customer c ON o.CustomerPhone = c.CustomerPhone "
                + "WHERE od.orderid ='" + selectedOrder.getOrderId() + "'";

        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(queryDetail);

            // Initialize variables to store data that will be used in lambda expression
            String dateTimeOrder = selectedOrder.getDatetimeOrder();
            String cpUsed = selectedOrder.getCouponId();
            String phone = selectedOrder.getCustomerPhone();
            AtomicInteger row = new AtomicInteger(1); // AtomicInteger for mutable row value

            // Tạo dialog để hiển thị chi tiết đơn hàng
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Bill Details");
            dialog.setHeaderText(null);

            // Tạo VBox để chứa các thông tin sản phẩm
            VBox vbox = new VBox();
            vbox.setSpacing(10);
            vbox.setPadding(new Insets(20, 150, 10, 10));

            Label time = new Label("Time: " + dateTimeOrder);
            Label cPhone = new Label("Phone Number: " + (phone.equals("0") ? "" : phone));
            Label coupon = new Label("Coupon applied: " + (cpUsed == null ? "" : cpUsed));

            vbox.getChildren().addAll(time, cPhone, coupon);
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

            ObservableList<OrderDetailQ> listProduct = FXCollections.observableArrayList();
            AtomicReference<Double> totalBillPrice = new AtomicReference<>(0.0);
            // Populate the GridPane with data from ResultSet rs
            while (rs.next()) {
                String productName = rs.getString("ProductName");
                int quantity = rs.getInt("Quantity");
                double totalPrice = rs.getDouble("total");
                totalBillPrice.updateAndGet(v -> v + totalPrice);

                OrderDetailQ orderDetail = new OrderDetailQ(productName, quantity, totalPrice);
                listProduct.add(orderDetail);

                // Hiển thị thông tin sản phẩm vào GridPane
                Label productNameLabel = new Label(productName);
                Label quantityLabel = new Label(Integer.toString(quantity));
                Label totalLabel = new Label(currencyFormat.format(totalPrice));

                gridPane.add(productNameLabel, 0, row.get());
                gridPane.add(quantityLabel, 1, row.get());
                gridPane.add(totalLabel, 2, row.get());

                row.incrementAndGet(); // Increment row
            }

// Thêm GridPane vào VBox
            vbox.getChildren().add(gridPane);

// Thêm dòng kẻ ngang sau danh sách sản phẩm
            vbox.getChildren().add(new Separator(Orientation.HORIZONTAL));

// Thêm dòng hiển thị tổng hóa đơn  
            int couponValue = getCouponValue(cpUsed);
            Label couponValueLabel = new Label("Coupon value:  -" + currencyFormat.format(couponValue));
            Label totalBillLabel = new Label("Total Bill:  " + currencyFormat.format(totalBillPrice.get() - couponValue));
            totalBillLabel.setStyle("-fx-font-weight: bold");
            vbox.getChildren().addAll(couponValueLabel, totalBillLabel);

// Nếu không có sản phẩm nào được tìm thấy
            if (row.get() == 1) { // Kiểm tra số lượng sản phẩm để hiển thị thông báo phù hợp
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
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                    String formattedDateTime = dtf.format(LocalDateTime.now());

                    // Create a FileChooser
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Save Word Document");
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Word documents (*.docx)", "*.docx");
                    fileChooser.getExtensionFilters().add(extFilter);

                    String random = UUID.randomUUID().toString().substring(0, 8); // Lấy 8 ký tự đầu của UUID
                    fileChooser.setInitialFileName("BillDetail_" + random);

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
                        infoRun.setText("Create time: " + formattedDateTime);
                        infoRun.addBreak();
                        infoRun.setText("Order Time: " + dateTimeOrder);
                        infoRun.addBreak();
                        infoRun.setText("Customer Phone: " + (phone.equals("0") ? "None" : phone));
                        infoRun.addBreak();
                        infoRun.setText("Coupon ID applied: " + (cpUsed == null ? "None" : cpUsed.toUpperCase().trim()));

                        // Create a table without borders
                        XWPFTable table = document.createTable(row.get(), 3); // Số hàng của bảng được tăng lên row + 1 (bao gồm cả dòng tiêu đề)

                        // Set column widths
                        table.setWidth("100%");
                        table.getRow(0).getCell(0).setText("Product Name");
                        table.getRow(0).getCell(1).setText("Quantity");
                        table.getRow(0).getCell(2).setText("Total Price");

                        // Format for displaying numbers with commas
                        DecimalFormat decimalFormat = new DecimalFormat("#,##0");

                        // Populate the table with data from listProduct
                        int rowIndex = 1;
                        for (OrderDetailQ od : listProduct) {
                            table.getRow(rowIndex).getCell(0).setText(od.getProductName());
                            table.getRow(rowIndex).getCell(1).setText(String.valueOf(od.getSoldQuantity()));
                            table.getRow(rowIndex).getCell(2).setText(decimalFormat.format(od.getTotalPrice()));
                            rowIndex++;
                        }

                        // Add line separator after table
                        XWPFParagraph discountedPriceParagraph = document.createParagraph();
                        discountedPriceParagraph.setAlignment(ParagraphAlignment.RIGHT);
                        XWPFRun runDiscountedPrice = discountedPriceParagraph.createRun();
                        runDiscountedPrice.setText("Coupon Value:           -" + decimalFormat.format(couponValue) + " VND");

                        // Add total bill paragraph
                        XWPFParagraph totalBillParagraph = document.createParagraph();
                        totalBillParagraph.setAlignment(ParagraphAlignment.RIGHT);
                        XWPFRun runTotalBill = totalBillParagraph.createRun();
                        String totalBillText = "Total Bill:                    " + decimalFormat.format(totalBillPrice.get() - couponValue) + " VND";
                        runTotalBill.setText(totalBillText);
                        runTotalBill.setBold(true);

                        // Write the Document in file system
                        try (FileOutputStream out = new FileOutputStream(file)) {
                            document.write(out);
                            showAlert("Export bill details successfully!");
                            Desktop.getDesktop().open(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                document.close();
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());;
                            }
                        }
                    } else {
                        System.out.println("Save operation cancelled.");
                    }
                }
                return null;
            });

            dialog.showAndWait();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());;
        }
    }

    @FXML
    private void btnExportExcelPageEP(ActionEvent event) {
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
            for (int i = 0; i < tbOrderPageEP.getItems().size(); i++) {
                Row row = sheet.createRow(i + 1);
                OrdersQ o = tbOrderPageEP.getItems().get(i);

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

    //method thêm sản phẩm vào cột thanh toán 
    private void addProductToList(ProductQ product) {

        if (selectedProducts.contains(product)) {
            return;
        }

        txtEmptyPageEP2.setVisible(false);

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
                listComboBox.add("Batch " + warehouseDetail + "  (Quantity: " + quantity + " )");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());;
        }

        ComboBox<String> comboBox = new ComboBox<>(listComboBox);
        if (!listComboBox.isEmpty()) {
            comboBox.setValue("Please choose batch");
        }
        comboBox.setStyle("-fx-background-color:white;-fx-border-color:green;");

        TextField quantityField = new TextField("1");
        HBox quantityBox = new HBox();
        quantityBox.setVisible(false);
        quantityBox.setSpacing(5);

        // Xử lý khi chọn comboBox lô hàng
        comboBox.setOnAction(event -> {
            batchSelectedMap.put(product, true); // Cập nhật khi lô hàng được chọn cho sản phẩm này

            System.out.println(batchSelectedMap.get(product));

            quantityField.setText("1");
            String selectedItemId = comboBox.getValue();
            if (selectedItemId != null && !selectedItemId.isEmpty()) {
                String[] parts = selectedItemId.split(" ");
                String nkid = parts[1]; // Lấy mã lô hàng từ phần tử đã chọn trong comboBox
                //SELECT ProductName, nkid, ProductPrice,  ImportQuantity-soldQuantity as quantity FROM ProductDetail pd join ProductQ p on pd.ProductId= p.ProductId WHERE p.ProductId = 'P001' and NKId=1
                String query1 = "SELECT productname,nkid, ProductPrice,  ImportQuantity-soldQuantity as quantity FROM ProductDetail pd join product p on pd.ProductId= p.ProductId WHERE p.ProductId = '" + product.getProductID() + "' and nkid = '" + nkid + "'";
                try (Statement statement = con.createStatement(); ResultSet rs5 = statement.executeQuery(query1)) {
                    if (rs5.next()) {
                        String pname = rs5.getString("productname");
                        int quantityTon = rs5.getInt("quantity"); // Lấy số lượng nhập về
                        product.setSelectedQuantity(Integer.parseInt(quantityField.getText()));
                        product.setTonkhoQuantity(quantityTon);// Cập nhật số lượng tồn kho của sản phẩm
                        int price = rs5.getInt("ProductPrice"); // Lấy giá sản phẩm
                        product.setProductPrice(price); // Cập nhật giá sản phẩm

                        // Hiển thị thông tin giá sản phẩm và số lượng bán được
                        productPrice.setVisible(true);
                        String formattedPrice = String.format("%,d", price);
                        productPrice.setText("Total: " + formattedPrice + " VND");
                        quantityBox.setVisible(true);

                        // Cập nhật danh sách chi tiết đơn hàng
                        orderDetailListGlobal.removeIf(od -> od.getProductID().equals(product.getProductID())); // Xóa OrderDetailQ cũ nếu có
                        orderDetailListGlobal.add(new OrderDetailQ(product.getProductID(), pname, Integer.parseInt(nkid), 1, price));
                        updateProductPrice(quantityField, productPrice, product);
                        updateTotalPrice();
                        System.out.println(selectedProducts);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());;
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
                product.setSelectedQuantity(Integer.parseInt(quantityField.getText()));
                updateProductPrice(quantityField, productPrice, product);
                updateTotalPrice();
                System.out.println(selectedProducts);
                // Cập nhật số lượng trong danh sách chi tiết đơn hàng
                orderDetailListGlobal.stream()
                        .filter(od -> od.getProductID().equals(product.getProductID()))
                        .forEach(od -> od.setSoldQuantity(currentQuantity - 1));

            } else {
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirm Alert");
                confirmAlert.setHeaderText(null);
                confirmAlert.setContentText("Are you sure you want to delete this product from the list?");
                Optional<ButtonType> result = confirmAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    listdachonPageEP2.getChildren().remove(newPane);
                    selectedProducts.remove(product);
                    batchSelectedMap.remove(product); // Xóa trạng thái chọn lô hàng khi sản phẩm bị xóa
                    updateTotalPrice();
                    System.out.println(selectedProducts);

                    orderDetailListGlobal.removeIf(od -> od.getProductID().equals(product.getProductID()));
                    if (selectedProducts.isEmpty()) {
                        txtEmptyPageEP2.setVisible(true);
                    }
                }
            }
        });

        // Icon +
        Button increaseButton = new Button("+");
        increaseButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        increaseButton.setOnAction(event -> {
            int currentQuantity = Integer.parseInt(quantityField.getText());
            if (currentQuantity < product.getTonkhoQuantity()) {
                quantityField.setText(String.valueOf(currentQuantity + 1));
                product.setSelectedQuantity(Integer.parseInt(quantityField.getText()));
                updateProductPrice(quantityField, productPrice, product);
                updateTotalPrice();
                System.out.println(selectedProducts);

                // Cập nhật số lượng trong danh sách chi tiết đơn hàng
                orderDetailListGlobal.stream()
                        .filter(od -> od.getProductID().equals(product.getProductID()))
                        .forEach(od -> od.setSoldQuantity(currentQuantity + 1));
            }
        });

        quantityBox.getChildren().addAll(decreaseButton, quantityField, increaseButton);

        VBox vbox = new VBox(productName, comboBox, quantityBox, productPrice);
        vbox.setSpacing(10);

        HBox hbox = new HBox(productImage, vbox);
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10));
        newPane.getChildren().add(hbox);

        listdachonPageEP2.getChildren().add(newPane);

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
        productPrice.setText("Total: " + formattedPrice + " VND");
    }

    private void updateTotalPrice() {
        int total = 0;

        for (Node node : listdachonPageEP2.getChildren()) {
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

        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        String formattedTotal = formatter.format(total);
        totalBillPageEP2.setText(formattedTotal + " VND");

        //gọi lại method totalBill
        totalBill();
    }

    private int getCouponValue(String couponCode) {
        int couponValue = 0;
        try {
            String query = "SELECT value FROM Coupon WHERE couponid = ?  ";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, couponCode);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                couponValue = resultSet.getInt("value");
            }
            statement.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return couponValue;
    }

    @FXML
    private void btnCheckCouponPageEP2(ActionEvent event) {
        String couponCode = txtCouponPageEP2.getText().trim();
        // Kiểm tra xem mã phiếu mua hàng có được nhập vào không
        if (couponCode.isEmpty()) {
            showAlert("Please enter the coupon code.");
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
                    alert.setTitle("Alert");
                    alert.setHeaderText(null);
                    alert.setContentText("The couponr has been used.");
                    txtCouponPageEP2.clear();
                    alert.showAndWait();
                    return;
                } else if (expiryLocalDate != null && expiryLocalDate.isBefore(currentDate)) {
                    // Kiểm tra xem expiryDate đã hết hạn chưa
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Alert");
                    alert.setHeaderText(null);
                    alert.setContentText("The coupon has expired.");
                    txtCouponPageEP2.clear();
                    alert.showAndWait();
                    return;
                } else {
                    // Tính tổng đơn hàng
                    int totalBill = totalBill();

                    // Kiểm tra tổng đơn hàng có lớn hơn hoặc bằng 200,000 không
                    if (totalBill < 200000) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Alert");
                        alert.setHeaderText(null);
                        alert.setContentText("The shopping voucher is only applicable to orders of 200,000 VND or more.");
                        alert.showAndWait();
                        txtCouponPageEP2.clear();
                        return;
                    }

                    // Nếu không có vấn đề gì, hiển thị thông báo áp dụng thành công
                    // Thực hiện cập nhật tổng bill và đổi trạng thái phiếu -> true
                    NumberFormat formatter = NumberFormat.getInstance(Locale.US);
                    String formattedTotal = formatter.format(totalBill - getCouponValue(couponCode));
                    totalBillPageEP2.setText(formattedTotal + " VND");
                    couponUsed = couponCode;
                    try {
                        String queryUpdate = "UPDATE Coupon SET status = 'true' WHERE couponid = ?";
                        PreparedStatement statement = con.prepareStatement(queryUpdate);
                        statement.setString(1, couponCode);
                        statement.executeUpdate();
                        statement.close();

                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }

                    // Chỉ hiển thị thông báo áp dụng thành công nếu phiếu mua hàng chưa được áp dụng
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Alert");
                    alert.setHeaderText(null);
                    alert.setContentText("Applied successfully");
                    listdachonPageEP2.setDisable(true);
                    alert.showAndWait();
                    txtCouponPageEP2.clear(); // Reset txtCoupon
                    //cho ẩn các chức năng có thể làm ảnh hưởng tới đơn hàng sau khi ấn Apply
                    btnDeleteAllPageEP2.setDisable(true);
                    txtCouponPageEP2.setDisable(true);
                    btnApplyCouponPageEP2.setDisable(true);
                    tbProductPageEP2.setDisable(true);
                }
            } else {
                // Nếu không tìm thấy phiếu mua hàng
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("The coupon does not exist.");
                alert.showAndWait();
                txtCouponPageEP2.clear();

            }
            stm4.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //method làm mới cột thanh toán
    @FXML
    private void btnDeleteAllPageEP2(MouseEvent event) {
        if (selectedProducts.isEmpty()) {
            showAlert("No products available !");
            return;
        }
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Alert");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete all products?");
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            listdachonPageEP2.getChildren().clear();
            listdachonPageEP2.setDisable(false);
            selectedProducts.clear();
            orderDetailListGlobal.clear();
            batchSelectedMap.clear();
            totalBillPageEP2.setText("0 VND");
            txtEmptyPageEP2.setVisible(true);
            initialize(null, null);
            System.out.println(selectedProducts);
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
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

    //method lấy tổng tiền
    public int totalBill() {
        int totalBill = Integer.parseInt(totalBillPageEP2.getText().replace("VND", "").replace(",", "").trim());
        return totalBill;
    }

    //Method lay ten KH da chon listCustomer
    public String getCustomerPhone() {
        String customerPhone = "";
        String selectedCustomerName = listCustomerPageEP2.getValue();
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
                System.out.println(ex.getMessage());
            }
        }

        return customerPhone;
    }

    @FXML
    private void btnThanhToanPageEP2(ActionEvent event) throws IOException {
        // Kiểm tra nếu chưa chọn sản phẩm nào
        if (selectedProducts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product before proceeding to payment.");
            alert.showAndWait();
            return;
        }

        // Kiểm tra nếu chưa chọn lô hàng cho tất cả các sản phẩm
        for (Boolean isBatchSelected : batchSelectedMap.values()) {
            if (!isBatchSelected) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Please select a batch for all products before proceeding with payment.");
                alert.showAndWait();
                return;
            }
        }

        // Kiểm tra nếu chưa chọn khách hàng
        String selectedCus = listCustomerPageEP2.getValue();

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Do you want to pay with the selected products?");
        Optional<ButtonType> confirmResult = confirmAlert.showAndWait();
        if (confirmResult.isEmpty() || confirmResult.get() != ButtonType.OK) {
            return;
        }

        // 1. Thêm mới thông tin phiếu xuất vào bảng order
        String CusName = "";
        String customerPhone = getCustomerPhone();
        if (selectedCus == null) {
            customerPhone = "0";
        }
        LocalDateTime dateTimeOrder = LocalDateTime.now();
        int status = 1;

        int empid = getLoginUser().getEmpID();//ma nhan vien

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

                showAlert("Payment success");
                // Lấy ID đơn hàng mới tạo
                try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int orderId = ordID = generatedKeys.getInt(1);

                        // 2. Thêm chi tiết đơn hàng vào bảng orderdetail và cập nhật số lượng đã bán trong bảng productdetail
                        for (OrderDetailQ od : orderDetailListGlobal) {
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
                                            showAlert("Points of " + cName + " is " + cusPoint + " (Total: " + point + ")");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Xác nhận in hóa đơn 
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to print the bill details ?");

                // Show the alert and wait for the user's response
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    createBillDetails(CusName, customerPhone, ordID, couponUsed);
                }

                // Reset dữ liệu sau khi thanh toán
                selectedProducts.clear();
                orderDetailListGlobal.clear();
                listdachonPageEP2.getChildren().clear();
                listdachonPageEP2.setDisable(false);
                listCustomerPageEP2.getSelectionModel().clearSelection();
                txtEmptyPageEP2.setVisible(true);
                listCustomerPageEP2.setPromptText("Choose customers");
                btnDeleteAllPageEP2.setDisable(false);
                txtCouponPageEP2.setDisable(false);
                btnApplyCouponPageEP2.setDisable(false);
                tbProductPageEP2.setDisable(false);
                btnBackPageEP2(null);
                updateTotalPrice();
                couponUsed = null;
            } else {
                showAlert("Thanh toán thất bại");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btnBackPageEP2(MouseEvent event) {
        pageBanHang.setVisible(true);
        pageTaoPhieu.setVisible(false);
        initialize(null, null);
    }

    @FXML
    private void btnReloadPageEP(ActionEvent event) {
        initialize(null, null);
        dateFromPageEP.setValue(null);
        dateToPageEP.setValue(null);
        showAlert("Reload successfully !");
    }

    //method xử lý khi kéo filter Price slider
    private void filterOrderByPrice(Double price) {
        if (price == 0 || price == null) {

            tbOrderPageEP.setItems(ordersList);
        } else {
            // Tạo một danh sách mới để lưu các mục tìm kiếm phù hợp
            ObservableList<OrdersQ> result = FXCollections.observableArrayList();

            // Lặp qua danh sách hiện tại và thêm các mục phù hợp vào danh sách kết quả
            for (OrdersQ o : ordersList) {
                if (o.getTotal() > 0 && o.getTotal() <= price) {
                    result.add(o);
                }
            }
            // Đặt lại dữ liệu cho bảng hiển thị
            tbOrderPageEP.setItems(result);
        }

    }

    private void filterCBCustomer(String item) {
        if (item == null || item.toLowerCase().contains("see all")) {
            tbOrderPageEP.setItems(ordersList);
            return;
        }

        try {
            String queryCustomer = "SELECT customerphone FROM customer WHERE customername = ?";
            PreparedStatement stm = con.prepareStatement(queryCustomer);
            stm.setString(1, item); // Thiết lập tham số cho câu truy vấn

            ResultSet rs = stm.executeQuery();

            ObservableList<OrdersQ> result = FXCollections.observableArrayList(); // Sử dụng ObservableList<Customer> thay vì OrdersQ

            while (rs.next()) {
                String customerPhone = rs.getString("customerphone");
                for (OrdersQ o : ordersList) {
                    if (o.getCustomerPhone().equals(customerPhone)) {
                        result.add(o);
                    }
                }

            }

            tbOrderPageEP.setItems(result); // Đặt lại dữ liệu của tbOrder

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());;
            // Xử lý ngoại lệ SQL
        }
    }

    private void filterCBEmployee(String item) {
        if (item == null || item.toLowerCase().contains("see all")) {
            tbOrderPageEP.setItems(ordersList);
            return;
        }

        try {
            String queryEmp = "SELECT empid FROM employee WHERE empname = ?";

            PreparedStatement stm = con.prepareStatement(queryEmp);
            stm.setString(1, item); // Thiết lập tham số cho câu truy vấn

            ResultSet rs = stm.executeQuery();

            ObservableList<OrdersQ> result = FXCollections.observableArrayList();

            // Lặp qua các dòng trong ResultSet
            while (rs.next()) {
                String empid = rs.getString("empid");

                // Lọc danh sách OrdersQ dựa trên empid
                for (OrdersQ o : ordersList) {
                    if (o.getEmpId().equals(empid)) {
                        result.add(o);
                    }
                }
            }

            tbOrderPageEP.setItems(result); // Đặt lại dữ liệu của tbOrder

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());;
        }
    }

    private double getPrice(ObservableList<OrderDetailQ> odl) {
        double totalPrice = 0;
        for (OrderDetailQ od : odl) {
            totalPrice += od.getTotalPrice();
        }
        return totalPrice;
    }

    public void createBillDetails(String name, String phone, int oid, String coupon) throws IOException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        String formattedDateTime = dtf.format(LocalDateTime.now());

        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Word Document");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Word documents (*.docx)", "*.docx");
        fileChooser.getExtensionFilters().add(extFilter);

        String random = UUID.randomUUID().toString().substring(0, 8); // Lấy 8 ký tự đầu của UUID
        fileChooser.setInitialFileName("BillDetails_" + random);

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
            infoRun.addBreak();
            if (coupon == null) {
                coupon = "";
            }
            infoRun.setText("Coupon applied: " + coupon.toUpperCase().trim());

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
            for (OrderDetailQ od : orderDetailListGlobal) {
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

            XWPFParagraph discountedPriceParagraph = document.createParagraph();
            discountedPriceParagraph.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun runDiscountedPrice = discountedPriceParagraph.createRun();
            runDiscountedPrice.setText("Coupon Value:           -" + decimalFormat.format(getCouponValue(coupon)) + " VND");

            // Add total bill paragraph
            XWPFParagraph totalBillParagraph = document.createParagraph();
            totalBillParagraph.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun runTotalBill = totalBillParagraph.createRun();
            runTotalBill.setText("Total Bill:                    " + decimalFormat.format(totalBill - getCouponValue(coupon)) + " VND");
            runTotalBill.setBold(true);

            // Write the Document in file system
            try (FileOutputStream out = new FileOutputStream(file)) {
                document.write(out);
                showAlert("Export bill details successfully!");
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                document.close();
            }
        } else {
            System.out.println("Save operation cancelled.");
        }
    }

    @FXML
    private void openOrderInterface(ActionEvent event) {
        StackPaneMainPage.getChildren().filtered(predicate -> predicate.isVisible()).forEach(node -> node.setVisible(false));
        OrderInterface.setVisible(true);
    }

    //===========================================================
    //===========================HOME INTERFACE =================
    //===========================================================
    @FXML
    private void handleHome(MouseEvent event) {
        StackPaneMainPage.getChildren().filtered(predicate -> predicate.isVisible()).forEach(node -> node.setVisible(false));
        HomeInterface.setVisible(true);
    }
    //=================================================================================
    //================================HANDLE COUPON INTERFACE =========================
    //=================================================================================

    @FXML
    // Xử lý sự kiện click vào nút Add
    private void btnAddPageCoupon(ActionEvent event) {
        // Tạo dialog để nhập thông tin CouponQ
        Dialog<CouponQ> dialog = new Dialog<>();
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

        datePicker.setValue(LocalDate.now().plusMonths(1));

// Ngăn không cho chọn những ngày trước ngày hiện tại
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()));
            }
        });
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
        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.addEventFilter(ActionEvent.ACTION, ae -> {
            String couponID = txtCouponID.getText().trim().toUpperCase();
            String valueText = txtValue.getText().trim();

            // Kiểm tra hợp lệ
            if (couponID.isEmpty() || valueText.isEmpty()) {
                showAlert("Please fill in all fields.");
                ae.consume(); // Giữ dialog mở
                return;
            }

            int value;
            try {
                value = Integer.parseInt(valueText);
                if (value <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                showAlert("Value must be a positive integer.");
                ae.consume(); // Giữ dialog mở
                return;
            }

            if (CouponList.stream().anyMatch(c -> c.getCouponid().equals(couponID))) {
                showAlert("Coupon already exists. Please try again!");
                ae.consume(); // Giữ dialog mở
            }
        });

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                String couponID = txtCouponID.getText().trim().toUpperCase();
                int value = Integer.parseInt(txtValue.getText().trim());
                LocalDate expiryDate = datePicker.getValue();
                int status = 0; // Mặc định là "Ready"

                // Tạo một đối tượng CouponQ mới và trả về
                return new CouponQ(couponID, value, expiryDate.toString(), status);
            }
            return null;
        });

        // Hiển thị dialog và xử lý kết quả
        dialog.showAndWait().ifPresent(coupon -> {
            // Nếu coupon không null, thêm vào danh sách và cập nhật TableView
            if (coupon != null) {
                // Thêm vào db

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
                        initialize(null, null); // Hàm này không được cung cấp trong đoạn mã của bạn, nhưng giả sử nó cập nhật TableView
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
    private void btnExportExcelPageCoupon(ActionEvent event) {
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
            for (int i = 0; i < tbCouponPageCoupon.getItems().size(); i++) {
                Row row = sheet.createRow(i + 1);
                CouponQ coupon = tbCouponPageCoupon.getItems().get(i);

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
    private void btnDeletePageCoupon(ActionEvent event) {
        // Lấy coupon được chọn từ TableView
        CouponQ selectedCoupon = tbCouponPageCoupon.getSelectionModel().getSelectedItem();

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

            // Câu lệnh SQL để xóa nếu trạng thái là false
            String sql = "DELETE FROM coupon WHERE couponid = ? AND status = 'false'";

            try {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, selectedCoupon.getCouponid());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    // Xóa khỏi danh sách hiện tại và cập nhật TableView
                    CouponList.remove(selectedCoupon);
                    tbCouponPageCoupon.refresh();
                    showAlert("Coupon deleted successfully!");
                    initialize(null, null);
                } else {
                    showAlert("Cannot delete Coupon because this Coupon is used ");
                }
            } catch (SQLException ex) {
                showAlert("Error occurred while deleting coupon: " + ex.getMessage());
            } finally {
                try {
                    if (con != null && !con.isClosed()) {
                        con.close();
                    }
                } catch (SQLException ex) {
                    showAlert("Error occurred while closing the connection: " + ex.getMessage());
                }
            }
        }
    }

    @FXML
    private void btnResetPageCoupon(ActionEvent event
    ) {
        initialize(null, null);
        showAlert("Reload successfully !");
    }

    //handle search 
    private void handleSearch() {
        String searchText = txtSearchPageCoupon.getText().trim().toLowerCase();

        if (searchText.isEmpty()) {

            tbCouponPageCoupon.setItems(CouponList);
        } else {
            // Tạo một danh sách mới để lưu các mục tìm kiếm phù hợp
            ObservableList<CouponQ> searchResults = FXCollections.observableArrayList();

            // Lặp qua danh sách hiện tại và thêm các mục phù hợp vào danh sách kết quả
            for (CouponQ c : CouponList) {
                if (c.getCouponid().toLowerCase().contains(searchText)) {
                    searchResults.add(c);
                }
            }
            // Đặt lại dữ liệu cho bảng hiển thị
            tbCouponPageCoupon.setItems(searchResults);
        }
    }

    //handle select comboBox Filter CouponQ
    private void handleCBFilter() {
        String selectedItem = cbFilterPageCoupon.getValue();

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

                    ObservableList<CouponQ> couponListAll = FXCollections.observableArrayList();

                    while (rs.next()) {
                        String couponID = rs.getString("couponid");
                        int value = rs.getInt("value");
                        String expiryDate = rs.getString("expirydate");
                        int status = rs.getInt("status");

                        couponListAll.add(new CouponQ(couponID, value, expiryDate, status));
                    }

                    tbCouponPageCoupon.setItems(couponListAll);

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                break;

            case "Coupon unexpired":
                // Lọc và hiển thị các coupon còn hạn
                LocalDate currentDate = LocalDate.now();
                String sqlExpired = "SELECT * FROM coupon WHERE expirydate > ?";

                try (PreparedStatement pstmt = con.prepareStatement(sqlExpired)) {
                    pstmt.setDate(1, java.sql.Date.valueOf(currentDate));
                    rs = pstmt.executeQuery();

                    ObservableList<CouponQ> expiredCoupons = FXCollections.observableArrayList();

                    while (rs.next()) {
                        String couponID = rs.getString("couponid");
                        int value = rs.getInt("value");
                        String expiryDate = rs.getString("expirydate");
                        int status = rs.getInt("status");

                        expiredCoupons.add(new CouponQ(couponID, value, expiryDate, status));
                    }

                    tbCouponPageCoupon.setItems(expiredCoupons);

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                break;

            case "Coupon ready":
                // Lọc và hiển thị các coupon sẵn sàng sử dụng
                String sqlReady = "SELECT * FROM coupon where status = 'false'";
                try {
                    stm = con.createStatement();
                    rs = stm.executeQuery(sqlReady);

                    ObservableList<CouponQ> couponListReady = FXCollections.observableArrayList();

                    while (rs.next()) {
                        String couponID = rs.getString("couponid");
                        int value = rs.getInt("value");
                        String expiryDate = rs.getString("expirydate");
                        int status = rs.getInt("status");

                        couponListReady.add(new CouponQ(couponID, value, expiryDate, status));
                    }

                    tbCouponPageCoupon.setItems(couponListReady);

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                break;

            default:
                showAlert("Unknown filter selected!");
                break;
        }
    }

    @FXML
    private void btnDetailAndPrintPageCoupon(ActionEvent event) {
        // Lấy coupon được chọn từ TableView
        CouponQ selectedCoupon = tbCouponPageCoupon.getSelectionModel().getSelectedItem();

        if (selectedCoupon == null) {
            showAlert("Please select a coupon to view details.");
            return;
        }

        // Tạo dialog để hiển thị chi tiết CouponQ và in
        Dialog<CouponQ> dialog = new Dialog<>();
        dialog.setTitle("Detail Coupon");
        dialog.getDialogPane().setMinWidth(500);

        // Thiết lập nút (các) bấm
        ButtonType printButtonType = new ButtonType("Print Coupon", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(printButtonType, ButtonType.CANCEL);

        // Tạo một grid pane để sắp xếp các thành phần nhập liệu
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Controls cho nhập liệu
        Label lblCouponID = new Label("Coupon ID:");
        Label lblValue = new Label("Value:");
        Label lblExpiryDate = new Label("Expiry Date:");
        Label lblStatus = new Label("Status:");
        // Thêm thông tin thời gian tạo
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Label txtCouponID = new Label(selectedCoupon.getCouponid());
        Label txtValue = new Label(String.format("%,d", selectedCoupon.getValue()) + "đ");
        Label txtExpiryDate = new Label(selectedCoupon.getExpirydate());
        Label txtStatus = new Label(selectedCoupon.getStatusText());

        // Đưa các control vào grid pane
        grid.add(lblCouponID, 0, 1);
        grid.add(txtCouponID, 1, 1);
        grid.add(lblValue, 0, 2);
        grid.add(txtValue, 1, 2);
        grid.add(lblExpiryDate, 0, 3);
        grid.add(txtExpiryDate, 1, 3);
        grid.add(txtStatus, 1, 4);
        grid.add(lblStatus, 0, 4);
        dialog.getDialogPane().setContent(grid);

        // Xử lý sự kiện khi người dùng nhấn Print CouponQ
        dialog.setResultConverter(buttonType -> {
            if (buttonType == printButtonType) {
                if (selectedCoupon.getStatus() == 1) {
                    Platform.runLater(() -> showAlert("Cannot print because this coupon has been used. Please try again !"));
                    return null;
                }
                // Tạo một tên file ngẫu nhiên bằng UUID
                String randomString = UUID.randomUUID().toString().substring(0, 8); // Lấy 8 ký tự đầu của UUID
                String initialFileName = "Coupon_" + randomString + ".docx";

                // Tạo FileChooser để lưu file
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Word Document");
                fileChooser.setInitialFileName(initialFileName);
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Word documents (*.docx)", "*.docx");
                fileChooser.getExtensionFilters().add(extFilter);

                // Hiển thị save dialog
                Stage tempStage = new Stage();
                File file = fileChooser.showSaveDialog(tempStage);

                if (file != null) {
                    try {
                        // Tạo đối tượng XWPFDocument để lưu trữ nội dung của tài liệu Word
                        XWPFDocument document = new XWPFDocument();

                        // Tạo tiêu đề
                        XWPFParagraph titleParagraph = document.createParagraph();
                        titleParagraph.setAlignment(ParagraphAlignment.CENTER);
                        XWPFRun titleRun = titleParagraph.createRun();
                        titleRun.setText("Coupon Details");
                        titleRun.setColor("0000FF"); // Màu chữ xanh
                        titleRun.setFontSize(20); // Cỡ chữ
                        titleRun.setBold(true); // Chữ đậm

                        // Thêm thông tin thời gian tạo
                        XWPFParagraph createTimeParagraph = document.createParagraph();
                        XWPFRun createTimeRun = createTimeParagraph.createRun();
                        createTimeRun.setText("Create time: " + createTime);

                        // Thêm dòng gạch ngang phân tách
                        XWPFParagraph lineParagraph = document.createParagraph();
                        XWPFRun lineRun = lineParagraph.createRun();
                        lineRun.setText("------------------------------------------------------------------------------------------------------------------------------------------");

                        // Thêm thông tin chi tiết vào tài liệu
                        // Thêm thông tin chi tiết vào tài liệu
                        XWPFParagraph infoParagraph = document.createParagraph();

                        XWPFRun couponIdRun = infoParagraph.createRun();
                        couponIdRun.setText("Coupon ID: " + selectedCoupon.getCouponid());
                        couponIdRun.setBold(true); // Đặt thuộc tính đậm
                        couponIdRun.setFontSize(15); // Đặt kích thước chữ là 14

// Thêm dòng mới
                        infoParagraph.createRun().addBreak();

// Thêm thông tin CouponQ Value
                        XWPFRun couponValueRun = infoParagraph.createRun();
                        couponValueRun.setText("Coupon Value: " + String.format("%,d", selectedCoupon.getValue()) + "đ");
                        couponValueRun.setFontSize(15); // Đặt kích thước chữ là 14

// Thêm dòng mới
                        infoParagraph.createRun().addBreak();

// Thêm thông tin Expiry Date
                        XWPFRun expiryDateRun = infoParagraph.createRun();
                        expiryDateRun.setText("Expiry Date: " + selectedCoupon.getExpirydate());
                        expiryDateRun.setFontSize(15); // Đặt kích thước chữ là 14

                        // Thêm dòng gạch ngang phân tách
                        XWPFParagraph lineParagraph2 = document.createParagraph();
                        XWPFRun lineRun2 = lineParagraph2.createRun();
                        lineRun2.setText("------------------------------------------------------------------------------------------------------------------------------------------");

                        // Thêm dòng "See you again"
                        XWPFParagraph seeYouParagraph = document.createParagraph();
                        XWPFRun seeYouRun = seeYouParagraph.createRun();
                        seeYouRun.setText("See you again.");
                        seeYouRun.setBold(true);

                        // Lưu tài liệu vào file
                        try (FileOutputStream out = new FileOutputStream(file)) {
                            document.write(out);
                            showAlert("Coupon details exported successfully!");
                            Desktop.getDesktop().open(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                            showAlert("Error exporting coupon details: " + e.getMessage());
                        } finally {
                            document.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        showAlert("Error creating Word document: " + e.getMessage());
                    }
                } else {
                    System.out.println("Save operation cancelled.");
                }
            }
            return null; // Trả về null vì không cần trả về đối tượng CouponQ khi xem chi tiết
        });

        // Hiển thị dialog và chờ đợi người dùng đóng hoặc nhấn Print CouponQ hoặc Cancel
        dialog.showAndWait();
    }

    @FXML
    private void openCouponInterface(ActionEvent event) {
        StackPaneMainPage.getChildren().filtered(predicate -> predicate.isVisible()).forEach(node -> node.setVisible(false));
        pageCoupon.setVisible(true);
    }

    @FXML
    private void HandleLogOut(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure to LogOut");
        alert.show();
        alert.setResultConverter(m -> {
            if (m == ButtonType.OK) {
                try {
                    System.out.println("Chuyen ve trang dang nhap");
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("Login.fxml"));
                    Parent root = loader.load();
                    Scene scene2 = new Scene(root);
//                    MainController controller = loader.getController();
//                    controller.setLoginUser(InfoUser);
//                    controller.setInfoUser();

//            System.out.println(controller.getLoginUser());
                    stage.setScene(scene2);
                    stage.centerOnScreen();
                } catch (IOException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            return null;

        });

    }

    //=================================================================================
    //====================================HANDLE WAREHOUSE INTERFACE ==================
    //==================================================================================
    public void handleSearchSupplier(String txt) {
        if (txt.isEmpty()) {
            tvWareHouseReceipt.setItems(WHRListPageNHI);
        } else {
            ObservableList<WareHouse> searchResults = FXCollections.observableArrayList();
            // Lặp qua danh sách hiện tại và thêm các mục phù hợp vào danh sách kết quả
            for (WareHouse w : WHRListPageNHI) {
                if (w.getSupplierName().toLowerCase().contains(txt.toLowerCase())) {
                    searchResults.add(w);
                }
            }
            // Đặt lại dữ liệu cho bảng hiển thị
            tvWareHouseReceipt.setItems(searchResults);
        }
    }

    public void getDataWHR() {
        String sql = "select nkid, EmpName,SuppilerName,WareHouseDateTime,Total from WareHouseReceipt w join Employee e on w.CreatorWareHouse = e.EmpID join Suppiler s on w.SupplierCode = s.SuppilerCode";
        try {
            // Tạo statement
            stm = con.createStatement();
            // Truy vấn
            rs = stm.executeQuery(sql);

            // Tạo ObservableList để lưu dữ liệu từ ResultSet
            WHRListPageNHI = FXCollections.observableArrayList();

            WHRListPageNHI.clear();

            while (rs.next()) {
                int Nkid = rs.getInt("nkid");
                String empName = rs.getString("empname");
                String suppilerName = rs.getString("SuppilerName");
                String whDateTime = rs.getString("WareHouseDateTime");
                float total = rs.getFloat("total");
                WHRListPageNHI.add(new WareHouse(Nkid, whDateTime, suppilerName, empName, total));

            }
            tvWareHouseReceipt.setItems(WHRListPageNHI);

            // Thiết lập giá trị cho các cột
            colSttPageWH.setCellValueFactory(data -> {
                WareHouse w = data.getValue();
                int rowIndex = tvWareHouseReceipt.getItems().indexOf(w) + 1;
                return new SimpleIntegerProperty(rowIndex);
            });

            colWRIPageWH.setCellValueFactory(new PropertyValueFactory<>("Nkid"));
            colDateTimePageWH.setCellValueFactory(new PropertyValueFactory<>("WHDateTime"));
            colSupplierNamePageWH.setCellValueFactory(new PropertyValueFactory<>("SupplierName"));
            colEmployeeNamePageWH.setCellValueFactory(new PropertyValueFactory<>("EmployeeName"));
            colTotalPageWH.setCellValueFactory(new PropertyValueFactory<>("Total"));

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        tvWareHouseReceipt.setFixedCellSize(40);
    }

    public void getDataTBProductPageNHI() {
        String sql = "select ProductId, ProductName from Product";
        try {
            // Tạo statement
            stm = con.createStatement();
            // Truy vấn
            rs = stm.executeQuery(sql);

            // Tạo ObservableList để lưu dữ liệu từ ResultSet
            ProductListPageNHI = FXCollections.observableArrayList();
            ProductListPageNHI.clear();

            while (rs.next()) {
                String pid = rs.getString("productid");
                String pname = rs.getString("ProductName");

                ProductListPageNHI.add(new ProductPageNHI(pid, pname));

            }
            tvProductPageNHI.setItems(ProductListPageNHI);

            colProductIDPageNHI.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
            colProductNamePageNHI.setCellValueFactory(new PropertyValueFactory<>("ProductName"));

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        tvProductPageNHI.setFixedCellSize(30);
    }

    public void handleSelectProductPageNHI(ProductPageNHI productSelected) {
        String pid = productSelected.getProductID().trim();
        String pname = productSelected.getProductName().trim();
        lbProductIDPageNHI.setText(pid);
        lbProductNamePageNHI.setText(pname);

    }

    public void getDataSupplierPageNHI() {
        String sql = "select suppilercode,suppilername from Suppiler";
        try {
            // Tạo statement
            stm = con.createStatement();
            // Truy vấn
            rs = stm.executeQuery(sql);

            // Tạo ObservableList để lưu dữ liệu từ ResultSet
            SuppilerListPageNHI = FXCollections.observableArrayList();
            SuppilerListPageNHI.clear();

            while (rs.next()) {
                int sid = rs.getInt("SuppilerCode");
                String sname = rs.getString("SuppilerName");
                SuppilerListPageNHI.add(new SuppilerQ(sid, sname));

            }

            cbSupplierPageNHI.setItems(SuppilerListPageNHI);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void setDataProductSelectedPageNHI() {

        tvProductSelectedPageNHI.getItems().clear();
        // Thiết lập giá trị cho các cột
        colSttSelectedPageNHI.setCellValueFactory(data -> {
            ProductSelectedPageNHI p = data.getValue();
            int rowIndex = tvProductSelectedPageNHI.getItems().indexOf(p) + 1;
            return new SimpleIntegerProperty(rowIndex);
        });

        colProductIdSelectedPageNHI.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        colProductNameSelectedPageNHI.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        colQuantitySelectedPageNHI.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colImportPriceSelectedPageNHI.setCellValueFactory(new PropertyValueFactory<>("ImportPrice"));
        colExpiryDateSelectedPageNHI.setCellValueFactory(new PropertyValueFactory<>("ExpiryDate"));

        // Thêm dữ liệu từ danh sách ProductSelectedListPageNHI vào bảng
        tvProductSelectedPageNHI.getItems().addAll(ProductSelectedListPageNHI);
    }

    @FXML
    private void handleSearchbyDateOfWareHouse(ActionEvent event) {
        // Kiểm tra nếu chưa chọn ngày Date To hoặc Date From
        if (dpDateFromofWareHouse.getValue() == null || dpDateToOfWareHouse.getValue() == null) {
            showAlert("Please choose Date To and Date From!");
            return;
        }

        // Lấy ngày Date From và Date To từ DatePicker
        LocalDate localDateFrom = dpDateFromofWareHouse.getValue();
        LocalDate localDateTo = dpDateToOfWareHouse.getValue();

        // Kiểm tra nếu Date From > Date To
        if (localDateFrom.isAfter(localDateTo)) {
            showAlert("Date From must be before Date To!");
            return;
        }

        // Chuyển đổi LocalDate sang định dạng String
        String dateFrom = localDateFrom.toString();
        String dateTo = localDateTo.toString();

        ObservableList<WareHouse> filteredList = FXCollections.observableArrayList();

        // Lọc danh sách dựa trên khoảng thời gian
        for (WareHouse wareHouse : WHRListPageNHI) {
            String wareHouseDate = wareHouse.getWHDateTime();
            if (isDateInRangePageWH(wareHouseDate, dateFrom, dateTo)) {
                filteredList.add(wareHouse);
            }
        }

        // Kiểm tra nếu không có bản ghi nào được tìm thấy
        if (filteredList.isEmpty()) {
            showAlert("No WareHouse records found for the selected date range.");
            tvWareHouseReceipt.setItems(WHRListPageNHI);
        } else {
            // Cập nhật TableView với danh sách lọc
            tvWareHouseReceipt.setItems(filteredList);
        }
    }

    private boolean isDateInRangePageWH(String wareHouseDate, String dateFrom, String dateTo) {
        // So sánh chuỗi ngày trực tiếp nếu định dạng là "yyyy-MM-dd HH-mm-ss"
        return wareHouseDate.compareTo(dateFrom) >= 0 && wareHouseDate.compareTo(dateTo) <= 0;
    }

    @FXML
    private void btnExportExcelPageWH(ActionEvent event) {
        // Lấy dữ liệu từ TableView (tvWareHouseReceipt) hoặc từ ObservableList lưu trữ dữ liệu

        ObservableList<WareHouse> data = tvWareHouseReceipt.getItems();

        if (data.isEmpty()) {
            showAlert("No data to export!");
            return;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String currentDateTime = LocalDateTime.now().format(dtf);

        // Tạo một Workbook mới (đối tượng Excel)
        Workbook workbook = new XSSFWorkbook();

        // Tạo một trang tính mới
        Sheet sheet = workbook.createSheet("WareHouse Receipt Data");

        // Tạo một hàng đầu tiên (tiêu đề)
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Time");
        headerRow.createCell(1).setCellValue("Employee Name");
        headerRow.createCell(2).setCellValue("Supplier Name");
        headerRow.createCell(3).setCellValue("WareHouse Receipt ID");
        headerRow.createCell(4).setCellValue("Total Bill");

        // Ghi dữ liệu từ ObservableList vào Workbook
        for (int i = 0; i < data.size(); i++) {
            WareHouse wareHouse = data.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(wareHouse.getWHDateTime());
            row.createCell(1).setCellValue(wareHouse.getEmployeeName());
            row.createCell(2).setCellValue(wareHouse.getSupplierName());
            row.createCell(3).setCellValue(wareHouse.getNkid());
            row.createCell(4).setCellValue(wareHouse.getTotal());
        }

        // Mở một cửa sổ lưu file Excel
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        String randomNumber = UUID.randomUUID().toString().substring(0, 8);
        fileChooser.setInitialFileName("WareHouseData_" + randomNumber);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx"));

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try {
                // Tạo một luồng để ghi dữ liệu vào file Excel
                FileOutputStream fileOut = new FileOutputStream(file);
                workbook.write(fileOut);
                fileOut.close();
                workbook.close();
                showAlert("Excel file exported successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error exporting Excel file: " + e.getMessage());
            }
        }
    }

    @FXML
    private void btnDetailPageWH(ActionEvent event
    ) {

        WareHouse selectedItem = tvWareHouseReceipt.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showAlert("Please select a bill first!");
            return;
        }

        String sql = "SELECT p.ProductName, wh.Quantity, wh.ImportPrice, wh.Quantity * wh.ImportPrice AS totalPrice "
                + "FROM WareHouseDetail wh "
                + "JOIN Product p ON wh.ProductId = p.ProductId "
                + "WHERE wh.nkid = ?";

        try {
            // Tạo prepared statement với con trỏ cuộn
            PreparedStatement pstm = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pstm.setInt(1, selectedItem.getNkid());

            // Thực thi câu lệnh SQL và lấy ResultSet
            ResultSet rs = pstm.executeQuery();

            String dateTimeOrder = selectedItem.getWHDateTime();
            String empName = selectedItem.getEmployeeName();
            String supplierName = selectedItem.getSupplierName();
            int nkid = selectedItem.getNkid();
            float totalPrice = selectedItem.getTotal();

            AtomicInteger row = new AtomicInteger(1); // AtomicInteger for mutable row value

            // Tạo dialog để hiển thị chi tiết đơn hàng
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("WareHouse Receipt Details");
            dialog.setHeaderText(null);

            // Tạo VBox để chứa các thông tin sản phẩm
            VBox vbox = new VBox();
            vbox.setSpacing(10);
            vbox.setPadding(new Insets(20, 150, 10, 10));

            Label lbTime = new Label("Time: " + dateTimeOrder);
            Label lbEmpName = new Label("Employee Name: " + empName);
            Label lbSupplierName = new Label("Supplier Name: " + supplierName);
            Label lbNkid = new Label("WareHouse Receipt ID: " + nkid);

            vbox.getChildren().addAll(lbTime, lbEmpName, lbSupplierName, lbNkid);
            vbox.getChildren().add(new Separator(Orientation.HORIZONTAL));

            // Tạo GridPane để hiển thị danh sách sản phẩm
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(5);

            // Định dạng tiền tệ Việt Nam
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

            // Tiêu đề cột
            Label productName = new Label("Product Name");
            Label quantity = new Label("Quantity");
            Label importPrice = new Label("Import Price");
            Label total = new Label("Total Price");

            productName.setStyle("-fx-font-weight: bold");
            quantity.setStyle("-fx-font-weight: bold");
            importPrice.setStyle("-fx-font-weight: bold");
            total.setStyle("-fx-font-weight: bold");

            gridPane.add(productName, 0, 0);
            gridPane.add(quantity, 1, 0);
            gridPane.add(importPrice, 2, 0);
            gridPane.add(total, 3, 0);

            // Populate the GridPane with data from ResultSet rs
            while (rs.next()) {
                String pName = rs.getString("ProductName");
                int pquantity = rs.getInt("Quantity");
                float pimportPrice = rs.getFloat("ImportPrice");
                float ptotalPrice = rs.getFloat("totalPrice");

                // Hiển thị thông tin sản phẩm vào GridPane
                Label productNameLabel = new Label(pName);
                Label quantityLabel = new Label(Integer.toString(pquantity));
                Label importPriceLabel = new Label(currencyFormat.format(pimportPrice));
                Label totalLabel = new Label(currencyFormat.format(ptotalPrice));

                gridPane.add(productNameLabel, 0, row.get());
                gridPane.add(quantityLabel, 1, row.get());
                gridPane.add(importPriceLabel, 2, row.get());
                gridPane.add(totalLabel, 3, row.get());

                row.incrementAndGet(); // Increment row
            }

            // Thêm GridPane vào VBox
            vbox.getChildren().add(gridPane);

            // Thêm dòng kẻ ngang sau danh sách sản phẩm
            vbox.getChildren().add(new Separator(Orientation.HORIZONTAL));

            // Thêm dòng hiển thị tổng hóa đơn
            Label totalBillLabel = new Label("Total Bill: " + currencyFormat.format(totalPrice));
            totalBillLabel.setStyle("-fx-font-weight: bold");
            vbox.getChildren().add(totalBillLabel);

            // Nếu không có sản phẩm nào được tìm thấy
            if (row.get() == 1) { // Kiểm tra số lượng sản phẩm để hiển thị thông báo phù hợp
                Label noDetailsLabel = new Label("No details found for selected.");
                vbox.getChildren().add(noDetailsLabel);
            }

            // Bọc VBox trong một ScrollPane để tránh tràn màn hình
            ScrollPane scrollPane = new ScrollPane(vbox);
            scrollPane.setFitToWidth(true);
            scrollPane.setPrefHeight(400);  // Set fixed height
            scrollPane.setPrefWidth(600);   // Set fixed width

            // Đặt ScrollPane làm nội dung của dialog
            dialog.getDialogPane().setContent(scrollPane);

            // Thêm nút "Close" vào dialog (trước nút "Print Bill Details")
            ButtonType closeButtonType = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().add(closeButtonType);

            // Thêm nút "Print Bill Details" vào dialog (sau nút "Close")
            ButtonType printButtonType = new ButtonType("Print Bill Details", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(printButtonType);

            // Xử lý sự kiện khi nhấn nút "Print Bill Details"
            dialog.setResultConverter(buttonType -> {
                if (buttonType == printButtonType) {
                    try {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                        String formattedDateTime = dtf.format(LocalDateTime.now());

                        ObservableList<ProductSelectedPageNHI> listProductSelected = FXCollections.observableArrayList();
                        rs.beforeFirst(); // Move cursor to the start of ResultSet
                        while (rs.next()) {
                            String pName = rs.getString("ProductName");
                            int pquantity = rs.getInt("Quantity");
                            float pimportPrice = rs.getFloat("ImportPrice");
                            float ptotalPrice = rs.getFloat("totalPrice");

                            listProductSelected.add(new ProductSelectedPageNHI(pName, pquantity, pimportPrice, ptotalPrice));
                        }

                        exportToWordPageWH(dateTimeOrder, empName, supplierName, nkid, listProductSelected, totalPrice);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            });

            dialog.showAndWait();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void exportToWordPageWH(String dateTimeOrder, String empName, String supplierName, int nkid, ObservableList<ProductSelectedPageNHI> listProductSelected, float totalPrice) {
        // Create a new document
        XWPFDocument document = new XWPFDocument();

        // Create title paragraph
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        titleRun.setText("WareHouse Receipt Details");
        titleRun.setBold(true);
        titleRun.setFontSize(20);

        // Create metadata paragraph
        XWPFParagraph meta = document.createParagraph();
        XWPFRun metaRun = meta.createRun();
        metaRun.setText("Time: " + dateTimeOrder);
        metaRun.addBreak();
        metaRun.setText("Employee Name: " + empName);
        metaRun.addBreak();
        metaRun.setText("Supplier Name: " + supplierName);
        metaRun.addBreak();
        metaRun.setText("WareHouse Receipt ID: " + nkid);
        metaRun.addBreak();

        // Create table
        XWPFTable table = document.createTable();

        // Create table headers
        XWPFTableRow header = table.getRow(0);
        header.getCell(0).setText("Product Name");
        header.addNewTableCell().setText("Quantity");
        header.addNewTableCell().setText("Import Price");
        header.addNewTableCell().setText("Total Price");

        // Populate table with product details
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        for (ProductSelectedPageNHI product : listProductSelected) {
            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(product.getProductName());
            row.getCell(1).setText(String.valueOf(product.getQuantity()));
            row.getCell(2).setText(currencyFormat.format(product.getImportPrice()));
            row.getCell(3).setText(currencyFormat.format(product.getTotalPrice()));
        }

        // Add total price at the end
        XWPFParagraph total = document.createParagraph();
        XWPFRun totalRun = total.createRun();
        totalRun.setText("Total Bill: " + currencyFormat.format(totalPrice));
        totalRun.setBold(true);

        // Save document to file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Word Document");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Word documents (*.docx)", "*.docx");
        fileChooser.getExtensionFilters().add(extFilter);

        String randomNumber = UUID.randomUUID().toString().substring(0, 8);
        fileChooser.setInitialFileName("BillWareHouseDetail_" + randomNumber);

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (FileOutputStream out = new FileOutputStream(file)) {
                document.write(out);
                showAlert("Export bill successfully!");
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error saving document!");
            }
        }
    }

    @FXML
    private void btnAddPageWH(ActionEvent event) {
        viewWarehouseReceiptInterface.setVisible(false);
        nhaphangInterface.setVisible(true);
    }

    @FXML
    private void btnDeleteSelectedPageNHI(MouseEvent event) {
        // Lấy sản phẩm được chọn trong TableView
        ProductSelectedPageNHI selectedProduct = tvProductSelectedPageNHI.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            showAlert("Please select a product to delete.");
            return;
        }

        // Hiển thị hộp thoại xác nhận
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure ?");

        // Tùy chọn các nút trong hộp thoại
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        // Thêm các nút vào hộp thoại
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        // Chờ người dùng xác nhận
        Optional<ButtonType> result = alert.showAndWait();

        // Xử lý kết quả từ hộp thoại
        if (result.isPresent() && result.get() == buttonTypeYes) {
            // Tìm vị trí của sản phẩm trong danh sách ProductSelectedListPageNHI
            int index = ProductSelectedListPageNHI.indexOf(selectedProduct);

            // Nếu tìm thấy sản phẩm, xóa nó khỏi danh sách và cập nhật giao diện
            if (index != -1) {
                ProductSelectedListPageNHI.remove(index); // Xóa sản phẩm khỏi danh sách

                // Cập nhật lại dữ liệu hiển thị trên TableView hoặc các thành phần giao diện khác
                setDataProductSelectedPageNHI(); // Ví dụ, cập nhật lại TableView
                calculateTotalPricePageNHI(); // Tính lại tổng tiền sau khi xóa

                showAlert("Product deleted successfully!");
                if (ProductSelectedListPageNHI.isEmpty()) {
                    iconDeletePageNHI.setVisible(false);
                }
            } else {
                showAlert("Product not found in the list!");
            }
        }
    }

    @FXML
    private void btnImportProductPageNHI(ActionEvent event) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        // Định dạng ngày giờ thành chuỗi
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        int selectedIDSupplier = 0;
        int nkid = getWHRIDPageNHI();
        SuppilerQ selectedSupplier = cbSupplierPageNHI.getValue();
        if (ProductSelectedListPageNHI.size() == 0) {
            showAlert("Please select at least 1 product !");
            return;
        }
        if (selectedSupplier != null) {
            selectedIDSupplier = selectedSupplier.getSuppilerCode();
        } else {
            showAlert("Please select a supplier first!");
            return;
        }

        //cập nhật db WH Receipt
        updateDataBaseWHReceiptPageNHI(formattedDateTime, selectedIDSupplier, getLoginUser().getEmpID(), txtTotalPageNHI.getText().replace(" VND", "").replace(",", "").trim());

        //cập nhật db WH Detail
        updateDataBaseWHDetailPageNHI(ProductSelectedListPageNHI, nkid);

        //cap nhật db ProductDetail
        updateDataBaseProductDetailPageNHI(ProductSelectedListPageNHI, nkid);

        showAlert("Import product successfully");

        showSuppilers();
        //reset
        tvProductSelectedPageNHI.getItems().clear();
        ProductSelectedListPageNHI.clear();
        iconDeletePageNHI.setVisible(false);
        resetTextPageNHI();
        txtTotalPageNHI.setText("0 VND");
        getDataWHR();
        getWHRIDPageNHI();
        //chuyen ve trang 1
        viewWarehouseReceiptInterface.setVisible(true);
        nhaphangInterface.setVisible(false);

    }

    @FXML
    private void btnAcceptPageNHI(ActionEvent event) {
        if (lbProductIDPageNHI.getText().isEmpty() || lbProductNamePageNHI.getText().isEmpty()) {
            showAlert("Before choose product first !");
            return;
        }

        boolean productExists = false;
        for (ProductSelectedPageNHI product : ProductSelectedListPageNHI) {
            if (product.getProductID().equals(lbProductIDPageNHI.getText().trim())) {
                productExists = true;
                break;
            }
        }

        if (productExists) {
            showAlert("This product is already selected!");
            resetTextPageNHI();
            return;
        }

        try {
            int quantity = Integer.parseInt(lbQuantityPageNHI.getText().trim());
            int importPrice = Integer.parseInt(lbImportPricePageNHI.getText().trim());
            float productPrice = importPrice * 1.3f;
            String date = DatepickerExpiryPageNHI.getValue().toString();
            //      if (tfCustomerDDob1.getValue().compareTo(LocalDate.now().minusYears(14)) <= 0
            //&& tfCustomerDDob1.getValue().compareTo(LocalDate.now().minusYears(70)) >= 0) {
            if (quantity <= 0 || importPrice <= 0 || productPrice <= 0 || productPrice < importPrice || DatepickerExpiryPageNHI.getValue().compareTo(LocalDate.now().plusDays(1)) < 0) {
                showAlert("Quantity, Import Price and Expiry Date is invalid ! Please try again !");
                return;
            }

            //xử lý cho sp vào bảng productSelected
            ProductSelectedPageNHI productSelected = new ProductSelectedPageNHI(
                    lbProductIDPageNHI.getText().trim(),
                    lbProductNamePageNHI.getText().trim(),
                    quantity,
                    importPrice,
                    date,
                    productPrice
            );

            // Thêm sản phẩm vào danh sách hiện có
            ProductSelectedListPageNHI.add(productSelected);

            // Cập nhật dữ liệu hiển thị trên bảng
            setDataProductSelectedPageNHI();
            calculateTotalPricePageNHI();
            iconDeletePageNHI.setVisible(true);
            //reset Text
            resetTextPageNHI();

        } catch (NumberFormatException e) {
            showAlert("Quantity, Import Price must be valid numbers!");
        }
    }

    private void calculateTotalPricePageNHI() {
        float total = 0;
        for (ProductSelectedPageNHI product : ProductSelectedListPageNHI) {
            total += product.getTotalPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedNumber = decimalFormat.format(total);

        txtTotalPageNHI.setText(formattedNumber + " VND");
    }

    public int getWHRIDPageNHI() {
        int nkid = 1;
        String sql = "SELECT TOP 1 nkid FROM WareHouseReceipt ORDER BY nkid DESC";

        try {
            // Tạo và thực thi câu lệnh SQL
            stm = con.createStatement();
            rs = stm.executeQuery(sql);

            if (rs.next()) {
                nkid = rs.getInt("nkid") + 1;
            }

            // Đặt giá trị vào txtWRIPageNHI
            txtWRIPageNHI.setText(String.valueOf(nkid));

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return nkid;
    }

    public void updateDataBaseWHReceiptPageNHI(String datetime, int supplierID, int empid, String total) {
        String sql = "INSERT INTO warehousereceipt (warehousedatetime, suppliercode, creatorwarehouse, total) "
                + "VALUES (?, ?, ?, ?)";

        try {
            // Tạo prepared statement với câu lệnh SQL đã chuẩn bị
            PreparedStatement pstmt = con.prepareStatement(sql);

            // Gán các giá trị vào các tham số của câu lệnh SQL
            pstmt.setString(1, datetime);
            pstmt.setInt(2, supplierID);
            pstmt.setInt(3, empid);
            pstmt.setString(4, total);

            // Thực thi câu lệnh INSERT
            pstmt.executeUpdate();

            // Đóng prepared statement sau khi hoàn thành
            pstmt.close();

            System.out.println("Data updated successfully!");

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            showAlert("Failed to update data ");
        }
    }

    public void updateDataBaseWHDetailPageNHI(ObservableList<ProductSelectedPageNHI> productSelected, int nkid) {
        String sql = "INSERT INTO warehousedetail (nkid, ProductId, Quantity, ImportPrice, hsd) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            // Tạo prepared statement với câu lệnh SQL đã chuẩn bị
            PreparedStatement pstmt = con.prepareStatement(sql);

            for (ProductSelectedPageNHI product : productSelected) {

                // Gán các giá trị vào các tham số của câu lệnh SQL cho từng sản phẩm
                pstmt.setInt(1, nkid);
                pstmt.setString(2, product.getProductID());  // ProductId
                pstmt.setInt(3, product.getQuantity()); // Quantity
                pstmt.setFloat(4, product.getImportPrice()); // ImportPrice
                pstmt.setString(5, product.getExpiryDate()); // hsd

                // Thực thi câu lệnh INSERT
                pstmt.executeUpdate();
            }

            // Đóng prepared statement sau khi hoàn thành
            pstmt.close();

            System.out.println("WareHouse Detail Table updated successfully!");

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            showAlert("Failed to update data");
        }
    }

    public void updateDataBaseProductDetailPageNHI(ObservableList<ProductSelectedPageNHI> productSelected, int nkid) {
        String sql = "INSERT INTO productDetail (productid, NKId, ImportPrice, ProductPrice, ImportQuantity,HSD,Discount,SoldQuantity) "
                + "VALUES (?, ?, ?, ?, ?,?,?,?)";

        try {
            // Tạo prepared statement với câu lệnh SQL đã chuẩn bị

            PreparedStatement pstmt = con.prepareStatement(sql);

            for (ProductSelectedPageNHI product : productSelected) {

                pstmt.setString(1, product.getProductID());
                pstmt.setInt(2, nkid);
                pstmt.setFloat(3, product.getImportPrice());
                pstmt.setFloat(4, product.getProductPrice());
                pstmt.setInt(5, product.getQuantity());
                pstmt.setString(6, product.getExpiryDate());
                pstmt.setFloat(7, 0);
                pstmt.setInt(8, 0);

                // Thực thi câu lệnh INSERT
                pstmt.executeUpdate();
            }

            // Đóng prepared statement sau khi hoàn thành
            pstmt.close();

            System.out.println("Product Detail updated successfully!");

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            showAlert("Failed to update data");
        }
    }

    @FXML
    private void btnBackPageNHI(MouseEvent event) {
        viewWarehouseReceiptInterface.setVisible(true);
        nhaphangInterface.setVisible(false);
        getDataWHR();
    }

    public void resetTextPageNHI() {
        lbProductIDPageNHI.clear();
        lbProductNamePageNHI.clear();
        lbQuantityPageNHI.clear();
        lbImportPricePageNHI.clear();
        DatepickerExpiryPageNHI.setValue(null);
        cbSupplierPageNHI.setValue(null);
        cbSupplierPageNHI.setPromptText("Choose supplier");
    }

    @FXML
    private void openWareHouseInterface(ActionEvent event) {
        StackPaneMainPage.getChildren().filtered(predicate -> predicate.isVisible()).forEach(item -> item.setVisible(false));
        WareHouseInterface.setVisible(true);
    }

    public ObservableList<Acount> getAcount() {
        //tao ds chua cac product
        ObservableList<Acount> list = FXCollections.observableArrayList();

        //tao doi tuong
        //viet lenh truy van
        String query = "SELECT * FROM Account";

        //thuc thi
        Statement st;
        ResultSet rs;

        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            Acount s;
            while (rs.next()) {
                int id = rs.getInt("EmpID");
                String username = rs.getString("Username");
                boolean Status = rs.getBoolean("Status");
                String password = rs.getString("password");
                int role = rs.getInt("role");

                s = new Acount(id, role, username, password, Status);
                //System.out.println(p);
                //them san pham moi vao list
                list.add(s);
                System.out.println(s);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;

    }

    public void showAcount() {
        ObservableList<Acount> lisr = getAcount();

        //show ds
        IDColOfAccountPage.setCellValueFactory(new PropertyValueFactory<>("id"));
        UsernameColOfAcountPage.setCellValueFactory(new PropertyValueFactory<>("username"));
        StatusColOfAccountPage.setCellValueFactory(new PropertyValueFactory<>("Status"));
        PasswordColOfAccountPage.setCellValueFactory(new PropertyValueFactory<>("password"));
        RoleColOfAccountPage.setCellValueFactory(new PropertyValueFactory<>("role"));

        StatusColOfAccountPage.setCellFactory(column -> new TableCell<Acount, Boolean>() {
            private final Label labelStatus = new Label();

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    labelStatus.setText(item ? "Enable" : "Disable");
                    if (labelStatus.getText().equals("Enable")) {
                        labelStatus.setStyle("-fx-background-color: blue; -fx-text-fill: #fff");
                    } else {
                        labelStatus.setStyle("-fx-background-color: black; -fx-text-fill: #fff");
                    }
                    setGraphic(labelStatus);

                } else {
                    setGraphic(null);
                }
            }

        });
        tvAcount.setItems(lisr);
    }

    @FXML
    private void OnAddAccount(ActionEvent event) {
        String name = NameAcountAdd.getText().trim();
        String pass = PassAcountAdd.getText().trim();

        Pattern p;
        Matcher m;
        try {

            p = Pattern.compile("[A-Za-z0-9]{4,10}");
            if (name.isEmpty()) {
                throw new Exception("Username is not null");
            }
            m = p.matcher(name); 
            if (m.matches()) {
            } else {
                throw new Exception("Username is not in correct format, name includes letters, numbers, - and no spaces, from 4 - 10 characters.");
            }
            List<Acount> AList = getAcount().stream().filter(predicate -> predicate.getUsername().equals(name)).collect(Collectors.toList());
            if(!AList.isEmpty()){
                throw new Exception("Username must be not dupplicate");
            }

//            p = Pattern.compile("[^a-zA-Z_0-9]{4,10}");
            if (pass.isEmpty()) {
                throw new Exception("Password is not null");
            }
//            m = p.matcher(pass);
//            if (m.matches()) {
//            } else {
//                throw new Exception("Password chua dung dinh dang.");
//            }
            String sql = "INSERT INTO Account VALUES('" + name + "','" + pass + "'," + "1,1,null)";
            System.out.println(sql);
            executeSQL(sql);
            showAcount();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();

        }

    }

    @FXML
    private void AddAcount(ActionEvent event) throws IOException {
        addAccountInterface.setDisable(false);
    }

    @FXML
    private void handleEditAccount(ActionEvent event) {

        String sql = "UPDATE Account SET Status = ? WHERE Username = ?";
        PreparedStatement ps;
        int Role;
        Boolean Status;
        if (toggleBtnStatusEdit.getText().equals("Enable")) {
            Status = true;
        } else {
            Status = false;
        }
        try {
            ps = con.prepareStatement(sql);
            ps.setBoolean(1, Status);
            ps.setString(2, SelectAccountOfAccountInterFace.getUsername());
            ps.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Edit Account Successfully");
            alert.show();

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.getMessage());
            alert.show();

        }
        showAcount();

    }

    @FXML
    private void OnCancelAddAccount(ActionEvent event) {
        addAccountInterface.setDisable(true);
    }

    public void SelectAccount() {
        tvAcount.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                SelectAccountOfAccountInterFace = newSelection;
                EditAccountInterface.setDisable(false);
                tfUsernameOfEditAccount.setText(SelectAccountOfAccountInterFace.getUsername());
                if (SelectAccountOfAccountInterFace.isStatus()) {
                    toggleBtnStatusEdit.setText("Enable");
                    toggleBtnStatusEdit.setStyle("-fx-background-color: blue; -fx-text-fill: #fff");
                } else {
                    toggleBtnStatusEdit.setText("Disable");
                    toggleBtnStatusEdit.setStyle("-fx-background-color: black; -fx-text-fill: #fff");
                }
                toggleBtnStatusEdit.setOnAction(event -> {
                    if (toggleBtnStatusEdit.getText().equals("Enable")) {
                        toggleBtnStatusEdit.setText("Disable");
                        toggleBtnStatusEdit.setStyle("-fx-background-color: black; -fx-text-fill: #fff");
                    } else {
                        toggleBtnStatusEdit.setText("Enable");
                        toggleBtnStatusEdit.setStyle("-fx-background-color: blue; -fx-text-fill: #fff");
                    }
                });

            }

        });
    }

    @FXML
    private void openAccountInterface(ActionEvent event) {
        StackPaneMainPage.getChildren().filtered(predicate -> predicate.isVisible()).forEach(item -> item.setVisible(false));
        AccountInterface.setVisible(true);
    }

    @FXML
    private void openEditProfile(ActionEvent event) {
//        StackPaneMainPage.getChildren().filtered(predicate -> predicate.isVisible()).forEach(item -> item.setVisible(false));
//        StackPaneMainPage.getChildren().filtered(predicate -> predicate.isVisible()).forEach(item -> item.setVisible(false));
        pageEditProfile.setVisible(true);
        EditProfileDialog = new Dialog<>();
        EditProfileDialog.getDialogPane().setContent(pageEditProfile);
        EditProfileDialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        EditProfileDialog.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);
        EditProfileDialog.show();

    }

    @FXML
    private void btnChooseAvatarEP(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        selectedFileAvatarProfile = fileChooser.showOpenDialog(null);

        if (selectedFileAvatarProfile != null) {
            selectImageURLofProfile = selectedFileAvatarProfile.toURI().toString();

            selectImageNameOfProfile = selectedFileAvatarProfile.getName();
            //System.out.println(selectImageName);
            //copy file anh da chon ve folder duong dan anh IMAGE_DIR trong project
            from = Paths.get(selectedFileAvatarProfile.toURI());
            to = Paths.get(IMAGE_DIR + selectImageNameOfProfile);

            CopyOption options = StandardCopyOption.REPLACE_EXISTING;
            try {
                Files.copy(from, to, options);
            } catch (IOException ex) {
                System.out.println("Error: cannot copy " + ex.getMessage());
            }

            if (event.getSource() == btnChangeAvatarProfile) {
                imgEmpEP.setImage(new Image(selectImageURLofProfile));
            } else {
                String imagePath = "src\\main\\resources\\group2\\imageProduct\\" + selectImageNameOfProfile;
                System.out.println(imagePath);

                Image image = new Image(new File(imagePath).toURI().toString());
                imgEmpEP.setImage(new Image(selectImageURLofProfile));
            }
        }
//        else {
//            selectImageURLofProfile = imgEmpEP.getImage().toString();
//            System.out.println(selectImageURLofProfile);
//        }
    }

    @FXML
    private void btnCancelEP(ActionEvent event) {
        EditProfileDialog.close();
    }

    @FXML
    private void btnSaveEP(ActionEvent event) {

        int EmpID = getLoginUser().getEmpID();
        String name = txtNameEP.getText().trim();
        String PhoneNumber = txtPhoneEP.getText().trim();
        String CCCD = txtidEP.getText().trim();

        String email = txtEmailEP.getText();
        String num = txtPhoneEP.getText().trim();
        String gender = FeMaleGenderRadioBtnofEditProfile.isSelected() ? "FeMale" : "Male";
        String Dob = DatepickerYOBEP.getValue().toString();
        Pattern p;
        Matcher m;
        try {
            if (name.isEmpty()) {
                throw new Exception("Name is not null");
            }

            p = Pattern.compile("[\\w.+\\-]+@[A-Za-z]{1,}\\.com");
            if (email.isEmpty()) {
                throw new Exception("Email is not null");
            }
             List<Staff> emailList = getStaff().stream().filter(predicate -> predicate.getSEmail().equals(email) && !predicate.getSEmail().equals(getLoginUser().getEmail())).collect(Collectors.toList());
            if (!emailList.isEmpty()) {
                throw new Exception("Email must be not duplicate");
            }

            m = p.matcher(email);
            if (m.matches()) {
            } else {
                throw new Exception("Email is not correct format.");
            }
            p = Pattern.compile("^0\\d{9}$");
            if (num.isEmpty()) {
                throw new Exception("PhoneNumber is not null");
            }

            m = p.matcher(num);
            if (m.matches()) {
            } else {
                throw new Exception("PhoneNumber is not correct format, (0XXXXXXXXX with X is digits, The first X is different from 0).");
            }
              List<Staff> c2List = getStaff().stream().filter(predicate -> predicate.getSPhone().equals(num) && !predicate.getSPhone().equals(getLoginUser().getPhoneNumber())).collect(Collectors.toList());
            if (!c2List.isEmpty()) {
                throw new Exception("Phone Number must be not duplicate");
            }
            p = Pattern.compile("^0\\d{11}$");
            if (CCCD.isEmpty()) {
                throw new Exception("Citizen identification number must be not null");
            }
             List<Staff> c3List = getStaff().stream().filter(predicate -> predicate.getSCCCD().equals(CCCD) && !predicate.getSCCCD().equals(getLoginUser().getCCCDNumber())).collect(Collectors.toList());
            if (!c3List.isEmpty()) {
                throw new Exception("Citizen identification must be not duplicate");
            }

            m = p.matcher(CCCD);
            if (m.matches()) {
            } else {
                throw new Exception("Citizen identification number is not correct format  (0XXXXXXXXXXX with X is digits");
            }

            String sql = "UPDATE Employee SET EmpName = ?, PhoneNumber = ?, CCCD = ?, YOB = ?,\n"
                    + "SEmail = ?, Gender = ?, SImage = ? \n"
                    + "where EmpID = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, num);
            ps.setString(3, CCCD);
            ps.setString(4, Dob);
            ps.setString(5, email);
            ps.setString(6, gender);
            ps.setString(7, selectImageNameOfProfile);
            ps.setInt(8, getLoginUser().getEmpID());

            if (ps.executeUpdate() != 1) {

                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setContentText("Edit profile failed");
                alert.show();
            } else {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setContentText("Edit profile successfully");
                alert.show();

                showProducts();

            }
            System.out.println(sql);

            if (selectedFileAvatarProfile != null) {

                //System.out.println(selectImageName);
                //copy file anh da chon ve folder duong dan anh IMAGE_DIR trong project
                from = Paths.get(selectedFileAvatarProfile.toURI());
                to = Paths.get(IMAGE_DIR + selectImageNameOfProfile);

                CopyOption options = StandardCopyOption.REPLACE_EXISTING;
                try {
                    Files.copy(from, to, options);
                } catch (IOException ex) {
                    System.out.println("Error: cannot copy " + ex.getMessage());
                }

            } else {

            }
            EditProfileDialog.close();
            lbNameUser.setText(name);
            txtNameEP.setText(name);
            txtPhoneEP.setText(num);
            txtidEP.setText(CCCD);
            txtEmailEP.setText(email);
            DatepickerYOBEP.setValue(LocalDate.parse(Dob));
            if (gender.equals("FeMale")) {
                FeMaleGenderRadioBtnofEditProfile.setSelected(true);
            } else {
                MaleGenderRadioBtnofEditProfile.setSelected(true);
            }
            String imagePath = "src\\main\\resources\\group2\\imageProduct\\" + selectImageNameOfProfile;
            System.out.println(imagePath);

            Image image = new Image(new File(imagePath).toURI().toString());
            imvUserLogin.setImage(image);
            imgEmpEP.setImage(image);
            showStaffofView();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
            event.consume();
        }

    }

    //==============================================================================
    //==============================HANDLE BRAND AND CATEGORY INTERFACE =============
    //===============================================================================
    public void setToolTipofBC() {
        Tooltip tooltip = new Tooltip("Add Brand");
        Tooltip.install(btnAddBrand, tooltip);

        Tooltip tt2 = new Tooltip("Edit Brand");
        Tooltip.install(btnEditBrand, tt2);

        Tooltip tt3 = new Tooltip("Add Category");
        Tooltip.install(btnAddCategory, tt3);

        Tooltip tt4 = new Tooltip("Edit Category");
        Tooltip.install(btnEditCategory, tt4);
    }

    public void getBrandofBC() {
        String sqlBrand = "select * from brand";
        try {
            // Tạo statement
            stm = con.createStatement();
            // Truy vấn
            rs = stm.executeQuery(sqlBrand);

            brandListofBC.clear();
            while (rs.next()) {
                int bid = rs.getInt("brandid");
                String bname = rs.getString("brandname");
                brandListofBC.add(new BrandQ(bid, bname.trim()));

            }
            tvBrandofBC.setItems(brandListofBC);

            colBrandIDofBC.setCellValueFactory(new PropertyValueFactory<>("bID"));
            colBrandNameofBC.setCellValueFactory(new PropertyValueFactory<>("bName"));

        } catch (SQLException ex) {
            Logger.getLogger(BrandAndCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tvBrandofBC.setFixedCellSize(30);
    }

    public void getCategoryofBC() {
        String sql = "select * from category";
        try {
            // Tạo statement
            stm = con.createStatement();
            // Truy vấn
            rs = stm.executeQuery(sql);

            categoryListofBC.clear();

            while (rs.next()) {
                int cid = rs.getInt("categoryid");
                String cname = rs.getString("categoryname");

                categoryListofBC.add(new CategoryQ(cid, cname.trim()));

            }
            tvCategoryofBC.setItems(categoryListofBC);

            colCategoryIDofBC.setCellValueFactory(new PropertyValueFactory<>("cID"));
            colCategoryNameofBC.setCellValueFactory(new PropertyValueFactory<>("cName"));

        } catch (SQLException ex) {
            Logger.getLogger(BrandAndCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tvCategoryofBC.setFixedCellSize(30);
    }

    @FXML
    private void btnAddBrand(MouseEvent event) {

        // Tạo dialog
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add New Brand");

        // Nút OK (Thêm) và Cancel
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Tạo các trường nhập liệu và layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField tfBrandName = new TextField();
        tfBrandName.setPromptText("Enter brand name");
        TextField tfBrandID = new TextField();
        tfBrandID.setText(getLastIndexBrand() + "");
        tfBrandID.setDisable(true);
        tfBrandID.setMinWidth(300);
        tfBrandID.setMinHeight(30);
        tfBrandName.setMinWidth(300);
        tfBrandName.setMinHeight(30);

        grid.add(new Label("ID:"), 0, 0);
        grid.add(tfBrandID, 1, 0);
        grid.add(new Label("Brand Name:"), 0, 1);
        grid.add(tfBrandName, 1, 1);

        // Đặt DialogPane là layout vừa tạo
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setContent(grid);
        dialogPane.setMinWidth(400);
        dialogPane.setMinHeight(100);

        // Xử lý sự kiện khi người dùng nhấn OK
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new Pair<>(tfBrandID.getText(), tfBrandName.getText());
            }
            return null;
        });

        // Hiển thị dialog và chờ người dùng nhập liệu
        dialog.showAndWait().ifPresent(result -> {
            if (result == null || result.getValue() == null) {
                showAlert("Error", "Brand name is not be blank. Try again !");
                return;
            }

            String brandName = result.getValue().trim(); // Lấy tên thương hiệu từ dialog và xóa khoảng trắng thừa

            // Kiểm tra xem tên thương hiệu đã tồn tại trong danh sách hiện tại hay chưa
            for (BrandQ b : brandListofBC) {
                if (b.getBName().equalsIgnoreCase(brandName)) {
                    showAlert("Error", "Brand name already exists, please try again! ");
                    return;
                }
            }

            // Thêm vào cơ sở dữ liệu nếu tên thương hiệu không trùng và không rỗng
            if (!brandName.isEmpty()) {
                addDBofBC("brand", "brandname", brandName);
            } else {
                showAlert("Error", "Brand name is not be blank. Try again !");
            }
        });

    }

    @FXML
    private void btnEditBrand(MouseEvent event) {
        BrandQ selectedItem = tvBrandofBC.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert("Warning", "Please select a brand first!");
            return;
        }
        // Tạo dialog
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Edit Brand");

        // Nút OK (Thêm) và Cancel
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Tạo các trường nhập liệu và layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField tfBrandName = new TextField();
        tfBrandName.setText(selectedItem.getBName());
        TextField tfBrandID = new TextField();
        tfBrandID.setText(selectedItem.getBID() + "");
        tfBrandID.setDisable(true);
        tfBrandID.setMinWidth(300);
        tfBrandID.setMinHeight(30);
        tfBrandName.setMinWidth(300);
        tfBrandName.setMinHeight(30);

        grid.add(new Label("ID:"), 0, 0);
        grid.add(tfBrandID, 1, 0);
        grid.add(new Label("Brand Name:"), 0, 1);
        grid.add(tfBrandName, 1, 1);

        // Đặt DialogPane là layout vừa tạo
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setContent(grid);
        dialogPane.setMinWidth(400);
        dialogPane.setMinHeight(100);

        // Xử lý sự kiện khi người dùng nhấn OK
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new Pair<>(tfBrandID.getText(), tfBrandName.getText());
            }
            return null;
        });

        // Hiển thị dialog và chờ người dùng nhập liệu
        dialog.showAndWait().ifPresent(result -> {
            String brandName = result.getValue();

            // Kiểm tra xem tên thương hiệu đã tồn tại trong danh sách hoặc đã được chọn để chỉnh sửa chưa
            for (BrandQ b : brandListofBC) {
                if (b.getBName().equalsIgnoreCase(brandName) || (selectedItem != null && selectedItem.getBName().equalsIgnoreCase(brandName))) {
                    showAlert("Error", "Brand name already exists, please try again!");
                    return;
                }
            }

            // Kiểm tra tên thương hiệu không được rỗng
            if (brandName == null || brandName.isEmpty()) {
                showAlert("Error", "Brand name is not be blank. Try again !");
                return;
            }

            // Nếu điều kiện đều thỏa mãn, cập nhật vào cơ sở dữ liệu
            updateDBofBC("brand", "brandname", brandName, "brandid", selectedItem.getBID());
        });

    }

    @FXML
    private void btnAddCategory(MouseEvent event) {
        // Tạo dialog
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add New Category");

        // Nút OK (Thêm) và Cancel
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Tạo các trường nhập liệu và layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField tfBrandName = new TextField();
        tfBrandName.setPromptText("Enter category name");
        TextField tfBrandID = new TextField();
        tfBrandID.setText(getLastIndexCategory() + "");
        tfBrandID.setDisable(true);
        tfBrandID.setMinWidth(300);
        tfBrandID.setMinHeight(30);
        tfBrandName.setMinWidth(300);
        tfBrandName.setMinHeight(30);

        grid.add(new Label("ID:"), 0, 0);
        grid.add(tfBrandID, 1, 0);
        grid.add(new Label("Category Name:"), 0, 1);
        grid.add(tfBrandName, 1, 1);

        // Đặt DialogPane là layout vừa tạo
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setContent(grid);
        dialogPane.setMinWidth(400);
        dialogPane.setMinHeight(100);

        // Xử lý sự kiện khi người dùng nhấn OK
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new Pair<>(tfBrandID.getText(), tfBrandName.getText());
            }
            return null;
        });

        // Hiển thị dialog và chờ người dùng nhập liệu
        dialog.showAndWait().ifPresent(result -> {
            String categoryName = result.getValue();

            // Kiểm tra xem tên danh mục đã tồn tại trong danh sách hiện tại chưa
            for (CategoryQ c : categoryListofBC) {
                if (c.getCName().equalsIgnoreCase(categoryName)) {
                    showAlert("Error", "Category name already exists, please try again!");
                    return;
                }
            }

            // Kiểm tra tên danh mục không rỗng
            if (categoryName == null || categoryName.isEmpty()) {
                showAlert("Error", "Category name is not be blank. Try again !");
                return;
            }

            // Thêm vào cơ sở dữ liệu nếu tên danh mục là duy nhất và hợp lệ
            addDBofBC("category", "categoryname", categoryName);
        });

    }

    @FXML
    private void btnEditCategory(MouseEvent event) {
        CategoryQ selectedItem = tvCategoryofBC.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert("Warning", "Please select a category first!");
            return;
        }
        // Tạo dialog
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Edit Category");

        // Nút OK (Thêm) và Cancel
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Tạo các trường nhập liệu và layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField tfBrandName = new TextField();
        tfBrandName.setText(selectedItem.getCName());
        TextField tfBrandID = new TextField();
        tfBrandID.setText(selectedItem.getCID() + "");
        tfBrandID.setDisable(true);
        tfBrandID.setMinWidth(300);
        tfBrandID.setMinHeight(30);
        tfBrandName.setMinWidth(300);
        tfBrandName.setMinHeight(30);

        grid.add(new Label("ID:"), 0, 0);
        grid.add(tfBrandID, 1, 0);
        grid.add(new Label("Category Name:"), 0, 1);
        grid.add(tfBrandName, 1, 1);

        // Đặt DialogPane là layout vừa tạo
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setContent(grid);
        dialogPane.setMinWidth(400);
        dialogPane.setMinHeight(100);

        // Xử lý sự kiện khi người dùng nhấn OK
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new Pair<>(tfBrandID.getText(), tfBrandName.getText());
            }
            return null;
        });

        // Hiển thị dialog và chờ người dùng nhập liệu
        dialog.showAndWait().ifPresent(result -> {
            String categoryName = result.getValue();

            // Kiểm tra xem tên danh mục đã tồn tại trong danh sách hiện tại hoặc đã được chọn để chỉnh sửa chưa
            for (CategoryQ c : categoryListofBC) {
                if (c.getCName().equalsIgnoreCase(categoryName) || (selectedItem != null && selectedItem.getCName().equalsIgnoreCase(categoryName))) {
                    showAlert("Error", "Category name already exists, please try again!");
                    return;
                }
            }

            // Kiểm tra tên danh mục không rỗng
            if (categoryName == null || categoryName.isEmpty()) {
                showAlert("Error", "Category name is not be blank. Try again !");
                return;
            }

            // Cập nhật vào cơ sở dữ liệu nếu tên danh mục là duy nhất và hợp lệ
            updateDBofBC("category", "categoryname", categoryName, "categoryid", selectedItem.getCID());
        });

    }

    public void showAlert(String tit, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(tit);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public int getLastIndexBrand() {
        int index = 0;
        String sql = "SELECT Max(brandid) as id FROM brand";
        try {
            // Tạo statement
            stm = con.createStatement();
            // Truy vấn
            rs = stm.executeQuery(sql);

            if (rs.next()) {
                index = rs.getInt("id");
            }

        } catch (SQLException ex) {
            Logger.getLogger(BrandAndCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return index + 1;
    }

    public int getLastIndexCategory() {
        int index = 0;
        String sql = "SELECT Max(categoryid) as id FROM category";
        try {
            // Tạo statement
            stm = con.createStatement();
            // Truy vấn
            rs = stm.executeQuery(sql);

            if (rs.next()) {
                index = rs.getInt("id");
            }

        } catch (SQLException ex) {
            Logger.getLogger(BrandAndCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return index + 1;
    }

    public void addDBofBC(String table, String colName, String bName) {
        try {
            // Câu lệnh INSERT SQL
            String sql = "INSERT INTO " + table + " (" + colName + ") VALUES (?)";

            // Chuẩn bị câu lệnh PreparedStatement
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, bName);

            // Thực thi câu lệnh INSERT
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Message", "Add new successfully");
                // Thực hiện các thao tác cập nhật khác sau khi thêm mới thành công, ví dụ như làm mới dữ liệu
                getBrandofBC();
                getCategoryofBC();
            } else {
                showAlert("Error", "Error!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BrandAndCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateDBofBC(String table, String col, String value, String colID, int id) {
        try {
            String sql = "UPDATE " + table + " SET " + col + " = ? WHERE " + colID + " = ?";

            // Chuẩn bị câu lệnh PreparedStatement
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, value);
            pst.setInt(2, id);

            // Thực thi câu lệnh UPDATE
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Message", "Update successfully");
                getBrandofBC();
                getCategoryofBC();
            } else {
                showAlert("Error", "Error!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BrandAndCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void openBrandAndCategoryInterface(ActionEvent event) {
        pageBrandandCat.setVisible(true);
        BrandAndCategoryDialog = new Dialog<>();
        BrandAndCategoryDialog.getDialogPane().setContent(pageBrandandCat);
        BrandAndCategoryDialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        BrandAndCategoryDialog.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);
        BrandAndCategoryDialog.show();
    }

    @FXML
    private void btnViewChartSupplier(ActionEvent event) {

        double total = getSuppilers().stream()
                .mapToDouble(Suppiler::getTotal)
                .sum();

        VBox vbox = new VBox();
        vbox.setMinSize(500, 600);
        PieChart pieChart = new PieChart();
        Label lbl = new Label("Supplier statistics chart".toUpperCase());
        lbl.setStyle("-fx-font-weight: bold;-fx-font-size:20px");
//        pieChart.setTitle("Supplier statistics chart".toUpperCase());
        for (Suppiler s : getSuppilers()) {
            
            double percentage = (s.getTotal() / total) * 100;
            PieChart.Data slice = new PieChart.Data(s.getSuppilerName() + " (" + String.format("%.2f", percentage) + "%)", s.getTotal());
            pieChart.getData().add(slice);
        }
        vbox.getChildren().addAll(lbl, pieChart);
        vbox.setStyle("-fx-alignment: center");
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("View chart");
        dialog.getDialogPane().setContent(vbox);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
        dialog.getDialogPane().getButtonTypes().stream()
                .map(dialog.getDialogPane()::lookupButton)
                .forEach(button -> button.managedProperty().bind(button.visibleProperty()));

        dialog.showAndWait();
    }

}
