package com.bjtu.campus_information_platform.fragment.stepUntils;

import android.content.Context;
import android.content.Intent;

import com.bjtu.campus_information_platform.activity.MainActivity;
import com.bjtu.campus_information_platform.activity.MyApplication;
import com.today.step.lib.BaseClickBroadcast;

public class MyReceiver extends BaseClickBroadcast {

    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        MyApplication tsApplication = (MyApplication) context.getApplicationContext();
        if (!tsApplication.isForeground()) {
            Intent mainIntent = new Intent(context, MainActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainIntent);
        } else {

        }
    }
}
