package com.lzc.exovideo.db.entity;

import org.greenrobot.greendao.annotation.Entity;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class FilmInfo{

    @Id(autoincrement = true)
    private Long id;
    private String filmId;
    private String filmType;
    private String filmName;
    private String fileLink;
    private Date updateTime;
    private String from;

    @Generated(hash = 2053253212)
    public FilmInfo(Long id, String filmId, String filmType, String filmName,
            String fileLink, Date updateTime, String from) {
        this.id = id;
        this.filmId = filmId;
        this.filmType = filmType;
        this.filmName = filmName;
        this.fileLink = fileLink;
        this.updateTime = updateTime;
        this.from = from;
    }

    @Generated(hash = 627615339)
    public FilmInfo() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getFilmType() {
        return filmType;
    }

    public void setFilmType(String filmType) {
        this.filmType = filmType;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    @Override
    public String toString() {
        return "FilmInfo{" +
                "filmId='" + filmId + '\'' +
                ", filmType='" + filmType + '\'' +
                ", filmName='" + filmName + '\'' +
                ", fileLink='" + fileLink + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
