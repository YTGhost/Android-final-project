package com.bjtu.campus_information_platform.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.bjtu.campus_information_platform.R;
import com.bjtu.campus_information_platform.adapter.ScreenSlidePageFragmentAdapter;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BottomBarActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private BubbleNavigationLinearView mBubbleNavigationLinearView;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private ScreenSlidePageFragmentAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);
        getSupportActionBar().hide();
        //设置顶部状态栏为透明
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        mViewPager = (ViewPager) findViewById(R.id.view_pager_bottom);
        mBubbleNavigationLinearView = (BubbleNavigationLinearView) findViewById(R.id.bottom_navigation_view_linear);

        // 添加所需的Fragment
        mFragmentList.add(MyApplication.classListFragment);
        mFragmentList.add(MyApplication.homeFragment);
        mFragmentList.add(MyApplication.holeFragment);
        mFragmentList.add(MyApplication.sportFragment);
        mFragmentList.add(MyApplication.profileFragment);
        MyApplication.sportFragment.setActivity(this);
        MyApplication.fragmentManager = getSupportFragmentManager();

        // 设置适配器
        mAdapter = new ScreenSlidePageFragmentAdapter(MyApplication.fragmentManager, mFragmentList);
        mViewPager.setAdapter(mAdapter);

        // viewpager滑动切换并改变导航栏
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBubbleNavigationLinearView.setCurrentActiveItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 导航栏切换改变Viewpager
        mBubbleNavigationLinearView.setNavigationChangeListener((view, position) -> {
            mViewPager.setCurrentItem(position, true);
        });

        // 新用户进入引导
        if(MyApplication.account.getIsNew() == 1) {
            Intent intent = new Intent(getBaseContext(), IntroActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String pathUrl =
                Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                        + "/zhangshangtongtong/";
        deleteFile(new File(pathUrl));
        MyApplication.sportFragment.map.clear();
    }

    private void deleteFile(File file){
        if(file.isDirectory()){
            File[] files=file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f=files[i];
                Log.e("LJZ","delete filename = "+f.getName());
                deleteFile(f);

            }
        }else if(file.exists()){
            file.delete();
        }
    }
}
