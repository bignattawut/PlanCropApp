package th.in.nattawut.plancrop.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import th.in.nattawut.plancrop.R;

public class PlantAdpter extends BaseAdapter {

private Context context;
        private String[] noStrings,pdataString,cidStrings,yieldStrings,cropStrings,areaStrings,
                midStrings,nameStrings,snoStrings,latStrings,lonStrings;

public PlantAdpter(Context context,
                   String[] noStrings,
                   String[] pdataString,
                   String[] cidStrings,
                   String[] yieldStrings,
                   String[] cropStrings,
                   String[] areaStrings,
                   String[] midStrings,
                   String[] nameStrings,
                   String[] snoStrings,
                   String[] latStrings,
                   String[] lonStrings) {
    this.context = context;
    this.noStrings = noStrings;
    this.pdataString = pdataString;
    this.cidStrings = cidStrings;
    this.yieldStrings = yieldStrings;
    this.cropStrings = cropStrings;
    this.areaStrings = areaStrings;
    this.midStrings = midStrings;
    this.nameStrings = nameStrings;
    this.snoStrings = snoStrings;
    this.latStrings = latStrings;
    this.lonStrings = lonStrings;
}

@Override
public int getCount() {
    return noStrings.length;
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
    View view = layoutInflater.inflate(R.layout.frm_plant_view, parent, false);

//        TextView noStrings1 = view.findViewById(R.id.noStrings);
    TextView pdataString1 = view.findViewById(R.id.pdataString);
//        TextView cidStrings1 = view.findViewById(R.id.cidStrings);
    TextView yieldStrings1 = view.findViewById(R.id.yieldStrings);
    TextView cropStrings1 = view.findViewById(R.id.cropStrings);
//        TextView areaStrings1 = view.findViewById(R.id.areaStrings);
//        TextView midStrings1 = view.findViewById(R.id.midStrings);
    TextView nameStrings1 = view.findViewById(R.id.nameStrings);
//        TextView snoStrings1 = view.findViewById(R.id.snoStrings);
//        TextView latStrings1 = view.findViewById(R.id.latStrings);
//        TextView lonStrings1 = view.findViewById(R.id.lonStrings);

//        noStrings1.setText(noStrings[position]);
    pdataString1.setText(pdataString[position]);
//        cidStrings1.setText(cidStrings[position]);
    yieldStrings1.setText(yieldStrings[position]);
    cropStrings1.setText(cropStrings[position]);
//        areaStrings1.setText(areaStrings[position]);
//        midStrings1.setText(midStrings[position]);
    nameStrings1.setText(nameStrings[position]);
//        snoStrings1.setText(snoStrings[position]);
//        latStrings1.setText(latStrings[position]);
//        lonStrings1.setText(lonStrings[position]);

    return view;
}
}