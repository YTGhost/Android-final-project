package com.bjtu.campus_information_platform.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bjtu.campus_information_platform.R;
import com.bjtu.campus_information_platform.model.Account;
import com.bjtu.campus_information_platform.util.network.HttpRequest;
import com.bjtu.campus_information_platform.util.network.OkHttpException;
import com.bjtu.campus_information_platform.util.network.RequestParams;
import com.bjtu.campus_information_platform.util.network.ResponseCallback;
import com.google.android.material.textfield.TextInputEditText;
import com.royrodriguez.transitionbutton.TransitionButton;

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
    }
}