package th.in.nattawut.plancrop.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import th.in.nattawut.plancrop.MemberActivity;
import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.APIUtils;
import th.in.nattawut.plancrop.utility.DeleteOrder;
import th.in.nattawut.plancrop.utility.EditOrder;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.Myconstant;
import th.in.nattawut.plancrop.utility.Order;
import th.in.nattawut.plancrop.utility.OrderAdapter;
import th.in.nattawut.plancrop.utility.OrderService;

public class OrderViewFragment extends Fragment {


    ListView listView;
    OrderService orderService;
    List<Order> list = new ArrayList<Order>();
    SwipeRefreshLayout mSwipeRefreshLayout;

    Spinner spin,selectMap;
    ArrayList<HashMap<String, String>> MyArrList,mapArrayList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map,m,selectsite;

    View view;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        CreateToolbal();

        showMid();

        swiRefreshLayou();

    }

    private void selectcroptype1() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        selectMap = view.findViewById(R.id.EditOrder);
        try {
            Myconstant myconstant = new Myconstant();
            GetData getData = new GetData(getActivity());
            getData.execute(myconstant.getUrlCrop());

            String jsonString = getData.get();
            Log.d("5/Jan CropType", "JSON ==>" + jsonString);
            JSONArray data = new JSONArray(jsonString);

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                m = new HashMap<String, String>();
                m.put("cid", c.getString("cid"));
                m.put("crop", c.getString("crop"));
                mapArrayList.add(m);
            }

            final SimpleAdapter s;
            s = new SimpleAdapter(getActivity(), mapArrayList, R.layout.spinner_plancrop,
                    new String[]{"cid", "crop"}, new int[]{R.id.textPlanCidSpinner, R.id.textPlanCropSpinner});
            selectMap.setAdapter(s);
            selectMap.setSelection(mapArrayList.indexOf(map));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void swiRefreshLayou() {
        mSwipeRefreshLayout = getView().findViewById(R.id.swiRefreshLayoutOrder);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showMid();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void showMid() {
        listView = getView().findViewById(R.id.listViewOrder);
        orderService = APIUtils.getService();
        if (getActivity().getIntent().getExtras() != null) {
            String mid = getActivity().getIntent().getExtras().getString("mid");
            selectOrder(mid);

        }
    }

    public void selectOrder(String mid) {
        Call<List<Order>> call = orderService.getOrder(mid);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(final Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    listView.setAdapter(new OrderAdapter(getActivity(), R.layout.frm_order_view, list));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            deleteorEditOrder(list.get(position).getNo(), list.get(position).getSdate()
                                    , list.get(position).getEdate(), String.valueOf(list.get(position).getQty()),list.get(position).getCid(), list.get(position).getCrop());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.e("Error", t.getMessage());

            }
        });
    }

    //alertให้เลือกลบหรือแก้ไข
    private void deleteorEditOrder(final String no, final String SData, final String EData, final String Qty, final String cid, final String crop) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setIcon(R.drawable.notepad);
        builder.setTitle("ลบข้อมูล หรือ ดูรายละเอียด");
        builder.setMessage("ต้องการแก้ไขกดปุ่ม ดูรายละเอียด");
        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("ลบข้อมูล", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editDeleteOrder(no);
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("ดูรายละเอียด", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editOrder(no, SData, EData, Qty,cid,crop);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //alertให้เลือกจะลบรายการหรือไม่
    private void editDeleteOrder(final String no) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                deleteOrder(no);
                dialog.dismiss();
            }
        });
        builder.show();

    }

    //ลบรายการประเภทพืชเพาะปลูก
    private void deleteOrder(String no) {

        Myconstant myconstant = new Myconstant();
        try {
            DeleteOrder deleteOrder = new DeleteOrder(getActivity());
            deleteOrder.execute(no, myconstant.getUrlDeleteOrder());

            if (Boolean.parseBoolean(deleteOrder.get())) {
                //createListView();
            } else {
                Toast.makeText(getActivity(), "ลบการแจ้งความต้องการ", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editOrder(final String no, final String SData, final String EData, final String Qty, final String cid, final String crop) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle("กำหนดชื่อใหม่");

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.edit_order, null);


        TextView texPlanMid = view.findViewById(R.id.EditTexPlanLogin);
        String strTextShowmid = getActivity().getIntent().getExtras().getString("mid");
        texPlanMid.setText(strTextShowmid);

        TextView texPlanName = view.findViewById(R.id.EditTextMidPlanFarmer);
        String strTextShowName = getActivity().getIntent().getExtras().getString("name");
        texPlanName.setText(strTextShowName);

        TextView txtSData1 = view.findViewById(R.id.txtSData);
        String newSData = getActivity().getIntent().getExtras().getString("sdate", SData);
        txtSData1.setText(newSData);

        final TextView txtEData1 = view.findViewById(R.id.txtEData);
        String newEData = getActivity().getIntent().getExtras().getString("edate", EData);
        txtEData1.setText(newEData);

        EditText edtEditQty = view.findViewById(R.id.edtEditQty);
        String newQty = getActivity().getIntent().getExtras().getString("qty", Qty);
        edtEditQty.setText(newQty);

        map = new HashMap<String, String>();
        map.put("crop",crop);
        map.put("cid",cid);
        selectcroptype1();

        final TextView txtSData = view.findViewById(R.id.txtSData);
        Button selctDataSData = view.findViewById(R.id.btnSData);
        selctDataSData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dataPickerDialog = new DatePickerDialog(getActivity(),/*AlertDialog.THEME_DEVICE_DEFAULT_DARK,*///Theme
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int y, int m, int d) {
                                txtSData.setText(y + "/" + (m + 1) + "/" + d);

                            }
                        }, day, month, year);
                dataPickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());//วันที่ปัจจุบัน
                dataPickerDialog.show();
            }
        });

        final TextView txtEData = view.findViewById(R.id.txtEData);
        Button selctDataEData = view.findViewById(R.id.btnEData);
        selctDataEData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dataPickerDialog = new DatePickerDialog(getActivity(),/*AlertDialog.THEME_DEVICE_DEFAULT_DARK,*///Theme
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int y, int m, int d) {
                                txtEData.setText(y + "/" + (m + 1) + "/" + d);

                            }
                        }, day, month, year);
                dataPickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());//วันที่ปัจจุบัน
                dataPickerDialog.show();
            }
        });


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
                TextView editCrop = view.findViewById(R.id.textPlanCidSpinner);
                String newEditCrop = editCrop.getText().toString();

                TextView editSData = view.findViewById(R.id.txtSData);
                String newSData = editSData.getText().toString();

                TextView editEData = view.findViewById(R.id.txtEData);
                String newEData = editEData.getText().toString();

                EditText edtQty = view.findViewById(R.id.edtEditQty);
                String newQty = edtQty.getText().toString();

                updateOrder(no, newSData, newEData, newEditCrop, newQty);
                dialog.dismiss();


            }
        });
        builder.show();
    }

    private void updateOrder(String no, String newSData, String newEData, String newEditCrop, String newQty) {
        Myconstant myconstant = new Myconstant();

        try {
            EditOrder editOrder = new EditOrder(getActivity());
            editOrder.execute(no, newEData, newSData, newEditCrop, newQty,
                    myconstant.getUrlEditOrder());

            String jsonString = editOrder.get();
            Log.d("5/Jan updateorder", "JSON ==>" + jsonString);

            if (Boolean.parseBoolean(editOrder.get())) {
            } else {
                Toast.makeText(getActivity(), "แก้ไขข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemlinkUrl) {
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contentMemberFragment, new OrderFragment())
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

        inflater.inflate(R.menu.manu_register, menu);

    }

    private void CreateToolbal() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarOrder);
        ((MemberActivity) getActivity()).setSupportActionBar(toolbar);
        ((MemberActivity) getActivity()).getSupportActionBar().setTitle("ข้อมูลการแจ้งความต้องการ");
        //((MainActivity)getActivity()).getSupportActionBar().setSubtitle("ddbdbvd");

        ((MemberActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MemberActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
        View view = inflater.inflate(R.layout.frm_view_order, container, false);
        return view;
    }
}
