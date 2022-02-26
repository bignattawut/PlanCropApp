package th.in.nattawut.plancrop.utility;

import com.google.gson.annotations.SerializedName;

public class PlantActivity {

    @SerializedName("picno")
    private String picno;
    @SerializedName("pdate")
    private String pdate;
    @SerializedName("description")
    private String description;

    public String getPicno() {
        return picno;
    }

    public String getPdate() {
        return pdate;
    }

    public String getDescription() {
        return description;
    }
}
