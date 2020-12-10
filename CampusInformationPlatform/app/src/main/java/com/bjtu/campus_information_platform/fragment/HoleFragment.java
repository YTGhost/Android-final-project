package com.bjtu.campus_information_platform.fragment;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bjtu.campus_information_platform.R;
import com.bjtu.campus_information_platform.fragment.view.MyCardView;

import java.util.ArrayList;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;
import stream.customalert.CustomAlertDialogue;

public class HoleFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate{

    private ImageButton img_btn;
    private LinearLayout hole_linear_layout;

    private BGARefreshLayout mRefreshLayout;
    private Handler mHandler = new Handler();

    public View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_hole, container, false);
        hole_linear_layout = (LinearLayout) view.findViewById(R.id.hole_linear_layout);
        img_btn = (ImageButton) view.findViewById(R.id.addHole);

        ArrayList<String> boxHint = new ArrayList<>();
        boxHint.add("");

        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomAlertDialogue.Builder alert = new CustomAlertDialogue.Builder(getActivity())
                        .setStyle(CustomAlertDialogue.Style.INPUT)
                        .setTitle("To: 树洞")
                        .setPositiveText("发送")
                        .setPositiveColor(R.color.positive)
                        .setPositiveTypeface(Typeface.DEFAULT_BOLD)
                        .setOnInputClicked(new CustomAlertDialogue.OnInputClicked() {
                            @Override
                            public void OnClick(View view, Dialog dialog, ArrayList<String> inputList) {
                                for (String input : inputList)
                                {
                                    Log.d("Input", input);
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeText("取消")
                        .setNegativeColor(R.color.negative)
                        .setOnNegativeClicked(new CustomAlertDialogue.OnNegativeClicked() {
                            @Override
                            public void OnClick(View view, Dialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .setBoxInputHint(boxHint)
                        .setDecorView(getActivity().getWindow().getDecorView())
                        .build();
                alert.show();
            }
        });

        initRefreshLayout();
        return view;
    }


    /**初始化刷新加载控件*/
    private void initRefreshLayout(){
        mRefreshLayout = findView(view, R.id.rl_hole_refresh);
        mRefreshLayout.setDelegate(this);
        BGAStickinessRefreshViewHolder refreshViewHolder = new BGAStickinessRefreshViewHolder(this.getActivity(), true);
        refreshViewHolder.setRotateImage(R.drawable.bga_refresh);
        refreshViewHolder.setStickinessColor(R.color.gery_inactive);
        refreshViewHolder.setLoadMoreBackgroundColorRes(R.color.colorPrimary);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        // 在这里加载最新数据

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.endRefreshing();
            }
        },1500);

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        // 在这里加载更多数据，或者更具产品需求实现上拉刷新也可以

        MyCardView myCardView = new MyCardView(getActivity(),null);
        myCardView.setReply_content("表白周子涵");
        myCardView.setReply_time("2020-12-12 12-21-12");
        hole_linear_layout.addView(myCardView);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.endLoadingMore();
            }
        },1500);



        return true;
    }

    // 通过代码方式控制进入正在刷新状态。应用场景：某些应用在activity的onStart方法中调用，自动进入正在刷新状态获取最新数据
    public void beginRefreshing() {
        mRefreshLayout.beginRefreshing();
    }

    // 通过代码方式控制进入加载更多状态
    public void beginLoadingMore() {
        mRefreshLayout.beginLoadingMore();
    }

    public <VT> VT findView(View rootView,int resId){
        VT t = (VT) rootView.findViewById(resId);
        return t;
    }
}
