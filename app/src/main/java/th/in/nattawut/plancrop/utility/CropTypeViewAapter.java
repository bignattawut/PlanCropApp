package th.in.nattawut.plancrop.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import th.in.nattawut.plancrop.R;

public class CropTypeViewAapter extends BaseAdapter {

    private Context context;
    private String[] tidString, cropTypeString;

    public CropTypeViewAapter(Context context,
                              String[] tidString,
                              String[] cropTypeString) {
        this.context = context;
        this.tidString = tidString;
        this.cropTypeString = cropTypeString;
    }

    @Override
    public int getCount() {
        return tidString.length;
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
        View view = layoutInflater.inflate(R.layout.frm_croptype_view, parent, false);

        TextView textTidView = view.findViewById(R.id.textTid);
        TextView textCropTypeView = view.findViewById(R.id.textCropType);

        textTidView.setText(tidString[position]);
        textCropTypeView.setText(cropTypeString[position]);
        return view;
    }
}