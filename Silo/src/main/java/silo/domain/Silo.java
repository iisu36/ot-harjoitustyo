/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silo.domain;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Iizu
 */
public class Silo {

    private int index;
    private ArrayList<String> history;
    private Button button;
    private Client client;
    private Grain grain;
    private Grain prefGrain;
    private int maxvolume;
    private Label label;

    public Silo() {
        client = null;
        this.label = new Label("");
        this.label.setMaxSize(75, 75);
        this.label.setAlignment(Pos.CENTER);
        this.label.setTextAlignment(TextAlignment.CENTER);
        this.label.setLineSpacing(11);
    }

    public Label getLabel() {
        return label;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        
        this.button.setText(String.valueOf(index));
        
        this.button.setStyle("-fx-text-fill: #00000043; "
                + " -fx-font: 58 Tahoma; -fx-font-weight: bold; ");
        this.button.setPadding(Insets.EMPTY);
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<String> history) {
        this.history = history;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
        this.button.setPadding(Insets.EMPTY);
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

    public Grain getPrefGrain() {
        return prefGrain;
    }

    public void setPrefGrain(Grain prefGrain) {
        this.prefGrain = prefGrain;
    }

    public int getMaxvolume() {
        return maxvolume;
    }

    public void setMaxvolume(int maxvolume) {
        this.maxvolume = maxvolume;
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

    public void reset() {
        setClient(null);
        setGrain(new Grain());
        
        this.button.setText(String.valueOf(index));
        
        this.button.setStyle("-fx-text-fill: #00000043; "
                + " -fx-font: 58 Tahoma; -fx-font-weight: bold; ");
        this.button.setPadding(Insets.EMPTY);
    }

    public void setStyle() {
        
        if (this.grain.getVolume() == 0) {
            
            this.button.setStyle("");
            
            return;
        }
        
        String background = "";
            
        if (this.grain.getVolume() < 150) {
            
            background = "-fx-background-color: greenyellow; ";
            
        } else if (this.grain.getVolume() < 200) {
            
            background = "-fx-background-color: yellowgreen; ";
            
        } else if (this.grain.getVolume() < 250) {
            
            background = "-fx-background-color: yellow; ";
            
        } else if (this.grain.getVolume() < 300) {
            
            background = "-fx-background-color: orange; ";
            
        } else if (this.grain.getVolume() <= 350) {
            
            background = "-fx-background-color: orangered; ";
            
        }
        
        this.label.setStyle(background + " -fx-font: 13 Tahoma; -fx-font-weight: bold; ");
    }
}
