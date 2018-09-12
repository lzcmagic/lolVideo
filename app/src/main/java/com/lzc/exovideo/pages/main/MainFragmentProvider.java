package com.lzc.exovideo.pages.main;

import com.lzc.exovideo.pages.main.fragment.center.CenterFragment;
import com.lzc.exovideo.pages.main.fragment.center.CenterModule;
import com.lzc.exovideo.pages.main.fragment.home.HomeFragment;
import com.lzc.exovideo.pages.main.fragment.home.HomeModule;
import com.lzc.exovideo.pages.main.fragment.info.InfoFragment;
import com.lzc.exovideo.pages.main.fragment.info.InfoModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentProvider {

    @ContributesAndroidInjector(modules = HomeModule.class)
    abstract HomeFragment providerHome();

    @ContributesAndroidInjector(modules = CenterModule.class)
    abstract CenterFragment providerCenter();

    @ContributesAndroidInjector(modules = InfoModule.class)
    abstract InfoFragment providerInfo();
}
