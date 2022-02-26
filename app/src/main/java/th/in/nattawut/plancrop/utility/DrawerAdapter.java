package th.in.nattawut.plancrop.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import th.in.nattawut.plancrop.R;

public class DrawerAdapter extends BaseAdapter {

    private Context context;
    private int[] iconInts;
    private String[] titileStrings;

    ////
    //private int[] ion;
    //private String[] titil;
    ///

    public DrawerAdapter(Context context, int[] iconInts, String[] titile){
        this.context = context;
        this.iconInts = iconInts;
        this.titileStrings = titile;

    }

    /*public DrawerAdapter(Context context, int[] iconInts, String[] titileStrings, int[] ion, String[] titil) {
        this.context = context;
        this.iconInts = iconInts;
        this.titileStrings = titileStrings;
        this.ion = ion;
        this.titil = titil;
    }*/

    @Override
    public int getCount() {
        return titileStrings.length;
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
        View view = layoutInflater.inflate(R.layout.listview_drawer, parent, false);

        ImageView imageView = view.findViewById(R.id.draIcon);
        imageView.setImageResource(iconInts[position]);

        TextView textView = view.findViewById(R.id.txtTitle);
        textView.setText(titileStrings[position]);

        /*ImageView imageView1 = view.findViewById(R.id.ion);
        imageView1.setImageResource(ion[position]);

        TextView textView1 = view.findViewById(R.id.titil);
        textView1.setText(titil[position]);*/
        return view;
    }
}
