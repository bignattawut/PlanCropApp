package th.in.nattawut.plancrop.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import th.in.nattawut.plancrop.AdminActivity;
import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.AddSite;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.MyAlertCrop;
import th.in.nattawut.plancrop.utility.Myconstant;


public class SiteFragment extends Fragment implements LocationListener {

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    //    TextView txtLat;
//    TextView txtLong;
    EditText txtLat, txtLong;

    private String midString, nameString, vidString,vidNameString, latString, longString;

    TextView siteMidName, siteMid;

    @SuppressLint("MissingPermission")
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //setUpTexeShowMid
        setUpTexeShowMid();

        gpsSetUp();

        siteController();

        createToolbal();

        selectSiteVillageFarmer();


    }

    private void siteController() {
        Button button = getView().findViewById(R.id.btnSite);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSite();
            }
        });
    }

    private void addSite() {
        TextView txtMid = getView().findViewById(R.id.textMId);
        TextView textName = getView().findViewById(R.id.textName);
        TextView txtVid = getView().findViewById(R.id.textvid);
        TextView textVidName = getView().findViewById(R.id.textVillageName);

//        TextView txtLat = getView().findViewById(R.id.txtLat);
//        TextView txtLong = getView().findViewById(R.id.txtLong);

        EditText txtLat = getView().findViewById(R.id.txtLat);
        EditText txtLong = getView().findViewById(R.id.txtLong);


        midString = txtMid.getText().toString().trim();
        nameString = textName.getText().toString().trim();
        vidNameString = textVidName.getText().toString().trim();
        vidString = txtVid.getText().toString().trim();
        latString = txtLat.getText().toString().trim();
        longString = txtLong.getText().toString().trim();

        MyAlertCrop myAlertCrop = new MyAlertCrop(getActivity());
        if (midString.isEmpty()) {
            myAlertCrop.onrmaIDialog("โปรดกรอก", "กรุณากรอกชื่อเกษตร");
        } else if (vidString.isEmpty()) {
            myAlertCrop.onrmaIDialog("โปรดกรอก", "กรุณากรอกที่ตั้งแปลง");
        } else if (latString.isEmpty()) {
            myAlertCrop.onrmaIDialog("โปรดกรอก", "กรุณากรอกละติจูล");
        } else if (longString.isEmpty()) {
            myAlertCrop.onrmaIDialog("โปรดกรอก", "กรุณากรอกลองจิจูล");
        } else {
            comfirmUpload();
        }
    }

    private void comfirmUpload() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("ข้อมูลแปลงเพาะปลูก");
        builder.setMessage("ชื่อเกษตรกร = " + nameString + "\n"
                + "ที่ตั้งแปลงเพาะปลูก = " + vidNameString + "\n"
                + "ละติจูด = " + latString + "\n"
                + "ลองจิจูด = " + longString);
        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {//ปุ่มที่1
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }); //
        builder.setPositiveButton("เพิ่ม", new DialogInterface.OnClickListener() {//ปุ่มที่2
            @Override
            public void onClick(DialogInterface dialog, int which) {
                uploadToServer();
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void uploadToServer() {
        try {
            Myconstant myconstant = new Myconstant();
            AddSite addSite = new AddSite(getActivity());
            addSite.execute(midString, vidString, latString, longString,
                    myconstant.getUrladdSite());

            String result = addSite.get();
            Log.d("crop", "result ==> " + result);
            if (Boolean.parseBoolean(result)) {
                getActivity().getSupportFragmentManager().popBackStack();
            } else {
                Toast.makeText(getActivity(), "เพิ่มข้อมูลเรียบร้อย", Toast.LENGTH_LONG).show();
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentAdminFragment, new SiteViewFrament())
                        .addToBackStack(null)
                        .commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpTexeShowMid() {
        if (android.os.Build.VERSION.SDK_INT > 9) { //setup policy เเพื่อมือถือที่มีประปฏิบัติการสูงกว่านีจะไม่สามารถconnectกับโปรโตรคอลได้
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final Spinner spin = getView().findViewById(R.id.SiteFarmerSpinner);
        try {

            Myconstant myconstant = new Myconstant();


            GetData getData = new GetData(getActivity());
            getData.execute(myconstant.getUrlSiteFarmer());

            String jsonString = getData.get();
            Log.d("5/Jan PlanCropSpinner", "JSON ==>" + jsonString);
            JSONArray data = new JSONArray(jsonString);

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;
            map = new HashMap<String, String>();
            map.put("mid", "");
            map.put("name", "");
            MyArrList.add(map);

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("mid", c.getString("mid"));
                map.put("name", c.getString("name"));
                MyArrList.add(map);
            }
            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_sitename,
                    new String[]{"mid", "name"}, new int[]{R.id.textMId, R.id.textName});
            spin.setAdapter(sAdap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectSiteVillageFarmer() {
        if (android.os.Build.VERSION.SDK_INT > 9) { //setup policy เเพื่อมือถือที่มีประปฏิบัติการสูงกว่านีจะไม่สามารถconnectกับโปรโตรคอลได้
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final Spinner spin = getView().findViewById(R.id.vidSiteSpinner);
        try {

            Myconstant myconstant = new Myconstant();
            GetData getData = new GetData(getActivity());
            getData.execute(myconstant.getUrlSelectSiteVillageFarmer());

            String jsonString = getData.get();
            Log.d("5/Jan SelectSiteVillage", "JSON ==>" + jsonString);
            JSONArray data = new JSONArray(jsonString);

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;
            map = new HashMap<String, String>();
            map.put("name", "");
            map.put("vid", "");
            map.put("thai", "");
            MyArrList.add(map);

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("name", c.getString("name"));
                map.put("vid", c.getString("vid"));
                map.put("thai", c.getString("thai"));
                MyArrList.add(map);
            }
            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_sitetextvillage,
                    new String[] {"name","vid","thai"}, new int[] {R.id.textname, R.id.textvid, R.id.textVillageName});
            spin.setAdapter(sAdap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gpsSetUp() {
        txtLat = getView().findViewById(R.id.txtLat);
        txtLong = getView().findViewById(R.id.txtLong);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    private void createToolbal() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarSite);
        ((AdminActivity) getActivity()).setSupportActionBar(toolbar);

        ((AdminActivity) getActivity()).getSupportActionBar().setTitle("แปลงเพาะปลูก");
        ((AdminActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AdminActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_site, container, false);
        return view;
    }

    @Override
    public void onLocationChanged(Location location) {
        txtLat.setText("" + location.getLatitude());
        txtLong.setText("" + location.getLongitude());

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
