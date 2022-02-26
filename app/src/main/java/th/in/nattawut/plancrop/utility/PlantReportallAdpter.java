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

public class PlantReportallAdpter extends ArrayAdapter<PlantReportall> {


    private Context context;
    private List<PlantReportall> plantReportalls;

    public PlantReportallAdpter(@NonNull Context context, @LayoutRes int resource, @NonNull List<PlantReportall> objects) {
        super(context, resource, objects);
        this.context = context;
        this.plantReportalls = objects;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.frm_plantreportall_view,parent,false);


        TextView textCrop = view.findViewById(R.id.textCrop);
        TextView pdate = view.findViewById(R.id.pdate);
        TextView textbeginharvest =  view.findViewById(R.id.textbeginharvest);
        TextView textArea = view.findViewById(R.id.textArea);
        TextView textQty =  view.findViewById(R.id.textQty);
        TextView textname = view.findViewById(R.id.textname);
        TextView texttel = view.findViewById(R.id.texttel);

        textCrop.setText(plantReportalls.get(position).getCrop());
        pdate.setText(plantReportalls.get(position).getPdate());
        textbeginharvest.setText(plantReportalls.get(position).getBeginharvest());
        textArea.setText(plantReportalls.get(position).getArea());
        //textQty.setText(plantReportalls.get(position).getYield());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String s1 = decimalFormat.format(plantReportalls.get(position).getYield());
        textQty.setText(s1);
        textname.setText(plantReportalls.get(position).getName());
        texttel.setText(plantReportalls.get(position).getTel());


        return view;
    }
}


