package th.in.nattawut.plancrop.fragment;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.Myconstant;

public class maptest extends Fragment {


    private Double Latitude = 0.00;
    private Double Longitude = 0.00;
    ArrayList<HashMap<String, String>> mapArrayList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map;
    MapView mapView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mapGoogle();
    }

    private void mapGoogle() {
         if (android.os.Build.VERSION.SDK_INT > 9) { //SDK มาก9 จะไม่สามารถconnextได้
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            Myconstant myconstant = new Myconstant();
            GetData getData = new GetData(getActivity());
            //getData.execute(myconstant.getUrlmap());
            getData.execute(myconstant.getUrlselectSite());

            String jsonString = getData.get();
            Log.d("18/Jan map", "JSON ==>" + jsonString);
            JSONArray data = new JSONArray(jsonString);

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("name", c.getString("name"));
                map.put("lat", c.getString("lat"));
                map.put("lon", c.getString("lon"));
                mapArrayList.add(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        OnMapReadyCallback callback = new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                Latitude = Double.parseDouble(mapArrayList.get(0).get("lat").toString());
                Longitude = Double.parseDouble(mapArrayList.get(0).get("lon").toString());
                LatLng coordinate = new LatLng(Latitude,Longitude);
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate,17));

                for (int i = 0; i < mapArrayList.size(); i++){
                    Latitude = Double.parseDouble(mapArrayList.get(i).get("lat").toString());
                    Longitude = Double.parseDouble(mapArrayList.get(i).get("lon").toString());
                    String name = mapArrayList.get(i).get("name").toString();

                    MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(Latitude,Longitude)).title(name);
                    googleMap.addMarker(markerOptions);

                }
            }
        };

        mapView = (MapView) getView().findViewById(R.id.maptest);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(callback);
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.maptest, container, false);
        return view;
    }
}
