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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bjtu.campus_information_platform.R;
import com.bjtu.campus_information_platform.activity.MyApplication;
import com.bjtu.campus_information_platform.fragment.ProfileParts.ProfileFragment_About;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ProfileFragment extends Fragment {
    private ImageView headImageView;
    private ImageView backImageView;
    private ImageView msgImageView;
    /*  private TextView tv;*/
    LinearLayout linearLayoutMsg;
    LinearLayout linearLayoutHis;
    LinearLayout linearLayoutGde;
    LinearLayout linearLayoutAbt;


    static ProfileFragment_About profileFragment_About = new ProfileFragment_About();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        linearLayoutMsg = (LinearLayout) view.findViewById(R.id.msg);
        linearLayoutHis = (LinearLayout) view.findViewById(R.id.history);
        linearLayoutGde = (LinearLayout) view.findViewById(R.id.grade);
        linearLayoutAbt = (LinearLayout) view.findViewById(R.id.about);

        headImageView= (ImageView) view.findViewById(R.id.h_head);
        backImageView = (ImageView) view.findViewById(R.id.h_back);
        /* tv=(TextView) view .findViewById(R.id.tv);*/
        /* msgImageView = (ImageView) view.findViewById(R.id.msg);*/


        Glide.with(MyApplication.context).load(R.drawable.wbb)
                .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                .into(backImageView);

        Glide.with(MyApplication.context).load(R.drawable.wbb)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(headImageView);


        linearLayoutMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.view_pager_bottom, MyApplication.profileFragment_about)
                        .commit();*/

                Log.d("测试函数是否通过","通过");
            }
        });

        linearLayoutHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.view_pager_bottom, MyApplication.profileFragment_about)
                        .commit();
                *//*tv.setText("我变了哇");*/
                Log.d("测试函数是否通过","通过");
            }
        });

        linearLayoutGde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.view_pager_bottom, MyApplication.profileFragment_about)
                        .commit();
*/
                Log.d("测试函数是否通过","通过");
            }
        });

        linearLayoutAbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.fragmentManager.beginTransaction().replace(R.id.view_pager_bottom, profileFragment_About)
                        .commit();


            }
        });



        return view;
    }
}
