package com.lzc.exovideo.pages.main;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.lzc.exovideo.R;
import com.lzc.exovideo.base.BaseActivity;
import com.lzc.exovideo.pages.main.fragment.center.CenterFragment;
import com.lzc.exovideo.pages.main.fragment.home.HomeFragment;
import com.lzc.exovideo.pages.main.fragment.info.InfoFragment;
import com.lzc.exovideo.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView {


    @Inject
    HomeFragment homeFragment;

    @Inject
    CenterFragment centerFragment;

    @Inject
    InfoFragment infoFragment;

    @Inject
    MainPresenter mainPresenter;

    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.main_view_pager);
        initViewPager();
        showHomeFragment();
    }

    private void initViewPager() {
        List<Fragment> list=new ArrayList<>();
        list.add(homeFragment);
        MainPagerAdapter adapter=new MainPagerAdapter(getSupportFragmentManager(),list);
        viewPager.setAdapter(adapter);
        viewPager.setPageMargin(DensityUtil.dip2px(10,this));
        viewPager.setOffscreenPageLimit(list.size());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected MainPresenter createPresenter() {
        return mainPresenter;
    }


    @Override
    public void showHomeFragment() {
        viewPager.setCurrentItem(0,false);
    }

    @Override
    public void showCenterFragment() {
        viewPager.setCurrentItem(1,false);
    }

    @Override
    public void showInfoFragment() {
        viewPager.setCurrentItem(2,false);
    }

    class MainPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> list;

        public MainPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            if (list != null) {
                this.list = list;
            } else {
                this.list = new ArrayList<>();
            }
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}
