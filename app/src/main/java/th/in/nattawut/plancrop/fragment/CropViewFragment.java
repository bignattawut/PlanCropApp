package th.in.nattawut.plancrop.fragment;

import android.app.AlertDialog;
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

import th.in.nattawut.plancrop.AdminActivity;
import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.CropViewAdpter;
import th.in.nattawut.plancrop.utility.DeleteCrop;
import th.in.nattawut.plancrop.utility.EditCrop;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.MyAlert;
import th.in.nattawut.plancrop.utility.Myconstant;

public class CropViewFragment extends Fragment {

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView listView;

    Spinner spinner;
    Spinner spin,selectMap;
    //ArrayList<HashMap<String, String>> MyArrList,mapArrayList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> m;
    View view;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Create ListView
        createListView();

        //Create Toolbal
        createToolbal();

        //Swipe Refresh Layout
        swipeRefreshLayout();


    }

    private void swipeRefreshLayout() {
        mSwipeRefreshLayout = getView().findViewById(R.id.swiRefreshLayouCrop);
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

    private void createListView() {
        //final ListView listView = getView().findViewById(R.id.listViewCrop);
        listView = getView().findViewById(R.id.listViewCrop);
        Myconstant myconstant = new Myconstant();
        String[] columnStrings = myconstant.getColumnCropString();
        try{
            GetData getData = new GetData(getActivity());
            getData.execute(myconstant.getUrlselectCrop());

            String jsonString = getData.get();
            Log.d("4กุมภาพันธ์","Json Crop ==> " + jsonString);

            JSONArray jsonArray = new JSONArray(jsonString);

            final String[] cidString = new String[jsonArray.length()];
            final String[] cropString = new String[jsonArray.length()];
            final String[] tidString = new String[jsonArray.length()];
            final String[] croptypeString = new String[jsonArray.length()];
            final String[] beginharvestString = new String[jsonArray.length()];
            final String[] harvestperiodString = new String[jsonArray.length()];
            final String[] yield = new String[jsonArray.length()];

            for (int i=0; i<jsonArray.length(); i+=1){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                cidString[i] = jsonObject.getString(columnStrings[0]);
                cropString[i] = jsonObject.getString(columnStrings[1]);
                tidString[i] = jsonObject.getString(columnStrings[2]);
                croptypeString[i] = jsonObject.getString(columnStrings[3]);
                beginharvestString[i] = jsonObject.getString(columnStrings[4]);
                harvestperiodString[i] = jsonObject.getString(columnStrings[5]);
                yield[i] = jsonObject.getString(columnStrings[6]);

            }

            CropViewAdpter cropViewAdpter = new CropViewAdpter(getActivity(),
                    cidString,cropString,tidString,croptypeString,beginharvestString,harvestperiodString,yield);
            listView.setAdapter(cropViewAdpter);
            //edit
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    deleteorEditCropType(cidString[position],
                            cropString[position],tidString[position],croptypeString[position],beginharvestString[position],
                            harvestperiodString[position],yield[position]);
                }
            });
            mSwipeRefreshLayout.setRefreshing(false);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //alertให้เลือกลบหรือแก้ไข
    private void deleteorEditCropType(final String cidString, final String cropString,final String tidString, final String croptypeString,
                                      final String beginharvestString, final String harvestperiodString, final String yield) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_spa_black_24dp);
        builder.setTitle("ข้อมูลพืช");
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
                deleteCrop(cidString);
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("ดูรายละเอียด", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editCrop(cidString,cropString,tidString,croptypeString,beginharvestString,harvestperiodString,yield);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //alertให้เลือกจะลบรายการหรือไม่
    private void deleteCrop(final String cidString){
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
                editDeleteCrop(cidString);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //แก้ไขพืชเพาะปลูก
    private void editCrop(final String cidString, final String cropString,final String tidString,final String croptypeString,
                          final String beginharvestString,final String harvestperiodString, final String yield){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle("ลงทะเบียนพืชเพาะปลูก");

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.edit_crop, null);

        EditText edtEditCropName = view.findViewById(R.id.edtEditCropName);
        String newCropName = getActivity().getIntent().getExtras().getString("crop",cropString);
        edtEditCropName.setText(newCropName);

        EditText edtEditBeginHarvest = view.findViewById(R.id.edtEditBeginHarvest);
        String newBeginHarvest = getActivity().getIntent().getExtras().getString("beginharvest",beginharvestString);
        edtEditBeginHarvest.setText(newBeginHarvest);

        EditText edtEditHarvestPeriod = view.findViewById(R.id.edtEditHarvestPeriod);
        String newHarvestPeriod = getActivity().getIntent().getExtras().getString("harvestperiod",harvestperiodString);
        edtEditHarvestPeriod.setText(newHarvestPeriod);

        EditText edtEditYield = view.findViewById(R.id.edtEditYield);
        String newYield = getActivity().getIntent().getExtras().getString("yield",yield);
        edtEditYield.setText(newYield);

        m = new HashMap<String, String>();
        m.put("croptype",croptypeString);
        m.put("tid",tidString);
        selectCropType();
        /*
        spinner = view.findViewById(R.id.EditcropTypeSpinner);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.spinner));
        spinner.setAdapter(adp);
        */
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

                //พืช
                EditText edtEditCropName = view.findViewById(R.id.edtEditCropName);
                String newCropName = edtEditCropName.getText().toString();
                //ประเภท
                TextView EditcropType = view.findViewById(R.id.textTID);
                String newcropType = EditcropType.getText().toString();
                //เริ่มต้นการเก็บเกี่ยว
                EditText edtEditBeginHarvest = view.findViewById(R.id.edtEditBeginHarvest);
                String newBeginHarvest = edtEditBeginHarvest.getText().toString();
                //ระยะเวลา
                EditText edtEditHarvestPeriod = view.findViewById(R.id.edtEditHarvestPeriod);
                String newHarvestPeriod = edtEditHarvestPeriod.getText().toString();
                //edtEditYield
                EditText edtEditYield = view.findViewById(R.id.edtEditYield);
                String newYield = edtEditYield.getText().toString();

               if (newCropName.isEmpty() || newcropType.isEmpty() || newBeginHarvest.isEmpty() || newHarvestPeriod.isEmpty() || newYield.isEmpty()  ) {
                   MyAlert myAlert = new MyAlert(getActivity());
                   myAlert.onrmaIDialog("สวัสดี", "กรุณากรอกข้อมูลให้ครบ");
                }
                updateCrop(cidString,newCropName,newcropType,newBeginHarvest,newHarvestPeriod,newYield);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //updateข้อมูลพืชเพาะปลูก
    private void updateCrop(String cidString, String newCropName, String newcropType, String newBeginHarvest, String newHarvestPeriod, String newYield){
        Myconstant myconstant = new Myconstant();

        try {
            EditCrop editCrop = new EditCrop(getActivity());
            editCrop.execute(cidString,newCropName,newcropType,newBeginHarvest,newHarvestPeriod,newYield,
                    myconstant.getUrlEditCrop());

            if (Boolean.parseBoolean(editCrop.get())) {
                createListView();
            } else {
                //Toast.makeText(getActivity(),"แก้ไขข้อมูลสำเร็จ",Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ลบรายการประเภทพืชเพาะปลูก
    private void editDeleteCrop(String tidString){

        Myconstant myconstant = new Myconstant();
        try {
            DeleteCrop deleteCrop = new DeleteCrop(getActivity());
            deleteCrop.execute(tidString, myconstant.getUrlDeleteCrop());

            if (Boolean.parseBoolean(deleteCrop.get())) {
                createListView();
            } else {
                Toast.makeText(getActivity(),"ลบรายการพืชเพาะปลูก",Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectCropType() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        selectMap = view.findViewById(R.id.EditcropTypeSpinner);
        try {
            Myconstant myconstant = new Myconstant();
            GetData getData = new GetData(getActivity());
            getData.execute(myconstant.getUrlCropType());

            String jsonString = getData.get();
            Log.d("5/Jan CropType", "JSON ==>" + jsonString);
            JSONArray data = new JSONArray(jsonString);

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("tid", c.getString("tid"));
                map.put("croptype", c.getString("croptype"));
                MyArrList.add(map);
            }
            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_crop,
                    new String[] {"tid", "croptype"}, new int[] {R.id.textTID, R.id.textCropType});
            selectMap.setAdapter(sAdap);
            selectMap.setSelection(MyArrList.indexOf(m));

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
                            .replace(R.id.contentAdminFragment, new CropFragment())
                            .addToBackStack(null)
                            .commit();
                    return false;
                }
            });

            // uploadValueToSever();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.manu_register, menu);

    }

    private void createToolbal() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarCropView);
        ((AdminActivity)getActivity()).setSupportActionBar(toolbar);

        ((AdminActivity)getActivity()).getSupportActionBar().setTitle("ข้อมูลพืช");
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
        View view = inflater.inflate(R.layout.frm_view_crop,container, false);
        return view;
    }
}

