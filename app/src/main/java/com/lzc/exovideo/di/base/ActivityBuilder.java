package com.lzc.exovideo.di.base;

import com.lzc.exovideo.di.ano.scope.SingleActivity;
import com.lzc.exovideo.pages.detail.DetailActivity;
import com.lzc.exovideo.pages.detail.DetailModule;
import com.lzc.exovideo.pages.main.MainActivity;
import com.lzc.exovideo.pages.main.MainFragmentProvider;
import com.lzc.exovideo.pages.main.MainModule;
import com.lzc.exovideo.pages.play.PlayActivity;
import com.lzc.exovideo.pages.play.PlayModule;
import com.lzc.exovideo.pages.search.SearchActivity;
import com.lzc.exovideo.pages.search.SearchModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @SingleActivity
    @ContributesAndroidInjector(modules = {MainModule.class, MainFragmentProvider.class})
    abstract MainActivity bindMainActiivty();

    @SingleActivity
    @ContributesAndroidInjector(modules = PlayModule.class)
    abstract PlayActivity bindPlayActivity();

    @SingleActivity
    @ContributesAndroidInjector(modules = DetailModule.class)
    abstract DetailActivity bindDetailActivity();

    @SingleActivity
    @ContributesAndroidInjector(modules = SearchModule.class)
    abstract SearchActivity bindSearchActivity();
}
