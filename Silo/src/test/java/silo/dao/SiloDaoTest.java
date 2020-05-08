package silo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import silo.domain.Client;
import silo.domain.Silo;
import silo.domain.User;
import static silo.ui.MainViewController.siloList;
import static silo.ui.MainViewController.clientList;


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
        dao = new SiloDao("testsilos", user);

        connection = dao.getConnection();

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
        
        PreparedStatement t = connection.prepareStatement("INSERT OR ABORT INTO "
                + "Silos(user, silo, row, column, client, "
                + "crop, variety, volume, production) "
                + "VALUES (?,?,?,?,?,?,?,?,?)");

        t.setString(1, user.getName());
        t.setInt(2, 99);
        t.setInt(3, 9);
        t.setInt(4, 9);
        t.setString(5, "");
        t.setString(6, "");
        t.setString(7, "");
        t.setInt(8, 0);
        t.setString(9, "");

        t.executeUpdate();
        
        ArrayList<Silo> list = new ArrayList<>();
        
        list.add(0, new Silo());
        list.add(1, new Silo());
        list.add(2, new Silo());
        list.add(3, new Silo());
        
        siloList = list;
        
        client1 = new Client("Mikko Mallikas");
        client2 = new Client("Maija Meikäläinen");
        
        ArrayList<Client> clients = new ArrayList<>();
        
        clients.add(client1);
        clients.add(client2);
        
        clientList = clients;
        
        silo1 = new Silo();
        silo2 = new Silo();
    }
    
    @Test
    public void hasMapWorksCorrectly() throws SQLException {
        assertTrue(dao.hasMap());
    }
    
    @Test
    public void creatingTableWorksCorrectly() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("DROP TABLE Silos");
        
        assertFalse(dao.hasMap());
        
        dao.createTable(4, 1);
        
        assertTrue(dao.hasMap());
    }
    
    @Test
    public void removeSiloWorksCorrectly() throws SQLException {
        silo1 = dao.getSilo(2, 3);
        silo1.setIndex(1);
        dao.remove(silo1);
        
        Silo silo3 = dao.getSilo(2, 3);
        
        assertEquals("", silo3.getClient().getName());
        assertEquals("", silo3.getGrain().getCrop());
        assertEquals(0, silo3.getGrain().getVolume());
    }
    
//    @Test
//    public void createSiloWorksCorrectly() throws SQLException {
//        Silo silo3 = new Silo();
//        silo3 = dao.getSilo(9, 9);
//
//        Grain grain3 = new Grain();        
//        grain3.setCrop("herne");
//        
//        Client client3 = new Client("Matti");
//        
//        silo3.setGrain(grain3);
//        silo3.setClient(client3);
//        //silo3.setIndex(99);
//        
//        
//        clientList.add(client3);
//        
//        
//        dao.create(silo3);
//        
//        
//        assertEquals("Matti", silo3.getClient().getName());
//        assertEquals("herne", silo3.getGrain().getCrop());
//        assertEquals(99, silo3.getIndex());
//    }

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
