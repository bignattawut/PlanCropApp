package th.in.nattawut.plancrop.utility;

import com.google.gson.annotations.SerializedName;

public class Farmer {

    @SerializedName("mid")
    private String mid;

    @SerializedName("userid")
    private String userid;

    @SerializedName("pwd")
    private String pwd;

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String address;

    @SerializedName("vid")
    private String vid;

    @SerializedName("sid")
    private String sid;

    @SerializedName("tel")
    private String tel;

    @SerializedName("email")
    private String email;

    @SerializedName("area")
    private String area;

    public String getMid() {
        return mid;
    }

    public String getUserid() {
        return userid;
    }

    public String getPwd() {
        return pwd;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getVid() {
        return vid;
    }

    public String getSid() {
        return sid;
    }

    public String getTel() {
        return tel;
    }

    public String getEmail() {
        return email;
    }

    public String getArea() {
        return area;
    }
}
