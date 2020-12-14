package com.bjtu.campus_information_platform.fragment.intro;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bjtu.campus_information_platform.R;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;

public class HoleHelpFragment extends Fragment implements ISlideBackgroundColorHolder {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hole_intro, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public int getDefaultBackgroundColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getContext().getResources().getColor(R.color.color_hole, null);
        } else {
            return getContext().getResources().getColor(R.color.color_hole);
        }
    }

    @Override
    public void setBackgroundColor(int color) {
        if (view != null) {
            view.setBackgroundColor(color);
        }
    }
}
