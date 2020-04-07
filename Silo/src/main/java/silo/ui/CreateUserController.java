/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silo.ui;

import silo.dao.UserDao;
import silo.domain.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Iizu
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

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        userNameField.setText("");
        passWordField.setText("");
        error.setText("");
        
        SiloUi.setRoot("LogInView");
    }

    @FXML
    private void handleCreate(ActionEvent event) throws SQLException, IOException {

        UserDao d = new UserDao();

        String name = userNameField.getText();

        if(name.length() < 3){
           // usernameLabel.setText("Username: (must contain at least 3 letters) !!!!!!!!!!!!!");
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

    public void initialize(URL location, ResourceBundle resources) {

    }
}
