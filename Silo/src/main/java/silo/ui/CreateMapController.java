package silo.ui;

import silo.dao.SiloDao;
import silo.dao.ClientDao;

import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author Iisakki
 * @version Viikko 6
 *
 * This class controls the creating of a silomap into database with users given
 * values and switches view to mainview.
 */
public class CreateMapController {

    public static int row;
    public static int column;

    public SiloDao siloDao;
    public ClientDao clientDao;

    @FXML
    public TextField rows;
    public TextField columns;
    public Label error;

    /**
     * Creates a silotable into database with users given values (row and
     * column), creates an empty table into client-database and switches view to
     * mainview.
     *
     * @throws IOException Exception.
     * @throws SQLException Exception.
     */
    @FXML
    private void switchToMainView() throws IOException, SQLException {

        if (columns.getText().matches("[1-9]") && rows.getText().matches("[1-9]")) {

            this.column = Integer.parseInt(columns.getText());
            this.row = Integer.parseInt(rows.getText());

            siloDao = new SiloDao("silos");
            siloDao.createTable(column, row);

            clientDao = new ClientDao("clients");
            clientDao.createTable();

            SiloUi.setRoot("MainView");
        } else {

            error.setText("Only numbers from 1 to 9");
        }
    }

    /**
     *
     * @return Integer-value describing how many rows.
     */
    public int getRows() {
        return Integer.parseInt(rows.getText());
    }

    /**
     *
     * @return Integer-value describing how many rows.
     */
    public int getColumns() {
        return Integer.parseInt(columns.getText());
    }

}
