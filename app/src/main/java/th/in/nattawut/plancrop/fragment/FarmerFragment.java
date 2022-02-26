package th.in.nattawut.plancrop.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import th.in.nattawut.plancrop.utility.AddAmpur;
import th.in.nattawut.plancrop.utility.AddFarmer;
import th.in.nattawut.plancrop.utility.AddProvince;
import th.in.nattawut.plancrop.utility.AddSubDistrict;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.MyAlertCrop;
import th.in.nattawut.plancrop.utility.Myconstant;

public class FarmerFragment extends Fragment {

    private Spinner spProvince,spAmphur, spSubDistrice,spVillag;

    private String userString, passwordString, idString, nameString, addressString,provinceString,amphurString,subDistriceString,villagString,phonString,emailString,editTextString,provincethai,amphurthai,subDistricethai,villagthai;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Create Toolbar
        CreateToolbal();

        //FarmerController
        farmerController();

        spProvince = getView().findViewById(R.id.spProvinceFarmer);
        Province();
        spAmphur = getView().findViewById(R.id.spAmphurFarmer);

        spSubDistrice = getView().findViewById(R.id.spDistriceFarmer);

        spVillag = getView().findViewById(R.id.spVillag);


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
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_province,
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
//            map = new HashMap<String, String>();
//            map.put("", "");
//            map.put("", "");
//            MyArrList.add(map);

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("did", c.getString("did"));
                map.put("thai", c.getString("thai"));
                MyArrList.add(map);

            }
            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_amphur,
                    new String[]{"did", "thai"}, new int[]{R.id.did, R.id.didthai});
            spAmphur.setAdapter(sAdap);
            //spAmphur.setSelection(1);

            spAmphur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (spAmphur.getSelectedItem() != null) {
                        SubDistrice(MyArrList.get(position).get("did"));
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
            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("sid", c.getString("sid"));
                map.put("thai", c.getString("thai"));
                MyArrList.add(map);
            }
            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_subdistrice,
                    new String[]{"sid", "thai"}, new int[]{R.id.sid, R.id.sidthai});
            spSubDistrice.setAdapter(sAdap);

            spSubDistrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (spSubDistrice.getSelectedItem() != null) {
                        Villag(MyArrList.get(position).get("sid"));
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

    public void Villag(String SubDistrice) {
        try {
            Myconstant myconstant = new Myconstant();
            AddSubDistrict addSubDistrict = new AddSubDistrict(getActivity());
            addSubDistrict.execute(SubDistrice,myconstant.getUrlVid());

            Log.d("am","aa"+ addSubDistrict);
            String jsonString = addSubDistrict.get();
            JSONArray data = new JSONArray(jsonString);

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("vid", c.getString("vid"));
                map.put("thai", c.getString("thai"));
                MyArrList.add(map);
            }
            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_village,
                    new String[]{"vid", "thai"}, new int[]{R.id.vid, R.id.vidthai});
            spVillag.setAdapter(sAdap);


        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private void farmerController() {
        Button button = getView().findViewById(R.id.btnFarmer);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadValueToSever();
            }
        });
    }

    private void uploadValueToSever() {

        EditText username = getView().findViewById(R.id.edtusername);
        EditText password = getView().findViewById(R.id.edtpassword);
        EditText id = getView().findViewById(R.id.edtid);
        EditText name = getView().findViewById(R.id.edtname);
        EditText address = getView().findViewById(R.id.edtaddress);
        TextView province = getView().findViewById(R.id.pid);
        TextView amphur = getView().findViewById(R.id.did);
        TextView subDistrice = getView().findViewById(R.id.sid);
        TextView villag = getView().findViewById(R.id.vid);
        EditText phon = getView().findViewById(R.id.edtphone);
        EditText email = getView().findViewById(R.id.edtemail);


        TextView provincet1 = getView().findViewById(R.id.pidthai);
        TextView amphur1 = getView().findViewById(R.id.didthai);
        TextView subDistrice1 = getView().findViewById(R.id.sidthai);
        TextView villag1 = getView().findViewById(R.id.vidthai);


        provincethai = provincet1.getText().toString().trim();
        amphurthai = amphur1.getText().toString().trim();
        subDistricethai = subDistrice1.getText().toString().trim();
        villagthai = villag1.getText().toString().trim();

        userString = username.getText().toString().trim();
        passwordString = password.getText().toString().trim();
        idString = id.getText().toString().trim();
        nameString = name.getText().toString().trim();
        addressString = address.getText().toString().trim();
        provinceString = province.getText().toString().trim();
        amphurString = amphur.getText().toString().trim();
        subDistriceString = subDistrice.getText().toString().trim();
        villagString = villag.getText().toString().trim();
        phonString = phon.getText().toString().trim();
        emailString = email.getText().toString().trim();


        EditText plan1 = getView().findViewById(R.id.add1);
        EditText plan2 = getView().findViewById(R.id.add2);
        EditText plan3 = getView().findViewById(R.id.add3);

        editTextString = Float.toString(Float.parseFloat(plan1.getText().toString().trim())
                +(Float.parseFloat(plan2.getText().toString().trim())*100+Float.parseFloat(plan3.getText().toString().trim()))/400);


        ////
        MyAlertCrop myAlertCrop = new MyAlertCrop(getActivity());
        if (userString.isEmpty()) {
            myAlertCrop.onrmaIDialog("โปรดกรอก", "กรุณากรอกชื่อผู้ใช้งาน");
        } else if (passwordString.isEmpty()) {
            myAlertCrop.onrmaIDialog("โปรดกรอก", "กรุณากรอกรหัสผ่าน");
        } else if (passwordString.length() < 3) {
            password.setError("รหัสผ่านควรมีความยาวอย่างน้อย 3 ตัว");
            password.requestFocus();
        }else if (nameString.isEmpty()){
            myAlertCrop.onrmaIDialog("โปรดกรอก", "กรุณากรอกชื่อ-นามสกุล");
        }else if (idString.isEmpty()){
            myAlertCrop.onrmaIDialog("โปรดกรอก", "กรุณารหัสบัตรประชาชน");
        } else if (idString.length() < 13) {
            id.setError("ต้องมีความยาวอย่างน้อย 13 ตัว");
            id.requestFocus();
        } else if (phonString.isEmpty()) {
            myAlertCrop.onrmaIDialog("โปรดกรอก", "กรุณากรอกเบอร์โทรศํพท์");
        } else if (phonString.length() < 10) {
            phon.setError("เบอร์โทรควรมีความยาวอย่างน้อย 10 ตัว");
            phon.requestFocus();
        } else if (emailString.isEmpty()) {
            myAlertCrop.onrmaIDialog("โปรดกรอก", "กรุณากรอกเอีเมล์");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
            email.setError("ป้อนอีเมลที่ถูกต้อง");
            email.requestFocus();
        }else if (editTextString.isEmpty()){
            myAlertCrop.onrmaIDialog("โปรดกรอก", "กรุณากรอกพื้นที่เพาะปลูก");
//        } else if (editTextString.length() < 0) {
//            plan1.setError("เบอรืโทรควรมีความยาวอย่างน้อย 10 ตัว");
//            plan2.setError("เบอรืโทรควรมีความยาวอย่างน้อย 10 ตัว");
//            plan3.setError("เบอรืโทรควรมีความยาวอย่างน้อย 10 ตัว");
//            plan1.requestFocus();
//            plan2.requestFocus();
//            plan3.requestFocus();
            //}if (idString.isEmpty()|| addressString.isEmpty() || provinceString.isEmpty() || amphurString.isEmpty() || subDistriceString.isEmpty() || villagString.isEmpty() || emailString.isEmpty() ) {

        }else {
            uploadFarmer();
        }
    }

    private void uploadFarmer() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("ข้อมูลเกษตรกร");
        builder.setMessage("ชื่อผู้ใช้งาน = " + userString + "\n"
                + "รหัสผ่าน = " + passwordString + "\n"
                + "ชื่อ-นามสกุล = " + nameString + "\n"
                + "รหัสปชช = " + idString + "\n"
                + "ที่อยู่ = " + addressString + "\n"
                + "จังหวัด = " + provincethai + "\n"
                + "อำเภอ = " + amphurthai + "\n"
                + "ตำบล = " + subDistricethai + "\n"
                + "หมู่บ้าน = " + villagthai + "\n"
                + "เบอร์โทรศัพท์ = " + phonString + "\n"
                + "อีเมล์ = " + emailString + "\n"
                + "พื้นที่เพาะปลูก = " + editTextString);
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
                AddFarmer addFarmer = new AddFarmer(getActivity());
                addFarmer.execute(userString, passwordString, idString, nameString, addressString,provinceString,amphurString,subDistriceString,villagString,phonString,emailString,editTextString,
                        myconstant.getUrlFarmer());

                String result = addFarmer.get();
                Log.d("1jan", "result ==>" + result);

                if (Boolean.parseBoolean(result)) {
                    getActivity().getSupportFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getActivity(), "ลงทะเบียนเกษตรกรเรียบร้อย", Toast.LENGTH_LONG).show();
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contentAdminFragment, new FarmerViewAdminFragment())
                            .addToBackStack(null)
                            .commit();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    private void CreateToolbal() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarFarmer);
        ((AdminActivity)getActivity()).setSupportActionBar(toolbar);

        ((AdminActivity)getActivity()).getSupportActionBar().setTitle("เพิ่มข้อมูลเกษตรกร");
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
        View view = inflater.inflate(R.layout.frm_farmer, container, false);
        return view;
    }
}



