package silo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import silo.domain.Client;
import silo.domain.User;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Iizu
 */
public class ClientDaoTest {

    Connection connection;
    ClientDao dao1;
    ClientDao dao2;
    User user;
    Client client1;
    Client client2;
    
    @Before
    public void setUp() throws Exception {

        user = new User("testi", "salasana");
        dao1 = new ClientDao("testclients", user);

        connection = dao1.getConnection();

        Statement stmt = connection.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS Clients (client_id INTEGER PRIMARY KEY, "
                + "user TEXT, name TEXT, phone TEXT, address TEXT)");

        PreparedStatement p = connection.prepareStatement("INSERT OR ABORT INTO Clients "
                + "(user, name, phone, address) VALUES (?,?,?,?)");

        p.setString(1, user.getName());
        p.setString(2, "Mikko Mallikas");
        p.setString(3, "0011223344");
        p.setString(4, "Peltoraitti 369");

        p.executeUpdate();
        
        PreparedStatement s = connection.prepareStatement("INSERT OR ABORT INTO Clients "
                + "(user, name, phone, address) VALUES (?,?,?,?)");

        s.setString(1, user.getName());
        s.setString(2, "Maija Meikäläinen");
        s.setString(3, "5566778899");
        s.setString(4, "Niittykumpu 1234");

        s.executeUpdate();
        
        stmt.close();
        s.close();
        p.close();
        
        client1 = new Client("Mikko Mallikas");
        client2 = new Client("Maija Meikäläinen");
    }
    
    
    @Test
    public void creatingTableWorksCorrectly() throws SQLException {
        
        dao2 = new ClientDao("tabletest", user);
        dao2.createTable();
        connection = dao2.getConnection();
        
        Statement stmt = connection.createStatement();
        
        ResultSet rs = stmt.executeQuery("SELECT name FROM Clients");
        
        assertFalse(rs.next());

        stmt.close();
    }
    
    @Test
    public void creatingClientWorksCorrectly() throws SQLException {
        
        Client testClient = new Client("Tommi Testaaja");
        dao1.create(testClient);
        
        Statement stmt = connection.createStatement();
        
        ResultSet rs = stmt.executeQuery("SELECT name FROM Clients WHERE name = 'Tommi Testaaja'");
        
        assertEquals("Tommi Testaaja", rs.getString("name"));     
        
        stmt.close();
    }
    
    @Test
    public void removingClientWorksCorrectly() throws SQLException {
        
        Statement stmt = connection.createStatement();
        
        ResultSet rs = stmt.executeQuery("SELECT name FROM Clients WHERE name = '" + client1.getName() + "'");
        
        assertTrue(rs.next());
        
        dao1.remove(client1);
        
        rs = stmt.executeQuery("SELECT name FROM Clients WHERE name = '" + client1.getName() + "'");
        
        assertFalse(rs.next());
        
        stmt.close();
    }

    @Test
    public void clientIdFound() throws SQLException {        
        
        assertEquals(1, dao1.getClientId(client1));
        assertEquals(2, dao1.getClientId(client2));
    }

    @After
    public void tearDown() throws SQLException {
        Statement s = connection.createStatement();

        s.execute("DROP TABLE Clients");

        dao1.stopConnection();
    }
}
