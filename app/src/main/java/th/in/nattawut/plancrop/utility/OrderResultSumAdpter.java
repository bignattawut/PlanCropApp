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

public class OrderResultSumAdpter extends ArrayAdapter<OrderResultSum> {


    private Context context;
    private List<OrderResultSum> orderResultSums;

    public OrderResultSumAdpter(@NonNull Context context, @LayoutRes int resource, @NonNull List<OrderResultSum> objects) {
        super(context, resource, objects);
        this.context = context;
        this.orderResultSums = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.frm_orderresultsum_view, parent, false);


        TextView textCrop = view.findViewById(R.id.textCropOrderResult);
        TextView textQty = view.findViewById(R.id.textOrderResultQty);

        textCrop.setText(orderResultSums.get(position).getCrop());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String s1 = decimalFormat.format(orderResultSums.get(position).getQty());
        textQty.setText(s1);
        return view;
    }
}
