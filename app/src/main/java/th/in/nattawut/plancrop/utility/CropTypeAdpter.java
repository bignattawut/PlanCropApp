package th.in.nattawut.plancrop.utility;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

import th.in.nattawut.plancrop.fragment.CropFragment;

public class CropTypeAdpter {

    private int tid;
    private String croptype;

    public CropTypeAdpter(FragmentActivity activity, CropFragment cropFragment, int simple_dropdown_item_1line, ArrayList<CropTypeAdpter> list) {
    }

    public CropTypeAdpter(int tid, String croptype) {
        this.tid = tid;
        this.croptype = croptype;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public void setCroptype(String croptype) {
        this.croptype = croptype;
    }
    @Override
    public String toString() {
        return croptype;
    }
}
