/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silo.domain;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author Iizu
 */
public class Silo {

    private int index;
    private Button button;
    private Client client;
    private Grain grain;
    private Label label;
    private String name;
    private String crop;
    private String variety;
    private Integer volume;

    public Silo() {
        this.client = null;
        this.name = "";
        this.crop = "";
        this.variety = "";
        this.volume = 0;
    }

    public String getName() {
        this.name = this.client.getName();

        return this.name;
    }

    public String getCrop() {
        this.crop = this.grain.getCrop();

        return this.crop;
    }

    public String getVariety() {
        this.variety = this.grain.getVariety();

        return this.variety;
    }

    public Integer getVolume() {
        this.volume = this.grain.getVolume();

        return this.volume;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Label getLabel() {
        return label;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Grain getGrain() {
        return grain;
    }

    public void setGrain(Grain grain) {
        this.grain = grain;
    }

    @Override
    public String toString() {
        return client.getName() + "\n" + grain.getCrop() + "\n" + grain.getVolume() + " hl";
    }

    public String allInfo() {
        return client.getName() + "\n" + grain.getCrop() + "\n"
                + grain.getVariety() + "\n" + grain.getVolume() + " hl\n"
                + grain.getProductionMethod();
    }
}
