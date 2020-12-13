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

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout R_emailLayout;
    private TextInputLayout R_passwordLayout;
    private TextInputLayout R_againLayout;
    private TextInputLayout R_nicknameLayout;
    private TextInputEditText R_emailInput;
    private TextInputEditText R_passwordInput;
    private TextInputEditText R_againInput;
    private TextInputEditText R_nicknameInput;
    private CodeInput R_codeInput;
    private Button codeBtn;
    private TransitionButton registerBtn;
    private TextView haveAccountText;

    private Boolean isEmailOk = false;
    private Boolean isPasswordOk = false;
    private Boolean isAgainOk = false;
    private Boolean isNicknameOk = false;
    private Boolean isEmailOnly = false;
    private Boolean isNicknameOnly = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        // 设置顶部状态栏为透明
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // 绑定控件
        R_emailLayout = (TextInputLayout) findViewById(R.id.R_emailLayout);
        R_passwordLayout = (TextInputLayout) findViewById(R.id.R_passwordLayout);
        R_againLayout = (TextInputLayout) findViewById(R.id.R_againLayout);
        R_nicknameLayout = (TextInputLayout) findViewById(R.id.R_nicknameLayout);
        codeBtn = (Button) findViewById(R.id.codeBtn);
        R_emailInput = (TextInputEditText) findViewById(R.id.R_emailInput);
        R_passwordInput = (TextInputEditText) findViewById(R.id.R_passwordInput);
        R_againInput = (TextInputEditText) findViewById(R.id.R_againInput);
        R_codeInput = (CodeInput) findViewById(R.id.R_codeInput);
        R_nicknameInput = (TextInputEditText) findViewById(R.id.R_nicknameInput);
        haveAccountText = (TextView) findViewById(R.id.haveAccountText);
        registerBtn = (TransitionButton) findViewById(R.id.registerBtn);


        // 点击事件
        registerBtn.setOnClickListener(v -> {
            registerBtn.startAnimation();
            if(!isEmailOk) {
                R_emailLayout.setErrorEnabled(true);
                R_emailLayout.setError("邮箱地址非法");
                registerBtn.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
                return;
            }
            if(!isEmailOnly) {
                R_emailLayout.setErrorEnabled(true);
                R_emailLayout.setError("邮箱已经被注册");
                registerBtn.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
                return;
            }
            if(!isNicknameOk) {
                R_nicknameLayout.setErrorEnabled(true);
                R_nicknameLayout.setError("用户名以字母开头，长度在3～10之间");
                registerBtn.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
                return;
            }
            if(!isNicknameOnly) {
                R_nicknameLayout.setErrorEnabled(true);
                R_nicknameLayout.setError("用户名已经被注册");
                registerBtn.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
                return;
            }
            if(!isPasswordOk) {
                R_passwordLayout.setErrorEnabled(true);
                R_passwordLayout.setError("密码以字母开头，长度在6～18之间");
                registerBtn.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
                return;
            }
            if(!isAgainOk) {
                R_againLayout.setErrorEnabled(true);
                R_againLayout.setError("密码不一致");
                registerBtn.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
                return;
            }
            StringBuilder code = new StringBuilder();
            String email = R_emailInput.getText().toString();
            String nickname = R_nicknameInput.getText().toString();
            String password = R_passwordInput.getText().toString();
            Character[] codeCharacter = R_codeInput.getCode();
            final Handler handler = new Handler();
            for(int i = 0; i < 6; i++) {
                if(codeCharacter[i] == null) break;
                code.append(codeCharacter[i]);
            }
            RequestParams params = new RequestParams();
            params.put("email", email);
            params.put("nickname", nickname);
            params.put("password", password);
            params.put("code", code.toString());
            HttpRequest.registerRequest(params, new ResponseCallback() {
                @Override
                public void onSuccess(Object responseObj) {
                    Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    registerBtn.stopAnimation(TransitionButton.StopAnimationStyle.EXPAND, () -> {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        handler.postDelayed(() -> finish(), 2000);
                    });
                }

                @Override
                public void onFailure(OkHttpException failuer) {
                    Toast.makeText(RegisterActivity.this, "注册失败，请检查验证码是否错误或过期", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(() -> {
                        registerBtn.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
                    }, 2000);
                }
            });
        });

        // 发送验证码
        codeBtn.setOnClickListener(v -> {
            if(!isEmailOk) {
                R_emailLayout.setErrorEnabled(true);
                R_emailLayout.setError("邮箱地址非法");
                return;
            }
            HttpRequest.getRegisterCodeRequest(R_emailInput.getText().toString(), null, new ResponseCallback() {
                @Override
                public void onSuccess(Object responseObj) {
                    startTimer();
                }

                @Override
                public void onFailure(OkHttpException failuer) {
                    if(failuer.getEcode() == 1) {
                        R_emailLayout.setErrorEnabled(true);
                        R_emailLayout.setError("邮箱已经被注册");
                        isEmailOnly = false;
                    }
                }
            });

        });

        // 失去焦点时判断邮箱唯一性
        R_emailInput.setOnFocusChangeListener((view, b) -> {
            // 失去焦点时
            if(!b) {
                HttpRequest.judgeEmailRequest(R_emailInput.getText().toString(), null, new ResponseCallback() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        if(isEmailOk) {
                            R_emailLayout.setErrorEnabled(false);
                            isEmailOnly = true;
                        }
                    }

                    @Override
                    public void onFailure(OkHttpException failuer) {
                        R_emailLayout.setErrorEnabled(true);
                        R_emailLayout.setError("邮箱已经被注册");
                        isEmailOnly = false;
                    }
                });
            }
        });

        // 失去焦点时判断用户名唯一性
        R_nicknameInput.setOnFocusChangeListener(((view, b) -> {
            // 失去焦点时
            if(!b) {
                HttpRequest.judgeNicknameRequest(R_nicknameInput.getText().toString(), null, new ResponseCallback() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        if(isNicknameOk) {
                            R_nicknameLayout.setErrorEnabled(false);
                            isNicknameOnly = true;
                        }
                    }

                    @Override
                    public void onFailure(OkHttpException failuer) {
                        R_nicknameLayout.setErrorEnabled(true);
                        R_nicknameLayout.setError("用户名已经被注册");
                        isNicknameOnly = false;
                    }
                });
            }
        }));

        // 动态判断邮箱是否符合规范
        R_emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!Validator.isEmail(editable.toString())) {
                    R_emailLayout.setErrorEnabled(true);
                    R_emailLayout.setError("邮箱地址非法");
                    isEmailOk = false;
                } else {
                    R_emailLayout.setErrorEnabled(false);
                    isEmailOk = true;
                }
            }
        });

        // 动态判断用户名是否符合规范
        R_nicknameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!Validator.isNickname(editable.toString())) {
                    R_nicknameLayout.setErrorEnabled(true);
                    R_nicknameLayout.setError("用户名长度3～10位，支持字母数字和下划线");
                    isNicknameOk = false;
                } else {
                    System.out.println("test2");
                    R_nicknameLayout.setErrorEnabled(false);
                    isNicknameOk = true;
                }
            }
        });

        // 动态判断密码是否符合规范
        R_passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!Validator.isPassword(editable.toString())) {
                    R_passwordLayout.setErrorEnabled(true);
                    R_passwordLayout.setError("密码以字母开头，长度在6～18之间");
                    isPasswordOk = false;
                } else {
                    R_passwordLayout.setErrorEnabled(false);
                    isPasswordOk = true;
                }
                // 密码更新时去判断下二次密码是否一致
                if(!editable.toString().equals(R_againInput.getText().toString())) {
                    R_againLayout.setErrorEnabled(true);
                    R_againLayout.setError("密码不一致");
                    isAgainOk = false;
                } else {
                    R_againLayout.setErrorEnabled(false);
                    isAgainOk = true;
                }
            }
        });

        // 动态判断再次输入的密码是否一致
        R_againInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals(R_passwordInput.getText().toString())) {
                    R_againLayout.setErrorEnabled(true);
                    R_againLayout.setError("密码不一致");
                    isAgainOk = false;
                } else {
                    R_againLayout.setErrorEnabled(false);
                    isAgainOk = true;
                }
            }
        });

        // 切换回登录界面
        haveAccountText.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
        });

    }

    /**
     * 验证码倒计时函数
     */
    private void startTimer() {
        codeBtn.setEnabled(false);
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
                            codeBtn.setText("发送");
                            codeBtn.setEnabled(true);
                        } else {
                            codeBtn.setText(second + "s");
                        }
                    }
                });
            }
        }).start();
    }
}
