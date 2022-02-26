package th.in.nattawut.plancrop.utility;

import com.google.gson.annotations.SerializedName;

public class Plan {

    @SerializedName("no")
    private String no;
    @SerializedName("pdate")
    private String pdate;
    @SerializedName("cid")
    private String cid;
    @SerializedName("yield")
    private int yield;
    @SerializedName("mid")
    private String mid;
    @SerializedName("name")
    private String name;
    @SerializedName("area")
    private float area;
    @SerializedName("crop")
    private String crop;


    public String getNo() {
        return no;
    }

    public String getPdate() {
        return pdate;
    }

    public String getCid() {
        return cid;
    }

    public int getYield() {
        return yield;
    }

    public String getMid() {
        return mid;
    }

    public String getName() {
        return name;
    }

    public String getArea() {
        return (int)Math.floor(area) + "-" + (int)Math.floor((area*400%400)/100) + "-" + (int)(area*400)%100;
    }

    public float getArea1(){
        return area;
    }

    public String getCrop() {
        return crop;
    }
}


