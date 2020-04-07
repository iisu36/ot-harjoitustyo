/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silo.ui;

/**
 *
 * @author Iizu
 */
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

    @FXML
    private void handleLogIn(ActionEvent event) throws SQLException, IOException {

        String username = userNameField.getText().toLowerCase();
        String password = passWordField.getText().toLowerCase();

        UserDao userDao = new UserDao();

        user = userDao.findUser(username);

        if (user == null) {
            userNameField.setText("");
            passWordField.setText("");
            error.setText("Create your account first!");

        } else {
            user = userDao.isLogInOK(username, password);
            
            SiloDao siloDao = new SiloDao();
            

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

    @FXML
    private void handleNewUser(ActionEvent event) throws IOException {

        SiloUi.setRoot("CreateUser");

    }
}
