package com.bjtu.campus_information_platform.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bjtu.campus_information_platform.R;
import com.bjtu.campus_information_platform.util.network.HttpRequest;
import com.bjtu.campus_information_platform.util.network.OkHttpException;
import com.bjtu.campus_information_platform.util.network.RequestParams;
import com.bjtu.campus_information_platform.util.network.ResponseCallback;
import com.bjtu.campus_information_platform.util.validator.Validator;
import com.github.glomadrian.codeinputlib.CodeInput;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.royrodriguez.transitionbutton.TransitionButton;

public class ForgetActivity extends AppCompatActivity {

    private TextInputLayout F_emailLayout;
    private TextInputLayout F_passwordLayout;
    private TextInputLayout F_againLayout;
    private TextInputEditText F_emailInput;
    private TextInputEditText F_passwordInput;
    private TextInputEditText F_againInput;
    private CodeInput F_codeInput;
    private Button F_codeBtn;
    private TransitionButton modifyPasswordBtn;
    private TextView returnLogin;

    private Boolean isEmailOk = false;
    private Boolean isPasswordOk = false;
    private Boolean isAgainOk = false;
    private Boolean isEmailExist = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        getSupportActionBar().hide();
        //设置顶部状态栏为透明
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // 绑定控件
        F_emailLayout = (TextInputLayout) findViewById(R.id.F_emailLayout);
        F_passwordLayout = (TextInputLayout) findViewById(R.id.F_passwordLayout);
        F_againLayout = (TextInputLayout) findViewById(R.id.F_againLayout);
        F_emailInput = (TextInputEditText) findViewById(R.id.F_emailInput);
        F_passwordInput = (TextInputEditText) findViewById(R.id.F_passwordInput);
        F_againInput = (TextInputEditText) findViewById(R.id.F_againInput);
        F_codeInput = (CodeInput) findViewById(R.id.F_codeInput);
        modifyPasswordBtn = (TransitionButton) findViewById(R.id.modifyPasswordBtn);
        F_codeBtn = (Button) findViewById(R.id.F_codeBtn);
        returnLogin = (TextView) findViewById(R.id.returnLogin);

        modifyPasswordBtn.setOnClickListener(v -> {
            modifyPasswordBtn.startAnimation();
            if(!isEmailOk) {
                F_emailLayout.setErrorEnabled(true);
                F_emailLayout.setError("邮箱地址非法");
                modifyPasswordBtn.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
                return;
            }
            if(!isEmailExist) {
                F_emailLayout.setErrorEnabled(true);
                F_emailLayout.setError("邮箱不存在");
                modifyPasswordBtn.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
                return;
            }
            if(!isPasswordOk) {
                F_passwordLayout.setErrorEnabled(true);
                F_passwordLayout.setError("密码以字母开头，长度在6～18之间");
                modifyPasswordBtn.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
                return;
            }
            if(!isAgainOk) {
                F_againLayout.setErrorEnabled(true);
                F_againLayout.setError("密码不一致");
                modifyPasswordBtn.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
                return;
            }
            StringBuilder code = new StringBuilder();
            String email = F_emailInput.getText().toString();
            String password = F_passwordInput.getText().toString();
            Character[] codeCharacter = F_codeInput.getCode();
            final Handler handler = new Handler();
            for(int i = 0; i < 6; i++) {
                if(codeCharacter[i] == null) break;
                code.append(codeCharacter[i]);
            }
            RequestParams params = new RequestParams();
            params.put("email", email);
            params.put("password", password);
            params.put("code", code.toString());
            HttpRequest.forgetRequest(params, new ResponseCallback() {
                @Override
                public void onSuccess(Object responseObj) {
                    Toast.makeText(ForgetActivity.this, "修改密码成功！", Toast.LENGTH_SHORT).show();
                    modifyPasswordBtn.stopAnimation(TransitionButton.StopAnimationStyle.EXPAND, () -> {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        handler.postDelayed(() -> finish(), 2000);
                    });
                }

                @Override
                public void onFailure(OkHttpException failuer) {
                    Toast.makeText(ForgetActivity.this, "修改密码失败，请检查验证码是否错误或过期", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(() -> {
                        modifyPasswordBtn.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
                    }, 2000);
                }
            });
        });

        // 返回登录界面
        returnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
        });

        // 失去焦点时判断邮箱是否存在
        F_emailInput.setOnFocusChangeListener((view, b) -> {
            // 失去焦点时
            if(!b) {
                HttpRequest.judgeEmailRequest(F_emailInput.getText().toString(), null, new ResponseCallback() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        F_emailLayout.setErrorEnabled(true);
                        F_emailLayout.setError("邮箱不存在");
                        isEmailExist = false;
                    }

                    @Override
                    public void onFailure(OkHttpException failuer) {
                        if(isEmailOk) {
                            F_emailLayout.setErrorEnabled(false);
                            isEmailExist = true;
                        }
                    }
                });
            }
        });

        // 动态判断邮箱是否符合规范
        F_emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!Validator.isEmail(editable.toString())) {
                    F_emailLayout.setErrorEnabled(true);
                    F_emailLayout.setError("邮箱地址非法");
                    isEmailOk = false;
                } else {
                    F_emailLayout.setErrorEnabled(false);
                    isEmailOk = true;
                }
            }
        });

        // 动态判断密码是否符合规范
        F_passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!Validator.isPassword(editable.toString())) {
                    F_passwordLayout.setErrorEnabled(true);
                    F_passwordLayout.setError("密码以字母开头，长度在6～18之间");
                    isPasswordOk = false;
                } else {
                    F_passwordLayout.setErrorEnabled(false);
                    isPasswordOk = true;
                }
                // 密码更新时去判断下二次密码是否一致
                if(!editable.toString().equals(F_againInput.getText().toString())) {
                    F_againLayout.setErrorEnabled(true);
                    F_againLayout.setError("密码不一致");
                    isAgainOk = false;
                } else {
                    F_againLayout.setErrorEnabled(false);
                    isAgainOk = true;
                }
            }
        });

        // 动态判断再次输入的密码是否一致
        F_againInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals(F_passwordInput.getText().toString())) {
                    F_againLayout.setErrorEnabled(true);
                    F_againLayout.setError("密码不一致");
                    isAgainOk = false;
                } else {
                    F_againLayout.setErrorEnabled(false);
                    isAgainOk = true;
                }
            }
        });

        // 发送验证码
        F_codeBtn.setOnClickListener(v -> {
            if(!isEmailOk) {
                F_emailLayout.setErrorEnabled(true);
                F_emailLayout.setError("邮箱地址非法");
                return;
            }
            HttpRequest.getForgetCodeRequest(F_emailInput.getText().toString(), null, new ResponseCallback() {
                @Override
                public void onSuccess(Object responseObj) {
                    startTimer();
                }

                @Override
                public void onFailure(OkHttpException failuer) {
                    int Ecode = failuer.getEcode();
                    if(Ecode == 1) {
                        F_emailLayout.setErrorEnabled(true);
                        F_emailLayout.setError("邮箱不存在");
                        isEmailExist = false;
                        Toast.makeText(ForgetActivity.this, "邮箱不存在", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

    /**
     * 短信验证码倒计时函数
     */
    private void startTimer() {
        F_codeBtn.setEnabled(false);
        new Thread(() -> {
            for (int i = 59; i >= 0; i--) {
                final int second = i;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (second <= 0) {
                            F_codeBtn.setText("发送");
                            F_codeBtn.setEnabled(true);
                        } else {
                            F_codeBtn.setText(second + "s");
                        }
                    }
                });
            }
        }).start();
    }
}
