package th.in.nattawut.plancrop.utility;

import com.google.gson.annotations.SerializedName;

public class UploadImg {

    @SerializedName("URL")
    private String Url;

    @SerializedName("SCode")
    private String SCode;

    @SerializedName("response")
    private String Response;

    public String getResponse() {
        return Response;
    }




}
