package silo.domain;

/**
 * @author Iizu
 * @version Viikko 7
 *
 * This class controls the grain's values.
 */
public class Grain {

    private String crop;
    private String variety;
    private int volume;
    private String productionMethod;

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
