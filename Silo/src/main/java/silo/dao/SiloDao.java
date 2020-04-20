/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silo.dao;

import java.sql.*;
import java.util.ArrayList;
import silo.domain.Client;
import silo.domain.Grain;

import silo.domain.Silo;
import silo.domain.User;
import silo.ui.LogInViewController;
import static silo.ui.MainViewController.siloList;

/**
 *
 * @author Iizu
 */
public class SiloDao {

    private String url;
    private Connection db;
    private User user;

    public User getUser() {
        return user;
    }

    public SiloDao() {
        this.url = "jdbc:sqlite:silos.db";
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

    public void createTable(int columns, int rows) throws SQLException {

        Statement s = db.createStatement();

        s.execute("CREATE TABLE IF NOT EXISTS Silos (silo_id INTEGER PRIMARY KEY, "
                + "user TEXT, silo INTEGER, row INTEGER, column INTEGER, "
                + "client TEXT, crop TEXT, variety TEXT, volume INTEGER, production TEXT)");

        int k = 1;

        for (int i = 1; i <= rows; i++) {

            for (int j = 1; j <= columns; j++) {

                PreparedStatement p = db.prepareStatement("INSERT OR ABORT INTO "
                        + "Silos(user, silo, row, column, client, "
                        + "crop, variety, volume, production) "
                        + "VALUES (?,?,?,?,?,?,?,?,?)");

                p.setString(1, user.getName());
                p.setInt(2, k);
                p.setInt(3, i);
                p.setInt(4, j);
                p.setString(5, "");
                p.setString(6, "");
                p.setString(7, "");
                p.setInt(8, 0);
                p.setString(9, "");

                p.executeUpdate();

                k++;
            }
        }

        s.close();

    }

    public void create(Silo silo) throws SQLException {

        Statement s = db.createStatement();

        PreparedStatement p = db.prepareStatement("UPDATE Silos SET client = ?, crop = ?, variety = ?, volume= ?, production= ? WHERE user = '" + user.getName() + "' AND silo = " + silo.getIndex());

        p.setString(1, silo.getClient().getName());
        p.setString(2, silo.getGrain().getCrop());
        p.setString(3, silo.getGrain().getVariety());
        p.setInt(4, silo.getGrain().getVolume());
        p.setString(5, silo.getGrain().getProductionMethod());

        p.executeUpdate();

        s.close();
        p.close();
    }

    public void remove(Silo silo) throws SQLException {

        Statement s = db.createStatement();

        PreparedStatement p = db.prepareStatement("UPDATE Silos SET client = ?, crop = ?, variety = ?, volume= ?, production= ? WHERE user = '" + user.getName() + "' AND silo = " + silo.getIndex());

        p.setString(1, "");
        p.setString(2, "");
        p.setString(3, "");
        p.setInt(4, 0);
        p.setString(5, "");

        p.executeUpdate();

        s.close();
        p.close();
    }

    public Silo getSilo(int row, int column) throws SQLException {

        Statement stmt = db.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Silos WHERE user = '" + user.getName() + "' AND row = " + row + " AND column = " + column);

        Silo silo = new Silo();
        Grain grain = new Grain();

        silo.setClient(new Client(rs.getString("client")));

        if (silo.getClient().getName().isBlank()) {
            silo.setClient(null);
        }

        silo.setGrain(grain);

        grain.setCrop(rs.getString("crop"));
        grain.setVolume(rs.getInt("volume"));

        stmt.close();
        rs.close();

        return silo;
    }

    public ArrayList<Silo> findSilos(Client client) throws SQLException {

        ArrayList list = new ArrayList<Silo>();
        
        PreparedStatement stmt = db.prepareStatement("SELECT silo FROM Silos WHERE user = '" + user.getName() + "' AND client = '" + client.getName() + "'");

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            return null;
        } else {
            list.add(siloList.get(rs.getInt("silo") - 1));
        }

        stmt.close();
        rs.close();

        return list;
    }

    public boolean hasMap() {

        Statement stmt;

        try {
            stmt = db.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT silo FROM Silos WHERE user = '" + user.getName() + "' AND silo = 1");

            if (rs.getInt("silo") == 0) {
                return false;
            }

            stmt.close();
            rs.close();

        } catch (SQLException ex) {
            return false;
        }

        return true;
    }

    public int getColumns() throws SQLException {

        Statement stmt = db.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Silos WHERE user = '" + user.getName() + "' AND row = 1");

        int i = rs.getInt(1);

        rs.close();
        stmt.close();

        return i;

    }

    public int getRows() throws SQLException {

        Statement stmt = db.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Silos WHERE user = '" + user.getName() + "' AND column = 1");

        int i = rs.getInt(1);

        rs.close();
        stmt.close();

        return i;
    }
}
