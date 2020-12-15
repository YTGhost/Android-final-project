package com.bjtu.campus_information_platform.fragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bjtu.campus_information_platform.R;
import com.bjtu.campus_information_platform.activity.AddCourseActivity;
import com.bjtu.campus_information_platform.activity.MyApplication;
import com.bjtu.campus_information_platform.model.Course;
import com.bjtu.campus_information_platform.util.network.HttpRequest;
import com.bjtu.campus_information_platform.util.network.OkHttpException;
import com.bjtu.campus_information_platform.util.network.RequestParams;
import com.bjtu.campus_information_platform.util.network.ResponseCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ClassListFragment extends Fragment {

    private Map<Integer, Integer> idMap;
    private Map<Integer, CardView> classMap;
    final private int maxCoursesNumber = 7;
    private View view;
    private FloatingActionButton addButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //对应map
        idMap = new HashMap<>();
        idMap.put(1, R.id.monday_class);
        idMap.put(2, R.id.tuesday_class);
        idMap.put(3, R.id.wednesday_class);
        idMap.put(4, R.id.thursday_class);
        idMap.put(5, R.id.friday_class);
        idMap.put(6, R.id.saturday_class);
        idMap.put(7, R.id.sunday_class);
        classMap = new HashMap<>();
        //view及button
        view = inflater.inflate(R.layout.fragment_classlist, container, false);
        addButton = view.findViewById(R.id.floatingActionButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddCourseActivity.class);
                startActivityForResult(intent,0);
            }
        });
        //界面刷新
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Android.intent.action.CART_BROADCAST");
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                String msg = intent.getStringExtra("data");
                if("refresh_classlist".equals(msg)){
                    loadData();
                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
        //界面初始化
        createLeftView();
        loadData();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadData();
    }

    //从数据库加载数据
    private void loadData() {
         //课程列表
        RequestParams param = new RequestParams();

        HttpRequest.getClass(MyApplication.account.getId(),param, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                List<Course> courseList = (List<Course>) responseObj;
                for (Course course : courseList) {
                    createItemCourseView(course);
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(MyApplication.context,"获取课表失败，请检查网络连接",Toast.LENGTH_LONG);
            }
        });
    }

    //创建"第几节数"视图
    private void createLeftView() {
        for (int i = 0; i < maxCoursesNumber; i++) {
            View view = LayoutInflater.from(MyApplication.context).inflate(R.layout.class_left_view, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(110,280);
            view.setLayoutParams(params);

            TextView text =view.findViewById(R.id.class_number_text);
            text.setText("第\n"+(i+1)+"\n节\n");

            LinearLayout leftViewLayout =  this.view.findViewById(R.id.left_view_layout);
            leftViewLayout.addView(view);
        }
    }

    private void createItemCourseView(final Course course) {
        int getDay = course.getDay();
        if (idMap.get(getDay) == null){
            Toast.makeText(MyApplication.context, "课程日期错误", Toast.LENGTH_LONG).show();
            return;
        }
        if(course.getStartTime() > course.getEndTime()){
            Toast.makeText(MyApplication.context, "课程开始结束时间冲突", Toast.LENGTH_LONG).show();
            return;
        }
        if(classMap.get(course.getDay()*100+course.getStartTime())!=null){
            return;
        }

        int dayId = idMap.get(getDay);
        RelativeLayout day = this.view.findViewById(dayId);

        int height = 280;
        final CardView v = (CardView)LayoutInflater.from(MyApplication.context).inflate(R.layout.course_card, null); //加载单个课程布局
        v.setY(height * (course.getStartTime()-1)); //设置开始高度,即第几节课开始x
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT,(course.getEndTime()-course.getStartTime()+1)*height - 8); //设置布局高度,即跨多少节课
        v.setLayoutParams(params);
        //设置随机颜色
        Random random = new Random();
        v.setCardBackgroundColor(Color.rgb(random.nextInt(96)+127,random.nextInt(96)+127,random.nextInt(96)+127));
        //显示课程名
        TextView name_text = v.findViewById(R.id.course_name_context);
        name_text.setText(course.getClassName()+"\n"+course.getRoom());
        day.addView(v);
        classMap.put(course.getDay()*100+course.getStartTime(),v);
        //长按删除课程
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder confirm = new AlertDialog.Builder(getContext());
                confirm.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remove(v,getDay);
                    }
                });
                confirm.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                confirm.setMessage("确认删除课程“"+course.getClassName()+"”吗？");
                confirm.setTitle("删除课程");
                confirm.show();
                return true;
            }
        });
    }

    private void remove(View v,int getDay){
        int dayId = idMap.get(getDay);
        RelativeLayout day = this.view.findViewById(dayId);
        RequestParams param = new RequestParams();
        param.put("id",String.valueOf(MyApplication.account.getId()));
        param.put("day",String.valueOf(getDay));
        param.put("startTime",String.valueOf((int)(v.getY()/280+1)));
        HttpRequest.deleteClass(param, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                v.setVisibility(View.GONE);//先隐藏
                day.removeView(v);//再移除课程视图
                classMap.remove((int)(dayId*100+v.getY()/280-1));
                Toast.makeText(MyApplication.context,"删除成功",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(MyApplication.context,"删除失败，请稍候重试",Toast.LENGTH_LONG).show();
            }
        });
    }


}
