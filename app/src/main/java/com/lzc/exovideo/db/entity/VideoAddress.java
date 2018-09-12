package com.lzc.exovideo.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class VideoAddress {

    @Id(autoincrement = true)
    private long id;
    private String belongTo;
    private String url;

    @Generated(hash = 95655790)
    public VideoAddress(long id, String belongTo, String url) {
        this.id = id;
        this.belongTo = belongTo;
        this.url = url;
    }

    @Generated(hash = 1302749470)
    public VideoAddress() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
