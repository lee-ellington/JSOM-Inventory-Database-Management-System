module com.ellington {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.ellington to javafx.fxml;
    exports com.ellington;
}
