package th.in.nattawut.plancrop.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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
import th.in.nattawut.plancrop.utility.AddProvince;
import th.in.nattawut.plancrop.utility.AddSubDistrict;
import th.in.nattawut.plancrop.utility.DeleteFammer;
import th.in.nattawut.plancrop.utility.EditRegister;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.GetDataWhereRegister;
import th.in.nattawut.plancrop.utility.Myconstant;
import th.in.nattawut.plancrop.utility.RegisterViewAdpter;

public class RegisterViewAdminFragment extends Fragment {

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView listView;

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



    private String idLoginString;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //Create Toolbal
        CreateToolbal();

        //CreateLisView
        createLisView();

        //Swipe Refresh Layout
        swipeRefreshLayout();

        //idLoginString = getActivity().getIntent().getExtras().getString("mid");


    }


    private void swipeRefreshLayout() {
        mSwipeRefreshLayout = getView().findViewById(R.id.swiRefreshLayouRegister);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Create ListView
                        createLisView();

                    }
                },2);
            }
        });
    }


    private void createLisView() {
        final ListView listView = getView().findViewById(R.id.listViewRegister);
        Myconstant myconstant = new Myconstant();
        String[] columString = myconstant.getComlumRegisterString();

        try {
//            GetData getData = new GetData(getActivity());
//            getData.execute(myconstant.getUrlGetRegister());

            GetDataWhereRegister getDataWhereOneColumn = new GetDataWhereRegister(getActivity());
            getDataWhereOneColumn.execute("mid",myconstant.getUrlselectMember());

            String jsonString = getDataWhereOneColumn.get();
            Log.d("19jan","JSon register ==> "+ jsonString);

            JSONArray jsonArray = new JSONArray(jsonString);

            final String[] userString = new String[jsonArray.length()];
            final String[] passwordString = new String[jsonArray.length()];
            final String[] idString = new String[jsonArray.length()];
            final String[] nameString = new String[jsonArray.length()];
            final String[] addressString = new String[jsonArray.length()];
            final String[] pidString = new String[jsonArray.length()];
            final String[] didString = new String[jsonArray.length()];
            final String[] sidString = new String[jsonArray.length()];
            final String[] vidString = new String[jsonArray.length()];
            final String[] phonString = new String[jsonArray.length()];
            final String[] emailString = new String[jsonArray.length()];
            final String[] midString = new String[jsonArray.length()];

            for (int i=0; i<jsonArray.length(); i+=1){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    userString[i] = jsonObject.getString(columString[1]);
                    passwordString[i] = jsonObject.getString(columString[2]);
                    idString[i] = jsonObject.getString(columString[3]);
                    nameString[i] = jsonObject.getString(columString[4]);
                    addressString[i] = jsonObject.getString(columString[5]);
                    pidString[i] = jsonObject.getString(columString[6]);
                    didString[i] = jsonObject.getString(columString[7]);
                    sidString[i] = jsonObject.getString(columString[8]);
                    vidString[i] = jsonObject.getString(columString[9]);
                    phonString[i] = jsonObject.getString(columString[10]);
                    emailString[i] = jsonObject.getString(columString[11]);
                    midString[i] = jsonObject.getString(columString[0]);
            }
            final RegisterViewAdpter registerAdpter = new RegisterViewAdpter(getActivity(),
                    userString, passwordString,idString,nameString, addressString,pidString,didString,sidString,vidString, phonString, emailString);
            listView.setAdapter(registerAdpter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    deleteorEditRegister(midString[position],userString[position],passwordString[position],nameString[position],
                            idString[position],addressString[position],pidString[position],didString[position],sidString[position],vidString[position],phonString[position],emailString[position]);

                }
            });
            mSwipeRefreshLayout.setRefreshing(false);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    //alertให้เลือกลบหรือแก้ไข
    private void deleteorEditRegister(final String midString, final String userString, final String passwordString, final String nameString, final String idString, final String addressString,final String pidString, final String didString,final String sidString, final String vidString,final String phonString, final String emailString) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AlertDialogTheme);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.iconregister);
        builder.setTitle("ข้อมูลสมาชิก");
        builder.setMessage("กรุณาเลือก ลบ หรือ ดูข้อมูล ?");
        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("ลบ" ,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteRegister(midString);
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("ดูข้อมูล", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editRegister(midString,userString,passwordString,nameString,idString,addressString,pidString,didString,sidString,vidString,phonString,emailString);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //alertให้เลือกจะลบรายการหรือไม่
    private void deleteRegister(final String midString){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AlertDialogTheme);
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
                editDeleteRegister(midString);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //แก้ไขประเทพืชเพาะปลูก
    private void editRegister(final String midString, final String userString, final String passwordString, final String nameString,final String idString, final String addressString,
                              final String pidString, final String didString,
                              final String sidString, final String vidString,
                              final String phonString, final String emailString){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle("ข้อมูลส่วนตัว");

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.edit_register, null);


        EditText EditEdtUsername = view.findViewById(R.id.EditEdtUsername);
        String newUsername = getActivity().getIntent().getExtras().getString("UserID",userString);
        EditEdtUsername.setText(newUsername);

        EditText EditEdtPassword = view.findViewById(R.id.EditEdtPassword);
        String newPassword = getActivity().getIntent().getExtras().getString("PWD",passwordString);
        EditEdtPassword.setText(newPassword);

        EditText EditEdtName = view.findViewById(R.id.EditEdtName);
        String newName = getActivity().getIntent().getExtras().getString("Name",nameString);
        EditEdtName.setText(newName);

        EditText EditEdtId = view.findViewById(R.id.EditEdtId);
        String newId = getActivity().getIntent().getExtras().getString("ID",idString);
        EditEdtId.setText(newId);


        EditText EditEdtAddress = view.findViewById(R.id.EditEdtAddress);
        String newAddress = getActivity().getIntent().getExtras().getString("Address",addressString);
        EditEdtAddress.setText(newAddress);

        EditText EditEdtPhone = view.findViewById(R.id.EditEdtPhone);
        String newPhone = getActivity().getIntent().getExtras().getString("Tel",phonString);
        EditEdtPhone.setText(newPhone);

        EditText EditEdtEmail = view.findViewById(R.id.EditEdtEmail);
        String newEmail = getActivity().getIntent().getExtras().getString("EMail",emailString);
        EditEdtEmail.setText(newEmail);

        spProvince = view.findViewById(R.id.EditspProvinceAdminMember);
        Province();
        spAmphur = view.findViewById(R.id.EditspAmphurAdminMember);

        spSubDistrice = view.findViewById(R.id.EditspSubDistriceAdminMember);

        spVillag = view.findViewById(R.id.EditspVillagAdminMember);


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

                EditText editText = view.findViewById(R.id.EditEdtUsername);
                String newUsername = editText.getText().toString();

                EditText EditEdtPassword = view.findViewById(R.id.EditEdtPassword);
                String newPassword = EditEdtPassword.getText().toString();

                EditText EditEdtName = view.findViewById(R.id.EditEdtName);
                String newName = EditEdtName.getText().toString();

                EditText EditEdtId = view.findViewById(R.id.EditEdtId);
                String newID = EditEdtId.getText().toString();

                EditText EditEdtAddress = view.findViewById(R.id.EditEdtAddress);
                String newAddress = EditEdtAddress.getText().toString();

                TextView province = view.findViewById(R.id.pid);
                String provinceString = province.getText().toString().trim();

                TextView amphur = view.findViewById(R.id.did);
                String amphurString = amphur.getText().toString().trim();

                TextView subDistrice = view.findViewById(R.id.sid);
                String subDistriceString = subDistrice.getText().toString().trim();

                TextView villag = view.findViewById(R.id.vid);
                String villagString = villag.getText().toString().trim();

                EditText EditEdtPhone = view.findViewById(R.id.EditEdtPhone);
                String newPhone = EditEdtPhone.getText().toString();

                EditText EditEdtEmail = view.findViewById(R.id.EditEdtEmail);
                String newEmail = EditEdtEmail.getText().toString();


                updateRegister(midString,newUsername,newPassword,newName,newID,newAddress,provinceString,amphurString,subDistriceString,villagString,newPhone,newEmail);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //updateข้อมูลประเภทพืชเพาะปลูก
    private void updateRegister(String midString, String newUsername ,String newPassword, String newName, String newID, String newAddress,String provinceString,String amphurString,String subDistriceString,String villagString, String newPhone, String newEmail){
    //private void updateRegister(String midString,String newName){

        Myconstant myconstant = new Myconstant();

        try {
            EditRegister editRegister = new EditRegister(getActivity());
             editRegister.execute(midString,newUsername,newPassword,newName,newID,newAddress,provinceString,amphurString,subDistriceString,villagString,newPhone,newEmail,
            //editRegister.execute(midString,newName,

                    myconstant.getUrlEditRegister());

            if (Boolean.parseBoolean(editRegister.get())) {

            }else {
                Toast.makeText(getActivity(),"แก้ไขข้อมูลสำเร็จ",
                        Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ลบรายการประเภทพืชเพาะปลูก
    private void editDeleteRegister(String midString){

        Myconstant myconstant = new Myconstant();
        try {
            DeleteFammer deleteFammer = new DeleteFammer(getActivity());
            deleteFammer.execute(midString, myconstant.getUrlDeleteFammer());

            if (Boolean.parseBoolean(deleteFammer.get())) {
                createLisView();
            } else {
                Toast.makeText(getActivity(),"ลบรายการพืชเพาะปลูก",Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemAddUser) {
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contentAdminFragment, new MemberAddFragment())
                            .addToBackStack(null)
                            .commit();
                    return false;
                }
            });

            // uploadValueToSever();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.manu_uploaduser, menu);

    }


    private void CreateToolbal() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarRegister);
        ((AdminActivity)getActivity()).setSupportActionBar(toolbar);

        ((AdminActivity)getActivity()).getSupportActionBar().setTitle("ข้อมูลสมาชิก");

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
        View view = inflater.inflate(R.layout.frm_view_register,container, false);
        return view;
    }
}

