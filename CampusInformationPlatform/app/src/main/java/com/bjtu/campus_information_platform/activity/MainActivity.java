package com.bjtu.campus_information_platform.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.campus_information_platform.R;
import com.bjtu.campus_information_platform.model.Account;
import com.bjtu.campus_information_platform.util.network.HttpRequest;
import com.bjtu.campus_information_platform.util.network.OkHttpException;
import com.bjtu.campus_information_platform.util.network.RequestParams;
import com.bjtu.campus_information_platform.util.network.ResponseByteCallback;
import com.bjtu.campus_information_platform.util.network.ResponseCallback;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.royrodriguez.transitionbutton.TransitionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

//    private Button getBtn;
//    private Button postBtn;
//    private Button startActivityBtn;
//    private TransitionButton transitionButton;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private TransitionButton loginBtn;
    private TextView registerText;
    private TextView forgetText;
    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        //设置顶部状态栏为透明
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        emailInput = (TextInputEditText) findViewById(R.id.R_emailInput);
        passwordInput = (TextInputEditText) findViewById(R.id.R_passwordInput);
        loginBtn = (TransitionButton) findViewById(R.id.loginBtn);
        registerText = (TextView) findViewById(R.id.registerText);
        forgetText = (TextView) findViewById(R.id.forgetText);

        loginBtn.setOnClickListener(v -> {
            loginBtn.startAnimation();
            final Handler handler = new Handler();
            RequestParams params = new RequestParams();
            params.put("email", emailInput.getText().toString());
            params.put("password", passwordInput.getText().toString());
            HttpRequest.loginRequest(params, new ResponseCallback() {
                @Override
                public void onSuccess(Object responseObj) {
                    MyApplication.account = (Account) responseObj;
                    loginBtn.stopAnimation(TransitionButton.StopAnimationStyle.EXPAND, () -> {
                        Intent intent = new Intent(getBaseContext(), BottomBarActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        handler.postDelayed(() -> finish(), 2000);
                    });
                }

                @Override
                public void onFailure(OkHttpException failuer) {
                    handler.postDelayed(() -> {
                        loginBtn.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
                    }, 2000);
                    Log.e("TAG", "请求失败=" + failuer.getEmsg());
                }
            });
        });

        registerText.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
            startActivity(intent);
        });

        forgetText.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), ForgetActivity.class);
            startActivity(intent);
        });
        request_permissions();
    }

    private void request_permissions() {
        // 创建一个权限列表，把需要使用而没用授权的的权限存放在这里
        List<String> permissionList = new ArrayList<>();

        // 判断权限是否已经授予，没有就把该权限添加到列表中
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q&&ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACTIVITY_RECOGNITION);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        // 如果列表为空，就是全部权限都获取了，不用再次获取了。不为空就去申请权限
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissionList.toArray(new String[permissionList.size()]), 1002);
        } else {
            //Toast.makeText(this.activity, "多个权限你都有了，不用再次申请", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1002:
                // 1002请求码对应的是申请多个权限
                if (grantResults.length > 0) {
                    // 因为是多个权限，所以需要一个循环获取每个权限的获取情况
                    for (int i = 0; i < grantResults.length; i++) {
                        // PERMISSION_DENIED 这个值代表是没有授权，我们可以把被拒绝授权的权限显示出来
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            Toast.makeText(this, permissions[i] + "权限被拒绝了", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }

}