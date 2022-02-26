package th.in.nattawut.plancrop.utility;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class AddNewUserUpload extends AsyncTask<String, Void, String> {

    private Context context;

    public AddNewUserUpload(Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(String... strings) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    //.add("MID", strings[0])
                    .add("UserID", strings[0])
                    .add("PWD", strings[1])
                    .add("Name", strings[2])
                    .add("ID", strings[3])
                    .add("Address", strings[4])
                    .add("Tel", strings[5])
                    .add("EMail", strings[6])
                    .add("VID", strings[7])
                    .add("SID", strings[8])
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(strings[9]).post(requestBody).build();
            Response response=okHttpClient.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
