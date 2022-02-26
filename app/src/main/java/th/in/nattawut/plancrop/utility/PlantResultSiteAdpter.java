package th.in.nattawut.plancrop.utility;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.List;

import th.in.nattawut.plancrop.R;

public class PlantResultSiteAdpter extends ArrayAdapter<PlantResultSite> {

    private Double Latitude = 0.00;
    private Double Longitude = 0.00;

    private Context context;
    private List<PlantResultSite> plantResultSites;

    public PlantResultSiteAdpter(@NonNull Context context, @LayoutRes int resource, @NonNull List<PlantResultSite> objects) {
        super(context, resource, objects);
        this.context = context;
        this.plantResultSites = objects;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.frm_plantresultmap_view,parent,false);



        TextView mid = view.findViewById(R.id.mid);
        TextView vid = view.findViewById(R.id.vidThai);
        TextView tel = view.findViewById(R.id.tel);
        TextView crop = view.findViewById(R.id.crop);
        TextView yield = view.findViewById(R.id.yield);
//        TextView lat = view.findViewById(R.id.lat);
//        TextView lon = view.findViewById(R.id.lon);
        mid.setText(plantResultSites.get(position).getName());
        vid.setText(plantResultSites.get(position).getThai());
        tel.setText(plantResultSites.get(position).getTel());
        crop.setText(plantResultSites.get(position).getCrop());
//        lat.setText(plantResultSites.get(position).getLat());
//        lon.setText(plantResultSites.get(position).getLon());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String s1 = decimalFormat.format(plantResultSites.get(position).getYield());
        yield.setText(s1);


//        OnMapReadyCallback callback = new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap googleMap) {
//
////                Latitude = Double.parseDouble(mapArrayList.get(0).get("lat").toString());
////                Longitude = Double.parseDouble(mapArrayList.get(0).get("lon").toString());
//                Latitude = Double.parseDouble(plantResultSites.get(position).getLat().toString());
//                Longitude = Double.parseDouble(plantResultSites.get(position).getLon().toString());
//
//                LatLng coordinate = new LatLng(Latitude,Longitude);
//                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate,17));
//
//                for (int i = 0; i < plantResultSites.size(); i++){
////                    Latitude = Double.parseDouble(plantResultSites.get(i).get("lat").toString());
////                    Longitude = Double.parseDouble(plantResultSites.get(i).get("lon").toString());
//                    Latitude = Double.parseDouble(plantResultSites.get(i).getLat().toString());
//                    Longitude = Double.parseDouble(plantResultSites.get(i).getLon().toString());
//                    String name = plantResultSites.get(i).getName().toString();
//
//                    MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(Latitude,Longitude)).title(name);
//                    googleMap.addMarker(markerOptions);
//
//                }
//            }
//        };
//        MapView mapView = view.findViewById(R.id.map1);
//        if (mapView != null) {
//            mapView.onCreate(null);
//            mapView.onResume();
//            mapView.getMapAsync(callback);
//        }



        return view;
    }
}