package silo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import silo.domain.Client;
import silo.domain.Silo;
import silo.domain.User;
import static silo.ui.MainViewController.siloList;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Iizu
 */
public class SiloDaoTest {

    Connection connection;
    SiloDao dao;
    User user;
    Client client1;
    Client client2;
    Silo silo1;
    Silo silo2;

    @Before
    public void setUp() throws Exception {

        user = new User("testi", "salasana");

        connection = DriverManager.getConnection("jdbc:sqlite:silos.db");

        Statement stmt = connection.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS Silos (silo_id INTEGER PRIMARY KEY, "
                + "user TEXT, silo INTEGER, row INTEGER, column INTEGER, "
                + "client TEXT, crop TEXT, variety TEXT, volume INTEGER, production TEXT)");

        PreparedStatement p = connection.prepareStatement("INSERT OR ABORT INTO "
                + "Silos(user, silo, row, column, client, "
                + "crop, variety, volume, production) "
                + "VALUES (?,?,?,?,?,?,?,?,?)");

        p.setString(1, user.getName());
        p.setInt(2, 1);
        p.setInt(3, 2);
        p.setInt(4, 3);
        p.setString(5, "Mikko Mallikas");
        p.setString(6, "Crop");
        p.setString(7, "Variety");
        p.setInt(8, 600);
        p.setString(9, "TV");

        p.executeUpdate();
        
        PreparedStatement s = connection.prepareStatement("INSERT OR ABORT INTO "
                + "Silos(user, silo, row, column, client, "
                + "crop, variety, volume, production) "
                + "VALUES (?,?,?,?,?,?,?,?,?)");

        s.setString(1, user.getName());
        s.setInt(2, 4);
        s.setInt(3, 5);
        s.setInt(4, 6);
        s.setString(5, "Maija Meikäläinen");
        s.setString(6, "Vilja");
        s.setString(7, "Lajike");
        s.setInt(8, 1200);
        s.setString(9, "Luomu");

        s.executeUpdate();
        
        ArrayList<Silo> list = new ArrayList<>();
        
        list.add(0, new Silo());
        list.add(1, new Silo());
        list.add(2, new Silo());
        list.add(3, new Silo());
        
        siloList = list;

        dao = new SiloDao(user);
        
        client1 = new Client("Mikko Mallikas");
        client2 = new Client("Maija Meikäläinen");
        
        silo1 = new Silo();
        silo2 = new Silo();
    }

    @Test
    public void listingSilosCorrectly() throws SQLException {
        ArrayList<Silo> silos1 = dao.findSilos(client1);
        assertEquals(1, silos1.size());
        ArrayList<Silo> silos2 = dao.findSilos(client2);
        assertEquals(1, silos2.size());
        ArrayList<Silo> silos3 = dao.findSilos(new Client("Tommi Testaaja"));
        assertEquals(null, silos3);
    }
    
    @Test
    public void listingNonExistingSilosCorrectly() throws SQLException {
        ArrayList<Silo> silos3 = dao.findSilos(new Client("Tommi Testaaja"));
        assertEquals(null, silos3);
    }

    @Test
    public void siloFound() throws SQLException {        
        silo1 = dao.getSilo(2, 3);
        silo2 = dao.getSilo(5, 6);
        
        assertEquals("Mikko Mallikas", silo1.getClient().getName());
        assertEquals("Crop", silo1.getGrain().getCrop());
        assertEquals(600, silo1.getGrain().getVolume());
        
        assertEquals("Maija Meikäläinen", silo2.getClient().getName());
        assertEquals("Vilja", silo2.getGrain().getCrop());
        assertEquals(1200, silo2.getGrain().getVolume());
    }
    
    @Test
    public void gettingColumnsWorks() throws SQLException {
        assertEquals(0, dao.getColumns());
    }
    
    @Test
    public void gettingRowsWorks() throws SQLException {
        assertEquals(0, dao.getRows());
    }

    @After
    public void tearDown() throws SQLException {
        Statement s = connection.createStatement();

        s.execute("DROP TABLE Silos");

        connection.close();
    }

}
