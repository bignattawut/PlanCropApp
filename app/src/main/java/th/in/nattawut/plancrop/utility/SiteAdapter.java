package th.in.nattawut.plancrop.utility;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import th.in.nattawut.plancrop.R;

public class SiteAdapter extends ArrayAdapter<Site> {

    private Context context;
    private List<Site> sites;

    public SiteAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Site> objects) {
        super(context, resource, objects);
        this.context = context;
        this.sites = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.frm_site_view, parent, false);

        TextView textUsername = view.findViewById(R.id.textUsername);
        TextView txtlang = view.findViewById(R.id.txtlang);
        TextView txtVid = view.findViewById(R.id.txtVid);
        TextView txtlat = view.findViewById(R.id.txtlat);

        textUsername.setText(sites.get(position).getName());
        txtlang.setText(sites.get(position).getLon());
        txtVid.setText(sites.get(position).getThai());
        txtlat.setText(sites.get(position).getLat());

        return view;
    }
}