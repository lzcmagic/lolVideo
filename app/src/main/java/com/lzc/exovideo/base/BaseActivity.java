package com.lzc.exovideo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity<T extends IBasePresenter> extends DaggerAppCompatActivity implements IBaseView {

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    protected abstract T createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public void showToast() {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }
}
