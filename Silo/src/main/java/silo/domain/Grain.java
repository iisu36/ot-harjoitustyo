/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silo.domain;

import java.util.Date;

/**
 *
 * @author Iizu
 */

public class Grain {
    
    private String crop;
    private String variety;
    private int volume;
    private String productionMethod;
    private double moisture;
    private double dryTime;
    private double coolTime;
    private int dryerTemp;
    private int exitTemp;
    private Date date;
    private String field;
    private String use;

    public void setUse(String use) {
        this.use = use;
    }

    
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

    public void setMoisture(double moisture) {
        this.moisture = moisture;
    }

    public void setDryTime(double dryTime) {
        this.dryTime = dryTime;
    }

    public void setCoolTime(double coolTime) {
        this.coolTime = coolTime;
    }

    public void setDryerTemp(int dryerTemp) {
        this.dryerTemp = dryerTemp;
    }

    public void setExitTemp(int exitTemp) {
        this.exitTemp = exitTemp;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setField(String field) {
        this.field = field;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }

    private String notes;

    public String getVariety() {
        return variety;
    }

    public int getVolume() {
        return volume;
    }

    public String getProductionMethod() {
        return productionMethod;
    }

    public double getMoisture() {
        return moisture;
    }

    public double getDryTime() {
        return dryTime;
    }

    public double getCoolTime() {
        return coolTime;
    }

    public int getDryerTemp() {
        return dryerTemp;
    }

    public int getExitTemp() {
        return exitTemp;
    }

    public Date getDate() {
        return date;
    }

    public String getField() {
        return field;
    }
    
    public String getUse() {
        return use;
    }

    public String getNotes() {
        return notes;
    }
    
}
