package com.lzc.exovideo.pages.main;

import com.lzc.exovideo.di.ano.scope.SingleActivity;
import com.lzc.exovideo.pages.main.fragment.center.CenterFragment;
import com.lzc.exovideo.pages.main.fragment.home.HomeFragment;
import com.lzc.exovideo.pages.main.fragment.info.InfoFragment;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class MainModule {

    @Binds
    abstract MainView bindMainView(MainActivity activity);

    @SingleActivity
    @Provides
    static HomeFragment provideHomeFragment(){
        return HomeFragment.newInstance();
    }

    @SingleActivity
    @Provides
    static CenterFragment provideCenterFragment(){
        return CenterFragment.newInstance();
    }

    @SingleActivity
    @Provides
    static InfoFragment provideInfoFragment(){
        return InfoFragment.newInstance();
    }


    @SingleActivity
    @Provides
    static MainPresenter providePresenter(){return new MainPresenter();}
}
