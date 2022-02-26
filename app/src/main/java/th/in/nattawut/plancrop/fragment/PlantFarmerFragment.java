package th.in.nattawut.plancrop.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import th.in.nattawut.plancrop.utility.AddPlant;
import th.in.nattawut.plancrop.utility.AddSiteFarmer;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.MyAlertCrop;
import th.in.nattawut.plancrop.utility.Myconstant;

public class PlantFarmerFragment extends Fragment {


    //Button selctDate;
    ImageView selctDate;
    TextView date;
    DatePickerDialog dataPickerDialog;
    Calendar calendar;
    private String idRecord;

    String siteSpinnerString;

    private ArrayList<String> arrmid = new ArrayList<>();
    private ArrayList<String> arrname = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        //Create Toolbal
        CreateToolbal();

        //PlanFarmerSpinner
        cropSpinner();

        //Pdate Controller
        pdateController();

        plantController();



        selectFarmer();

    }

    private void plantController() {
        Button button = getView().findViewById(R.id.btnPlantFarmer);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plantAdd();
            }
        });
    }

    private void plantAdd() {
        TextView textMId = getView().findViewById(R.id.textMId);
        TextView textSite = getView().findViewById(R.id.textPlantSiteSnoSpinner);
        TextView textPlantCidSpinner = getView().findViewById(R.id.textPlanCidSpinner);
        TextView textMyDate = getView().findViewById(R.id.myDatePlantFramer);
        EditText plan1 = getView().findViewById(R.id.addplantFramer1);
        EditText plan2 = getView().findViewById(R.id.addplantFramer2);
        EditText plan3 = getView().findViewById(R.id.addplantFramer3);

        String midString = textMId.getText().toString().trim();
        String siteString = textSite.getText().toString().trim();//แปลงค่าText ให้เป็น String , trim ลบค่าที่เว้นวรรคอัตโนวัติ
        String cidString = textPlantCidSpinner.getText().toString().trim();
        String myDataString = textMyDate.getText().toString().trim();

        String addPlantTextString = Float.toString(Float.parseFloat(plan1.getText().toString().trim())
                + (Float.parseFloat(plan2.getText().toString().trim()) * 100 + Float.parseFloat(plan3.getText().toString().trim())) / 400);

//        Spinner spin = getView().findViewById(R.id.Siteid);
//        siteSpinnerString = spin.getSelectedItem().toString().trim();

        MyAlertCrop myAlertCrop = new MyAlertCrop(getActivity());
//        if (siteString.isEmpty() || cidString.isEmpty() || myDataString.isEmpty() || addPlantTextString.isEmpty()) {
//            myAlertCrop.onrmaIDialog("โปรดกรอก", "กรุณากรอกข้อมูลให้ครบ");
//        } else {
        if (midString.isEmpty()){
            myAlertCrop.onrmaIDialog("กรุณาเลือก","เกษตรกร");
        } else if (siteString.isEmpty()) {
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
                            .replace(R.id.contentAdminFragment, new PlantViewFragment1())
                            .addToBackStack(null)
                            .commit();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void selectFarmer() {
        if (android.os.Build.VERSION.SDK_INT > 9) { //setup policy เเพื่อมือถือที่มีประปฏิบัติการสูงกว่านีจะไม่สามารถconnectกับโปรโตรคอลได้
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final Spinner spin = getView().findViewById(R.id.spPlanNameFramer);
        try {

            Myconstant myconstant = new Myconstant();


            GetData getData = new GetData(getActivity());
            getData.execute(myconstant.getUrlSiteFarmer());

            String jsonString = getData.get();
            Log.d("5/Jan PlanCropSpinner", "JSON ==>" + jsonString);
            JSONArray data = new JSONArray(jsonString);

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;
//            map = new HashMap<String, String>();
//            map.put("mid", "");
//            map.put("name", "");
//            MyArrList.add(map);

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
        final Spinner spin = getView().findViewById(R.id.SiteidPlantFramer);
        try {

            Myconstant myconstant = new Myconstant();
            AddSiteFarmer addSiteFarmer = new AddSiteFarmer(getActivity());
            addSiteFarmer.execute(mid,myconstant.getSelectsitefarmer());

            String jsonString = addSiteFarmer.get();
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
        date = getActivity().findViewById(R.id.myDatePlantFramer);
        selctDate = getActivity().findViewById(R.id.imageViewDatePlantFramer);
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
        final Spinner spin = getView().findViewById(R.id.PlantCropSpinnerFramer);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CreateToolbal() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarPlantFarmer);
        ((AdminActivity)getActivity()).setSupportActionBar(toolbar);

        ((AdminActivity)getActivity()).getSupportActionBar().setTitle("เพิ่มข้อมูลการเพาะปลูก");
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
        View view = inflater.inflate(R.layout.frm_plantfarmer, container, false);
        return view;
    }
}
