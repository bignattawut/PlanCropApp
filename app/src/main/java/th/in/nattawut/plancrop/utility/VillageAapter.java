package th.in.nattawut.plancrop.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import th.in.nattawut.plancrop.R;

public class VillageAapter extends BaseAdapter {

    private Context context;
    private String[] vidString, thaiString;

    public VillageAapter(Context context,
                              String[] tidString,
                              String[] cropTypeString) {
        this.context = context;
        this.vidString = tidString;
        this.thaiString = cropTypeString;
    }

    @Override
    public int getCount() {
        return vidString.length;
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
        View view = layoutInflater.inflate(R.layout.spinner_sitename, parent, false);

        TextView textVidSite = view.findViewById(R.id.textMId);
        TextView textVidSiteName = view.findViewById(R.id.textName);

        textVidSite.setText(vidString[position]);
        textVidSiteName.setText(thaiString[position]);
        return view;
    }
}
