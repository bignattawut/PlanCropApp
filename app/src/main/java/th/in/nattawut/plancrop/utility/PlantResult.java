package th.in.nattawut.plancrop.utility;

import com.google.gson.annotations.SerializedName;

public class PlantResult {

    @SerializedName("crop")
    private String crop;

    @SerializedName("yield")
    private int yield;



    public String getCrop() {
        return crop;
    }

    public int getYield() {
        return yield;
    }

}
