package com.bjtu.campus_information_platform.activity;

import android.app.Application;
import android.content.Context;


/*
* 这里继承Application是为了实现应用程序级的全局变量
* 它的生命周期就等于这个程序的生命周期
* 因为它是全局的，单例的，所以在不同的Activity，Service中获得的对象都是同一个对象
* 所以可以通过它来进行如数据传递、数据共享和数据缓存等操作*/
public class MyApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
