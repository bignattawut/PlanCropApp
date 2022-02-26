package th.in.nattawut.plancrop.fragment;

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
import android.widget.Button;
import android.widget.EditText;
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
import th.in.nattawut.plancrop.utility.AddCrop;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.MyAlertCrop;
import th.in.nattawut.plancrop.utility.Myconstant;

public class CropFragment extends Fragment {

    private String cropString,tidString, BeginHarvestString, HarvestPeriodString, YieldString,cropTypeString;
    TextView textCropType;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Create Toolbal
        createToolbal();

        //cropTypeSpinner
        cropTypeSpinner();

        //CropController
        cropController();


    }

    private void cropTypeSpinner(){
        if (android.os.Build.VERSION.SDK_INT > 9) { //SDK มาก9 จะไม่สามารถconnextได้
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final Spinner spin = getView().findViewById(R.id.cropTypeSpinner);
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
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_crop,
                    new String[] {"tid", "croptype"}, new int[] {R.id.textTID, R.id.textCropType});
            spin.setAdapter(sAdap);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void cropController() {
        Button button = getView().findViewById(R.id.btnCrop);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCrop();
            }
        });
    }

    private void AddCrop() {

        EditText edtCrop = getView().findViewById(R.id.edtCropName);
        TextView tid = getView().findViewById(R.id.textTID);
        EditText edtBeginHarvest = getView().findViewById(R.id.edtBeginHarvest);
        EditText edtHarvestPeriod = getView().findViewById(R.id.edtHarvestPeriod);
        EditText edtYield = getView().findViewById(R.id.edtYield);

        TextView textCropType = getView().findViewById(R.id.textCropType);
        cropTypeString = textCropType.getText().toString().trim();

        cropString = edtCrop.getText().toString().trim();
        tidString = tid.getText().toString().trim();
        BeginHarvestString = edtBeginHarvest.getText().toString().trim();
        HarvestPeriodString = edtHarvestPeriod.getText().toString().trim();
        YieldString = edtYield.getText().toString().trim();

        MyAlertCrop myAlertCrop = new MyAlertCrop(getActivity());
        if (cropString.isEmpty()) {
            myAlertCrop.onrmaIDialog("กรุณาใส่", "ชื่อพืชเพาะปลูก");
        } else if (tidString.isEmpty()) {
            myAlertCrop.onrmaIDialog("กรุณาเลือก", "ประเภทพืชเพาะปลูก");
        }else if (BeginHarvestString.isEmpty()) {
            myAlertCrop.onrmaIDialog("กรุณาใส่", "จำนวนวันเก็บเกี่ยวได้");
        }else if (HarvestPeriodString.isEmpty()) {
            myAlertCrop.onrmaIDialog("กรุณาใส่", "ระยะเวลาที่เก็บเกี่ยวได้");
        }else if (YieldString.isEmpty()) {
            myAlertCrop.onrmaIDialog("กรุณาใส่", "ผลผลิตเฉลียต่อ (กิโลกรัม)");
        }else {
            comfirmUpload();
        }
    }

    private void comfirmUpload() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("ข้อมูลการลงทะเบียนพืชเพาปลูก");
        builder.setMessage("ชื่อพืช = " + cropString + "\n"
                + "ชื่อประเภทพืช = " + cropTypeString + "\n"
                + "จำนวนวันที่เก็บเกี่ยวได้ (วัน) = " + BeginHarvestString + "\n"
                + "ระยะเวลาที่เก็บเกี่ยวได้ (วัน) = " + HarvestPeriodString + "\n"
                + "ผลผลิตเฉลียต่อ (กิโลกรัม) = " + YieldString);
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

    private void uploadToServer(){
        try {
            Myconstant myconstant = new Myconstant();
            AddCrop addCrop = new AddCrop(getActivity());
            addCrop.execute(cropString,tidString, BeginHarvestString, HarvestPeriodString, YieldString,
                    myconstant.getUrlAddCrop());

            String result = addCrop.get();
            Log.d("crop", "result ==> " + result);
            if (Boolean.parseBoolean(result)) {
                getActivity().getSupportFragmentManager().popBackStack();
            } else {
                Toast.makeText(getActivity(), "เพิ่มข้อมูลเรียบร้อย", Toast.LENGTH_LONG).show();
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentAdminFragment, new CropViewFragment())
                        .addToBackStack(null)
                        .commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //toolbal
    private void createToolbal() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarCrop);
        ((AdminActivity)getActivity()).setSupportActionBar(toolbar);

        ((AdminActivity)getActivity()).getSupportActionBar().setTitle("ลงทะเบียนพืชเพาะปลูก");
        ((AdminActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AdminActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_crop, container,false);
        return view;
    }
}
