package com.cctv.ssfs.netdata;

import com.cctv.ssfs.App;
import com.cctv.ssfs.utils.LogUtils;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Qi on 2017/9/24.
 */

public class RetrofitManage {

    private static RetrofitService apiService;
    private final int CACHE_SIZE = 10 * 1024 * 1024;

    public static RetrofitService getApiService(String url) {
        new RetrofitManage(url);
        return apiService;
    }

    public OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                LogUtils.i("RetrofitLog", "retrofitBack = " + message);
            }
        });
        File cacheFile = new File(App.mActivity.getExternalCacheDir().toString(), "cache");
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Cache cache = new Cache(cacheFile, CACHE_SIZE);
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(loggingInterceptor)
                .build();
        return client;
    }


    private RetrofitManage(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(RetrofitService.class);
    }
}
