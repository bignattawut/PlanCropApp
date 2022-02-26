package th.in.nattawut.plancrop.utility;


import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class EditCrop extends AsyncTask<String, Void, String> {

    private Context context;

    public EditCrop(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("cid", strings[0])
                    .add("crop", strings[1])
                    .add("tid", strings[2])
                    .add("beginharvest", strings[3])
                    .add("harvestperiod", strings[4])
                    .add("yield", strings[5])
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(strings[6]).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

