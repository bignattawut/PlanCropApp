package th.in.nattawut.plancrop.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tuann.floatingactionbuttonexpandable.FloatingActionButtonExpandable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.APIUtils;
import th.in.nattawut.plancrop.utility.DeletePlant;
import th.in.nattawut.plancrop.utility.EditPlant;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.GetDataWhereOneColumn;
import th.in.nattawut.plancrop.utility.Myconstant;
import th.in.nattawut.plancrop.utility.OrderService;
import th.in.nattawut.plancrop.utility.PlantFarmer;
import th.in.nattawut.plancrop.utility.PlantFarmerAdapter;

public class PlantFarmerViewFragment extends Fragment {

    ListView listView;
    OrderService orderService;
    List<PlantFarmer> list = new ArrayList<PlantFarmer>();
    private String sdata;
    SwipeRefreshLayout mSwipeRefreshLayout;

    ImageView selctDate;
    TextView date;
    DatePickerDialog dataPickerDialog;
    Calendar calendar;

    View view;

    private ArrayList<String> arrcid = new ArrayList<>();
    private ArrayList<String> arrname = new ArrayList<>();

    private String idRecord;
    Spinner spin,selectMap;
    ArrayList<HashMap<String, String>> MyArrList,mapArrayList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map,m,selectsite;

    private ArrayAdapter<String> adpProvince;

    String newcid;
    TextView qty,yield;
    EditText rai, ngan, wa;
    String sum;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //showMid();

        plantViewController();

        swiRefreshLayou();

        showMid();

        cropController();

        edateController();
    }



    private void swiRefreshLayou() {
        mSwipeRefreshLayout = getView().findViewById(R.id.swiRefreshLayouPlantFarmer);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showMid();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void plantViewController() {
        FloatingActionButtonExpandable floatingActionButton = getView().findViewById(R.id.floatingActionButtonViewPlant);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new PlantFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void cropController() {
        Button button = getView().findViewById(R.id.selete1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMid();

            }
        });
    }

    private void edateController() {
        final TextView sdate = getActivity().findViewById(R.id.sdate);
        ImageView selctDate = getActivity().findViewById(R.id.imageViewDatesdate);
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

    private void setUpTexeShowMid() {
        TextView textNameFarmer = view.findViewById(R.id.EdittextNameFarmer);

        String strTextShow = getActivity().getIntent().getExtras().getString("name");
        textNameFarmer.setText(strTextShow);
    }

    private void siteController() {
        if (android.os.Build.VERSION.SDK_INT > 9) { //setup policy เเพื่อมือถือที่มีประปฏิบัติการสูงกว่านีจะไม่สามารถconnectกับโปรโตรคอลได้
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final Spinner spin = view.findViewById(R.id.EditSiteName);
        try {

            Myconstant myconstant = new Myconstant();
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(myconstant.getNameFileSharePreference(), Context.MODE_PRIVATE);
            idRecord = sharedPreferences.getString("mid", "");

            GetDataWhereOneColumn getDataWhereOneColumn = new GetDataWhereOneColumn(getActivity());
            getDataWhereOneColumn.execute("mid", idRecord, myconstant.getSelectsitefarmer());

            String jsonString = getDataWhereOneColumn.get();
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
            getData.execute(myconstant.getUrlCrop1());

            String jsonString = getData.get();
            Log.d("5/Jan CropType", "JSON ==>" + jsonString);
            JSONArray data = new JSONArray(jsonString);

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                m = new HashMap<String, String>();
                m.put("cid", c.getString("cid"));
                m.put("crop", c.getString("crop"));
                m.put("yield", c.getString("yield"));
                mapArrayList.add(m);
            }

            final SimpleAdapter s;
            s = new SimpleAdapter(getActivity(), mapArrayList, R.layout.spinner_yield,
                    new String[]{"cid", "crop","yield"}, new int[]{R.id.textPlanCidSpinner, R.id.textPlanCropSpinner,R.id.textyield});
            selectMap.setAdapter(s);
            selectMap.setSelection(mapArrayList.indexOf(map));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calculator() {
        ImageView calculator = view.findViewById(R.id.calculator);
        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                rai = view.findViewById(R.id.EditAddPlant1);
                ngan = view.findViewById(R.id.EditAddPlant2);
                wa = view.findViewById(R.id.EditAddPlant3);
                yield = view.findViewById(R.id.textyield);
                qty = view.findViewById(R.id.textQty);
                Float area = Float.valueOf(Float.toString(Float.parseFloat(rai.getText().toString().trim())
                        + (Float.parseFloat(ngan.getText().toString().trim()) * 100 + Float.parseFloat(wa.getText().toString().trim())) / 400));
                sum = decimalFormat.format((int) (Float.parseFloat(yield.getText().toString().trim())*area));
                qty.setText(sum);

            }
        });
    }

    public void showMid() {

        TextView sdate = getView().findViewById(R.id.sdate);
        String sdateString = sdate.getText().toString().trim();


        listView = getView().findViewById(R.id.listViewPlantFarmer);
        orderService = APIUtils.getService();
        if (getActivity().getIntent().getExtras() != null) {
            String mid = getActivity().getIntent().getExtras().getString("mid");
            createListView(mid,sdateString);

        }
    }

    private void createListView(String mid,String sdateString) {
        Call<List<PlantFarmer>> call = orderService.getPlantFarmer(mid,sdateString);
        call.enqueue(new Callback<List<PlantFarmer>>() {
            @Override
            public void onResponse(Call<List<PlantFarmer>> call, Response<List<PlantFarmer>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    listView.setAdapter(new PlantFarmerAdapter(getActivity(), R.layout.frm_plantfarmer_view, list));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            deleteorEditPlantFarmer(list.get(position).getNo(),list.get(position).getSno(),list.get(position).getPdate()
                                    , list.get(position).getCrop(), list.get(position).getArea1(),list.get(position).getCid(),list.get(position).getYield(),list.get(position).getYieldq());

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<PlantFarmer>> call, Throwable t) {

            }
        });
    }

    //alertให้เลือกลบหรือแก้ไข
    private void deleteorEditPlantFarmer(final String no, final String sno,final String pdata, final String crop, final float area, final String cid,final String yield,final String yieldq) {

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
        builder.setNeutralButton("ลบข้อมูล", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editDeletePlantFarmer(no);
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("ดูรายละเอียด", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editPlantFarmer(no,sno,pdata, crop, area,cid,yield,yieldq);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //alertให้เลือกจะลบรายการหรือไม่
    private void editDeletePlantFarmer(final String no) {
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
                deleteCPlantFarmer(no);
                dialog.dismiss();
            }
        });
        builder.show();

    }

    //ลบรายการการเพาะปลูก
    private void deleteCPlantFarmer(String no) {
        Myconstant myconstant = new Myconstant();
        try {
            DeletePlant deletePlant = new DeletePlant(getActivity());
            deletePlant.execute(no, myconstant.getUrlDeletePlant());

            if (Boolean.parseBoolean(deletePlant.get())) {
            } else {
                Toast.makeText(getActivity(), "ลบการเพาะปลูก", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editPlantFarmer(final String no,final String sno,String pdata, String crop, float area,final String cid,final String yield,final String yieldq) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
        builder.setCancelable(false);
        //กำหนดหัวเเรื้อง
        builder.setTitle("การเพาะปลูกพืช");
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.edit_plant_farmer, null);

        setUpTexeShowMid();

        siteController();

        //editpdate
        editpdateController();


        //selectcroptype
        //selectcroptype(cid,crop);

        calculator();

//        map = new HashMap<String, String>();
//        map.put("cid",cid);
//        map.put("crop",crop);
//        map.put("yield",yield);
//        selectcroptype1();

        map = new HashMap<String, String>();
        map.put("cid",cid);
        map.put("crop",crop);
        map.put("yield",yield);
        selectcroptype1();

        Log.d("cid","cid" + cid);
        Log.d("crop","crop" + crop);
        Log.d("yield","yield" + yield);



        TextView textPDate = view.findViewById(R.id.EditMyDate);
        String newPDate = getActivity().getIntent().getExtras().getString("pdate", pdata);
        textPDate.setText(newPDate);

        EditText EditAddPlan1 = view.findViewById(R.id.EditAddPlant1);
        EditText EditAddPlan2 = view.findViewById(R.id.EditAddPlant2);
        EditText EditAddPlan3 = view.findViewById(R.id.EditAddPlant3);

        EditAddPlan1.setText(String.valueOf((int) Math.floor(area)));
        EditAddPlan2.setText(String.valueOf((int) Math.floor((area*400%400)/100)));
        EditAddPlan3.setText(String.valueOf((int) Math.floor((area*400)%100)));


        qty = view.findViewById(R.id.textQty);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String sum = decimalFormat.format(Float.parseFloat(yieldq));
        qty.setText(sum);

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_view_plantfarmer, container, false);
        return view;
    }
}
