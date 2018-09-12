package com.lzc.exovideo.pages.main;

import com.lzc.exovideo.base.IBasePresenter;

public class MainPresenter implements IBasePresenter<MainView> {


    private MainView mvpView;

    public MainPresenter() {
    }

    @Override
    public void attachView(MainView activity) {
        this.mvpView = activity;
    }

    @Override
    public void detachView() {

    }
}
