package th.in.nattawut.plancrop.utility;

import android.content.Context;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

import th.in.nattawut.plancrop.fragment.RegisterFragment;

public class Devs {

    private Context context;
    private int pid;
    private String thai;

    public Devs(FragmentActivity activity, RegisterFragment registerFragment, int simple_dropdown_item_1line, ArrayList<Devs> lista) {

    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Devs(int pid, String thai) {
        this.pid = pid;
        this.thai = thai;
    }

    public void setThai(String thai) {
        this.thai = thai;
    }

    @Override
    public String toString() {
        return thai;
    }
}