package com.lzc.exovideo.api;

import com.lzc.exovideo.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static final int RELEASE_TIMEOUT=10;
    private volatile static RetrofitManager instance;
    private Retrofit retrofit;

    private RetrofitManager(){
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG){
            httpClientBuilder.addInterceptor(new HttpLoggingInterceptor());
        }else {
            httpClientBuilder.connectTimeout(RELEASE_TIMEOUT, TimeUnit.SECONDS);
        }
        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.HOST_API)
                .build();
    }

    public static RetrofitManager getInstance(){
        if (instance==null){
            synchronized (RetrofitManager.class){
                if (instance==null){
                    instance=new RetrofitManager();
                }
            }
        }
        return instance;
    }


    public <T> T createApi(Class<T> T){
        return retrofit.create(T);
    }
}
