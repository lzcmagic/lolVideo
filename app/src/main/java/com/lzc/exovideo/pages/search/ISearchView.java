package com.lzc.exovideo.pages.search;

import com.lzc.exovideo.base.IBaseView;
import com.lzc.exovideo.db.entity.FilmInfo;

import java.util.List;

public interface ISearchView extends IBaseView {

    void searchFailed(String text);
    void searchSuccess(List<FilmInfo> list);
}
