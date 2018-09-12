package com.lzc.exovideo.pages.main.fragment.center;

import dagger.Module;
import dagger.Provides;

@Module
public class CenterModule {

    @Provides
    static CenterPresenter proveideCenterProvider(){
        return new CenterPresenter();
    }
}
