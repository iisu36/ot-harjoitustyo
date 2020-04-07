/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silo.dao;

import silo.domain.User;

import java.sql.*;

/**
 *
 * @author Iizu
 */
public class UserDao {

    private static String URL = "jdbc:sqlite:testsql.db";
    private static Connection db = createConnection();

    public UserDao() {
        this.db = db;
        this.URL = URL;
    }

    public UserDao(String URL) {
        this.URL = URL; //TESTEJÄ VARTEN, ETTEI OIKEA TIETOKANTA SEKOTU
        this.db = db;
    }

    private static Connection createConnection() {
        try {
            return DriverManager.getConnection(URL);
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

        s.execute("CREATE TABLE IF NOT EXISTS Users (user_id INTEGER PRIMARY KEY, username TEXT unique, password TEXT)");

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

    public User read(Integer key) throws SQLException {

        return null;
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

    public User update(User user) throws SQLException {
        return null;
    }

    public void list() throws SQLException {
        
        Statement stmt = db.createStatement();
        
        ResultSet rs = stmt.executeQuery("SELECT username, password FROM Users");
        
        
        
        while (rs.next()) {
            System.out.println(rs.getString("username") + " " + rs.getString("password"));
        }
        
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

