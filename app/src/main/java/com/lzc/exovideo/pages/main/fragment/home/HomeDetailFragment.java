package com.lzc.exovideo.pages.main.fragment.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lzc.exovideo.R;
import com.lzc.exovideo.adapter.MyAdapter;
import com.lzc.exovideo.base.BaseFragment1;
import com.lzc.exovideo.db.entity.FilmInfo;
import com.lzc.exovideo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

public class HomeDetailFragment extends BaseFragment1<HomeDetailPresenter> implements HomeDetailView {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private SwipeRefreshLayout refreshLayout;
    private int location;
    public static HomeDetailFragment newInstance(String linkTag, String linkName) {

        Bundle args = new Bundle();
        args.putString("link_tag", linkTag);
        args.putString("link_name", linkName);
        HomeDetailFragment fragment = new HomeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public HomeDetailPresenter createPresenter() {
        return new HomeDetailPresenter();
    }

    @Override
    public void initLazyDate() {
        mPresenter.initDate(getArguments().getString("link_tag"));
    }

    public void smoothTop(){
        recyclerView.scrollToPosition(0);
//        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public int getResId() {
        return R.layout.fragment_home_detail;
    }

    @Override
    public void initView(View rootView) {
        List<FilmInfo> filmInfos = new ArrayList<>();
        refreshLayout = rootView.findViewById(R.id.home_refresh);
        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(true);
            mPresenter.refreshList(getArguments().getString("link_tag"));
        });
        recyclerView = rootView.findViewById(R.id.home_detail_recycler);
        refreshLayout.setRefreshing(true);
        myAdapter = new MyAdapter(filmInfos,mAct,true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mAct);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mPresenter.pullRefrsh(getArguments().getString("link_tag"),newState,location==myAdapter.getItemCount()-1);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                location=layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void refreshRecyclerView(List<FilmInfo> list) {
        refreshLayout.setRefreshing(false);
        if (list != null && list.size() > 0) {
            myAdapter.refreshDate(list);
        } else {
            ToastUtil.toastShort("请求信息失败");
        }
    }

    @Override
    public void refreshRecyclerNew(List<FilmInfo> list) {
        if (list != null && list.size() > 0) {
            myAdapter.refreshDate(list);
        } else {
            ToastUtil.toastShort("刷新失败");
        }
        refreshLayout.setRefreshing(false);
    }


}
