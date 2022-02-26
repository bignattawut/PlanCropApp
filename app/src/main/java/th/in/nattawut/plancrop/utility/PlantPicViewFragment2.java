package th.in.nattawut.plancrop.utility;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tuann.floatingactionbuttonexpandable.FloatingActionButtonExpandable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.fragment.PlantPicreFragment3;

import static android.app.Activity.RESULT_OK;

public class PlantPicViewFragment2 extends Fragment {
    ListView listView;
    OrderService orderService;
    List<PlantActivity> list = new ArrayList<PlantActivity>();
    private String idRecord;

    private static final int PERMISSION_WRITE_STORAGE = 10;
    Button btnUpload;
    private Uri uri;
    private ImageView photoImageView;
    Bitmap bitmap;
    //private String mediaPath ;
    TextView str1;
    //String mediaPath = new String("/storage/emulated/0/DCIM/");
    String mediaPath = new String("image/jpg");
    //String mediaPath = new String("/storage/emulated/0/Download/FB_IMG_1566371876498.jpg");
    //String mediaPath = new String("/storage/emulated/0/");

    SwipeRefreshLayout mSwipeRefreshLayout;


    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    View view;
    Myconstant myconstant = new Myconstant();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //เช็คPermission ถ้าสูงกว่า6
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, PERMISSION_WRITE_STORAGE);
        }

        //showMid();
        cropController();

        selectPlant();

        planPicViewController();

        swiRefreshLayou();

    }

    private void swiRefreshLayou() {
        mSwipeRefreshLayout = getView().findViewById(R.id.swiRefreshLayouPlantPic);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showMid();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void planPicViewController() {
        FloatingActionButtonExpandable floatingActionButton = getView().findViewById(R.id.floatingActionButtonViewPlantPic);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new PlantPicreFragment3())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void cropController() {
        Button button = getView().findViewById(R.id.selete1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMid();

            }
        });
    }

    private void selectPlant() {
        if (android.os.Build.VERSION.SDK_INT > 11) { //setup policy เเพื่อมือถือที่มีประปฏิบัติการสูงกว่านีจะไม่สามารถconnectกับโปรโตรคอลได้
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final Spinner spin = getView().findViewById(R.id.PlantNo);
        try {

//            Myconstant myconstant = new Myconstant();
//            GetData getData = new GetData(getActivity());
//            getData.execute(myconstant.getUrlPlant());

            Myconstant myconstant = new Myconstant();
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(myconstant.getNameFileSharePreference(), Context.MODE_PRIVATE);
            idRecord = sharedPreferences.getString("mid", "");

            GetDataWhereOneColumn getDataWhereOneColumn = new GetDataWhereOneColumn(getActivity());
            getDataWhereOneColumn.execute("mid", idRecord, myconstant.getUrlPlant());

            String jsonString = getDataWhereOneColumn.get();
            Log.d("5/Jan spPlantNo", "JSON ==>" + jsonString);
            JSONArray data = new JSONArray(jsonString);

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("no", c.getString("no"));
                map.put("crop", c.getString("crop"));
                map.put("sno", c.getString("sno"));
                MyArrList.add(map);
            }
            SimpleAdapter sAdap;sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.spinner_plant,
                    new String[]{"no", "crop","sno"}, new int[]{R.id.textPlantNo, R.id.textPlantCrop,R.id.textPlantSno});
            spin.setAdapter(sAdap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showMid() {
        listView = getView().findViewById(R.id.listViewPlantPicture);
        orderService = APIUtils.getService();
        TextView textPlantNo = getView().findViewById(R.id.textPlantNo);
        String no = textPlantNo.getText().toString().trim();

        plantPicController(no);
    }

    private void plantPicController(String no){
        Call<List<PlantActivity>> call = orderService.getPlantActivity(no);
        call.enqueue(new Callback<List<PlantActivity>>() {
            @Override
            public void onResponse(Call<List<PlantActivity>> call, Response<List<PlantActivity>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    listView.setAdapter(new PlantPicAdpter2(getActivity(),R.layout.frm_plantpicture_view,list));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            deleteorEditPlantPic(list.get(position).getPicno(),list.get(position).getPdate(),list.get(position).getDescription());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<PlantActivity>> call, Throwable t) {

            }
        });
    }

    //alertให้เลือกลบหรือแก้ไข
    private void deleteorEditPlantPic(final String picnoStringArrayList, final String pdateStringArrayList, final String descriptionStringArrayList) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_LIGHT);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_action_camera);
        builder.setTitle("ข้อมูลกิจกกรมเพาะปลูก");
        builder.setMessage("กรุณาเลือก ลบข้อมูล หรือ ดูข้อมูล ?");
        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("ลบข้อมูล" ,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deletePlanPic(picnoStringArrayList);
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("ดูรายละเอียด", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editPlantPic(picnoStringArrayList,pdateStringArrayList,descriptionStringArrayList);
                dialog.dismiss();
            }
        });
        builder.show();

    }

    private void editPlantPic(final String picnoStringArrayList, String pdateStringArrayList, String descriptionStringArrayList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        //กำหนดหัวเเรื้อง
        builder.setTitle("ข้อมูลกิจกกรมเพาะปลูก");
        //กำหนดเนื้อหา

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.edit_plantpic, null);


        galleryController();

        dateController();


        uploadFile(picnoStringArrayList);

//        Log.d("pic","result == > " + picnoStringArrayList);
//        Log.d("str1","result == > " + str1);

        photoImageView = view.findViewById(R.id.edit_imagePhoto);
        str1 = view.findViewById(R.id.filename1);
        Picasso.get()
                .load("https://www.plancropapp.online/android/php/picture/activity/"+picnoStringArrayList+".jpg")
                //.load("http://10.200.1.126/android/php/picture/activity/"+picnoStringArrayList+".jpg")
                .resize(150, 150)
                .into(photoImageView);


        TextView textdescription = view.findViewById(R.id.edit_edtDescription);
        String newdescription = getActivity().getIntent().getExtras().getString("description",descriptionStringArrayList);
        textdescription.setText(newdescription);

        TextView textPDate = view.findViewById(R.id.edit_textViewDatePicture);
        String newPDate = getActivity().getIntent().getExtras().getString("pdate",pdateStringArrayList);
        textPDate.setText(newPDate);

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
                TextView edit_textViewDatePicture = view.findViewById(R.id.edit_textViewDatePicture);
                TextView edit_edtDescription = view.findViewById(R.id.edit_edtDescription);

                String neweDatePicture = edit_textViewDatePicture.getText().toString();
                String newDescription = edit_edtDescription.getText().toString();
                updatePlantPic(picnoStringArrayList,neweDatePicture,newDescription);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void updatePlantPic(String picnoStringArrayList, String neweDatePicture, String newDescription) {
        Myconstant myconstant = new Myconstant();

        try {
            EditPlantPic editPlantPic = new EditPlantPic(getActivity());
            editPlantPic.execute(picnoStringArrayList,
                    neweDatePicture,
                    newDescription,
                    myconstant.getUrlEditPlantPic());

            Log.d("are", "editPlantPic ===>" + newDescription+picnoStringArrayList+neweDatePicture);

            if (Boolean.parseBoolean(editPlantPic.get())) {

            } else {
                Toast.makeText(getActivity(), "แก้ไขข้อมูลสำเร็จ",
                        Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //alertให้เลือกจะลบรายการหรือไม่
    private void deletePlanPic(final String picnoStringArrayList){
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
                editDeletePlantPic(picnoStringArrayList);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //ลบรายการ
    private void editDeletePlantPic(String tidString){

        Myconstant myconstant = new Myconstant();
        try {
            DeletePlantPic deletePlantPic = new DeletePlantPic(getActivity());
            deletePlantPic.execute(tidString, myconstant.getUrlDeletePlantPic());

            if (Boolean.parseBoolean(deletePlantPic.get())) {

            } else {
                Toast.makeText(getActivity(),"ลบข้อมูลสำเร็จ",Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //เลือกวันที่
    private void dateController() {
        final TextView data = view.findViewById(R.id.edit_textViewDatePicture);
        ImageView selctData = view.findViewById(R.id.edit_imageViewDatePicture);
        selctData.setOnClickListener(new View.OnClickListener() {
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
                                data.setText(y + "/" + (m + 1) + "/" + d);
                            }
                        }, day, month, year);
                dataPickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());//วันที่ปัจจุบัน

                dataPickerDialog.show();
            }
        });

    }

    //แก้ไขรูป
    public void uploadFile(String pinoString) {
        Log.d("mediaPath","result == > " + mediaPath);
        File file = new File(mediaPath);
        //File file = new File(android.os.Environment.getExternalStorageDirectory()+"/emulated/0/Pictures");
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"),pinoString);


        OrderService orderService = ApiClient.getApiClient().create(OrderService.class);
        Call<ServerResponse> call = orderService.uploadFile22(fileToUpload,filename);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.isSuccess()) {
                        Toast.makeText(getActivity(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "ภาพปัญหาการอัปโหลด", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    // Log.v("Response", serverResponse.toString());
                }
                //progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

            // Get the Image from data
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mediaPath = cursor.getString(columnIndex);
            str1.setText(mediaPath);
            // Set the Image in ImageView for Previewing the Media
            photoImageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
            cursor.close();
        }
    }


    private void galleryController() {
        ImageView imageView = view.findViewById(R.id.edit_imvGallery);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_view_plantpicture, container, false);
        return view;
    }
}
