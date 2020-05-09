package silo.dao;

import java.sql.*;
import silo.domain.Client;
import silo.domain.User;
import silo.ui.LogInViewController;

/**
 * @author Iisakki
 * @version Viikko 7
 *
 * This class controls the clientdatabase.
 */
public class ClientDao {

    private String url;
    private Connection db;
    private User user;

    public ClientDao(String url) {

        this.url = "jdbc:sqlite:" + url + ".db";
        this.db = createConnection();

        this.user = LogInViewController.user;
    }

    public ClientDao(String url, User user) {

        this.url = "jdbc:sqlite:" + url + ".db";
        this.db = createConnection();

        this.user = user;
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
     * Creates the "Clients" table to the database.
     *
     * A client has a user, name, phonenumber and an address.
     *
     * @throws SQLException Exception.
     */
    public void createTable() throws SQLException {

        Statement s = db.createStatement();

        s.execute("CREATE TABLE IF NOT EXISTS Clients (client_id INTEGER PRIMARY KEY, "
                + "user TEXT, name TEXT, phone TEXT, address TEXT)");

        s.close();
    }

    /**
     * Inserts a new client to the database.
     *
     * A client has a user, name, phonenumber and an address.
     *
     * @param client The client to be inserted to the database.
     * @throws SQLException Exception.
     */
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

    /**
     * Removes a client from the database.
     *
     * @param removable The client to be removed.
     * @throws SQLException Exception.
     */
    public void remove(Client removable) throws SQLException {

        int id = getClientId(removable);

        Statement s = db.createStatement();

        s.execute("DELETE FROM Clients WHERE user = '" + user.getName()
                + "' AND client_id = " + id);

        s.close();
    }

    /**
     * Searches for the selected client's id-index in the database.
     *
     * Id-index is the primary key in the Clients-table.
     *
     * @param client The selected client.
     * @return The id of the selected client.
     * @throws SQLException Exception.
     */
    public int getClientId(Client client) throws SQLException {

        PreparedStatement stmt = db.prepareStatement("SELECT client_id FROM Clients "
                + "WHERE user = '" + user.getName() + "' AND name = '"
                + client.getName() + "'");

        ResultSet rs = stmt.executeQuery();

        int id = rs.getInt("client_id");

        stmt.close();
        rs.close();

        return id;
    }
}
