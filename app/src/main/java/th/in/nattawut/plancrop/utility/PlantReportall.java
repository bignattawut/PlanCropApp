package th.in.nattawut.plancrop.utility;

import com.google.gson.annotations.SerializedName;

public class PlantReportall {

    @SerializedName("crop")
    private String crop;
    @SerializedName("pdate")
    private String pdate;
    @SerializedName("hdate")
    private String beginharvest;
    @SerializedName("area")
    private float area;
    @SerializedName("yield")
    private int yield;
    @SerializedName("name")
    private String name;
    @SerializedName("tel")
    private String tel;


    public String getCrop() {
        return crop;
    }

    public String getPdate() {
        return pdate;
    }

    public String getBeginharvest() {
        return beginharvest;
    }

    public String getArea() {
        return (int)Math.floor(area) + "-" + (int)Math.floor((area*400%400)/100) + "-" + (int)(area*400)%100;
    }

    public int getYield() {
        return yield;
    }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }
}
