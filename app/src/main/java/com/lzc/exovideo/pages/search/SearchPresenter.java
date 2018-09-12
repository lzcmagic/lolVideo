package com.lzc.exovideo.pages.search;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import com.lzc.exovideo.MyApp;
import com.lzc.exovideo.api.ApiService;
import com.lzc.exovideo.api.RetrofitManager;
import com.lzc.exovideo.base.IBasePresenter;
import com.lzc.exovideo.db.entity.FilmInfo;
import com.lzc.exovideo.downloader.greendao.FilmInfoDao;
import com.lzc.exovideo.utils.TimeUtil;

import org.greenrobot.greendao.query.DeleteQuery;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenter implements IBasePresenter<ISearchView> {

    private ISearchView mvpView;
    private Activity mAct;
    private Call<ResponseBody> call;
    private DownloadTask downloadTask;
    public SearchPresenter(Activity activity){
        this.mAct=activity;
    }

    @Override
    public void attachView(ISearchView activity) {
        this.mvpView=activity;
    }

    @Override
    public void detachView() {
        if (call!=null){
            call.cancel();
            call=null;
        }
        if (downloadTask!=null){
            downloadTask.cancel(true);
            downloadTask=null;
        }
        mvpView=null;
        mAct=null;
    }

    public void searchWd(String wd){
        ApiService api = RetrofitManager.getInstance().createApi(ApiService.class);
        call = api.queryByName(wd, "submit");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String string = response.body().string();
                   downloadTask=new DownloadTask();
                   downloadTask.executeOnExecutor(Executors.newSingleThreadExecutor(),string);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mvpView.searchFailed("解析错误");
            }
        });
    }


    private class DownloadTask extends AsyncTask<String, String, List<FilmInfo>> {

        @Override
        protected List<FilmInfo> doInBackground(String... voids) {

            if (isCancelled()){
                return null;
            }
            List<FilmInfo> filmInfos = new ArrayList<>();
            try {
                Document doc = Jsoup.parse(voids[0]);
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
                            filmInfo.setFrom("query");
                            filmInfo.setUpdateTime(TimeUtil.getInstance().fotmatTimeByString(element5.text()));
                            filmInfos.add(filmInfo);
                        }
                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return filmInfos;
        }


        @Override
        protected void onPostExecute(List<FilmInfo> aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid!=null){
                if (aVoid.size()>0){
                    mvpView.searchSuccess(aVoid);
                }else{
                    mvpView.searchFailed("没有搜到结果");
                }
            }
        }
    }
}
