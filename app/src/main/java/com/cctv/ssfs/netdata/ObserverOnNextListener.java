package com.cctv.ssfs.netdata;

/**
 * Created by Qi on 2017/9/25.
 * 成功的接口回调
 */
public interface ObserverOnNextListener<T> {
    void onNext(T t);
}
