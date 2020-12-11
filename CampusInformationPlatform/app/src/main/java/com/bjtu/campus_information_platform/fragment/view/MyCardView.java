package com.bjtu.campus_information_platform.fragment.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bjtu.campus_information_platform.R;

public class MyCardView extends ConstraintLayout {

    private TextView reply_time;
    private TextView reply_content;

    public MyCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.my_card, this, true);
        reply_content = (TextView) findViewById(R.id.my_reply_content);
        reply_time = (TextView)findViewById(R.id.my_reply_time);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.MyCardAttribute);
        if (attributes != null) {
            String date = attributes.getString(R.styleable.MyCardAttribute_hole_date);
            if (!TextUtils.isEmpty(date)) {
                reply_time.setText(date);
            }

            String text = attributes.getString(R.styleable.MyCardAttribute_hole_content);
            if (!TextUtils.isEmpty(text)) {
                reply_content.setText(text);
            }
            attributes.recycle();
        }

    }


    public TextView getReply_time() {
        return reply_time;
    }

    public TextView getReply_content() {
        return reply_content;
    }

    public void setReply_time(String time){
        reply_time.setText(time);
    }

    public void setReply_content(String content){
        reply_content.setText(content);
    }
}
