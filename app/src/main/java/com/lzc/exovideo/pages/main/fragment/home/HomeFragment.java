package com.lzc.exovideo.pages.main.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.lzc.exovideo.R;
import com.lzc.exovideo.base.BaseFragment;
import com.lzc.exovideo.db.entity.BWTitle;
import com.lzc.exovideo.pages.search.SearchActivity;
import com.lzc.exovideo.utils.DensityUtil;
import com.lzc.exovideo.view.MySwipeRefreshLayout;
import com.lzc.exovideo.view.viewspread.helper.BaseViewHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeView, View.OnClickListener {

    public static final String TAG = HomeFragment.class.getSimpleName();

    @Inject
    HomePresenter homePresenter;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public HomePresenter createPresenter() {
        return homePresenter;
    }

    @Override
    public void initLazyDate() {
        mPresenter.initTitle();
    }

    @Override
    public int getResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View rootView) {
        fab=rootView.findViewById(R.id.float_btn);
        fab.setOnClickListener(this);
        tabLayout = rootView.findViewById(R.id.home_tab);
        viewPager = rootView.findViewById(R.id.home_view_pager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                tab.getTag()
                FragmentPagerAdapter adapter= (FragmentPagerAdapter) viewPager.getAdapter();
                HomeDetailFragment detailFragment= (HomeDetailFragment) adapter.getItem(tab.getPosition());
                detailFragment.smoothTop();
            }
        });
    }

    @Override
    public void setTitle(List<BWTitle> bwTitles) {
        viewPager.setAdapter(new HomePagerFragmentAdapter(getChildFragmentManager(), bwTitles));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(bwTitles.size());
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.float_btn){

            Intent intent=new Intent(mAct,SearchActivity.class);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeBasic();
            ActivityCompat.startActivity(mAct,intent,optionsCompat.toBundle());
        }
    }

    private class HomePagerFragmentAdapter extends FragmentPagerAdapter {

        private List<HomeDetailFragment> fragments;
        private List<BWTitle> bwTitles;
        public HomePagerFragmentAdapter(FragmentManager fm, List<BWTitle> bwTitles) {
            super(fm);
            fragments = new ArrayList<>();
            this.bwTitles=bwTitles;
            for (BWTitle bwTitle : bwTitles) {
                fragments.add(HomeDetailFragment.newInstance(bwTitle.getLink(), bwTitle.getName()));
            }
        }


        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return bwTitles.get(position).getName();
        }
    }
}
