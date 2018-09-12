package com.lzc.exovideo.pages.main.fragment.home;

import android.os.AsyncTask;
import android.util.Log;

import com.lzc.exovideo.BuildConfig;
import com.lzc.exovideo.MyApp;
import com.lzc.exovideo.base.IBasePresenter;
import com.lzc.exovideo.db.entity.BWTitle;
import com.lzc.exovideo.db.entity.FilmInfo;
import com.lzc.exovideo.downloader.greendao.BWTitleDao;
import com.lzc.exovideo.downloader.greendao.FilmInfoDao;
import com.lzc.exovideo.utils.TimeUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;

public class HomePresenter implements IBasePresenter<HomeView> {


    private HomeView mvpView;
    @Override
    public void attachView(HomeView activity) {
        this.mvpView=activity;
    }


    @Override
    public void detachView() {
        mvpView=null;
    }

    public void initTitle(){
        BWTitleDao bwTitleDao = MyApp.getDaoSession().getBWTitleDao();
        List<BWTitle> list = bwTitleDao.queryBuilder().list();
        if (list != null && list.size() > 0) {
            mvpView.setTitle(list);
            return;
        }
        new DownloadTitleTask().execute(BuildConfig.HOST_API);
    }


    private class DownloadTitleTask extends AsyncTask<String, String, List<BWTitle>> {

        @Override
        protected List<BWTitle> doInBackground(String... voids) {
            List<BWTitle> filmInfos = new ArrayList<>();
            BWTitleDao bwTitleDao = MyApp.getDaoSession().getBWTitleDao();
            try {
                Document doc = Jsoup.connect(voids[0]).get();
                Elements select = doc.select("ul[id=sddm]");
                if (select.size() > 0) {
                    Element element = select.get(0);
                    if (element.childNodeSize() > 0) {
                        Elements children = element.children();
                        for (int i = 0; i < children.size(); i++) {
                            if (i == 0 || i == 1 || i == 7 || i == 8) {
                                continue;
                            }
                            BWTitle bwTitle = new BWTitle();
                            Element element1 = children.get(i);
                            Element element2 = element1.select("a[href]").get(0);
                            bwTitle.setLink(element2.attr("href"));
                            bwTitle.setName(element2.text());
                            filmInfos.add(bwTitle);
                        }
                    }
                }
                bwTitleDao.insertInTx(filmInfos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return filmInfos;
        }


        @Override
        protected void onPostExecute(List<BWTitle> aVoid) {
            super.onPostExecute(aVoid);
            mvpView.setTitle(aVoid);
        }
    }
}
