package com.lzc.exovideo.db.entity;

import org.greenrobot.greendao.annotation.Entity;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class VideoInfo {

    @Id(autoincrement = true)
    private Long id;
    private int videoId;
    private String videoName;
    private String videoLink;
    private String videoType;
    private Date updateTime;

    @Generated(hash = 109683931)
    public VideoInfo(Long id, int videoId, String videoName, String videoLink,
            String videoType, Date updateTime) {
        this.id = id;
        this.videoId = videoId;
        this.videoName = videoName;
        this.videoLink = videoLink;
        this.videoType = videoType;
        this.updateTime = updateTime;
    }

    @Generated(hash = 296402066)
    public VideoInfo() {
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }
}
