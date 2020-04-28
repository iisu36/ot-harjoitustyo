/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author  Iisakki
 * @version Viikko6
 * 
 * This class controls the creating of a new user and switching back to login-view.
 */
public class CreateUserController {

    private UserDao dao;

    private User p;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField passWordField;

    @FXML
    private Label error;

    @FXML
    private Label userNameLabel;

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
     * @throws IOException  Exception.
     */
    @FXML
    private void handleCreate(ActionEvent event) throws SQLException, IOException {

        UserDao d = new UserDao();

        String name = userNameField.getText();

        if (name.length() < 3) {
            // usernameLabel.setText("Username: (must contain at least 3 letters)");
            error.setText("Practice your reading skills....");
            return;
        }

        if (d.findUser(userNameField.getText()) != null) {
            error.setText("Oops! This username is already registered");
            return;

        } else {

            User p = new User(userNameField.getText().toLowerCase(), passWordField.getText().toLowerCase());

            d.create(p);
            userNameField.setText("");
            passWordField.setText("");
            //    usernameLabel.setText("Username: (must contain at least 3 letters)");
            error.setText("");

            SiloUi.setRoot("LogInView");

        }
    }
}
