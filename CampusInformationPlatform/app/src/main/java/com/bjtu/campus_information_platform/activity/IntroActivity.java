package com.bjtu.campus_information_platform.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.bjtu.campus_information_platform.fragment.intro.CourseHelpFragment;
import com.bjtu.campus_information_platform.fragment.intro.HoleHelpFragment;
import com.bjtu.campus_information_platform.fragment.intro.RoomHelpFragment;
import com.bjtu.campus_information_platform.fragment.intro.SportHelpFragment;
import com.github.paolorotolo.appintro.AppIntro;

import org.jetbrains.annotations.Nullable;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        //设置顶部状态栏为透明
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        addSlide(new CourseHelpFragment());
        addSlide(new RoomHelpFragment());
        addSlide(new HoleHelpFragment());
        addSlide(new SportHelpFragment());

        showStatusBar(false);
        setColorTransitionsEnabled(true);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        // Do something when users tap on Done button.
        super.onDonePressed(currentFragment);
        this.finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }
}
