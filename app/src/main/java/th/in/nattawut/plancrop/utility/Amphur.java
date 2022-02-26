package th.in.nattawut.plancrop.utility;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

import th.in.nattawut.plancrop.fragment.RegisterFragment;

public class Amphur {
    private int did;
    private String thai;

    public Amphur(FragmentActivity activity, RegisterFragment registerFragment, int simple_dropdown_item_1line, ArrayList<Amphur> lis) {
    }

    public Amphur(int did, String thai) {
        this.did = did;
        this.thai = thai;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public void setThai(String thai) {
        this.thai = thai;
    }
    @Override
    public String toString() {
        return thai;
    }
}
