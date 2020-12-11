package com.bjtu.campus_information_platform.fragment.ProfileParts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bjtu.campus_information_platform.R;

public class ProfileFragment_About extends Fragment{

    private ImageView backImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_about, container, false);
        Log.d("测试函数是否通过","通过");
        backImageView= (ImageView) view.findViewById(R.id.back);

/*
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                if(manager.getBackStackEntryCount() != 0){
                    manager.popBackStack();
                }
            }
        });
*/




        return view;
    }
}
