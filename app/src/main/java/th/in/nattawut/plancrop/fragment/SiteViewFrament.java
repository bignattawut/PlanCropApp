package th.in.nattawut.plancrop.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import th.in.nattawut.plancrop.AdminActivity;
import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.APIUtils;
import th.in.nattawut.plancrop.utility.DeleteSite;
import th.in.nattawut.plancrop.utility.EditSite;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.Myconstant;
import th.in.nattawut.plancrop.utility.OrderService;
import th.in.nattawut.plancrop.utility.Site;
import th.in.nattawut.plancrop.utility.SiteAdapter;

public class SiteViewFrament extends Fragment implements LocationListener {

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView listView;

    OrderService orderService;
    List<Site> list = new ArrayList<Site>();

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    TextView txtLat;
    TextView txtLong;

    View view;

    ArrayList<HashMap<String, String>> MyArrList,mapArrayList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map,m,selectsite,mapname;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Create Toolbal
        CreateToolbal();

        showMid();

        swipeRefreshLayout();

    }

    private void selectSiteVillageFarmer() {
        if (android.os.Build.VERSION.SDK_INT > 9) { //setup policy เเพื่อมือถือที่มีประปฏิบัติการสูงกว่านีจะไม่สามารถconnectกับโปรโตรคอลได้
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final Spinner spin = view.findViewById(R.id.editvidSiteSpinner);
        try {

            Myconstant myconstant = new Myconstant();
            GetData getData = new GetData(getActivity());
            getData.execute(myconstant.getUrlSelectSiteVillageFarmer());

            String jsonString = getData.get();
            Log.d("5/Jan SelectSiteVillage", "JSON ==>" + jsonString);
            JSONArray data = new JSONArray(jsonString);

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

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

    private void swipeRefreshLayout() {
        mSwipeRefreshLayout = getView().findViewById(R.id.swiRefreshLayou);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showMid();
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                }, 2);
            }
        });
    }

    public void showMid() {
        listView = getView().findViewById(R.id.listViewSite);
        orderService = APIUtils.getService();
        String mid = getActivity().getIntent().getExtras().getString("mid");
        createListView(mid);


    }

    private void createListView(String mid) {
        Call<List<Site>> call = orderService.getSite(mid);
        call.enqueue(new Callback<List<Site>>() {
            @Override
            public void onResponse(Call<List<Site>> call, Response<List<Site>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    listView.setAdapter(new SiteAdapter(getActivity(), R.layout.frm_site_view, list));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            deleteorEditSite(list.get(position).getSno(), list.get(position).getMid(),
                                    list.get(position).getLat(), list.get(position).getLon(), list.get(position).getThai(),list.get(position).getName());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Site>> call, Throwable t) {

            }
        });
    }

    //alertให้เลือกลบหรือแก้ไข
    private void deleteorEditSite(final String sno, final String mid, final String lat, final String lon, final String thai,final String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setIcon(R.drawable.map);
        builder.setTitle("ข้อมูลแปลงเพาะปลูก");
        builder.setMessage("กรุณาเลือก ลบ หรือ ดูข้อมูล ?");
        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("ลบ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editDeleteSite(sno);
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("แก้ไข", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editSite(sno, mid, lat, lon, thai,name);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //แก้ไขประเทพืชเพาะปลูก
    private void editSite(final String sno, String mid, String lat, String lon, String thai,String name) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        //กำหนดหัวเเรื้อง
        builder.setTitle("กำหนดแปลงเพาะปลูกใหม่");
        //กำหนดเนื้อหา
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.edit_site, null);

        mapname = new HashMap<String, String>();
        mapname.put("name",name);
        mapname.put("mid",mid);

        selectSiteVillageFarmer();

        TextView texSiteMid = view.findViewById(R.id.editsiteMid);
        String strTextShowmid = getActivity().getIntent().getExtras().getString("Mid",mid);
        texSiteMid.setText(strTextShowmid);

        TextView Name = view.findViewById(R.id.editsiteMidName);
        String strTextShowName = getActivity().getIntent().getExtras().getString("Name",name);
        Name.setText(strTextShowName);


        EditText edittxtLat = view.findViewById(R.id.edittxtLat);
        String newEditLat = getActivity().getIntent().getExtras().getString("lat", lat);
        edittxtLat.setText(newEditLat);

        EditText edittxtLong = view.findViewById(R.id.edittxtLong);
        String newEditlon = getActivity().getIntent().getExtras().getString("lon",lon);
        edittxtLong.setText(newEditlon);


        builder.setView(view);
        //กำหนดปุ่ม
        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("แก้ไข", new DialogInterface.OnClickListener() {
            //กำหนด Event ให้กับปุ่ม
            @Override
            public void onClick(DialogInterface dialog, int which) {

                TextView editsiteMid = view.findViewById(R.id.editsiteMid);
                TextView editvidSiteSpinner = view.findViewById(R.id.textvid);
                EditText edittxtLat = view.findViewById(R.id.edittxtLat);
                EditText edittxtLong = view.findViewById(R.id.edittxtLong);

                String newTextMidSite = editsiteMid.getText().toString();
                String newTextVid = editvidSiteSpinner.getText().toString();
                String newTextLat = edittxtLat.getText().toString();
                String newTextLong = edittxtLong.getText().toString();

                updateSite(sno, newTextMidSite,
                        newTextVid,
                        newTextLat,
                        newTextLong);
                dialog.dismiss();
            }
        });
        builder.show();
    }

//    private void editSite(final String sno, String mid, String lat, String lon, String thai) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
//        builder.setCancelable(false);
//        //กำหนดหัวเเรื้อง
//        builder.setTitle("แปลงเพาะปลูกใหม่");
//        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
//        final View view = layoutInflater.inflate(R.layout.edit_site, null);
//
//        TextView texSiteMid = view.findViewById(R.id.editsiteMid);
//        String strTextShowmid = getActivity().getIntent().getExtras().getString("mid");
//        texSiteMid.setText(strTextShowmid);
//
//        TextView texSiteName = view.findViewById(R.id.editsiteMidName);
//        String strTextShowName = getActivity().getIntent().getExtras().getString("name");
//        texSiteName.setText(strTextShowName);
//
//        TextView siteVidThai = view.findViewById(R.id.editvidSiteSpinner);
//        String strTextShowVidThai = getActivity().getIntent().getExtras().getString("thai", thai);
//        siteVidThai.setText(strTextShowVidThai);
//
//        TextView siteVid = view.findViewById(R.id.editvidSite);
//        String strTextShowVid = getActivity().getIntent().getExtras().getString("vid");
//        siteVid.setText(strTextShowVid);
//
//        txtLat = view.findViewById(R.id.edittxtLat);
//        txtLong = view.findViewById(R.id.edittxtLong);
//
//        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//
//
//        builder.setView(view);
//
//        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        builder.setPositiveButton("แก้ไข", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                TextView editsiteMid = view.findViewById(R.id.editsiteMid);
//                TextView editvidSiteSpinner = view.findViewById(R.id.editvidSite);
//                TextView edittxtLat = view.findViewById(R.id.edittxtLat);
//                TextView edittxtLong = view.findViewById(R.id.edittxtLong);
//
//                String newTextMidSite = editsiteMid.getText().toString();
//                String newTextVid = editvidSiteSpinner.getText().toString();
//                String newTextLat = edittxtLat.getText().toString();
//                String newTextLong = edittxtLong.getText().toString();
//
//                updateSite(sno, newTextMidSite,
//                        newTextVid,
//                        newTextLat,
//                        newTextLong);
//            }
//        });
//        builder.show();
//    }

    private void updateSite(String sno, String newTextMidSite, String newTextVid, String newTextLat, String newTextLong) {
        Myconstant myconstant = new Myconstant();
        try {
            EditSite editSite = new EditSite(getActivity());
            editSite.execute(sno, newTextMidSite,
                    newTextVid,
                    newTextLat,
                    newTextLong,
                    myconstant.getUrlEditSite());
            if (Boolean.parseBoolean(editSite.get())) {

            } else {
                Toast.makeText(getActivity(), "แก้ไขข้อมูลสำเร็จ",
                        Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //alertให้เลือกจะลบรายการหรือไม่
    private void editDeleteSite(final String sno) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle("ต้องการลบรายการนี้หรือไม่?");
        builder.setNegativeButton("ไม่ใช่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteSite(sno);
                dialog.dismiss();
            }
        });
        builder.show();

    }

    //ลบรายการการเพาะปลูก
    private void deleteSite(String sno) {
        Myconstant myconstant = new Myconstant();
        try {
            DeleteSite deleteSite = new DeleteSite(getActivity());
            deleteSite.execute(sno, myconstant.getUrlDeleteSite());

            if (Boolean.parseBoolean(deleteSite.get())) {
            } else {
                Toast.makeText(getActivity(), "ลบแปลงเพาะปลูก", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemlinkUrl) {
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contentAdminFragment, new SiteFragment())
                            .addToBackStack(null)
                            .commit();
                    return false;
                }
            });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.manu_register, menu);

    }

    private void CreateToolbal() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarSite);
        ((AdminActivity) getActivity()).setSupportActionBar(toolbar);

        ((AdminActivity) getActivity()).getSupportActionBar().setTitle("ข้อมูลแปลงเพาะปลูก");
        //((MainActivity)getActivity()).getSupportActionBar().setSubtitle("ddbdbvd");

        ((AdminActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AdminActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_view_site, container, false);
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
