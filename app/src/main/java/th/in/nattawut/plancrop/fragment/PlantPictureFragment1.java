package th.in.nattawut.plancrop.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.AddPlantPictuteUpload;
import th.in.nattawut.plancrop.utility.ApiClient;
import th.in.nattawut.plancrop.utility.GetDataWhereOneColumn;
import th.in.nattawut.plancrop.utility.MyAlert;
import th.in.nattawut.plancrop.utility.Myconstant;
import th.in.nattawut.plancrop.utility.OrderService;
import th.in.nattawut.plancrop.utility.ServerResponse;

import static android.app.Activity.RESULT_OK;

public class PlantPictureFragment1 extends Fragment {

    private static final int PERMISSION_WRITE_STORAGE = 10;
    Button btnUpload;
    private Uri uri;
    private ImageView photoImageView;
    OrderService orderService;
    Bitmap bitmap;
    ProgressDialog progressDialog;
    String mediaPath, mediaPath1;
    TextView str1;

    //Button selctDate;
    ImageView selctDate;
    TextView date;
    DatePickerDialog dataPickerDialog;
    Calendar calendar;

    TextView picno;
    String ID;



    private String idRecord;

    private String NoString,DatepictureString,DescriptionString,result;

    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Uploading...");


        //เช็คPermission ถ้าสูงกว่า6
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, PERMISSION_WRITE_STORAGE);
        }

        //Gallery Controller
        galleryController();

        selectPlant();

        DataPickerDialog();

        photoImageView = getView().findViewById(R.id.imagePhoto);
        str1 = getView().findViewById(R.id.filename1);
        btnUpload = getView().findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadValueToServer();
            }
        });

    }

    private void uploadValueToServer() {
        TextView textPlantNo = getView().findViewById(R.id.textPlantNo);
        TextView textmyDate = getView().findViewById(R.id.textViewDatePicture);
        EditText edtDescription = getView().findViewById(R.id.edtDescription);

        NoString = textPlantNo.getText().toString().trim();
        DatepictureString = textmyDate.getText().toString().trim();
        DescriptionString = edtDescription.getText().toString().trim();


        if (NoString.isEmpty() ||DatepictureString.isEmpty() || DescriptionString.isEmpty()) {
            MyAlert myAlert = new MyAlert(getActivity());
            myAlert.onrmaIDialog("สวัสดี", "กรุณากรอกข้อมูล");
        } else {
            try {
                Myconstant myconstant = new Myconstant();
                AddPlantPictuteUpload addPlantPictuteUpload = new AddPlantPictuteUpload(getActivity());
                addPlantPictuteUpload.execute(NoString,DatepictureString,DescriptionString,
                        myconstant.getUrlAddPlantPicture());

                result = addPlantPictuteUpload.get();

//                picno = getView().findViewById(R.id.PicNo2);
//                picno.setText(result);



//                int i = Integer.parseInt(result); //เอาค่า id มาเป็นตัวเลข
//                i = i + 1; //เพิ่มค่าทีละ1
//                result = Integer.toString(i);


                Log.d("PlantPicture", "result ==>" + result);
                if (Boolean.parseBoolean(result)) {
                    getActivity().getSupportFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getActivity(), "เพิ่มข้อมูลเรียบร้อย",Toast.LENGTH_LONG).show();
                }
                //อัพโหลดรูป
                uploadFile();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void selectPlant() {
        if (android.os.Build.VERSION.SDK_INT > 9) { //setup policy เเพื่อมือถือที่มีประปฏิบัติการสูงกว่านีจะไม่สามารถconnectกับโปรโตรคอลได้
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final Spinner spin = getView().findViewById(R.id.spPlantNo);
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

    private void DataPickerDialog() {
        date = getActivity().findViewById(R.id.textViewDatePicture);
        selctDate = getActivity().findViewById(R.id.imageViewDatePicture);
        selctDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar  = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                dataPickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int y, int m, int d) {
                                //date.setText(d + "/" + (m + 1) + "/" + y);
                                date.setText(y + "/" + (m + 1) + "/" + d);
                            }
                        },day,month,year);
                dataPickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dataPickerDialog.show();
            }
        });
    }

    private void uploadFile() {
        if (mediaPath == null || mediaPath.equals("")) {
            Toast.makeText(getActivity(),"กรุณาเลือกรูปภาพ",Toast.LENGTH_LONG).show();
            return;
        }
        showDialog();
        String name = picno.getText().toString();

        File file = new File(mediaPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"),result);
        MultipartBody.Part picno = MultipartBody.Part.createFormData("picno", result,filename);


        OrderService orderService = ApiClient.getApiClient().create(OrderService.class);
        Call<ServerResponse> call = orderService.uploadFile1(fileToUpload,picno);
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
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });

    }


    protected void showDialog() {
        if (!progressDialog.isShowing()) progressDialog.show();
    }

    protected void hidepDialog() {
        if (progressDialog.isShowing())progressDialog.dismiss();
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
        ImageView imageView = getView().findViewById(R.id.imvGallery);
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
        View view = inflater.inflate(R.layout.frm_plantpicture, container, false);
        return view;

    }
}