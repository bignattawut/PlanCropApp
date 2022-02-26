package th.in.nattawut.plancrop.utility;

import com.google.gson.annotations.SerializedName;

public class OrderResultSum {
    @SerializedName("crop")
    private String crop;
    @SerializedName("qty")
    private int qty;
    @SerializedName("yield")
    private int yield;



    public String getCrop() {
        return crop;
    }

    public int getQty() {
        return qty;
    }

    public int getYield() {
        return yield-qty;
    }
}
