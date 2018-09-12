package com.lzc.exovideo.pages.main.fragment.home;

import com.lzc.exovideo.base.IBaseView;
import com.lzc.exovideo.db.entity.BWTitle;

import java.util.List;

public interface HomeView extends IBaseView {

    void setTitle(List<BWTitle> bwTitles);
}
