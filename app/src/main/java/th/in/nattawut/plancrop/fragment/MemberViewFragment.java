package th.in.nattawut.plancrop.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
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

import th.in.nattawut.plancrop.MemberActivity;
import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.AddAmpur;
import th.in.nattawut.plancrop.utility.AddProvince;
import th.in.nattawut.plancrop.utility.AddSubDistrict;
import th.in.nattawut.plancrop.utility.EditMemberandroid;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.GetDataWhereOneColumn;
import th.in.nattawut.plancrop.utility.MyAlert;
import th.in.nattawut.plancrop.utility.Myconstant;

public class MemberViewFragment extends Fragment {

    Myconstant myconstant = new Myconstant();
    private String idRecord;
    private boolean aBoolean = true;

    public MemberViewFragment() {

    }
    private ArrayList<String> arrProvince = new ArrayList<>();
    private ArrayList<String> arrProvinceID = new ArrayList<>();

    private ArrayList<String> arrAmphur = new ArrayList<>();
    private ArrayList<String> arrAmphurID = new ArrayList<>();

    private ArrayList<String> arrSid = new ArrayList<>();
    private ArrayList<String> arrSidID = new ArrayList<>();

    private ArrayList<String> arrVid = new ArrayList<>();
    private ArrayList<String> arrVidID = new ArrayList<>();

    private Spinner spProvince,spAmphur, spSubDistrice,spVillag;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showView();

        editController();

        CreateToolbal();

        spProvince = getView().findViewById(R.id.spProvinceMember);
        Province();
        spAmphur = getView().findViewById(R.id.spAmphurMember);

        spSubDistrice = getView().findViewById(R.id.spDistriceMember);

        spVillag = getView().findViewById(R.id.spVillagMember);

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


    private void CreateToolbal() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarMember);
        ((MemberActivity)getActivity()).setSupportActionBar(toolbar);

        ((MemberActivity)getActivity()).getSupportActionBar().setTitle("ข้อมูลส่วนตัว");
        //((MemberActivity)getActivity()).getSupportActionBar().setSubtitle(nameString);

        ((MemberActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MemberActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        setHasOptionsMenu(true);

    }

    private void editController() {
        Button button = getView().findViewById(R.id.btnEditMember);
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
            getDataWhereOneColumn.execute("mid", idRecord, myconstant.getSelectMemberAndroid());

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

//            TextView spProvinceFarmer = getView().findViewById(R.id.spProvinceFarmer);
//            spProvinceFarmer.setText(jsonObject.getString("pid"));
//
//            TextView spAmphurFarmer = getView().findViewById(R.id.spAmphurFarmer);
//            spAmphurFarmer.setText(jsonObject.getString("did"));
//
//            TextView spDistriceFarmer = getView().findViewById(R.id.spDistriceFarmer);
//            spDistriceFarmer.setText(jsonObject.getString("sid"));
//
//            TextView spVillag = getView().findViewById(R.id.spVillag);
//            spVillag.setText(jsonObject.getString("vid"));

            TextView edtphone = getView().findViewById(R.id.edtphone);
            edtphone.setText(jsonObject.getString("tel"));

            TextView edtemail = getView().findViewById(R.id.edtemail);
            edtemail.setText(jsonObject.getString("email"));

//            TextView add1 = getView().findViewById(R.id.add1);
//            add1.setText(jsonObject.getString("area"));

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


        MyAlert myAlert = new MyAlert(getActivity());//การสร้างออปเจ็ค
        // check Spqce  การหาช่องว่าง
        if (newmid.isEmpty() || newusername.isEmpty() || newPassword.isEmpty() || newID.isEmpty() || newName.isEmpty() || newAddress.isEmpty()
                || newprovince.isEmpty() || newamphur.isEmpty() || newsubDistrice.isEmpty() || newvillag.isEmpty()
                || newPhone.isEmpty() || newEmail.isEmpty()) {

            myAlert.onrmaIDialog("มีช่องว่าง", "กรุณาเติมทุกช่องว่าง");
        } else {
            try {
                Myconstant myconstant = new Myconstant();
                EditMemberandroid editMemberandroid = new EditMemberandroid(getActivity());
                editMemberandroid.execute(newmid, newusername, newPassword, newID, newName, newAddress, newprovince, newamphur, newsubDistrice, newvillag,
                        newPhone, newEmail, myconstant.getUrlEditMemberAndroid());

                if (Boolean.parseBoolean(editMemberandroid.get())) {
                } else {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contentMemberFragment,new MemberFragment())
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
        return inflater.inflate(R.layout.frm_member_view, container, false);
    }
}
