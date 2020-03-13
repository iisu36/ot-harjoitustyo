package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Iizu
 */
public class KassapaateTest {
    
    Kassapaate kassa;
    Maksukortti kortti;
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(450);
    }
    
    @Test
    public void alkutilanneOikein() {
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoToimiiEdullisillaKunMaksuOnRiittava() {
        assertEquals(260, kassa.syoEdullisesti(500));
        assertEquals(100240, kassa.kassassaRahaa());
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoToimiiMaukkaillaKunMaksuOnRiittava() {
        assertEquals(100, kassa.syoMaukkaasti(500));
        assertEquals(100400, kassa.kassassaRahaa());
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoToimiiEdullisillaKunMaksuEiOleRiittava() {
        assertEquals(200, kassa.syoEdullisesti(200));
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoToimiiMaukkaillaKunMaksuEiOleRiittava() {
        assertEquals(399, kassa.syoMaukkaasti(399));
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void korttiostoToimiiEdullisillaKunMaksuOnRiittava() {
        assertTrue(kassa.syoEdullisesti(kortti));
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
        assertEquals(210, kortti.saldo());
    }
    
    @Test
    public void korttisostoToimiiMaukkaillaKunMaksuOnRiittava() {
        assertTrue(kassa.syoMaukkaasti(kortti));
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
        assertEquals(50, kortti.saldo());
    }
    
    @Test
    public void korttiostoEiOnnistuEdullisillaKunMaksuEiOleRiittava() {
        kassa.syoEdullisesti(kortti);
        assertFalse(kassa.syoEdullisesti(kortti));
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
        assertEquals(210, kortti.saldo());
    }
    
    @Test
    public void korttiostoEiOnnistuMaukkaillaKunMaksuEiOleRiittava() {
        kassa.syoMaukkaasti(kortti);
        assertFalse(kassa.syoMaukkaasti(kortti));
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
        assertEquals(50, kortti.saldo());
    }
    
    @Test
    public void kortillaRahanLataaminenToimii() {
        kassa.lataaRahaaKortille(kortti, 1000);
        assertEquals(101000, kassa.kassassaRahaa());
        assertEquals(1450, kortti.saldo());
    }
    
    @Test
    public void kortillaRahanLataaminenEiToimiNegatiivisellaArvolla() {
        kassa.lataaRahaaKortille(kortti, -1000);
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(450, kortti.saldo());
    }
    
}
