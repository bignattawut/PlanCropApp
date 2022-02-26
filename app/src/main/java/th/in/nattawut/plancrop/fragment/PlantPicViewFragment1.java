package th.in.nattawut.plancrop.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.DeletePlantPic;
import th.in.nattawut.plancrop.utility.EditPlantPic;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.Myconstant;
import th.in.nattawut.plancrop.utility.OrderService;
import th.in.nattawut.plancrop.utility.PlantPicAdpter1;

import static android.app.Activity.RESULT_OK;

public class PlantPicViewFragment1 extends Fragment {

    ListView listView;

    private static final int PERMISSION_WRITE_STORAGE = 10;
    Button btnUpload;
    private Uri uri;
    private ImageView photoImageView;
    OrderService orderService;
    Bitmap bitmap;
    ProgressDialog progressDialog;
    String mediaPath, mediaPath1;
    TextView str1;


    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    View view;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Create ListView
        createListView();


        //เช็คPermission ถ้าสูงกว่า6
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, PERMISSION_WRITE_STORAGE);
        }
    }



    private void createListView() {
        listView = getView().findViewById(R.id.listViewPlantPicture);
        Myconstant myconstant = new Myconstant();

        try {
            GetData getAllData = new GetData(getActivity());
            getAllData.execute(myconstant.getUrlselectPlantPic());

            String jsonString = getAllData.get();
            Log.d("4กุมภาพันธ์","Json PlantPic ==> " + jsonString);

            JSONArray jsonArray = new JSONArray(jsonString);
            final String[] picnoStringArrayList = new String[jsonArray.length()];
            final String[] pdateStringArrayList = new String[jsonArray.length()];
            final String[] descriptionStringArrayList = new String[jsonArray.length()];

            for (int i=0; i<jsonArray.length(); i+=1){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                picnoStringArrayList[i] = jsonObject.getString("picno");
                pdateStringArrayList[i] = jsonObject.getString("pdate");
                descriptionStringArrayList[i] = jsonObject.getString("description");
            }

            PlantPicAdpter1 plantPicAdpter1 = new PlantPicAdpter1(getActivity(),picnoStringArrayList,pdateStringArrayList,descriptionStringArrayList);
            listView.setAdapter(plantPicAdpter1);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    deleteorEditPlantPic(picnoStringArrayList[position],pdateStringArrayList[position],descriptionStringArrayList[position]);
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //alertให้เลือกลบหรือแก้ไข
    private void deleteorEditPlantPic(final String picnoStringArrayList, final String pdateStringArrayList, final String descriptionStringArrayList) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_LIGHT);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_action_drawerplan);
        builder.setTitle("ลบ หรือ แก้ไข");
        builder.setMessage("กรุณาเลือก ลบ หรือ แก้ไข ?");
        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("ลบ" ,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deletePlanPic(picnoStringArrayList);
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("แก้ไข", new DialogInterface.OnClickListener() {
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
        builder.setTitle("กำหนดชื่อประเภทใหม่");
        //กำหนดเนื้อหา

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.edit_plantpic, null);


        galleryController();

        dateController();

        photoImageView = view.findViewById(R.id.edit_imagePhoto);
        str1 = view.findViewById(R.id.filename1);
        Picasso.get()
                .load("http://192.168.1.122/android/php/picture/activity/"+picnoStringArrayList+".jpg")
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

            Log.d("are", "editPlantPic ===>" + newDescription);

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

    //ลบรายการประเภทพืชเพาะปลูก
    private void editDeletePlantPic(String tidString){

        Myconstant myconstant = new Myconstant();
        try {
            DeletePlantPic deletePlantPic = new DeletePlantPic(getActivity());
            deletePlantPic.execute(tidString, myconstant.getUrlDeletePlantPic());

            if (Boolean.parseBoolean(deletePlantPic.get())) {
                createListView();
            } else {
                Toast.makeText(getActivity(),"ลบข้อมูลสำเร็จ",Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

//    private void uploadFile() {
//        str1 = view.findViewById(R.id.filename1);
//       File file = new File(mediaPath);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
//        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"),picnoStringArrayList);
//
//
//        OrderService orderService = ApiClient.getApiClient().create(OrderService.class);
//        Call<ServerResponse> call = orderService.uploadFile22(fileToUpload,filename);
//        call.enqueue(new Callback<ServerResponse>() {
//            @Override
//            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
//                ServerResponse serverResponse = response.body();
//                if (serverResponse != null) {
//                    if (serverResponse.isSuccess()) {
//                        Toast.makeText(getActivity(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getActivity(), "ภาพปัญหาการอัปโหลด", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    assert serverResponse != null;
//                    // Log.v("Response", serverResponse.toString());
//                }
//                //progressDialog.dismiss();
//            }
//            @Override
//            public void onFailure(Call<ServerResponse> call, Throwable t) {
//
//            }
//        });
//
//    }

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
