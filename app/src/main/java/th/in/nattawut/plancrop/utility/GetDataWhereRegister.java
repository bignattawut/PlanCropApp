package th.in.nattawut.plancrop.utility;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class GetDataWhereRegister extends AsyncTask<String, Void, String> {

    private Context context;

    public GetDataWhereRegister(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                  // .add("mid",strings[0])
                    .add(strings[0], strings[0])
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(strings[1])
//            Request request = builder.url(strings[2])
                    .post(requestBody).build();
            Response response=okHttpClient.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("27AprilV4", " e ==> " + e.toString());
            return null;
        }

    }
}
