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

public class PlantReportAdpter extends ArrayAdapter<PlantReport> {


    private Context context;
    private List<PlantReport> plantReports;

    public PlantReportAdpter(@NonNull Context context, @LayoutRes int resource, @NonNull List<PlantReport> objects) {
        super(context, resource, objects);
        this.context = context;
        this.plantReports = objects;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.frm_plantreport_view,parent,false);



        //TextView textNO = view.findViewById(R.id.textNO);
        TextView textCrop = view.findViewById(R.id.textCrop);
        //TextView pdate = view.findViewById(R.id.pdate);
        TextView textbeginharvest =  view.findViewById(R.id.textbeginharvest);
        //TextView textArea = view.findViewById(R.id.textArea);
        TextView textQty =  view.findViewById(R.id.textQty);


        //textNO.setText(plantReports.get(position).getNo());
        textCrop.setText(plantReports.get(position).getCrop());
        //pdate.setText(plantReports.get(position).getPdate());
        textbeginharvest.setText(plantReports.get(position).getBeginharvest());
        //textArea.setText(plantReports.get(position).getArea());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String s1 = decimalFormat.format(plantReports.get(position).getYield());
        textQty.setText(s1);
        //textQty.setText(plantReports.get(position).getYield());


        return view;
    }
}


