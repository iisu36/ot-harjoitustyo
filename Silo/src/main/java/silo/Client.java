/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silo;


/**
 *
 * @author Iizu
 */
public class Client {
    
    private String name;
    private String plant;
    private String variety;
    private String volume;
    private String moisture;

    public Client(String name, String plant, String variety, String volume, String moisture) {
    
        this.name = name;
        this.plant = plant;
        this.variety = variety;
        this.volume = volume;
        this.moisture = moisture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public void setVolume(String volyme) {
        this.volume = volyme;
    }

    public void setMoisture(String moisture) {
        this.moisture = moisture;
    }        

    public String getName() {
        return name;
    }

    public String getPlant() {
        return plant;
    }

    public String getVariety() {
        return variety;
    }

    public String getVolume() {
        return volume;
    }

    public String getMoisture() {
        return moisture;
    }

    @Override
    public String toString() {
        return name + ": " + plant + " (" + variety + ") " + volume + " hl " + moisture + " %";
    }  
}
