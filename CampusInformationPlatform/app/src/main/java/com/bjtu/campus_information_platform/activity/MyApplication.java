package com.bjtu.campus_information_platform.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import com.bjtu.campus_information_platform.fragment.ClassListFragment;
import com.bjtu.campus_information_platform.fragment.HoleFragment;
import com.bjtu.campus_information_platform.fragment.HomeFragment;
import com.bjtu.campus_information_platform.fragment.ProfileFragment;
import com.bjtu.campus_information_platform.fragment.SportFragment;
import com.bjtu.campus_information_platform.model.Account;


/*
* 这里继承Application是为了实现应用程序级的全局变量
* 它的生命周期就等于这个程序的生命周期
* 因为它是全局的，单例的，所以在不同的Activity，Service中获得的对象都是同一个对象
* 所以可以通过它来进行如数据传递、数据共享和数据缓存等操作*/
public class MyApplication extends Application {

    // Application和Fragment单例
    private static MyApplication sApplication;
    public static HomeFragment homeFragment = new HomeFragment();
    public static ClassListFragment classListFragment = new ClassListFragment();
    public static HoleFragment holeFragment = new HoleFragment();
    public static SportFragment sportFragment = new SportFragment();
    public static ProfileFragment profileFragment = new ProfileFragment();
    public static FragmentManager fragmentManager;

    // 登录后获取的用户信息，包括用户id，用户nickname和用户email
    public static Account account;
    //当前用户步数
    public static int step;
    //判断当前应用是否处于前台
    private static int appCount = 0;
    public static Context context;


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
