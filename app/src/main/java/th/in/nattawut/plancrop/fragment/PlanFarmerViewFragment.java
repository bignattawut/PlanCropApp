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
import android.util.Log;
import android.view.LayoutInflater;
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
import th.in.nattawut.plancrop.utility.DeletePlan;
import th.in.nattawut.plancrop.utility.EditPlan;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.Myconstant;
import th.in.nattawut.plancrop.utility.OrderService;
import th.in.nattawut.plancrop.utility.PlanFarmer;
import th.in.nattawut.plancrop.utility.PlanFarmerAdapter;

public class PlanFarmerViewFragment extends Fragment {

    ListView listView;
    OrderService orderService;
    List<PlanFarmer> list = new ArrayList<PlanFarmer>();
    SwipeRefreshLayout mSwipeRefreshLayout;

    Spinner spin,selectMap;
    ArrayList<HashMap<String, String>> MyArrList,mapArrayList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map,m,selectsite;
    View view;

    EditText EditAddPlan1,EditAddPlan2,EditAddPlan3;
    TextView qty,yield,yieldedit;
    private String sum;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Create ListView
        //createListView();

        //Swipe Refresh Layout
        //swipeRefreshLayout();

        //showMid();

        planViewController();

        swiRefreshLayou();

        showMid();




    }


    private void calculator() {
        ImageView calculator = view.findViewById(R.id.calculator);
        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                EditAddPlan1 = view.findViewById(R.id.EditAddPlan1);
                EditAddPlan2 = view.findViewById(R.id.EditAddPlan2);
                EditAddPlan3 = view.findViewById(R.id.EditAddPlan3);
                yield = view.findViewById(R.id.textyield);
                qty = view.findViewById(R.id.textQty);
                Float area = Float.valueOf(Float.toString(Float.parseFloat(EditAddPlan1.getText().toString().trim())
                        + (Float.parseFloat(EditAddPlan2.getText().toString().trim()) * 100 + Float.parseFloat(EditAddPlan3.getText().toString().trim())) / 400));
                sum = decimalFormat.format((int) (Float.parseFloat(yield.getText().toString().trim())*area));
                qty.setText(sum);
            }
        });
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

    private void swiRefreshLayou() {
        mSwipeRefreshLayout = getView().findViewById(R.id.swiRefreshLayouPlanFarmer);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showMid();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void planViewController() {
        FloatingActionButtonExpandable floatingActionButton = getView().findViewById(R.id.floatingActionButtonViewPlan);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new PlanFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public void showMid() {
        listView = getView().findViewById(R.id.listViewPlanFarmer);
        orderService = APIUtils.getService();
        if (getActivity().getIntent().getExtras() != null) {
            String mid = getActivity().getIntent().getExtras().getString("mid");
            createListView(mid);

        }
    }

    private void createListView(String mid) {
        Call<List<PlanFarmer>> call = orderService.getPlanFarmer(mid);
        call.enqueue(new Callback<List<PlanFarmer>>() {
            @Override
            public void onResponse(Call<List<PlanFarmer>> call, Response<List<PlanFarmer>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    listView.setAdapter(new PlanFarmerAdapter(getActivity(), R.layout.frm_planfarmer_view, list));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            deleteorEditPlanFarmer(list.get(position).getNo(), list.get(position).getPdate()
                                    , list.get(position).getCrop(), list.get(position).getArea1(),list.get(position).getCid(),list.get(position).getYield(),list.get(position).getYieldq());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<PlanFarmer>> call, Throwable t) {

            }
        });
    }

    //alertให้เลือกลบหรือแก้ไข
    private void deleteorEditPlanFarmer(final String no, final String pdate, final String crop, final float area,final String cid,final String yield,final String yieldq) {

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
        builder.setNeutralButton("ลบข้อมูล", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editDeletePlanFarmer(no);
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("ดูรายละเอียด", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editPlanFarmer(no, pdate, crop, area,cid,yield,yieldq);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //alertให้เลือกจะลบรายการหรือไม่
    private void editDeletePlanFarmer(final String no){
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
                deleteCPlanFarmer(no);
                dialog.dismiss();
            }
        });
        builder.show();

    }

    //ลบรายการวางแผนเพาะปลูก
    private void deleteCPlanFarmer(String no) {

        Myconstant myconstant = new Myconstant();
        try {
            DeletePlan deletePlan = new DeletePlan(getActivity());
            deletePlan.execute(no, myconstant.getUrlDeletePlan());

            if (Boolean.parseBoolean(deletePlan.get())) {
            } else {
                Toast.makeText(getActivity(), "ลบประเภทพืช", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editPlanFarmer(final String no, String pdate, String crop, float area,final String cid,final String yield,final String yieldq) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_LIGHT);
        builder.setCancelable(false);
        //กำหนดหัวเเรื้อง
        builder.setTitle("วางแผนเพาะปลูกพืช");
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.edit_plan, null);


        calculator();

        TextView texPlanMid = view.findViewById(R.id.EditTextPlanMid);
        String strTextShowmid = getActivity().getIntent().getExtras().getString("mid");
        texPlanMid.setText(strTextShowmid);

        TextView texPlanName = view.findViewById(R.id.EditTexPlanName);
        String strTextShowName = getActivity().getIntent().getExtras().getString("name");
        texPlanName.setText(strTextShowName);

        TextView textPDate = view.findViewById(R.id.EditMyDate);
        String newPDate = getActivity().getIntent().getExtras().getString("pdate", pdate);
        textPDate.setText(newPDate);

        //(int)Math.floor(area) + "-" + (int)Math.floor((area*400%400)/100) + "-" + (int)(area*400)%100;

        EditAddPlan1 = view.findViewById(R.id.EditAddPlan1);
        EditAddPlan2 = view.findViewById(R.id.EditAddPlan2);
        EditAddPlan3 = view.findViewById(R.id.EditAddPlan3);

        EditAddPlan1.setText(String.valueOf((int) Math.floor(area)));
        EditAddPlan2.setText(String.valueOf((int) Math.floor((area*400%400)/100)));
        EditAddPlan3.setText(String.valueOf((int) Math.floor((area*400)%100)));

        qty = view.findViewById(R.id.textQty);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String sum = decimalFormat.format(Float.parseFloat(yieldq));
        qty.setText(sum);


        map = new HashMap<String, String>();
        map.put("cid",cid);
        map.put("crop",crop);
        map.put("yield",yield);
        selectcroptype1();

        Log.d("cid","cid" + cid);
        Log.d("crop","crop" + crop);
        Log.d("yield","yield" + yield);

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
                        }, day, month, year);
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
                        + (Float.parseFloat(EditAddPlan2.getText().toString().trim()) * 100
                        + Float.parseFloat(EditAddPlan3.getText().toString().trim())) / 400);

                //Log.d("are","are ===>" + newEditTextMidPlan + newEditMyDate + newEditPlanCropSpinner);

                updatePlan(no, newEditTextMidPlan,
                        newEditMyDate,
                        newEditPlanCropSpinner,
                        newEditArea);

            }
        });
        builder.show();
    }

    private void updatePlan(String no, String newEditTextMidPlan, String newEditMyDate, String newEditPlanCropSpinner, String newEditArea) {
        Myconstant myconstant = new Myconstant();

        Log.d("are", "areb ===>" + newEditTextMidPlan + newEditMyDate);
        try {
            EditPlan editPlan = new EditPlan(getActivity());
            editPlan.execute(no, newEditTextMidPlan,
                    newEditMyDate,
                    newEditPlanCropSpinner,
                    newEditArea,
                    myconstant.getUrlEditPlan());

            if (Boolean.parseBoolean(editPlan.get())) {

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
        View view = inflater.inflate(R.layout.frm_view_planfarmer, container, false);
        return view;
    }
}
