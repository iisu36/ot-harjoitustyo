package silo;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PrimaryController {

    public static int row;
    public static int column;

    @FXML
    public TextField rows;
    public TextField columns;

    @FXML
    private void switchToSecondary() throws IOException {

        if (rows.getText().matches("[0-9]+") && columns.getText().matches("[0-9]+")) {

            this.row = Integer.parseInt(rows.getText());
            this.column = Integer.parseInt(columns.getText());
            
            App.setRoot("secondary");
        }
    }
}
