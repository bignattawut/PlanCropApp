package th.in.nattawut.plancrop.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

import th.in.nattawut.plancrop.AdminActivity;
import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.AddSiteFarmer;
import th.in.nattawut.plancrop.utility.DeletePlant;
import th.in.nattawut.plancrop.utility.EditPlant;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.Myconstant;
import th.in.nattawut.plancrop.utility.PlantAdpter;

public class PlantViewFragment extends Fragment {

    SwipeRefreshLayout mSwipeRefreshLayout;

    private ArrayList<String> arrmid = new ArrayList<>();
    private ArrayList<String> arrname = new ArrayList<>();
    View view;

    ImageView selctDate;
    TextView date;
    DatePickerDialog dataPickerDialog;
    Calendar calendar;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        //planViewController
//        plantViewController();

        //Create Toolbal
        CreateToolbal();

        //Create ListView
        createListView();

        //Swipe Refresh Layout
        swipeRefreshLayout();
    }

    private void swipeRefreshLayout() {
        mSwipeRefreshLayout = getView().findViewById(R.id.swiRefreshLayou);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Create ListView
                        createListView();

                    }
                },2);

            }
        });
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

    private void selectcroptype() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final Spinner spin = view.findViewById(R.id.EditPlantCropSpinner);
        try {
            Myconstant myconstant = new Myconstant();
            GetData getData = new GetData(getActivity());
            getData.execute(myconstant.getUrlCrop());

            String jsonString = getData.get();
            Log.d("5/Jan CropType", "JSON ==>" + jsonString);
            JSONArray data = new JSONArray(jsonString);

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("cid", c.getString("cid"));
                map.put("crop", c.getString("crop"));
                MyArrList.add(map);
            }
            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_plancrop,
                    new String[]{"cid", "crop"}, new int[]{R.id.textPlanCidSpinner, R.id.textPlanCropSpinner});
            spin.setAdapter(sAdap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createListView() {
        final ListView listView = getView().findViewById(R.id.listViewPlant);
        Myconstant myconstant = new Myconstant();
        String[] columnStrings = myconstant.getColumnPlantString();

        try {

            GetData getData = new GetData(getActivity());
            getData.execute(myconstant.getUrlselectPlant());


            String jsonString = getData.get();
            Log.d("NatTWut","JSON plant ==> " + jsonString);

            JSONArray jsonArray = new JSONArray(jsonString);

            final String[] noStrings = new String[jsonArray.length()];
            final String[] pdataString = new String[jsonArray.length()];
            final String[] cidStrings = new String[jsonArray.length()];
            final String[] yieldStrings = new String[jsonArray.length()];
            final String[] cropStrings = new String[jsonArray.length()];
            final String[] areaStrings = new String[jsonArray.length()];
            final String[] midStrings = new String[jsonArray.length()];
            final String[] nameStrings = new String[jsonArray.length()];
            final String[] snoStrings = new String[jsonArray.length()];
            final String[] latStrings = new String[jsonArray.length()];
            final String[] lonStrings = new String[jsonArray.length()];

            for (int i=0; i<jsonArray.length(); i+=1){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                noStrings[i] = jsonObject.getString(columnStrings[0]);
                pdataString[i] = jsonObject.getString(columnStrings[1]);
                cidStrings[i] = jsonObject.getString(columnStrings[2]);
                yieldStrings[i] = jsonObject.getString(columnStrings[3]);
                cropStrings[i] = jsonObject.getString(columnStrings[4]);
                areaStrings[i] = jsonObject.getString(columnStrings[5]);
                midStrings[i] = jsonObject.getString(columnStrings[6]);
                nameStrings[i] = jsonObject.getString(columnStrings[7]);
                snoStrings[i] = jsonObject.getString(columnStrings[8]);
                latStrings[i] = jsonObject.getString(columnStrings[9]);
                lonStrings[i] = jsonObject.getString(columnStrings[10]);
            }

            PlantAdpter platAdpter = new PlantAdpter(getActivity(),
                    noStrings,pdataString,cidStrings,yieldStrings,cropStrings,areaStrings,
                    midStrings,nameStrings,snoStrings,latStrings,lonStrings);
            listView.setAdapter(platAdpter);

            //edit
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    deleteorEditPlant(noStrings[position],snoStrings[position],pdataString[position],midStrings[position],nameStrings[position]);
                    //mSwipeRefreshLayout.setRefreshing(false);
                }
            });
            mSwipeRefreshLayout.setRefreshing(false);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //alertให้เลือกลบหรือแก้ไข
    private void deleteorEditPlant(final String noStrings,final String sno,final String pdataString,final String midStrings,final String nameStrings) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_action_drawerplan);
        builder.setTitle("ลบ หรือ แก้ไข");
        builder.setMessage("กรุณาเลือก ลบ หรือ แก้ไข ?");
        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("ลบ" ,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deletePlant(noStrings);
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("แก้ไข", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editPlant(noStrings,sno,pdataString,midStrings,nameStrings);
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
                createListView();
            } else {
                Toast.makeText(getActivity(),"ลบการเพาะปลูก",Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editPlant(final String no,final String sno,final String pdataString,final String midStrings,final String nameStrings) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
        builder.setCancelable(false);
        //กำหนดหัวเเรื้อง
        builder.setTitle("วางการเพาะปลูกใหม่");
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.edit_plant, null);


        selectFarmer();

        //editpdate
        editpdateController();

        //selectcroptype
        selectcroptype();

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

                TextView EditName = view.findViewById(R.id.EditPlantName);
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
                            .replace(R.id.contentAdminFragment, new PlantFragment())
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

        ((AdminActivity)getActivity()).getSupportActionBar().setTitle("ข้อมูลเพาะปลูก");
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
