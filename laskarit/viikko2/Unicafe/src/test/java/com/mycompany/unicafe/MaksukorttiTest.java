package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinKonstruktoriToimiiOikein() {
        assertEquals("saldo: 10.0",kortti.toString());
    }
    
    @Test
    public void rahanLataaminenToimiiOikein() {
        kortti.lataaRahaa(100);
        assertEquals("saldo: 11.0",kortti.toString());
    }
    
    @Test
    public void saldonVaheneminenToimiiOikein() {
        kortti.otaRahaa(200);
        assertEquals("saldo: 8.0",kortti.toString());
    }
    
    @Test
    public void saldoEiVäheneJosSeEiRiitä() {
        kortti.otaRahaa(1100);
        assertEquals("saldo: 10.0",kortti.toString());
    }
    
    @Test
    public void tasarahaToimii() {
        assertTrue(kortti.otaRahaa(1000));
    }
}
