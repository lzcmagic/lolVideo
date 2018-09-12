package com.lzc.exovideo.pages.detail;

import android.app.Activity;
import android.os.AsyncTask;

import com.lzc.exovideo.BuildConfig;
import com.lzc.exovideo.MyApp;
import com.lzc.exovideo.base.IBasePresenter;
import com.lzc.exovideo.db.entity.BWTitle;
import com.lzc.exovideo.db.entity.VideoDetailInfo;
import com.lzc.exovideo.downloader.greendao.BWTitleDao;
import com.lzc.exovideo.downloader.greendao.DaoSession;
import com.lzc.exovideo.downloader.greendao.FilmInfoDao;
import com.lzc.exovideo.downloader.greendao.VideoDetailInfoDao;

import org.greenrobot.greendao.query.Query;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class DetailPresenter implements IBasePresenter<IDetailView> {

    private IDetailView mvpView;

    private Activity mAct;

    public DetailPresenter(Activity mAct) {
        this.mAct = mAct;
    }

    @Override
    public void attachView(IDetailView activity) {
        this.mvpView = activity;
    }

    @Override
    public void detachView() {

    }

    public void initDate(String belongTo) {
        DaoSession daoSession = MyApp.getDaoSession();
        VideoDetailInfoDao infoDao = daoSession.getVideoDetailInfoDao();
        Query<VideoDetailInfo> build = infoDao.queryBuilder().where(VideoDetailInfoDao.Properties.DetailId.eq(belongTo)).build();
        List<VideoDetailInfo> list = build.list();
        if (list != null && list.size() > 0) {
            mvpView.refreshView(list.get(0));
        } else {
            new DownloadInfoTask().executeOnExecutor(Executors.newSingleThreadExecutor(), BuildConfig.HOST_API + belongTo, belongTo);
        }
    }

    private class DownloadInfoTask extends AsyncTask<String, String, VideoDetailInfo> {

        @Override
        protected VideoDetailInfo doInBackground(String... voids) {

            VideoDetailInfo info = new VideoDetailInfo();
            VideoDetailInfoDao detailInfoDao = MyApp.getDaoSession().getVideoDetailInfoDao();
            try {
                Document doc = Jsoup.connect(voids[0]).get();
                info.setDetailId(voids[1]);
                //图片
                Elements select1 = doc.select("div[class=vodImg] > img[class=lazy]");
                if (select1 != null && select1.size() > 0) {
                    String src = select1.get(0).attr("src");
                    info.setVideoCover(src);
                }
                //标题
                Elements select2 = doc.select("div[class=vodh] > h2");
                if (select2 != null && select2.size() > 0) {
                    String title = select2.get(0).text();
                    info.setVideoName(title);
                }
                //别名
                Elements select3 = doc.select("div[class=vodinfobox]");
                if (select3 != null && select3.size() > 0) {
                    Element element = select3.get(0);
                    Elements children = element.children();
                    if (children != null && children.size() > 0) {
                        List<String> descs = new ArrayList<>();
                        Element element1 = children.get(0);
                        Elements li = element1.getElementsByTag("li");
                        for (int i = 0; i < li.size(); i++) {
                            if (i == li.size() - 1 || i == li.size() - 2) {
                                continue;
                            }
                            Element ulNode = li.get(i);
                            String font = ulNode.getElementsByTag("li").get(0).text().split("：")[0];
                            String end = ulNode.select("li > span").get(0).text();
                            descs.add(font +"："+ end);
                        }
                        info.setDescs(descs);
                    }
                }
                //描述
                Elements select4 = doc.select("div[class=vodplayinfo]");
                String desc = select4.get(1).text();
                info.setDesc(desc);

                //播放链接集合
                Elements select5 = doc.select("font[color=red] > div[class=vodplayinfo]");
                if (select5 != null && select5.size() > 0) {
                    Element element = select5.get(0);
                    Elements li = element.getElementsByTag("li");
                    if (li != null && li.size() > 0) {
                        List<String> strings = new ArrayList<>();
                        for (int i = 0; i < li.size(); i++) {
                            String attr = li.get(i).getElementsByTag("input").attr("value");
                            if (attr.toLowerCase().contains(".m3u8")) {
                                String urlDesc = li.get(i).text();
                                strings.add(attr + "@@" + urlDesc);
                            }
                        }
                        info.setUrls(strings);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            detailInfoDao.insertInTx(info);
            return info;
        }


        @Override
        protected void onPostExecute(VideoDetailInfo aVoid) {
            super.onPostExecute(aVoid);
            mvpView.refreshView(aVoid);
        }
    }
}
