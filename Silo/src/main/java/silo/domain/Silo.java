package silo.domain;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * @author Iizu
 * @version Viikko 7
 *
 * This class controls the silo's values.
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

    /**
     * Returns the client's name, crop and volume.
     *
     * @return Returns the client's name, crop and volume.
     */
    @Override
    public String toString() {
        return client.getName() + "\n" + grain.getCrop() + "\n" + grain.getVolume() + " hl";
    }

    /**
     * Returns all the values of a silo.
     *
     * @return Returns the client's name, crop, variety, volume and production
     * method.
     */
    public String allInfo() {
        return client.getName() + "\n" + grain.getCrop() + "\n"
                + grain.getVariety() + "\n" + grain.getVolume() + " hl\n"
                + grain.getProductionMethod();
    }
}
