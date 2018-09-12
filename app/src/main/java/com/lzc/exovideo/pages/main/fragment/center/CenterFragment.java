package com.lzc.exovideo.pages.main.fragment.center;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lzc.exovideo.R;
import com.lzc.exovideo.base.BaseFragment;
import com.lzc.exovideo.base.IBasePresenter;

import javax.inject.Inject;

public class CenterFragment extends BaseFragment implements CenterView {


    public static final String TAG = CenterFragment.class.getSimpleName();
    @Inject
    CenterPresenter centerPresenter;

    public static CenterFragment newInstance() {

        Bundle args = new Bundle();
        CenterFragment fragment = new CenterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public IBasePresenter createPresenter() {
        return centerPresenter;
    }

    @Override
    public void initLazyDate() {
    }

    @Override
    public int getResId() {
        return R.layout.fragment_center;
    }


    @Override
    public void initView(View rootView) {
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    public static int FILE_CHOOSER_REQUEST_CODE = 111;
}
