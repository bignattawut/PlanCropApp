package th.in.nattawut.plancrop.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import th.in.nattawut.plancrop.AdminActivity;
import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.CropTypeViewAapter;
import th.in.nattawut.plancrop.utility.DeleteCropType;
import th.in.nattawut.plancrop.utility.EditCropType;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.Myconstant;

public class CropTypeViewFragment extends Fragment {

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView listView;
    RecyclerView recyclerView;

    private int[] imv;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Create ListView
        createListView();

        //Create Toolbal
        CreateToolbal();

        //Swipe Refresh Layout
        swipeRefreshLayout();

    }

    private void swipeRefreshLayout() {
        mSwipeRefreshLayout = getView().findViewById(R.id.swiRefreshLayouCropType);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Create ListView
                        createListView();

                    }
                },2);
            }
        });
    }

    private void createListView() {
        listView = getView().findViewById(R.id.listViewCropType);
        //listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        Myconstant myconstant = new Myconstant();
        String[] columnStrings = myconstant.getColumnCropTypeString();

        try{
            GetData getData = new GetData(getActivity());
            getData.execute(myconstant.getUrlselectcroptype());

            String jsonString = getData.get();
            Log.d("4กุมภาพันธ์","Json CropType ==> " + jsonString);

            JSONArray jsonArray = new JSONArray(jsonString);

            final String[] tidString = new String[jsonArray.length()];
            final String[] cropTypeString = new String[jsonArray.length()];

            for (int i=0; i<jsonArray.length(); i+=1){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                tidString[i] = jsonObject.getString(columnStrings[0]);
                cropTypeString[i] = jsonObject.getString(columnStrings[1]);
            }


            CropTypeViewAapter cropTypeViewAapter = new CropTypeViewAapter(getActivity(),tidString,cropTypeString/*,android.R.layout.simple_list_item_checked*/);
            listView.setAdapter(cropTypeViewAapter);

            //edit
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    deleteorEditCropType(tidString[position],cropTypeString[position]);

                }
            });

            mSwipeRefreshLayout.setRefreshing(false);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //alertให้เลือกลบหรือแก้ไข
    private void deleteorEditCropType(final String tidString, final String cropTypeString ) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()/*,AlertDialog.THEME_HOLO_LIGHT*/);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_spa_black_24dp);
        builder.setTitle("ข้อมูลประเภทพืช");
        builder.setMessage("กรุณาเลือก ลบ หรือ ดูข้อมูล ?");
        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("ลบข้อมูล" ,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteCropType(tidString);
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("ดูรายละเอียด", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editCropType(tidString,cropTypeString);
                dialog.dismiss();
            }
        });
        builder.show();

        /*ImageView imageView = getView().findViewById(R.id.DEl);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCropType(tidString);
            }
        });
        ImageView Edit = getView().findViewById(R.id.Edit);
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCropType(tidString,cropTypeString);
            }
        });*/
    }

    //alertให้เลือกจะลบรายการหรือไม่
    private void deleteCropType(final String tidString){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_LIGHT);
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
                editDeleteCropType(tidString);
                dialog.dismiss();
            }
        });
        builder.show();

    }

    //แก้ไขประเทพืชเพาะปลูก
    private void editCropType(final String tidString, final String cropTypeString){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        //กำหนดหัวเเรื้อง
        builder.setTitle("ลงทะเบียนประเภทพืชเพาะปลูก");
        //กำหนดเนื้อหา
        ///builder.setMessage("ประเภทพืช ==> " + cropTypeString);

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.edit_croptype, null);

        TextView edtEditCropType = view.findViewById(R.id.edtEditCropType);
        TextView texPlanMid = view.findViewById(R.id.editCropTypeId);

        String strTextShow = getActivity().getIntent().getExtras().getString("croptype",cropTypeString);
        edtEditCropType.setText(strTextShow);
        String strTextShowmid = getActivity().getIntent().getExtras().getString("tid",tidString);
        texPlanMid.setText(strTextShowmid);
        builder.setView(view);

        //กำหนดปุ่ม
        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("แก้ไข", new DialogInterface.OnClickListener() {

            //กำหนด Event ให้กับปุ่ม
            @Override
            public void onClick(DialogInterface dialog, int which) {
               EditText editText = view.findViewById(R.id.edtEditCropType);
                String newCropType = editText.getText().toString();
                if (newCropType.isEmpty()) {
                    //newCropType = "0";
                }
                updateCropType(tidString,newCropType);

                dialog.dismiss();
            }
        });
        builder.show();
    }

    //updateข้อมูลประเภทพืชเพาะปลูก
    private void updateCropType(String tidString, String newCropType){

        Myconstant myconstant = new Myconstant();

        try {
            EditCropType editCropType = new EditCropType(getActivity());
            editCropType.execute(tidString,newCropType,
                    myconstant.getUrlEditCropType());




            if (Boolean.parseBoolean(editCropType.get())) {
                createListView();
            }else {
                Toast.makeText(getActivity(),"แก้ไขข้อมูลสำเร็จ",
                        Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ลบรายการประเภทพืชเพาะปลูก
    private void editDeleteCropType(String tidString){

        Myconstant myconstant = new Myconstant();
        try {
            DeleteCropType deleteCropType = new DeleteCropType(getActivity());
            deleteCropType.execute(tidString, myconstant.getUrlDeleteCropType());

            if (Boolean.parseBoolean(deleteCropType.get())) {
                createListView();
            } else {
                Toast.makeText(getActivity(),"ลบประเภทพืช",Toast.LENGTH_SHORT).show();
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
                            .replace(R.id.contentAdminFragment, new CropTypeFragment())
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
        Toolbar toolbar = getView().findViewById(R.id.toolbarCropType);
        ((AdminActivity)getActivity()).setSupportActionBar(toolbar);

        ((AdminActivity)getActivity()).getSupportActionBar().setTitle("ข้อมูลประเภทพืช");
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
        View view = inflater.inflate(R.layout.frm_view_croptype,container, false);
        return view;
    }
}
