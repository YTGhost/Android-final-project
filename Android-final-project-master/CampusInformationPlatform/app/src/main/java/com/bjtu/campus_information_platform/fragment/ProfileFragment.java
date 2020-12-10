package com.bjtu.campus_information_platform.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bjtu.campus_information_platform.R;
import com.bjtu.campus_information_platform.activity.MyApplication;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ProfileFragment extends Fragment {
    private ImageView headImageView;
    private ImageView backImageView;
    private ImageView msgImageView;
    LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        linearLayout = (LinearLayout) view.findViewById(R.id.msg);
        headImageView= (ImageView) view.findViewById(R.id.h_head);
        backImageView = (ImageView) view.findViewById(R.id.h_back);
       /* msgImageView = (ImageView) view.findViewById(R.id.msg);*/

        Glide.with(MyApplication.context).load(R.drawable.wbb)
                .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                .into(backImageView);

        Glide.with(MyApplication.context).load(R.drawable.wbb)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(headImageView);


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a="打印：";
                //System.out.println("整个布局被点击");
                Log.d(a,"成功！！！");
             }
        });
        return view;
    }
}
