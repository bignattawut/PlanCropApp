package th.in.nattawut.plancrop.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import th.in.nattawut.plancrop.AdminActivity;
import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.APIUtils;
import th.in.nattawut.plancrop.utility.AddAmpur;
import th.in.nattawut.plancrop.utility.AddProvince;
import th.in.nattawut.plancrop.utility.AddSiteFarmer;
import th.in.nattawut.plancrop.utility.DeletePlant;
import th.in.nattawut.plancrop.utility.EditPlant;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.Myconstant;
import th.in.nattawut.plancrop.utility.OrderService;
import th.in.nattawut.plancrop.utility.Plant;
import th.in.nattawut.plancrop.utility.PlantAdpter1;

public class PlantViewFragment1 extends Fragment {

    ListView listView;
    OrderService orderService;
    List<Plant> list = new ArrayList<Plant>();

    ImageView selctDate;
    TextView date;
    DatePickerDialog dataPickerDialog;
    Calendar calendar;

    SwipeRefreshLayout mSwipeRefreshLayout;

    View view;

    private ArrayList<String> arrmid = new ArrayList<>();
    private ArrayList<String> arrname = new ArrayList<>();

    Spinner spin,selectMap;
    ArrayList<HashMap<String, String>> MyArrList,mapArrayList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map,m,selectsite,mapname;
    private Spinner spProvince,spAmphur, spSubDistrice;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CreateToolbal();

        swiRefreshLayou();

        PlantController();

        //pdateController
        pdateController();

        cropTypeSpinner();

        planFarmerSpinner();


        listView = getView().findViewById(R.id.listViewPlant);
        orderService = APIUtils.getService();
        spProvince = getView().findViewById(R.id.spProvincePlant);
        Province();
        spAmphur = getView().findViewById(R.id.spAmphurPlant);

        spSubDistrice = getView().findViewById(R.id.spDistricePlant);



    }
    private void swiRefreshLayou() {
        mSwipeRefreshLayout = getView().findViewById(R.id.swiRefreshLayouPlant);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                add();
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
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_province_plantadmin,
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

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void Amphur(String province) {
        try {
            Myconstant myconstant = new Myconstant();
            AddProvince addProvince = new AddProvince(getActivity());
            addProvince.execute(province,myconstant.getUrlAmphur());

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
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_amphur_plantadmin,
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

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void SubDistrice(String amphur) {
        try {
            Myconstant myconstant = new Myconstant();
            AddAmpur addAmpur = new AddAmpur(getActivity());
            addAmpur.execute(amphur,myconstant.getUrlSid());

            Log.d("am","aa"+ amphur);
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
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_subdistrice_plantadmin,
                    new String[]{"sid", "thai"}, new int[]{R.id.sid, R.id.sidthai});
            spSubDistrice.setAdapter(sAdap);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    ////
    private void cropTypeSpinner(){
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

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("tid", c.getString("tid"));
                map.put("croptype", c.getString("croptype"));
                MyArrList.add(map);
            }
            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_crop_plantadmin,
                    new String[] {"tid", "croptype"}, new int[] {R.id.textTID, R.id.textCropType});
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
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_plancrop_plantadmin,
                    new String[]{"cid", "crop"}, new int[]{R.id.textPlanCidSpinner, R.id.textPlanCropSpinner});
            spin.setAdapter(sAdap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    ////

    private void pdateController() {
        final TextView sdate = getActivity().findViewById(R.id.sdate);
        ImageView selctDate = getActivity().findViewById(R.id.imageViewDatepdate);
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
                        }, year,month,day);
                //dataPickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dataPickerDialog.show();
            }
        });
    }

    private void editpdateController() {
        date = view.findViewById(R.id.EditMyDate);
        selctDate = view.findViewById(R.id.EditImageViewDate);
        selctDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dataPickerDialog = new DatePickerDialog(getActivity(),/*AlertDialog.THEME_DEVICE_DEFAULT_DARK,*///Theme
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int y, int m, int d) {
                                date.setText(y + "/" + (m + 1) + "/" + d);
                            }
                        }, day, month, year);
                dataPickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());//วันที่ปัจจุบัน
                dataPickerDialog.show();
            }
        });
    }

    private void selectcroptype1() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        selectMap = view.findViewById(R.id.EditPlantCropSpinner);
        try {
            Myconstant myconstant = new Myconstant();
            GetData getData = new GetData(getActivity());
            getData.execute(myconstant.getUrlCrop());

            String jsonString = getData.get();
            Log.d("5/Jan CropType", "JSON ==>" + jsonString);
            JSONArray data = new JSONArray(jsonString);

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                m = new HashMap<String, String>();
                m.put("cid", c.getString("cid"));
                m.put("crop", c.getString("crop"));
                mapArrayList.add(m);
            }

            final SimpleAdapter s;
            s = new SimpleAdapter(getActivity(), mapArrayList, R.layout.spinner_plancrop,
                    new String[]{"cid", "crop"}, new int[]{R.id.textPlanCidSpinner, R.id.textPlanCropSpinner});
            selectMap.setAdapter(s);
            selectMap.setSelection(mapArrayList.indexOf(map));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectFarmer() {
        if (android.os.Build.VERSION.SDK_INT > 9) { //setup policy เเพื่อมือถือที่มีประปฏิบัติการสูงกว่านีจะไม่สามารถconnectกับโปรโตรคอลได้
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final Spinner spin = view.findViewById(R.id.EditPlantName);
        try {

            Myconstant myconstant = new Myconstant();


            GetData getData = new GetData(getActivity());
            getData.execute(myconstant.getUrlSiteFarmer());

            String jsonString = getData.get();
            Log.d("5/Jan PlanCropSpinner", "JSON ==>" + jsonString);
            JSONArray data = new JSONArray(jsonString);

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("mid", c.getString("mid"));
                map.put("name", c.getString("name"));

                arrmid.add(c.getString("mid"));
                arrname.add(c.getString("name"));
                MyArrList.add(map);
            }
            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_sitename,
                    new String[]{"mid", "name"}, new int[]{R.id.textMId, R.id.textName});
            spin.setAdapter(sAdap);
            spin.setSelection(MyArrList.indexOf(mapname));

        } catch (Exception e) {
            e.printStackTrace();
        }

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spin.getSelectedItem() != null) {
                    siteController(arrmid.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void siteController(String mid) {
        if (android.os.Build.VERSION.SDK_INT > 9) { //setup policy เเพื่อมือถือที่มีประปฏิบัติการสูงกว่านีจะไม่สามารถconnectกับโปรโตรคอลได้
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final Spinner spin = view.findViewById(R.id.EditSiteName);
        try {

            Myconstant myconstant = new Myconstant();
            AddSiteFarmer addSiteFarmer = new AddSiteFarmer(getActivity());
            addSiteFarmer.execute(mid,myconstant.getSelectsitefarmer());

            String jsonString = addSiteFarmer.get();
            Log.d("5/Jan PlanCropSpinner", "JSON ==>" + jsonString);
            JSONArray data = new JSONArray(jsonString);

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("sno", c.getString("sno"));
                map.put("thai", c.getString("thai"));
                MyArrList.add(map);
            }

            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_site,
                    new String[] {"sno", "thai"}, new int[] {R.id.textPlantSiteSnoSpinner, R.id.textPlantThaiSpinner});
            spin.setAdapter(sAdap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void PlantController() {
        Button button = getView().findViewById(R.id.seletePlant);
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
        TextView sid = getView().findViewById(R.id.sid);
        TextView textTypeSpinner = getView().findViewById(R.id.textTID);
        TextView textCropSpinner = getView().findViewById(R.id.textPlanCidSpinner);
        TextView sdate = getView().findViewById(R.id.sdate);

        String provinceString = province.getText().toString().trim();
        String amphurString = amphur.getText().toString().trim();
        String subDistriceString = sid.getText().toString().trim();
        String croptypeString = textTypeSpinner.getText().toString().trim();
        String cropString = textCropSpinner.getText().toString().trim();
        String sdateString = sdate.getText().toString().trim();

        selectPlant(provinceString,amphurString,subDistriceString,croptypeString,cropString,sdateString);
    }

    private void selectPlant(String provinceString,String amphurString,String subDistriceString,String croptypeString,String cropString,String sdateString){
        Call<List<Plant>> call = orderService.getPlant(provinceString,amphurString,subDistriceString,croptypeString,cropString,sdateString);
        call.enqueue(new Callback<List<Plant>>() {
            @Override
            public void onResponse(Call<List<Plant>> call, Response<List<Plant>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    listView.setAdapter(new PlantAdpter1(getActivity(),R.layout.frm_plant_view,list));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            deleteorEditPlant(list.get(position).getNo(),list.get(position).getSno(),
                                    list.get(position).getPdate(),list.get(position).getMid(),list.get(position).getName(),list.get(position).getCid(),list.get(position).getCrop(),list.get(position).getArea1());
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<Plant>> call, Throwable t) {

            }
        });

    }

    //alertให้เลือกลบหรือแก้ไข
    private void deleteorEditPlant(final String noStrings,final String sno,final String pdataString,final String midStrings,final String nameStrings,final String cidStrings,final String cropStrings,final float area) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setIcon(R.drawable.shovel);
        builder.setTitle("ข้อมูลการเพาะปลูก");
        builder.setMessage("กรุณาเลือก ลบ หรือ ดูข้อมูล ?");
        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("ลบข้อมูล" ,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deletePlant(noStrings);
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("ดูรายละเอียด", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editPlant(noStrings,sno,pdataString,midStrings,nameStrings,cidStrings,cropStrings,area);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //alertให้เลือกจะลบรายการหรือไม่
    private void deletePlant(final String noStrings){
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
                DeletePlant(noStrings);
                dialog.dismiss();
            }
        });
        builder.show();

    }

    //ลบรายการประเภทพืชเพาะปลูก
    private void DeletePlant(String noStrings){

        Myconstant myconstant = new Myconstant();
        try {
            DeletePlant deletePlant = new DeletePlant(getActivity());
            deletePlant.execute(noStrings, myconstant.getUrlDeletePlant());

            if (Boolean.parseBoolean(deletePlant.get())) {
            } else {
                Toast.makeText(getActivity(),"ลบการเพาะปลูก",Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editPlant(final String no,final String sno,final String pdataString,final String midStrings,final String nameStrings,final String cidStrings,final String cropStrings,final float area) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
        builder.setCancelable(false);
        //กำหนดหัวเเรื้อง
        builder.setTitle("วางการเพาะปลูกใหม่");
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.edit_plant, null);

        //editpdate
        editpdateController();

        mapname = new HashMap<String, String>();
        mapname.put("name",nameStrings);
        mapname.put("mid",midStrings);

        map = new HashMap<String, String>();
        map.put("crop",cropStrings);
        map.put("cid",cidStrings);
        selectcroptype1();

        selectFarmer();

        EditText EditAddPlan1 = view.findViewById(R.id.EditAddPlant1);
        EditText EditAddPlan2 = view.findViewById(R.id.EditAddPlant2);
        EditText EditAddPlan3 = view.findViewById(R.id.EditAddPlant3);

        EditAddPlan1.setText(String.valueOf((int) Math.floor(area)));
        EditAddPlan2.setText(String.valueOf((int) Math.floor((area*400%400)/100)));
        EditAddPlan3.setText(String.valueOf((int) Math.floor((area*400)%100)));


        TextView textPDate = view.findViewById(R.id.EditMyDate);
        String newPDate = getActivity().getIntent().getExtras().getString("pdate",pdataString);
        textPDate.setText(newPDate);


        builder.setView(view);
        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("แก้ไข", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                TextView EditName = view.findViewById(R.id.textPlantSiteSnoSpinner);
                TextView EditPlanCropSpinner = view.findViewById(R.id.textPlanCidSpinner);
                TextView EditMyDate = view.findViewById(R.id.EditMyDate);
                EditText EditAddPlant1 = view.findViewById(R.id.EditAddPlant1);
                EditText EditAddPlant2 = view.findViewById(R.id.EditAddPlant2);
                EditText EditAddPlant3 = view.findViewById(R.id.EditAddPlant3);

                String newEditName = EditName.getText().toString();
                String newEditPlanCropSpinner = EditPlanCropSpinner.getText().toString();
                String newEditMyDate = EditMyDate.getText().toString();
                String newEditArea = Float.toString(Float.parseFloat(EditAddPlant1.getText().toString().trim())
                        + (Float.parseFloat(EditAddPlant2.getText().toString().trim()) * 100
                        + Float.parseFloat(EditAddPlant3.getText().toString().trim())) / 400);

                updatePlant(no,newEditName,newEditMyDate, newEditPlanCropSpinner, newEditArea);
            }
        });
        builder.show();
    }

    private void updatePlant(String no, String newEditName, String newEditMyDate, String newEditPlanCropSpinner, String newEditArea) {
        Myconstant myconstant = new Myconstant();

        try {
            EditPlant editPlant = new EditPlant(getActivity());
            editPlant.execute(no, newEditName,
                    newEditMyDate,
                    newEditPlanCropSpinner,
                    newEditArea,
                    myconstant.getUrlEditPlant());

            Log.d("are", "areb ===>" + editPlant);

            if (Boolean.parseBoolean(editPlant.get())) {

            } else {
                Toast.makeText(getActivity(), "แก้ไขข้อมูลสำเร็จ",
                        Toast.LENGTH_SHORT).show();
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
                            .replace(R.id.contentAdminFragment, new PlantFarmerFragment())
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
        Toolbar toolbar = getView().findViewById(R.id.toolbarPlant);
        ((AdminActivity)getActivity()).setSupportActionBar(toolbar);

        ((AdminActivity)getActivity()).getSupportActionBar().setTitle("ข้อมูลการเพาะปลูก");
        //((MainActivity)getActivity()).getSupportActionBar().setSubtitle("ddbdbvd");

        ((AdminActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AdminActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        View view = inflater.inflate(R.layout.frm_view_plant,container, false);
        return view;
    }
}
