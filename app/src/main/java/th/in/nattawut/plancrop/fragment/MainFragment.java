package th.in.nattawut.plancrop.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import th.in.nattawut.plancrop.AdminActivity;
import th.in.nattawut.plancrop.HomeActivity;
import th.in.nattawut.plancrop.MemberActivity;
import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.AddlLogin;
import th.in.nattawut.plancrop.utility.MyAlert;
import th.in.nattawut.plancrop.utility.Myconstant;

public class MainFragment extends Fragment {


    private String typeUser;
    private int typeDataInt;
    private EditText username, password;
    LinearLayout loginForm;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //btnBlack
        btnBlack();
        // Register Controller
        registerController();

        //Login Controkker
        loginControkker();

       // swiRefreshLayou();


//        loginForm = getView().findViewById(R.id.loginForm);
//        if (SaveSharedPreference.getLoggedStatus(getActivity())) {
//            Intent intent = new Intent(getActivity(), HomeActivity.class);
//            startActivity(intent);
//        } else {
//            loginForm.setVisibility(View.VISIBLE);
//        }


    }// onActivityCreat

//    private void swiRefreshLayou() {
//        mSwipeRefreshLayout = getView().findViewById(R.id.swiRefreshLayoulogin);
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                loginControkker();
//                mSwipeRefreshLayout.setRefreshing(false);
//            }
//        });
//    }

    private void loginControkker() {
        Button button = getView().findViewById(R.id.btnlogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = getView().findViewById(R.id.edtusername);
                EditText password = getView().findViewById(R.id.edtpassword);

                String userString = username.getText().toString().trim();
                String passwordString = password.getText().toString().trim();
                MyAlert myAlert = new MyAlert(getActivity());


                if (userString.isEmpty() || passwordString.isEmpty()) {
                    myAlert.onrmaIDialog("สวัสดี", "กรุณากรอกชื่อผู้ใช้หรือรหัสผ่าน");
                } else {
                    try {
                        Myconstant myconstant = new Myconstant();
                        AddlLogin addlLogin = new AddlLogin(getActivity());
                        addlLogin.execute(userString,myconstant.getUrlGetUser());
                        String jsonString = addlLogin.get();
                        Log.d("1/may", "JSON ==>" + jsonString);

                        if (jsonString.equals("null")) {
                            myAlert.onrmaIDialog("รหัสผ่าน", "รหัสผ่านไม่ถูกต้อง");
                        } else {
                            JSONArray jsonArray = new JSONArray(jsonString);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);

                            if (passwordString.equals(jsonObject.getString("pwd"))) {
                                Toast.makeText(getActivity(), "Welcome " + jsonObject.getString("name"), Toast.LENGTH_SHORT).show();

                                String nameuser = null, miduser = null, vid = null;
                                nameuser = jsonObject.getString("name");
                                miduser = jsonObject.getString("mid");
                                vid = jsonObject.getString("vid");
                                typeUser = jsonObject.getString("type");

                                String typeDataString = jsonObject.getString("type").trim();
                                typeDataInt = Integer.parseInt(typeDataString);

                                //ฝัง MID ในแอพ
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(myconstant.getNameFileSharePreference(), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("mid", miduser);
                                editor.putString("name", nameuser);
                                editor.putString("vid", vid);
                                editor.putString("type", typeUser);
                                editor.commit();

                                switch (typeDataInt) {
                                    case 1:
                                        Intent admin = new Intent(getActivity(), AdminActivity.class);
                                        admin.putExtra("name", nameuser);
                                        admin.putExtra("mid", miduser);
                                        admin.putExtra("vid", vid);
                                        startActivity(admin);
                                        getActivity().finish();//คำสั่งปิดแอป
                                        break;
                                    case 2:
                                        Intent home = new Intent(getActivity(), HomeActivity.class);
                                        home.putExtra("name", nameuser);
                                        home.putExtra("mid", miduser);
                                        home.putExtra("vid", vid);
                                        startActivity(home);
                                        getActivity().finish();//คำสั่งปิดแอป
                                        break;
                                    case 3:
                                        Intent member = new Intent(getActivity(), MemberActivity.class);
                                        member.putExtra("name", nameuser);
                                        member.putExtra("mid", miduser);
                                        member.putExtra("vid", vid);
                                        startActivity(member);
                                        break;
                                }

                            } else {
                                myAlert.onrmaIDialog("รหัสผ่าน", "รหัสผ่านไม่ถูกต้อง");
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }


    private void registerController() {
        Button button = getView().findViewById(R.id.btnregister);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Replace Fragment
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void btnBlack(){
        Button button = getView().findViewById(R.id.btnBlack);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Replace Fragment
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, new HomeFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_login1, container, false);
        return view;
    }
}
//}else {
//        Myconstant myconstant = new Myconstant();
//        Boolean b = true;
//        String truePass = null, nameuser = null, miduser = null;
//        MyAlert myAlert = new MyAlert(getActivity());
//
//        try {
//        GetData getData = new GetData(getActivity());
//        getData.execute(myconstant.getUrlGetUser());
//
//        String jsonString = getData.get();
//        Log.d("1/Jan", "JSON ==>" + jsonString);
//
//        JSONArray jsonArray = new JSONArray(jsonString);
//        for (int i = 0; i < jsonArray.length(); i += 1) {
//        JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//        if (userString.equals(jsonObject.getString("UserID"))) {
//        b = false;
//        truePass = jsonObject.getString("PWD");
//        nameuser = jsonObject.getString("Name");
//        miduser = jsonObject.getString("MID");
//        }
//        }
//        if (b) {
//        myAlert.onrmaIDialog("กรุณากรอกข้อมูล", "ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง");
//        } else if (passwordString.equals(truePass)) {
//        Toast.makeText(getActivity(), "ยินดีต้อนรับ" + nameuser, Toast.LENGTH_LONG).show();

//        } else {
//        myAlert.onrmaIDialog("รหัสไม่ถูกต้อง", "กรุณากรอกรหัสผ่านใหม่");
//        }