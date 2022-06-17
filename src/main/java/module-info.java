module main.lab9 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javax.persistence;
    requires java.sql;


    opens main.lab9 to javafx.fxml;
    exports main.lab9;
}