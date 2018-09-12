package com.lzc.exovideo.pages.play;

import android.app.Activity;

import com.lzc.exovideo.base.IBasePresenter;

public class PlayPresenter implements IBasePresenter<IPlayView> {


    private Activity mAc;

    public PlayPresenter(Activity mAc) {
        this.mAc = mAc;
    }

    @Override
    public void attachView(IPlayView activity) {

    }

    @Override
    public void detachView() {

    }
}
