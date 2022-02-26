package th.in.nattawut.plancrop.utility;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface OrderService {

    @GET("selectorder.php")
    Call<List<Order>> getOrder(@Query("mid") String mid);

    @GET("selectplanfarmerandroid.php")
    Call<List<PlanFarmer>> getPlanFarmer(@Query("mid") String mid);

    @GET("selectplantfarmer.php")
    Call<List<PlantFarmer>> getPlantFarmer(@Query("mid") String mid,@Query("sdate") String sdate);

    Call<List<PlantFarmer>> getsdataFarmer(
            @Query("sdate") String sdate);

    @GET("selectfarmerandroid.php")
    Call<List<Farmer>> getFarmer(@Query("mid") String mid);

//    @GET("selectsite.php")
//    Call<List<Site>> getSite(@Query("mid") String mid);


    @POST("selectsite.php")
    @FormUrlEncoded
    Call<List<Site>> getSite(@Field("mid") String mid);


    @GET("plantreportall.php")
    Call<List<PlantReportall>> getPlantReportall(@Query("sdate") String sdate, @Query("edate") String edate);

    @GET("planresult.php")
    Call<List<PlanResult>> getPlanReport(@Query("pdate") String pdate);

    @GET("plantreport.php")
    Call<List<PlantReport>> getPlantReport(@Query("mid") String mid);

    @GET("plantresult.php")
    Call<List<PlantResult>> getPlantResult(@Query("pid") String pid,
                                           @Query("did") String did,
                                           @Query("sid") String sid,
                                           @Query("sdate") String sdate,
                                           @Query("edate") String edate,
                                           @Query("mid") String mid,
                                           @Query("tid") String tid,
                                           @Query("cid") String cid);


    ///
    @GET("plantmap.php")
    Call<List<PlantResultSite>> getPlantResultSite(@Query("pid") String pid,
                                           @Query("did") String did,
                                           @Query("sid") String sid,
                                           @Query("sdate") String sdate,
                                           @Query("edate") String edate,
                                           @Query("mid") String mid,
                                           @Query("tid") String tid,
                                           @Query("cid") String cid);
    ///

    @POST("memberlogin.php")
    @FormUrlEncoded
    Call<List<LoginResponse>> getuserLogin(
            @Field("username") String username,
            @Field("passwd") String passwd
    );


    @POST("logout")
    Call<Void> logout();

    @FormUrlEncoded
    @POST("upload1.php")
    Call<UploadImg> uploadImage(@Field("SCode") String SCode,@Field("URL") String url);

//    @FormUrlEncoded
//    @POST("upload2.php")
//    Call<UploadImg> uploadImage(@Field("type ") String type,
//                                @Field("picno ") String picno,
//                                @Field("SCode") String SCode,
//                                @Field("URL") String url);



    @GET("selectplanandroidtest.php")
    Call<List<Plan>> selectplanandroidtest(@Query("tid") String tid,
                                           @Query("cid") String cid,
                                           @Query("sdate") String sdate);

    @GET("selectplant.php")
    Call<List<Plant>> getPlant(@Query("pid") String pid,
                               @Query("did") String did,
                               @Query("sid") String sid,
                               @Query("tid") String tid,
                               @Query("cid") String cid,
                               @Query("sdate") String sdate);


    @GET("plantactivity.php")
    Call<List<PlantActivity>> getPlantActivity(@Query("no") String no);

    //อับโหลดรูป
    @Multipart
    @POST("upload3.php")
    Call<ServerResponse> uploadFile22(@Part MultipartBody.Part file,
                                     @Part("picno") RequestBody picno);



    @Multipart
    @POST("upload3.php")
    Call<ServerResponse> uploadFile1(@Part MultipartBody.Part file,
                                     @Part MultipartBody.Part picno);


    @GET("orderresultsum.php")
    Call<List<OrderResultSum>> OrderResultSum();


    @GET("orderresultbalance.php")
    Call<List<OrderResultBalance>> OrderResultBalance();


}
