module g2store {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    
    opens group2.g2store to javafx.fxml;
    exports group2.g2store;

    requires javafx.controlsEmpty;
    requires javafx.fxmlEmpty;
    requires com.microsoft.sqlserver.jdbc;
    requires org.apache.poi.ooxml;
    requires org.apache.poi.poi;
    requires org.apache.logging.log4j;
requires org.apache.commons.lang3;
    requires org.apache.pdfbox;
    
}
