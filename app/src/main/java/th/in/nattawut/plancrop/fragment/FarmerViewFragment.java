package th.in.nattawut.plancrop.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.AddAmpur;
import th.in.nattawut.plancrop.utility.AddProvince;
import th.in.nattawut.plancrop.utility.AddVillag;
import th.in.nattawut.plancrop.utility.EditFarmerandroid;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.GetDataWhereOneColumn;
import th.in.nattawut.plancrop.utility.MyAlert;
import th.in.nattawut.plancrop.utility.Myconstant;

public class FarmerViewFragment extends Fragment {

    Myconstant myconstant = new Myconstant();
    private String idRecord;
    private boolean aBoolean = true;

    public FarmerViewFragment() {

    }

    private ArrayList<String> arrProvince = new ArrayList<>();
    private ArrayList<String> arrProvinceID = new ArrayList<>();

    private ArrayList<String> arrAmphur = new ArrayList<>();
    private ArrayList<String> arrAmphurID = new ArrayList<>();

    private ArrayList<String> arrSid = new ArrayList<>();
    private ArrayList<String> arrSidID = new ArrayList<>();

    private ArrayList<String> arrVid = new ArrayList<>();
    private ArrayList<String> arrVidID = new ArrayList<>();

    private Spinner spProvince, spAmphur, spSubDistrice, spVillag;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showView();

        editController();

        spProvince = getView().findViewById(R.id.spProvinceFarmer);
        Province();
        spAmphur = getView().findViewById(R.id.spAmphurFarmer);

        spSubDistrice = getView().findViewById(R.id.spDistriceFarmer);

        spVillag = getView().findViewById(R.id.spVillagFarmer);
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

                arrProvinceID.add(c.getString("pid"));
                arrProvince.add(c.getString("thai"));
                MyArrList.add(map);
            }
            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_province,
                    new String[]{"pid", "thai"}, new int[]{R.id.pid, R.id.pidthai});
            spProvince.setAdapter(sAdap);
            spProvince.setSelection(25);

        } catch (Exception e) {
            e.printStackTrace();
        }

        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spProvince.getSelectedItem() != null) {
                    Amphur(arrProvinceID.get(position));

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void Amphur(String province) {
        try {
            Myconstant myconstant = new Myconstant();
            AddProvince addProvince = new AddProvince(getActivity());
            addProvince.execute(province, myconstant.getUrlAmphur());

            String jsonString = addProvince.get();
            JSONArray data = new JSONArray(jsonString);

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("did", c.getString("did"));
                map.put("thai", c.getString("thai"));

                arrAmphurID.add(c.getString("did"));
                arrAmphur.add(c.getString("thai"));
                MyArrList.add(map);

            }
            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_amphur,
                    new String[]{"did", "thai"}, new int[]{R.id.did, R.id.didthai});
            spAmphur.setAdapter(sAdap);

            spAmphur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (spAmphur.getSelectedItem() != null) {
                        SubDistrice(arrAmphurID.get(position));
                        arrSid.clear();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SubDistrice(String amphur) {
        try {
            Myconstant myconstant = new Myconstant();
            AddAmpur addAmpur = new AddAmpur(getActivity());
            addAmpur.execute(amphur, myconstant.getUrlSid());

            String jsonString = addAmpur.get();
            JSONArray data = new JSONArray(jsonString);

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("sid", c.getString("sid"));
                map.put("thai", c.getString("thai"));

                arrSidID.add(c.getString("sid"));
                arrSid.add(c.getString("thai"));
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
                        Villag(arrSidID.get(position));

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Villag(String villag) {
        try {
            Myconstant myconstant = new Myconstant();
            AddVillag addVillag = new AddVillag(getActivity());
            addVillag.execute(villag, myconstant.getUrlVid());

            String jsonString = addVillag.get();
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editController() {
        Button button = getView().findViewById(R.id.btnEditFarmer);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadValueToServer();
            }
        });
    }

    private void showView() {
        try {

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(myconstant.getNameFileSharePreference(), Context.MODE_PRIVATE);
            idRecord = sharedPreferences.getString("mid", "");

            GetDataWhereOneColumn getDataWhereOneColumn = new GetDataWhereOneColumn(getActivity());
            getDataWhereOneColumn.execute("mid", idRecord, myconstant.getSelectfarmerandroid());

            String result = getDataWhereOneColumn.get();
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            TextView textViewmid = getView().findViewById(R.id.midtext);
            textViewmid.setText(jsonObject.getString("mid"));

            TextView edtusername = getView().findViewById(R.id.edtusername);
            edtusername.setText(jsonObject.getString("userid"));

            TextView edtpassword = getView().findViewById(R.id.edtpassword);
            edtpassword.setText(jsonObject.getString("pwd"));

            TextView edtid = getView().findViewById(R.id.edtid);
            edtid.setText(jsonObject.getString("id"));

            TextView edtname = getView().findViewById(R.id.edtname);
            edtname.setText(jsonObject.getString("name"));

            TextView edtaddress = getView().findViewById(R.id.edtaddress);
            edtaddress.setText(jsonObject.getString("address"));


//            TextView spProvinceFarmer = getView().findViewById(R.id.spProvince);
//            spProvinceFarmer.setText(jsonObject.getString("thai"));
//
//            TextView spAmphurFarmer = getView().findViewById(R.id.spAmphur);
//            spAmphurFarmer.setText(jsonObject.getString("thai"));
//
//            TextView spDistriceFarmer = getView().findViewById(R.id.spDistrice);
//            spDistriceFarmer.setText(jsonObject.getString("thai"));
//
//            TextView spVillag = getView().findViewById(R.id.spVillag);
//            spVillag.setText(jsonObject.getString("thai"));

            TextView edtphone = getView().findViewById(R.id.edtphone);
            edtphone.setText(jsonObject.getString("tel"));

            TextView edtemail = getView().findViewById(R.id.edtemail);
            edtemail.setText(jsonObject.getString("email"));

            EditText EditAddPlan1 = getView().findViewById(R.id.add1);
            EditText EditAddPlan2 = getView().findViewById(R.id.add2);
            EditText EditAddPlan3 = getView().findViewById(R.id.add3);

            EditAddPlan1.setText(String.valueOf((int) Math.floor(jsonObject.getDouble("area"))));
            EditAddPlan2.setText(String.valueOf((int) Math.floor((jsonObject.getDouble("area") * 400 % 400) / 100)));
            EditAddPlan3.setText(String.valueOf((int) Math.floor((jsonObject.getDouble("area") * 400) % 100)));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void uploadValueToServer() {
        TextView midtext = getView().findViewById(R.id.midtext);
        EditText edtusername = getView().findViewById(R.id.edtusername);
        EditText EditEdtPassword = getView().findViewById(R.id.edtpassword);
        EditText EditEdtId = getView().findViewById(R.id.edtid);
        EditText EditEdtName = getView().findViewById(R.id.edtname);
        EditText EditEdtAddress = getView().findViewById(R.id.edtaddress);
        TextView province = getView().findViewById(R.id.pid);
        TextView amphur = getView().findViewById(R.id.did);
        TextView subDistrice = getView().findViewById(R.id.sid);
        TextView villag = getView().findViewById(R.id.vid);
        EditText EditEdtPhone = getView().findViewById(R.id.edtphone);
        EditText EditEdtEmail = getView().findViewById(R.id.edtemail);
        EditText plan1 = getView().findViewById(R.id.add1);
        EditText plan2 = getView().findViewById(R.id.add2);
        EditText plan3 = getView().findViewById(R.id.add3);


        String newmid = midtext.getText().toString();
        String newusername = edtusername.getText().toString();
        String newPassword = EditEdtPassword.getText().toString();
        String newID = EditEdtId.getText().toString();
        String newName = EditEdtName.getText().toString();
        String newAddress = EditEdtAddress.getText().toString();
        String newprovince = province.getText().toString().trim();
        String newamphur = amphur.getText().toString().trim();
        String newsubDistrice = subDistrice.getText().toString().trim();
        String newvillag = villag.getText().toString().trim();
        String newPhone = EditEdtPhone.getText().toString();
        String newEmail = EditEdtEmail.getText().toString();
        String editTextString = Float.toString(Float.parseFloat(plan1.getText().toString().trim())
                + (Float.parseFloat(plan2.getText().toString().trim()) * 100 + Float.parseFloat(plan3.getText().toString().trim())) / 400);

        MyAlert myAlert = new MyAlert(getActivity());//การสร้างออปเจ็ค
        // check Spqce  การหาช่องว่าง
        if (newmid.isEmpty() || newusername.isEmpty() || newPassword.isEmpty() || newID.isEmpty() || newName.isEmpty() || newAddress.isEmpty()
                || newprovince.isEmpty() || newamphur.isEmpty() || newsubDistrice.isEmpty() || newvillag.isEmpty()
                || newPhone.isEmpty() || newEmail.isEmpty() || editTextString.isEmpty()) {

            myAlert.onrmaIDialog("มีช่องว่าง", "กรุณาเติมทุกช่องว่าง");
        } else {
            try {
                Myconstant myconstant = new Myconstant();
                EditFarmerandroid editFarmerandroid = new EditFarmerandroid(getActivity());
                editFarmerandroid.execute(newmid, newusername, newPassword, newID, newName, newAddress, newprovince, newamphur, newsubDistrice, newvillag,
                        newPhone, newEmail, editTextString, myconstant.getUrlEditFarmerAndroid());

                if (Boolean.parseBoolean(editFarmerandroid.get())) {
                } else {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contentHomeFragment, new MainPlanFragment())
                            .commit();
                    Toast.makeText(getActivity(), "แก้ไขข้อมูลสำเร็จ",
                            Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frm_farmerandroid_view, container, false);
    }
}
