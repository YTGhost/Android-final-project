package com.bjtu.campus_information_platform.fragment.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bjtu.campus_information_platform.R;
import com.bjtu.campus_information_platform.activity.MainActivity;
import com.bjtu.campus_information_platform.util.network.HttpRequest;
import com.bjtu.campus_information_platform.util.network.ResponseByteCallback;

import java.io.File;
import java.util.List;
import java.util.Map;

public class MyListViewAdapter extends BaseAdapter {

    public List<Map<String,String>> list;
    public Context context;

    public MyListViewAdapter(Context context, List<Map<String, String>> data) {
        this.context=context;
        this.list=data;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        viewHolder = new ViewHolder();
        convertView = LayoutInflater.from(context).inflate(R.layout.itemstyle_layout, null);

        viewHolder.rank = (TextView) convertView.findViewById(R.id.rank);
        viewHolder.nickname = (TextView) convertView.findViewById(R.id.nickname);
        viewHolder.steps = (TextView) convertView.findViewById(R.id.steps);
        viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);


        String url = list.get(position).get("avatarUrl");
        if(url==null){
            viewHolder.nickname.setText(list.get(position).get("nickname"));
            viewHolder.rank.setText(list.get(position).get("rank"));
            viewHolder.steps.setText(list.get(position).get("steps"));
            viewHolder.avatar.setImageResource(R.drawable.ic_baseline_person_24);
        }else{
            HttpRequest.getImgApi(url, null, String.valueOf(System.currentTimeMillis()) + ".png", new ResponseByteCallback() {
                @Override
                public void onSuccess(File file) {
                    viewHolder.nickname.setText(list.get(position).get("nickname"));
                    viewHolder.rank.setText(list.get(position).get("rank"));
                    viewHolder.steps.setText(list.get(position).get("steps"));
                    Log.e("TAG", "图片下载成功="+file.getAbsolutePath());
                    viewHolder.avatar.setImageURI(Uri.fromFile(new File(file.getAbsolutePath())));
                }
                @Override
                public void onFailure(String failureMsg) {
                    viewHolder.nickname.setText(list.get(position).get("nickname"));
                    viewHolder.rank.setText(list.get(position).get("rank"));
                    viewHolder.steps.setText(list.get(position).get("steps"));
                    viewHolder.avatar.setImageResource(R.drawable.ic_baseline_person_24);
                }
            });
        }



        return convertView;
    }

    static class ViewHolder{
        ImageView avatar;
        TextView rank;
        TextView nickname;
        TextView steps;
    }
}
