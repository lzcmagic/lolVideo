package com.lzc.exovideo.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.android.support.DaggerFragment;

public abstract class BaseFragment<T extends IBasePresenter> extends DaggerFragment implements IBaseView{


    protected Activity mAct;

    protected T mPresenter;

    private boolean isInitPrepare;//是否创建
    private boolean isInitDate;//是否初始化数据
    private boolean isVisibleToUser;//是否可见
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAct = (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mAct = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenter==null){
            mPresenter=createPresenter();
        }
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    public abstract T createPresenter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(mAct).inflate(getResId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        isInitPrepare=true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lazyInitDate();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
        lazyInitDate();
    }

    private void lazyInitDate(){
        if (isInitPrepare&&isVisibleToUser&&!isInitDate){
            isInitDate=true;
            initLazyDate();
        }
    }

    public abstract void initLazyDate();

    public abstract int getResId();

    public abstract void initView(View rootView);

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
