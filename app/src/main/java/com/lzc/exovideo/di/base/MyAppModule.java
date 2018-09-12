package com.lzc.exovideo.di.base;

import android.app.Application;
import android.content.Context;

import com.lzc.exovideo.api.RetrofitManager;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class MyAppModule {
    @Singleton
    @Binds
    abstract Context provideContext(Application application);

    @Singleton
    @Provides
    static RetrofitManager provideRetrofitManager(){
        return RetrofitManager.getInstance();
    }
}
