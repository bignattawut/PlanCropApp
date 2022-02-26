package th.in.nattawut.plancrop.utility;

import com.google.gson.annotations.SerializedName;

public class PlanFarmer {


    @SerializedName("no")
    private String no;
    @SerializedName("pdate")
    private String pdate;
    @SerializedName("cid")
    private String cid;
    @SerializedName("crop")
    private String crop;
    @SerializedName("area")
    private float area;
    @SerializedName("yieldq")
    private String yieldq;

    @SerializedName("yield")
    private String yield;


    public String getNo() {
        return no;
    }

    public String getPdate() {
        return pdate;
    }

    public String getCid() {
        return cid;
    }

    public String getCrop() {
        return crop;
    }

    public String getArea() {
        return (int)Math.floor(area) + "-" + (int)Math.floor((area*400%400)/100) + "-" + (int)(area*400)%100;
    }

    public float getArea1(){
        return area;
    }

    public String getYield() {
        return yield;
    }

    public String getYieldq() {
        return yieldq;
    }
}
