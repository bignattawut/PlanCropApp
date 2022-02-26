package th.in.nattawut.plancrop.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import th.in.nattawut.plancrop.R;

public class PlantPicAdpter1 extends BaseAdapter {

    private Context context;
    private String[] picnoStringArrayList,
            pdateStringArrayList, descriptionStringArrayList;

    public PlantPicAdpter1(Context context,
                           String[] picnoStringArrayList,
                           String[] pdateStringArrayList,
                           String[] descriptionStringArrayList) {
        this.context = context;
        this.picnoStringArrayList = picnoStringArrayList;
        this.pdateStringArrayList = pdateStringArrayList;
        this.descriptionStringArrayList = descriptionStringArrayList;
    }

    @Override
    public int getCount() {
        return picnoStringArrayList.length;
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
        View view = layoutInflater.inflate(R.layout.frm_plantpicture_view, parent, false);

        TextView pdateTextView = view.findViewById(R.id.txtName);
        TextView descriptionTextView = view.findViewById(R.id.txtMessage);
        ImageView imageView = view.findViewById(R.id.imvIcon);

        pdateTextView.setText(pdateStringArrayList[position]);
        descriptionTextView.setText(descriptionStringArrayList[position]);
        Picasso.get()
                .load("http://192.168.1.122/android/php/picture/activity/"+picnoStringArrayList[position]+".jpg")
                .resize(150, 150)
                .into(imageView);

        return view;
    }
}