package th.in.nattawut.plancrop.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;

import th.in.nattawut.plancrop.R;

public class OrderReportAdpter extends BaseAdapter {

    private Context context;
    private String[] sdateStrings,edateString,cropStrings,qtyString, nameStrings,telStrings;

    public OrderReportAdpter(Context context, String[] sdateStrings, String[] edateString, String[] cropStrings, String[] qtyString, String[] nameStrings, String[] telStrings) {
        this.context = context;
        this.sdateStrings = sdateStrings;
        this.edateString = edateString;
        this.cropStrings = cropStrings;
        this.qtyString = qtyString;
        this.nameStrings = nameStrings;
        this.telStrings = telStrings;
    }

    @Override
    public int getCount() {
        return sdateStrings.length;
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
        View view = layoutInflater.inflate(R.layout.frm_orderreport_view, parent, false);

        TextView textSDate = view.findViewById(R.id.textSData);
        TextView textEDate = view.findViewById(R.id.textEDate);
        TextView textCrop =  view.findViewById(R.id.textCrop);
        TextView textQty = view.findViewById(R.id.textQty);
        TextView textname = view.findViewById(R.id.textname);
        TextView texttel = view.findViewById(R.id.texttel);


        textSDate.setText(sdateStrings[position]);
        textEDate.setText(edateString[position]);
        textCrop.setText(cropStrings[position]);
        //textQty.setText(qtyString[position]);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String s1 = decimalFormat.format(Float.valueOf(qtyString[position]));
        textQty.setText(s1);
        textname.setText(nameStrings[position]);
        texttel.setText(telStrings[position]);

        return view;
    }
}
