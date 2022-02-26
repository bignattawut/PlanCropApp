package th.in.nattawut.plancrop.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.AddOrder;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.MyAlertCrop;
import th.in.nattawut.plancrop.utility.Myconstant;

public class OrderFragment extends Fragment {

    //Button selctDate;
    //ImageView selctDate;
    TextView txtSData,txtEData;
    DatePickerDialog dataPickerDialog;
    Calendar calendar;
    Button selctDate;

    private String addMid,addCrop,addSData,addEData,addQty,strTextShow,addCropName;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        addCrop();

        sdateController();

        eateController();

        addMember();

        addOrder();

    }

    private void addMember() {
        TextView txtName = getView().findViewById(R.id.txtName);
        TextView txtMid = getView().findViewById(R.id.txtMid);

        strTextShow = getActivity().getIntent().getExtras().getString("name");
        txtName.setText(strTextShow);

        String strTextShowmid = getActivity().getIntent().getExtras().getString("mid");
        txtMid.setText(strTextShowmid);

    }

    private void addCrop() {
        if (android.os.Build.VERSION.SDK_INT > 9) { //setup policy เเพื่อมือถือที่มีประปฏิบัติการสูงกว่านีจะไม่สามารถconnectกับโปรโตรคอลได้
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final Spinner spin = getView().findViewById(R.id.addCrop);
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
            map.put("", "");
            map.put("", "");
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

    private void sdateController() {
        txtSData = getActivity().findViewById(R.id.txtSData);
        selctDate = getActivity().findViewById(R.id.btnSData);
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
                                txtSData.setText(y + "/" + (m + 1) + "/" + d);
                                //DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.UK);

                            }
                        }, year,month,day);
                //dataPickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dataPickerDialog.show();
            }
        });
    }

    private void eateController() {
        txtEData = getActivity().findViewById(R.id.txtEData);
        selctDate = getActivity().findViewById(R.id.btnEData);
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
                                txtEData.setText(y + "/" + (m + 1) + "/" + d);
                                //DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.UK);

                            }
                        }, year,month,day);
                //dataPickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dataPickerDialog.show();
            }
        });
    }

    private void addOrder() {
        Button button = getView().findViewById(R.id.btnAddOrder);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order();
            }
        });
    }

    private void order() {
        TextView txtMid = getView().findViewById(R.id.txtMid);
        TextView textCrop = getView().findViewById(R.id.textPlanCidSpinner);
        TextView textCropName = getView().findViewById(R.id.textPlanCropSpinner);
        TextView txtSData = getView().findViewById(R.id.txtSData);
        TextView txtEData = getView().findViewById(R.id.txtEData);
        EditText edtQty = getView().findViewById(R.id.edtQty);

        addMid = txtMid.getText().toString().trim();
        addCrop = textCrop.getText().toString().trim();
        addCropName = textCropName.getText().toString().trim();
        addSData = txtSData.getText().toString().trim();
        addEData = txtEData.getText().toString().trim();
        addQty = edtQty.getText().toString().trim();


        MyAlertCrop myAlertCrop = new MyAlertCrop(getActivity());
        if (addMid.isEmpty()) {
            myAlertCrop.onrmaIDialog("โปรดกรอก", "กรุณากรอกชื่อสมาชิก");
        } else if (addCrop.isEmpty()) {
            myAlertCrop.onrmaIDialog("กรุณาเลือก", "พืชปลอดสารที่ต้องการ");
        } else if (addSData.isEmpty()) {
            myAlertCrop.onrmaIDialog("กรุณา", "กรอกวันที่เริ่มต้น");
        } else if (addEData.isEmpty()) {
            myAlertCrop.onrmaIDialog("กรุณา", "กรอกวันที่สิ้นสุด");
        } else if (addQty.isEmpty()) {
            myAlertCrop.onrmaIDialog("กรุณา", "กรอกปริมาณความต้องการ");
        }else {
            comfirmUpload();
        }

    }

    private void comfirmUpload() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("ข้อมูลการแจ้งความต้องการพืชปลอดสาร");
        builder.setMessage("ชื่อสมาชิก = " + strTextShow + "\n"
                + "พืชที่ต้องการ = " + addCropName + "\n"
                + "วันที่เริ่มต้น = " + addSData + "\n"
                + "วันที่สินสุด = " + addEData + "\n"
                + "ปริมาณความต้องการ = " + addQty + "กิโลกรัม");
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

    private void uploadToServer() {
        try {
            Myconstant myconstant = new Myconstant();
            AddOrder addOrder = new AddOrder(getActivity());
            addOrder.execute(addMid,addSData,addEData,addCrop,addQty,
                    myconstant.getUrladdorder());

            String result = addOrder.get();
            Log.d("addOrder", "result ==> " + result);
            if (Boolean.parseBoolean(result)) {
                getActivity().getSupportFragmentManager().popBackStack();
            } else {
                Toast.makeText(getActivity(), "เพิ่มข้อมูลเรียบร้อย", Toast.LENGTH_LONG).show();
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMemberFragment, new OrderViewFragment())
                        .addToBackStack(null)
                        .commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_add_requirement, container, false);
        return view;
    }
}
