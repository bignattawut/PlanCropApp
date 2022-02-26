package th.in.nattawut.plancrop.utility;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import th.in.nattawut.plancrop.R;

public class PlanAdpter1 extends ArrayAdapter<Plan> {

    private Context context;
    private List<Plan> plans;

    public PlanAdpter1(@NonNull Context context, @LayoutRes int resource, @NonNull List<Plan> objects) {
        super(context, resource, objects);
        this.context = context;
        this.plans = objects;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.frm_plan_view,parent,false);

        TextView pdataString1 = view.findViewById(R.id.textArePlant);
        TextView yieldStrings1 = view.findViewById(R.id.textcrop);
        TextView areaStrings = view.findViewById(R.id.textDataplant);
        TextView cropStrings1 = view.findViewById(R.id.textplanCrop);
        TextView nameStrings1 = view.findViewById(R.id.textTypePlan);

        pdataString1.setText(plans.get(position).getPdate());
        //yieldStrings1.setText(plants.get(position).getYield());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String s1 = decimalFormat.format(plans.get(position).getYield());
        yieldStrings1.setText(s1);
        areaStrings.setText(plans.get(position).getArea());
        cropStrings1.setText(plans.get(position).getCrop());
        nameStrings1.setText(plans.get(position).getName());



        return view;
    }
}


