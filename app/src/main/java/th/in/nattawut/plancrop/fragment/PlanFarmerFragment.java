package th.in.nattawut.plancrop.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import th.in.nattawut.plancrop.utility.AddPlan;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.MyAlertCrop;
import th.in.nattawut.plancrop.utility.Myconstant;

public class PlanFarmerFragment extends Fragment {
    //Button selctDate;
    ImageView selctDate;
    TextView date;
    DatePickerDialog dataPickerDialog;
    Calendar calendar;

    EditText plan1, plan2, plan3;
    Button button;

    private String midString, cidNameString, myDataString, areaString,cropNameString,nameString;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Create Toolbal
        CreateToolbal();

        //CropController
        cropController();

        //Pdate Controller
        pdateController();

        //PlanFarmerSpinner
        planFarmerSpinner();

        //PlanMidSpinner
        //planMidSpinner();

        //selectFarmer
        selectFarmer();



    }

    private void selectFarmer() {
        if (android.os.Build.VERSION.SDK_INT > 9) { //setup policy เเพื่อมือถือที่มีประปฏิบัติการสูงกว่านีจะไม่สามารถconnectกับโปรโตรคอลได้
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final Spinner spin = getView().findViewById(R.id.midSpinnerPlanFarmer);
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

    private void planFarmerSpinner() {
        if (android.os.Build.VERSION.SDK_INT > 9) { //setup policy เเพื่อมือถือที่มีประปฏิบัติการสูงกว่านีจะไม่สามารถconnectกับโปรโตรคอลได้
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final Spinner spin = getView().findViewById(R.id.plancropspinnerFarmer);
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
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_plancrop,
                    new String[]{"cid", "crop"}, new int[]{R.id.textPlanCidSpinner, R.id.textPlanCropSpinner});
            spin.setAdapter(sAdap);
            spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> arg0, View selectedItemView, int position, long id) {

                }

                public void onNothingSelected(AdapterView<?> arg0) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pdateController() {
        date = getActivity().findViewById(R.id.myDatePlanFarmer);
        selctDate = getActivity().findViewById(R.id.imageViewDatePlanFarmer);
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
                                date.setText(y + "/" + (m + 1) + "/" + d);
                                //DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.UK);

                            }
                        }, day, month, year);
                dataPickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dataPickerDialog.show();
            }
        });
    }

    private void cropController() {
        Button button = getView().findViewById(R.id.btnPlanFarmer);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCrop();

            }
        });
    }

    private void AddCrop() {

        TextView textmid = getView().findViewById(R.id.textMId);
        TextView textName = getView().findViewById(R.id.textName);
        TextView textcid = getView().findViewById(R.id.textPlanCidSpinner);
        TextView textPlanCropSpinner = getView().findViewById(R.id.textPlanCropSpinner);
        TextView textmyDate = getView().findViewById(R.id.myDatePlanFarmer);
        EditText plan1 = getView().findViewById(R.id.addplanFarmer1);
        EditText plan2 = getView().findViewById(R.id.addplanFarmer2);
        EditText plan3 = getView().findViewById(R.id.addplanFarmer3);

        midString = textmid.getText().toString().trim();//แปลงค่าText ให้เป็น String , trim ลบค่าที่เว้นวรรคอัตโนวัติ
        nameString = textName.getText().toString().trim();
        cidNameString = textcid.getText().toString().trim();
        cropNameString = textPlanCropSpinner.getText().toString().trim();
        myDataString = textmyDate.getText().toString().trim();

//        String editTextString = Float.toString(Float.parseFloat(plan1.getText().toString().trim())
//                +(Float.parseFloat(plan2.getText().toString().trim())*100+Float.parseFloat(plan3.getText().toString().trim()))/400);
        areaString = Float.toString(Float.parseFloat(plan1.getText().toString().trim())
                + (Float.parseFloat(plan2.getText().toString().trim()) * 100 + Float.parseFloat(plan3.getText().toString().trim())) / 400);


        MyAlertCrop myAlertCrop = new MyAlertCrop(getActivity());
        if (midString.isEmpty()) {
            myAlertCrop.onrmaIDialog("กรุณาเลือก", "เกษตรกร");
        } else if (cidNameString.isEmpty()) {
            myAlertCrop.onrmaIDialog("กรุณาเลืแก", "พืชที่จะเพาะปลูก");
        } else if (myDataString.isEmpty()) {
            myAlertCrop.onrmaIDialog("กรุณาเลือก", "วันที่ที่จะเพาะปลูก");
        } else if (areaString.isEmpty()) {
            myAlertCrop.onrmaIDialog("กรุณาใส่", "ใส่ขนาดพื้นที่ที่จะเพาะปลูก");
        } else {
            comfirmUpload();
        }
    }

    private void comfirmUpload() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("ข้อมูลการวางแผนเพาะปลูก");
        builder.setMessage("ชื่อเกษตรกร = " + nameString + "\n"
                + "ชื่อพืช = " + cropNameString + "\n"
                + "วันที่เพาะปลูก = " + myDataString + "\n"
                + "ขนาดพื้นที่เพาะปลูก = " + areaString + "ไร่");
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
            AddPlan addPlan = new AddPlan(getActivity());
            addPlan.execute(midString, myDataString, cidNameString, areaString,
                    myconstant.getUrladdPlan());

            String result = addPlan.get();
            Log.d("plan", "result ==> " + result);
            if (Boolean.parseBoolean(result)) {
                getActivity().getSupportFragmentManager().popBackStack();
            } else {
                Toast.makeText(getActivity(), "เพิ่มข้อมูลเรียบร้อย", Toast.LENGTH_LONG).show();
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentAdminFragment, new PlanViewFragment1())
                        .addToBackStack(null)
                        .commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CreateToolbal() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarPlanFarmer);
        ((AdminActivity)getActivity()).setSupportActionBar(toolbar);

        ((AdminActivity)getActivity()).getSupportActionBar().setTitle("เพิ่มข้อมูลการวางแผนเพาะปลูก");
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
        View view = inflater.inflate(R.layout.frm_planfarmer, container, false);
        return view;
    }
}
