package com.bjtu.campus_information_platform.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bjtu.campus_information_platform.R;
import com.bjtu.campus_information_platform.adapter.ScreenSlidePageFragmentAdapter;
import com.bjtu.campus_information_platform.fragment.ClassListFragment;
import com.bjtu.campus_information_platform.fragment.HoleFragment;
import com.bjtu.campus_information_platform.fragment.HomeFragment;
import com.bjtu.campus_information_platform.fragment.ProfileFragment;
import com.bjtu.campus_information_platform.fragment.SportFragment;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;

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
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new ClassListFragment());
        mFragmentList.add(new HoleFragment());
        mFragmentList.add(new SportFragment());
        mFragmentList.add(new ProfileFragment());

        // 设置适配器
        mAdapter = new ScreenSlidePageFragmentAdapter(getSupportFragmentManager(), mFragmentList);
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
    }
}
