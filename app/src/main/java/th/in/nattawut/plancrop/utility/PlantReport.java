package th.in.nattawut.plancrop.utility;

import com.google.gson.annotations.SerializedName;

public class PlantReport {

    @SerializedName("no")
    private String no;
    @SerializedName("crop")
    private String crop;
    @SerializedName("area")
    private String area;
    @SerializedName("yield")
    private int yield;
    @SerializedName("pdate")
    private String pdate;
    @SerializedName("hdate")
    private String beginharvest;

    public String getNo() {
        return no;
    }

    public String getCrop() {
        return crop;
    }

    public String getArea() {
        return area;
    }

    public int getYield() {
        return yield;
    }

    public String getPdate() {
        return pdate;
    }

    public String getBeginharvest() {
        return beginharvest;
    }
}
