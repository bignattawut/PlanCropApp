package th.in.nattawut.plancrop.utility;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;

public class GetDataWherePlanFarmer extends AsyncTask<String, Void, String> {

    private Context context;

    public GetDataWherePlanFarmer(Context context) {
        this.context = context;
    }

    private String logger;

    @Override
    protected String doInBackground(String... strings) {
        try {
//            where $_POST
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    //.add(strings[0], strings[0])
                    .addEncoded(strings[0],strings[0])
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(strings[1]).post(requestBody).build();
            Response response=okHttpClient.newCall(request).execute(); //con next to server โดยใช้method ที่ชื่อ newCall ใช้ตัวrequestและexecute แล้วจะได้ตัวresponse กลับมา responseจะบรรจุข้อมูลมาด้วย
            return response.body().string();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("27AprilV4", " e ==> " + e.toString());
            return null;
        }
    }
}

