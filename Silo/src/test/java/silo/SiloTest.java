/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silo;

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
        client1 = new Client("A", "B", "C", "D", "E");
    }

    @Test
    public void testToString() {
        assertEquals("A: B (C) D hl E %", client1.toString());
    }
}
