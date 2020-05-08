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

/**
 * @author Iisakki
 * @version Viikko 6
 *
 * This class controls the userdatabase.
 */
public class UserDao {

    public String url;
    public Connection db;

    public UserDao(String url) {

        this.url = "jdbc:sqlite:" + url + ".db";
        this.db = createConnection();
    }

    private Connection createConnection() {

        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {

        return db;
    }

    public void stopConnection() throws SQLException {

        db.close();
    }

    /**
     * Creates the users table to the userdatabase.
     *
     * A user has a username and a password.
     *
     * @throws SQLException Exception.
     */
    public void createTable() throws SQLException {

        Statement s = db.createStatement();

        s.execute("CREATE TABLE IF NOT EXISTS Users (user_id INTEGER PRIMARY KEY, "
                + "username TEXT UNIQUE, password TEXT)");

        s.close();
    }

    /**
     * Creates a new user to the database.
     *
     * @param user The user which username and password will be saved to
     * database.
     * @throws SQLException Exception.
     */
    public void create(User user) throws SQLException {

        createTable();

        Statement s = db.createStatement();

        try {
            PreparedStatement p = db.prepareStatement("INSERT OR ABORT INTO "
                    + "Users(username, password) VALUES (?,?)");
            p.setString(1, user.getName());
            p.setString(2, user.getPassword());

            p.executeUpdate();

        } catch (SQLException e) {
        }

        s.close();
    }

    /**
     * Searches the user from the database.
     *
     * Returns the user's name and password if found, if not, null.
     *
     * @param username The user's name that is searched for.
     * @return User with the corresponding password. Or null.
     * @throws SQLException Exception.
     */
    public User findUser(String username) throws SQLException {

        createTable();

        PreparedStatement stmt = db.prepareStatement("SELECT * FROM Users WHERE "
                + "username = ?");

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

    /**
     * Checks if the the username and the password matches eachother.
     *
     * If not, returns null, and if they do, returns the user.
     *
     * @param username Username in the database.
     * @param passw Username's corresponding password in the database.
     * @return Logging in user or null.
     * @throws SQLException Exception.
     */
    public User isLogInOK(String username, String passw) throws SQLException {

        PreparedStatement stmt = db.prepareStatement("SELECT * FROM Users WHERE "
                + "username =? AND password =?");
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
