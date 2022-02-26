package th.in.nattawut.plancrop.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import th.in.nattawut.plancrop.MainActivity;
import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.AddAmpur;
import th.in.nattawut.plancrop.utility.AddProvince;
import th.in.nattawut.plancrop.utility.AddRegister;
import th.in.nattawut.plancrop.utility.AddSubDistrict;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.MyAlert;
import th.in.nattawut.plancrop.utility.Myconstant;

public class RegisterFragment extends Fragment {

    private ArrayList<String> arrProvince = new ArrayList<>();
    private ArrayList<String> arrProvinceID = new ArrayList<>();

    private ArrayList<String> arrAmphur = new ArrayList<>();
    private ArrayList<String> arrAmphurID = new ArrayList<>();

    private ArrayList<String> arrSid = new ArrayList<>();
    private ArrayList<String> arrSidID = new ArrayList<>();

    private ArrayList<String> arrVid = new ArrayList<>();
    private ArrayList<String> arrVidID = new ArrayList<>();

    private ArrayAdapter<String> adpProvince,adpAmphur,adpSid,adpVid;
    private Spinner spProvince,spAmphur, spSubDistrice,spVillag;
    private int rubIDprovince;

    //private MaterialSpinner spProvince,spAmphur, spSubDistrice,spVillag;
    //private MaterialSpinner spProvince,spAmphur, spSubDistrice,spVillag;;

    private String provinceString,amphurString,subDistriceString,villagString;



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Create Toolbar
        createToolbar();

        //RegisterController
        registerController();

        spProvince = getView().findViewById(R.id.spProvince);
        Province();
        spAmphur = getView().findViewById(R.id.spAmphur);

        spSubDistrice = getView().findViewById(R.id.spSubDistrice);

        spVillag = getView().findViewById(R.id.spVillagMember);

    }




    private void registerController() {
        Button button = getView().findViewById(R.id.btnRegister);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadValueToSever();
            }
        });
    }

    /*
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            if (item.getItemId() == R.id.itemupload) {

                uploadValueToSever();
                return true;

            }


            return super.onOptionsItemSelected(item);
        }*/

    private void uploadValueToSever() {

        EditText username = getView().findViewById(R.id.edtusername);
        EditText password = getView().findViewById(R.id.edtpassword);
        EditText id = getView().findViewById(R.id.edtid);
        EditText name = getView().findViewById(R.id.edtname);
        EditText address = getView().findViewById(R.id.edtaddress);
//        Spinner province = getView().findViewById(R.id.spProvince);
//        Spinner amphur = getView().findViewById(R.id.spAmphur);
//        Spinner subDistrice = getView().findViewById(R.id.spSubDistrice);
//        Spinner villag = getView().findViewById(R.id.spVillag);
        TextView province = getView().findViewById(R.id.pid);
        TextView amphur = getView().findViewById(R.id.did);
        TextView subDistrice = getView().findViewById(R.id.sid);
        TextView villag = getView().findViewById(R.id.vid);
        EditText phon = getView().findViewById(R.id.edtphone);
        EditText email = getView().findViewById(R.id.edtemail);


        String userString = username.getText().toString().trim();
        String passwordString = password.getText().toString().trim();
        String nameString = name.getText().toString().trim();
        String idString = id.getText().toString().trim();
        String addressString = address.getText().toString().trim();
        String provinceString = province.getText().toString().trim();
        String amphurString = amphur.getText().toString().trim();
        String subDistriceString = subDistrice.getText().toString().trim();
        String villagString = villag.getText().toString().trim();
//        provinceString = province.getSelectedItem().toString().trim();
//        amphurString = amphur.getSelectedItem().toString().trim();
//        subDistriceString = subDistrice.getSelectedItem().toString().trim();
//        villagString = villag.getSelectedItem().toString().trim();
        String phonString = phon.getText().toString().trim();
        String emailString = email.getText().toString().trim();



        if (userString.isEmpty() || passwordString.isEmpty() || idString.isEmpty() || nameString.isEmpty()  || addressString.isEmpty()  /*|| provinceString.isEmpty() || amphurString.isEmpty() || subDistriceString.isEmpty() || villagString.isEmpty()*/ || phonString.isEmpty() || emailString.isEmpty()) {
            MyAlert myAlert = new MyAlert(getActivity());
            myAlert.onrmaIDialog("สวัสดี", "*กรุณากรอกข้อมูลให้ครบ");
        } else {

            try {
                Myconstant myconstant = new Myconstant();
                AddRegister addFarmer = new AddRegister(getActivity());
                addFarmer.execute(userString, passwordString, idString, nameString, addressString, provinceString, amphurString, subDistriceString,villagString, phonString, emailString,
                        myconstant.getUrlRegister());

                String result = addFarmer.get();
                Log.d("1jan", "result ==>" + result + arrProvinceID);

                if (Boolean.parseBoolean(result)) {
                    getActivity().getSupportFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getActivity(), "สมัครสมาชิกเรียบร้อย", Toast.LENGTH_LONG).show();
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contentMainFragment, new MainFragment())
                            .addToBackStack(null)
                            .commit();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.manu_register, menu);
    }*/

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarRegister);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("สมัครสมาชิก");
        //((MainActivity)getActivity()).getSupportActionBar().setSubtitle("ddbdbvd");

        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        View view = inflater.inflate(R.layout.frm_register, container, false);
        return view;
    }
}


