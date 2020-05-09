package silo.domain;

import java.util.ArrayList;

/**
 * @author Iizu
 * @version Viikko 7
 *
 * This class controls the client's values.
 */
public class Client {

    private String name;
    private String address;
    private String phone;
    private ArrayList<Grain> grainList;
    private ArrayList<Silo> siloList;
    private String crop;
    private String variety;
    private String volume;

    public Client(String name) {

        this.name = name;
        this.grainList = new ArrayList<>();
        this.siloList = new ArrayList<>();
        this.address = "";
        this.phone = "";
        this.crop = "";
        this.variety = "";
        this.volume = "";
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

    public ArrayList<Silo> getSiloList() {
        return siloList;
    }

    public void setSiloList(ArrayList<Silo> siloList) {
        this.siloList = siloList;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return name;
    }
}
