package th.in.nattawut.plancrop.utility;

import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("no")
    private String no;
    @SerializedName("sdate")
    private String sdate;
    @SerializedName("edate")
    private String edate;
    @SerializedName("cid")
    private String cid;
    @SerializedName("crop")
    private String crop;
    @SerializedName("qty")
    private int qty;

//    public Order() {
//
//    }
//
//    public Order(String no, String sdate, String edate, String cid, String crop, String qty) {
//        this.no = no;
//        this.sdate = sdate;
//        this.edate = edate;
//        this.cid = cid;
//        this.crop = crop;
//        this.qty = qty;
//    }

//    public void setNo(String no) {
//        this.no = no;
//    }
//
//    public void setSdate(String sdate) {
//        this.sdate = sdate;
//    }
//
//    public void setEdate(String edate) {
//        this.edate = edate;
//    }
//
//    public void setCid(String cid) {
//        this.cid = cid;
//    }
//
//    public void setCrop(String crop) {
//        this.crop = crop;
//    }
//
//    public void setQty(String qty) {
//        this.qty = qty;
//    }

    public String getNo() {
        return no;
    }

    public String getSdate() {
        return sdate;
    }

    public String getEdate() {
        return edate;
    }

    public String getCid() {
        return cid;
    }

    public String getCrop() {
        return crop;
    }

    public int getQty() {
        return qty;
    }
}
