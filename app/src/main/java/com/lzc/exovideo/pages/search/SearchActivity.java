package com.lzc.exovideo.pages.search;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CursorAdapter;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import com.lzc.exovideo.MyApp;
import com.lzc.exovideo.R;
import com.lzc.exovideo.adapter.MyAdapter;
import com.lzc.exovideo.base.BaseActivity;
import com.lzc.exovideo.db.entity.FilmInfo;
import com.lzc.exovideo.pages.detail.DetailActivity;
import com.lzc.exovideo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SearchActivity extends BaseActivity<SearchPresenter> implements ISearchView {

    @Inject
    SearchPresenter searchPresenter;
    private SearchView searchView;
    private Cursor cursor;
    private CursorAdapter cursorAdapter;
    private List<String> curSorString;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    @Override
    protected SearchPresenter createPresenter() {
        return searchPresenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        curSorString=new ArrayList<>();
        initView();
    }



    private void initView() {
        progressBar=findViewById(R.id.progress);
        searchView = findViewById(R.id.search);
        recyclerView=findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(null,this,false);
        recyclerView.setAdapter(myAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Log.d("lzc", "onQueryTextSubmit: " + query);
                progressBar.setVisibility(View.VISIBLE);
                mPresenter.searchWd(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("lzc", "onQueryTextChange: "+newText);
                cursor = MyApp.getDaoSession().getDatabase()
                        .rawQuery("select _id,FILM_ID,FILM_NAME,FILM_TYPE from FILM_INFO where FILM_NAME like ?", new String[]{"%" + newText + "%"});
                curSorString.clear();
                while(cursor.moveToNext()){
                    String id = cursor.getString(cursor.getColumnIndex("FILM_ID"));
                    curSorString.add(id);
                }
                cursorAdapter.swapCursor(cursor);
                cursorAdapter.notifyDataSetChanged();
                return false;
            }
        });
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                intentDetail(curSorString.get(position));
                return false;
            }
        });



        cursorAdapter = new SimpleCursorAdapter(this,
                R.layout.cursor_item, cursor,
                new String[]{"FILM_NAME", "FILM_TYPE"},
                new int[]{R.id.item_name, R.id.item_type},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        searchView.setSuggestionsAdapter(cursorAdapter);

        searchView.setOnSearchClickListener(v -> {
            Log.d("lzc", "initView: ");
        });

    }

    private void intentDetail(String detailId){
        Intent intent=new Intent(this, DetailActivity.class);
        intent.putExtra("linkUrl",detailId);
        startActivity(intent);
        finish();
    }

    @Override
    public void searchFailed(String text) {
        ToastUtil.toastShort(text);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void searchSuccess(List<FilmInfo> list) {

        progressBar.setVisibility(View.GONE);
        myAdapter.refreshDate(list);
        Log.d("lzc", "searchSuccess: "+list);
    }

}
