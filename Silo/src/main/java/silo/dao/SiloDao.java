package silo.dao;

import java.sql.*;
import java.util.ArrayList;
import silo.domain.Client;
import silo.domain.Grain;

import silo.domain.Silo;
import silo.domain.User;
import silo.ui.LogInViewController;
import static silo.ui.MainViewController.siloList;
import static silo.ui.MainViewController.clientList;

/**
 * @author Iisakki
 * @version Viikko 7
 *
 * This class controls the silodatabase.
 */
public class SiloDao {

    private String url;
    private Connection db;
    private User user;

    public SiloDao(String url) {

        this.url = "jdbc:sqlite:" + url + ".db";
        this.db = createConnection();

        this.user = LogInViewController.user;
    }

    public SiloDao(String url, User user) {

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
     * Creates the "Silos" table to the database.
     *
     * A silo has a user, silonumber, row and column index, client and a grain.
     * The grain consists of crop, variety, volume and production method.
     *
     * @param columns The number of columns in the silomap.
     * @param rows The number of rows in the silomap.
     * @throws SQLException Exception.
     *
     * @see silo.dao.SiloDao#insertTable(int, int)
     */
    public void createTable(int columns, int rows) throws SQLException {

        Statement s = db.createStatement();

        s.execute("CREATE TABLE IF NOT EXISTS Silos (silo_id INTEGER PRIMARY KEY, "
                + "user TEXT, silo INTEGER, row INTEGER, column INTEGER, "
                + "client TEXT, crop TEXT, variety TEXT, volume INTEGER, production TEXT)");

        s.close();

        insertTable(columns, rows);
    }

    /**
     * Inserts base values to the silos in the table.
     *
     * Works for the createTable-method.
     *
     * @param columns The number of columns in the silomap.
     * @param rows The number of rows in the silomap.
     * @throws SQLException Exception.
     */
    public void insertTable(int columns, int rows) throws SQLException {

        int k = 1;

        for (int i = 1; i <= rows; i++) {

            for (int j = 1; j <= columns; j++, k++) {

                PreparedStatement p = db.prepareStatement("INSERT OR ABORT INTO "
                        + "Silos(user, silo, row, column, client, crop, variety, "
                        + "volume, production) VALUES (?,?,?,?,?,?,?,?,?)");

                p.setString(1, user.getName()); //user
                p.setInt(2, k); //silo
                p.setInt(3, i); //row
                p.setInt(4, j); //column
                p.setString(5, ""); //client        
                p.setString(6, ""); //crop
                p.setString(7, ""); //variety
                p.setInt(8, 0); // volume
                p.setString(9, ""); //productionmethod

                p.executeUpdate();
            }
        }
    }

    /**
     * Updates silo's information in the database.
     *
     * A silo has a user, silonumber, row and column index, client and a grain.
     * The grain consists of crop, variety, volume and production method.
     *
     * @param silo The selected silo to be updated.
     * @throws SQLException Exception.
     */
    public void create(Silo silo) throws SQLException {

        PreparedStatement p = db.prepareStatement("UPDATE Silos SET client = ?, "
                + "crop = ?, variety = ?, volume= ?, production= ? WHERE user = '"
                + user.getName() + "' AND silo = " + silo.getIndex());

        p.setString(1, silo.getClient().getName());
        p.setString(2, silo.getGrain().getCrop());
        p.setString(3, silo.getGrain().getVariety());
        p.setInt(4, silo.getGrain().getVolume());
        p.setString(5, silo.getGrain().getProductionMethod());

        p.executeUpdate();

        p.close();
    }

    /**
     * Returns silo's information to basevalues.
     *
     * @param silo The selected silo to be updated.
     * @throws SQLException Exception.
     */
    public void remove(Silo silo) throws SQLException {

        PreparedStatement p = db.prepareStatement("UPDATE Silos SET client = ?, "
                + "crop = ?, variety = ?, volume= ?, production= ? WHERE user = '"
                + user.getName() + "' AND silo = " + silo.getIndex());

        p.setString(1, "");
        p.setString(2, "");
        p.setString(3, "");
        p.setInt(4, 0);
        p.setString(5, "");

        p.executeUpdate();

        p.close();
    }

    /**
     * Searches the silo from the database.
     *
     * Returns the silo based on row and column index.
     *
     * @param column The column index of the selected silo.
     * @param row The rox index of the selected silo.
     * @return The silo based on row and column index.
     * @throws SQLException Exception.
     */
    public Silo getSilo(int row, int column) throws SQLException {

        Statement stmt = db.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Silos WHERE user = '"
                + user.getName() + "' AND row = " + row + " AND column = " + column);

        Silo silo = new Silo();
        Grain grain = new Grain();

        String clientName = rs.getString("client");

        silo = checkClient(silo, clientName);

        grain.setCrop(rs.getString("crop"));
        grain.setVariety(rs.getString("variety"));
        grain.setProductionMethod(rs.getString("production"));
        grain.setVolume(rs.getInt("volume"));

        silo.setGrain(grain);

        stmt.close();
        rs.close();

        return silo;
    }

    /**
     * Sets an exixting or a new client to a silo.
     *
     * Goes through existing clients from a list and checks if the client is old
     * or new, and sets a corresponding client for a silo.
     *
     * @param silo The selected silo.
     * @param name The name of the client in search.
     * @return The selected silo with updated client.
     */
    public Silo checkClient(Silo silo, String name) {

        if (!clientList.isEmpty()) {

            for (Client listClient : clientList) {

                if (listClient.getName().equals(name)) {

                    silo.setClient(listClient);
                    break;
                }
            }

            silo.setClient(new Client(name));

        } else {

            silo.setClient(new Client(name));
        }

        return silo;
    }

    /**
     * Lists all the silos the selected client is using.
     *
     * @param client The selected client.
     * @return List of all the client's silos or null, if the client has no
     * silos.
     * @throws SQLException Exception.
     */
    public ArrayList<Silo> findSilos(Client client) throws SQLException {

        ArrayList list = new ArrayList<Silo>();

        PreparedStatement stmt = db.prepareStatement("SELECT silo FROM Silos WHERE "
                + "user = '" + user.getName() + "' AND client = '" + client.getName() + "'");

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

    /**
     * Checks if the user has created a silomap.
     *
     * @return True if user has a silomap, false if not.
     */
    public boolean hasMap() {

        Statement stmt;

        try {
            stmt = db.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT silo FROM Silos WHERE user = '"
                    + user.getName() + "' AND silo = 1");

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

    /**
     * Counts the columns in the silomap.
     *
     * @return The column count in the silomap.
     * @throws SQLException Exception.
     */
    public int getColumns() throws SQLException {

        Statement stmt = db.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Silos WHERE user = '"
                + user.getName() + "' AND row = 1");

        int i = rs.getInt(1);

        rs.close();
        stmt.close();

        return i;
    }

    /**
     * Counts the rows in the silomap.
     *
     * @return The row count in the silomap.
     * @throws SQLException Exception.
     */
    public int getRows() throws SQLException {

        Statement stmt = db.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Silos WHERE user = '"
                + user.getName() + "' AND column = 1");

        int i = rs.getInt(1);

        rs.close();
        stmt.close();

        return i;
    }
}
