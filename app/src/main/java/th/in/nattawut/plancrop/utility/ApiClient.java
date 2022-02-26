package th.in.nattawut.plancrop.utility;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String Url = "https://www.plancropapp.online/android/php/";
    //public static final String Url = "http://192.168.1.12/android/php/";
    private static Retrofit retrofit;

    public static Retrofit getApiClient() {
        retrofit = new Retrofit.Builder().baseUrl(Url).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }
}
