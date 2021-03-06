package silo.ui;

import java.io.IOException;
import silo.dao.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import silo.domain.User;

/**
 * @author Iisakki
 * @version Viikko 6
 *
 * This class controls the logging in -action.
 */
public class LogInViewController implements Initializable {

    public static User user;

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passWordField;

    @FXML
    private Label error;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Handles logging in.
     *
     * Guides user to log in correctly: if username is wrong -> create an
     * account if password is wrong -> wrong password. After succesful log in,
     * switches to 1. mainview, if the user is not new or 2. to createmap-view,
     * if the user is new.
     *
     * @param event ActionEvent that triggers this method (pressing log in)
     * @throws SQLException Exception.
     * @throws IOException Exception.
     */
    @FXML
    private void handleLogIn(ActionEvent event) throws SQLException, IOException {

        String username = userNameField.getText().toLowerCase();
        String password = passWordField.getText().toLowerCase();

        UserDao userDao = new UserDao("users");

        user = userDao.findUser(username);

        if (user == null) {
            userNameField.setText("");
            passWordField.setText("");
            error.setText("Create your account first!");

        } else {
            user = userDao.isLogInOK(username, password);

            SiloDao siloDao = new SiloDao("silos");

            if (user != null) {

                if (siloDao.hasMap()) {

                    SiloUi.setRoot("MainView");

                } else {

                    SiloUi.setRoot("CreateMap");

                }

            } else {
                error.setText("Wrong password!");
            }
        }
    }

    /**
     * Switches to createuser-view where a new user can be made.
     *
     * @param event ActionEvent that triggers this method (pressing create user)
     * @throws IOException Exception.
     */
    @FXML
    private void handleNewUser(ActionEvent event) throws IOException {

        SiloUi.setRoot("CreateUser");

    }
}
