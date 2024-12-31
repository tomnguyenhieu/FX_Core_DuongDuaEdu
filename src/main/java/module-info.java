module com.edu.duongdua.core.fx_core_duongduaedu {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.ooxml;
    requires org.apache.xmlbeans;
    requires java.desktop;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.apache.commons.compress;
    requires org.slf4j;

    exports com.edu.duongdua.core to javafx.graphics;
}