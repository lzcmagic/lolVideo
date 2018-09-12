package com.lzc.exovideo.pages.detail;

import com.lzc.exovideo.base.IBaseView;
import com.lzc.exovideo.db.entity.VideoDetailInfo;

public interface IDetailView extends IBaseView {

    void refreshView(VideoDetailInfo info);
}
