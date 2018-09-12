package com.lzc.exovideo.pages.main.fragment.home;

import com.lzc.exovideo.base.IBaseView;
import com.lzc.exovideo.db.entity.FilmInfo;

import java.util.List;

public interface HomeDetailView extends IBaseView {

    void refreshRecyclerView(List<FilmInfo> list);


    void refreshRecyclerNew(List<FilmInfo> list);
}
