package th.in.nattawut.plancrop.utility;

import androidx.fragment.app.FragmentActivity;
import java.util.ArrayList;
import th.in.nattawut.plancrop.fragment.CropFragment;
import th.in.nattawut.plancrop.fragment.PlanFragment;

public class CropAdapter {

    private int cid;
    private String crop;

    public CropAdapter(
            FragmentActivity activity,
            CropFragment cropAdapter,
            int simple_dropdown_item_1line,
            ArrayList<CropAdapter> list) {

    }
    public CropAdapter(
            FragmentActivity activity,
            PlanFragment cropAdapter,
            int simple_dropdown_item_1line,
            ArrayList<CropAdapter> list) {

    }

    public CropAdapter(int cid, String crop) {
        this.cid = cid;
        this.crop = crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }
    @Override
    public String toString() {
        return crop;
    }
}
