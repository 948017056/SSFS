package com.cctv.ssfs.netdata;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Qi on 2017/9/25.
 */

public class MyObserver<T> implements Observer<T> {

    private static final String TAG = "MyObserver";
    private ObserverOnNextListener listener;

    public MyObserver(ObserverOnNextListener listener) {
        this.listener = listener;

    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG,e.toString());
    }

    @Override
    public void onComplete() {
    }


    @Override
    public void onNext(T t) {
        listener.onNext(t);
    }


}
