package th.in.nattawut.plancrop.utility;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import th.in.nattawut.plancrop.R;

public class PlantPicAdpter2 extends ArrayAdapter<PlantActivity> {


    Myconstant myconstant = new Myconstant();
    private Context context;
    private List<PlantActivity> plantActivities;

    public PlantPicAdpter2(@NonNull Context context, @LayoutRes int resource, @NonNull List<PlantActivity> objects) {
        super(context, resource, objects);
        this.context = context;
        this.plantActivities = objects;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.frm_plantpicture_view,parent,false);


        TextView pdateTextView = view.findViewById(R.id.txtName);
        TextView descriptionTextView = view.findViewById(R.id.txtMessage);
        ImageView imageView = view.findViewById(R.id.imvIcon);

        pdateTextView.setText(plantActivities.get(position).getPdate());
        descriptionTextView.setText(plantActivities.get(position).getDescription());
        Picasso.get()
                .load("https://www.plancropapp.online/android/php/picture/activity/"+plantActivities.get(position).getPicno()+".jpg")
                .resize(150, 150)
                .into(imageView);

        return view;
    }
}


