package com.lzc.exovideo.api;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @Headers(value = {"Content-Type: application/x-www-form-urlencoded", "Accept: application/json"})
    @POST("/index.php?m=vod-search")
    Call<ResponseBody> queryByName(@Field(value = "wd") String wd, @Field(value = "submit") String submit);
}
