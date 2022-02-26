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

public class PlanResultAdpter extends ArrayAdapter<PlanResult> {


    private Context context;
    private List<PlanResult> planResults;

    public PlanResultAdpter(@NonNull Context context, @LayoutRes int resource, @NonNull List<PlanResult> objects) {
        super(context, resource, objects);
        this.context = context;
        this.planResults = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.frm_planresult_view, parent, false);


        TextView textCrop = view.findViewById(R.id.textPlanCrop);
        TextView textQty = view.findViewById(R.id.textPlanPdate);
        TextView textArea = view.findViewById(R.id.textPlantEdate);
        TextView textyield = view.findViewById(R.id.textPlanYield);


        textCrop.setText(planResults.get(position).getCrop());
        textQty.setText(planResults.get(position).getBeginharvest());
        textArea.setText(planResults.get(position).getHarvestperiod());
        //textyield.setText(planResults.get(position).getYield());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String s1 = decimalFormat.format(planResults.get(position).getYield());
        textyield.setText(s1);
        //textyield.setText(String.valueOf(planResults.get(position).getYield()));


        return view;
    }
}