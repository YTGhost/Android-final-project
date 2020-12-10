package com.bjtu.campus_information_platform.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/*
* 这里继承Application是为了实现应用程序级的全局变量
* 它的生命周期就等于这个程序的生命周期
* 因为它是全局的，单例的，所以在不同的Activity，Service中获得的对象都是同一个对象
* 所以可以通过它来进行如数据传递、数据共享和数据缓存等操作*/
public class MyApplication extends Application {

    private static MyApplication sApplication;
    private int appCount = 0;
    public static Context context;
    public Boolean isSend=false;
    public String nickname="ljz";
    public int mStep=0;
    public String[] steps={nickname+" step: "+mStep};
    public List<String> stepList= new ArrayList<>(Arrays.asList(steps));
    public Boolean isUpdate=false;




    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        sApplication = this;

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                appCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                appCount--;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    /**
     * app是否在前台
     * @return true前台，false后台
     */
    public boolean isForeground(){
        return appCount > 0;
    }

    public static MyApplication getApplication() {
        return sApplication;
    }

}
