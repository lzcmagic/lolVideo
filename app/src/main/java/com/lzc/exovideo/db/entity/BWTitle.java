package com.lzc.exovideo.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BWTitle {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String link;
    @Generated(hash = 1447995645)
    public BWTitle(Long id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
    }
    @Generated(hash = 2012573130)
    public BWTitle() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLink() {
        return this.link;
    }
    public void setLink(String link) {
        this.link = link;
    }
}
