/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silo.domain;

/**
 *
 * @author Iizu
 */

public class Grain {
    
    private String crop;
    private String variety;
    private int volume;
    private String productionMethod;
    
    public Grain() {
        
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setProductionMethod(String productionMethod) {
        this.productionMethod = productionMethod;
    }

    public String getVariety() {
        return variety;
    }

    public int getVolume() {
        return volume;
    }

    public String getProductionMethod() {
        return productionMethod;
    }    
}
