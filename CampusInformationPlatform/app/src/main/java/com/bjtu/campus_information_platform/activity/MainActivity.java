package com.bjtu.campus_information_platform.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bjtu.campus_information_platform.R;
import com.bjtu.campus_information_platform.model.Test;
import com.bjtu.campus_information_platform.util.network.HttpRequest;
import com.bjtu.campus_information_platform.util.network.OkHttpException;
import com.bjtu.campus_information_platform.util.network.RequestParams;
import com.bjtu.campus_information_platform.util.network.ResponseCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button getBtn;
    private Button postBtn;
    private Button startActivityBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        //设置顶部状态栏为透明
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        getBtn = (Button) findViewById(R.id.getBtn);
        postBtn = (Button) findViewById(R.id.postBtn);
        startActivityBtn = (Button) findViewById(R.id.startActivityBtn);

        startActivityBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,BottomBarActivity.class));
        });

        getBtn.setOnClickListener(v -> {
            HttpRequest.getTestApi(null, new ResponseCallback() {
                @Override
                public void onSuccess(Object responseObj) {
                    Toast.makeText(MainActivity.this, "请求成功" + responseObj.toString(), Toast.LENGTH_SHORT).show();
                    List<Test> list = (List<Test>) responseObj;
                    for(int i = 0; i < list.size(); i++) {
                        Test test = list.get(i);
                        Toast.makeText(MainActivity.this, "请求成功:" + test.getId() + "," + test.getUsername() + "," + test.getPassword(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(OkHttpException failuer) {
                    Log.e("TAG", "请求失败=" + failuer.getEmsg());
                    Toast.makeText(MainActivity.this, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        postBtn.setOnClickListener(v -> {
            System.out.println("测试测试测试");
            RequestParams params = new RequestParams();
            params.put("username", "admin");
            params.put("password", "123456");
            HttpRequest.postTestApi(params, new ResponseCallback() {
                @Override
                public void onSuccess(Object responseObj) {
                    Toast.makeText(MainActivity.this, "请求成功" + responseObj.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(OkHttpException failuer) {
                    Log.e("TAG", "请求失败=" + failuer.getEmsg());
                    Toast.makeText(MainActivity.this, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}