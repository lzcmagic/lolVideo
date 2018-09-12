package com.lzc.exovideo.base;

public interface IBasePresenter<T> {

    void attachView(T activity);

    void detachView();
}
