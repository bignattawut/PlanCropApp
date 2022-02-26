package th.in.nattawut.plancrop.utility;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class EditFarmerandroid extends AsyncTask<String, Void, String> {
    private Context context;

    public EditFarmerandroid(Context context) {
        this.context = context;
    }
    @Override
    protected String doInBackground(String... strings) {
        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("mid", strings[0])
                    .add("username", strings[1])
                    .add("password", strings[2])
                    .add("id", strings[3])
                    .add("name", strings[4])
                    .add("address", strings[5])
                    .add("pid", strings[6])
                    .add("did", strings[7])
                    .add("sid", strings[8])
                    .add("vid", strings[9])
                    .add("tel", strings[10])
                    .add("email", strings[11])
                    .add("area", strings[12])
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(strings[13]).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
