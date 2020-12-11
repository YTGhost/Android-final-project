package com.bjtu.campus_information_platform.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bjtu.campus_information_platform.R;
import com.bjtu.campus_information_platform.activity.MyApplication;
import com.bjtu.campus_information_platform.model.Step;
import com.bjtu.campus_information_platform.util.network.HttpRequest;
import com.bjtu.campus_information_platform.util.network.OkHttpException;
import com.bjtu.campus_information_platform.util.network.RequestParams;
import com.bjtu.campus_information_platform.util.network.ResponseCallback;
import com.today.step.lib.ISportStepInterface;
import com.today.step.lib.TodayStepManager;
import com.today.step.lib.TodayStepService;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;

import static com.bjtu.campus_information_platform.activity.MyApplication.getApplication;

public class SportFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate{
    private Activity activity;

    private BGARefreshLayout mRefreshLayout;
    public View view;
    private ListView listView;
    ImageView mImageView;
    TextView mTextView;
    //private MyScrollView scrollView;
    //循环取当前时刻的步数中间的间隔时间
    private long TIME_INTERVAL_REFRESH = 3000;
    private Handler mDelayHandler = new Handler(new TodayStepCounterCall());
    private int mStepSum=0;
    private static final int REFRESH_STEP_WHAT = 0;
    private ISportStepInterface iSportStepInterface;

    private Handler mHandler=new Handler();

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sport, container, false);
        TodayStepManager.startTodayStepService(getApplication());


        listView=view.findViewById(R.id.step_list_view);
        View header=LayoutInflater.from(activity).inflate(R.layout.listview_header,null);
        mImageView=(ImageView)header.findViewById(R.id.layout_header_image);
        listView.addHeaderView(header);
        View footer=LayoutInflater.from(activity).inflate(R.layout.listview_footer,null);
        mTextView=(TextView)footer.findViewById(R.id.footer_text);
        listView.addFooterView(footer);
        initRefreshLayout();
        beginRefreshing();


        //权限申请
        request_permissions();
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
                    MyApplication.step=mStepSum;
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


    private void request_permissions() {
        // 创建一个权限列表，把需要使用而没用授权的的权限存放在这里
        List<String> permissionList = new ArrayList<>();

        // 判断权限是否已经授予，没有就把该权限添加到列表中
        if (ContextCompat.checkSelfPermission(this.activity, Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACTIVITY_RECOGNITION);
        }

        if (ContextCompat.checkSelfPermission(this.activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this.activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        // 如果列表为空，就是全部权限都获取了，不用再次获取了。不为空就去申请权限
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this.activity,
                    permissionList.toArray(new String[permissionList.size()]), 1002);
        } else {
            //Toast.makeText(this.activity, "多个权限你都有了，不用再次申请", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1002:
                // 1002请求码对应的是申请多个权限
                if (grantResults.length > 0) {
                    // 因为是多个权限，所以需要一个循环获取每个权限的获取情况
                    for (int i = 0; i < grantResults.length; i++) {
                        // PERMISSION_DENIED 这个值代表是没有授权，我们可以把被拒绝授权的权限显示出来
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            Toast.makeText(this.activity, permissions[i] + "权限被拒绝了", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }
    private void initRefreshLayout(){
        mRefreshLayout = findView(view, R.id.sport_refresh_layout);
        mRefreshLayout.setDelegate(this);
        BGAStickinessRefreshViewHolder refreshViewHolder = new BGAStickinessRefreshViewHolder(this.getActivity(), true);
        refreshViewHolder.setRotateImage(R.drawable.bga_refresh);
        refreshViewHolder.setStickinessColor(R.color.gery_inactive);
        refreshViewHolder.setLoadMoreBackgroundColorRes(R.color.colorPrimary);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        beginRefreshing();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        beginLoadingMore();
        return true;
    }
    // 通过代码方式控制进入正在刷新状态。应用场景：某些应用在activity的onStart方法中调用，自动进入正在刷新状态获取最新数据
    public void beginRefreshing() {
        RequestParams params = new RequestParams();
        params.put("nickname", MyApplication.account.getNickname());
        params.put("steps", String.valueOf(mStepSum));
        MyApplication.step=mStepSum;
        Log.e("ljz", "start request");
        HttpRequest.postStepApi(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {

                List<Step> list = (List<Step>) responseObj;
                List<String> stepsList = new ArrayList<>();
                list.forEach(step -> {
                    stepsList.add(step.getNickname() + "  steps: " + step.getSteps());
                });
                String[] steps = (String[]) stepsList.toArray(new String[0]);

                listView.setAdapter(new ArrayAdapter<String>(activity,
                        android.R.layout.simple_expandable_list_item_1,
                        steps
                ));
                fixListViewHeight(listView);
                mRefreshLayout.endRefreshing();
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Log.e("ljz", "failed");
                String[] steps=new String[1];
                steps[0]=MyApplication.account.getNickname()+"  steps: "+0;

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
            View listViewItem = listAdapter.getView(index , null, listView);
            // 计算子项View 的宽高
            listViewItem.measure(0, 0);
            // 计算所有子项的高度和
            totalHeight += listViewItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // listView.getDividerHeight()获取子项间分隔符的高度
        // params.height设置ListView完全显示需要的高度
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    // 通过代码方式控制进入加载更多状态
    public void beginLoadingMore() {
        mHandler.post(()->mRefreshLayout.endLoadingMore());
    }

    public <VT> VT findView(View rootView,int resId){
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
                            step = iSportStepInterface.getCurrentTimeSportStep();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        if (mStepSum != step) {
                            mStepSum = step;
                            MyApplication.step=mStepSum;
                            updateStepCount();
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