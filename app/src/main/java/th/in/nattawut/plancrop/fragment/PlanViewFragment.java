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
import th.in.nattawut.plancrop.utility.DeletePlan;
import th.in.nattawut.plancrop.utility.EditPlan;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.Myconstant;
import th.in.nattawut.plancrop.utility.PlanAdapter;

public class PlanViewFragment extends Fragment {

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView listView;
    View view;

    Spinner spin,selectMap;
    ArrayList<HashMap<String, String>> MyArrList,mapArrayList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map,m,selectsite;

    EditText EditAddPlan1,EditAddPlan2,EditAddPlan3;
    TextView qty,yield,yieldedit;
    private String sum;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Create Toolbal
        CreateToolbal();

        //Create ListView
        createListView();

        //Swipe Refresh Layout
        swipeRefreshLayout();

//        //planViewController
//        planViewController();


    }

//    private void calculator() {
//        ImageView calculator = view.findViewById(R.id.calculator);
//        calculator.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//                EditAddPlan1 = view.findViewById(R.id.EditAddPlan1);
//                EditAddPlan2 = view.findViewById(R.id.EditAddPlan2);
//                EditAddPlan3 = view.findViewById(R.id.EditAddPlan3);
//                yield = view.findViewById(R.id.textyield);
//                qty = view.findViewById(R.id.textQty);
//                Float area = Float.valueOf(Float.toString(Float.parseFloat(EditAddPlan1.getText().toString().trim())
//                        + (Float.parseFloat(EditAddPlan2.getText().toString().trim()) * 100 + Float.parseFloat(EditAddPlan3.getText().toString().trim())) / 400));
//                sum = decimalFormat.format((int) (Float.parseFloat(yield.getText().toString().trim())*area));
//                qty.setText(sum);
//            }
//        });
//    }

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

    private void createListView() {
        final ListView listView = getView().findViewById(R.id.listViewPlan);
        Myconstant myconstant = new Myconstant();
        String[] columnStrings = myconstant.getColumnPlanString();

        try {

            GetData getData = new GetData(getActivity());
            getData.execute(myconstant.getUrlselectPlan());

            String jsonString = getData.get();
            Log.d("22big","JSON plan ==> " + jsonString);

            JSONArray jsonArray = new JSONArray(jsonString);

            final String[] planStrings = new String[jsonArray.length()];
            final String[] midString = new String[jsonArray.length()];
            final String[] typeStrings = new String[jsonArray.length()];
            final String[] cidString = new String[jsonArray.length()];
            final String[] cropStrings = new String[jsonArray.length()];
            final String[] yieldStrings = new String[jsonArray.length()];
            final String[] areStrings = new String[jsonArray.length()];
            final String[] dateStrings = new String[jsonArray.length()];


            for (int i=0; i<jsonArray.length(); i+=1){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                planStrings[i] = jsonObject.getString(columnStrings[0]);
                midString[i] = jsonObject.getString(columnStrings[1]);
                typeStrings[i] = jsonObject.getString(columnStrings[2]);
                cidString[i] = jsonObject.getString(columnStrings[3]);
                cropStrings[i] = jsonObject.getString(columnStrings[4]);
                yieldStrings[i] = jsonObject.getString(columnStrings[5]);
                areStrings[i] = jsonObject.getString(columnStrings[6]);
                dateStrings[i] = jsonObject.getString(columnStrings[7]);
            }

            PlanAdapter pantAdapter = new PlanAdapter(getActivity(),
                    planStrings,midString,typeStrings,cidString,cropStrings,yieldStrings,areStrings,dateStrings);
            listView.setAdapter(pantAdapter);

            //edit
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    deleteorEditCropPlan(planStrings[position],midString[position],typeStrings[position],cidString[position],cropStrings[position],areStrings[position],Float.valueOf(dateStrings[position]),yieldStrings[position]);
                    //mSwipeRefreshLayout.setRefreshing(false);
                }
            });
            mSwipeRefreshLayout.setRefreshing(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //alertให้เลือกลบหรือแก้ไข
    private void deleteorEditCropPlan(final String planStrings, final String midString,final String typeStrings,final String cidString,final String cropStrings,final String dateStrings,final float area,final String yieldStrings) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setIcon(R.drawable.planhome);
        builder.setTitle("ข้อมูลการวางแผน");
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
                deleteCropType(planStrings);
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("ดูรายละเอียด", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editPlan(planStrings,midString,typeStrings,cidString,cropStrings,dateStrings,area,yieldStrings);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //alertให้เลือกจะลบรายการหรือไม่
    private void deleteCropType(final String planStrings){
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
                editDeletePlan(planStrings);
                dialog.dismiss();
            }
        });
        builder.show();

    }

    private void editPlan(final String planStrings,final String midString,final String typeStrings,final String cidString,final String cropStrings,final String dateStrings,final float area,final String yieldStrings){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        //กำหนดหัวเเรื้อง
        builder.setTitle("วางแผนเพาะปลูกใหม่");
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.edit_plan_admin, null);
        //builder = new AlertDialog.Builder(getActivity(),android.R.style.Theme_DeviceDefault_Dialog_Alert);//theme

        //calculator();

        map = new HashMap<String, String>();
        map.put("crop",cropStrings);
        map.put("cid",cidString);
        selectcroptype1();

        TextView texPlanMid = view.findViewById(R.id.EditTextPlanMid);
        String strTextShowmid = getActivity().getIntent().getExtras().getString("Mid",midString);
        texPlanMid.setText(strTextShowmid);

        TextView EditTexPlanName = view.findViewById(R.id.EditTexPlanName);
        String strTextShowName = getActivity().getIntent().getExtras().getString("Name",typeStrings);
        EditTexPlanName.setText(strTextShowName);

        TextView EditTexpdate = view.findViewById(R.id.EditMyDate);
        String strTextShowpdate = getActivity().getIntent().getExtras().getString("pdate",dateStrings);
        EditTexpdate.setText(strTextShowpdate);

        EditText EditAddPlan1 = view.findViewById(R.id.EditAddPlan1);
        EditText EditAddPlan2 = view.findViewById(R.id.EditAddPlan2);
        EditText EditAddPlan3 = view.findViewById(R.id.EditAddPlan3);

        EditAddPlan1.setText(String.valueOf((int) Math.floor(area)));
        EditAddPlan2.setText(String.valueOf((int) Math.floor((area*400%400)/100)));
        EditAddPlan3.setText(String.valueOf((int) Math.floor((area*400)%100)));

//        qty = view.findViewById(R.id.textQty);
//        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        String sum = decimalFormat.format(Float.parseFloat(yieldStrings));
//        qty.setText(sum);

        final TextView data = view.findViewById(R.id.EditMyDate);
        ImageView selctData = view.findViewById(R.id.EditImageViewDate);
        selctData.setOnClickListener(new View.OnClickListener() {
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
                                data.setText(y + "/" + (m + 1) + "/" + d);
                            }
                        },day,month,year);
                dataPickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());//วันที่ปัจจุบัน

                dataPickerDialog.show();
            }
        });
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

                TextView EditTextMidPlan = view.findViewById(R.id.EditTextPlanMid);
                TextView EditPlanCropSpinner = view.findViewById(R.id.textPlanCidSpinner);
                TextView EditMyDate = view.findViewById(R.id.EditMyDate);
                EditText EditAddPlan1 = view.findViewById(R.id.EditAddPlan1);
                EditText EditAddPlan2 = view.findViewById(R.id.EditAddPlan2);
                EditText EditAddPlan3 = view.findViewById(R.id.EditAddPlan3);

                String newEditTextMidPlan = EditTextMidPlan.getText().toString();
                String newEditPlanCropSpinner = EditPlanCropSpinner.getText().toString();
                String newEditMyDate = EditMyDate.getText().toString();

                String newEditArea = Float.toString(Float.parseFloat(EditAddPlan1.getText().toString().trim())
                        +(Float.parseFloat(EditAddPlan2.getText().toString().trim()) *100
                        +Float.parseFloat(EditAddPlan3.getText().toString().trim()))/400);

                //Log.d("are","are ===>" + newEditTextMidPlan + newEditMyDate + newEditPlanCropSpinner);

                updatePlan(planStrings,newEditTextMidPlan,
                        newEditMyDate,
                        newEditPlanCropSpinner,
                        newEditArea);

            }
        });
        builder.show();
    }

    private void updatePlan(String planStrings, String newEditTextMidPlan, String newEditMyDate, String newEditPlanCropSpinner,String newEditArea) {
        Myconstant myconstant = new Myconstant();

        Log.d("are","areb ===>" + newEditTextMidPlan + newEditMyDate);
        try {
            EditPlan editPlan = new EditPlan(getActivity());
            editPlan.execute(planStrings,newEditTextMidPlan,
                    newEditMyDate,
                    newEditPlanCropSpinner,
                    newEditArea,
                    myconstant.getUrlEditPlan());

            if (Boolean.parseBoolean(editPlan.get())) {
                createListView();
            } else {
                Toast.makeText(getActivity(),"แก้ไขข้อมูลสำเร็จ",
                        Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ลบรายการประเภทพืชเพาะปลูก
    private void editDeletePlan(String planStrings){

        Myconstant myconstant = new Myconstant();
        try {
            DeletePlan deletePlan = new DeletePlan(getActivity());
            deletePlan.execute(planStrings, myconstant.getUrlDeletePlan());

            if (Boolean.parseBoolean(deletePlan.get())) {
                createListView();
            } else {
                Toast.makeText(getActivity(),"ลบประเภทพืช",Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectcroptype1() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        selectMap = view.findViewById(R.id.EditPlanCropSpinner);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemlinkUrl) {
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contentAdminFragment, new PlanFarmerFragment())
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
        Toolbar toolbar = getView().findViewById(R.id.toolbarPlan);
        ((AdminActivity)getActivity()).setSupportActionBar(toolbar);

        ((AdminActivity)getActivity()).getSupportActionBar().setTitle("ข้อมูลวางแผน");
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
        View view = inflater.inflate(R.layout.frm_view_plan,container, false);
        return view;
    }
}
