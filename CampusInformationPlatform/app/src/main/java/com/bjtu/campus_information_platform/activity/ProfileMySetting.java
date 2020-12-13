package com.bjtu.campus_information_platform.activity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bjtu.campus_information_platform.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ProfileMySetting extends AppCompatActivity {


    private int SELECT_BACKGROUND = 0x01;
    private int SELECT_PICTURE = 0x00;
    private Bitmap bitmap;

    private TextView name;
    private TextView id;
    LinearLayout linearLayoutImage;
    LinearLayout linearLayoutBackground;
    LinearLayout linearLayoutName;
    LinearLayout linearLayoutId;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile_setting);

        name = (TextView) findViewById(R.id.set_name);
        id = (TextView) findViewById(R.id.set_id);

        name.setText(MyApplication.account.getNickname());
        id.setText(String.valueOf(MyApplication.account.getId()));

        linearLayoutImage = (LinearLayout) findViewById(R.id.imageLayout);
        linearLayoutBackground = (LinearLayout) findViewById(R.id.backgroundLayout);
        linearLayoutName = (LinearLayout) findViewById(R.id.nameLayout);
        linearLayoutId = (LinearLayout) findViewById(R.id.idLayout);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        linearLayoutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] items = new String[]{"拍照", "从相册获取"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileMySetting.this);

                builder.setTitle("选择获取图片方式");     //设置对话框标题
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {



                        switch (which) {
                            case 0:
                                /*Intent intentCamer = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intentCamer, SELECT_CAMER);
                                dialog.dismiss();*/
                                Toast.makeText(ProfileMySetting.this, "暂不支持拍照功能", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.addCategory(intent.CATEGORY_OPENABLE);
                                intent.setType("image/*");
                                startActivityForResult(intent.createChooser(intent, "选择图片"), SELECT_PICTURE);
                                dialog.dismiss();//取消弹窗
                                break;
                        }

                        Toast.makeText(ProfileMySetting.this, "更换头像成功", Toast.LENGTH_SHORT).show();

                    }
                });


                AlertDialog dialog = builder.create();  //创建对话框

                dialog.setCanceledOnTouchOutside(true);      //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏

                dialog.show();


            }
        });

        linearLayoutBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] items = new String[]{"拍照", "从相册获取"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileMySetting.this);

                builder.setTitle("选择获取图片方式");     //设置对话框标题
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                             /*   Intent intentCamer = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intentCamer, SELECT_CAMER);*/
                                Toast.makeText(ProfileMySetting.this, "暂不支持拍照功能", Toast.LENGTH_SHORT).show();
                                /* dialog.dismiss();*/
                                break;
                            case 1:
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.addCategory(intent.CATEGORY_OPENABLE);
                                intent.setType("image/*");
                                startActivityForResult(intent.createChooser(intent, "选择图片"), SELECT_BACKGROUND);
                                dialog.dismiss();//取消弹窗
                                break;
                        }
                        Toast.makeText(ProfileMySetting.this, "更换背景成功", Toast.LENGTH_SHORT).show();
                    }


                });

                AlertDialog dialog = builder.create();  //创建对话框

                dialog.setCanceledOnTouchOutside(true);      //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏

                dialog.show();
            }
        });

        linearLayoutName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(ProfileMySetting.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileMySetting.this);

                builder.setTitle("请输入修改后的用户名");     //设置对话框标题
                builder.setView(editText);
                builder.setIcon(android.R.drawable.btn_star);      //设置对话框标题前的图标
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialog, int which) {
                        String change_Name = String.valueOf(editText.getText());
                        MyApplication.account.setNickname(change_Name);
                        /*更改用户名*/
                        name.setText(MyApplication.account.getNickname());
                        Toast.makeText(ProfileMySetting.this, "更改成功", Toast.LENGTH_SHORT).show();

                    }
                });


                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setCancelable(true);   //设置按钮是否可以按返回键取消,false则不可以取消

                AlertDialog dialog = builder.create();  //创建对话框

                dialog.setCanceledOnTouchOutside(true);      //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏

                dialog.show();
            }
        });

        linearLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(ProfileMySetting.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileMySetting.this);

                builder.setTitle("请输入修改后的用户ID");     //设置对话框标题
                builder.setView(editText);
                builder.setIcon(android.R.drawable.btn_star);      //设置对话框标题前的图标


                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialog, int which) {
                        int ID = 0;
                        try {
                            ID = Integer.parseInt(editText.getText().toString());
                        } catch (Exception e) {
                            return;
                        }
                        MyApplication.account.setId(ID);
                        /*更改用户ID*/
                        id.setText(String.valueOf(MyApplication.account.getId()));
                        Toast.makeText(ProfileMySetting.this, "更改成功", Toast.LENGTH_SHORT).show();

                    }
                });


                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setCancelable(true);   //设置按钮是否可以按返回键取消,false则不可以取消

                AlertDialog dialog = builder.create();  //创建对话框

                dialog.setCanceledOnTouchOutside(true);      //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏

                dialog.show();
            }
        });


    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE) {

            handle(resultCode, data);
            MyApplication.account.setUserPhoto(bitmap);
            /*更改用户头像*/

        } else if(requestCode==SELECT_BACKGROUND){
            handle(resultCode, data);
            MyApplication.account.setUserBackground(bitmap);
            /*更改用户背景*/
        }


    }

    private void handle(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {//结果代码是Ok的
            Uri uri = data.getData();
            if (uri != null && data.getData() != null) {

                ContentResolver contentResolver = this.getContentResolver();
                if (bitmap != null) {
                    bitmap.recycle();
                }
                try {
                    bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                Log.i("提示：", "uri为空或者data为空   " + "数据：" + data.getData() + "  uri: " + uri);
            }
        }
    }

}