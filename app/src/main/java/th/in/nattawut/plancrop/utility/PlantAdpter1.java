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

public class PlantAdpter1 extends ArrayAdapter<Plant> {

    private Context context;
    private List<Plant> plants;

    public PlantAdpter1(@NonNull Context context, @LayoutRes int resource, @NonNull List<Plant> objects) {
        super(context, resource, objects);
        this.context = context;
        this.plants = objects;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.frm_plant_view,parent,false);

        TextView pdataString1 = view.findViewById(R.id.pdataString);
        TextView yieldStrings1 = view.findViewById(R.id.yieldStrings);
        TextView areaStrings = view.findViewById(R.id.areaStrings);
        TextView cropStrings1 = view.findViewById(R.id.cropStrings);
        TextView nameStrings1 = view.findViewById(R.id.nameStrings);

        pdataString1.setText(plants.get(position).getPdate());
        //yieldStrings1.setText(plants.get(position).getYield());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String s1 = decimalFormat.format(plants.get(position).getYield());
        yieldStrings1.setText(s1);
        areaStrings.setText(plants.get(position).getArea());
        cropStrings1.setText(plants.get(position).getCrop());
        nameStrings1.setText(plants.get(position).getName());



        return view;
    }
}


