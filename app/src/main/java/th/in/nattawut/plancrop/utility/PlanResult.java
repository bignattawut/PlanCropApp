package th.in.nattawut.plancrop.utility;

import com.google.gson.annotations.SerializedName;

public class PlanResult {

    @SerializedName("crop")
    private String crop;
    @SerializedName("bdate")
    private String beginharvest;
    @SerializedName("edate")
    private String harvestperiod;
    @SerializedName("yield")
    private float yield;

    public String getCrop() {
        return crop;
    }

    public String getBeginharvest() {
        return beginharvest;
    }

    public String getHarvestperiod() {
        return harvestperiod;
    }

    public float getYield() {
        return yield;
    }
}
