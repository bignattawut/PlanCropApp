package th.in.nattawut.plancrop.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;

import th.in.nattawut.plancrop.R;

public class PlanAdapter extends BaseAdapter {

    private Context context;
    private String[] planStrings,midString,typeStrings,cidString,cropStrings,yieldStrings,areStrings, dateStrings;

    public PlanAdapter(Context context,
                       String[] planStrings,
                       String[] midString,
                       String[] typeStrings,
                       String[] cidString,
                       String[] cropStrings,
                       String[] yieldStrings,
                       String[] areStrings,
                       String[] dateStrings){
        this.context = context;
        this.planStrings = planStrings;
        this.midString = midString;
        this.typeStrings = typeStrings;
        this.cidString = cidString;
        this.cropStrings = cropStrings;
        this.yieldStrings = yieldStrings;
        this.areStrings = areStrings;
        this.dateStrings = dateStrings;

    }
    @Override
    public int getCount() {
        return planStrings.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.frm_plan_view, parent, false);

        TextView planTextView = view.findViewById(R.id.texPlan);
        TextView midTextView = view.findViewById(R.id.textMid);
        TextView typeTextView = view.findViewById(R.id.textTypePlan);
        TextView cidTextView = view.findViewById(R.id.textcrop);
        TextView cropTextView = view.findViewById(R.id.textplanCrop);
        TextView areTextView = view.findViewById(R.id.textArePlant);
        TextView dataTextView = view.findViewById(R.id.textDataplant);

        planTextView.setText(planStrings[position]);
        midTextView.setText(midString[position]);
        typeTextView.setText(typeStrings[position]);
        //cidTextView.setText(yieldStrings[position]);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String s1 = decimalFormat.format(Float.valueOf(yieldStrings[position]));
        cidTextView.setText(s1);
        cropTextView.setText(cropStrings[position]);
        areTextView.setText(areStrings[position]);

        //dataTextView.setText(dateStrings[position]);

        dataTextView.setText(String.valueOf((int) Math.floor(Float.valueOf(dateStrings[position]))) +"-"+
                (int) Math.floor(Float.valueOf(dateStrings[position])*400%400)/100+ "-" +
                (int) Math.floor(Float.valueOf(dateStrings[position])*400)%100);
        return view;
    }
}