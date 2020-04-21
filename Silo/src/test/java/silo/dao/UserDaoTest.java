package silo.dao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Iizu
 */
import java.sql.*;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import static org.junit.Assert.*;
import silo.domain.User;

public class UserDaoTest {

    Connection connection;
    UserDao dao;
    User user;

    @Before
    public void setUp() throws Exception {

        connection = DriverManager.getConnection("jdbc:sqlite:users.db");

        Statement stmt = connection.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS "
                + "Users (user_id INTEGER PRIMARY KEY, username TEXT UNIQUE, password TEXT)");

        Statement s = connection.createStatement();
        s.execute("INSERT INTO Users (username, password) VALUES ('testi', 'salasana')");

        dao = new UserDao();
    }

    @Test
    public void listingUsersCorrectly() throws SQLException {
        ArrayList<String> users = dao.list();
        assertEquals(1, users.size());
        String user = users.get(0);
        assertEquals("testi", user);
    }

    @Test
    public void existingUserIsFound() throws SQLException {
        User user = dao.findUser("testi");
        assertEquals("testi", user.getName());
    }
    
    @Test
    public void nonExistingUserIsFound() throws SQLException {
        User user = dao.findUser("matti");
        assertEquals(null, user);
    }
  
    @Test
    public void savedUserIsFound() throws Exception {
        User newUser = new User("matti", "meikalainen");
        dao.create(newUser);
        
        User user = dao.findUser("matti");
        assertEquals("matti", user.getName());
        assertEquals("meikalainen", user.getPassword());
    }
    
    @Test
    public void logInWorksCorrectly() throws Exception {
        User user = new User("matti", "meikalainen");
        dao.create(user);
        
        User logInOk = dao.isLogInOK(user.getName(), user.getPassword());
        
        assertEquals(user, logInOk);
    }
    
    @Test
    public void logInWorksCorrectlyWithNonUser() throws Exception {
        User user = new User("matti", "meikalainen");
        
        User logInOk = dao.isLogInOK(user.getName(), user.getPassword());
        
        assertEquals(null, logInOk);
    }
    
    @After
    public void tearDown() throws SQLException {
        Statement s = connection.createStatement();
        
        s.execute("DROP TABLE Users");
        
        connection.close();
    }
}
