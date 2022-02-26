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

public class PlantResultAdpter extends ArrayAdapter<PlantResult> {


    private Context context;
    private List<PlantResult> plantResults;

    public PlantResultAdpter(@NonNull Context context, @LayoutRes int resource, @NonNull List<PlantResult> objects) {
        super(context, resource, objects);
        this.context = context;
        this.plantResults = objects;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.frm_plantresult_view,parent,false);



        TextView textCrop = view.findViewById(R.id.textPlantQty);
        TextView textQty = view.findViewById(R.id.textPlantYield);
        //TextView textPlantsno = view.findViewById(R.id.textPlantsno);

        textCrop.setText(plantResults.get(position).getCrop());
        //textQty.setText(plantResults.get(position).getYield());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String s1 = decimalFormat.format(plantResults.get(position).getYield());
        textQty.setText(s1);
        //textPlantsno.setText(plantResults.get(position).getSno());



        return view;
    }
}