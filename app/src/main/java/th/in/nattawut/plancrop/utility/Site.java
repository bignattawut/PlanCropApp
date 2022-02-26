package th.in.nattawut.plancrop.utility;

import com.google.gson.annotations.SerializedName;

public class Site {

    @SerializedName("sno")
    private String sno;
    @SerializedName("mid")
    private String mid;
    @SerializedName("lat")
    private String lat;
    @SerializedName("lon")
    private String lon;
    @SerializedName("thai")
    private String thai;

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public String getSno() {
        return sno;
    }

    public String getMid() {
        return mid;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getThai() {
        return thai;
    }
}
