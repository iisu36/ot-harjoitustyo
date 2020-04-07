package silo.ui;

import silo.dao.SiloDao;

import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class CreateMapController {

    public static int row;
    public static int column;

    public SiloDao siloDao;

    @FXML
    public TextField rows;
    public TextField columns;

    @FXML
    private void switchToMainView() throws IOException, SQLException {

        if (columns.getText().matches("[0-9]+") && rows.getText().matches("[0-9]+")) {

            this.column = Integer.parseInt(columns.getText());
            this.row = Integer.parseInt(rows.getText());

            siloDao = new SiloDao();

            siloDao.createTable(column, row);

            SiloUi.setRoot("MainView");
        }
    }

    public int getRows() {
        return Integer.parseInt(rows.getText());
    }

    public int getColumns() {
        return Integer.parseInt(columns.getText());
    }

}
