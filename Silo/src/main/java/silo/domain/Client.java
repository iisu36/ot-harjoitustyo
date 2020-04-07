/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silo.domain;

import java.util.ArrayList;


/**
 *
 * @author Iizu
 */
public class Client {
    
    private String name;
    private String address;
    private String phone;
    private String billing;
    private ArrayList<Grain> grainList;
    private ArrayList<Silo> siloList;

    public Client(String name) {
    
        this.name = name;
        grainList = new ArrayList<>();
        siloList = new ArrayList<>();
        this.address = "";
        this.billing = "";
        this.phone = "";
    }
    
    public void addSilo(Silo silo) {
        this.siloList.add(silo);
    }
    
    public void addGrain(Grain grain) {
        this.grainList.add(grain);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBilling() {
        return billing;
    }

    public void setBilling(String billing) {
        this.billing = billing;
    }

    public ArrayList<Grain> getGrainList() {
        return grainList;
    }

    public void setGrainList(ArrayList<Grain> grainList) {
        this.grainList = grainList;
    }

    public ArrayList<Silo> getSiloList() {
        return siloList;
    }

    public void setSiloList(ArrayList<Silo> siloList) {
        this.siloList = siloList;
    }

    @Override
    public String toString() {
        return name + " " + phone + " " + address;
    }
}