package com.lzc.exovideo;

import android.content.Context;

import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.lzc.exovideo.di.base.AppComponent;
import com.lzc.exovideo.di.base.DaggerAppComponent;
import com.lzc.exovideo.downloader.greendao.DaoMaster;
import com.lzc.exovideo.downloader.greendao.DaoSession;
import com.tencent.smtt.sdk.QbSdk;

import org.greenrobot.greendao.database.Database;

import java.lang.ref.WeakReference;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class MyApp extends DaggerApplication {

    private static DaoSession daoSession;
    private static WeakReference<Context> mContext;
    protected String userAgent;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mContext=new WeakReference<>(base);
    }

    public static Context getAppContext(){
        return mContext.get();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //腾讯x5
        QbSdk.initX5Environment(this,null);
        initGreenDao();
        userAgent = Util.getUserAgent(this, "exovideo");
        trustEveryone();
    }

    /** Returns a {@link HttpDataSource.Factory}. */
    public HttpDataSource.Factory buildHttpDataSourceFactory(
            TransferListener<? super DataSource> listener) {
        return new DefaultHttpDataSourceFactory(userAgent, listener);
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper openHelper=new DaoMaster.DevOpenHelper(this,"exovideo.db");
        Database writableDb = openHelper.getWritableDb();
        DaoMaster daoMaster=new DaoMaster(writableDb);
        daoSession = daoMaster.newSession();

    }

    public static DaoSession getDaoSession(){
        return daoSession;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent build =
                DaggerAppComponent.builder().application(this).build();
        build.inject(this);
        return build;
    }


    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager(){
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) {}
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) {}
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }}}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }
}
