package com.lzc.exovideo.pages.detail;

import com.lzc.exovideo.di.ano.scope.SingleActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class DetailModule {

    @Binds
    abstract IDetailView bingDetailAvtivity(DetailActivity activity);

    @Provides
    @SingleActivity
    static DetailPresenter providePresenter(DetailActivity activity){
        return new DetailPresenter(activity);
    }
}
