/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silo.domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Iizu
 */
public class SiloTest {

    Client client1;

    @Before
    public void setUp() {
        client1 = new Client("Mikko Mallikas");
    }

    @Test
    public void testToString() {
        assertEquals("Mikko Mallikas", client1.toString());
    }
}
