package th.in.nattawut.plancrop.utility;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

import th.in.nattawut.plancrop.fragment.PlanFragment;

public class MidAdpter {

    private int mid;
    private String name;

    public MidAdpter(FragmentActivity activity, PlanFragment planFragment, int simple_dropdown_item_1line, ArrayList<MidAdpter> list) {
    }

    public MidAdpter(int mid, String name) {
        this.mid = mid;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }
}
