package th.in.nattawut.plancrop.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerImage;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.internal.bind.MapTypeAdapterFactory;
import com.squareup.okhttp.OkHttpClient;
import com.tuann.floatingactionbuttonexpandable.FloatingActionButtonExpandable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.APIUtils;
import th.in.nattawut.plancrop.utility.AddAmpur;
import th.in.nattawut.plancrop.utility.AddProvince;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.Myconstant;
import th.in.nattawut.plancrop.utility.OrderService;
import th.in.nattawut.plancrop.utility.PlanResultAdpter;
import th.in.nattawut.plancrop.utility.PlantResult;
import th.in.nattawut.plancrop.utility.PlantResultAdpter;
import th.in.nattawut.plancrop.utility.PlantResultSite;
import th.in.nattawut.plancrop.utility.PlantResultSiteAdpter;

//implements OnMapReadyCallback
public class PlantResultViewFragment extends Fragment {


    ListView listView, listViewSite;
    OrderService orderService;
    List<PlantResult> list = new ArrayList<PlantResult>();
    List<PlantResultSite> listsite = new ArrayList<PlantResultSite>();


    ImageView selctDate;
    TextView date;
    DatePickerDialog dataPickerDialog;
    Calendar calendar;
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ArrayList<String> arrProvince = new ArrayList<>();
    private ArrayList<String> arrProvinceID = new ArrayList<>();
    private Spinner spProvince, spAmphur, spSubDistrice;


    private Double Latitude = 0.00;
    private Double Longitude = 0.00;
    ArrayList<HashMap<String, String>> mapArrayList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map;
    MapView mapGoogle;
    MapTypeAdapterFactory mapTypeAdapterFactory;
    GoogleMap googleMap;
    String name;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = getView().findViewById(R.id.listViewPlantResult);
        listViewSite = getView().findViewById(R.id.listViewPlantResultSite);

        orderService = APIUtils.getService();
        spProvince = getView().findViewById(R.id.spProvince);
        Province();
        spAmphur = getView().findViewById(R.id.spAmphur);
        spSubDistrice = getView().findViewById(R.id.spSubDistrice);


        edateController();

        pdateController();

//        SwipeRefreshLayout();

        nameSpinner();

        cropTypeSpinner();

        planFarmerSpinner();

        cropController();



    }




    private void SwipeRefreshLayout() {
        mSwipeRefreshLayout = getView().findViewById(R.id.swiRefreshLayoutPlantResult);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void Province() {
        try {
            Myconstant myconstant = new Myconstant();
            GetData getData = new GetData(getActivity());
            getData.execute(myconstant.getUrlProvince());

            String jsonString = getData.get();
            JSONArray data = new JSONArray(jsonString);

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("pid", c.getString("pid"));
                map.put("thai", c.getString("thai"));
                MyArrList.add(map);

            }
            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_province_result,
                    new String[]{"pid", "thai"}, new int[]{R.id.pid, R.id.pidthai});
            spProvince.setAdapter(sAdap);
            spProvince.setSelection(25);

            spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (spProvince.getSelectedItem() != null) {
                        Amphur(MyArrList.get(position).get("pid"));
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Amphur(String province) {
        try {
            Myconstant myconstant = new Myconstant();
            AddProvince addProvince = new AddProvince(getActivity());
            addProvince.execute(province, myconstant.getUrlAmphur());

            String jsonString = addProvince.get();
            JSONArray data = new JSONArray(jsonString);

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;
            map = new HashMap<String, String>();
            map.put("did", "");
            map.put("thai", "");
            MyArrList.add(map);

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("did", c.getString("did"));
                map.put("thai", c.getString("thai"));
                MyArrList.add(map);

            }
            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_amphur_result,
                    new String[]{"did", "thai"}, new int[]{R.id.did, R.id.didthai});
            spAmphur.setAdapter(sAdap);
            //spAmphur.setSelection(1);

            spAmphur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (spAmphur.getSelectedItem() != null) {
                        SubDistrice(MyArrList.get(position).get("did"));

                        //arrSid.clear();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SubDistrice(String amphur) {
        try {
            Myconstant myconstant = new Myconstant();
            AddAmpur addAmpur = new AddAmpur(getActivity());
            addAmpur.execute(amphur, myconstant.getUrlSid());

            Log.d("am", "aa" + amphur);
            String jsonString = addAmpur.get();
            JSONArray data = new JSONArray(jsonString);

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;
            map = new HashMap<String, String>();
            map.put("sid", "");
            map.put("thai", "");
            MyArrList.add(map);

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("sid", c.getString("sid"));
                map.put("thai", c.getString("thai"));
                MyArrList.add(map);
            }
            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_subdistrice_result,
                    new String[]{"sid", "thai"}, new int[]{R.id.sid, R.id.sidthai});
            spSubDistrice.setAdapter(sAdap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void edateController() {
        final TextView sdate = getActivity().findViewById(R.id.sdate);
        ImageView selctDate = getActivity().findViewById(R.id.imageViewDatesdate);

        Calendar a =  Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String s = dateFormat.format(a.getTime());
        sdate.setText(s);

        selctDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                //final Date cha = calendar.getTime();

                dataPickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int y, int m, int d) {
                                //date.setText(y + "/" + (m + 1) + "/" + d);
                                sdate.setText(y + "/" + (m + 1) + "/" + d);
                                //DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.UK);

                            }
                        }, year, month, day);
                //dataPickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dataPickerDialog.show();
            }
        });
    }

    private void pdateController() {
        final TextView edate = getActivity().findViewById(R.id.edate);
        ImageView selctDate = getActivity().findViewById(R.id.imageViewDateedate);

        Calendar a =  Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String s = dateFormat.format(a.getTime());
        edate.setText(s);

        selctDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                //final Date cha = calendar.getTime();

                dataPickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int y, int m, int d) {
                                //date.setText(y + "/" + (m + 1) + "/" + d);
                                edate.setText(y + "/" + (m + 1) + "/" + d);
                                //DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.UK);

                            }
                        }, year, month, day);
                //dataPickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dataPickerDialog.show();
            }
        });
    }

    private void nameSpinner() {
        if (android.os.Build.VERSION.SDK_INT > 9) { //setup policy เเพื่อมือถือที่มีประปฏิบัติการสูงกว่านีจะไม่สามารถconnectกับโปรโตรคอลได้
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final Spinner spin = getView().findViewById(R.id.midSpinner);

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
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_sitename_result,
                    new String[]{"mid", "name"}, new int[]{R.id.textMId, R.id.textName});
            spin.setAdapter(sAdap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cropTypeSpinner() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final Spinner spin = getView().findViewById(R.id.croptypespinner);
        try {
            Myconstant myconstant = new Myconstant();
            GetData getData = new GetData(getActivity());
            getData.execute(myconstant.getUrlCropType());

            String jsonString = getData.get();
            Log.d("5/Jan CropType", "JSON ==>" + jsonString);
            JSONArray data = new JSONArray(jsonString);

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;
            map = new HashMap<String, String>();
            map.put("tid", "");
            map.put("croptype", "");
            MyArrList.add(map);

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("tid", c.getString("tid"));
                map.put("croptype", c.getString("croptype"));
                MyArrList.add(map);
            }
            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_crop_result,
                    new String[]{"tid", "croptype"}, new int[]{R.id.textTID, R.id.textCropType});
            spin.setAdapter(sAdap);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //
    private void planFarmerSpinner() {
        if (android.os.Build.VERSION.SDK_INT > 9) { //setup policy เเพื่อมือถือที่มีประปฏิบัติการสูงกว่านีจะไม่สามารถconnectกับโปรโตรคอลได้
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final Spinner spin = getView().findViewById(R.id.crop);
        try {
            Myconstant myconstant = new Myconstant();
            GetData getData = new GetData(getActivity());
            getData.execute(myconstant.getUrlCrop());

            String jsonString = getData.get();
            Log.d("5/Jan PlanCropSpinner", "JSON ==>" + jsonString);
            JSONArray data = new JSONArray(jsonString);

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;
            map = new HashMap<String, String>();
            map.put("cid", "");
            map.put("crop", "");
            MyArrList.add(map);

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("cid", c.getString("cid"));
                map.put("crop", c.getString("crop"));
                MyArrList.add(map);

            }
            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_plancrop_reslt,
                    new String[]{"cid", "crop"}, new int[]{R.id.textPlanCidSpinner, R.id.textPlanCropSpinner});
            spin.setAdapter(sAdap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cropController() {
        Button button = getView().findViewById(R.id.seletePlantResult);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();

            }
        });
    }

    private void add() {

        TextView province = getView().findViewById(R.id.pid);
        TextView amphur = getView().findViewById(R.id.did);
        TextView subDistrice = getView().findViewById(R.id.sid);
        TextView sdate = getView().findViewById(R.id.sdate);
        TextView edate = getView().findViewById(R.id.edate);
        TextView nameSpinner = getView().findViewById(R.id.textMId);
        TextView textTypeSpinner = getView().findViewById(R.id.textTID);
        TextView textCropSpinner = getView().findViewById(R.id.textPlanCidSpinner);

        String provinceString = province.getText().toString().trim();
        String amphurString = amphur.getText().toString().trim();
        String subDistriceString = subDistrice.getText().toString().trim();
        String sdateString = sdate.getText().toString().trim();
        String edateString = edate.getText().toString().trim();
        String nameString = nameSpinner.getText().toString().trim();
        String croptypeString = textTypeSpinner.getText().toString().trim();
        String cropString = textCropSpinner.getText().toString().trim();

        selectPlantReportall(provinceString, amphurString, subDistriceString, sdateString, edateString, nameString, croptypeString, cropString);
        selectPlantReportSite(provinceString, amphurString, subDistriceString, sdateString, edateString, nameString, croptypeString, cropString);

    }

    private void selectPlantReportall(String provinceString, String amphurString, String subDistriceString, String sdateString, String edateString, String nameString, String croptypeString, String cropString) {
        Call<List<PlantResult>> call = orderService.getPlantResult(provinceString, amphurString, subDistriceString, sdateString, edateString, nameString, croptypeString, cropString);
        call.enqueue(new Callback<List<PlantResult>>() {
            @Override
            public void onResponse(Call<List<PlantResult>> call, Response<List<PlantResult>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    listView.setAdapter(new PlantResultAdpter(getActivity(), R.layout.frm_plantresult_view, list));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //selectResult();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<PlantResult>> call, Throwable t) {

            }
        });
    }

    private void selectPlantReportSite(String provinceString, String amphurString, String subDistriceString, String sdateString, String edateString, String nameString, String croptypeString, String cropString) {
        Call<List<PlantResultSite>> call = orderService.getPlantResultSite(provinceString, amphurString, subDistriceString, sdateString, edateString, nameString, croptypeString, cropString);
        call.enqueue(new Callback<List<PlantResultSite>>() {
            @Override
            public void onResponse(Call<List<PlantResultSite>> call, Response<List<PlantResultSite>> response) {


                if (response.isSuccessful()) {
                    listsite = response.body();
                    //listViewSite.setAdapter(new PlantResultSiteAdpter(getActivity(), R.layout.frm_plantresultmap_view, listsite));
                    listViewSite.setAdapter(new PlantResultSiteAdpter(getActivity(), R.layout.frm_plantresultmap_view, listsite));
                }

                listsite = response.body();
                initMakerker(listsite);

            }

            @Override
            public void onFailure(Call<List<PlantResultSite>> call, Throwable t) {

            }
        });
    }

    private void initMakerker(List<PlantResultSite> mListMarker) {

        OnMapReadyCallback callback = new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                LatLng coordinate = new LatLng(Latitude, Longitude);
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 17));
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setCompassEnabled(true);
                for (int i = 0; i < mListMarker.size(); i++){
                    Latitude = Double.parseDouble(mListMarker.get(i).getLat().toString());
                    Longitude = Double.parseDouble(mListMarker.get(i).getLon().toString());
                    name = mListMarker.get(i).getName().toString();

                    MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(Latitude,Longitude))
                            .title(name);
                    googleMap.addMarker(markerOptions);

                }

                googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Nullable
                    @Override
                    public View getInfoContents(@NonNull Marker marker) {
                        View v = null;
                        try {
                            v = getLayoutInflater().inflate(R.layout.frm_plantresultmap_view,null);

                            for (int i = 0; i < mListMarker.size(); i++) {
                                TextView textView = v.findViewById(R.id.mid);
                                TextView vidThai = v.findViewById(R.id.vidThai);
                                TextView tel = v.findViewById(R.id.tel);
                                TextView crop = v.findViewById(R.id.crop);
                                TextView yield = v.findViewById(R.id.yield);

                               // textView.setText(mListMarker.get(i).getName());
                                textView.setText(marker.getTitle());
                                vidThai.setText(mListMarker.get(i).getThai());
                                tel.setText(mListMarker.get(i).getTel());
                                crop.setText(mListMarker.get(i).getCrop());
                                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                                String s1 = decimalFormat.format(mListMarker.get(i).getYield());
                                yield.setText(s1);


                            }

                        } catch (Exception e) {
                            System.out.print(e.getMessage());
                        }
                        return v;
                    }

                    @Nullable
                    @Override
                    public View getInfoWindow(@NonNull Marker marker) {
                        return null;
                    }
                });



            }
        };

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapGoogle);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

    }



    private void selectResult() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setIcon(R.drawable.planhome);
        builder.setTitle("ข้อมูลแปลงเพาะปลูก");
        builder.setMessage("ต้องการดูข้อมูลแปลงเพาะปลูก หรือ ไม่");
        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("ดูรายละเอียด", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectResultFarmer();
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void selectResultFarmer() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
        builder.setCancelable(false);
        //กำหนดหัวเเรื้อง
        builder.setTitle("แปลงเพาะปลูก");
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.frm_plantresultmap_view, null);


        builder.setView(view);
        builder.setNegativeButton("ปิด", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_view_plantresultsite, container, false);
        return view;
    }
}


