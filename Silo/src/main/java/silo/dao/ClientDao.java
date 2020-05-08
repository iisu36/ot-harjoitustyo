/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silo.dao;

import java.sql.*;
import silo.domain.Client;
import silo.domain.User;
import silo.ui.LogInViewController;

/**
 *
 * @author Iizu
 */
public class ClientDao {

    private String url;
    private Connection db;
    private User user;

    public User getUser() {
        return user;
    }

    public ClientDao(String url) {
        
        this.url = "jdbc:sqlite:" + url + ".db";
        this.db = createConnection();

        this.user = LogInViewController.user;
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

    public void createTable() throws SQLException {

        Statement s = db.createStatement();

        s.execute("CREATE TABLE IF NOT EXISTS Clients (client_id INTEGER PRIMARY KEY, "
                + "user TEXT, name TEXT, phone TEXT, address TEXT)");

        s.close();
    }

    public void create(Client client) throws SQLException {

        Statement s = db.createStatement();

        PreparedStatement p = db.prepareStatement("INSERT OR ABORT INTO Clients "
                + "(user, name, phone, address) VALUES (?,?,?,?)");

        p.setString(1, user.getName());
        p.setString(2, client.getName());
        p.setString(3, client.getPhone());
        p.setString(4, client.getAddress());

        p.executeUpdate();

        s.close();
        p.close();
    }

    public void remove(Client poistettava) throws SQLException {
        
        int id = getClientId(poistettava);

        Statement s = db.createStatement();

        s.execute("DELETE FROM Clients WHERE user = '" + user.getName() + "' AND client_id = " + id);

        s.close();
    }

    

    public int getClientId(Client client) throws SQLException {
        
        PreparedStatement stmt = db.prepareStatement("SELECT client_id FROM Clients WHERE user = '" + user.getName() + "' AND name = '" + client.getName() + "'");

        ResultSet rs = stmt.executeQuery();
        
        int id = rs.getInt("client_id");
        
        stmt.close();
        rs.close();

        return id;
    }

    public String list() throws SQLException {

        Statement stmt = db.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT name FROM Clients WHERE user = '" + user.getName() + "'");

        String list = "";
        
        while (rs.next()) {
            list += rs.getString("name") + "\n";
        }

        stmt.close();
        rs.close();

        return list;
    }
}
