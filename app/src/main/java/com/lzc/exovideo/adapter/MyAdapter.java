package com.lzc.exovideo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lzc.exovideo.R;
import com.lzc.exovideo.db.entity.FilmInfo;
import com.lzc.exovideo.pages.detail.DetailActivity;
import com.lzc.exovideo.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FilmInfo> filmInfos;
    private Context mAct;
    private static final int NORMAL_ITEM = 1;
    private static final int REFRESH_ITEM = 2;
    private boolean needPullRefresh;

    public MyAdapter(List<FilmInfo> filmInfos, Context mAct,boolean needPullRefresh) {
        this.mAct = mAct;
        this.setNeedPullRefresh(needPullRefresh);
        if (filmInfos == null) {
            this.filmInfos = new ArrayList<>();
        } else {

            this.filmInfos = filmInfos;
        }
    }

    private void setNeedPullRefresh(boolean flag) {
        this.needPullRefresh = flag;
    }

    public void refreshDate(List<FilmInfo> list) {
        if (list != null && list.size() > 0) {
            this.filmInfos.clear();
            this.filmInfos.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (!needPullRefresh) {
            return super.getItemViewType(position);
        } else {
            if (position == filmInfos.size() ) {
                return REFRESH_ITEM;
            } else {
                return NORMAL_ITEM;
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_home_detail, parent, false);
        if (!needPullRefresh) {
            return new MyHolder(rootView);
        } else {
            if (viewType == REFRESH_ITEM) {
                View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item, parent, false);
                return new RefreshHolder(inflate);
            } else if (viewType == NORMAL_ITEM) {
                return new MyHolder(rootView);
            } else {
                return new MyHolder(rootView);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (!needPullRefresh){
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(mAct, DetailActivity.class);
                intent.putExtra("linkUrl", filmInfos.get(position).getFilmId());
                mAct.startActivity(intent);
            });
            ((MyHolder) holder).date.setText(TimeUtil.getInstance().formatDateToString(filmInfos.get(position).getUpdateTime()));
            ((MyHolder) holder).name.setText(filmInfos.get(position).getFilmName());
            ((MyHolder) holder).type.setText(filmInfos.get(position).getFilmType());
        }else{
            if (holder instanceof MyHolder) {

                holder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(mAct, DetailActivity.class);
                    intent.putExtra("linkUrl", filmInfos.get(position).getFilmId());
                    mAct.startActivity(intent);
                });
                ((MyHolder) holder).date.setText(TimeUtil.getInstance().formatDateToString(filmInfos.get(position).getUpdateTime()));
                ((MyHolder) holder).name.setText(filmInfos.get(position).getFilmName());
                ((MyHolder) holder).type.setText(filmInfos.get(position).getFilmType());
            }

            if (holder instanceof RefreshHolder) {
                ((RefreshHolder) holder).textView.setText("加载中...");
            }
        }
    }

    @Override
    public int getItemCount() {
        if (!needPullRefresh) {
            return filmInfos.size();
        } else {
            return filmInfos.size() + 1;
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView type;
        private TextView date;

        public MyHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.detail_name);
            type = itemView.findViewById(R.id.detail_type);
            date = itemView.findViewById(R.id.detail_date);
        }
    }

    class RefreshHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;
        private TextView textView;

        public RefreshHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress);
            textView = itemView.findViewById(R.id.progress_text);
        }
    }

}