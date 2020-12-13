package com.bjtu.campus_information_platform.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bjtu.campus_information_platform.R;
import com.bjtu.campus_information_platform.model.Course;
import com.bjtu.campus_information_platform.util.network.HttpRequest;
import com.bjtu.campus_information_platform.util.network.OkHttpException;
import com.bjtu.campus_information_platform.util.network.RequestParams;
import com.bjtu.campus_information_platform.util.network.ResponseCallback;

public class AddCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        setFinishOnTouchOutside(false);

        final EditText inputCourseName = (EditText) findViewById(R.id.course_name);
        final EditText inputClassRoom = (EditText) findViewById(R.id.class_room);
        final Spinner inputDay = (Spinner) findViewById(R.id.weekTime);
        final Spinner inputStart = (Spinner) findViewById(R.id.startTime);

        Button okButton = (Button) findViewById(R.id.button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = inputCourseName.getText().toString();
                String classRoom = inputClassRoom.getText().toString();
                int day = inputDay.getSelectedItemPosition();
                int start = inputStart.getSelectedItemPosition();

                if (courseName.equals("") || day==0 || start==0 || classRoom.equals("")) {
                    Toast.makeText(getApplicationContext(), "基本课程信息未填写", Toast.LENGTH_SHORT).show();
                }
                else {
                    RequestParams params = new RequestParams();
                    params.put("id",String.valueOf(MyApplication.account.getId()));
                    params.put("day",String.valueOf(day));
                    params.put("startTime",String.valueOf(start));
                    params.put("endTime",String.valueOf(start));
                    params.put("courseName",courseName);
                    params.put("room",classRoom);
                    HttpRequest.postClass(params, new ResponseCallback() {
                        @Override
                        public void onSuccess(Object responseObj) {
                            Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                            intent.putExtra("data","refresh_classlist");
                            LocalBroadcastManager.getInstance(AddCourseActivity.this).sendBroadcast(intent);
                            sendBroadcast(intent);
                            Toast.makeText(MyApplication.context,"创建成功",Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void onFailure(OkHttpException failuer) {
                            Toast.makeText(getApplicationContext(),"课程已存在",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        Button cancelButton = (Button) findViewById(R.id.button2);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
