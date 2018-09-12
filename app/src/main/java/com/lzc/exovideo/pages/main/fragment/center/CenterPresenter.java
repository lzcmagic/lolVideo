package com.lzc.exovideo.pages.main.fragment.center;

import com.lzc.exovideo.base.IBasePresenter;

public class CenterPresenter implements IBasePresenter<CenterView> {

    private CenterView mvpView;
    @Override
    public void attachView(CenterView activity) {
        this.mvpView=activity;
    }

    @Override
    public void detachView() {

    }
}
