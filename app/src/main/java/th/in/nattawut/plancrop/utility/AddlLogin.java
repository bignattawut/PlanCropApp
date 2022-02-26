package th.in.nattawut.plancrop.utility;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class AddlLogin extends AsyncTask<String, Void, String> {

    private Context context;

    public AddlLogin(Context context) {
        this.context = context;
    }

    //public static final String LOGIN_URL = "http://192.168.1.113/android/php/memberlogin.php";

    @Override
    protected String doInBackground(String... strings) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("username", strings[0])
                    //.add("pwd",strings[1])
                    .build();
            Request.Builder builder = new Request.Builder();
            //Request request = builder.url(LOGIN_URL)
                    Request request = builder.url(strings[1])
            //Request request = builder.url("http://192.168.1.110/android/php/memberlogin.php")
                    .post(requestBody).build();
            Response response=okHttpClient.newCall(request).execute();
            return response.body().string();



        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}

