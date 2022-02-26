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

public class PlantFarmerAdapter extends ArrayAdapter<PlantFarmer> {

    private Context context;
    private List<PlantFarmer> plantFarmers;

    public PlantFarmerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<PlantFarmer> objects) {
        super(context, resource, objects);
        this.context = context;
        this.plantFarmers = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.frm_plantfarmer_view, parent, false);


        //TextView textPlanFarmersno = view.findViewById(R.id.sno);

        TextView txtPDataPlantFarmer = view.findViewById(R.id.txtPDataPlantFarmer);
        TextView txtCropPlantFarmer = view.findViewById(R.id.txtCropPlantFarmer);
        TextView txtYieldPlantFarmer = view.findViewById(R.id.txtYieldPlantFarmer);
        TextView txtAreaPlantFarmer = view.findViewById(R.id.txtAreaPlantFarmer);

        //textPlanFarmerNo.setText(plantFarmers.get(position).getCid());

        txtPDataPlantFarmer.setText(plantFarmers.get(position).getPdate());
        txtCropPlantFarmer.setText(plantFarmers.get(position).getCrop());
        //txtYieldPlantFarmer.setText(plantFarmers.get(position).getYield());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String s1 = decimalFormat.format(Float.parseFloat(plantFarmers.get(position).getYieldq()));
        txtYieldPlantFarmer.setText(s1);
        txtAreaPlantFarmer.setText(plantFarmers.get(position).getArea());


        //textPlanFarmersno.setText((plantFarmers.get(position).getSno()));


        return view;
    }
}