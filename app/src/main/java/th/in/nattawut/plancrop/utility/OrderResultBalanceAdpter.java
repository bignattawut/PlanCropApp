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

public class OrderResultBalanceAdpter extends ArrayAdapter<OrderResultBalance> {


    private Context context;
    private List<OrderResultBalance> orderResultBalances;

    public OrderResultBalanceAdpter(@NonNull Context context, @LayoutRes int resource, @NonNull List<OrderResultBalance> objects) {
        super(context, resource, objects);
        this.context = context;
        this.orderResultBalances = objects;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.frm_orderresultbalance_view,parent,false);



        TextView textCrop = view.findViewById(R.id.textCropOrderResult);
        TextView textQty = view.findViewById(R.id.textOrderResultQty);
        TextView textOrderResultBalance = view.findViewById(R.id.textOrderResultBalance);


        textCrop.setText(orderResultBalances.get(position).getCrop());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String s1 = decimalFormat.format(orderResultBalances.get(position).getQty());
        textQty.setText(s1);

        DecimalFormat decimalFormat1 = new DecimalFormat("###,###,###");
        String s2 = decimalFormat1.format(orderResultBalances.get(position).getYield());
        textOrderResultBalance.setText(s2);

        return view;
    }
}