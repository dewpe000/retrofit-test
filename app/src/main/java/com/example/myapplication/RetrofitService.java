package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

        @GET("{path}")
        Call<List<UserData>> getUD(@Path("path") String path);





        @FormUrlEncoded
        @POST("post_test")
        Call<UserData> postUD(@Field("name") String name,
                              @Field("id") String id,
                              @Field("pw") String pw);

}
