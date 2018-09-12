package com.lzc.exovideo.pages.main.fragment.center;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.lzc.exovideo.R;
import com.lzc.exovideo.base.BaseFragment;
import com.lzc.exovideo.base.IBasePresenter;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

public class CenterFragment extends BaseFragment implements CenterView {


    public static final String TAG=CenterFragment.class.getSimpleName();
    @Inject
    CenterPresenter centerPresenter;

    public static CenterFragment newInstance() {

        Bundle args = new Bundle();
        CenterFragment fragment = new CenterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public IBasePresenter createPresenter() {
        return centerPresenter;
    }

    @Override
    public void initLazyDate() {
        mWebView.loadUrl("file:///android_asset/center.html");
    }

    @Override
    public int getResId() {
        return R.layout.fragment_center;
    }


    private ValueCallback uploadMessage;
    private ValueCallback uploadMessageAboveL;
        WebView mWebView;
    @Override
    public void initView(View rootView) {

        mWebView = rootView.findViewById(R.id.forum_context);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);


        // 打开本地缓存提供JS调用,至关重要
        mWebView.getSettings().setDomStorageEnabled(true);
        // 8M缓存
        mWebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        String appCachePath = mWebView.getContext().getCacheDir().getAbsolutePath();
        mWebView.getSettings().setAppCachePath(appCachePath);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void openFileChooser(ValueCallback<Uri> valueCallback, String s, String s1) {
                uploadMessage = valueCallback;
                choosePicture();
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
                uploadMessageAboveL = valueCallback;
                choosePicture();
                return true;
            }
        });


            rootView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String path= Environment.getExternalStorageDirectory().getAbsolutePath();
                    QbSdk.openFileReader(getContext(), path, null, s -> {

                    });
                }
            });
    }

    private void choosePicture() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_CHOOSER_REQUEST_CODE) {
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (uploadMessageAboveL != null) {
                onActivityResultAboveL(data);
            } else if (uploadMessage != null) {
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(Intent intent) {
        Uri[] results = null;
        if (intent != null) {
            String dataString = intent.getDataString();
            ClipData clipData = intent.getClipData();
            if (clipData != null) {
                results = new Uri[clipData.getItemCount()];
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    results[i] = item.getUri();
                }
            }
            if (dataString != null)
                results = new Uri[]{Uri.parse(dataString)};
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
    }


    public static int FILE_CHOOSER_REQUEST_CODE=111;
}
