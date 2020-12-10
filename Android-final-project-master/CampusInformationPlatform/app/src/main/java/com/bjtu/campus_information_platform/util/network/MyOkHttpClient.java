package com.bjtu.campus_information_platform.util.network;

import com.bjtu.campus_information_platform.activity.MyApplication;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MyOkHttpClient {

    private static final int TIME_OUT = 30;
    private static OkHttpClient okHttpClient;

    /**
     * 为我们的Client配置参数，使用静态语句块来配置
     * 只执行一次，运行一开始就开辟了内存，内存放在全局
     */
    static {
        //获取缓存路径
        File cacheDir = MyApplication.context.getExternalCacheDir();

        //设置缓存的大小
        int cacheSize = 10 * 1024 * 1024;
        //创建我们Client对象的构建者
        okhttp3.OkHttpClient.Builder okHttpBuilder = new okhttp3.OkHttpClient.Builder();
        okHttpBuilder
                //为构建者设置超时时间
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                //设置缓存
                .cache(new Cache(cacheDir.getAbsoluteFile(), cacheSize));

        okHttpClient = okHttpBuilder.build();
    }

    /**
     * GET请求
     */
    public static Call get(Request request, ResposeDataHandle handle) {
        Call call = okHttpClient.newCall(request);
        call.enqueue(new MyJsonCallback(handle));
        return call;
    }

    /**
     * POST请求
     */
    public static Call post(Request request, ResposeDataHandle handle) {
        Call call = okHttpClient.newCall(request);
        call.enqueue(new MyJsonCallback(handle));
        return call;
    }
}
