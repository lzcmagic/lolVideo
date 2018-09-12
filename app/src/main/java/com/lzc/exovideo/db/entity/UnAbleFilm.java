package com.lzc.exovideo.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UnAbleFilm {

    @Id(autoincrement = true)
    private Long _id;

    private String linkUrl;

    private String fileName;

    @Generated(hash = 796977061)
    public UnAbleFilm(Long _id, String linkUrl, String fileName) {
        this._id = _id;
        this.linkUrl = linkUrl;
        this.fileName = fileName;
    }

    @Generated(hash = 389762708)
    public UnAbleFilm() {
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getLinkUrl() {
        return this.linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
