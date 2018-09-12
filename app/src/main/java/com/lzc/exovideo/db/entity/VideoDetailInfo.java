package com.lzc.exovideo.db.entity;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Arrays;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class VideoDetailInfo {

    @Id(autoincrement = true)
    private Long id;
    @Property
    private String detailId;
    private String videoCover;
    private String videoName;
    @Convert(converter = StringConverter.class, columnType = String.class)
    private List<String> descs;
//    private String videoAlias;
//    private String director;
//    private String mainStar;
//    private String type;
//    private String area;
//    private String lanague;
//    private String onPrint;
//    private String update;
    private String desc;
    @Convert(converter = StringConverter.class, columnType = String.class)
    private List<String> urls;

    @Generated(hash = 166163909)
    public VideoDetailInfo(Long id, String detailId, String videoCover, String videoName,
            List<String> descs, String desc, List<String> urls) {
        this.id = id;
        this.detailId = detailId;
        this.videoCover = videoCover;
        this.videoName = videoName;
        this.descs = descs;
        this.desc = desc;
        this.urls = urls;
    }

    @Generated(hash = 432654343)
    public VideoDetailInfo() {
    }


    public static class StringConverter implements PropertyConverter<List<String>, String> {

        @Override
        public List<String> convertToEntityProperty(String databaseValue) {
            if (databaseValue == null) {
                return null;
            }
            else {
                List<String> list = Arrays.asList(databaseValue.split("&&"));
                return list;
            }
        }

        @Override
        public String convertToDatabaseValue(List<String> entityProperty) {
            if(entityProperty==null){
                return null;
            }
            else{
                StringBuilder sb= new StringBuilder();
                for(String link:entityProperty){
                    sb.append(link);
                    sb.append("&&");
                }
                return sb.toString();
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getVideoCover() {
        return videoCover;
    }

    public void setVideoCover(String videoCover) {
        this.videoCover = videoCover;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public List<String> getDescs() {
        return descs;
    }

    public void setDescs(List<String> descs) {
        this.descs = descs;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    @Override
    public String toString() {
        return "VideoDetailInfo{" +
                "id=" + id +
                ", detailId='" + detailId + '\'' +
                ", videoCover='" + videoCover + '\'' +
                ", videoName='" + videoName + '\'' +
                ", descs=" + descs +
                ", desc='" + desc + '\'' +
                ", urls=" + urls +
                '}';
    }

}
