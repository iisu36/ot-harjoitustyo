/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silo.dao;

import silo.domain.User;

import java.sql.*;
import java.util.ArrayList;
import silo.domain.Silo;
import static silo.ui.MainViewController.siloList;

/**
 *
 * @author Iizu
 */
public class UserDao {

    private static String url = "jdbc:sqlite:users.db";
    private static Connection db = createConnection();

    public UserDao() {
        this.db = db;
        this.url = url;
    }

    public UserDao(String url) {
        this.url = url;
        this.db = db;
    }

    private static Connection createConnection() {
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return db;
    }

    public static void stopConnection() throws SQLException {
        db.close();

    }

    public void createTable() throws SQLException {

        Statement s = db.createStatement();

        s.execute("CREATE TABLE IF NOT EXISTS Users (user_id INTEGER PRIMARY KEY, username TEXT UNIQUE, password TEXT)");

        s.close();

    }

    public void create(User user) throws SQLException {

        createTable();

        Statement s = db.createStatement();

        try {
            PreparedStatement p = db.prepareStatement("INSERT OR ABORT INTO Users(username, password) VALUES (?,?)");
            p.setString(1, user.getName());
            p.setString(2, user.getPassword());

            p.executeUpdate();

        } catch (SQLException e) {
        }
        s.close();
    }

    public User findUser(String username) throws SQLException {

        createTable();

        PreparedStatement stmt = db.prepareStatement("SELECT * FROM Users WHERE username =?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            return null;
        }
        User pp = new User(rs.getString("username"),
                rs.getString("password"));

        stmt.close();
        rs.close();

        return pp;
    }

    public ArrayList<String> list() throws SQLException {
        
        ArrayList list = new ArrayList<Silo>();
        
        PreparedStatement stmt = db.prepareStatement("SELECT username FROM Users");

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            return null;
        } else {
            list.add(rs.getString("username"));
        }

        stmt.close();
        rs.close();

        return list;
        
    }

    public User isLogInOK(String username, String passw) throws SQLException {

        PreparedStatement stmt = db.prepareStatement("SELECT * FROM Users WHERE username =? AND password =?");
        stmt.setString(1, username);
        stmt.setString(2, passw);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            return null;
        }
        User okay = new User(rs.getString("username"),
                rs.getString("password"));

        stmt.close();
        rs.close();

        return okay;
    }
}

