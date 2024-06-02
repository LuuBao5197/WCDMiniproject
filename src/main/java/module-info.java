module group2.g2store {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens group2.g2store to javafx.fxml;
    exports group2.g2store;
}
