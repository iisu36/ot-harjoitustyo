package silo.ui;

import silo.dao.UserDao;
import silo.domain.User;

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author Iisakki
 * @version Viikko6
 *
 * This class controls the creating of a new user and switching back to
 * login-view.
 */
public class CreateUserController {

    @FXML
    private TextField userNameField;

    @FXML
    private TextField passWordField;

    @FXML
    private Label error;

    /**
     * Switches the view back to login-view.
     *
     * @param event ActionEvent that triggers this method (pressing back)
     * @throws IOException Exception.
     */
    @FXML
    private void handleBack(ActionEvent event) throws IOException {

        userNameField.setText("");
        passWordField.setText("");
        error.setText("");

        SiloUi.setRoot("LogInView");
    }

    /**
     * Creates user to database and switches to login-view.
     *
     * @param event ActionEvent that triggers this method (pressing create)
     * @throws SQLException Exception.
     * @throws IOException Exception.
     */
    @FXML
    private void handleCreate(ActionEvent event) throws SQLException, IOException {

        UserDao dao = new UserDao("users");

        String name = userNameField.getText();
        String password = passWordField.getText();

        if (name.length() < 3 || password.length() < 3) {

            error.setText("Must contain >2 characters");
            return;
        }

        if (dao.findUser(userNameField.getText()) != null) {

            error.setText("This username is already registered");
            return;

        } else {

            User user = new User(userNameField.getText().toLowerCase(),
                    passWordField.getText().toLowerCase());

            dao.create(user);
            userNameField.setText("");
            passWordField.setText("");
            error.setText("");

            SiloUi.setRoot("LogInView");
        }
    }
}
