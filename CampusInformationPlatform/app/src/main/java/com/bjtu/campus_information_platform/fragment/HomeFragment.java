package com.bjtu.campus_information_platform.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bjtu.campus_information_platform.R;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView textView = (TextView) view.findViewById(R.id.tv_fragment);
        textView.setText("首页示例");
        view.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red_inactive));
        return view;
    }
}
