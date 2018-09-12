package com.lzc.exovideo.pages.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzc.exovideo.R;
import com.lzc.exovideo.base.BaseActivity;
import com.lzc.exovideo.base.GlideApp;
import com.lzc.exovideo.db.entity.VideoDetailInfo;
import com.lzc.exovideo.pages.play.PlayActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DetailActivity extends BaseActivity<DetailPresenter> implements IDetailView {

    @Inject
    DetailPresenter detailPresenter;

    private TextView title;
    private TextView alais;
    private TextView director;
    private TextView mainStar;
    private TextView type;
    private TextView area;
    private TextView language;
    private TextView print;
    private TextView update;
    private TextView desc;

    private ImageView coverImg;
    private RecyclerView recyclerView;
    private String videoName;

    @Override
    protected DetailPresenter createPresenter() {
        return detailPresenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        mPresenter.initDate(getIntent().getStringExtra("linkUrl"));
    }

    private void initView() {
        coverImg = findViewById(R.id.cover_img);
        title = findViewById(R.id.title);
        alais = findViewById(R.id.alias);
        director = findViewById(R.id.director);
        mainStar = findViewById(R.id.main_star);
        type = findViewById(R.id.type);
        area = findViewById(R.id.area);
        language = findViewById(R.id.lanague);
        print = findViewById(R.id.print);
        update = findViewById(R.id.update);
        desc = findViewById(R.id.desc);
        recyclerView = findViewById(R.id.detail_recycler);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);
        NestedScrollView scrollView=findViewById(R.id.des_con);
//        scrollView.scrollTo(0,0);
    }

    @Override
    public void refreshView(VideoDetailInfo info) {

        GlideApp.with(this)
                .load(info.getVideoCover())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(coverImg);
        videoName = info.getVideoName();
        title.setText(videoName);
        List<String> descs = info.getDescs();
        alais.setText(setText(descs.get(0)));
        director.setText(setText(descs.get(1)));
        mainStar.setText(setText(descs.get(2)));
        type.setText(setText(descs.get(3)));
        area.setText(setText(descs.get(4)));
        language.setText(setText(descs.get(5)));
        print.setText(setText(descs.get(6)));
        update.setText(setText(descs.get(7)));
        desc.setText(setText(info.getDesc()));

        DetailAdapter adapter = new DetailAdapter(info.getUrls());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


    private class DetailAdapter extends RecyclerView.Adapter<MyHolder> {

        private List<String> imgurls;

        public DetailAdapter(List<String> imgurls) {
            if (imgurls == null) {
                this.imgurls=new ArrayList<>();
            } else {
                this.imgurls = imgurls;
            }
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_recycler_item, parent, false);
            return new MyHolder(rootView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.button.setOnClickListener(v -> {
                Intent intent = new Intent(DetailActivity.this, PlayActivity.class);
                intent.putExtra("playUrl", imgurls.get(position).split("@@")[0]);
                intent.putExtra("title", videoName);
                startActivity(intent);
            });
            holder.button.setText(imgurls.get(position).split("@@")[1]);
        }

        @Override
        public int getItemCount() {
            return imgurls.size();
        }

    }

    class MyHolder extends RecyclerView.ViewHolder {

        Button button;

        MyHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button2);
        }
    }

    private String setText(String value) {
        return TextUtils.isEmpty(value) ? "暂无" : value;
    }
}
