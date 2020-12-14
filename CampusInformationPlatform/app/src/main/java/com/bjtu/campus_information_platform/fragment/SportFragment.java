package com.bjtu.campus_information_platform.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bjtu.campus_information_platform.R;
import com.bjtu.campus_information_platform.activity.MyApplication;
import com.bjtu.campus_information_platform.fragment.view.MyListViewAdapter;
import com.bjtu.campus_information_platform.model.Step;
import com.bjtu.campus_information_platform.util.network.HttpRequest;
import com.bjtu.campus_information_platform.util.network.OkHttpException;
import com.bjtu.campus_information_platform.util.network.RequestParams;
import com.bjtu.campus_information_platform.util.network.ResponseByteCallback;
import com.bjtu.campus_information_platform.util.network.ResponseCallback;
import com.today.step.lib.ISportStepInterface;
import com.today.step.lib.TodayStepManager;
import com.today.step.lib.TodayStepService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;

import static com.bjtu.campus_information_platform.activity.MyApplication.context;
import static com.bjtu.campus_information_platform.activity.MyApplication.getApplication;

public class SportFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private Activity activity;

    private BGARefreshLayout mRefreshLayout;
    public View view;
    private ListView listView;
    ImageView mImageView;
    View header;

    //循环取当前时刻的步数中间的间隔时间
    private long TIME_INTERVAL_REFRESH = 3000;
    private Handler mDelayHandler = new Handler(new TodayStepCounterCall());
    private int mStepSum = 0;
    private int oldStep=0;
    private int newStep=0;
    private static final int REFRESH_STEP_WHAT = 0;
    private ISportStepInterface iSportStepInterface;
    private CountDownTimer countDownTimer;
    private Handler mHandler = new Handler();

    public Map<String,String> map=new HashMap<>();
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    private Boolean isFinished = true;
    private Boolean isFirst=true;
    private Boolean isRefreshing=false;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sport, container, false);
        TodayStepManager.startTodayStepService(getApplication());


        listView = view.findViewById(R.id.step_list_view);
        header = LayoutInflater.from(activity).inflate(R.layout.listview_header, null);
        mImageView = (ImageView) header.findViewById(R.id.layout_header_image);
        if(map.get(getApplication().account.getBackgroundUrl())==null){
            HttpRequest.getImgApi(getApplication().account.getBackgroundUrl(), null, String.valueOf(System.currentTimeMillis()) + ".png", new ResponseByteCallback() {
                @Override
                public void onSuccess(File file) {
                    map.put(getApplication().account.getBackgroundUrl(),Uri.fromFile(new File(file.getAbsolutePath())).toString());
                    mImageView.setImageURI(Uri.fromFile(new File(file.getAbsolutePath())));
                    if(listView.getHeaderViewsCount()!=0&&!isRefreshing){
                        beginRefreshing();
                    }
                }

                @Override
                public void onFailure(String failureMsg) {

                }
            });
        }else{
            mImageView.setImageURI(Uri.parse(map.get(getApplication().account.getBackgroundUrl())));
        }

        listView.addHeaderView(header);

        initRefreshLayout();
        if(!isRefreshing){
            beginRefreshing();
        }



        //权限申请

        //开启计步Service，同时绑定Activity进行aidl通信
        Intent intent = new Intent(this.activity, TodayStepService.class);
        activity.startService(intent);
        activity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //Activity和Service通过aidl进行通信
                iSportStepInterface = ISportStepInterface.Stub.asInterface(service);
                try {
                    mStepSum = iSportStepInterface.getCurrentTimeSportStep();
                    oldStep=mStepSum;
                    MyApplication.step = mStepSum;
                    updateStepCount();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                mDelayHandler.sendEmptyMessageDelayed(REFRESH_STEP_WHAT, TIME_INTERVAL_REFRESH);
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        }, Context.BIND_AUTO_CREATE);
        //计时器
        mhandmhandlele.post(timeRunable);
        return view;
    }



    private void initRefreshLayout() {
        mRefreshLayout = findView(view, R.id.sport_refresh_layout);
        mRefreshLayout.setDelegate(this);
        BGAStickinessRefreshViewHolder refreshViewHolder = new BGAStickinessRefreshViewHolder(this.getActivity(), false);
        refreshViewHolder.setRotateImage(R.drawable.bga_refresh);
        refreshViewHolder.setStickinessColor(R.color.gery_inactive);
        refreshViewHolder.setLoadMoreBackgroundColorRes(R.color.colorPrimary);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (isFinished&&!isRefreshing) {
            beginRefreshing();
            isFinished = false;
            countDownTimer = new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    //Log.e("LJZ", "1s is gone!");
                }

                @Override
                public void onFinish() {
                    isFinished = true;
                }
            };
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        beginLoadingMore();
        return true;
    }

    // 通过代码方式控制进入正在刷新状态。应用场景：某些应用在activity的onStart方法中调用，自动进入正在刷新状态获取最新数据
    public void beginRefreshing() {
        isRefreshing=true;
        RequestParams params = new RequestParams();
        params.put("nickname", MyApplication.account.getNickname());
        params.put("steps", String.valueOf(mStepSum));
        MyApplication.step = mStepSum;
        Log.e("ljz", "start request");
        HttpRequest.postStepApi(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {

                List<Step> list = (List<Step>) responseObj;
                List<Map<String, String>> data = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    if(list.get(i).getNickname().equals(getApplication().account.getNickname())&&list.get(i).getSteps()>mStepSum){
                        mStepSum=list.get(i).getSteps();

                    }
                    Map<String, String> userData = new HashMap<>();
                    userData.put("nickname", list.get(i).getNickname());
                    userData.put("rank", String.valueOf(i + 1));
                    userData.put("steps", String.valueOf(list.get(i).getSteps()));
                    userData.put("avatarUrl", list.get(i).getAvatarUrl());
                    data.add(userData);
                }
                if(map.get(getApplication().account.getBackgroundUrl())==null){
                    HttpRequest.getImgApi(getApplication().account.getBackgroundUrl(), null, String.valueOf(System.currentTimeMillis()) + ".png", new ResponseByteCallback() {
                        @Override
                        public void onSuccess(File file) {
                            map.put(getApplication().account.getBackgroundUrl(),Uri.fromFile(new File(file.getAbsolutePath())).toString());
                            listView.setAdapter(null);
                            listView.removeHeaderView(header);
                            header = LayoutInflater.from(activity).inflate(R.layout.listview_header, null);
                            mImageView = (ImageView) header.findViewById(R.id.layout_header_image);
                            mImageView.setImageURI(Uri.fromFile(new File(file.getAbsolutePath())));
                            header.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,400));
                            listView.addHeaderView(header);
                            listView.setAdapter(new MyListViewAdapter(activity, data));
                            fixListViewHeight(listView);
                            mRefreshLayout.endRefreshing();
                            isFinished = true;
                            isRefreshing=false;
                        }

                        @Override
                        public void onFailure(String failureMsg) {

                        }
                    });
                }else{
                    listView.setAdapter(new MyListViewAdapter(activity, data));
                    fixListViewHeight(listView);
                    mRefreshLayout.endRefreshing();
                    isFinished = true;
                    isRefreshing=false;
                }


            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Log.e("ljz", "failed");
                String[] steps = new String[1];
                steps[0] = MyApplication.account.getNickname() + "  steps: " + 0;

            }

        });

    }

    public void fixListViewHeight(ListView listView) {
        // 如果没有设置数据适配器，则ListView没有子项，返回。
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        if (listAdapter == null) {
            return;
        }
        for (int index = 0, len = listAdapter.getCount(); index < len; index++) {
            View listViewItem = listAdapter.getView(index, null, listView);
            // 计算子项View 的宽高
            listViewItem.measure(0, 0);
            // 计算所有子项的高度和
            totalHeight += listViewItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // listView.getDividerHeight()获取子项间分隔符的高度
        // params.height设置ListView完全显示需要的高度
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    // 通过代码方式控制进入加载更多状态
    public void beginLoadingMore() {
        mHandler.post(() -> mRefreshLayout.endLoadingMore());
    }

    public <VT> VT findView(View rootView, int resId) {
        VT t = (VT) rootView.findViewById(resId);
        return t;
    }

    class TodayStepCounterCall implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            updateStepCount();
            switch (msg.what) {
                case REFRESH_STEP_WHAT: {
                    //每隔500毫秒获取一次计步数据刷新UI
                    if (null != iSportStepInterface) {
                        int step = 0;
                        try {
                            
                            newStep=iSportStepInterface.getCurrentTimeSportStep();
                            mStepSum=mStepSum+newStep-oldStep;
                            oldStep=newStep;
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }

                    }
                    mDelayHandler.sendEmptyMessageDelayed(REFRESH_STEP_WHAT, TIME_INTERVAL_REFRESH);

                    break;
                }
            }
            return false;
        }
    }

    private void updateStepCount() {
        //Log.e("aaa","updateStepCount : " + mStepSum);

    }


    /*****************计时器*******************/
    private Runnable timeRunable = new Runnable() {
        @Override
        public void run() {

            currentSecond = currentSecond + 1000;

            if (!isPause) {
                //递归调用本runable对象，实现每隔一秒一次执行任务
                mhandmhandlele.postDelayed(this, 1000);
            }
        }
    };
    //计时器
    private Handler mhandmhandlele = new Handler();
    private boolean isPause = false;//是否暂停
    private long currentSecond = 0;//当前毫秒数
/*****************计时器*******************/

    /**
     * 根据毫秒返回时分秒
     *
     * @param time
     * @return
     */
    public static String getFormatHMS(long time) {
        time = time / 1000;//总秒数
        int s = (int) (time % 60);//秒
        int m = (int) (time / 60);//分
        int h = (int) (time / 3600);//秒
        return String.format("%02d:%02d:%02d", h, m, s);
    }
}
