package com.bjtu.campus_information_platform.fragment;

import android.os.Bundle;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bjtu.campus_information_platform.R;
import com.bjtu.campus_information_platform.activity.MyApplication;

import com.bjtu.campus_information_platform.activity.ProfileAbout;
import com.bjtu.campus_information_platform.activity.ProfileMySetting;
import com.bjtu.campus_information_platform.util.network.HttpRequest;
import com.bjtu.campus_information_platform.util.network.OkHttpException;
import com.bjtu.campus_information_platform.util.network.RequestParams;
import com.bjtu.campus_information_platform.util.network.ResponseCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import android.widget.Toast;


import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ProfileFragment extends Fragment {
    private ImageView headImageView;
    private ImageView backImageView;


    private TextView user_name;
    private TextView user_id;

    LinearLayout linearLayoutSet;
    LinearLayout linearLayoutUpd;
    LinearLayout linearLayoutAbt;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        linearLayoutSet = (LinearLayout) view.findViewById(R.id.set);
        linearLayoutUpd = (LinearLayout) view.findViewById(R.id.update);
        linearLayoutAbt = (LinearLayout) view.findViewById(R.id.about);

        headImageView = (ImageView) view.findViewById(R.id.h_head);
        backImageView = (ImageView) view.findViewById(R.id.h_back);
        user_name = (TextView) view.findViewById(R.id.user_name);
        user_id = (TextView) view.findViewById(R.id.user_id);

        //初始头像和背景

        Glide.with(MyApplication.context).load(MyApplication.account.getBackgroundUrl())
                .bitmapTransform(new BlurTransformation(MyApplication.context, 12), new CenterCrop(MyApplication.context))
                .into(backImageView);

        Glide.with(MyApplication.context).load(MyApplication.account.getAvatarUrl())
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(headImageView);
/*
但不知道为什么换完背景和头像Glide框架效果就消失了
 */
        user_name.setText(MyApplication.account.getNickname());
        user_id.setText(String.valueOf(MyApplication.account.getId()));


        linearLayoutSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), ProfileMySetting.class);
                    startActivityForResult(intent, 1);


                }
            });


        linearLayoutUpd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "您已经是最新版本", Toast.LENGTH_SHORT).show();

                }
            });

        linearLayoutAbt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), ProfileAbout.class);
                    startActivity(intent);


                }
            });

        return view;
    }

    public void changeAvatar() {
//        更新到数据库
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.account.getId().toString());
        params.put("avatarUrl", MyApplication.account.getAvatarUrl());
        HttpRequest.changeAvatar(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
            }

            @Override
            public void onFailure(OkHttpException failuer) {
            }
        });
        Glide.with(MyApplication.context).load(MyApplication.account.getAvatarUrl())
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(headImageView);
    }

    public void changeBackground() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.account.getId().toString());
        params.put("backgroundUrl", MyApplication.account.getBackgroundUrl());
        HttpRequest.changeBackground(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
            }

            @Override
            public void onFailure(OkHttpException failuer) {
            }
        });
        Glide.with(MyApplication.context).load(MyApplication.account.getBackgroundUrl())
                .bitmapTransform(new BlurTransformation(MyApplication.context, 12), new CenterCrop(MyApplication.context))
                .into(backImageView);
    }

public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
        user_name.setText(MyApplication.account.getNickname());
        user_id.setText(String.valueOf(MyApplication.account.getId()));

//        backImageView.setImageBitmap(MyApplication.account.getUserBackground());
//        headImageView.setImageBitmap(MyApplication.account.getUserPhoto());
        }
    }
}
