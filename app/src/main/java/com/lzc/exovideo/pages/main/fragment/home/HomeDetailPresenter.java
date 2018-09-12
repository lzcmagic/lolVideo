package com.lzc.exovideo.pages.main.fragment.home;

import android.os.AsyncTask;

import com.lzc.exovideo.BuildConfig;
import com.lzc.exovideo.MyApp;
import com.lzc.exovideo.base.IBasePresenter;
import com.lzc.exovideo.db.entity.FilmInfo;
import com.lzc.exovideo.downloader.greendao.FilmInfoDao;
import com.lzc.exovideo.utils.TimeUtil;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;
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
import java.util.concurrent.atomic.AtomicInteger;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

public class HomeDetailPresenter implements IBasePresenter<HomeDetailView> {

    private HomeDetailView mvpView;
    private AtomicInteger atomicInteger;

    private String pageSuffix="http://www.baiwanzy.com/?m=vod-type-id-1-pg-1.html";

    private List<FilmInfo> filmInfos;

    public HomeDetailPresenter() {
        filmInfos=new ArrayList<>();
        atomicInteger=new AtomicInteger(1);
    }

    @Override
    public void attachView(HomeDetailView activity) {
        this.mvpView = activity;
    }

    @Override
    public void detachView() {
        mvpView = null;
    }

    public void refreshList(String link) {
        filmInfos.clear();
        atomicInteger.set(1);
        new DownloadTask(true)
                .executeOnExecutor(Executors.newSingleThreadExecutor(), BuildConfig.HOST_API + link, link);
    }

    public void initDate(String link) {
        filmInfos.clear();
        atomicInteger.set(1);
        FilmInfoDao filmInfoDao = MyApp.getDaoSession().getFilmInfoDao();
        QueryBuilder<FilmInfo> where = filmInfoDao.queryBuilder().where(FilmInfoDao.Properties.From.eq(link));
        List<FilmInfo> list = where.list();
        if (list != null && list.size() > 0) {
            filmInfos.addAll(list);
            mvpView.refreshRecyclerView(list);
            return;
        }
        new DownloadTask(false)
                .executeOnExecutor(Executors.newSingleThreadExecutor(), BuildConfig.HOST_API + link, link);
    }


    public void pullRefrsh(String link,int state, boolean isLast){

        if (state==SCROLL_STATE_IDLE&&isLast){
            atomicInteger.addAndGet(1);
            String newLink = link.split("\\.")[0] + "-pg-" + atomicInteger.get() + ".html";
            new DownloadTask(false)
                    .executeOnExecutor(Executors.newSingleThreadExecutor(), BuildConfig.HOST_API + newLink, link);
        }
    }

    private class DownloadTask extends AsyncTask<String, String, List<FilmInfo>> {


        private boolean isUpdate;

        public DownloadTask(boolean isUpdate) {
            this.isUpdate = isUpdate;
        }

        @Override
        protected List<FilmInfo> doInBackground(String... voids) {
            List<FilmInfo> filmInfos = new ArrayList<>();
            FilmInfoDao filmInfoDao = MyApp.getDaoSession().getFilmInfoDao();

            try {
                Document doc = Jsoup.connect(voids[0]).get();
                Element xingVb = doc.getElementsByClass("xing_vb").get(0);
                Elements children = xingVb.children();
                for (Element element : children) {
                    if (element.childNodeSize() <= 0) {
                        continue;
                    }
                    Element child = element.child(0);
                    Elements element1 = child.getElementsByClass("xing_vb4");//link
                    if (element1.size() > 0) {
                        Element element2 = element1.get(0);
                        Elements select1 = element2.select("a[href]");
                        if (select1.size() > 0) {
                            Element element3 = select1.get(0);
                            Element element4 = child.getElementsByClass("xing_vb5").get(0);//type
                            Element element5 = child.getElementsByClass("xing_vb6").get(0);// date
                            FilmInfo filmInfo = new FilmInfo();
                            filmInfo.setFilmId(element3.attr("href"));
                            filmInfo.setFileLink(element3.attr("href"));
                            filmInfo.setFilmName(element3.text());
                            filmInfo.setFilmType(element4.text());
                            filmInfo.setFrom(voids[1]);
                            filmInfo.setUpdateTime(TimeUtil.getInstance().fotmatTimeByString(element5.text()));
                            filmInfos.add(filmInfo);
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (isUpdate){
                DeleteQuery<FilmInfo> deleteQuery = filmInfoDao.queryBuilder().where(FilmInfoDao.Properties.From.eq(voids[1])).buildDelete();
                deleteQuery.executeDeleteWithoutDetachingEntities();
            }
            filmInfoDao.insertOrReplaceInTx(filmInfos);
            return filmInfos;
        }


        @Override
        protected void onPostExecute(List<FilmInfo> aVoid) {
            super.onPostExecute(aVoid);
            if (isUpdate) {
                filmInfos.clear();
                filmInfos.addAll(aVoid);
                mvpView.refreshRecyclerNew(filmInfos);
            } else {
                filmInfos.addAll(aVoid);
                mvpView.refreshRecyclerView(filmInfos);
            }
        }
    }
}
