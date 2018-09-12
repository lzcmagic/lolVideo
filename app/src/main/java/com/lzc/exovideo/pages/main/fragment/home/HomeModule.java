package com.lzc.exovideo.pages.main.fragment.home;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    @Provides
    static HomePresenter providerHomePresenter(){
        return new HomePresenter();
    }
}
