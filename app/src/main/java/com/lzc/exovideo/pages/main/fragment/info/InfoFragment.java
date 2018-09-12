package com.lzc.exovideo.pages.main.fragment.info;

import android.os.Bundle;
import android.view.View;

import com.lzc.exovideo.R;
import com.lzc.exovideo.base.BaseFragment;
import com.lzc.exovideo.base.IBasePresenter;

import javax.inject.Inject;

public class InfoFragment extends BaseFragment implements InfoView{

    public static String TAG=InfoFragment.class.getSimpleName();

    @Inject
    InfoPresenter infoPresenter;

    public static InfoFragment newInstance() {

        Bundle args = new Bundle();
        InfoFragment fragment = new InfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public IBasePresenter createPresenter() {
        return infoPresenter;
    }

    @Override
    public void initLazyDate() {

    }

    @Override
    public int getResId() {
        return R.layout.fragment_info;
    }

    @Override
    public void initView(View rootView) {

    }
}
