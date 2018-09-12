package com.lzc.exovideo.pages.main.fragment.info;

import dagger.Module;
import dagger.Provides;

@Module
public class InfoModule {

    @Provides
    static InfoPresenter providePresenter(){
        return new InfoPresenter();
    }
}
