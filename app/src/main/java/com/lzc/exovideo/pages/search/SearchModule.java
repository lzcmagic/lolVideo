package com.lzc.exovideo.pages.search;


import com.lzc.exovideo.di.ano.scope.SingleActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class SearchModule {

    @Binds
    abstract ISearchView bindSearchActivity(SearchActivity activity);

    @SingleActivity
    @Provides
    static SearchPresenter providePresenter(SearchActivity activity){
        return  new SearchPresenter(activity);
    }
}
