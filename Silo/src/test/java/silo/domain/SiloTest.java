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

    Silo silo1;
    Silo silo2;
    
    Client client1;
    Client client2;
    
    Grain grain1;
    Grain grain2;

    @Before
    public void setUp() {
        
        silo1 = new Silo();
        silo2 = new Silo();
        
        client1 = new Client("Mikko Mallikas");
        client2 = new Client("Maija Meikäläinen");
        
        grain1 = new Grain();
        grain2 = new Grain();
        
        grain1.setCrop("Crop");
        grain1.setVariety("Variety");
        grain1.setVolume(600);
        grain1.setProductionMethod("TV");
        
        grain2.setCrop("Vilja");
        grain2.setVariety("Lajike");
        grain2.setVolume(1200);
        grain2.setProductionMethod("Luomu");
        
        silo1.setClient(client1);
        silo1.setGrain(grain1);
        silo1.setIndex(1);
        
        silo2.setClient(client2);
        silo2.setGrain(grain2);
        silo2.setIndex(2);
    }

    @Test
    public void testClientToString() {
        assertEquals("Mikko Mallikas", silo1.getClient().toString());
        assertEquals("Maija Meikäläinen", silo2.getClient().toString());
    }
    
    @Test
    public void testSiloToString() {
        assertEquals("Mikko Mallikas\nCrop\n600 hl", silo1.toString());
        assertEquals("Maija Meikäläinen\nVilja\n1200 hl", silo2.toString());
    }
    
    @Test
    public void testAllInfo() {
        assertEquals("Mikko Mallikas\nCrop\nVariety\n600 hl\nTV", silo1.allInfo());
        assertEquals("Maija Meikäläinen\nVilja\nLajike\n1200 hl\nLuomu", silo2.allInfo());
    }
}
