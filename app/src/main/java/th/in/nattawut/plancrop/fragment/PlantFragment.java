package th.in.nattawut.plancrop.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.AddPlant;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.GetDataWhereOneColumn;
import th.in.nattawut.plancrop.utility.MyAlertCrop;
import th.in.nattawut.plancrop.utility.Myconstant;

public class PlantFragment extends Fragment {


    //Button selctDate;
    ImageView selctDate;
    TextView date;
    DatePickerDialog dataPickerDialog;
    Calendar calendar;
    private String idRecord;

    String siteSpinnerString;
    TextView qty,yield;
    EditText rai, ngan, wa;
    String sum;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        //Create Toolbal
//        createToolbal();

        //PlanFarmerSpinner
        cropSpinner();

        //Pdate Controller
        pdateController();

        plantController();

        siteController();

        setUpTexeShowMid();

        calculator();


    }

    private void calculator() {
        ImageView calculator = getView().findViewById(R.id.calculator);
        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                rai = getView().findViewById(R.id.addplan1);
                ngan = getView().findViewById(R.id.addplan2);
                wa = getView().findViewById(R.id.addplan3);
                yield = getView().findViewById(R.id.textyield);
                qty = getView().findViewById(R.id.textQty);
                Float area = Float.valueOf(Float.toString(Float.parseFloat(rai.getText().toString().trim())
                        + (Float.parseFloat(ngan.getText().toString().trim()) * 100 + Float.parseFloat(wa.getText().toString().trim())) / 400));
                sum = decimalFormat.format((int) (Float.parseFloat(yield.getText().toString().trim())*area));
                qty.setText(sum);

            }
        });
    }

    private void plantController() {
        Button button = getView().findViewById(R.id.btnPlant);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plantAdd();
            }
        });
    }

    private void plantAdd() {
        TextView textSite = getView().findViewById(R.id.textPlantSiteSnoSpinner);
        TextView textPlantCidSpinner = getView().findViewById(R.id.textPlanCidSpinner);
        TextView textMyDate = getView().findViewById(R.id.myDatePlant);
         rai = getView().findViewById(R.id.addplan1);
         ngan = getView().findViewById(R.id.addplan2);
         wa = getView().findViewById(R.id.addplan3);

        String siteString = textSite.getText().toString().trim();//แปลงค่าText ให้เป็น String , trim ลบค่าที่เว้นวรรคอัตโนวัติ
        String cidString = textPlantCidSpinner.getText().toString().trim();
        String myDataString = textMyDate.getText().toString().trim();

        String addPlantTextString = Float.toString(Float.parseFloat(rai.getText().toString().trim())
                + (Float.parseFloat(ngan.getText().toString().trim()) * 100 + Float.parseFloat(wa.getText().toString().trim())) / 400);

        Spinner spin = getView().findViewById(R.id.Siteid);
        siteSpinnerString = spin.getSelectedItem().toString().trim();

        MyAlertCrop myAlertCrop = new MyAlertCrop(getActivity());
//        if (siteString.isEmpty() || cidString.isEmpty() || myDataString.isEmpty() || addPlantTextString.isEmpty()) {
//            myAlertCrop.onrmaIDialog("โปรดกรอก", "กรุณากรอกข้อมูลให้ครบ");
//
        if (siteString.isEmpty()) {
            myAlertCrop.onrmaIDialog("กรุณาเลือก","หมู่บ้านที่ตั้งแปลงเพาะปลูก");
        } else if (cidString.isEmpty()) {
            myAlertCrop.onrmaIDialog("กรุณาเลือก", "พืชที่ปลูก");
        } else if (myDataString.isEmpty()) {
            myAlertCrop.onrmaIDialog("กรุณาเลือก", "วันที่เพาะปลูก");
        } else if (addPlantTextString.isEmpty()) {
            myAlertCrop.onrmaIDialog("กรุณาใส่", "ขนาดพื้นที่ที่จะเพาะปลูก");
        } else {
            try {
                Myconstant myconstant = new Myconstant();
                AddPlant addPlant = new AddPlant(getActivity());
                addPlant.execute(myDataString, cidString, addPlantTextString, siteString,
                        myconstant.getUrladdPlant());

                String result = addPlant.get();
                Log.d("plant", "result ==> " + result);

                if (Boolean.parseBoolean(result)) {
                    getActivity().getSupportFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getActivity(), "เพิ่มข้อมูลเรียบร้อย", Toast.LENGTH_LONG).show();
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contentHomeFragment, new PlantFarmerViewFragment())
                            .addToBackStack(null)
                            .commit();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setUpTexeShowMid() {
        TextView textNameFarmer = getView().findViewById(R.id.textNameFarmer);

        String strTextShow = getActivity().getIntent().getExtras().getString("name");
        textNameFarmer.setText(strTextShow);
    }

    private void siteController() {
        if (android.os.Build.VERSION.SDK_INT > 9) { //setup policy เเพื่อมือถือที่มีประปฏิบัติการสูงกว่านีจะไม่สามารถconnectกับโปรโตรคอลได้
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final Spinner spin = getView().findViewById(R.id.Siteid);
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
            map = new HashMap<String, String>();
            map.put("sno", "");
            map.put("thai", "");
            MyArrList.add(map);

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

    private void pdateController() {
        date = getActivity().findViewById(R.id.myDatePlant);
        selctDate = getActivity().findViewById(R.id.imageViewDatePlant);
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

    private void cropSpinner() {
        if (android.os.Build.VERSION.SDK_INT > 9) { //setup policy เเพื่อมือถือที่มีประปฏิบัติการสูงกว่านีจะไม่สามารถconnectกับโปรโตรคอลได้
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final Spinner spin = getView().findViewById(R.id.PlantCropSpinner);
        try {
            Myconstant myconstant = new Myconstant();
            GetData getData = new GetData(getActivity());
            getData.execute(myconstant.getUrlCrop1());

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
                map.put("yield", c.getString("yield"));
                MyArrList.add(map);
            }
            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_yield,
                    new String[]{"cid", "crop","yield"}, new int[]{R.id.textPlanCidSpinner, R.id.textPlanCropSpinner,R.id.textyield,});
            spin.setAdapter(sAdap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void createToolbal() {
//        Toolbar toolbar = getView().findViewById(R.id.toolbarPlant);
//        ((HomeActivity)getActivity()).setSupportActionBar(toolbar);
//
//        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("แปลงเพาะปลูก");
//        ((HomeActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
//        ((HomeActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().getSupportFragmentManager().popBackStack();
//            }
//        });
//    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_plant, container, false);
        return view;
    }
}
