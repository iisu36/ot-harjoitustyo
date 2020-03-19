module silo {
    requires javafx.controls;
    requires javafx.fxml;

    opens silo to javafx.fxml;
    exports silo;
}