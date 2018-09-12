package com.lzc.exovideo.pages.main.fragment.info;

import android.icu.text.IDNA;

import com.lzc.exovideo.base.IBasePresenter;

public class InfoPresenter implements IBasePresenter<InfoView> {

    private InfoView mvpView;
    @Override
    public void attachView(InfoView activity) {
        this.mvpView=activity;
    }

    @Override
    public void detachView() {

    }
}
