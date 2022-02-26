package th.in.nattawut.plancrop.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import th.in.nattawut.plancrop.HomeActivity;
import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.APIUtils;
import th.in.nattawut.plancrop.utility.LoginResponse;
import th.in.nattawut.plancrop.utility.OrderService;
import th.in.nattawut.plancrop.utility.SaveSharedPreference;
import th.in.nattawut.plancrop.utility.User;

public class MainFragment1 extends Fragment {

    EditText editTextUserId;
    EditText editTextPassword;
    OrderService orderService;
    private String typeUser;
    private int typeDataInt;
    Button button;
    LinearLayout loginForm;

    List<User> list = new ArrayList<User>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        editTextUserId = getView().findViewById(R.id.edtusername);
        editTextPassword = getView().findViewById(R.id.edtpassword);
        loginForm = getView().findViewById(R.id.loginForm);
        orderService = APIUtils.getService();
        button = getView().findViewById(R.id.btnlogin);

        if (SaveSharedPreference.getLoggedStatus(getActivity())) {
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
        } else {
            loginForm.setVisibility(View.VISIBLE);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loginControkker(editTextUserId.getText().toString(), editTextPassword.getText().toString());


                String userString = editTextUserId.getText().toString().trim();
                String passwordString = editTextPassword.getText().toString().trim();

                if (userString.isEmpty()) {
                    editTextUserId.setError("ชื้อผู้ใช้งานไม่ถูกต้อง");
                    editTextUserId.requestFocus();
                    return;
                }
                if (passwordString.isEmpty()) {
                    editTextPassword.setError("รหัสผ่านไม่ถูกต้อง");
                    editTextPassword.requestFocus();
                    return;
                }
                if (passwordString.length() < 2) {
                    editTextPassword.setError("รหัสผ่านควรมีความยาวอย่างน้อย 2 ตัว");
                    editTextPassword.requestFocus();
                    return;
                }
                loginControkker(userString, passwordString);

            }

        });


    }

    /**
     * Login API call
     * TODO: Please modify according to your need it is just an example
     *
     * @param username
     * @param password
     */

    private void loginControkker(String username, String password) {

        Call<List<LoginResponse>> call = orderService.getuserLogin(username, password);
        call.enqueue(new Callback<List<LoginResponse>>() {
            @Override
            public void onResponse(Call<List<LoginResponse>> call, Response<List<LoginResponse>> response) {
                if (response.isSuccessful()) {
                    SaveSharedPreference.setLoggedIn(getActivity(), true);
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else {
                    Toast.makeText(getActivity(), "ยินดีต้อนรับ", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<LoginResponse>> call, Throwable t) {
                Log.e("TAG", "========ONfY " + t.toString());
                t.printStackTrace();
            }
        });
    }
    ////

//        Button button = getView().findViewById(R.id.btnlogin);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String userString = editTextUserId.getText().toString().trim();
//                String passwordString = editTextPassword.getText().toString().trim();
//
//                if (userString.isEmpty()) {
//                    editTextUserId.setError("ชื้อผู้ใช้งานไม่ถูกต้อง");
//                    editTextUserId.requestFocus();
//                    return;
//                }
//                if (passwordString.isEmpty()) {
//                    editTextPassword.setError("รหัสผ่านไม่ถูกต้อง");
//                    editTextPassword.requestFocus();
//                    return;
//                }
//                if (passwordString.length() < 2) {
//                    editTextPassword.setError("รหัสผ่านควรมีความยาวอย่างน้อย 2 ตัว");
//                    editTextPassword.requestFocus();
//                    return;
//                }
//                Call<LoginResponse> call = orderService.getuserLogin(userString, passwordString);
//                call.enqueue(new Callback<LoginResponse>() {
//                    @Override
//                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                        LoginResponse loginResponse = response.body();
//                        if (!loginResponse.isError()) {
//                            Toast.makeText(getActivity(), loginResponse.getMessage(), Toast.LENGTH_LONG).show();
//                        } else {
//                            Toast.makeText(getActivity(), loginResponse.getMessage(), Toast.LENGTH_LONG).show();
//
//                        }
////                        if (response.isSuccessful()) {
////                            if (response.body() != null) {
////                                Log.i("onSucces",response.body().toString());
////
////                                String json = response.body().toString();
////                                login(json);
////                            }
////                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<LoginResponse> call, Throwable t) {
//                        Log.i("onEmptyResponse", "Returned empty response");
//
//                    }
//                });
//
//
//            }
//        });


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_login1, container, false);
        return view;
    }
}
